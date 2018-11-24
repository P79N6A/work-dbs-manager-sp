package com.dcdzsoft.business.mb;

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
 * <p>Description: 取件密码短消息重新发送 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBPwdShortMsgReSend extends ActionBean
{

    public int doBusiness(InParamMBPwdShortMsgReSend in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.WaterID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断操作员是否在线。
		OPOperOnline operOnline = null;
		if(StringUtils.isNotEmpty(in.OperID)){
			operOnline = commonDAO.isOnline(in.OperID);
		}

		//3.	调用UtilDAO.getSysDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		String[] waterIDList = in.WaterID.split(",");
		for(int i = 0; i < waterIDList.length; i++)
		{
			result += sentPwdShortMsg(NumberUtils.parseLong(waterIDList[i]),sysDateTime);
		}
		
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "";

		commonDAO.addOperatorLog(log);


        return result;
    }
    
    /**
     * 包括人工重发，和发送失败重发
     * @param WaterID
     * @param sysDateTime
     * @return
     * @throws EduException
     */
    public int sentPwdShortMsg(long WaterID,java.sql.Timestamp sysDateTime) throws EduException
    {
		////////////////////////////////////////////////////////////""
		MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
		MBPwdShortMsg shortMsg = new MBPwdShortMsg();
		shortMsg.WaterID = WaterID;
		
		shortMsg = shortMsgDAO.find(shortMsg);
		
		//在箱检查
		PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
		PTInBoxPackage inboxPack = new PTInBoxPackage();
        inboxPack.TerminalNo = shortMsg.TerminalNo;
        inboxPack.PackageID = shortMsg.PackageID;
        
        //非在箱信息不发送
        if(inboxPackDAO.isExist(inboxPack) == false){
    		//#start修改短信里面的包裹状态
    		/*JDBCFieldArray setCols2 = new JDBCFieldArray();
    		JDBCFieldArray whereCols2 = new JDBCFieldArray();
    		setCols2.add("PackageStatus", SysDict.ITEM_STATUS_DROP_TAKEOUT);
    		whereCols2.add("PackageID", shortMsg.PackageID);
    		whereCols2.add("TerminalNo", shortMsg.TerminalNo);
    		whereCols2.addSQL(utilDAO.getFlagInSQL("PackageStatus", "5,6,8,9"));//5-Dropped,6-D-Dropped，8-Expired，9-M-Expired
    		
    		shortMsgDAO.update(setCols2, whereCols2);
    		*/
    		commonDAO.updatePwdSMSStauts(shortMsg, SysDict.ITEM_STATUS_DROP_TAKEOUT, sysDateTime);
            //#end
        	return 0;
        }
        inboxPack = inboxPackDAO.find(inboxPack);

		//发送短信
		if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()))
		{
            //////////////////////////////////////////
            modifySMSStat(shortMsg.TerminalNo);
            
            ///////////////////////////////////////////////////
			TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
			TBTerminal terminal = new TBTerminal();
			terminal.TerminalNo = shortMsg.TerminalNo;
			
			terminal = terminalDAO.find(terminal);
			
	        // //////////////////////////////////
			if("2".equals(shortMsg.SendStatus)){//发送成功，要求重新发送，生成新的umid
                shortMsg.UMID = StringUtils.getUUID();
            }else{
                if(StringUtils.isEmpty(shortMsg.UMID)){
                    shortMsg.UMID = StringUtils.getUUID();
                }
            }
            //重新设置预定发送时间，并设置为未发送状态，可由发送线程进行短信发送
            shortMsg.ScheduleDateTime = null;
            
            JDBCFieldArray setCols1 = new JDBCFieldArray();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            
            setCols1.add("SendStatus", "0");//0:未发送 1:发送进行中 2:发送成功 4:发送失败
            setCols1.add("UMID", shortMsg.UMID);
            //setCols1.add("MsgType", shortMsg.MsgType);
            setCols1.add("ScheduleDateTime",shortMsg.ScheduleDateTime);
            setCols1.add("LastModifyTime", sysDateTime);
            
            whereCols1.add("WaterID", shortMsg.WaterID);
            
            shortMsgDAO.update(setCols1, whereCols1);
            
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
			smsInfo.MsgType  = SMSInfo.MSG_TYPE_RESENT;
			smsInfo.WaterID  = shortMsg.WaterID;
			smsInfo.UMID     = shortMsg.UMID;
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
		return 1;
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

