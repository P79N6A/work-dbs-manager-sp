package com.dcdzsoft.business.dm;

import javax.sql.RowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
 * <p>Description: 商业伙伴账户余额查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMPartnerBalanceQry extends ActionBean
{

    public OutParamDMPartnerBalanceQry doBusiness(InParamDMPartnerBalanceQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMPartnerBalanceQry out = new OutParamDMPartnerBalanceQry();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		IMBusinessPartner businessPartner = new IMBusinessPartner();
        businessPartner.BPartnerID = in.BPartnerID;
        
		try{
			businessPartner = businessPartnerDAO.find(businessPartner);
		}catch (EduException  e){
			throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
		}
		
		out.Balance      = businessPartner.Balance;
		out.BPartnerID   = businessPartner.BPartnerID;
		out.CreditLimit   = businessPartner.CreditLimit;
		
		//取可选服务
		String limitsql = "";
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("BPartnerID", in.BPartnerID); 
        
        JSONArray  serviceArray = new JSONArray();
        try{
        	String sql = "SELECT * FROM V_IMBPartnerServiceRight WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitsql;
            
            RowSet rset = dbSession.executeQuery(sql,whereSQL);
            while (RowSetUtils.rowsetNext(rset)) {
            	JSONObject serviceJson = new JSONObject();
            	serviceJson.put("id", RowSetUtils.getStringValue(rset, "ServiceID"));
            	serviceJson.put("name", RowSetUtils.getStringValue(rset, "ServiceName"));
            	serviceJson.put("small", RowSetUtils.getDoubleValue(rset, "ServiceAmtSmall"));
            	serviceJson.put("med", RowSetUtils.getDoubleValue(rset, "ServiceAmtMed"));
            	serviceJson.put("big", RowSetUtils.getDoubleValue(rset, "ServiceAmtBig"));
            	serviceArray.add(serviceJson);
            }
    		
        }catch(EduException e){
        	System.err.println(e.getMessage());
        }
        out.ServicesList  = serviceArray.toString();
        
        return out;
    }
}
