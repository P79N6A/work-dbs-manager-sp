package com.dcdzsoft.business.dm;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 商业合作伙伴登录 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMBusinessPartnerLogin extends ActionBean
{

	/**
	 * 作为Portal网站的登录入口，包括BusinessPartner 以及其他用户登录
	 * @param in
	 * @return
	 * @throws EduException
	 */
    public OutParamDMBusinessPartnerLogin doBusiness(InParamDMBusinessPartnerLogin in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMBusinessPartnerLogin out = new OutParamDMBusinessPartnerLogin();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.Username)
			||StringUtils.isEmpty(in.Password))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//#start 检查用户名和密码是否存在
		in.Password = SecurityUtils.md5(in.Password);
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
    	whereSQL.add("UserName", in.Username);
    	whereSQL.add("UserKey", in.Password);
    	String sql = "SELECT * FROM  V_LoginUser a WHERE 1=1 " + whereSQL.getPreparedWhereSQL();
    	RowSet rset = dbSession.executeQuery(sql, whereSQL);
    	if (RowSetUtils.rowsetNext(rset)) {
    		String UserType = RowSetUtils.getStringValue(rset, "UserType");
    		String UserID   = RowSetUtils.getStringValue(rset, "UserID");
    		switch(UserType){
    		case SysDict.USER_TYPE_BPARTNER:
    			IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
    			IMBusinessPartner businessPartner = new IMBusinessPartner();
    	        businessPartner.BPartnerID = UserID;
    	        businessPartner = businessPartnerDAO.find(businessPartner);
    	        
    	        /*in.Password = SecurityUtils.md5(in.Password);
    	        
    	        if (!businessPartner.Password.equalsIgnoreCase(in.Password))//
    	            throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
    	        */
    	        
    	        //获取Business Partner信息
    	        out.BPartnerID   = businessPartner.BPartnerID;
    	        out.BPartnerName = businessPartner.BPartnerName;
    	        //out.Address      = businessPartner.Address;
    	        //out.Email        = businessPartner.Email;
    	        //out.Mobile       = businessPartner.Mobile;
    	        out.Username     = businessPartner.Username;
    	        out.UserType     = UserType;
    	        out.Balance      = businessPartner.Balance;
    	       // out.CreditFlag   = businessPartner.CreditFlag;
    	        out.CreditLimit  = businessPartner.CreditLimit;
    	        out.Discount     = businessPartner.Discount;
    	        out.ReturnFlag   = businessPartner.ReturnServiceFlag;
    	        out.CollectionFlag  = businessPartner.CollectionServiceFlag;
    	        //out.CollectZoneID          = businessPartner.CollectZoneID;
    	        //out.CollectZoneName        = "";
    	        
    	        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
    			OPOperatorLog log = new OPOperatorLog();
    			log.OperID = businessPartner.BPartnerID;
    			log.FunctionID = in.getFunctionID();
    			log.OccurTime = sysDateTime;
    			log.StationAddr = "";
    			log.Remark = businessPartner.BPartnerName;

    			commonDAO.addOperatorLog(log);
    			break;
    		default:
    			out.Username     = in.Username;
    	        out.UserType     = UserType;
    			break;
    		}
    	}else{
    		//用户名或密码错误
        	throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
    	}
    	//#end
		//验证用户名和密码
		/*String partnerID = "";
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("Username", in.Username);
        RowSet rset = businessPartnerDAO.select(whereCols0);
        if (RowSetUtils.rowsetNext(rset)) {
            partnerID = RowSetUtils.getStringValue(rset, "BPartnerID");
        } else {
            whereCols0 = new JDBCFieldArray();
            whereCols0.add("BPartnerID", in.Username);
            rset = businessPartnerDAO.select(whereCols0);
            if (RowSetUtils.rowsetNext(rset)) {
            	partnerID = RowSetUtils.getStringValue(rset, "BPartnerID");
            } else {
            	//用户名或密码错误
            	throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
            }
        }*/       
		

        return out;
    }
}
