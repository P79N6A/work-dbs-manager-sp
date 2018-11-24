package com.dcdzsoft.dto.business;

/**
* 分拣区域柜体权限查询
*/

public class OutParamIMZoneLockerRightQry implements java.io.Serializable
{
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String Location = ""; //自提柜地址

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

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getTerminalName()
	{
		return TerminalName;
	}
	public void setTerminalName(String TerminalName)
	{
		this.TerminalName = TerminalName;
	}

	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String Location)
	{
		this.Location = Location;
	}

}