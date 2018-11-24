package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMBusinessPartner implements java.io.Serializable
{
	public String BPartnerID = "";
	public String CollectZoneID = "";
	public String BPartnerName = "";
	public String Address = "";
	public String Email = "";
	public String Mobile = "";
	public String Username = "";
	public String Password = "";
	public double Balance;
	public String CreditFlag = "";
	public double CreditLimit;
	public int Discount;
	public String CollectionServiceFlag = "";
	public String ReturnServiceFlag = "";
	public double Longitude;
	public double Latitude;
	public java.sql.Timestamp CreateTime;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}