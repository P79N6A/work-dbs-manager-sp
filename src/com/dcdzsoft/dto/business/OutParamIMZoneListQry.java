package com.dcdzsoft.dto.business;

/**
* 分拣区域表查询
*/

public class OutParamIMZoneListQry implements java.io.Serializable
{
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String CompanyID = ""; //服务商编号
	public String CompanyName = ""; //服务商名称

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getZoneName()
	{
		return ZoneName;
	}
	public void setZoneName(String ZoneName)
	{
		this.ZoneName = ZoneName;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String CompanyName)
	{
		this.CompanyName = CompanyName;
	}

}