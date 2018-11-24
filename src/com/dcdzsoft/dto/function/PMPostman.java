package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PMPostman implements java.io.Serializable
{
	public String PostmanID = "";
	public String ZoneID = "";
	public String CollectZoneID = "";
	public String PostmanType = "";
	public String PostmanName = "";
	public String Password = "";
	public String PlainPassword = "";
	public String BindCardID = "";
	public String BindMobile = "";
	public String IDCard = "";
	public String OfficeTel = "";
	public String Mobile = "";
	public String Email = "";
	public String ContactAddress = "";
	public String InputMobileFlag = "";
	public String PostmanStatus = "";
	public String DropRight = "";
	public String ReturnRight = "";
	public String CollectRight = "";
	public String DADRight = "";
	public String CreateManID = "";
	public java.sql.Date CreateDate;
	public java.sql.Timestamp CreateTime;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}