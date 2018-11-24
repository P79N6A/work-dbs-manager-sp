package com.dcdzsoft.business.dm;

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
 * <p>Description: 分拣中心确认收到包裹 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryReceived extends ActionBean
{

    public String doBusiness(InParamDMDeliveryReceived in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.ItemStatus))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);
        
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
        
		//4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID); 
        
        String postmanid = "";
        
        //#start 订单信息检查，筛选符合条件的订单 ;
        String nextStatus = SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED;
        DMHistoryItemDAO historyItemDAO = daoFactory.getDMHistoryItemDAO();
        
		DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
		java.util.List<DMHistoryItem> historyPackList = new java.util.LinkedList<DMHistoryItem>();
		for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode   = itemCodeAndTime[0];
			String createtime = itemCodeAndTime[1];
			if(StringUtils.isEmpty(itemCode)|| StringUtils.isEmpty(createtime)){
				continue;
			}
			DMCollectionParcel collectionParcel = new DMCollectionParcel();
			collectionParcel.PackageID = itemCode;
			collectionParcel.CreateTime = java.sql.Timestamp.valueOf(createtime);
        	try{
        		collectionParcel = collectionParcelDAO.find(collectionParcel);
			}catch(EduException e){
				continue;
			}
        	
        	//要求同一CollectionAgent的订单
        	if(StringUtils.isNotEmpty(collectionParcel.CollectionAgentID)){
        	    if(StringUtils.isEmpty(postmanid)){
                    postmanid = collectionParcel.CollectionAgentID;
                }
        	    
                if(!postmanid.equals(collectionParcel.CollectionAgentID)){
                    throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTIPOSTMAN);
                }
        	}
			//状态检查 
			if(!SysDict.ITEM_STATUS_COLLECT_INTRANSIT_COLLECTED.equals(collectionParcel.ItemStatus)
				&& !SysDict.ITEM_STATUS_COLLECT_CREATED.equals(collectionParcel.ItemStatus)
				&& !SysDict.ITEM_STATUS_COLLECT_TOBECOLLECTED.equals(collectionParcel.ItemStatus)){
				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
        	}
			
			//#start 先删除，保证数据不重复
	        JDBCFieldArray whereCols1 = new JDBCFieldArray();
	        whereCols1.add("PackageID", collectionParcel.PackageID);
	        whereCols1.add("CreateTime", collectionParcel.CreateTime);
	        
	        historyItemDAO.delete(whereCols1);
	        //#end
	        
	        //#start 插入历史记录
	        DMHistoryItem historyItem = new DMHistoryItem();
	        historyItem.CreateTime = collectionParcel.CreateTime;
	        historyItem.PackageID  = collectionParcel.PackageID;
	        historyItem.BPartnerID = collectionParcel.BPartnerID;
	        historyItem.ItemStatus = nextStatus;//collectionParcel.ItemStatus
	        historyItem.ServiceID = collectionParcel.ServiceID;
	        historyItem.ServiceAmt = collectionParcel.ServiceAmt;
	        historyItem.CollectionFlag = collectionParcel.CollectionFlag;
	        historyItem.CollectionAmt = collectionParcel.CollectionAmt;
	        historyItem.ReturnFlag = collectionParcel.ReturnFlag;
	        historyItem.ZoneID = zone.ZoneID;
	        historyItem.CompanyID = zone.CompanyID;
	        historyItem.TradeWaterNo = collectionParcel.TradeWaterNo;
	        historyItem.CustomerAddress = collectionParcel.CustomerAddress;
	        historyItem.CustomerName = collectionParcel.CustomerName;
	        historyItem.CustomerMobile = collectionParcel.CustomerMobile;
	        historyItem.CustomerID = collectionParcel.CustomerID;
	        historyItem.TerminalNo = collectionParcel.TerminalNo;
	        historyItem.BoxNo = collectionParcel.BoxNo;
	        historyItem.ParcelSize = collectionParcel.ParcelSize;
	        historyItem.CollectZoneID = collectionParcel.CollectZoneID;
	        historyItem.CollectionType = collectionParcel.CollectionType;
	        historyItem.CollectionAgentID = collectionParcel.CollectionAgentID;
	        historyItem.CollectionType = collectionParcel.CollectionType;
	        historyItem.CollectionTime = collectionParcel.CollectionTime;
	        historyItem.PPCID = collectionParcel.PPCID;
	        historyItem.PrintedFlag = collectionParcel.PrintedFlag;
	        historyItem.ReportOrderID = "";
	        historyItem.LastModifyTime = sysDateTime;
	        
	        historyItemDAO.insert(historyItem);
	        
	        collectionParcelDAO.delete(collectionParcel);
	        //#end
	        
    		//记录订单信息，进行打印
    		historyPackList.add(historyItem);
	        
	        //#start 记录订单状态更新信息
	        //调用CommonDAO. addItemLifeCycle(DMItemLifeCycle itemLifeCycle)添加投递订单周期记录。
	        DMItemLifeCycle itemLifeCycle = new DMItemLifeCycle();
	        itemLifeCycle.PackageID  = historyItem.PackageID;
	        itemLifeCycle.ItemStatus = nextStatus;
	        itemLifeCycle.OperatorID     = operOnline.OperID;
	        itemLifeCycle.OperatorType   = ""+operOnline.OperType;
	        itemLifeCycle.LastModifyTime = sysDateTime;
	        itemLifeCycle.Remark = "Inward Received";
	        commonDAO.addItemLifeCycle(itemLifeCycle);
            //#end 
	        
	        //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(historyItem.PPCID, nextStatus, historyItem, SysDict.TRANSFER_TYPE_SUBMIT_ITEM);
	        //#end
			
	        //生成消费记录
    		commonDAO.doBusinessPartnerPay(collectionParcel.TradeWaterNo, collectionParcel.BPartnerID, collectionParcel.PackageID);
    		
    	}
		//#end
        
        //检查是否需要打印：
		boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, "", 
				zone.MandatoryFlag, SysDict.MANDATORY_REPORT5_CODE, ErrorCode.ERR_MANDATORY_REPORT5_PRINT);*/
		

		String documentID = "";
    	//获取报表数据
        if(historyPackList.size()>0 && isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_COLLECTION, operOnline.ZoneID, operOnline.ZoneID);
        	
        	//#start  获取打印报表数据列表
        	Iterator<DMHistoryItem> hisortyPackListIre = historyPackList.iterator();
        	while(hisortyPackListIre.hasNext()){
        		DMHistoryItem historyPack = hisortyPackListIre.next();

            	//更新订单打印报表流水号
            	JDBCFieldArray whereCols = new JDBCFieldArray();
            	JDBCFieldArray setCols = new JDBCFieldArray();
            	setCols.add("ReportOrderID", documentID);
            	setCols.add("LastModifyTime", sysDateTime);
            	
            	whereCols.add("PackageID", historyPack.PackageID);
    	        whereCols.add("CreateTime", historyPack.CreateTime);
    	        historyItemDAO.update(setCols, whereCols);
            	
            	//#start  调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)        	
    			OPOperatorLog log = new OPOperatorLog();
    			log.OperID = in.OperID;
    			log.FunctionID = in.getFunctionID();
    			log.OccurTime = sysDateTime;
    			log.StationAddr = operOnline.LoginIPAddr;
    			log.Remark = "itemcode="+historyPack.PackageID+",reportid="+documentID;

    			commonDAO.addOperatorLog(log);
    			//#end
        	}
        	//#end 
        }

        return documentID;
    }
}
