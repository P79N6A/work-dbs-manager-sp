package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMZone implements java.io.Serializable
{
	public String ZoneID = "";
	public String CompanyID = "";
	public String ZoneName = "";
	public String Description = "";
	public double CollectCharge;
	public int MandatoryFlag;
	public java.sql.Timestamp CreateTime;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}