package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMCompany implements java.io.Serializable
{
	public String CompanyID = "";
	public String CompanyName = "";
	public String CompanyType = "";
	public String MasterFlag = "";
	public String Address = "";
	public String Email = "";
	public String Mobile = "";
	public String Feedback = "";
	public String LogoUrl = "";
	public String Slogan = "";
	public String SMS_Notification = "";
	public int ExpiredDays;
	public int ReminderDays;
	public String ReminderTime = "";
	public java.sql.Timestamp CreateTime;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}