package com.dcdzsoft.business.pt;

import java.util.UUID;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.sequence.SequenceGenerator;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 重置提货码 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTResetOpenBoxKey extends ActionBean
{

    public int doBusiness(InParamPTResetOpenBoxKey in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.PackageID)
        	|| StringUtils.isEmpty(in.TerminalNo)
            || StringUtils.isEmpty(in.CustomerMobile))
            throw new EduException(ErrorCode.ERR_PARMERR);

        //2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

        //4.	调用UtilDAO.getSysDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        
        //////////////////////////////////////////////////////////////
        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
        PTInBoxPackage inboxPack = new PTInBoxPackage();
        
        inboxPack.PackageID = in.PackageID;
        inboxPack.TerminalNo = in.TerminalNo;
        
        try{
        	inboxPack = inboxPackDAO.find(inboxPack);
        }catch(EduException e){
        	throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
        }

        //生成开箱密码
        String pwd = generatePwd();
        in.OpenBoxKey = SecurityUtils.md5(pwd);
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("OpenBoxKey", in.OpenBoxKey);
		whereCols0.add("TerminalNo", in.TerminalNo);

		if (inboxPackDAO.isExist(whereCols0) > 0) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}

			pwd = generatePwd();

			if (StringUtils.isEmpty(in.OpenBoxKey)) {
				in.OpenBoxKey = SecurityUtils.md5(pwd);
			}

			JDBCFieldArray whereCols00 = new JDBCFieldArray();
			whereCols00.add("OpenBoxKey", in.OpenBoxKey);
			whereCols00.add("TerminalNo", in.TerminalNo);

			if (inboxPackDAO.isExist(whereCols00) > 0) {
				throw new EduException(ErrorCode.ERR_OPENBOXKEYEXISTS);
			}
		}

        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()))
	   		 in.OpenBoxKey = SecurityUtils.md5(pwd);
	   	else
	   		 in.OpenBoxKey = SecurityUtils.md5("333333");

        ////////////////////////////////////////////////////////////////
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
         
        setCols.add("OpenBoxKey",in.OpenBoxKey);
        setCols.add("LastModifyTime",sysDateTime);
        whereCols.add("PackageID", in.PackageID);
        whereCols.add("TerminalNo", in.TerminalNo);
        
        inboxPackDAO.update(setCols,whereCols);

        //发送短信
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()))
        {
        	MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
        	MBPwdShortMsg shortMsg = new MBPwdShortMsg();
        	
        	JDBCFieldArray whereCols2 = new JDBCFieldArray();
        	whereCols2.add("PackageID", inboxPack.PackageID);
    		whereCols2.add("TerminalNo", inboxPack.TerminalNo);
    		whereCols2.add("StoredTime", inboxPack.StoredTime);
            
            RowSet rset = shortMsgDAO.select(whereCols2);
            if(RowSetUtils.rowsetNext(rset))
            {
            	shortMsg.WaterID = RowSetUtils.getLongValue(rset, "WaterID");
            }
            
            if(shortMsg.WaterID <= 0){
            	SequenceGenerator seqGen = SequenceGenerator.getInstance();
            	shortMsg.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_WATERID);
                shortMsg.TerminalNo = inboxPack.TerminalNo;
                shortMsg.PackageID = inboxPack.PackageID;
                shortMsg.StoredTime = inboxPack.StoredTime;
                shortMsg.StoredDate = inboxPack.StoredDate;
                shortMsg.OpenBoxKey = pwd;
                shortMsg.CustomerMobile = inboxPack.CustomerMobile;
                shortMsg.LastModifyTime = sysDateTime;
                shortMsg.ReSendNum = 0;
                shortMsg.PackageStatus = inboxPack.ItemStatus;
                shortMsg.SendStatus = "0"; // 0:未发送 1:发送进行中 2:发送成功 4:发送失败
                
                shortMsg.MsgType    = SMSInfo.MSG_TYPE_DELIVERY;;
                shortMsg.UMID       = StringUtils.getUUID();
                shortMsg.ScheduleDateTime = null;
                
                shortMsgDAO.insert(shortMsg);
            }else{
                shortMsg = shortMsgDAO.find(shortMsg);
                
                shortMsg.MsgType    = SMSInfo.MSG_TYPE_DELIVERY;
                shortMsg.UMID       = StringUtils.getUUID();
                shortMsg.ScheduleDateTime = null;
                JDBCFieldArray setCols22 = new JDBCFieldArray();
                JDBCFieldArray whereCols22 = new JDBCFieldArray();
                
                setCols22.add("SendStatus", "0");//0:未发送 1:发送进行中 2:发送成功 4:发送失败
                setCols22.add("UMID", shortMsg.UMID);
                setCols22.add("MsgType", shortMsg.MsgType);
                setCols22.add("ScheduleDateTime",shortMsg.ScheduleDateTime);
                setCols22.add("LastModifyTime", sysDateTime);
                
                whereCols22.add("WaterID", shortMsg.WaterID);
                
                shortMsgDAO.update(setCols22, whereCols22);
            	
            }
        	//////////////////////////
        	TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        	TBTerminal terminal = new TBTerminal();
        	terminal.TerminalNo = in.TerminalNo;
        	
        	terminal = terminalDAO.find(terminal);
        	
        	SMSInfo smsInfo = new SMSInfo();
        	smsInfo.PackageID = in.PackageID;
        	smsInfo.TerminalNo = in.TerminalNo;
        	smsInfo.StoredTime = inboxPack.StoredTime;
        	smsInfo.CustomerMobile = in.CustomerMobile;
        	smsInfo.OpenBoxKey = pwd;
        	smsInfo.TerminalName = terminal.TerminalName;
        	smsInfo.Location = terminal.Location;
        	smsInfo.TerminalType = terminal.TerminalType;
        	smsInfo.MsgType = SMSInfo.MSG_TYPE_RESENT;
        	smsInfo.UMID    = shortMsg.UMID;
            smsInfo.ScheduledDateTime = shortMsg.ScheduleDateTime;
			
			IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
	        IMCompany company = new IMCompany();
	        company.CompanyID = inboxPack.CompanyID;
	        try {
	        	company = companyDAO.find(company);
	        } catch (Exception e) {
	        }
	        smsInfo.CompanyID = company.CompanyID;
            smsInfo.CompanyName = company.CompanyName;
	        smsInfo.Latitude = terminal.Latitude;
			smsInfo.Longitude = terminal.Longitude;
			smsInfo.TrailerMsg = company.SMS_Notification;
			smsInfo.expireddays = company.ExpiredDays;
			
			//发送到同一个手机的多条短信，延迟发送
			commonDAO.scheduleSendSMS(smsInfo, shortMsg, sysDateTime);
        	
        }
        
        ///推送到设备端
        com.dcdzsoft.businessproxy.PushBusinessProxy.doBusiness(in);
        
        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = "";

        commonDAO.addOperatorLog(log);

        return result;
    }
	private String generatePwd() {
		ControlParam ctrlParam = ControlParam.getInstance();

		// 生成开箱密码
		int pwdLen = NumberUtils.parseInt(ctrlParam.getTakeOutPwdLen());
		String pwd = "";

		if (SysDict.TAKEOUTPWD_FORM_NUMBER.equals(ctrlParam.getTakeOutPwdFormat()))
			pwd = RandUtils.generateNumber(pwdLen);
		else if (SysDict.TAKEOUTPWD_FORM_CHAR.equals(ctrlParam.getTakeOutPwdFormat()))
			pwd = RandUtils.generateCharacter(pwdLen);
		else if (SysDict.TAKEOUTPWD_FORM_NUMBERCHAR.equals(ctrlParam.getTakeOutPwdFormat()))
			pwd = RandUtils.generateString(pwdLen);

		return pwd;
	}
}
