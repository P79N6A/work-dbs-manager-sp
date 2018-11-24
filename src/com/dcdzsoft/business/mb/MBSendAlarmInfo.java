package com.dcdzsoft.business.mb;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.email.EmailSenderManager;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 发送警报信息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBSendAlarmInfo extends ActionBean
{

	/**
	 * 
	 * @param in
	 *   Mode 发送方式（1-邮件，2-短信，3-短信&邮件，默认1）
	 * @return
	 * @throws EduException
	 */
    public int doBusiness(InParamMBSendAlarmInfo in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = null;
		if(StringUtils.isNotEmpty(in.OperID)){
		    operOnline = commonDAO.isOnline(in.OperID);
		}

		
		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
		TBTerminal terminal = new TBTerminal();
		terminal.TerminalNo = in.TerminalNo;
		terminal = terminalDAO.find(terminal);
		
		String sendEmail = "";
		String  sendSMS= "";
		
		if(in.SendMode == 1 || in.SendMode==3){//1-邮件，2-短信，3-短信&邮件，默认3
			if(!commonDAO.isEmail(terminal.MaintEmail)){
				throw new EduException(ErrorCode.ERR_BUSINESS_EMAILFORMATERROR);
			}
			sendEmail = terminal.MaintEmail;
		}
		if(in.SendMode == 2 || in.SendMode==3){//1-邮件，2-短信，3-短信&邮件，默认3
			if(!commonDAO.isPhoneNumber(terminal.MaintMobile)){
				throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
			}
			sendSMS = terminal.MaintMobile;
		}

		result = commonDAO.doSendAlarmInfo(0,in.TerminalNo, in.SendMode);
		
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		if(operOnline!=null){
		    switch(result){
	        case -1:
	        case -2:
	        case -3:
	        case -5:
	            throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
	        case -4:
	        case -6:
	            throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
	        }
		    
		    OPOperatorLog log = new OPOperatorLog();
	        log.OperID = in.OperID;
	        log.FunctionID = in.getFunctionID();
	        log.OccurTime = sysDateTime;
	        log.StationAddr = operOnline.LoginIPAddr;
	        log.Remark = "Send Alarm info :"+sendEmail +" "+sendSMS;

	        commonDAO.addOperatorLog(log);
		}
		

        return result;
    }
}
