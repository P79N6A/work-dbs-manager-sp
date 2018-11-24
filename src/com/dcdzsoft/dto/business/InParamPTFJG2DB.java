package com.dcdzsoft.dto.business;

/**
* 分拣柜发送建包信息到包裹柜，写入数据库
*/

public class InParamPTFJG2DB implements java.io.Serializable
{
	public String FunctionID = "330701"; //功能编号

	public String PackageID = ""; //包裹号
	public String ZoneID = ""; //分拣区域编号
	public String TerminalNo = ""; //设备号
	public String OperID = ""; //建包操作员
	public java.sql.Timestamp CreateTime; //扫描建包时间

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "330701";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "330701";
		else
			this.FunctionID = FunctionID;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
	}

	public java.sql.Timestamp getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(java.sql.Timestamp CreateTime)
	{
		this.CreateTime = CreateTime;
	}

}