package com.dcdzsoft.dto.business;

/**
* 邮递员柜体权限查询
*/

public class OutParamPMManTerminalRightQry implements java.io.Serializable
{
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String ZoneID = ""; //运营网点编号
	public String ZongName = ""; //运营网点名称

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

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getZongName()
	{
		return ZongName;
	}
	public void setZongName(String ZongName)
	{
		this.ZongName = ZongName;
	}

}