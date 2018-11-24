package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMServiceCounter implements java.io.Serializable
{
	public String ServiceID = "";
	public String CounterType = "";
	public String CounterName = "";
	public int CntValue;
	public int CntMaxValue;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}