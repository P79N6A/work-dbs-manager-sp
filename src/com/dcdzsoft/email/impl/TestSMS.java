package com.dcdzsoft.email.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.dcdzsoft.EduException;
import com.dcdzsoft.client.web.MBWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.dto.business.InParamMBSendBoxUsedEmail;
import com.dcdzsoft.email.EmailInfo;
import com.dcdzsoft.email.EmailSenderProxy;
import com.dcdzsoft.sms.ISMSProxy;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.RandUtils;

public class TestSMS {
   
    public static void main(String[] args) throws EduException {
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setFormat("HTML");
        
//        emailInfo.setMailServerHost("smtp.ym.163.com");
//        emailInfo.setMailServerPort("25");
//        emailInfo.setUserName("chenyufeng@hzdongcheng.cn");
//        emailInfo.setPassword("chenyufeng123");
//        emailInfo.setFromAddress("chenyufeng@hzdongcheng.cn");
//        emailInfo.setToAddress("chenyufeng@hzdongcheng.cn");
//        emailInfo.setSubject("格口使用统计情况");
//        emailInfo.setContent("chenyufeng@hzdongcheng.cn");
        
        emailInfo.setMailServerHost("smtpout.secureserver.net");
        emailInfo.setMailServerPort("25");
        emailInfo.setUserName("splocker@elaammal.com");
        emailInfo.setPassword("123456");
        emailInfo.setFromAddress("splocker@elaammal.com");
        emailInfo.setToAddress("chenyufeng@hzdongcheng.cn");
        emailInfo.setSubject("格口使用统计情况");
        emailInfo.setContent("chenyufeng@hzdongcheng.cn");
        
        EmailSenderProxy smsSender = new EmailSenderProxyHTML();
        Boolean result;
        result = smsSender.sendEmail(emailInfo);
        System.out.println("result="+result);
//        Date sysdate = new Date(System.currentTimeMillis());
//        System.out.println(sysdate.toString());
//        
//        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");//小写的mm表示的是分钟  
//        String dstr="06:00:00";  
//        java.util.Date date = null ;
//		try {
//			date = sdf.parse(dstr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//        System.out.println(date.toString());
//        
    }

}
