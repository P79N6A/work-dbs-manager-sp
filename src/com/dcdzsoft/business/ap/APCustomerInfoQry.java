package com.dcdzsoft.business.ap;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 个人信息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APCustomerInfoQry extends ActionBean
{

    public OutParamAPCustomerInfoQry doBusiness(InParamAPCustomerInfoQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamAPCustomerInfoQry out = new OutParamAPCustomerInfoQry();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.CustomerID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

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
        String CustomerStatus = "";
        String POBoxAddress   = "";
        //String CustomerID     = "";
        //String CustomerName   = "";
		if (RowSetUtils.rowsetNext(rset)) {//同一个手机只能有一条记录
			out.LockerID       = RowSetUtils.getStringValue(rset, "TerminalNo");
			out.LockerName     = RowSetUtils.getStringValue(rset, "TerminalName");
			out.CustomerID     = RowSetUtils.getStringValue(rset, "CustomerID");
			out.CustomerName   = RowSetUtils.getStringValue(rset, "CustomerName");
			out.CustomerEmail  = RowSetUtils.getStringValue(rset, "Email");
			out.CustomerMobile = RowSetUtils.getStringValue(rset, "Mobile");
			out.Validity       = RowSetUtils.getStringValue(rset, "Validity");//Validity-DATE
			POBoxAddress       = RowSetUtils.getStringValue(rset, "Address");
			CustomerStatus     = RowSetUtils.getStringValue(rset, "Status");
			//break;
		}
		if("0".equals(CustomerStatus)){
			//0-注册为激活
			out.Message   = SMSManager.getInstance().sendReminderActiveSMS(0,out.CustomerMobile,out.CustomerID, out.Validity);//"请及时到附近邮局激活。";
			out.Status = 0;//0-未激活；1-正常；2-失效
		}else if("1".equals(CustomerStatus)){
			//1-正常
			out.Message   = POBoxAddress;
			out.Status = 1;//0-未激活；1-正常；2-失效
		}else if("2".equals(CustomerStatus)){
			//2-失效，但在保留期内
			out.Message   = SMSManager.getInstance().sendReminderExtendSMS(0,out.CustomerMobile,out.CustomerID, out.Validity);//"地址信息失效，请及时延长使用期限"
			out.Status = 2;//0-未激活；1-正常；2-失效
		}else{
			out = null;
		}
		
        return out;
    }
}
