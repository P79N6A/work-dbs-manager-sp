package com.dcdzsoft.dto.business;

/**
* 揽件区域查询数量
*/

public class InParamIMCollectZoneQryCount implements java.io.Serializable
{
	public String FunctionID = "250105"; //功能编号

	public String OperID = ""; //管理员编号
	public String CollectZoneID = ""; //揽件区域编号
	public String CollectZoneName = ""; //揽件区域名称
	public String CollectZoneDesc = ""; //揽件区域描述
	public String ZoneID = ""; //分拣区域编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250105";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250105";
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

	public String getCollectZoneName()
	{
		return CollectZoneName;
	}
	public void setCollectZoneName(String CollectZoneName)
	{
		this.CollectZoneName = CollectZoneName;
	}

	public String getCollectZoneDesc()
	{
		return CollectZoneDesc;
	}
	public void setCollectZoneDesc(String CollectZoneDesc)
	{
		this.CollectZoneDesc = CollectZoneDesc;
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