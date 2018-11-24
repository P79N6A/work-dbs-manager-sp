package com.dcdzsoft.sms.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.dcdzsoft.EduException;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.Constant;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.email.ApiFailCount;
import com.dcdzsoft.sms.ISMSProxy;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.ISMSProxy;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.util.DateUtils;
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
public class MsgProxyOurSMS4STC implements ISMSProxy {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(MsgProxyOurSMS4STC.class);
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	private static String apiString = "http://www.OurSms.net/api/sendsms.php";
    private static ApiFailCount counter = new ApiFailCount("SMS Gateway(www.oursms.net)");
    private static Template shortMsgEnTemplate = null;//英文模板短信
    private static Template pickupMsgEnTemplate = null;//英文模板短信
    static{
        try {
            shortMsgEnTemplate = Velocity.getTemplate("shortEnMsg.vm", "UNICODE");
            pickupMsgEnTemplate = Velocity.getTemplate("pickupEnMsg.vm","UNICODE");
        } catch (Exception e) {
            log.error("[load vm error]" + e.getMessage());
        }
    }
	@Override
	public String sendMessage(SMSInfo smsInfo) throws EduException{
	    log.info("sendMessage Start:PID="+smsInfo.PackageID+",MTP="+smsInfo.MsgType);
	    if(SMSInfo.MSG_TYPE_DELIVERY == smsInfo.MsgType 
	            || SMSInfo.MSG_TYPE_REMINDER == smsInfo.MsgType
	            || SMSInfo.MSG_TYPE_RESENT == smsInfo.MsgType
	            || SMSInfo.MSG_TYPE_TAKEDOUT == smsInfo.MsgType){
	        String result = _sendMessage(smsInfo);
	        //投递短信分两条发，一条阿拉伯文，一条英文
	        if("0".equals(result)){
	            try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
	            if(SMSInfo.MSG_TYPE_TAKEDOUT == smsInfo.MsgType){
	                result = _sentPickupSMSEn(smsInfo);
	            }else{
	                result = _sendDeliverySMSEn(smsInfo);
	            }
	            
	        }
	        return result;
	    }else{
	        return _sendMessage(smsInfo);
	    }
	    
	}
	
	
	
	/**
	 * 取件后短信-英文
	 * @return
	 * @throws EduException
	 */
	private String _sentPickupSMSEn(SMSInfo smsInfo) throws EduException {
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
            pickupMsgEnTemplate.merge(context, writer);
            msgContent = writer.toString();
            //System.out.println("DeliverySMS:"+msgContent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        }

        smsInfo.MsgContent = msgContent;
        
        return _sendMessage(smsInfo);
	}
	/**
	 * 投递后短信-英文
	 * @param smsInfo
	 * @return
	 * @throws EduException
	 */
	private String _sendDeliverySMSEn(SMSInfo smsInfo) throws EduException {
	    Date vaildDate = DateUtils.addDate(smsInfo.StoredTime, smsInfo.expireddays);
        VelocityContext context = new VelocityContext();
        context.put("packageid", smsInfo.PackageID);
        context.put("terminalName", smsInfo.TerminalName);
        context.put("msgtel", ControlParam.getInstance().getElockerServiceTel());
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
            shortMsgEnTemplate.merge(context, writer);
            msgContent = writer.toString();
            //System.out.println("DeliverySMS:"+msgContent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
        }

        smsInfo.MsgContent = msgContent;
        
        return _sendMessage(smsInfo);
	}
	
	private String _sendMessage(SMSInfo smsInfo) throws EduException {
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
        }/*else{
            //发送之前先检查短信发送状态
            result = getMessageStatus(smsInfo.UMID);
            if("Success".equals(result) || "Sent".equals(result)){//发送中或发送成功
                return result;
            }
        }*/
        String sender = apCfg.getGatewayField3();//sender
        if(StringUtils.isEmpty(sender)){
            sender = "STC Lockers";
        }
        try {
            sender=URLEncoder.encode(sender, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /*if(StringUtils.isNotEmpty(smsInfo.CompanyName)){
            sender = smsInfo.CompanyName;
        }*/
        //发送短信
        String url = apiString;
        String username  = "MySTC";  //账号apCfg.getGatewayUser()
        String password  = "smsgateway";  //密码apCfg.getGatewayPwd()
        String mobile    = smsInfo.CustomerMobile;    //发送的手机号
        String content   = smsInfo.MsgContent;   //内容
        
        try {
            content=URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String param ="username="+username+"&password="+password+"&message="+content+"&numbers="+mobile+"&sender="+sender+"&unicode=e&Rmduplicated=1&return=json";
        String ret   =  sendGet(url+"?"+param);
        if(ret.startsWith("{")&& ret.endsWith("}")){
            JSONObject retJson = JSONObject.fromObject(ret);
            if("100".equals(retJson.optString("Code"))){
                result = "0";
                log.info("SMS_PID="+smsInfo.PackageID+","+result+":"+ret);
            }else{
                result = "-555";
                log.error("SMS_PID="+smsInfo.PackageID+","+result+":"+ret);
            }
            
        }else{
            log.error("SMS_PID="+smsInfo.PackageID+","+result+":"+ret);
            result = "-444";
        }
        //System.out.println("ret="+ret);
        log.info("sendMessage result="+result+",PID="+smsInfo.PackageID+",MTP="+smsInfo.MsgType);
        return result;
    }
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        log.debug("url="+url);
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", 
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36"); //防止报403错误。

            // 建立实际的连接
            connection.connect();
            //设置相应请求时间
            connection.setConnectTimeout(30000);
            //设置读取超时时间
            connection.setReadTimeout(30000);
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }*/
            //System.out.println("响应时间--->" + map.get(null));
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error(e);
            return "发送GET请求出现异常！";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
