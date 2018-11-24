package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMServiceRate implements java.io.Serializable
{
	public String ServiceID = "";
	public String ServiceName = "";
	public double ServiceAmt;
	public double ServiceAmtSmall;
	public double ServiceAmtBig;
	public double ServiceAmtMed;
	public String ServicePrefix = "";
	public String ExtraServiceID = "";
	public String Active = "";
	public double ReturnAmt;
	public java.sql.Timestamp CreateTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}