package com.dcdzsoft.business.dm;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

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
 * <p>Description: 寄件订单查询 (eLocker web Portal)</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryItemsQry extends ActionBean
{

    public java.util.List<OutParamDMDeliveryItemsQry> doBusiness(InParamDMDeliveryItemsQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        java.util.List<OutParamDMDeliveryItemsQry> outList = new java.util.LinkedList<OutParamDMDeliveryItemsQry>();

        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		//System.out.println(in.BPartnerID);
		//
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		
		java.sql.Date date =null;
        if(StringUtils.isNotEmpty(in.BeginDate)){//
        	date = commonDAO.strToDate(in.BeginDate);
        	if(date!=null){
        		whereSQL.checkAdd("CreateTime", ">=", new java.sql.Timestamp(date.getTime()));
        	}
        }
        if(StringUtils.isNotEmpty(in.EndDate)){
        	date = commonDAO.strToDate(in.EndDate);
        	if(date!=null){
        		whereSQL.checkAdd("CreateTime", "<=", DateUtils.addTimeStamp(new java.sql.Timestamp(date.getTime()), 1));//
        	}
        }
        	
        whereSQL.checkAdd("PackageID", in.PackageID);
        /*if(StringUtils.isNotEmpty(in.PackageID)){//模糊查询
        	whereSQL.addSQL(" AND (PackageID ");
        	whereSQL.addSQL(" LIKE '"+in.PackageID+"%'");
        	whereSQL.addSQL(" )");
        }*/
        if(StringUtils.isNotEmpty(in.CustomerName)){//模糊查询
        	whereSQL.addSQL(" AND (CustomerName ");
        	whereSQL.addSQL(" LIKE '"+in.CustomerName+"%'");
        	whereSQL.addSQL(" )");
        }
        whereSQL.add("BPartnerID", in.BPartnerID);
        whereSQL.checkAdd("CustomerMobile", in.CustomerMobile);
        if(StringUtils.isNotEmpty(in.ItemStatus)){
        	whereSQL.addSQL(utilDAO.getFlagInSQL("a.ItemStatus", in.ItemStatus));
        }
        
        String viewName = "V_DMAllDeliveryItems";
        String limitSQL = "";//commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
        
		String sql = "SELECT * FROM " + viewName + " a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
        
		java.util.List<String> orderByField = new java.util.LinkedList<String>();
        orderByField.add("CreateTime DESC");

        rset = dbSession.executeQuery(sql,orderByField,in.getRecordBegin(),in.getRecordNum(),whereSQL);
        
        
        
        while (RowSetUtils.rowsetNext(rset)) {
        	OutParamDMDeliveryItemsQry out = new OutParamDMDeliveryItemsQry();
        	
        	String packageID = RowSetUtils.getStringValue(rset, "PackageID");
        	out.setPackageID(packageID);
        	
        	java.sql.Timestamp createTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
        	String dateTimeString = Constant.dateFromat.format(createTime);
        	out.setCreateTime(dateTimeString);
        	
        	//#start V_PTItemStatus 在投递订单中查询最新的订单状态
        	String itemstatus = RowSetUtils.getStringValue(rset, "ItemStatus");
        	String itemStatusName = RowSetUtils.getStringValue(rset, "ItemStatusName");
        	switch(itemstatus){
        	case SysDict.ITEM_STATUS_COLLECT_TRANSFER:
        	case SysDict.ITEM_STATUS_COLLECT_TOBE_D_DEOPPED:
        	case SysDict.ITEM_STATUS_COLLECT_TOBE_DEOPPED:
        		//在投递订单中查询最新的订单状态
        		PreparedWhereExpression whereSQL2 = new PreparedWhereExpression();
        		whereSQL2.add("CreateTime", ">=", createTime);
        		whereSQL2.add("PackageID", packageID);
        		String sql2 = "SELECT * FROM V_PTItemStatus  a WHERE 1=1 " + whereSQL2.getPreparedWhereSQL() ;
                
        		java.util.List<String> orderByField2 = new java.util.LinkedList<String>();
                orderByField2.add("CreateTime DESC");

                RowSet rset2 = dbSession.executeQuery(sql2,orderByField2,0,0,whereSQL2);
                if(RowSetUtils.rowsetNext(rset2)){//取最新的状态
                	itemstatus = RowSetUtils.getStringValue(rset2, "ItemStatus");
                	itemStatusName = RowSetUtils.getStringValue(rset2, "ItemStatusName");
                }
        		break;
        	}
        	out.setItemStatus(itemstatus);
        	out.setItemStatusName(itemStatusName);
        	//#end
        	
        	out.setBPartnerID(RowSetUtils.getStringValue(rset, "BPartnerID"));
        	out.setBPartnerName(RowSetUtils.getStringValue(rset, "BPartnerName"));
        	out.setAddress(RowSetUtils.getStringValue(rset, "Address"));
        	out.setMobile(RowSetUtils.getStringValue(rset, "Mobile"));
        	
        	out.setServiceID(RowSetUtils.getStringValue(rset, "ServiceID"));
        	out.setServiceName(RowSetUtils.getStringValue(rset, "ServiceName"));
        	out.setParcelSize(RowSetUtils.getStringValue(rset, "ParcelSize"));
        	out.setParcelSizeName(RowSetUtils.getStringValue(rset, "ParcelSizeName"));
        	out.setServiceAmt(RowSetUtils.getDoubleValue(rset, "ServiceAmt"));
        	out.setCollectionAmt(RowSetUtils.getDoubleValue(rset, "CollectionAmt"));
        	out.setTradeWaterNo(RowSetUtils.getStringValue(rset, "TradeWaterNo"));
        	out.setCustomerID(RowSetUtils.getStringValue(rset, "CustomerID"));
        	out.setCustomerName(RowSetUtils.getStringValue(rset, "CustomerName"));
        	out.setCustomerAddress(RowSetUtils.getStringValue(rset, "CustomerAddress"));
        	out.setCustomerMobile(RowSetUtils.getStringValue(rset, "CustomerMobile"));
        	out.setTerminalNo(RowSetUtils.getStringValue(rset, "TerminalNo"));
        	out.setTerminalName(RowSetUtils.getStringValue(rset, "TerminalName"));
        	out.setZoneID(RowSetUtils.getStringValue(rset, "ZoneID"));
        	out.setZoneName(RowSetUtils.getStringValue(rset, "ZoneName"));
        	out.setCollectZoneID(RowSetUtils.getStringValue(rset, "CollectZoneID"));
        	out.setCollectZoneName(RowSetUtils.getStringValue(rset, "CollectZoneName"));
        	out.setPostmanID(RowSetUtils.getStringValue(rset, "PostmanID"));
        	out.setPostmanName(RowSetUtils.getStringValue(rset, "PostmanName"));
        	
        	java.sql.Timestamp CollectionTime = RowSetUtils.getTimestampValue(rset, "CollectionTime");
        	String CollectionTimeString = Constant.dateFromat.format(CollectionTime);
        	out.setCollectionTime(CollectionTimeString);
        	
        	out.setReturnFlag(RowSetUtils.getStringValue(rset, "ReturnFlag"));
        	out.setCollectionFlag(RowSetUtils.getStringValue(rset, "CollectionFlag"));
        	        	
        	outList.add(out);
        }
        return outList;
    }
    
}
