package com.dcdzsoft.sms.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.email.ApiFailCount;
import com.dcdzsoft.sms.ISMSProxy;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.util.NumberUtils;

public class MsgProxyDcdzsoft implements ISMSProxy {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(MsgProxyDcdzsoft.class);
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	private static String apiString = "http://utf8.sms.webchinese.cn";
    private static ApiFailCount counter = new ApiFailCount("SMS Gateway(webchinese.cn)");
    
	public String sendMessage(SMSInfo smsInfo) throws EduException
	{
		String result = "";
		
		HttpClient client = new HttpClient();
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams(); 
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(10000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(10000);
		
        PostMethod post = new PostMethod(apiString);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
        NameValuePair[] data ={ new NameValuePair("Uid", apCfg.getSmsServerIp()),//apCfg.getGatewayUser()
        		                new NameValuePair("Key", apCfg.getSmsServerPort()),//apCfg.getGatewayPwd()
        		                new NameValuePair("smsMob",smsInfo.CustomerMobile),
        		                new NameValuePair("smsText",smsInfo.MsgContent)};

        try
        {
        	 //System.out.println(msgContent);

        	 post.setRequestBody(data);
        	 int statusCode = client.executeMethod(post);

        	 if(statusCode == HttpStatus.SC_OK)
        	 {
        		 result = new String(post.getResponseBodyAsString().getBytes("utf8"));
        		 
        		 //大于零短信数量
        		 System.out.println("sms result = " + result);
        	 }else
        	 {
        		 log.error("[statusCode]" + statusCode);
        		 throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        	 }
        }catch(Exception e)
        {
            counter.addFailMsg(e.getMessage());
        	e.printStackTrace();
        	throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        }
        finally
        {
        	post.releaseConnection();
        }
        
        return result;
	}
	public static boolean sendFailReport(){
        long thresholdCount = NumberUtils.parseLong(ControlParam.getInstance().getSendSMSFailReportCnt());
        counter.setThresholdCount(thresholdCount);
        counter.setApiString(apiString);
        boolean isOk = counter.sendFailReport();
        return isOk;
    }
}
