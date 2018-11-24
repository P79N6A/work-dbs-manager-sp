package com.dcdzsoft.email;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;


/**
 * 邮件发送器
 * @author zhengxy
 *
 */
public class EmailSender implements Callable<String> {
	private static final String BUSINESS_METHOD_NAME = "sendEmail";
	
	private EmailInfo mailInfo;
	public EmailSender(EmailInfo mailInfo){
		this.mailInfo = mailInfo;
	}
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		boolean isOk = doSend();
		
		if(isOk){
			//发送成功
			System.out.println("Send Email Successful.");
		}else{
		    System.out.println("Send Email Fail.");
		}
		
		return null;
	}
	
	private boolean doSend(){
		try{
			String format = this.mailInfo.getFormat();
			String proxyName = "EmailSenderProxy"+format.toUpperCase();
			Class<?> proxyClass = Class.forName(EmailSender.class.getPackage().getName() +".impl."+ proxyName);//PROXY_PACKAGE_PREFIX
			EmailSenderProxy senderProxy = (EmailSenderProxy)proxyClass.newInstance();
			
			Method method = proxyClass.getMethod(BUSINESS_METHOD_NAME, new Class[]{EmailInfo.class});
			
			Boolean result = (Boolean)method.invoke(senderProxy, new Object[]{mailInfo});
	        
			return result;
		 }catch(Exception e){
			e.printStackTrace();
		 }
		return false;
	}

}
