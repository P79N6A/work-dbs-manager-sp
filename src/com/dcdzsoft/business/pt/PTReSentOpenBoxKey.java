package com.dcdzsoft.business.pt;

import java.util.UUID;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
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
 * <p>Description: 重新发送用户取件密码 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTReSentOpenBoxKey extends ActionBean
{

    public String doBusiness(InParamPTReSentOpenBoxKey in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        String result = "";

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.CustomerMobile))
			throw new EduException(ErrorCode.ERR_PARMERR);

		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
		PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
		PTInBoxPackage inboxPack = new PTInBoxPackage();
        inboxPack.TerminalNo = in.TerminalNo;
        inboxPack.PackageID = in.PackageID;
        
        //非在箱信息不发送
        try{
        	inboxPack = inboxPackDAO.find(inboxPack);
        }catch(EduException e)
        {
        	return result;
        }
        	
        ////////////////////////////////////////////////////////////////////
        MBPwdShortMsgDAO pwdSMSDAO = daoFactory.getMBPwdShortMsgDAO();
        MBPwdShortMsg shortMsg = new MBPwdShortMsg();
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PackageID", in.PackageID);
        whereCols0.add("TerminalNo", in.TerminalNo);
        whereCols0.add("StoredTime", inboxPack.StoredTime);
        
        RowSet rset = pwdSMSDAO.select(whereCols0);
        while(RowSetUtils.rowsetNext(rset))
        {
        	shortMsg.WaterID = RowSetUtils.getLongValue(rset, "WaterID");
        }
        
        if(shortMsg.WaterID <= 0)
        	return result;
        
        shortMsg = pwdSMSDAO.find(shortMsg);
		
        if(shortMsg.ReSendNum > 5)
        	return result;
        
        JDBCFieldArray setCols1 = new JDBCFieldArray();
		JDBCFieldArray whereCols1 = new JDBCFieldArray();
		
		//setCols1.add("OpenBoxKey",smsObj.OpenBoxKey);
		setCols1.add("ReSendNum", shortMsg.ReSendNum + 1);
		setCols1.add("LastModifyTime", utilDAO.getCurrentDateTime());
		
		whereCols1.add("WaterID", shortMsg.WaterID);
		
		pwdSMSDAO.update(setCols1, whereCols1);
		
		//发送短信
		if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()))
		{
			TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
			TBTerminal terminal = new TBTerminal();
			terminal.TerminalNo = shortMsg.TerminalNo;
			
			terminal = terminalDAO.find(terminal);
			
			//////////////
			modifySMSStat(shortMsg.TerminalNo);
			
			////////////////
			SMSInfo smsInfo = new SMSInfo();
			smsInfo.PackageID = shortMsg.PackageID;
			smsInfo.TerminalNo = shortMsg.TerminalNo;
			smsInfo.StoredTime = shortMsg.StoredTime;
			smsInfo.CustomerMobile = shortMsg.CustomerMobile;
			smsInfo.OpenBoxKey = shortMsg.OpenBoxKey;
			smsInfo.TerminalName = terminal.TerminalName;
			smsInfo.Location = terminal.Location;
			smsInfo.TerminalType = terminal.TerminalType;
			smsInfo.ZoneID = inboxPack.ZoneID;
			smsInfo.MsgType = SMSInfo.MSG_TYPE_RESENT;
			smsInfo.WaterID = shortMsg.WaterID;
			
			IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
	        IMCompany company = new IMCompany();
	        company.CompanyID = inboxPack.CompanyID;
	        try {
	        	company = companyDAO.find(company);
	        } catch (Exception e) {
	        }
			smsInfo.Latitude = terminal.Latitude;
			smsInfo.Longitude = terminal.Longitude;
			smsInfo.TrailerMsg = company.SMS_Notification;
			smsInfo.expireddays = company.ExpiredDays;
			
			SMSManager.getInstance().sentDeliverySMS(smsInfo);
		}
		
        return result;
    }
    
    private void modifySMSStat(String TerminalNo) throws EduException
    {
    	MBSmsStatDAO smsStatDAO = daoFactory.getMBSmsStatDAO();
    	MBSmsStat smsStat = new MBSmsStat();
    	smsStat.TerminalNo = TerminalNo;
    	smsStat.OccurYear = DateUtils.currentYear();
    	smsStat.OccurMonth = DateUtils.currentMonth();
    	
    	if(smsStatDAO.isExist(smsStat))
    	{
    		JDBCFieldArray setCols = new JDBCFieldArray();
    		JDBCFieldArray whereCols = new JDBCFieldArray();
    		
    		setCols.addSQL(" TotalNum=TotalNum+1 ");
    		setCols.addSQL(" PwdNum=PwdNum+1 ");
    		whereCols.add("TerminalNo", TerminalNo);
    		whereCols.add("OccurYear", smsStat.OccurYear);
    		whereCols.add("OccurMonth", smsStat.OccurMonth);
    		
    		smsStatDAO.update(setCols, whereCols);
    	}else
    	{
    		smsStat.TotalNum = 1;
    		smsStat.PwdNum = 1;
    		smsStat.ExpireNum = 0;
    		smsStat.ReminderNum = 0;
    		smsStat.DynamicNum = 0;
    		smsStat.PickupNum = 0;
    		smsStat.OtherNum = 0;
    		
    		smsStatDAO.insert(smsStat);
    	}
    }
}
