package com.dcdzsoft.dto.business;

/**
* 揽件区域信息删除
*/

public class InParamIMCollectZoneDel implements java.io.Serializable
{
	public String FunctionID = "250103"; //功能编号

	public String OperID = ""; //管理员编号
	public String CollectZoneID = ""; //揽件区域编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250103";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250103";
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

	public String getCollectZoneID()
	{
		return CollectZoneID;
	}
	public void setCollectZoneID(String CollectZoneID)
	{
		this.CollectZoneID = CollectZoneID;
	}

}