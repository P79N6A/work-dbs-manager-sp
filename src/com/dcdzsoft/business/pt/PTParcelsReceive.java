package com.dcdzsoft.business.pt;

import java.io.File;
import java.util.Iterator;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.print.ItemBean;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 确认接收包裹 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTParcelsReceive extends ActionBean
{

    public String doBusiness(InParamPTParcelsReceive in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PPCID)	
			||StringUtils.isEmpty(in.ItemStatus)	
			||StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);
        
        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

           //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
        
        //if(!SysDict.ITEM_STATUS_DROP_LISTED.equals(in.ItemStatus)){
        //	throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
        //}
        
        //获取操作员所属分拣中心信息		     
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);
        
        IMPostalProcessingCenterDAO ppcDAO = daoFactory.getIMPostalProcessingCenterDAO();
        IMPostalProcessingCenter ppc = new IMPostalProcessingCenter();
        ppc.PPCID = in.PPCID;
        ppc = ppcDAO.find(ppc);
        
        //4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID); 
        
		String nowStatus  = in.ItemStatus;//当前订单状态判断
        String nextStatus = SysDict.ITEM_STATUS_DROP_RECEIVED;
        
        //#start订单信息检查，筛选符合条件的订单 ：1）属于同一PPC的订单，订单状态为:0-Listed
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
  			//获取订单的PPCID，检查是否是来自同一PPC的订单
          	if(!in.PPCID.equals(readyPack.PPCID)){
      			throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTIPPC);
      		}
  			//状态检查0-Listed ,Receive
  			if(!in.ItemStatus.equals(readyPack.ItemStatus)
  				&&!SysDict.ITEM_STATUS_DROP_RECEIVED.equals(readyPack.ItemStatus)){
  				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
  				//continue;
          	}
	        
          	//添加到订单打印列表
          	readyPackList.add(readyPack);
          	
	        //#start更新订单状态
  			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        if(StringUtils.isNotEmpty(readyPack.ZoneIDDes)){//将转运过来的ZoneID更新
	        	setCols.add("ZoneID", readyPack.ZoneIDDes);
	        }
	        setCols.add("ItemStatus", nextStatus);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", readyPack.PackageID);
	        whereCols.add("ItemStatus", nowStatus);
	        int count = readyPackDAO.update(setCols, whereCols);
	        if(count<=0){
	        	continue;
	        }
	        //#end
	        
	        //"Receive("+ppc.PPCName+"=>"+zone.ZoneName+")"
	        String[] itemDesc= {ppc.PPCName,zone.ZoneName};
	        
	        //#start记录订单状态更新信息
            PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
            itemLifeCycle.PackageID  = readyPack.PackageID;
            itemLifeCycle.ItemStatus = nextStatus;
            itemLifeCycle.OperatorID     = operOnline.OperID;
            itemLifeCycle.OperatorType   = ""+operOnline.OperType;
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.ZoneID = readyPack.ZoneID;
            itemLifeCycle.LastStatus = readyPack.ItemStatus;
            if(readyPack.LastModifyTime == null){
            	readyPack.LastModifyTime = sysDateTime;
            }
            itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_RECEIVE, itemLifeCycle, itemDesc);
            //#end
	        
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");
	        //#end
          	
      	}
      	//#end
      	
		//检查是否需要打印：
		boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, in.ItemStatus, 
      	zone.MandatoryFlag, SysDict.MANDATORY_REPORT6_CODE,ErrorCode.ERR_MANDATORY_REPORT6_PRINT);*/
		
    	String documentID = "";
        //获取报表数据
        if(readyPackList.size()>0 && isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_RECEIVED, in.PPCID, operOnline.ZoneID);
        	
        	//#start  获取打印报表数据列表
        	//PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        	Iterator<PTReadyPackage> readyPackListIre = readyPackList.iterator();
        	while(readyPackListIre.hasNext()){
        		PTReadyPackage readyPack = readyPackListIre.next();
        		
    	        //更新订单打印报表流水号
            	JDBCFieldArray whereCols = new JDBCFieldArray();
            	JDBCFieldArray setCols = new JDBCFieldArray();
            	setCols.add("ReportOrderID", documentID);
            	setCols.add("LastModifyTime", sysDateTime);
            	
            	whereCols.add("PackageID", readyPack.PackageID);
    	        readyPackDAO.update(setCols, whereCols);
            	
        	}
        	//#end 
        }

        return documentID;
    }
}
