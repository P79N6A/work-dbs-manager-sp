package com.dcdzsoft.business.dm;

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
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 取消寄件订单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryCancel extends ActionBean
{

    public int doBusiness(InParamDMDeliveryCancel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (//StringUtils.isEmpty(in.OperID)
			StringUtils.isEmpty(in.BPartnerID)
			||StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//////商业合作伙伴检查
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		IMBusinessPartner businessPartner = new IMBusinessPartner();
	    businessPartner.BPartnerID = in.BPartnerID;
	    businessPartner = businessPartnerDAO.find(businessPartner);
	    
	    DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("PackageID", in.PackageID);
        RowSet rset = collectionParcelDAO.select(whereCols);
        if(RowSetUtils.rowsetNext(rset)){
        	String itemstatus = RowSetUtils.getStringValue(rset, "ItemStatus");
            java.sql.Timestamp createTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
            //System.out.println("itemcode="+in.PackageID+",itemstatus="+itemstatus+",createTime="+createTime);
            if(createTime==null){
            	throw new EduException(ErrorCode.ERR_DMCOLLECTIONPARCELNODATA);
            }
        	switch(itemstatus){
        	case SysDict.ITEM_STATUS_COLLECT_CREATED://create 
        	case SysDict.ITEM_STATUS_COLLECT_TOBECOLLECTED:
        		String nextStatus = SysDict.ITEM_STATUS_COLLECT_CANCELLED;
        		DMCollectionParcel collectionParcel = new DMCollectionParcel();
        		collectionParcel.PackageID = in.PackageID;
        		collectionParcel.CreateTime = createTime;
        		collectionParcel = collectionParcelDAO.find(collectionParcel);
        		
        		DMHistoryItemDAO historyItemDAO = daoFactory.getDMHistoryItemDAO();
        		//#start 先删除，保证数据不重复
        		JDBCFieldArray whereCols3 = new JDBCFieldArray();
 		        whereCols3.add("PackageID", collectionParcel.PackageID);
 		        whereCols3.add("CreateTime", collectionParcel.CreateTime);
 		        
 		        historyItemDAO.delete(whereCols3);
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
		        historyItem.ZoneID = collectionParcel.ZoneID;
		        historyItem.CompanyID = collectionParcel.CompanyID;
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
		        
		        //#start 记录订单状态更新信息
		        DMItemLifeCycle itemLifeCycle = new DMItemLifeCycle();
	            itemLifeCycle.PackageID  = historyItem.PackageID;
	            itemLifeCycle.ItemStatus = nextStatus;
	            itemLifeCycle.OperatorID    = in.BPartnerID;
	            itemLifeCycle.OperatorType  = SysDict.OPER_TYPE_BPARTNER;
	            itemLifeCycle.LastModifyTime = sysDateTime;
	            itemLifeCycle.Remark = "Cancel";
	            commonDAO.addItemLifeCycle(itemLifeCycle);
 	            //#end 
	            
	            commonDAO.doBusinessPartnerRefund(businessPartner, collectionParcel.TradeWaterNo, collectionParcel.PackageID);
        		break;
        	default :
        		throw new EduException(ErrorCode.ERR_COLLECT_FORBID_CANCEL);
        	}
        }else{
        	throw new EduException(ErrorCode.ERR_DMCOLLECTIONPARCELNODATA);
        }
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.BPartnerID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "";

		commonDAO.addOperatorLog(log);

        return result;
    }
}
