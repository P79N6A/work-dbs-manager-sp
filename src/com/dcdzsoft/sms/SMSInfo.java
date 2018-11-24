package com.dcdzsoft.sms;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.naming.java.javaURLContextFactory;

public class SMSInfo  implements java.io.Serializable{
	public final static int MSG_TYPE_DELIVERY = 1; //投递
	public final static int MSG_TYPE_EXPIRED = 2; //逾期
	public final static int MSG_TYPE_REMINDER = 3; //催领
	public final static int MSG_TYPE_TAKEDOUT = 4; //取件
	public final static int MSG_TYPE_RESENT = 5;   //重发投递
	public final static int MSG_TYPE_REMINDER2 = 6; //通知通过远程求助取走遗留在箱中的物品
	public final static int MSG_TYPE_REGISTER = 8; //投递员注册
	public final static int MSG_TYPE_SENDURGENTSMS = 9; //紧急取件短信
	
	public final static int MSG_TYPE_LOCKERALARMSMS = 11; //柜体告警短信
	
	public final static int MSG_TYPE_STATIC            = 20;//静态短信，不加载模板
	public final static int MSG_TYPE_NEW_POBOX_ADDRESS = 21;//新的POBox地址
	public final static int MSG_TYPE_REMINDER_ACTIVE   = 22;//提醒用户注册
	public final static int MSG_TYPE_REMINDER_Extend   = 23;//提醒用户延长使用期
	public final static int MSG_TYPE_UPDATE_IDENTIFYINGCODE = 24;//提醒用户更新信息的验证码短信
	
	public final static int MSG_TYPE_OPTER_INIT_PWD = 31;//操作员初始密码短信
	public final static int MSG_TYPE_BP_INIT_PWD    = 32;//BP密码短信
	
	public final static int MSG_TYPE_TOPUP_MSG      = 35;//充值短信
	public final static int MSG_TYPE_BALANCE_MSG    = 36;//余额短信
	
	
	public long WaterID = 0L;
	public String PackageID = "";
	public java.sql.Timestamp StoredTime;
	public java.sql.Timestamp sysDateTime;
	public String TerminalNo = "";
	public String TerminalName = "";
	public String TerminalType = "";//设备类型 0-包裹柜  1-包裹柜台
	public String BoxNo = "";
	public String OfBureau = ""; //所属投递段
	public String Location = "";
	public String ZoneID = "";
	public String CustomerMobile = "";
	public String CustomerName = "";
	public String OpenBoxKey = "";
	public String ItemStatus = "";
	public String PostmanID = "";
	public String PostmanName = "";
	public String CompanyID = "";
	public String CompanyName = ""; 
	//public int reminderdays = 0;//催领间隔
	public int expireddays  = 0;//逾期天数
	//public int dropdays     = 0;//投递到箱的天数
	public String MsgContent = "";
	public String Remark = "";
	public double Latitude = 0.0;
	public double Longitude = 0.0;
	public String TrailerMsg = "";
	public String UMID = "";//unique messageID
	
	public java.sql.Timestamp ScheduledDateTime=null;
	public java.sql.Timestamp TakeoutDateTime = null;
	public int MsgType = 0;
	
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
