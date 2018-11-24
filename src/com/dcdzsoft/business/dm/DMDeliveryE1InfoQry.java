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
 * <p>Description: 寄件订单信息查询（E1信息） </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryE1InfoQry extends ActionBean
{

    public OutParamDMDeliveryE1InfoQry doBusiness(InParamDMDeliveryE1InfoQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMDeliveryE1InfoQry out = new OutParamDMDeliveryE1InfoQry();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID))//||StringUtils.isEmpty(in.BPartnerID)
			throw new EduException(ErrorCode.ERR_PARMERR);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		
		RowSet rset = null;
	    String viewName = "V_DMDeliveryItems";
		String limitSQL = "";
	    PreparedWhereExpression whereSQL = new PreparedWhereExpression();
	    
		String itemStatus = "";
		if("History".equals(in.QryFlag)){
			viewName = "V_DMHistoryItems";
			whereSQL.add("PackageID", in.PackageID);
			whereSQL.add("CreateTime", in.CreateTime);
		}else{
			itemStatus = "50,51";
			viewName = "V_DMDeliveryItems";
			
			whereSQL.add("PackageID", in.PackageID);
			whereSQL.add("BPartnerID", in.BPartnerID);
	        if(StringUtils.isNotEmpty(itemStatus)){
	        	whereSQL.addSQL(utilDAO.getFlagInSQL("a.ItemStatus", itemStatus));
	        }
		}
        
		String sql = "SELECT * FROM " + viewName + " a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
        java.util.List<String> orderByField = new java.util.LinkedList<String>();
        orderByField.add("CreateTime DESC");

        rset = dbSession.executeQuery(sql,orderByField,0,0,whereSQL);
        if (RowSetUtils.rowsetNext(rset)) {
        	
	    	//用户信息
	    	out.setPackageID(RowSetUtils.getStringValue(rset, "PackageID"));
	    	out.setCustomerName(RowSetUtils.getStringValue(rset, "CustomerName"));
        	out.setCustomerAddress(RowSetUtils.getStringValue(rset, "CustomerAddress"));
        	out.setCustomerMobile(RowSetUtils.getStringValue(rset, "CustomerMobile"));
	    	out.setReturnFlag(RowSetUtils.getStringValue(rset, "ReturnFlag"));//
	    	out.setService(RowSetUtils.getStringValue(rset, "ServiceName"));
	    	out.setParcelSize(RowSetUtils.getStringValue(rset, "ParcelSizeName"));
	    	
	    	//businessPartner信息
	  	    out.setBPartnerID(RowSetUtils.getStringValue(rset, "BPartnerID"));
	      	out.setBPartnerName(RowSetUtils.getStringValue(rset, "BPartnerName"));
	      	out.setAddress(RowSetUtils.getStringValue(rset, "Address"));
	      	out.setMobile(RowSetUtils.getStringValue(rset, "Mobile"));
	      	out.OfBureau = RowSetUtils.getStringValue(rset, "OfBureau");//目的柜子所在的分拣中心编号：PPC-AZC#
	      	
	    	//"0000"为默认服务，用于BP的接口测试
	    	String serverid = RowSetUtils.getStringValue(rset, "ServiceID");
        	if("0000".equals(serverid)){
        		out.setPackageID("--Test Mode--");
        	}
	    }else{
	    	throw new EduException(ErrorCode.ERR_PARMERR);
	    }

        return out;
    }
}
