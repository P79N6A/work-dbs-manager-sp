package com.dcdzsoft.dto.business;

/**
* 个人客户信息查询
*/

public class OutParamAPCustomerInfoQry implements java.io.Serializable
{
	public int Status; //客户激活状态（0-未激活；1-正常；2-失效）
	public String LockerID = "";
	public String LockerName = "";
	public String Message = ""; //Status=1时客户虚拟POBox地址（POBoxAddress）；其他状态为提醒消息
	public String CustomerID = ""; //客户编号
	public String CustomerMobile = ""; //客户手机
	public String Validity = ""; //有效期（yyyy-MM-dd）
	public String CustomerName = ""; //客户姓名
	public String CustomerEmail = ""; //客户电子邮箱

	public int getStatus()
	{
		return Status;
	}
	public void setStatus(int Status)
	{
		this.Status = Status;
	}

	/**
	 * @return the lockerID
	 */
	public String getLockerID() {
		return LockerID;
	}
	/**
	 * @param lockerID the lockerID to set
	 */
	public void setLockerID(String lockerID) {
		LockerID = lockerID;
	}
	/**
	 * @return the lockerName
	 */
	public String getLockerName() {
		return LockerName;
	}
	/**
	 * @param lockerName the lockerName to set
	 */
	public void setLockerName(String lockerName) {
		LockerName = lockerName;
	}
	public String getMessage()
	{
		return Message;
	}
	public void setMessage(String Message)
	{
		this.Message = Message;
	}

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getValidity()
	{
		return Validity;
	}
	public void setValidity(String Validity)
	{
		this.Validity = Validity;
	}

	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String CustomerName)
	{
		this.CustomerName = CustomerName;
	}

	public String getCustomerEmail()
	{
		return CustomerEmail;
	}
	public void setCustomerEmail(String CustomerEmail)
	{
		this.CustomerEmail = CustomerEmail;
	}

}