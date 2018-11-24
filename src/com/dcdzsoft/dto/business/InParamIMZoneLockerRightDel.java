package com.dcdzsoft.dto.business;

/**
* 分拣区域柜体权限删除
*/

public class InParamIMZoneLockerRightDel implements java.io.Serializable
{
	public String FunctionID = "251023"; //功能编号

	public String OperID = ""; //管理员编号
	public String ZoneID = ""; //服务商编号
	public String LockerList = ""; //自提柜柜号列表

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "251023";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "251023";
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

	public String getLockerList()
	{
		return LockerList;
	}
	public void setLockerList(String LockerList)
	{
		this.LockerList = LockerList;
	}

}