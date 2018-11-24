package com.dcdzsoft.sms;

import groovy.transform.Synchronized;

import java.io.StringWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.sql.RowSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.Velocity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;















































import com.dcdzsoft.EduException;
import com.dcdzsoft.business.GServer;
import com.dcdzsoft.client.web.IMWebClientAdapter;
import com.dcdzsoft.client.web.MBWebClientAdapter;
import com.dcdzsoft.client.web.OPWebClientAdapter;
import com.dcdzsoft.client.web.SMWebClientAdapter;
import com.dcdzsoft.client.web.TBWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.Constant;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dto.business.InParamIMCustomerModStatusAuto;
import com.dcdzsoft.dto.business.InParamIMCustomerQry;
import com.dcdzsoft.dto.business.InParamMBModSMSSentStatus;
import com.dcdzsoft.dto.business.InParamMBPwdShortMsgQry;
import com.dcdzsoft.dto.business.InParamMBPwdShortMsgQryCount;
import com.dcdzsoft.dto.business.InParamMBPwdShortMsgReSend;
import com.dcdzsoft.dto.business.InParamMBReminderMsgQry;
import com.dcdzsoft.dto.business.InParamMBReportEmailRecrQry;
import com.dcdzsoft.dto.business.InParamMBScheduleMsgQry;
import com.dcdzsoft.dto.business.InParamMBSendOfflineInfo;
import com.dcdzsoft.dto.business.InParamMBSendReminderMsg;
import com.dcdzsoft.dto.business.InParamMBSendScheduleMsg;
import com.dcdzsoft.dto.business.InParamOPOperatorQry;
import com.dcdzsoft.dto.business.InParamTBTerminalListQry;
import com.dcdzsoft.email.EmailInfo;
import com.dcdzsoft.email.impl.EmailSenderProxyTEXT;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.util.DateUtils;
import com.dcdzsoft.util.NumberUtils;


public class SMSManager {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(SMSManager.class);

	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	private static ControlParam ctrlParam = ControlParam.getInstance();
	private static String g_Charset    = "UNICODE";//utf-8
	
	//记录同一个手机号最后发送短信的时间
	private ConcurrentHashMap<String,SMSMobileInfo> lastSentSMSMobile;//记录同一手机号短信发送信息
	private ConcurrentHashMap<String,SMSInfo> sendSMSInfoList;//等待发送短信
	
	private Template shortMsgTemplate = null;
	private Template expiredMsgTemplate = null;
	private Template postmanPwdTemplate = null;
	private Template reminderTemplate = null;
	private Template reminderTemplate2 = null;//遗留物品取件提醒
	private Template pickupMsgTemplate = null;//取件短信提醒
	
	private Template repairmanMsgTemplate = null;//维修人员消息模板
	private Template urgentSMSTemplate = null;
	
	private Template shortMsg4CounterTemplate = null;
    private Template expiredMsg4CounterTemplate = null;
    private Template reminder4CounterTemplate = null;
    
	private Template lockerAlarmSMSTemplate = null;
	
	private Template businessPartnerPwdTemplate = null;
	private Template operatorPwdTemplate = null;
	private Template balanceMsgTemplate = null;
	private Template topupMsgTemplate = null;
	
	private Template customerAddressTemplate = null;//POBox地址消息短信模板
	private Template customerReminderActiveTemplate = null;//提醒用户激活短信模板
	private Template customerReminderExtendTemplate = null;//提醒用户延期短信模板
	private Template customerAppGuideTemplate = null;//POBox服务应用指南
	private Template customerVerificationCodeTemplate = null;//用户更新信息模板
	
	
	/**
	 * 工作的线程数
	 */
	private int workerCount = 100;

	private ThreadPoolExecutor executor;
	private ReminderThread reminderThread;
	private CustomerManagerThread customerManagerThread;
	private ReSendSMSThread resendSMSThread;
	
	private SendOfflineInfoThread sendOfflineInfoThread;
	/**
	 * 
	 * @param smsInfo
	 * @param currentDateTime
	 */
	public void addSMSInfo(SMSInfo smsInfo, java.sql.Timestamp currentDateTime){
        if(StringUtils.isEmpty(smsInfo.CustomerMobile) || smsInfo.WaterID == 0){
            return;
        }
        long l_timegap = NumberUtils.parseLong(ctrlParam.getSendSMSTimeGapInMs());
        if(l_timegap<0){
            l_timegap = 0;
        }
        SMSMobileInfo mobileInfo = null;
        //synchronized (SMSMobileInfo.class) { 
            mobileInfo = lastSentSMSMobile.get(smsInfo.CustomerMobile);
            if(mobileInfo == null){
                mobileInfo = new SMSMobileInfo();
                mobileInfo.setMobile(smsInfo.CustomerMobile);
                mobileInfo.setCount(0);
                lastSentSMSMobile.put(smsInfo.CustomerMobile, mobileInfo);
            }
        //}
        
        /////
        synchronized (mobileInfo) { 
            long lastSentTimestamp = mobileInfo.getLastSentTimestamp();
            if((currentDateTime.getTime()-lastSentTimestamp) >= l_timegap){
                lastSentTimestamp = currentDateTime.getTime();
            }else if(currentDateTime.getTime()-lastSentTimestamp>=0){
                long temp = l_timegap-(currentDateTime.getTime()-lastSentTimestamp);
                lastSentTimestamp += temp;
            }
            long count = mobileInfo.getCount();
            long scheduledTimeInMs = lastSentTimestamp+l_timegap*(count);
            java.sql.Timestamp ScheduledDateTime = new java.sql.Timestamp(scheduledTimeInMs);
            if(smsInfo.ScheduledDateTime==null || ScheduledDateTime.after(smsInfo.ScheduledDateTime)){
                smsInfo.ScheduledDateTime = ScheduledDateTime;
            }
            SMSInfo smsInfoOld = sendSMSInfoList.get(""+smsInfo.WaterID);
            if(smsInfoOld!=null){
                if(smsInfo.ScheduledDateTime.after(smsInfoOld.ScheduledDateTime)){//更新发送时间
                    sendSMSInfoList.put(""+smsInfo.WaterID, smsInfo);
                }
            }else{
                sendSMSInfoList.put(""+smsInfo.WaterID, smsInfo);
                mobileInfo.addCount();
            }
            System.out.println("****"+Constant.dateFromat.format(currentDateTime)+":"+smsInfo.PackageID+","+smsInfo.MsgType
                    +(l_timegap*(count))+","+Constant.dateFromat.format(smsInfo.ScheduledDateTime)+","+smsInfo.CustomerMobile+","+count+","+sendSMSInfoList.size());
        }
        
        return;
    }
	public void removeSMSInfo(SMSInfo smsInfo, java.sql.Timestamp currentDateTime, boolean sendFlag){
	    if(StringUtils.isEmpty(smsInfo.CustomerMobile) || smsInfo.WaterID == 0){
            return;
        }
	    SMSMobileInfo mobileInfo = null;
        //synchronized (SMSMobileInfo.class) { 
            mobileInfo = lastSentSMSMobile.get(smsInfo.CustomerMobile);
            if(mobileInfo == null){
                mobileInfo = new SMSMobileInfo();
                mobileInfo.setMobile(smsInfo.CustomerMobile);
                mobileInfo.setCount(0);
                mobileInfo.setLastSentTimestamp(currentDateTime.getTime());
                lastSentSMSMobile.put(smsInfo.CustomerMobile, mobileInfo);
            }
        //}
	    
	    synchronized (mobileInfo) { 
	        smsInfo = sendSMSInfoList.remove(""+smsInfo.WaterID);
	        if(smsInfo!=null){
                mobileInfo.subCount();
                if(sendFlag){//true 已发送 false 取消发送，
                    mobileInfo.setLastSentTimestamp(currentDateTime.getTime());
                }
                
	        }
	    }
	    System.out.println("----"+Constant.dateFromat.format(currentDateTime)+":"+smsInfo.PackageID+","+smsInfo.MsgType
                + ","+Constant.dateFromat.format(smsInfo.ScheduledDateTime)+","+smsInfo.CustomerMobile+","+mobileInfo.getCount()+","+sendSMSInfoList.size());
	    return;
	}
	public void removeLastSentSMSMobile(){
	    java.sql.Timestamp currentDateTime = GServer.getInstance().getCurrentDateTime();
	    
	    long l_timegap = NumberUtils.parseLong(ctrlParam.getSendSMSTimeGapInMs());
	    Set<String> keySet = lastSentSMSMobile.keySet();
	    //synchronized (SMSMobileInfo.class) { 
	        String mobile = "";
	        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
	            mobile = it.next();
	            SMSMobileInfo mobileInfo = lastSentSMSMobile.get(mobile);
	            if(mobileInfo == null){
	                continue;
	            }
	            //移除过期Mobile信息
	            long lastSentTimestamp = mobileInfo.getLastSentTimestamp();
	            if(lastSentTimestamp>0&&
	                    (currentDateTime.getTime()-lastSentTimestamp)>(l_timegap*2)){
	                mobileInfo = lastSentSMSMobile.remove(mobile);
	                mobileInfo = null;
	            }
	        }
	    //}
	}
	public Set<String> getSendSMSInfoList()
    {
        return sendSMSInfoList.keySet();
    }
	public SMSInfo getSendSMSInfo(String WaterID)
    {
        return sendSMSInfoList.get(WaterID);
    }
	public int getSendSMSInfoListSize()
    {
        return sendSMSInfoList.size();
    }
	public void clearSMSInfoList(){
	    lastSentSMSMobile.clear();
	    
	}
	/**
	 * 私有默认构造函数
	 */
	private SMSManager() {
		workerCount = apCfg.getWorkerCount();
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(workerCount);
		
		reminderThread = new ReminderThread();
		reminderThread.start();
		
		customerManagerThread = new CustomerManagerThread();
		customerManagerThread.start();
		
		//短信重发线程
		resendSMSThread = new ReSendSMSThread();
		resendSMSThread.start();
		
		sendOfflineInfoThread = new SendOfflineInfoThread();
		sendOfflineInfoThread.start();

		
		loadTemplate();

		lastSentSMSMobile = new ConcurrentHashMap<String, SMSMobileInfo>(workerCount);
		sendSMSInfoList   = new ConcurrentHashMap<String, SMSInfo>(workerCount);
	}

	private static class SingletonHolder {
		private static final SMSManager instance = new SMSManager();
	}

	/**
	 * 静态工厂方法，返还此类的惟一实例
	 * 
	 * @return a SMSManager instance
	 */
	public static SMSManager getInstance() {
		return SingletonHolder.instance;
	}
	
	public void loadTemplate() {
		try {
			shortMsgTemplate = Velocity.getTemplate("shortMsg.vm", g_Charset);
			expiredMsgTemplate = Velocity.getTemplate("expiredMsg.vm", g_Charset);//
			postmanPwdTemplate = Velocity.getTemplate("postmanPwd.vm", g_Charset);
			reminderTemplate = Velocity.getTemplate("reminderMsg.vm", g_Charset);
			reminderTemplate2 = Velocity.getTemplate("reminderMsg2.vm", g_Charset);//遗留物品取件提醒
			urgentSMSTemplate = Velocity.getTemplate("urgentPwd.vm", g_Charset);
			
			
			lockerAlarmSMSTemplate = Velocity.getTemplate("lockerAlarmMsg.vm", g_Charset);
			repairmanMsgTemplate   = Velocity.getTemplate("repairmanMsg.vm", g_Charset);
			businessPartnerPwdTemplate = Velocity.getTemplate("businessPartnerPwd.vm", g_Charset);
			operatorPwdTemplate        = Velocity.getTemplate("operatorInitPwd.vm", g_Charset);
			balanceMsgTemplate         = Velocity.getTemplate("balanceMsg.vm", g_Charset);
			topupMsgTemplate           = Velocity.getTemplate("topupMsg.vm", g_Charset);
					
		} catch (Exception e) {
			log.error("[load vm error]" + e.getMessage());
		}
		
		try{
		    customerAddressTemplate = Velocity.getTemplate("customerAddressMsg.vm", g_Charset);
            customerReminderActiveTemplate = Velocity.getTemplate("customerReminderActiveMsg.vm", g_Charset);
            customerReminderExtendTemplate = Velocity.getTemplate("customerReminderExtendMsg.vm", g_Charset);
            customerAppGuideTemplate = Velocity.getTemplate("customerAppGuide.vm", g_Charset);
            
            customerVerificationCodeTemplate = Velocity.getTemplate("verificationCodeMsg.vm", g_Charset);
		}catch (Exception e) {
            log.error("[load  customer vm error]" + e.getMessage());
        }
		try{
		    shortMsg4CounterTemplate = Velocity.getTemplate("shortMsg4Counter.vm", g_Charset);
            reminder4CounterTemplate = Velocity.getTemplate("reminderMsg4Counter.vm", g_Charset);
            expiredMsg4CounterTemplate = Velocity.getTemplate("expiredMsg4Counter.vm", g_Charset);//
		}catch (Exception e) {
            log.error("[load Counter vm error]" + e.getMessage());
        }
		try{
            pickupMsgTemplate = Velocity.getTemplate("pickupMsg.vm", g_Charset);
        }catch (Exception e) {
            log.error("[load pickupMsg.vm error]" + e.getMessage());
        }
	}
	/**
	 * 发送投递短消息
	 * 
	 * @param smsInfo
	 * @throws EduException
	 */
	public void sentDeliverySMS(SMSInfo smsInfo) throws EduException {
	    log.info("sentDeliverySMS Start:PID="+smsInfo.PackageID);
	    Date vaildDate = DateUtils.addDate(smsInfo.StoredTime, smsInfo.expireddays);
		VelocityContext context = new VelocityContext();
		context.put("packageid", smsInfo.PackageID);
		context.put("terminalName", smsInfo.TerminalName);
		context.put("msgtel", ctrlParam.getElockerServiceTel());
		//context.put("manmobile", smsInfo.PostmanID);
		context.put("location", smsInfo.Location);
		context.put("pwd", smsInfo.OpenBoxKey);
		context.put("lat", new java.text.DecimalFormat("###.000000").format(smsInfo.Latitude));
        context.put("lng", new java.text.DecimalFormat("###.000000").format(smsInfo.Longitude));
		context.put("trailermsg", smsInfo.TrailerMsg);
		context.put("ValidDate", Constant.dateFromatSa.format(vaildDate));

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
		    if(SysDict.TERMINAL_TYPE_COUNTER.equals(smsInfo.TerminalType)){
		        shortMsg4CounterTemplate.merge(context, writer);
		    }else{
		        shortMsgTemplate.merge(context, writer);
		    }
			

			msgContent = writer.toString();
			//System.out.println("DeliverySMS:"+msgContent);
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		smsInfo.MsgContent = msgContent;
		sentMessage(smsInfo);
	}
	
	/**
	 * 发送催领短消息
	 * @param smsInfo
	 * @throws EduException
	 */
	public void sentReminderSMS(SMSInfo smsInfo) throws EduException {
	    log.info("sentReminderSMS Start:PID="+smsInfo.PackageID);
	    Date vaildDate = DateUtils.addDate(smsInfo.StoredTime, smsInfo.expireddays);
		VelocityContext context = new VelocityContext();
		context.put("packageid", smsInfo.PackageID);
		context.put("terminalName", smsInfo.TerminalName);
		/*context.put("dropdays", smsInfo.dropdays);
		context.put("reminderdays", smsInfo.reminderdays);*/
		context.put("msgtel", ctrlParam.getElockerServiceTel());
		
		context.put("location", smsInfo.Location);
		context.put("pwd", smsInfo.OpenBoxKey);
		context.put("lat", new java.text.DecimalFormat("###.000000").format(smsInfo.Latitude));
		context.put("lng", new java.text.DecimalFormat("###.000000").format(smsInfo.Longitude));
		context.put("trailermsg", smsInfo.TrailerMsg);
		context.put("ValidDate", Constant.dateFromatSa.format(vaildDate));
		
		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
		    if(SysDict.TERMINAL_TYPE_COUNTER.equals(smsInfo.TerminalType)){
		        reminder4CounterTemplate.merge(context, writer);
		    }else{
		        reminderTemplate.merge(context, writer);
		    }

			msgContent = writer.toString();
			//System.out.println("ReminderSMS:"+msgContent);
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		smsInfo.MsgContent = msgContent;
		sentMessage(smsInfo);
	}

	/**
     * 发送遗留物品取件提醒
     * @param smsInfo
     * @throws EduException
     */
    public void sentReminderSMS2(SMSInfo smsInfo) throws EduException {
        log.info("sentReminderSMS2 Start:PID="+smsInfo.PackageID);
        Date vaildDate = DateUtils.addDate(smsInfo.StoredTime, smsInfo.expireddays);
        VelocityContext context = new VelocityContext();
        context.put("packageid", smsInfo.PackageID);
        context.put("terminalName", smsInfo.TerminalName);
        /*context.put("dropdays", smsInfo.dropdays);
        context.put("reminderdays", smsInfo.reminderdays);*/
        context.put("msgtel", ctrlParam.getElockerServiceTel());
        
        context.put("location", smsInfo.Location);
        context.put("pwd", smsInfo.OpenBoxKey);
        context.put("lat", new java.text.DecimalFormat("###.000000").format(smsInfo.Latitude));
        context.put("lng", new java.text.DecimalFormat("###.000000").format(smsInfo.Longitude));
        context.put("trailermsg", smsInfo.TrailerMsg);
        context.put("ValidDate", Constant.dateFromatSa.format(vaildDate));
        
        // 设置输出
        StringWriter writer = new StringWriter();
        String msgContent = "";

        try {
            // 将环境数据转化输出
            reminderTemplate2.merge(context, writer);

            msgContent = writer.toString();
            //System.out.println("ReminderSMS:"+msgContent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        }

        smsInfo.MsgContent = msgContent;
        sentMessage(smsInfo);
    }
	/**
	 * 发送逾期短消息
	 * 
	 * @param terminalName
	 * @param location
	 * @param packageid
	 * @param pwd
	 * @param mobile
	 * @throws EduException
	 */
	public void sentExpiredSMS(SMSInfo smsInfo) throws EduException {
	    log.info("sentExpiredSMS Start:PID="+smsInfo.PackageID);
		VelocityContext context = new VelocityContext();
		context.put("packageid", smsInfo.PackageID);
		context.put("location", smsInfo.Location);
		context.put("expireddays", ""+smsInfo.expireddays);
		context.put("terminalName", smsInfo.TerminalName);
		context.put("msgtel", ctrlParam.getElockerServiceTel());

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
		    if(SysDict.TERMINAL_TYPE_COUNTER.equals(smsInfo.TerminalType)){
		        expiredMsg4CounterTemplate.merge(context, writer);
		    }else{
		        expiredMsgTemplate.merge(context, writer);
		    }

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		smsInfo.MsgContent = msgContent;
		sentMessage(smsInfo);
	}
	/**
	 * 发送locker故障告警消息
	 * @param lockerid
	 * @param location
	 * @param mobile
	 * @throws EduException
	 */
	public void sentLockerAlarmSMS(String lockerid, String location, String alarm, String mobile) throws EduException {
		VelocityContext context = new VelocityContext();
		context.put("lockerid", lockerid);
		context.put("location", location);
		context.put("alarm", alarm);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			lockerAlarmSMSTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = mobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_LOCKERALARMSMS;
		sentMessage(smsInfo);
	}
	/**
     * 发送遗留物品取件提醒
     * @param smsInfo
     * @throws EduException
     */
    public void sentRepairmanMsg(SMSInfo smsInfo) throws EduException {
        log.info("sentRepairmanMsg Start:PID="+smsInfo.PackageID);
        VelocityContext context = new VelocityContext();
        context.put("packageid", smsInfo.PackageID);
        context.put("terminalNo", smsInfo.TerminalNo);
        context.put("terminalName", smsInfo.TerminalName);
        context.put("boxNo", smsInfo.BoxNo);
        
        context.put("location", smsInfo.Location);
        context.put("customerMobile", smsInfo.CustomerMobile);
        context.put("appealTime", Constant.dateFromat.format(smsInfo.sysDateTime));
        context.put("lat", new java.text.DecimalFormat("###.000000").format(smsInfo.Latitude));
        context.put("lng", new java.text.DecimalFormat("###.000000").format(smsInfo.Longitude));
        
        
        // 设置输出
        StringWriter writer = new StringWriter();
        String msgContent = "";

        try {
            // 将环境数据转化输出
            repairmanMsgTemplate.merge(context, writer);

            msgContent = writer.toString();
            //System.out.println("ReminderSMS:"+msgContent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        }

        smsInfo.MsgContent = msgContent;
        sentMessage(smsInfo);
    }
	/**
	 * 发送取件短消息
	 * 
	 * @param terminalName
	 * @param location
	 * @param packageid
	 * @param pwd
	 * @param mobile
	 * @throws EduException
	 */
	public void sentPickupSMS(SMSInfo smsInfo) throws EduException {
	    log.info("sentPickupSMS Start:PID="+smsInfo.PackageID);
		VelocityContext context = new VelocityContext();
	    context.put("packageid", smsInfo.PackageID);
	    context.put("terminalNo", smsInfo.TerminalNo);
	    context.put("terminalName", smsInfo.TerminalName);	        
	    context.put("location", smsInfo.Location);
	    context.put("customerMobile", smsInfo.CustomerMobile);	 
	    context.put("takeoutDate", Constant.dateFromat1Sa.format(smsInfo.TakeoutDateTime));
	    context.put("takeoutTime", Constant.timeFromat1.format(smsInfo.TakeoutDateTime));
	    // 设置输出
	    StringWriter writer = new StringWriter();
	    String msgContent = "";
	    try {
	         // 将环境数据转化输出
	        pickupMsgTemplate.merge(context, writer);
	        msgContent = writer.toString();
            //System.out.println("sentPickupSMS:"+msgContent);
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
	    }
	    smsInfo.MsgContent = msgContent;
	    smsInfo.MsgType = SMSInfo.MSG_TYPE_TAKEDOUT;
	    sentMessage(smsInfo);
	}
	
	/**
	 * 发送投递员注册短消息
	 * 
	 * @param pwd
	 * @param mobile
	 * @throws EduException
	 */
	public void sentPostManPwd(String pwd, String mobile)
			throws EduException {
		VelocityContext context = new VelocityContext();
		context.put("pwd", pwd);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			postmanPwdTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = mobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_REGISTER;

		sentMessage(smsInfo);
	}
	/**
	 * 发送BP添加密码短信短消息
	 * @param username
	 * @param pwd
	 * @param mobile
	 * @throws EduException
	 */
	public void sentBPInitPwd(String username, String pwd, String mobile)
			throws EduException {
		VelocityContext context = new VelocityContext();
		context.put("username", username);
		context.put("pwd", pwd);
		context.put("portalweb", ctrlParam.getElockerPortal());

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			businessPartnerPwdTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = mobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_BP_INIT_PWD;

		sentMessage(smsInfo);
	}
	/**
	 * 发送操作员初始密码短消息
	 * 
	 * @param pwd
	 * @param mobile
	 * @throws EduException
	 */
	public void sentOpterInitPwd(String pwd, String mobile)
			throws EduException {
		VelocityContext context = new VelocityContext();
		context.put("pwd", pwd);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			operatorPwdTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = mobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_OPTER_INIT_PWD;

		sentMessage(smsInfo);
	}
	/**
	 * 
	 * @param username
	 * @param datetime
	 * @param amount
	 * @param balance
	 * @param tradeno
	 * @param mobile
	 * @throws EduException
	 */
	public void sentTopUpMsg(String username, String datetime, double amount, double balance, String tradeno,String mobile)
			throws EduException {
		VelocityContext context = new VelocityContext();
		context.put("username", username);
		context.put("datetime", datetime);
		context.put("amount", amount);
		context.put("balance", balance);
		context.put("tradeno", tradeno);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			topupMsgTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = mobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_TOPUP_MSG;

		sentMessage(smsInfo);
	}
	public void sentBalanceMsg(String username, String datetime, double balance, String mobile)
			throws EduException {
		VelocityContext context = new VelocityContext();
		context.put("username", username);
		context.put("datetime", datetime);
		context.put("balance", balance);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			balanceMsgTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = mobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_TOPUP_MSG;

		sentMessage(smsInfo);
	}
	/**
	 * 发送紧急取件短信
	 * 
	 * @param pwd
	 * @param mobile
	 * @throws EduException
	 */
	public void sentUrgentSMS(String pwd, String customerMobile,String urgentMobile)
			throws EduException {
		VelocityContext context = new VelocityContext();
		context.put("pwd", pwd);
		context.put("mobile",customerMobile);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			urgentSMSTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = urgentMobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_SENDURGENTSMS;

		sentMessage(smsInfo);
	}

	/**
	 * 发送用户更新的身份验证短信
	 * @param pwd
	 * @param mobile
	 * @throws EduException
	 */
	public void sentUpdateVerificationCodeSMS(String pwd, String customerMobile)
			throws EduException {
		VelocityContext context = new VelocityContext();
		context.put("pwd", pwd);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			customerVerificationCodeTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = customerMobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_UPDATE_IDENTIFYINGCODE;

		sentMessage(smsInfo);
	}
	/**
	 * 发送静态短信：不加载模板
	 * @param msgContent
	 * @param mobile
	 * @throws EduException
	 */
	public void sentStaticSMS(String msgContent, String customerMobile,String umid)
			throws EduException {

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = customerMobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_STATIC;
    	smsInfo.UMID = umid;

		sentMessage(smsInfo);
	}
	/**
	 * 发送POBox地址短信
	 * @param mobile   客户手机
	 * @param validity
	 * @param pobox_address
	 * @throws EduException
	 */
	public String sendPOBoxAddressSMS(String mobile, String validity, String pobox_address) throws EduException{
		VelocityContext context = new VelocityContext();
		context.put("validity", validity);
		context.put("pobox_address",pobox_address);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			customerAddressTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		
		SMSInfo smsInfo = new SMSInfo();
    	smsInfo.MsgContent = msgContent;
    	smsInfo.CustomerMobile = mobile;
    	smsInfo.MsgType = SMSInfo.MSG_TYPE_NEW_POBOX_ADDRESS;

		sentMessage(smsInfo);
		return msgContent;
	}
	/**
	 * 发送提醒用户激活短信
	 * @param sendFlag  1-发送SMS，0-获取消息内容
	 * @param mobile   客户手机
	 * @param customerid 
	 * @param validity
	 * @throws EduException
	 */
	public String sendReminderActiveSMS(int sendFlag,String mobile, String customerid, String validity) throws EduException{
		VelocityContext context = new VelocityContext();
		context.put("validity", validity);
		context.put("customerid",customerid);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			customerReminderActiveTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		if(sendFlag==1){
			SMSInfo smsInfo = new SMSInfo();
	    	smsInfo.MsgContent = msgContent;
	    	smsInfo.CustomerMobile = mobile;
	    	smsInfo.MsgType = SMSInfo.MSG_TYPE_REMINDER_ACTIVE;

			sentMessage(smsInfo);
		}
		
		
		return msgContent;
	}
	/**
	 * 发送提醒用户延期短信
	 * @param sendFlag  1-发送SMS，0-获取消息内容
	 * @param mobile   客户手机
	 * @param customerid 
	 * @param validity
	 * @throws EduException
	 */
	public String sendReminderExtendSMS(int sendFlag, String mobile, String customerid, String validity) throws EduException{
		VelocityContext context = new VelocityContext();
		context.put("validity", validity);
		context.put("customerid",customerid);

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			customerReminderExtendTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
		}

		// System.out.println(msgContent);
		if(sendFlag==1){
			SMSInfo smsInfo = new SMSInfo();
	    	smsInfo.MsgContent = msgContent;
	    	smsInfo.CustomerMobile = mobile;
	    	smsInfo.MsgType = SMSInfo.MSG_TYPE_REMINDER_Extend;

			sentMessage(smsInfo);
		}
		
		return msgContent;
	}
	
	/**
	 * 获取柜体APP指南
	 * @param limitdays
	 * @throws EduException
	 */
	public String getApplcationGuide(int limitdays) throws EduException{
		String msgContent = "";
		if("1".equals(ctrlParam.getMessageTempletLoadMode())
				&& StringUtils.isNotEmpty(ctrlParam.getPoBoxApplicationGuide())){
			msgContent = ctrlParam.getPoBoxApplicationGuide();
			msgContent = msgContent.replaceAll("$limitdays", ""+limitdays);
		}else{
			VelocityContext context = new VelocityContext();
			context.put("limitdays", limitdays);
			
			// 设置输出
			StringWriter writer = new StringWriter();
			

			try {
				// 将环境数据转化输出
				customerAppGuideTemplate.merge(context, writer);

				msgContent = writer.toString();
			} catch (IOException e) {
				e.printStackTrace();
				throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
			}
		}
		
		return msgContent;
	}
	
	/**
	 * 启动线程发送短消息
	 * @param smsInfo
	 * @throws EduException
	 */
	private void sentMessage(SMSInfo smsInfo)
			throws EduException {
		executor.submit(new SMSWorker(smsInfo));
	}

	/**
	 * 发送催领短消息线程
	 * <p>Title: 智能自助包裹柜系统</p>
	 *
	 * <p>Description: </p>
	 *
	 * <p>Copyright: Copyright (c) 2014</p>
	 *
	 * <p>Company: 杭州东城电子有限公司</p>
	 *
	 * @author wangzl
	 * @version 1.0
	 */
	private class ReminderThread extends Thread
	{
		public void run() 
		{
			try
			{
				Thread.sleep(1000*60*5);
				System.out.println("Send Reminder Thread Begin --------------");
				while(true)
				{
					//只有主服务器才执行定期任务
					if(!GServer.getInstance().isMasterServer()){
						try
						{
							Thread.sleep(1000*60*10); //休眠10分钟
						}catch(InterruptedException ex){}
						continue;
					}
					Date nowDate = new Date();
					
					//6点以后，每小时启动
					if((DateUtils.getHour(nowDate) > 6 && DateUtils.getHour(nowDate)<18)
						&& "1".equalsIgnoreCase(ControlParam.getInstance().getSendReminderSMS()))//sendReminderSMS
					{
						//查询
						//System.out.println("send reminder msg begin --------------");
						
						int count = 0;
						InParamTBTerminalListQry inParam0 = new InParamTBTerminalListQry();
						RowSet rset = TBWebClientAdapter.doBusiness(inParam0);
						while(RowSetUtils.rowsetNext(rset))
						{
							InParamMBReminderMsgQry inParam1 = new InParamMBReminderMsgQry();
							inParam1.TerminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
							
							RowSet rset1 = MBWebClientAdapter.doBusiness(inParam1);
							while(RowSetUtils.rowsetNext(rset1))
							{
								try
								{
									InParamMBSendReminderMsg inParam2 = new InParamMBSendReminderMsg();
									inParam2.TerminalNo = RowSetUtils.getStringValue(rset1, "TerminalNo");
									inParam2.PackageID = RowSetUtils.getStringValue(rset1, "PackageID");
									
									MBWebClientAdapter.doBusiness(inParam2);
									count++;
									Thread.sleep(50);
								}catch(Exception e)
								{
									System.err.println("[ReminderThread business error] = " + e.getMessage());
								}
							}
							
						}
						System.out.println("send reminder msg count="+count);
						//System.out.println("send reminder msg end --------------");
					}
					
					try
					{
						Thread.sleep(1000*60*59); //休眠1小时
					}catch(InterruptedException ex)
					{
						
					}
				}
			}catch(Exception e)
			{
				System.err.println("[ReminderThread error] = " + e.getMessage());
			}
		}
	}
	
	/**
	 * Customer注册管理线程
	 * <p>Title: 智能自提柜系统</p>
	 *
	 * <p>Description: </p>
	 *
	 * <p>Copyright: Copyright (c) 2015</p>
	 *
	 * <p>Company: 杭州东城电子有限公司</p>
	 *
	 * @author zxy
	 * @version 1.0
	 */
	private class CustomerManagerThread extends Thread
	{
		private void doExpired(){
			Date nowDate = new Date();
			
			InParamIMCustomerQry inParam0 = new InParamIMCustomerQry();
			inParam0.Status = SysDict.CUSTOMER_STATUS_NORMAL;
			RowSet rset;
			try {
				rset = IMWebClientAdapter.doBusiness(inParam0);
				
				while(RowSetUtils.rowsetNext(rset)){
					//java.sql.Timestamp Validity = RowSetUtils.getTimestampValue(rset, "Validity");//
					Date Validity = RowSetUtils.getDateValue(rset, "Validity");//V_IMCustomer Validity-DATE
					if(nowDate.before(Validity)){
						continue;
					}
					try {
						InParamIMCustomerModStatusAuto inParam1 = new InParamIMCustomerModStatusAuto();
						inParam1.OperID = Constant.DEFAULT_SUPEROPERID;
						inParam1.CustomerID = RowSetUtils.getStringValue(rset, "CustomerID");
						inParam1.Mode = "1";
						IMWebClientAdapter.doBusiness(inParam1);
						
						Thread.sleep(50);
					}catch(Exception e)
					{
						System.err.println("[CustomerManagerThread business error] = " + e.getMessage());
					}
				}
			} catch (EduException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		private void doDelete(){
			try {
				InParamIMCustomerModStatusAuto inParam1 = new InParamIMCustomerModStatusAuto();
				inParam1.OperID = Constant.DEFAULT_SUPEROPERID;
				inParam1.CustomerID = "";
				inParam1.Mode = "0";//删除失效的Customer账号
				IMWebClientAdapter.doBusiness(inParam1);
				
			}catch(Exception e)
			{
				System.err.println("[CustomerManagerThread business error] = " + e.getMessage());
			}
		}
		
		public void run() 
		{
			try
			{
				Thread.sleep(1000*60*5);
				System.out.println("Customer Manager Thread Begin --------------");
				while(true)
				{
					//只有主服务器才执行定期任务
					if(!GServer.getInstance().isMasterServer()){
						try
						{
							Thread.sleep(1000*60*10); //休眠10分钟
						}catch(InterruptedException ex){}
						continue;
					}
					Date nowDate = new Date();
					
					if((DateUtils.getHour(nowDate) == 9 ))
					{
						//检查正常Customer的有效期，过期则更新状态，发送延期提醒短信。
						doExpired();
						//删除失效的Customer账号
						doDelete();
					}
					try
					{
						Thread.sleep(1000*60*59); //*60*2   休眠
					}catch(InterruptedException ex)
					{
						
					}
				}
			}catch(Exception e)
			{
				System.err.println("[CustomerManagerThread error] = " + e.getMessage());
			}
		}
	}
	/**
	 * 重发短消息线程(包括延迟发送功能)
	 * <p>Title: 智能自助包裹柜系统</p>
	 *
	 * <p>Description: </p>
	 *
	 * <p>Copyright: Copyright (c) 2015</p>
	 *
	 * <p>Company: 杭州东城电子有限公司</p>
	 *
	 * @author zxy
	 * @version 1.0
	 */
	private class ReSendSMSThread extends Thread
	{
	    private Set<String> GLockerSet = new HashSet<String>();
	    public ReSendSMSThread(){
	        GLockerSet.clear();
	    }
	    private void loadLockerToSet(){
	        try{
	            
	            InParamTBTerminalListQry inParam0 = new InParamTBTerminalListQry();
	            RowSet rset = TBWebClientAdapter.doBusiness(inParam0);
	            
	            GLockerSet.clear();
	            while(RowSetUtils.rowsetNext(rset))
	            {
	                String terminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
	                GLockerSet.add(terminalNo);
	            }
	        }catch(EduException e){
	        }
	    }
		private int doReSend() throws EduException{
			int count = 0;
			Iterator<String> it = GLockerSet.iterator();  
            while (it.hasNext()) {  
                String terminalNo = it.next();  
                
				//需要重发的为少数，先查询数量
				InParamMBPwdShortMsgQryCount inCount = new InParamMBPwdShortMsgQryCount();
				inCount.TerminalNo = terminalNo;
				inCount.SendStatus = "4";//1-Sending,4-Failure
				inCount.ReSendNum  = 5;//>=5次不再发送
				int num = MBWebClientAdapter.doBusiness(inCount);
				if(num<=0){
					continue;
				}
				String wateridLists = "";
				InParamMBPwdShortMsgQry inParam1 = new InParamMBPwdShortMsgQry();
				inParam1.TerminalNo = terminalNo;
				inParam1.SendStatus = "4";//1-Sending,4-Failure
				inParam1.ReSendNum  = 5;//>=5次不再发送
				RowSet rset1 = MBWebClientAdapter.doBusiness(inParam1);
				while(RowSetUtils.rowsetNext(rset1)){
					long waterid = RowSetUtils.getLongValue(rset1, "WaterID");
					if(StringUtils.isEmpty(wateridLists)){
						wateridLists += ""+waterid;
					}else{
						wateridLists += ","+waterid;
					}
				}
				if(StringUtils.isEmpty(wateridLists)){
					continue;
				}
				
				try
				{
					InParamMBPwdShortMsgReSend inParam2 = new InParamMBPwdShortMsgReSend();
					inParam2.WaterID = wateridLists;
					
					count += MBWebClientAdapter.doBusiness(inParam2);
					
					Thread.sleep(50);
				}catch(Exception e)
				{
					System.err.println("[ReSendSMSThread business error] = " + e.getMessage());
				}
				
			}
            System.out.println(new Date().toString()+" Resend sms count="+count);
			return count;
		}
		/**
		 * 短信发送超时
		 * @return
		 * @throws EduException
		 */
		private int pwdSMSTimeout() throws EduException{
		    
		    int count = 0;
		    java.sql.Timestamp currentDateTime = GServer.getInstance().getCurrentDateTime();
		    Iterator<String> it = GLockerSet.iterator();  
            while (it.hasNext()) {  
              String terminalNo = it.next();  
              //System.out.println(terminalNo);  
              InParamMBScheduleMsgQry inParam1 = new InParamMBScheduleMsgQry();
              inParam1.TerminalNo = terminalNo;
              RowSet rset1 = MBWebClientAdapter.doBusiness(inParam1);
              while(RowSetUtils.rowsetNext(rset1)){
                  SMSInfo smsInfo = new SMSInfo();
                  smsInfo.WaterID = RowSetUtils.getLongValue(rset1, "WaterID");
                  //smsInfo.CustomerMobile = RowSetUtils.getStringValue(rset1, "CustomerMobile");
                  //smsInfo.ScheduledDateTime = RowSetUtils.getTimestampValue(rset1, "ScheduleDateTime");
                  java.sql.Timestamp LastModifyTime = RowSetUtils.getTimestampValue(rset1, "LastModifyTime");
                  //超时未发送，修改发送状态：发送失败
                  if(LastModifyTime == null 
                     || ((currentDateTime.getTime()-LastModifyTime.getTime())>(1000*3600*12))){
                      try{
                          InParamMBModSMSSentStatus inParam = new InParamMBModSMSSentStatus();
                          inParam.WaterID = smsInfo.WaterID;
                          inParam.SendStatus = "4"; //发送失败
                          MBWebClientAdapter.doBusiness(inParam);
                          Thread.sleep(50);
                      }catch(EduException | InterruptedException e){
                          System.err.println("[SMS Timeout error] = " + e.getMessage());
                      }
                      count ++;
                  }
              }
            } 
            System.out.println(new Date().toString()+" SMS Timeout count="+count);
            return count;
		}
		/**
		 * 发送预定时间发送的短消息
		 * @return
		 * @throws EduException
		 */
		private int doScheduledSend() {
            int count = 0;
            try
            {
                Set<String> keySet = getSendSMSInfoList();
                
                String waterid = "";
                String wateridLists = "";
                int wateridListsNum = 0;
                java.sql.Timestamp currentDateTime = GServer.getInstance().getCurrentDateTime();
                for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
                    waterid = it.next();
                    SMSInfo smsInfo = getSendSMSInfo(waterid);
                    if(smsInfo != null){
                        if(smsInfo.WaterID==0 
                           || StringUtils.isEmpty(smsInfo.CustomerMobile) 
                           || smsInfo.ScheduledDateTime == null){
                            removeSMSInfo(smsInfo, currentDateTime, false);
                            continue;
                        }
                        if(currentDateTime.before(smsInfo.ScheduledDateTime)){
                            continue;
                        }
                        removeSMSInfo(smsInfo, currentDateTime, true);
                        
                        if(StringUtils.isEmpty(wateridLists)){
                            wateridLists = ""+smsInfo.WaterID;
                        }else{
                            wateridLists += ","+smsInfo.WaterID;
                        }
                        wateridListsNum++;
                        
                        if(StringUtils.isEmpty(wateridLists) 
                                || wateridListsNum<20){
                            continue;
                        }
                        try
                        {
                            InParamMBSendScheduleMsg inParam2 = new InParamMBSendScheduleMsg();
                            inParam2.WaterIDList = wateridLists;
                            count += MBWebClientAdapter.doBusiness(inParam2);
                            Thread.sleep(50);
                        }catch(Exception e)
                        {
                            System.err.println("[ScheduledSendThread business error] = " + e.getMessage());
                        }finally{
                            wateridListsNum = 0;
                            wateridLists = "";
                        }
                    }
                }
                
                if(StringUtils.isNotEmpty(wateridLists)){
                    try
                    {
                        InParamMBSendScheduleMsg inParam2 = new InParamMBSendScheduleMsg();
                        inParam2.WaterIDList = wateridLists;
                        count += MBWebClientAdapter.doBusiness(inParam2);
                    }catch(Exception e)
                    {
                        System.err.println("[ScheduledSendThread business error] = " + e.getMessage());
                    }finally{
                        wateridListsNum = 0;
                        wateridLists = "";
                    }
                }
            }catch(Exception e)
            {
                System.err.println("[ScheduledSendThread business error("+")] = " + e.getMessage());
            }
            return count;
            
        }
		private int getNextSendHour(int lasthour){
		    int nextSendHour = 0;
            if(lasthour<6){
                nextSendHour = 6;
            }else if(lasthour<10){
                nextSendHour = 10;
            }else if(lasthour<12){
                nextSendHour = 12;
            }else if(lasthour<14){
                nextSendHour = 14;
            }else if(lasthour<16){
                nextSendHour = 16;
            }else if(lasthour<18){
                nextSendHour = 18;
            }else if(lasthour<20){
                nextSendHour = 20;
            }else{
                nextSendHour = 6;
            }
            return nextSendHour;
		}
		public void run() 
		{
			try
			{
				Thread.sleep(1000*10);
				System.out.println("ReSend SMS Thread Begin --------------");
				
				//加载Locker
				loadLockerToSet();
				int nextSendHour = getNextSendHour(DateUtils.getHour(new Date()));
				
				if(GServer.getInstance().isMasterServer()){
				    pwdSMSTimeout();
				    
				}
				while(true)
				{
					//延迟短信发送
					doScheduledSend();

					Date nowDate = new Date();
					if(DateUtils.getHour(nowDate) ==nextSendHour){
					    nextSendHour = getNextSendHour(DateUtils.getHour(nowDate));
					    loadLockerToSet();
					    removeLastSentSMSMobile();
					    
					    if(GServer.getInstance().isMasterServer()){
					        pwdSMSTimeout();
		                    //发送失败的短信，每隔4小时重发一次(由主服务器发送)
		                    if("1".equalsIgnoreCase(ControlParam.getInstance().getSendReSendSMS())){
	                            doReSend();
	                        }
		                }
					}
					try
                    {
                        Thread.sleep(1000*60*5); //休眠
                    }catch(InterruptedException ex){}
				}
			}catch(Exception e)
			{
				System.err.println("[ReSend error] = " + e.getMessage());
			}
		}
	}
	/**
	 * 发送离线告警信息线程
	 * <p>Title: 智能自助包裹柜系统</p>
	 *
	 * <p>Description: </p>
	 *
	 * <p>Copyright: Copyright (c) 2014</p>
	 *
	 * <p>Company: 杭州东城电子有限公司</p>
	 *
	 * @author zhengxy
	 * @version 1.0
	 */
	private class SendOfflineInfoThread extends Thread
	{
		public void run() 
		{
			try
			{
				long delayInMs = (1000*60*Constant.LOCKER_OFFLINE_DELAY_MINUTES);//延迟10分钟，发送离线通知消息
				Thread.sleep(1000*60*5);//*60*2
				System.out.println("Send Offline Info Thread Begin --------------");
				while(true)
				{
					//只有主服务器才执行定期任务
					if(!GServer.getInstance().isMasterServer()){
						try
						{
							Thread.sleep(1000*60*10); //休眠10分钟
						}catch(InterruptedException ex){}
						continue;
					}
					//Date nowDate = new Date();
					
					//
					if(true)
					{
						InParamTBTerminalListQry inParam0 = new InParamTBTerminalListQry();
						inParam0.TerminalStatus = SysDict.TERMINAL_STATUS_FAULT_OFFLINE;//3-离线状态
						RowSet rset = TBWebClientAdapter.doBusiness(inParam0);
						while(RowSetUtils.rowsetNext(rset))
						{
							try
							{
								InParamMBSendOfflineInfo inParam1 = new InParamMBSendOfflineInfo();
								inParam1.TerminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
								
								MBWebClientAdapter.doBusiness(inParam1);
								Thread.sleep(50);
							}catch(Exception e)
							{
								System.err.println("[SendOfflineInfoThread business error] = " + e.getMessage());
							}
						}
					}
					
					try
					{
						Thread.sleep(delayInMs); 
					}catch(InterruptedException ex){}
				}
			}catch(Exception e)
			{
				System.err.println("[SendOfflineInfoThread error] = " + e.getMessage());
			}
		}
	}

}
