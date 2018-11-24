package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DMItemLifeCycle implements java.io.Serializable
{
	public long WaterID;
	public String PackageID = "";
	public String ItemStatus = "";
	public int StatusTime;
	public String LastStatus = "";
	public String ZoneID = "";
	public String OperatorID = "";
	public String OperatorType = "";
	public int RecordLevel;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}