package com.dcdzsoft.dto.business;

/**
* 揽件区域列表查询
*/

public class OutParamIMCollectZoneListQry implements java.io.Serializable
{
	public String CollectZoneID = ""; //揽件区域编号
	public String CollectZoneName = ""; //揽件区域名称

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

}