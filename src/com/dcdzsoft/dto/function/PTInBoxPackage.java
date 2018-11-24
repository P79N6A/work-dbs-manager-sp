package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PTInBoxPackage implements java.io.Serializable
{
	public String PackageID = "";
	public String TerminalNo = "";
	public String BoxNo = "";
	public String ItemStatus = "";
	public java.sql.Timestamp CreateTime;
	public String PPCID = "";
	public String ZoneID = "";
	public String CompanyID = "";
	public String RefNo = "";
	public String ParcelSize = "";
	public String CustomerID = "";
	public String CustomerName = "";
	public String CustomerAddress = "";
	public String CustomerMobile = "";
	public String DropAgentID = "";
	public String DynamicCode = "";
	public int DropNum;
	public java.sql.Date StoredDate;
	public java.sql.Timestamp StoredTime;
	public java.sql.Timestamp ExpiredTime;
	public java.sql.Timestamp ReminderDateTime;
	public String OpenBoxKey = "";
	public String DADFlag = "";
	public String TradeWaterNo = "";
	public String PosPayFlag = "";
	public String UploadFlag = "";
	public String ParcelStatus = "";
	public int PrintedFlag;
	public String ReportOrderID = "";
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}