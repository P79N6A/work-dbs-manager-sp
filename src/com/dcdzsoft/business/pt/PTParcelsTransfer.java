package com.dcdzsoft.business.pt;

import java.io.File;
import java.util.Iterator;

import javax.sql.RowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dcdzsoft.EduException;
import com.dcdzsoft.ppcapi.SendInfo;
import com.dcdzsoft.ppcapi.SendItemManager;
import com.dcdzsoft.print.ItemBean;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sequence.SequenceGenerator;
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
 * <p>Description: 订单数据发往包裹处理中心服务器 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTParcelsTransfer extends ActionBean
{

    public String doBusiness(InParamPTParcelsTransfer in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.PPCID)
			||StringUtils.isEmpty(in.ItemStatus))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//AZC
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = operOnline.ZoneID;
		zone = zoneDAO.find(zone);
		
		//PPC
		IMPostalProcessingCenterDAO ppcDAO = daoFactory.getIMPostalProcessingCenterDAO();
		IMPostalProcessingCenter  ppc = new IMPostalProcessingCenter();
		ppc.PPCID = in.PPCID;
		ppc  = ppcDAO.find(ppc);
		
		//4.	解析订单号。
		String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
		
		//5.	更新订单状态
		PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
		java.util.List<PTDeliverHistory> historyPackList = new java.util.LinkedList<PTDeliverHistory>();
    	PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
        switch(in.ItemStatus){
        case SysDict.ITEM_STATUS_DROP_LISTED:
        case SysDict.ITEM_STATUS_DROP_RECEIVED:
        	String nextStatus = SysDict.ITEM_STATUS_DROP_TRANSFER;
        	String nowStatus  = in.ItemStatus;

    		//#start 更新订单状态
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
    			
    			//Listed 状态？？？？
    			if(!SysDict.ITEM_STATUS_DROP_LISTED.equalsIgnoreCase(readyPack.ItemStatus)
    				&&	!SysDict.ITEM_STATUS_DROP_RECEIVED.equalsIgnoreCase(readyPack.ItemStatus)){
    				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
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
	        	historyPack.RunStatus  = SysDict.ITEM_RUN_STATUS_TRANSFER_ERR_INFO;
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
	            historyPack.ReturnAgentID = "";
	            historyPack.DynamicCode = "";
	            historyPack.DADFlag     = "0";
	            historyPack.TradeWaterNo = "";
	            historyPack.DropNum = readyPack.DropNum;
	            historyPack.LastModifyTime = sysDateTime;
	            historyPack.Remark = readyPack.Remark;
		        
		        historyPackDAO.insert(historyPack);
	      
		        readyPackDAO.delete(readyPack);
		        //#end 
    	        
		        //记录订单信息，用于打印
		        historyPackList.add(historyPack);
		        
		        //
		        addItemLifeCycleAndStatusUpdate(  ppc,  zone, operOnline, readyPack, historyPack, nextStatus, nowStatus,  sysDateTime);

            }
    		//#end
        	break;
        case SysDict.ITEM_STATUS_DROP_RETURNED:
        	nowStatus = SysDict.ITEM_STATUS_DROP_RETURNED;
        	nextStatus = SysDict.ITEM_STATUS_DROP_TRANSFER;
        	
    		//#start 更新订单状态
        	for(String[] itemCodeAndTime:itemsCodeAndTime){
    			String itemCode = itemCodeAndTime[0];
    			String createtime = itemCodeAndTime[1];
    			
    			if(StringUtils.isEmpty(itemCode) || StringUtils.isEmpty(createtime)){
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
	        	
				//状态检查 
				if(!in.ItemStatus.equals(historyPack.ItemStatus)){
					throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
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
		        setCols.add("RunStatus", SysDict.ITEM_RUN_STATUS_TRANSFER_EXPIRED);
		        
		        whereCols.add("PackageID", historyPack.PackageID);
		        whereCols.add("CreateTime", historyPack.CreateTime);
		        whereCols.add("ItemStatus", nowStatus);
		        int count =historyPackDAO.update(setCols, whereCols);
		        if(count<=0){
    	        	continue;
    	        }
		        //#end 
		        
	            ///////
		        addItemLifeCycleAndStatusUpdate(  ppc,  zone, operOnline,  historyPack, nextStatus, nowStatus,  sysDateTime);
            }
        	//#end
        	
        	break;
        }
        
		//检查是否需要打印：
		boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, nowStatus, 
            		zone.MandatoryFlag, SysDict.MANDATORY_REPORT4_CODE,ErrorCode.ERR_MANDATORY_REPORT4_PRINT);*/
		
		String documentID = "";
        //获取报表数据
        if(historyPackList.size()>0 && isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_TRANSFER, operOnline.ZoneID, ppc.PPCID);
        	
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

    private String addItemLifeCycleAndStatusUpdate(IMPostalProcessingCenter  ppc, IMZone zone,OPOperOnline operOnline, PTReadyPackage readyPack,PTDeliverHistory historyPack, 
    		String nextStatus, String nowStatus, java.sql.Timestamp sysDateTime) throws EduException{
    	
    	//"Transfer("+ppc.PPCName+"=>"+zone.ZoneName+")"
        String[] itemDesc= {ppc.PPCName,zone.ZoneName};
        
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
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_TRANSFER, itemLifeCycle, itemDesc);
        //#end 
        
        //#start 订单数据加入发送队列进行发送
    	String transferType = "";
    	if(nowStatus.equals(SysDict.ITEM_STATUS_DROP_MISSING)){
    		transferType = SysDict.TRANSFER_TYPE_MISSING;
        }else if(nowStatus.equals(SysDict.ITEM_STATUS_DROP_RETURNED)){
            if(StringUtils.isNotEmpty(historyPack.Remark) && historyPack.Remark.contains("M_expire")){
            	transferType = SysDict.TRANSFER_TYPE_M_EXPIRED;
            }else{
            	transferType = SysDict.TRANSFER_TYPE_EXPIRED;
            }
        }else if(nowStatus.equals(SysDict.ITEM_STATUS_DROP_LISTED)){
        	transferType = SysDict.TRANSFER_TYPE_PARCEL_NOT_RECEIVED;
        }else if(nowStatus.equals(SysDict.ITEM_STATUS_DROP_RECEIVED)){
        	transferType = SysDict.TRANSFER_TYPE_ERROR_INFO;
        }
    	
    	commonDAO.addItemStatusUpdate(ppc.PPCID, nextStatus, historyPack,  transferType);
        //#end
    	return "";
    }
    private String addItemLifeCycleAndStatusUpdate(IMPostalProcessingCenter  ppc, IMZone zone,OPOperOnline operOnline, PTDeliverHistory historyPack, 
    		String nextStatus, String nowStatus, java.sql.Timestamp sysDateTime) throws EduException{
    	
    	//"Transfer("+ppc.PPCName+"=>"+zone.ZoneName+")"
        String[] itemDesc= {ppc.PPCName,zone.ZoneName};
        
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
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_TRANSFER, itemLifeCycle, itemDesc);
        //#end 
        
        //#start 订单数据加入发送队列进行发送
    	String transferType = "";
    	if(nowStatus.equals(SysDict.ITEM_STATUS_DROP_MISSING)){
    		transferType = SysDict.TRANSFER_TYPE_MISSING;
        }else if(nowStatus.equals(SysDict.ITEM_STATUS_DROP_RETURNED)){
            if(StringUtils.isNotEmpty(historyPack.Remark) && historyPack.Remark.contains("M_expire")){
            	transferType = SysDict.TRANSFER_TYPE_M_EXPIRED;
            }else{
            	transferType = SysDict.TRANSFER_TYPE_EXPIRED;
            }
        }else if(nowStatus.equals(SysDict.ITEM_STATUS_DROP_LISTED)){
        	transferType = SysDict.TRANSFER_TYPE_PARCEL_NOT_RECEIVED;
        }else if(nowStatus.equals(SysDict.ITEM_STATUS_DROP_RECEIVED)){
        	transferType = SysDict.TRANSFER_TYPE_ERROR_INFO;
        }
    	
    	commonDAO.addItemStatusUpdate(ppc.PPCID, nextStatus, historyPack,  transferType);
        //#end
    	return "";
    }
}
