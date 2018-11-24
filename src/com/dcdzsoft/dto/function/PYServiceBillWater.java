package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PYServiceBillWater implements java.io.Serializable
{
	public String BPartnerID = "";
	public String TradeWaterID = "";
	public java.sql.Timestamp BillTime;
	public String BillType = "";
	public String BillDetails = "";
	public double BillAmt;
	public double Balance;
	public String PackageID = "";
	public String ServiceID = "";
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}