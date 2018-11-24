package com.dcdzsoft.sms.impl;

import java.util.UUID;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
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
import com.dcdzsoft.util.NumberUtils;

/**
 * 
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: http://www.wavecell.com/</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class MsgProxyWaveCell implements ISMSProxy {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(MsgProxyWaveCell.class);
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	private static String apiString = "http://wms1.wavecell.com/Send.asmx/SendMT";
    private static ApiFailCount counter = new ApiFailCount("SMS Gateway(wavecell.com)");
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
		}else{
			//发送之前先检查短信发送状态
			result = getMessageStatus(smsInfo.UMID);
			if("Success".equals(result) || "Sent".equals(result)){//发送中或发送成功
				return result;
			}
		}
		String sender = apCfg.getGatewayField2();//sender
        if(StringUtils.isNotEmpty(smsInfo.CompanyName)){
            sender = smsInfo.CompanyName;
        }
		//发送短信
		HttpClient client = new HttpClient();
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams(); 
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(10000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(10000);
		//String apiString = apCfg.getGatewayUser();//"http://api.otsdc.com/rest/Messages/Send";
		//String appSid    = apCfg.getGatewayPwd();//"bovuv8X_BCkPErfdm6N8Zq9elDjO98";
        PostMethod post  = new PostMethod(apiString);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//utf8在头文件中设置转码
        NameValuePair[] data ={ new NameValuePair("AccountId", apCfg.getGatewayUser()),//
        						new NameValuePair("SubAccountId", apCfg.getGatewayField1()),
        						new NameValuePair("Password", apCfg.getGatewayPwd()),//
        						new NameValuePair("Destination", smsInfo.CustomerMobile),//The destination phone number (MSISDN) to send to.
        		                new NameValuePair("Source",sender),//apCfg.getGatewayField2()
        		                new NameValuePair("Body",smsInfo.MsgContent),//
        		                new NameValuePair("Encoding","UNICODE"),//utf8
        		                new NameValuePair("ScheduledDateTime",""),//"2012-29-08T17:00:00" is accepted in UTC time.
        		                new NameValuePair("UMID",smsInfo.UMID)//Specify this parameter blank. For example: UMID=""
        		                };

        try
        {
        	 //System.out.println(msgContent);

        	 post.setRequestBody(data);
        	 int statusCode = client.executeMethod(post);

        	 if(statusCode == HttpStatus.SC_OK)
        	 {
        		 /*java.util.regex.Pattern p_xml;
 		         java.util.regex.Matcher m_xml;
 		          String regEx_xml = "<(?).*?>"; //XML标签的正则表达式
        		 result = new String(post.getResponseBodyAsString().getBytes("utf8"));
        		 
        		 //<string xmlns="http://wavecell.com/">RECEIVED:6d4ca310-4c03-4c21-8401-756146dbb5f3</string>
        		 //
    		     p_xml = Pattern.compile(regEx_xml,Pattern.CASE_INSENSITIVE);
    		     m_xml = p_xml.matcher(result);
    		     result = m_xml.replaceAll(""); //过滤XML标签
    		     result = result.replace("\n", "").trim();*/
        		 //休眠，获取消息状态
        		 Thread.sleep(1000*60);
        		 result = getMessageStatus(smsInfo.UMID);
        		 log.info("SendSMS:"+smsInfo.CustomerMobile+"["+smsInfo.PackageID+"],UMID="+smsInfo.UMID+",Result="+result);
        		 if("1".equals(ControlParam.getInstance().getPrintfSMSContent())){
        			 System.out.println("SendSMS:"+smsInfo.CustomerMobile+"["+smsInfo.PackageID+"],UMID="+smsInfo.UMID+",Result="+result+","+smsInfo.MsgContent);
        		 }
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
	
	public String getMessageStatus(String UMID){
		String result = "";
		HttpClient client = new HttpClient();
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams(); 
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(5000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(5000);
		//String apiString = apCfg.getGatewayUser();//"http://api.otsdc.com/rest/Messages/Send";
		//String appSid    = apCfg.getGatewayPwd();//"bovuv8X_BCkPErfdm6N8Zq9elDjO98";
        PostMethod post  = new PostMethod("http://wms1.wavecell.com/getDLRApi.asmx/SMSDLR");
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
        NameValuePair[] data ={ new NameValuePair("AccountId", apCfg.getGatewayUser()),//
        						new NameValuePair("SubAccountId", apCfg.getGatewayField1()),
        						new NameValuePair("Password", apCfg.getGatewayPwd()),//
        		                new NameValuePair("UMID",UMID)//Specify this parameter blank. For example: UMID=""
        		                };
        try
        {
        	 //System.out.println(msgContent);

        	 post.setRequestBody(data);
        	 int statusCode = client.executeMethod(post);

        	 if(statusCode == HttpStatus.SC_OK)
        	 {
        		 java.util.regex.Pattern p_xml;
 		         java.util.regex.Matcher m_xml;
 		          String regEx_xml = "<(?).*?>"; //XML标签的正则表达式
        		 result = new String(post.getResponseBodyAsString().getBytes("utf8"));
        		 
        		 //<string xmlns="http://wavecell.com/">RECEIVED:6d4ca310-4c03-4c21-8401-756146dbb5f3</string>
        		 //
    		     p_xml = Pattern.compile(regEx_xml,Pattern.CASE_INSENSITIVE);
    		     m_xml = p_xml.matcher(result);
    		     result = m_xml.replaceAll(""); //过滤XML标签
    		     result = result.replace("\n", "");
    		     //result = result.replaceAll(" ", "");
    		     result = result.replace("\r", "").trim();
    		     System.out.println("result=" + result);
    		     log.info("GetMessageStatus:UMID="+UMID+",Result="+result);
    		     if(result.indexOf(UMID)>=0 && result.indexOf("DELIVERED TO DEVICE")>=0){//apCfg.getGatewayField3()
    		    	 result = "Success";
    		     }else if(result.indexOf("SENT")>=0 ||result.indexOf("DELIVERED TO CARRIER")>=0){
    		    	 result = "Sent";
    		     }else{
    		    	 result = "Fail";
    		     }
        		 //大于零短信数量
        	 }else
        	 {
        		 log.error("[statusCode]" + statusCode);
        		 throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        	 }
        }catch(Exception e)
        {
        	result = "Fail";
        	//e.printStackTrace();
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
