package com.dcdzsoft.business.im;

import javax.sql.RowSet;

import net.sf.json.JSONObject;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ErrorMsgConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 个人客户信息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCustomerInfoQry extends ActionBean
{

    public String doBusiness(InParamIMCustomerInfoQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamIMCustomerInfoQry out = new OutParamIMCustomerInfoQry();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);


		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.addSQL(" AND( ");
		whereSQL.addSQL("  CustomerID= "+StringUtils.addQuote(in.CustomerID));
    	whereSQL.addSQL(" OR Mobile=" + StringUtils.addQuote(in.CustomerID));
    	whereSQL.addSQL(" )");
    	
    	String sql = "SELECT * FROM  V_IMCustomer WHERE 1=1 " + whereSQL.getPreparedWhereSQL() ;
    	
    	RowSet rset = dbSession.executeQuery(sql, whereSQL);
		if(RowSetUtils.rowsetNext(rset)) {//同一个手机只能有一条记录
			out.CustomerID     = RowSetUtils.getStringValue(rset, "CustomerID");
			out.CustomerName  = RowSetUtils.getStringValue(rset, "CustomerName");
			out.Email  = RowSetUtils.getStringValue(rset, "Email");
			out.Mobile = RowSetUtils.getStringValue(rset, "Mobile");
			out.Address = RowSetUtils.getStringValue(rset, "Address");
			out.TerminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
			out.ZoneID    = RowSetUtils.getStringValue(rset, "ZoneID");
		}
		if(StringUtils.isEmpty(out.Mobile)){
			String message = ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_CUSTOMERNOTEXISTS);
			message = message.replaceFirst("#ID", in.CustomerID);
			out.Address = message;
		}else{
			if(!operOnline.ZoneID.equals(out.ZoneID)){
				String message = ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_OPERAT_ITEMBELONGOTDIFFERENTAZC);
				if(StringUtils.isNotEmpty(out.Address)){
					message += "\nCustomer new address:\n"+out.Address;
				}else{
					message += "\nProper AZC "+out.ZoneID;
				}
				out.Address = message;
				out.Mobile  = "";
			}
		}
		JSONObject outJson = JSONObject.fromObject(out);
        return outJson.toString();
    }
}
