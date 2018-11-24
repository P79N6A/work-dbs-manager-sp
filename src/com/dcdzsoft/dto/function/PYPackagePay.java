package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PYPackagePay implements java.io.Serializable
{
	public java.sql.Date StoredDate;
	public String PartnerID = "";
	public String PartnerName = "";
	public String CardType = "";
	public String CardNo = "";
	public String ValidityPeriod = "";
	public String BatchID = "";
	public String RefNo = "";
	public String TransactionDate = "";
	public String TransactionType = "";
	public String TransactionAmt = "";
	public String TradeTerminalNo = "";
	public java.sql.Timestamp LastModifyTime;
	public String UploadFlag = "";
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}