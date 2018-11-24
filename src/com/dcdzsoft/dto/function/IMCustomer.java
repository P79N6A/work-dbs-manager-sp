package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMCustomer implements java.io.Serializable
{
	public String CustomerID = "";
	public String TerminalNo = "";
	public String CustomerName = "";
	public String IDCard = "";
	public String Address = "";
	public String Email = "";
	public String Mobile = "";
	public String Active = "";
	public String Status = "";
	public String ActiveCode = "";
	public java.sql.Timestamp Validity;
	public int keepMonths;
	public java.sql.Timestamp CreateTime;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}