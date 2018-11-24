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
import com.dcdzsoft.sms.impl.WebServiceStub.AvailableSenderArr;
import com.dcdzsoft.sms.impl.WebServiceStub.BalanceArr;
import com.dcdzsoft.sms.impl.WebServiceStub.BalanceSMS;
import com.dcdzsoft.sms.impl.WebServiceStub.BalanceSMSResponse;
import com.dcdzsoft.sms.impl.WebServiceStub.GetSenderAvailability;
import com.dcdzsoft.sms.impl.WebServiceStub.*;
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
public class MsgProxySaharaWs implements ISMSProxy {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(MsgProxySaharaWs.class);
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	private static String apiString = "http://smsc.sahara.com/webService/webService.php";
    private static ApiFailCount counter = new ApiFailCount("SMS Gateway(sahara.com)");
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
            WebServiceStub stub = new WebServiceStub(apiString);
            stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
            SendSMS sendSMS0 = new SendSMS();
            SendSMSArr param = new SendSMSArr();
            param.setMobile(apCfg.getGatewayUser());//registered mobile instead of account username
            param.setPassword(apCfg.getGatewayPwd());
            param.setSender(sender);//
            param.setNumbers(smsInfo.CustomerMobile);
            param.setMsg(smsInfo.MsgContent);//
            param.setMsgId(smsInfo.UMID);//
            sendSMS0.setArrayData(param);
            SendSMSResponse response =  stub.sendSMS(sendSMS0);

            result = response.get_return();
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
    public static void checkSender(String sender){
      
        try {
            WebServiceStub stub = new WebServiceStub(apiString);
            stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
            CheckSender checkSender12 = new CheckSender();
            CheckSenderArr param = new CheckSenderArr();
            param.setMobile("966501793475");//apCfg.getGatewayUser() registered mobile instead of account username
            param.setPassword("b$_2o!6");//apCfg.getGatewayPwd()
            param.setSender(sender);
            checkSender12.setArrayData(param );
            com.dcdzsoft.sms.impl.WebServiceStub.CheckSenderResponse response= stub.checkSender(checkSender12 );

            String result = response.get_return();//1-已添加  6-不存在
            
            System.out.println("checkSender:"+sender+",Result="+result);
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void addSender(String sender){
        
        try {
            WebServiceStub stub = new WebServiceStub(apiString);
            stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
            AddSender addSender2 = new AddSender();
            AddSenderArr param = new AddSenderArr();
            param.setMobile("966501793475");//apCfg.getGatewayUser() registered mobile instead of account username
            param.setPassword("b$_2o!6");//apCfg.getGatewayPwd()
            param.setSender(sender);
            param.setSiteSender("NEW SMS");///?????
            addSender2.setArrayData(param);
            com.dcdzsoft.sms.impl.WebServiceStub.AddSenderResponse response= stub.addSender(addSender2 );

            String result = response.get_return();//1-已添加  6-不存在  7-SiteSender不存在
            
            System.out.println("addSender:"+sender+",Result="+result);
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void getSenderAvailability(){
        
        try {
            WebServiceStub stub = new WebServiceStub(apiString);
            stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
            AvailableSenderArr param = new AvailableSenderArr();
            param .setMobile("966501793475");//apCfg.getGatewayUser() registered mobile instead of account username
            param.setPassword("b$_2o!6");//apCfg.getGatewayPwd()
            GetSenderAvailability getSenderAvailability8 = new GetSenderAvailability();
            getSenderAvailability8.setArrayData(param);
            com.dcdzsoft.sms.impl.WebServiceStub.GetSenderAvailabilityResponse response= stub.getSenderAvailability(getSenderAvailability8);

            String result = response.get_return();//
            
            System.out.println("getSenderAvailability:"+result);
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void balanceSMS(){
        
        try {
            WebServiceStub stub = new WebServiceStub(apiString);
            stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);

            BalanceArr param = new BalanceArr();
            param .setMobile("966501793475");//apCfg.getGatewayUser() registered mobile instead of account username
            param.setPassword("b$_2o!6");//apCfg.getGatewayPwd()
            BalanceSMS balanceSMS4 = new BalanceSMS();
            balanceSMS4.setArrayData(param);
            BalanceSMSResponse response= stub.balanceSMS(balanceSMS4 );

            String result = response.get_return();//1-已添加  6-不存在
            
            System.out.println("balanceSMS:"+result);
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws EduException {
        // TODO Auto-generated method stub
        System.out.println("MsgProxySaharaWs test");
        balanceSMS();
        getSenderAvailability();
        //checkSender("DHL");
        addSender("DHL");
        checkSender("DHL");
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
