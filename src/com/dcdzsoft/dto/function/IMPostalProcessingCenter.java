package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMPostalProcessingCenter implements java.io.Serializable
{
	public String PPCID = "";
	public String CompanyID = "";
	public String PPCName = "";
	public String Username = "";
	public String Password = "";
	public String PPCServerIP = "";
	public String PPCServerURL = "";
	public String PPCServerUsername = "";
	public String PPCServerPassword = "";
	public java.sql.Timestamp CreateTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}