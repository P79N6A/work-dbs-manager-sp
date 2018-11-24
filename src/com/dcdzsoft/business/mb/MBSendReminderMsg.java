package com.dcdzsoft.business.mb;

import java.util.Calendar;
import java.util.UUID;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
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
 * <p>Description: 发送催领短消息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBSendReminderMsg extends ActionBean
{

    public int doBusiness(InParamMBSendReminderMsg in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);
				
    	PTInBoxPackageDAO inBoxPackageDAO = daoFactory.getPTInBoxPackageDAO();
    	PTInBoxPackage inboxPack = new PTInBoxPackage();
        inboxPack.PackageID  = in.PackageID;
        inboxPack.TerminalNo = in.TerminalNo;
        try{
            inboxPack = inBoxPackageDAO.find(inboxPack);
        }catch(EduException e){
            return 0;
        }
        
        
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		//java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
        
        //更新催领短信发送时间
        inboxPack.ReminderDateTime = commonDAO.getReminderTime(inboxPack.CompanyID, false);
        
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("ReminderDateTime", inboxPack.ReminderDateTime);
        
        whereCols.add("PackageID", inboxPack.PackageID);
        whereCols.add("TerminalNo", inboxPack.TerminalNo);

        inBoxPackageDAO.update(setCols, whereCols);
        
        if(SysDict.ITEM_STATUS_DROP_EXPIRED.equals(inboxPack.ItemStatus)
                ||SysDict.ITEM_STATUS_DROP_M_EXPIRED.equals(inboxPack.ItemStatus)){
            return result;
        }
        ////////////////////////////////////////////////////////////////////////////////
        MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
    	MBPwdShortMsg shortMsg = new MBPwdShortMsg();
    	
    	JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PackageID", in.PackageID);
        whereCols0.add("TerminalNo", in.TerminalNo);
        whereCols0.add("StoredTime", inboxPack.StoredTime);//add by zxy :MBPwdShortMsg表中相同PackageID、TerminalNo的可能有多条记录，故增加StoredTime条件
        
        RowSet rset = shortMsgDAO.select(whereCols0);
        if(RowSetUtils.rowsetNext(rset))
        {
        	shortMsg.WaterID = RowSetUtils.getLongValue(rset, "WaterID");
        }
        
        if(shortMsg.WaterID <= 0){
        	return result;
        }
    	shortMsg = shortMsgDAO.find(shortMsg);
    	
    	//插入催领流水
        MBReminderWaterDAO waterDAO = daoFactory.getMBReminderWaterDAO();
        MBReminderWater water = new MBReminderWater();

        SequenceGenerator seqGen = SequenceGenerator.getInstance();
        water.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_WATERID);
        water.TerminalNo = inboxPack.TerminalNo;
        water.CustomerMobile = inboxPack.CustomerMobile;
        water.PackageID = inboxPack.PackageID;
        water.PostmanID = inboxPack.DropAgentID;
        water.StoredDate = inboxPack.StoredDate;
        water.StoredTime = inboxPack.StoredTime;
        water.ReminderNum = 1;
        water.LastModifyTime = utilDAO.getCurrentDateTime();
        
        waterDAO.insert(water);
        
        //发送催领短信
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()))
        {
        	//////////////////////////////////////////
        	modifySMSStat(in.TerminalNo);
        	
        	///////////////////////////////////////////////////
        	TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        	TBTerminal terminal = new TBTerminal();
        	terminal.TerminalNo = in.TerminalNo;
        	
        	terminal = terminalDAO.find(terminal);
        	
        	// //////////////////////////////////
        	//重置预定发送时间，并设置为未发送状态，可由发送线程进行短信发送
        	shortMsg.UMID = StringUtils.getUUID();
            shortMsg.MsgType = SMSInfo.MSG_TYPE_REMINDER;
            shortMsg.ScheduleDateTime = null;
            
            
            JDBCFieldArray setCols1 = new JDBCFieldArray();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            
            setCols1.add("SendStatus", "0");//0:未发送 1:发送进行中 2:发送成功 4:发送失败
            setCols1.add("UMID", shortMsg.UMID);
            setCols1.add("MsgType", shortMsg.MsgType);
            setCols1.add("ScheduleDateTime",shortMsg.ScheduleDateTime);
            setCols1.add("LastModifyTime", sysDateTime);
            
            whereCols1.add("WaterID", shortMsg.WaterID);
            
            shortMsgDAO.update(setCols1, whereCols1);
            
    		///////////////
        	SMSInfo smsInfo = new SMSInfo();
        	smsInfo.WaterID = shortMsg.WaterID;
        	smsInfo.PackageID = in.PackageID;
        	smsInfo.TerminalNo = in.TerminalNo;
        	smsInfo.StoredTime = inboxPack.StoredTime;
        	smsInfo.CustomerMobile = inboxPack.CustomerMobile;
        	smsInfo.OpenBoxKey = shortMsg.OpenBoxKey;
        	smsInfo.TerminalName = terminal.TerminalName;
        	smsInfo.Location = terminal.Location;
        	smsInfo.TerminalType = terminal.TerminalType;
        	smsInfo.MsgType = SMSInfo.MSG_TYPE_REMINDER;
        	smsInfo.UMID  = shortMsg.UMID;
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
    		setCols.addSQL(" ReminderNum=ReminderNum+1 ");
    		whereCols.add("TerminalNo", TerminalNo);
    		whereCols.add("OccurYear", smsStat.OccurYear);
    		whereCols.add("OccurMonth", smsStat.OccurMonth);
    		
    		smsStatDAO.update(setCols, whereCols);
    	}else
    	{
    		smsStat.TotalNum = 1;
    		smsStat.PwdNum = 0;
    		smsStat.ExpireNum = 0;
    		smsStat.ReminderNum = 1;
    		smsStat.DynamicNum = 0;
    		smsStat.PickupNum = 0;
    		smsStat.OtherNum = 0;
    		
    		smsStatDAO.insert(smsStat);
    	}
    }
}
