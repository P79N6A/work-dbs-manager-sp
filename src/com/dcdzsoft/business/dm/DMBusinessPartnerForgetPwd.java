package com.dcdzsoft.business.dm;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.email.EmailSenderManager;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: BP忘记密码 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMBusinessPartnerForgetPwd extends ActionBean
{

    public int doBusiness(InParamDMBusinessPartnerForgetPwd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		IMBusinessPartner businessPartner = new IMBusinessPartner();
        businessPartner.BPartnerID = in.BPartnerID;
        businessPartner = businessPartnerDAO.find(businessPartner);
        
        String pwd = RandUtils.generateString(6);//6位密码
        
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("Password", SecurityUtils.md5(pwd));
        
        whereCols.add("BPartnerID", in.BPartnerID);

        result = businessPartnerDAO.update(setCols, whereCols);
        
       //send email
        if(StringUtils.isNotEmpty(businessPartner.Email)
        		&&StringUtils.isNotEmpty(ApplicationConfig.getInstance().getEmailAddress())){
    		EmailSenderManager.getInstance().sendWebPortalLoginAccount(businessPartner.BPartnerName, businessPartner.Username, pwd, businessPartner.Email);
        }
        
      //发送短信密码
        ControlParam ctrlParam = ControlParam.getInstance();
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()) 
        		&&StringUtils.isNotEmpty(businessPartner.Mobile)
        		&&"1".equals(ctrlParam.getSendBPPwdSMS()))
        {
        	SMSManager.getInstance().sentBPInitPwd(businessPartner.Username, pwd,businessPartner.Mobile);
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
