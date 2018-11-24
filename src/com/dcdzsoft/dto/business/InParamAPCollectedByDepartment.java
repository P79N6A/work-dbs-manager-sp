package com.dcdzsoft.dto.business;

/**
* 揽件部门回收寄件包裹
*/

public class InParamAPCollectedByDepartment implements java.io.Serializable
{
	public String FunctionID = "650065"; //功能编号

	public String PackageID = ""; //订单号
	public String OperID = ""; //操作员编号
	public String Password = ""; //操作员密码（MD5）
	public String Action = ""; //操作（“1”-Transfer；“2”-Redistribute）
	public String PPCID = ""; //PPC服务器编号
	public String ZoneID = ""; //AZC编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650065";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650065";
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

	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
	}

	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String Password)
	{
		this.Password = Password;
	}

	public String getAction()
	{
		return Action;
	}
	public void setAction(String Action)
	{
		this.Action = Action;
	}

	public String getPPCID()
	{
		return PPCID;
	}
	public void setPPCID(String PPCID)
	{
		this.PPCID = PPCID;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

}