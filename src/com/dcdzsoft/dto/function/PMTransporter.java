package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PMTransporter implements java.io.Serializable
{
	public String TransporterID = "";
	public String TransporterName = "";
	public String ActiveFlag = "";
	public String Mobile = "";
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}