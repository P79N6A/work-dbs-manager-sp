package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PTTransferItemWater implements java.io.Serializable
{
	public long WaterID;
	public String PackageID = "";
	public String ItemStatus = "";
	public java.sql.Timestamp CreateTime;
	public String TerminalNo = "";
	public String ZoneID = "";
	public String PPCID = "";
	public String TransferType = "";
	public String TransferStatus = "";
	public String TransferID = "";
	public int ResendNum;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}