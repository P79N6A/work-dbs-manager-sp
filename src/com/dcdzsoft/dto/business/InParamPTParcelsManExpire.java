package com.dcdzsoft.dto.business;

/**
* 包裹人工超期
*/

public class InParamPTParcelsManExpire implements java.io.Serializable
{
	public String FunctionID = "331040"; //功能编号

	public String OperID = ""; //管理员编号
	public String ZoneID = ""; //分拣区域编号
	public String PackageID = ""; //订单号
	public String TerminalNo = ""; //柜体编号
	public java.sql.Timestamp ExpiredTime; //逾期时间
	public String ItemStatus = ""; //当前订单状态

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331040";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331040";
		else
			this.FunctionID = FunctionID;
	}

	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public java.sql.Timestamp getExpiredTime()
	{
		return ExpiredTime;
	}
	public void setExpiredTime(java.sql.Timestamp ExpiredTime)
	{
		this.ExpiredTime = ExpiredTime;
	}

	public String getItemStatus()
	{
		return ItemStatus;
	}
	public void setItemStatus(String ItemStatus)
	{
		this.ItemStatus = ItemStatus;
	}

}