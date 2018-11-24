package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class APUserKeyMap implements java.io.Serializable
{
	public String KeyID = "";
	public String UserID = "";
	public String UserType = "";
	public String UserKey = "";
	public java.sql.Timestamp LastModifyTime;
	public java.sql.Timestamp CreateTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}