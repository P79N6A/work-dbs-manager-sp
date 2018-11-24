package com.dcdzsoft.dto.business;

/**
* 个人客户信息查询
*/

public class OutParamIMCustomerInfoQry implements java.io.Serializable
{
	public String CustomerID = ""; //个人客户编号
	public String CustomerName = ""; //个人客户名称
	public String Address = ""; //地址
	public String Email = ""; //邮箱
	public String Mobile = ""; //手机
	public String TerminalNo = "";
	public String ZoneID = "";

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
	}

	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String CustomerName)
	{
		this.CustomerName = CustomerName;
	}

	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String Address)
	{
		this.Address = Address;
	}

	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String Email)
	{
		this.Email = Email;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}
	/**
	 * @return the terminalNo
	 */
	public String getTerminalNo() {
		return TerminalNo;
	}
	/**
	 * @param terminalNo the terminalNo to set
	 */
	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}
	/**
	 * @return the zoneID
	 */
	public String getZoneID() {
		return ZoneID;
	}
	/**
	 * @param zoneID the zoneID to set
	 */
	public void setZoneID(String zoneID) {
		ZoneID = zoneID;
	}

}