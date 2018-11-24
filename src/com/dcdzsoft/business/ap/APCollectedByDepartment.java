package com.dcdzsoft.business.ap;

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
 * <p>Description: 揽件部门回收寄件包裹 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APCollectedByDepartment extends ActionBean
{

    public int doBusiness(InParamAPCollectedByDepartment in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.Password))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//
		OPOperatorDAO operDAO = daoFactory.getOPOperatorDAO();
        OPOperator oper = new OPOperator();
        oper.OperID = in.OperID;

        try
        {
        	oper = operDAO.find(oper);
        }catch(EduException e)
        {
        	JDBCFieldArray whereCols = new JDBCFieldArray();
        	whereCols.add("UserID", in.OperID);
        	RowSet rset1 = operDAO.select(whereCols);
        	if(RowSetUtils.rowsetNext(rset1))
        	{
        		oper.OperID = RowSetUtils.getStringValue(rset1, "OperID");
        		oper = operDAO.find(oper);
        	}else{
        		throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
        	}
        }
        //检查密码
        if (oper.OperStatus.compareTo("0") != 0)
            throw new EduException(ErrorCode.ERR_OPERSTATUSABNORMAL);
        
        if (oper.OperPassword.compareTo(in.Password) != 0)
            throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
        
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = oper.ZoneID;
        zone = zoneDAO.find(zone);
        
        //设置允许操作的订单状态
        String ItemStatus = SysDict.ITEM_STATUS_COLLECT_TOBECOLLECTED+","//51
    			+ SysDict.ITEM_STATUS_COLLECT_INTRANSIT_COLLECTED+","//53
    			+ SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED+","//54
    			+ SysDict.ITEM_STATUS_COLLECT_CREATED//50
    			; 
        //检查订单号
        String viewName = "V_DMAllDeliveryItems";
        String limitSQL = "";
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("PackageID", in.PackageID);
        if(StringUtils.isNotEmpty(ItemStatus)){
        	whereSQL.addSQL(utilDAO.getFlagInSQL("a.ItemStatus", ItemStatus));
        }
        String sql = "SELECT * FROM " + viewName + " a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
        RowSet rset = dbSession.executeQuery(sql,whereSQL);
        if(RowSetUtils.rowsetNext(rset)){
        	DMHistoryItemDAO historyItemDAO = daoFactory.getDMHistoryItemDAO();
        	String itemCode= RowSetUtils.getStringValue(rset, "PackageID");
        	java.sql.Timestamp CreateTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
        	String itemstatus = RowSetUtils.getStringValue(rset, "ItemStatus");
        	switch(itemstatus){
        	case SysDict.ITEM_STATUS_COLLECT_CREATED:
        	case SysDict.ITEM_STATUS_COLLECT_INTRANSIT_COLLECTED:
        	case SysDict.ITEM_STATUS_COLLECT_TOBECOLLECTED:
        		DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
        		DMCollectionParcel collectionParcel = new DMCollectionParcel();
    			collectionParcel.PackageID = itemCode;
    			collectionParcel.CreateTime = CreateTime;
    			collectionParcel = collectionParcelDAO.find(collectionParcel);
    			
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
    	        historyItem.ItemStatus = SysDict.ITEM_STATUS_COLLECT_DELIVERED;
    	        historyItem.ServiceID = collectionParcel.ServiceID;
    	        historyItem.ServiceAmt = collectionParcel.ServiceAmt;
    	        historyItem.CollectionFlag = collectionParcel.CollectionFlag;
    	        historyItem.CollectionAmt = collectionParcel.CollectionAmt;
    	        historyItem.ReturnFlag = collectionParcel.ReturnFlag;
    	        historyItem.ZoneID    = zone.ZoneID;
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
    	        
    	        //生成消费记录
        		commonDAO.doBusinessPartnerPay(collectionParcel.TradeWaterNo, collectionParcel.BPartnerID, collectionParcel.PackageID);
        		break;
        	case SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED:
        		JDBCFieldArray setCols = new JDBCFieldArray();
    	        JDBCFieldArray whereCols = new JDBCFieldArray();
    	        setCols.add("ZoneID", zone.ZoneID);
    	        setCols.add("CompanyID", zone.CompanyID);
        		setCols.add("ItemStatus", SysDict.ITEM_STATUS_COLLECT_DELIVERED);
    	        setCols.add("LastModifyTime", sysDateTime);
    	        
    	        whereCols.add("PackageID", itemCode);
    	        whereCols.add("CreateTime", CreateTime);
    	        whereCols.add("ItemStatus", SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED);
    	        historyItemDAO.update(setCols, whereCols);
        		break;
        	
        	}
        	
	        //#start 添加投递订单周期记录
            DMItemLifeCycle itemLifeCycle = new DMItemLifeCycle();
            itemLifeCycle.PackageID  = itemCode;
            itemLifeCycle.ItemStatus = SysDict.ITEM_STATUS_COLLECT_DELIVERED;
            itemLifeCycle.OperatorID     = oper.OperID;
            itemLifeCycle.OperatorType   = ""+oper.OperType;
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.Remark = "CollectionAPP";
            commonDAO.addItemLifeCycle(itemLifeCycle);
            //#end 
        }else{
        	throw new EduException(ErrorCode.ERR_COLLECT_ITEMCODE_EXPIRED);
        }
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "CollectionAPP";

		commonDAO.addOperatorLog(log);
        return result;
    }
}
