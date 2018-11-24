package com.dcdzsoft.dto.business;

/**
* 个人客户信息查询
*/

public class OutParamIMCustomerQry implements java.io.Serializable
{
	public String CustomerID = ""; //个人客户编号
	public String CustomerName = ""; //个人客户名称
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String IDCard = ""; //身份证号
	public String Address = ""; //地址
	public String Email = ""; //邮箱
	public String Mobile = ""; //手机
	public String Active = ""; //激活状态
	public String ActiveName = ""; //激活状态名称
	public String Status = ""; //用户状态
	public String StatusName = ""; //用户状态名称
	public java.sql.Date Validity; //有效期
	public int Months; //应用期限(月)
	public String Remark = ""; //备注

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

	public String getIDCard()
	{
		return IDCard;
	}
	public void setIDCard(String IDCard)
	{
		this.IDCard = IDCard;
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

	public String getActive()
	{
		return Active;
	}
	public void setActive(String Active)
	{
		this.Active = Active;
	}

	public String getActiveName()
	{
		return ActiveName;
	}
	public void setActiveName(String ActiveName)
	{
		this.ActiveName = ActiveName;
	}

	public String getStatus()
	{
		return Status;
	}
	public void setStatus(String Status)
	{
		this.Status = Status;
	}

	public String getStatusName()
	{
		return StatusName;
	}
	public void setStatusName(String StatusName)
	{
		this.StatusName = StatusName;
	}

	public java.sql.Date getValidity()
	{
		return Validity;
	}
	public void setValidity(java.sql.Date Validity)
	{
		this.Validity = Validity;
	}

	public int getMonths()
	{
		return Months;
	}
	public void setMonths(int Months)
	{
		this.Months = Months;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}