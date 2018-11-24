package com.dcdzsoft.email;

 
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.sql.RowSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.dcdzsoft.EduException;
import com.dcdzsoft.client.web.MBWebClientAdapter;
import com.dcdzsoft.client.web.OPWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.dto.business.InParamMBReportEmailRecrQry;
import com.dcdzsoft.dto.business.InParamMBSendBoxUsedEmail;
import com.dcdzsoft.dto.business.InParamOPOperatorQry;
import com.dcdzsoft.dto.business.OutParamItemDetail;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.DateUtils;





/**
 * 邮件发送器管理 
 * @author zhengxy
 *
 */
public class EmailSenderManager  {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(EmailSenderManager.class);

	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	private static ControlParam ctrlParam = ControlParam.getInstance();
	
	private Template elockerPortalPwdEmailTemplate = null;
	private Template elockerFeedbackEmailTemplate = null;
	/**
	 * 工作的线程数
	 */
	private int workerCount = 80;
	private SendBoxUsedEmailThread sendBoxUsedEmailThread;

	private ThreadPoolExecutor executor;
	/**
     * 私有
     */
	private EmailSenderManager(){
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(workerCount);
		//发送格口使用统计邮件
		sendBoxUsedEmailThread = new SendBoxUsedEmailThread();
		sendBoxUsedEmailThread.start();
		
		
		loadTemplate();
	}
	private static class SingletonHolder {
		private static final EmailSenderManager instance = new EmailSenderManager();
	}

	/**
	 * 静态工厂方法，返还此类的惟一实例
	 * @return a EmailSenderManager instance
	 */
	public static EmailSenderManager getInstance() {
		return SingletonHolder.instance;
	}
	/**
	 * 启动线程发送邮件
	 * @param mailInfo
	 * @throws EduException
	 */
	private void sentMessage(EmailInfo mailInfo)
			throws Exception {
		executor.submit(new EmailSender(mailInfo));
	}
	
	public void loadTemplate() {
		try {
			elockerPortalPwdEmailTemplate = Velocity.getTemplate("elockerPortalPwdEmail.vm", "utf-8");
			elockerFeedbackEmailTemplate  = Velocity.getTemplate("elockerFeedbackEmail.vm", "utf-8");
		} catch (Exception e) {
			log.error("[load vm error]" + e.getMessage());
		}
	}
	/**   
      * 以文本格式发送邮件   
      * @param mailInfo 待发送的邮件的信息   
      */    
    public void sendTextMail(EmailInfo mailInfo) throws Exception{
    	mailInfo.setMailServerHost(apCfg.getEmailServerHost());    
		mailInfo.setMailServerPort(apCfg.getEmailServerPort());    
		mailInfo.setValidate(true);    
		mailInfo.setUserName(apCfg.getEmailUser());    
		mailInfo.setPassword(apCfg.getEmailPwd());
		mailInfo.setFromAddress(apCfg.getEmailAddress());
    	mailInfo.setFormat("TEXT");
    	log.info("EmailInfo:"+mailInfo.toString());
    	sentMessage(mailInfo);
    }    
       
    /**   
      * 以HTML格式发送邮件   
      * @param mailInfo 待发送的邮件信息   
      */    
    public void sendHtmlMail(EmailInfo mailInfo) throws Exception{   
    	mailInfo.setMailServerHost(apCfg.getEmailServerHost());    
		mailInfo.setMailServerPort(apCfg.getEmailServerPort());    
		mailInfo.setValidate(true);    
		mailInfo.setUserName(apCfg.getEmailUser());    
		mailInfo.setPassword(apCfg.getEmailPwd());
		mailInfo.setFromAddress(apCfg.getEmailAddress());
    	mailInfo.setFormat("HTML");
    	log.info("EmailInfo:"+mailInfo.toString());
    	sentMessage(mailInfo);
    }    
    /**
     * 发送丢失包裹的订单的详细信息
     * @param itemDetail
     * @throws EduException 
     */
    public static void sendMissingParcelDetailInfo(OutParamItemDetail itemDetail) throws EduException {
    	//#start
		EmailSenderManager sender = EmailSenderManager.getInstance();
		EmailInfo mailInfo = new EmailInfo();
		mailInfo.setToAddress(apCfg.getLegalDepartmentEmail()); //目的地址
		
		mailInfo.setSubject("Parcel Missing："+itemDetail.getItemCode()); 
		StringBuffer contentBuff = new StringBuffer();
		contentBuff.append("ItemCode:"+itemDetail.getItemCode()+"\n");
		contentBuff.append("CreateTime:"+itemDetail.getCreateTime()+"\n");
		contentBuff.append("From:\n<hr/>"+itemDetail.getFromBuff()+"\n");
		contentBuff.append("To:\n<hr/>"+itemDetail.getToBuff()+"\n");
		contentBuff.append("History:\n<hr/>"+itemDetail.getItemDetail()+"\n");
		mailInfo.setContent(contentBuff.toString().replaceAll("\n", "<br>"));
		
	    try {
			sender.sendHtmlMail(mailInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
		}
		//#end
    }
    
    /**
     * 发送柜体告警信息给维修中心
     * @param subjectMsg
     * @param lockerid
     * @param lockerInfo
     * @param alarmInfo
     * @param toEmailAddress
     * @throws EduException 
     */
    public static void sendTreminalAlarmInfo(String subjectMsg, String lockerid, String lockerInfo,String alarmInfo, String toEmailAddress) throws EduException{
    	
    	//#start 设置邮箱账户
		EmailSenderManager sender = EmailSenderManager.getInstance();
		EmailInfo mailInfo = new EmailInfo();
		mailInfo.setToAddress(toEmailAddress); //目的地址
		//#end
		
		//#start 设置邮件内容
		mailInfo.setSubject("Alarm Record: lockerid="+lockerid+" "+subjectMsg); 
		StringBuffer contentBuff = new StringBuffer();
		if(StringUtils.isNotEmpty(lockerInfo)){
			contentBuff.append("Locker Info \n<hr/>"+lockerInfo+"\n");
		}
		
		if(StringUtils.isNotEmpty(alarmInfo)){
			contentBuff.append("Alarm  Info \n<hr/>"+alarmInfo+"\n");
		}
		mailInfo.setContent(contentBuff.toString().replaceAll("\n", "<br>"));
		//#end
		
	    try {
			sender.sendHtmlMail(mailInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
		}
		
    }
    
    /**
     * 取件后发送反馈邮件
     * @param taketype
     * @param itemcode
     * @param storedtime
     * @param expiredtime
     * @param takedtime
     * @param takedImgUri
     * @param email
     * @throws EduException
     */
    public void sendFeedback(
    		String taketype,
    		String itemcode, 
    		String storedtime, 
    		String expiredtime,
    		String takedtime, 
    		String takedImgUri,
    		String email)throws EduException{
    	
    	VelocityContext context = new VelocityContext();
		context.put("taketype", taketype);
		context.put("itemcode", itemcode);
		context.put("storedtime", storedtime);
		context.put("expiredtime", expiredtime);
		context.put("takedtime", takedtime);
		
		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			elockerFeedbackEmailTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
		}

		
    	
    	//#start 设置邮箱账户
		EmailSenderManager sender = EmailSenderManager.getInstance();
		EmailInfo mailInfo = new EmailInfo();
		mailInfo.setToAddress(email); //目的地址
		//#end
		
		String filename = apCfg.getFullTerminalLogPath() + "/" + takedImgUri;
		if(new File(filename).exists()){
			msgContent += "<br>ImgUri="+apCfg.getImgServerUri() + takedImgUri;
			msgContent += "<img src='cid:0'><br>";
			String[] imgFileNames = {filename};
			mailInfo.setImgFileNames(imgFileNames);
		}
		//#start 设置邮件内容
		mailInfo.setSubject("Feedback from elocker system"); 
		mailInfo.setContent(msgContent.replaceAll("\n", "<br>"));
		//#end
		
	    try {
			sender.sendHtmlMail(mailInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
		}
    }
    
    /**
     * 发送门户网站的登录账户信息
     * @param bpname
     * @param username
     * @param pwd
     * @param bpemail
     * @throws Exception
     */
    public void sendWebPortalLoginAccount(String bpname, String username,String pwd, String bpemail)throws EduException{
    	
    	VelocityContext context = new VelocityContext();
		context.put("bpname", bpname);
		context.put("username", username);
		context.put("pwd", pwd);
		context.put("portalweb", ctrlParam.getElockerPortal());
		

		// 设置输出
		StringWriter writer = new StringWriter();
		String msgContent = "";

		try {
			// 将环境数据转化输出
			elockerPortalPwdEmailTemplate.merge(context, writer);

			msgContent = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
		}

		
    	
    	//#start 设置邮箱账户
		EmailSenderManager sender = EmailSenderManager.getInstance();
		EmailInfo mailInfo = new EmailInfo();
		mailInfo.setToAddress(bpemail); //目的地址
		//#end
		
		//#start 设置邮件内容
		mailInfo.setSubject("Account"); 
		mailInfo.setContent(msgContent.replaceAll("\n", "<br>"));
		//#end
		
	    try {
			sender.sendHtmlMail(mailInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
		}
    }
    
    /**
     * 发送API发送失败统计信息给API维护人员
     * @param subject
     * @param reportInfo
     * @throws EduException
     */

    public boolean sendApiMonitorReport(String subject, String reportInfo) throws EduException{
        
        String toEmailAddress = ctrlParam.getMonitorEmail();
        if(StringUtils.isEmpty(toEmailAddress)
                ||StringUtils.isEmpty(subject)
                ||StringUtils.isEmpty(reportInfo)){
            return false;
        }
        //#start 设置邮箱账户
        EmailSenderManager sender = EmailSenderManager.getInstance();
        EmailInfo mailInfo = new EmailInfo();
        mailInfo.setToAddress(toEmailAddress); //目的地址
        //#end
        
        //#start 设置邮件内容
        mailInfo.setSubject(subject); 
        StringBuffer contentBuff = new StringBuffer(reportInfo);
        mailInfo.setContent(contentBuff.toString().replaceAll("\n", "<br/>"));
        //#end
        
        try {
            sender.sendHtmlMail(mailInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
        }
        return true;
    }
    /**
     * 发送系统异常信息给系统维护人员
     * @param subject
     * @param reportInfo
     * @throws EduException
     */

    public boolean sendSystemErrorLog(String subject, String reportInfo) throws EduException{
        
        String toEmailAddress = ApplicationConfig.getInstance().getSystemServiceEmail();
        if(StringUtils.isEmpty(toEmailAddress)
                ||StringUtils.isEmpty(subject)
                ||StringUtils.isEmpty(reportInfo)){
            return false;
        }
        //#start 设置邮箱账户
        EmailSenderManager sender = EmailSenderManager.getInstance();
        EmailInfo mailInfo = new EmailInfo();
        mailInfo.setToAddress(toEmailAddress); //目的地址
        //#end
        
        //#start 设置邮件内容
        mailInfo.setSubject(subject); 
        StringBuffer contentBuff = new StringBuffer(reportInfo);
        mailInfo.setContent(contentBuff.toString().replaceAll("\n", "<br/>"));
        //#end
        
        try {
            sender.sendHtmlMail(mailInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EduException(ErrorCode.ERR_BUSINESS_SENDEMAILFAIL);
        }
        return true;
    }
	 /**
	 * 发送格口使用状况邮件线程
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
	private class SendBoxUsedEmailThread extends Thread
	{
		public void run() 
		{
			try
			{
	
				System.out.println("SendBoxUsedEmailThread Begin --------------");
				while(true)
				{
					Date nowDate = new Date();
					//if(DateUtils.getHour(nowDate) == 19){
						try
						{	
							InParamMBReportEmailRecrQry inParam1 = new InParamMBReportEmailRecrQry();
							InParamMBSendBoxUsedEmail inParam2   = new InParamMBSendBoxUsedEmail();
							inParam1.OperID = "SendBoxUsedEmail";//查询需要发送邮件管理员
							RowSet rset = MBWebClientAdapter.doBusiness(inParam1);
							while(RowSetUtils.rowsetNext(rset)){
								try {
									InParamOPOperatorQry inParam3 = new InParamOPOperatorQry();
									inParam3.OperID = "SendBoxUsedEmail";
									inParam3.UserID = RowSetUtils.getStringValue(rset, "OperID");
									RowSet rset2 = OPWebClientAdapter.doBusiness(inParam3);
									while(RowSetUtils.rowsetNext(rset2)){
										inParam2.OperID       = RowSetUtils.getStringValue(rset, "OperID");
										inParam2.EmailType    = RowSetUtils.getStringValue(rset, "EmailType");
										inParam2.SendAsRights = RowSetUtils.getStringValue(rset, "SendAsRights");
										inParam2.CreateTime   = RowSetUtils.getTimestampValue(rset, "CreateTime");
										inParam2.LastModifyTime=RowSetUtils.getTimestampValue(rset, "LastModifyTime");
										inParam2.ZoneID       = RowSetUtils.getStringValue(rset2, "ZoneID");
										inParam2.Mobile       = RowSetUtils.getStringValue(rset2, "Mobile");
										inParam2.Email        = RowSetUtils.getStringValue(rset2, "Email");
									}
									Thread.sleep(50);
								}catch(Exception e)
								{
									System.err.println("[GET Email Address business error] = " + e.getMessage());
									log.info("[GET Email Address business error] = " + e.getMessage());
								}
							}
							MBWebClientAdapter.doBusiness(inParam2);
							
						}catch(Exception e)
						{
							System.err.println("[SendBoxUsedEmailThread business error] = " + e.getMessage());
							log.info("[SendBoxUsedEmailThread business error] = " + e.getMessage());
						}
					//}
					System.out.println("SendBoxUsedEmailThread sleep --------------");
					try
					{							
						Thread.sleep(1000*60*59); //休眠1个小时
						//Thread.sleep(1000*60);//测试沉睡1分钟
					}catch(InterruptedException ex){}
				}
			}catch(Exception e)
			{
				System.err.println("[SendBoxUsedEmailThread error] = " + e.getMessage());
			}
		}
	}
}   