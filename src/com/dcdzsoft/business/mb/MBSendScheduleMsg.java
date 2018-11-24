package com.dcdzsoft.business.mb;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sms.SMSInfo;
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
 * <p>Description: 发送预定时间的短消息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBSendScheduleMsg extends ActionBean
{

    public int doBusiness(InParamMBSendScheduleMsg in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.WaterIDList))
			throw new EduException(ErrorCode.ERR_PARMERR);

	    //2.    调用CommonDAO.isOnline(管理员编号)判断操作员是否在线。

		//3.调用UtilDAO.getSysDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);


        String[] waterIDList = in.WaterIDList.split(",");
        for(int i = 0; i < waterIDList.length; i++)
        {
            result += sentPwdShortMsg(NumberUtils.parseLong(waterIDList[i]),sysDateTime);
              
        }
	    
        return result;
    }
    public int sentPwdShortMsg(long WaterID,java.sql.Timestamp sysDateTime) throws EduException
    {
        ////////////////////////////////////////////////////////////""
        MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
        MBPwdShortMsg shortMsg = new MBPwdShortMsg();
        shortMsg.WaterID = WaterID;
        try{
            shortMsg = shortMsgDAO.find(shortMsg);
        }catch(EduException e){
            return 0;
        }
        
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
            setCols2.add("SendStatus", "5");//0:未发送 1:发送进行中 2:发送成功 4:发送失败 5:取消发送(非在箱信息,取消发送)
            setCols2.add("LastModifyTime", sysDateTime);
            
            whereCols2.add("WaterID", shortMsg.WaterID);
            
            shortMsgDAO.update(setCols2, whereCols2);*/
            commonDAO.updatePwdSMSStauts(shortMsg, SysDict.ITEM_STATUS_DROP_TAKEOUT, sysDateTime);
            //#end
            return 0;
        }
        inboxPack = inboxPackDAO.find(inboxPack);
        
        //发送短信
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()))
        {
            ///////////////////////////////////////////////////
            TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
            TBTerminal terminal = new TBTerminal();
            terminal.TerminalNo = shortMsg.TerminalNo;
            
            terminal = terminalDAO.find(terminal);
            
            if(shortMsg.ScheduleDateTime==null){
                shortMsg.ScheduleDateTime = sysDateTime;
                JDBCFieldArray setCols1 = new JDBCFieldArray();
                JDBCFieldArray whereCols1 = new JDBCFieldArray();
                
                if(StringUtils.isEmpty(shortMsg.UMID)){
                    shortMsg.UMID = StringUtils.getUUID();
                    setCols1.add("UMID", shortMsg.UMID);
                }
                
                //setCols1.add("MsgType", shortMsg.MsgType);
                setCols1.add("ScheduleDateTime",shortMsg.ScheduleDateTime);
                setCols1.add("LastModifyTime", sysDateTime);
                
                whereCols1.add("WaterID", shortMsg.WaterID);
                
                shortMsgDAO.update(setCols1, whereCols1);
            }
            
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
            smsInfo.MsgType  = shortMsg.MsgType;
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
        }else{
            //短信接口未开，短信发送失败
            JDBCFieldArray setCols2 = new JDBCFieldArray();
            JDBCFieldArray whereCols2 = new JDBCFieldArray();
            setCols2.add("SendStatus", "4");//0:未发送 1:发送进行中 2:发送成功 4:发送失败
            setCols2.add("LastModifyTime", sysDateTime);
            
            whereCols2.add("WaterID", shortMsg.WaterID);
            
            shortMsgDAO.update(setCols2, whereCols2);
        }
        return 1;
    }
}
