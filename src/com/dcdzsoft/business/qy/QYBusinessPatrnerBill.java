package com.dcdzsoft.business.qy;

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
 * <p>Description: 商业伙伴账单查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class QYBusinessPatrnerBill extends ActionBean
{

	/**
	 * 交易账单导出
	 * @param in
	 * @return
	 * @throws EduException
	 */
    public java.util.List<OutParamQYBusinessPatrnerBill> doBusiness(InParamQYBusinessPatrnerBill in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        java.util.List<OutParamQYBusinessPatrnerBill> outList = new java.util.LinkedList<OutParamQYBusinessPatrnerBill>();

		//1.验证输入参数是否有效，如果无效返回-1。
        
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
        if(in.BeginDate==null){
			in.BeginDate = DateUtils.addDate(sysDate, -60);//查询近2个月的订单记录
		}
		if(in.EndDate==null){
			in.EndDate = sysDate;
		}
		in.EndDate = DateUtils.addDate(in.EndDate, 1);//
		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("BPartnerID", in.BPartnerID);
        whereSQL.add("BillTime", ">=",in.BeginDate);
        whereSQL.add("BillTime", "<=",in.EndDate);
        String sql = "SELECT * FROM V_PYServiceBillWater  WHERE 1=1 " + whereSQL.getPreparedWhereSQL() ;
        
        java.util.List<String> orderByField = new java.util.LinkedList<String>();
        orderByField.add("BillTime ASC");

        RowSet rset = dbSession.executeQuery(sql,orderByField,0, 0, whereSQL);
        while (RowSetUtils.rowsetNext(rset)) {
        	OutParamQYBusinessPatrnerBill out = new OutParamQYBusinessPatrnerBill();
        	out.setTradeWaterID(RowSetUtils.getStringValue(rset, "TradeWaterID"));
        	out.setBPartnerID(RowSetUtils.getStringValue(rset, "BPartnerID"));
        	out.setBPartnerName(RowSetUtils.getStringValue(rset, "BPartnerName"));
        	out.setBillType(RowSetUtils.getStringValue(rset, "BillType"));
        	out.setBillTypeName(RowSetUtils.getStringValue(rset, "BillTypeName"));
        	out.setBillAmt(RowSetUtils.getDoubleValue(rset, "BillAmt"));
        	out.setBalance(RowSetUtils.getDoubleValue(rset, "Balance"));
        	out.setBillTime(RowSetUtils.getTimestampValue(rset, "BillTime"));
        	out.setPackageID(RowSetUtils.getStringValue(rset, "PackageID"));
        	out.setServiceID(RowSetUtils.getStringValue(rset, "ServiceID"));
        	out.setServiceName(RowSetUtils.getStringValue(rset, "ServiceName"));
        	out.setBillDetails(RowSetUtils.getStringValue(rset, "BillDetails"));
        	outList.add(out);
        	
        }
        
        return outList;
    }
}
