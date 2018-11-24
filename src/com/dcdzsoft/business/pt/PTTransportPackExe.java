package com.dcdzsoft.business.pt;

import java.util.Iterator;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 指定投递员派送 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class PTTransportPackExe extends ActionBean
{

    public String doBusiness(InParamPTTransportPackExe in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        String result = "";

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ZoneIDDes)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.DropAgentID)
			||StringUtils.isEmpty(in.ItemStatus))
			throw new EduException(ErrorCode.ERR_PARMERR);
		//判断订单状态是否为转运
		if(!SysDict.ITEM_STATUS_DROP_TRANSFERLIST.equals(in.ItemStatus)){
			throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
		}

		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		//#start 信息检查
        //管理员分拣区域信息
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);
        
      //投递员信息
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.DropAgentID;
        postman = postmanDAO.find(postman);
        
//      if(!postman.ZoneID.equals(operOnline.ZoneID)){
//        throw new EduException(ErrorCode.ERR_OPERAT_SELECTPOSTMAN_BELONGZONE);
//      }
		//4.解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
        String nextStatus = SysDict.ITEM_STATUS_DROP_PACKAGE_LISTED;
		String nowStatus  = in.ItemStatus;
		
		//5.更新订单状态。
		//#start订单信息检查，ZoneIDDes记录到PTReadyPackage
        PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
  		java.util.List<PTReadyPackage> readyPackList = new java.util.LinkedList<PTReadyPackage>();  
  		
      	for(String[] itemCodeAndTime:itemsCodeAndTime){
  			String itemCode = itemCodeAndTime[0];
  			if(StringUtils.isEmpty(itemCode)){
  				continue;
  			}
  			PTReadyPackage readyPack = new PTReadyPackage();
  			readyPack.PackageID = itemCode;
  			try{
  				readyPack = readyPackDAO.find(readyPack);
  			}catch(EduException e){
  				continue;
  			}
  			if(!SysDict.ITEM_STATUS_DROP_TRANSFERLIST.equals(readyPack.ItemStatus)){
  				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
  				//continue;
          	}
  			//记录订单信息，用于打印
  			readyPackList.add(readyPack);
  			result = readyPack.ReportOrderID;
	        //#start 更新订单状态
	        JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
	        setCols.add("TerminalNo", "");
	        setCols.add("ZoneIDDes", in.ZoneIDDes);
	        setCols.add("DropAgentID", postman.PostmanID);
	        setCols.add("ItemStatus", nextStatus);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", readyPack.PackageID);
	        whereCols.add("ItemStatus", nowStatus);
	        
	        readyPackDAO.update(setCols, whereCols);
	        //#end
	        //#start记录订单状态更新信息
            PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
            itemLifeCycle.PackageID  = itemCode;
            itemLifeCycle.ItemStatus = nextStatus;
            itemLifeCycle.OperatorID    = in.OperID;
            itemLifeCycle.OperatorType  = ""+operOnline.OperType;
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.ZoneID = readyPack.ZoneID;
            itemLifeCycle.LastStatus = readyPack.ItemStatus;
            if(readyPack.LastModifyTime == null){
            	readyPack.LastModifyTime = sysDateTime;
            }
            itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_ADD2ORDER, itemLifeCycle, null);
            //#end
            
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");
	       // #end

      	}
        
      	//检查是否需要打印：
      	boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, nowStatus, 
              		zone.MandatoryFlag, SysDict.MANDATORY_REPORT4_CODE,ErrorCode.ERR_MANDATORY_REPORT4_PRINT);*/
      
      	String documentID = "";
      	//获取报表数据
        if(readyPackList.size()>0 && isPrint){
	        //DocumentID
	        documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_TRANSFER, operOnline.ZoneID, in.ZoneIDDes);         
	        //#start  获取打印报表数据列表            
	        Iterator<PTReadyPackage> readyItemIre = readyPackList.iterator();
	      	while(readyItemIre.hasNext()){
	      		PTReadyPackage historyPack = readyItemIre.next();
        		              	
                //更新订单打印报表流水号
              	JDBCFieldArray whereCols = new JDBCFieldArray();
              	JDBCFieldArray setCols = new JDBCFieldArray();
              	
              	setCols.add("ReportOrderID", documentID);
              	setCols.add("LastModifyTime", sysDateTime);
              
              	whereCols.add("PackageID", historyPack.PackageID);
              	whereCols.add("CreateTime", historyPack.CreateTime);
              
              	readyPackDAO.update(setCols, whereCols);
              	
              	//#start  调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)        	
        		OPOperatorLog log = new OPOperatorLog();
        		log.OperID = in.OperID;
        		log.FunctionID = in.getFunctionID();
        		log.OccurTime = sysDateTime;
        		log.StationAddr = operOnline.LoginIPAddr;
        		log.Remark = "itemCode="+historyPack.PackageID+",reportid="+documentID;

        		commonDAO.addOperatorLog(log);
        		//#end
          	}
          	//#end
	      	result = documentID;
        }
        return result;
    }
}
