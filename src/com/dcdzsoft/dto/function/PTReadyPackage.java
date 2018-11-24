package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PTReadyPackage implements java.io.Serializable
{
	public String PackageID = "";
	public java.sql.Timestamp CreateTime;
	public String ItemStatus = "";
	public String PPCID = "";
	public String ZoneID = "";
	public String ZoneIDDes = "";
	public String CompanyID = "";
	public String RefNo = "";
	public String ParcelSize = "";
	public String CustomerID = "";
	public String CustomerName = "";
	public String CustomerAddress = "";
	public String CustomerMobile = "";
	public String TerminalNo = "";
	public String BoxType = "";
	public String BoxNo = "";
	public int PrintedFlag;
	public String ReportOrderID = "";
	public String DropAgentID = "";
	public java.sql.Timestamp ExpiredTime;
	public int DropNum;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}