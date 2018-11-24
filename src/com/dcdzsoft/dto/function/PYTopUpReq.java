package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PYTopUpReq implements java.io.Serializable
{
	public String TradeWaterID = "";
	public double Amount;
	public String UserID = "";
	public String UserCode = "";
	public String Status = "";
	public java.sql.Timestamp CreateTime;
	public int TimeoutIns;
	public String Field1 = "";
	public String Field2 = "";
	public String Field3 = "";
	public String Field4 = "";
	public String Field5 = "";
	public String Field6 = "";
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}