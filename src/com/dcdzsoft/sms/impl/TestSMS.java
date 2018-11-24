package com.dcdzsoft.sms.impl;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.dcdzsoft.EduException;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.sms.ISMSProxy;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.RandUtils;

public class TestSMS {
	private static String physicalPath = "D:\\RD\\01.4SoftwareDept\\01.4.1ProductDevelopment\\04_BGG\\智能自助包裹柜标准版\\E_软件\\4_系统编码\\1_后台编码\\braches\\DBSManagerSA\\work\\";
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();

	public static void init() {

		apCfg.setPhysicalPath(physicalPath);
		try {
			apCfg.load(physicalPath+"WEB-INF\\appconfig.xml");

			Properties p = new Properties();
			p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					physicalPath + "WEB-INF/vm/");
			p.setProperty(Velocity.INPUT_ENCODING, "utf-8");
			p.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
			Velocity.init(p);

			//apCfg.setSmsCharset("UTF-8");
			SMSManager.getInstance().loadTemplate();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws EduException {
		// TODO Auto-generated method stub
		init();

		SMSInfo smsInfo = new SMSInfo();
		smsInfo.MsgType = 1;// 1-投递 2-逾期 3-催领 4; //取件 5; //重发投递 7; //注册验证码
		smsInfo.CustomerMobile = "502999922";
		// smsInfo.MsgContent =
		// "您的快递"+RandUtils.generateNumber(6)+"已送达杭海路1221号东城电子园区研发楼3楼自提柜,请凭提货密码"+RandUtils.generateNumber(6)+"取件。投递员电话:123456";
		//smsInfo.MsgContent = "Your parcel test20180411 has been delivered to LockerSTC, the pickup code 123456.";
		smsInfo.MsgContent = "SMSTESTSTC";//
		smsInfo.OpenBoxKey = RandUtils.generateNumber(4);
		smsInfo.TerminalName = "LockerSTC";
		smsInfo.PostmanID = "123456";
		smsInfo.BoxNo = "1";
		smsInfo.Location = "elocker";
		smsInfo.PackageID = "1701" + RandUtils.generateNumber(8);
		smsInfo.OfBureau = "33";
		apCfg.setGatewayUser("MySTC");
		apCfg.setGatewayPwd("smsgateway");
		//apCfg.setSmsMobilePrefix("001 ");
		ISMSProxy smsSender = new MsgProxyOurSMS4STC();
		
		String result = "";

		result = smsSender.sendMessage(smsInfo);
		System.out.println("result=" + result);
	}

}
