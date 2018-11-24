package com.dcdzsoft.ppcapi;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SendInfo {
    public String ppcId  = "";
	public String apiString = "";
	public String userName = "";
	public String userKey = "";
	public String PPCInterface = "";
	public String uploadFlag = "";
	public String TerminalNo = "";
	
	public String TransferFlag = "";//Constant.TRANSFER_FLAG_UPLOAD,Constant.TRANSFER_FLAG_UPDATE
	public long ItemID = 0;
	public String ItemCode = "";
	public String ItemType = "";
	public String ZoneID = "";
	public String ItemStatus = "";
	public long TransferWaterID = 0;
	
	public String OfBureau = "";//PPC-AZC#
	
	public java.sql.Timestamp CreateTime;
	public String SenderName    = "";
	public String SenderMobile   = "";
	public String CustomerName   = "";
	public String CustomerMobile = "";
	public String CustomerAddress = "";
	
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
