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
 * <p>Description: 指定投递员派送 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTDropOrderItemsExe extends ActionBean
{
    public String doBusiness(InParamPTDropOrderItemsExe in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)
        	//||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.DropAgentID))
            throw new EduException(ErrorCode.ERR_PARMERR);
        
        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        
        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
        
        if(!SysDict.ITEM_STATUS_DROP_SCHEDULED.equals(in.ItemStatus)){
        	throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
        }
        
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
        
        if(!postman.ZoneID.equals(operOnline.ZoneID)){
            throw new EduException(ErrorCode.ERR_OPERAT_SELECTPOSTMAN_BELONGZONE);
        }
        
        //柜体信息
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        TBTerminal terminal = new TBTerminal();
        terminal.TerminalNo  =  in.TerminalNo;
        terminal = terminalDAO.find(terminal);
        
        //柜体权限检查
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        IMZoneLockerRight zoneLockerRight = new IMZoneLockerRight();
        zoneLockerRight.ZoneID     = operOnline.ZoneID;
        zoneLockerRight.TerminalNo = in.TerminalNo;
        if(!zoneLockerRightDAO.isExist(zoneLockerRight)){
        	throw new EduException(ErrorCode.ERR_OPERAT_FORBIDUSERLOCKER);
        }
        
        if(!postman.ZoneID.equals(operOnline.ZoneID)){
        	throw new EduException(ErrorCode.ERR_OPERAT_SELECTPOSTMAN_BELONGZONE);
        }
        if(!SysDict.POSTMAN_RIGHT_YES.equals(postman.DropRight)){
        	throw new EduException(ErrorCode.ERR_OPERAT_SELECTPOSTMAN_NO_DROPRIGHT);
        }
        //#end
        
        //4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
        
        String nowStatus  = SysDict.ITEM_STATUS_DROP_SCHEDULED;//当前订单状态判断
        String nextStatus = SysDict.ITEM_STATUS_DROP_INTRANSIT_OUT;
        
        //#start订单信息检查，筛选符合条件的订单 ：属于同一柜体(in.TerminalNo)的订单;订单状态为:3-scheduled
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
  			//检查TerminalNo
          	if(StringUtils.isEmpty(in.TerminalNo)){
          		in.TerminalNo = readyPack.TerminalNo;
          	}
          	if(!in.TerminalNo.equals(readyPack.TerminalNo)){
      			throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTILOCKER);
      		}
  			//
  			if(!SysDict.ITEM_STATUS_DROP_SCHEDULED.equals(readyPack.ItemStatus)){
  				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
  				//continue;
          	}
  			
  		    //记录订单信息，用于打印
	        readyPackList.add(readyPack);
	        
	        //#start 更新订单状态
	        JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
	        setCols.add("DropAgentID", postman.PostmanID);
	        setCols.add("ItemStatus", nextStatus);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", readyPack.PackageID);
	        whereCols.add("ItemStatus", nowStatus);
	        
	        readyPackDAO.update(setCols, whereCols);
	        //#end
	        
	        //"ExeDropOrder("+postman.PostmanID+"-"+postman.PostmanName+"=>"+readyPack.TerminalNo+")"
	        String[] itemDesc= {postman.PostmanID,postman.PostmanName, readyPack.TerminalNo};

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
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_EXEORDER, itemLifeCycle, itemDesc);
            //#end
	        
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");
	        //#end
      	}
      	//#end
      	
      	String documentID = "";
		//检查是否需要打印：
		boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, in.ItemStatus, 
      	zone.MandatoryFlag, SysDict.MANDATORY_REPORT2_CODE,ErrorCode.ERR_MANDATORY_REPORT2_PRINT);*/
		
        //获取报表数据
        if(readyPackList.size()>0 && isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_DROP, operOnline.ZoneID, in.TerminalNo);
        	
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
            	
            	//#start  调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)        	
    			OPOperatorLog log = new OPOperatorLog();
    			log.OperID = in.OperID;
    			log.FunctionID = in.getFunctionID();
    			log.OccurTime = sysDateTime;
    			log.StationAddr = operOnline.LoginIPAddr;
    			log.Remark = "itemCode="+readyPack.PackageID+",reportid="+documentID;

    			commonDAO.addOperatorLog(log);
    			//#end
        	}
        	//#end 
        	

        }
    	return documentID;
    }
}
