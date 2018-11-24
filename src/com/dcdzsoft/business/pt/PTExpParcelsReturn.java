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
 * <p>Description: 确认收到超期未取包裹 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTExpParcelsReturn extends ActionBean
{

    public String doBusiness(InParamPTExpParcelsReturn in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			//||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.ItemStatus))
            throw new EduException(ErrorCode.ERR_PARMERR);

        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        in.ZoneID = operOnline.ZoneID;
        
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);
        
        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

        //4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);  	
		
        String postmanid = "";
        String postmanName = "";
        
        //更新订单状态
        PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
        java.util.List<PTDeliverHistory> historyPackList = new java.util.LinkedList<PTDeliverHistory>();
		String nextStatus = SysDict.ITEM_STATUS_DROP_RETURNED;
		switch(in.ItemStatus){
		case SysDict.ITEM_STATUS_DROP_INTRANSIT_OUT:
			//订单信息检查，筛选符合条件的订单 ：同一柜体订单，订单状态为:intransit-out
			PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
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
				//状态检查 
				if(!in.ItemStatus.equals(readyPack.ItemStatus)){
					throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
	  				//continue;
	        	}
				
				if(StringUtils.isEmpty(postmanid)){
	        		postmanid = readyPack.DropAgentID;
	        	}
	        	//同一postman的订单
	        	if(!postmanid.equals(readyPack.DropAgentID)){
	        		throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTIPOSTMAN);
	  				//continue;
	        	}
	        	
		        //#start 订单插入历史记录表
		        //先删除，保证数据不重复??  --预先插入历史订单表？
		        JDBCFieldArray whereCols1 = new JDBCFieldArray();
		        whereCols1.add("PackageID", readyPack.PackageID);
		        whereCols1.add("CreateTime", readyPack.CreateTime);

		        historyPackDAO.delete(whereCols1);
		        
		        //插入历史记录
		        PTDeliverHistory historyPack = new PTDeliverHistory();
		        historyPack.PackageID = readyPack.PackageID;
		        historyPack.CreateTime = readyPack.CreateTime;
		        historyPack.ItemStatus = nextStatus;
	        	historyPack.RunStatus  = SysDict.ITEM_RUN_STATUS_RUNNING;
	        	historyPack.PPCID  = readyPack.PPCID;
	            historyPack.ZoneID = readyPack.ZoneID;
	            historyPack.CompanyID = readyPack.CompanyID;
	            historyPack.RefNo = readyPack.RefNo;
	            historyPack.PrintedFlag = readyPack.PrintedFlag;
	            historyPack.ReportOrderID = "";
	            historyPack.ParcelSize = readyPack.ParcelSize;
	            historyPack.CustomerID = readyPack.CustomerID;
	            historyPack.CustomerMobile = readyPack.CustomerMobile;
	            historyPack.CustomerName = readyPack.CustomerName;
	            historyPack.CustomerAddress = readyPack.CustomerAddress;
	            historyPack.TerminalNo = readyPack.TerminalNo;
	            historyPack.BoxNo      = readyPack.BoxNo;
	            historyPack.DropAgentID = readyPack.DropAgentID;
	            historyPack.ReturnAgentID = readyPack.DropAgentID;
	            historyPack.DynamicCode = "";
	            historyPack.DADFlag   = "0";
	            historyPack.TradeWaterNo = "";
	            historyPack.DropNum = readyPack.DropNum;
	            historyPack.LastModifyTime = sysDateTime;
	            historyPack.Remark = readyPack.Remark;
		        
		        historyPackDAO.insert(historyPack);

		        readyPackDAO.delete(readyPack);
		        //#end
				
		        //记录订单信息，用于打印
    	        historyPackList.add(historyPack);
		        
    	        //////
		        postmanName = addItemLifeCycleAndStatusUpdate(postmanName, postmanid, zone, operOnline,readyPack ,historyPack,nextStatus, sysDateTime);
		        
	    	}
			break;
		case SysDict.ITEM_STATUS_DROP_INTRANSIT_BACK:
			//#start 订单信息检查，筛选符合条件的订单 ：订单状态为:Intransit-back
			String nowStatus = SysDict.ITEM_STATUS_DROP_INTRANSIT_BACK;
			for(String[] itemCodeAndTime:itemsCodeAndTime){
				String itemCode   = itemCodeAndTime[0];
				String createtime = itemCodeAndTime[1];
				if(StringUtils.isEmpty(itemCode)|| StringUtils.isEmpty(createtime)){
					continue;
				}
	        	PTDeliverHistory historyPack = new PTDeliverHistory();
	        	historyPack.PackageID = itemCode;
	        	historyPack.CreateTime = java.sql.Timestamp.valueOf(createtime);
	        	try{
	        		historyPack = historyPackDAO.find(historyPack);
				}catch(EduException e){
					continue;
				}
				
	        	//检查TerminalNo
	        	if(StringUtils.isEmpty(in.TerminalNo)){
	        		in.TerminalNo = historyPack.TerminalNo;
	        	}
	        	if(!in.TerminalNo.equals(historyPack.TerminalNo)){
	        		throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTILOCKER);
	        	}
	        	
				//状态检查 
				if(!in.ItemStatus.equals(historyPack.ItemStatus)){
					throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
	  				//continue;
	        	}
		        
				if(StringUtils.isEmpty(postmanid)){
	        		postmanid = historyPack.ReturnAgentID;
	        	}
	        	//同一postman的订单
	        	if(!postmanid.equals(historyPack.ReturnAgentID)){
	        		throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTIPOSTMAN);
	  				//continue;
	        	}
	        	
		        //记录订单信息，用于打印
    	        historyPackList.add(historyPack);
    	        
		        //#start 更新订单状态
		        JDBCFieldArray setCols = new JDBCFieldArray();
		        JDBCFieldArray whereCols = new JDBCFieldArray();
		        setCols.add("ReportOrderID", "");
		        setCols.add("ItemStatus", nextStatus);
		        setCols.add("LastModifyTime", sysDateTime);
		        
		        whereCols.add("PackageID", historyPack.PackageID);
		        whereCols.add("CreateTime", historyPack.CreateTime);
		        whereCols.add("ItemStatus", nowStatus);
		        int count = historyPackDAO.update(setCols, whereCols);
		        if(count<=0){
    	        	continue;
    	        }
		        //#end
		        
		        //////
		        postmanName = addItemLifeCycleAndStatusUpdate(postmanName, postmanid, zone, operOnline, historyPack,nextStatus, sysDateTime);
		        
	    	}
			break;
		default:
			throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
		}

        //检查是否需要打印：
		boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, SysDict.ITEM_STATUS_DROP_INTRANSIT_OUT, 
				zone.MandatoryFlag, SysDict.MANDATORY_REPORT3_CODE, ErrorCode.ERR_MANDATORY_REPORT3_PRINT);*/
		
		String documentID = "";
        //获取报表数据
        if(historyPackList.size()>0 && isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_RETURN, in.TerminalNo, operOnline.ZoneID);
            
        	//#start  获取打印报表数据列表
        	//PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
            Iterator<PTDeliverHistory> historyItemIre = historyPackList.iterator();
	    	while(historyItemIre.hasNext()){
	    		PTDeliverHistory historyPack = historyItemIre.next();
      		              	
    	        //更新订单打印报表流水号
            	JDBCFieldArray whereCols = new JDBCFieldArray();
            	JDBCFieldArray setCols = new JDBCFieldArray();
            	
            	setCols.add("ReportOrderID", documentID);
		        setCols.add("LastModifyTime", sysDateTime);
		        
		        whereCols.add("PackageID", historyPack.PackageID);
		        whereCols.add("CreateTime", historyPack.CreateTime);
		        
		        historyPackDAO.update(setCols, whereCols);
            	
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
        }
    	
        return documentID;
    }
    
    private String addItemLifeCycleAndStatusUpdate(String postmanName, String postmanid, 
    		IMZone zone,  OPOperOnline operOnline, PTReadyPackage readyPack,PTDeliverHistory historyPack, 
    		String nextStatus, java.sql.Timestamp sysDateTime) throws EduException{
    	
    	if(StringUtils.isEmpty(postmanName)){
            PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
            PMPostman postman = new PMPostman();
            postman.PostmanID = postmanid;
            try{
                postman = postmanDAO.find(postman);
                postmanName = postman.PostmanName;
            }catch(EduException e){
                postmanName = postmanid;
            }
        }
        
        //"Return("+postmanid+"-"+postmanName+"=>"+zone.ZoneName+")"
        String[] itemDesc= {postmanid, postmanName, zone.ZoneName};
        
		//#start 记录订单状态更新信息
		PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID  = historyPack.PackageID;
        itemLifeCycle.ItemStatus = nextStatus;
        itemLifeCycle.OperatorID    = operOnline.OperID;
        itemLifeCycle.OperatorType = ""+operOnline.OperType;
        itemLifeCycle.LastModifyTime = sysDateTime;
        itemLifeCycle.ZoneID = readyPack.ZoneID;
        itemLifeCycle.LastStatus = readyPack.ItemStatus;
        if(readyPack.LastModifyTime == null){
        	readyPack.LastModifyTime = sysDateTime;
        }
        itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_RETURN, itemLifeCycle, itemDesc);
        //#end 
        
        //#start 订单数据加入发送队列进行发送(状态更新)
    	commonDAO.addItemStatusUpdate(historyPack.PPCID, nextStatus, historyPack, "");
        //#end
    	return postmanName;
    }
    private String addItemLifeCycleAndStatusUpdate(String postmanName, String postmanid, 
    		IMZone zone,  OPOperOnline operOnline, PTDeliverHistory historyPack, 
    		String nextStatus, java.sql.Timestamp sysDateTime) throws EduException{
    	
    	if(StringUtils.isEmpty(postmanName)){
            PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
            PMPostman postman = new PMPostman();
            postman.PostmanID = postmanid;
            try{
                postman = postmanDAO.find(postman);
                postmanName = postman.PostmanName;
            }catch(EduException e){
                postmanName = postmanid;
            }
        }
        
        //"Return("+postmanid+"-"+postmanName+"=>"+zone.ZoneName+")"
        String[] itemDesc= {postmanid, postmanName, zone.ZoneName};
        
		//#start 记录订单状态更新信息
		PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID  = historyPack.PackageID;
        itemLifeCycle.ItemStatus = nextStatus;
        itemLifeCycle.OperatorID    = operOnline.OperID;
        itemLifeCycle.OperatorType = ""+operOnline.OperType;
        itemLifeCycle.LastModifyTime = sysDateTime;
        itemLifeCycle.ZoneID = historyPack.ZoneID;
        itemLifeCycle.LastStatus = historyPack.ItemStatus;
        itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - historyPack.LastModifyTime.getTime()) / 60000L));
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_RETURN, itemLifeCycle, itemDesc);
        //#end 
        
        //#start 订单数据加入发送队列进行发送(状态更新)
    	commonDAO.addItemStatusUpdate(historyPack.PPCID, nextStatus, historyPack, "");
        //#end
    	return postmanName;
    }
}
