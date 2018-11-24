package com.dcdzsoft.dto.business;

/**
* 修改订单超时时间
*/

public class InParamPTModExpiredTime implements java.io.Serializable
{
	public String FunctionID = "330304"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String PackageID = ""; //订单号
	public java.sql.Date ExpiredTime; //逾期时间
	public String ModType = ""; //修改类型（1-只修改催领时间；2-修改逾期和催领时间；其他-修改逾期时间）
	public java.sql.Date ReminderTime; //催领时间
	public String RemoteFlag = ""; //远程操作标志

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "330304";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "330304";
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

	public java.sql.Date getExpiredTime()
	{
		return ExpiredTime;
	}
	public void setExpiredTime(java.sql.Date ExpiredTime)
	{
		this.ExpiredTime = ExpiredTime;
	}

	public String getModType()
	{
		return ModType;
	}
	public void setModType(String ModType)
	{
		this.ModType = ModType;
	}

	public java.sql.Date getReminderTime()
	{
		return ReminderTime;
	}
	public void setReminderTime(java.sql.Date ReminderTime)
	{
		this.ReminderTime = ReminderTime;
	}

	public String getRemoteFlag()
	{
		return RemoteFlag;
	}
	public void setRemoteFlag(String RemoteFlag)
	{
		this.RemoteFlag = RemoteFlag;
	}

}