package com.dcdzsoft.dto.business;

/**
* 分拣区域信息删除
*/

public class InParamIMZoneDel implements java.io.Serializable
{
	public String FunctionID = "250023"; //功能编号

	public String OperID = ""; //管理员编号
	public String ZoneID = ""; //分拣区域中心编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250023";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250023";
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

}