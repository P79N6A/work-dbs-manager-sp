package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PTDeliverHistory implements java.io.Serializable
{
	public String PackageID = "";
	public java.sql.Timestamp CreateTime;
	public String ItemStatus = "";
	public String RunStatus = "";
	public String PPCID = "";
	public String ZoneID = "";
	public String CompanyID = "";
	public String RefNo = "";
	public String CustomerID = "";
	public String ParcelSize = "";
	public String CustomerName = "";
	public String CustomerAddress = "";
	public String CustomerMobile = "";
	public String TerminalNo = "";
	public String BoxNo = "";
	public String DropAgentID = "";
	public String DynamicCode = "";
	public java.sql.Date StoredDate;
	public java.sql.Timestamp StoredTime;
	public java.sql.Timestamp ExpiredTime;
	public String OpenBoxKey = "";
	public String TakedWay = "";
	public java.sql.Timestamp TakedTime;
	public String ReturnAgentID = "";
	public String DADFlag = "";
	public String TradeWaterNo = "";
	public String PosPayFlag = "";
	public String UploadFlag = "";
	public int DropNum;
	public int PrintedFlag;
	public String ReportOrderID = "";
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}