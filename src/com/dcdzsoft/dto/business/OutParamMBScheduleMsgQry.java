package com.dcdzsoft.dto.business;

/**
* 预定时间发送的短消息查询
*/

public class OutParamMBScheduleMsgQry implements java.io.Serializable
{
	public String TerminalNo = ""; //设备号
	public String PackageID = ""; //订单号
	public String CustomerMobile = ""; //手机号
	public long WaterID; //流水号

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public long getWaterID()
	{
		return WaterID;
	}
	public void setWaterID(long WaterID)
	{
		this.WaterID = WaterID;
	}

}