package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMEcommercePartner implements java.io.Serializable
{
	public String EPartnerID = "";
	public String EPartnerName = "";
	public String Address = "";
	public String Email = "";
	public String Mobile = "";
	public String Username = "";
	public String Password = "";
	public String Active = "";
	public java.sql.Timestamp CreateTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}