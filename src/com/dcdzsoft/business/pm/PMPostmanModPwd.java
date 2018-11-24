package com.dcdzsoft.business.pm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.sms.SMSManager;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 邮递员修改密码 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMPostmanModPwd extends ActionBean
{

	/**
	 * 管理员执行修改密码功能，系统将密码以短信发送到投递员绑定的手机，要求输入投递员绑定的手机号
	 * @param in
	 * @return
	 * @throws EduException
	 */
    public int doBusiness(InParamPMPostmanModPwd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PostmanID)
			//||StringUtils.isEmpty(in.Password)
			)
			throw new EduException(ErrorCode.ERR_PARMERR);

		if(!commonDAO.isPhoneNumber(in.getBindMobile())){
        	throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
        }
		
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.PostmanID;
        postman  = postmanDAO.find(postman);
        
        //必须与绑定的手机号一致        
        /*if(!commonDAO.isPhoneNumber(postman.BindMobile)){
        	throw new EduException(ErrorCode.ERR_BUSINESS_BOUNDMOBILEERROR);
        } 
        if(!in.getBindMobile().equals(postman.BindMobile)){
        	throw new EduException(ErrorCode.ERR_BUSINESS_INPUTMOBILEERROR);
        }*/

        //生成登入密码
        if(StringUtils.isEmpty(in.Password)){
        	in.Password = RandUtils.generateNumber(4);//4位密码，沙特要求
        }
        String newPwd = SecurityUtils.md5(in.Password); //MD5 加密
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();

        setCols.add("Password",newPwd);
        whereCols.add("PostmanID",in.PostmanID);
        
        result = postmanDAO.update(setCols,whereCols);
        
        //发送短信密码
        ControlParam ctrlParam = ControlParam.getInstance();
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg())
        	&&"1".equals(ctrlParam.getRegSentPwdSms()))
        {
        	SMSManager.getInstance().sentPostManPwd(in.Password, postman.BindMobile);
        }

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.PostmanID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = "";
        log.Remark = in.OperID;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
