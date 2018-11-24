package com.dcdzsoft.dto.business;

/**
* 柜体信息列表查询
*/

public class OutParamTBTerminalListQry implements java.io.Serializable
{
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String TerminalStatus = ""; //柜体状态
	public String TerminalStatusName = ""; //柜体状态名称
	public String Location = ""; //柜体地址
	public String Address = ""; //柜体详细地址

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

	public String getTerminalStatus()
	{
		return TerminalStatus;
	}
	public void setTerminalStatus(String TerminalStatus)
	{
		this.TerminalStatus = TerminalStatus;
	}

	public String getTerminalStatusName()
	{
		return TerminalStatusName;
	}
	public void setTerminalStatusName(String TerminalStatusName)
	{
		this.TerminalStatusName = TerminalStatusName;
	}

	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String Location)
	{
		this.Location = Location;
	}

	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String Address)
	{
		this.Address = Address;
	}

}