package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DMHistoryItem implements java.io.Serializable
{
	public String PackageID = "";
	public java.sql.Timestamp CreateTime;
	public String BPartnerID = "";
	public String PPCID = "";
	public String ItemStatus = "";
	public String ServiceID = "";
	public double ServiceAmt;
	public String CollectionFlag = "";
	public double CollectionAmt;
	public String ReturnFlag = "";
	public String ZoneID = "";
	public String CompanyID = "";
	public String TradeWaterNo = "";
	public String CustomerAddress = "";
	public String CustomerName = "";
	public String CustomerMobile = "";
	public String CustomerID = "";
	public String TerminalNo = "";
	public String BoxNo = "";
	public String ParcelSize = "";
	public String CollectZoneID = "";
	public String CollectionType = "";
	public String CollectionAgentID = "";
	public java.sql.Timestamp CollectionTime;
	public String ReportOrderID = "";
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public int PrintedFlag;
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}