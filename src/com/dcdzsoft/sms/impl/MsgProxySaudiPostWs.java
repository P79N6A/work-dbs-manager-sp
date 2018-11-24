package com.dcdzsoft.sms.impl;

import java.rmi.RemoteException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.email.ApiFailCount;
import com.dcdzsoft.sms.ISMSProxy;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.ISMSProxy;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.impl.ServiceStub.SendSMS;
import com.dcdzsoft.sms.impl.ServiceStub.SendSMSResponse;
import com.dcdzsoft.util.NumberUtils;

/**
 * 
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: http://smsc.sahara.com/webService/webService.php</p>
 *
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author zhengxy
 * @version 1.0
 */
public class MsgProxySaudiPostWs implements ISMSProxy {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(MsgProxySaudiPostWs.class);
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	private static String apiString = "http://ws.sp.com.sa/sendsms_stc/service.asmx";
    private static ApiFailCount counter = new ApiFailCount("SMS Gateway(ws.sp.com.sa)");
	@Override
	public String sendMessage(SMSInfo smsInfo) throws EduException 
	{
		String result = "";
		
		if(smsInfo.CustomerMobile.startsWith("0")){
			smsInfo.CustomerMobile = smsInfo.CustomerMobile.substring(1);
		}
		if(!smsInfo.CustomerMobile.startsWith("966")){//沙特手机号国际编码
			smsInfo.CustomerMobile = "966"+smsInfo.CustomerMobile;
		}
		if(StringUtils.isEmpty(smsInfo.UMID)){
			UUID uuid = UUID.randomUUID();
			smsInfo.UMID = uuid.toString();
		}
		String sender = apCfg.getGatewayField2();//sender
		if("SaudiPostSenders".equalsIgnoreCase(sender) && StringUtils.isNotEmpty(smsInfo.CompanyName)){
		    sender = smsInfo.CompanyName;
		}
		StringBuffer logBuff = new StringBuffer();
		logBuff.append("SendSMS:"+smsInfo.CustomerMobile+"["+smsInfo.PackageID+"],UMID="+smsInfo.UMID);
		//发送短信
		try {
            ServiceStub stub = new ServiceStub(apiString);
            stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
            
            SendSMS sendSMS0 = new SendSMS();
            sendSMS0.setUserName(apCfg.getGatewayUser());//registered mobile instead of account username
            sendSMS0.setPassword(apCfg.getGatewayPwd());
            sendSMS0.setMessage(smsInfo.MsgContent);
            sendSMS0.setMobileno(smsInfo.CustomerMobile);
            SendSMSResponse response =  stub.sendSMS(sendSMS0);

            result = response.getSendSMSResult();
            logBuff.append(",Result=").append(result);
            
            if(!"1".equals(result)){
                result ="Fail";
                counter.addFailMsg(result);
            }
            if("1".equals(ControlParam.getInstance().getPrintfSMSContent())){
                logBuff.append(",").append(smsInfo.MsgContent);
            }
            log.info(logBuff.toString());
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            logBuff.append(",Result=").append(e.getMessage());
            log.error(logBuff.toString());
            counter.addFailMsg(e.getMessage());
            e.printStackTrace();
            throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        } catch (Exception e){
            logBuff.append(",Result=").append(e.getMessage());
            log.error(logBuff.toString());
            counter.addFailMsg(e.getMessage());
            e.printStackTrace();
            throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        }
        
        return result;
    }
    public static void main(String[] args) throws EduException {
        // TODO Auto-generated method stub
        System.out.println("MsgProxy test");
        
        try {
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	public static boolean sendFailReport(){
	    
	    long thresholdCount = NumberUtils.parseLong(ControlParam.getInstance().getSendSMSFailReportCnt());
	    counter.setThresholdCount(thresholdCount);
        counter.setApiString(apiString);
        boolean isOk = counter.sendFailReport();
        return isOk;
    }
}
