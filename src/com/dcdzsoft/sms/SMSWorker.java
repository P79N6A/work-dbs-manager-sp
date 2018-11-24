package com.dcdzsoft.sms;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;

import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.dto.business.InParamMBModSMSSentStatus;
import com.dcdzsoft.client.web.MBWebClientAdapter;

public class SMSWorker implements Callable<String> {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(SMSWorker.class);
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	
	private static final String PROXY_PACKAGE_PREFIX = "com.dcdzsoft.sms.impl.";
	private static final String BUSINESS_METHOD_NAME = "sendMessage";
	
	private static Class proxyClass = null;
	private static ISMSProxy smsProxy = null;
	private static Method method = null; 
	
	 static{
		 try
		 {
			 proxyClass = Class.forName(PROXY_PACKAGE_PREFIX + apCfg.getSendShortMsg());
			 smsProxy = (ISMSProxy)proxyClass.newInstance();
			 
			 method = proxyClass.getMethod(BUSINESS_METHOD_NAME, new Class[]{SMSInfo.class});
		 }catch(Exception e)
		 {
			 
		 }
	 }
	
	private SMSInfo smsInfo = null;

	public SMSWorker(SMSInfo smsInfo) {
		this.smsInfo = smsInfo;
	}

	// @Override
	public String call() throws Exception {
	    log.info("SMSWorker Start:PID="+smsInfo.PackageID+",MTP="+smsInfo.MsgType);
		//发送短消息
		/*if(smsInfo.MsgType != SMSInfo.MSG_TYPE_TAKEDOUT){*/
		    
			int isSentOK = 1;//-1,发送失败，0-发送中，1-发送成功
			
			isSentOK = sentSMS();
			
	        //结果入库
			if(smsInfo.MsgType == SMSInfo.MSG_TYPE_DELIVERY
					|| smsInfo.MsgType == SMSInfo.MSG_TYPE_REMINDER
					//|| smsInfo.MsgType == SMSInfo.MSG_TYPE_EXPIRED
					|| smsInfo.MsgType == SMSInfo.MSG_TYPE_RESENT)
			{
				InParamMBModSMSSentStatus inParam = new InParamMBModSMSSentStatus();
				inParam.WaterID = smsInfo.WaterID;
				
				if(isSentOK == 1)
					inParam.SendStatus = "2"; //发送成功
				else
					inParam.SendStatus = "4"; //发送失败
				
				MBWebClientAdapter.doBusiness(inParam);
			}
		/*}*/
		
		return "";
	}
	
	/**
	 * 
	 * @return -1,发送失败，0-发送中，1-发送成功
	 */
	private int sentSMS()
	{
		int isSentOK = 1;//-1,发送失败，0-发送中，1-发送成功
		
		//具体发送短消息
		try{
			Object result = method.invoke(smsProxy, new Object[]{smsInfo});
	        if(result == null){
	        	isSentOK = 1;
	        }else{
	        	if(("-222" .equals(result)
	        			|| "-333".equals(result) 
	        			|| "-444".equals(result) 
	        			|| "-555".equals(result) 
	        			|| "-666".equals(result)
	        			|| "-4".equals(result)
	        			|| "Fail".equals(result))){
	        		isSentOK = -1;
	        	}else if("Send" .equals(result)){
	        		isSentOK = 0;
	        	}else{
	        		isSentOK = 1;
	        	}
	        }
	        
		}catch(Exception e)
		{
			e.printStackTrace();
			
			isSentOK = -1;
		}
		
		return isSentOK;
	}
}
