package com.dcdzsoft.dto.business;

/**
* 个人信息注册
*/

public class InParamAPCustomerRegister implements java.io.Serializable
{
	public String FunctionID = "650151"; //功能编号

	public String CustomerMobile = ""; //个人客户手机
	public String LockerID = ""; //柜号
	public int Months; //注册使用时间
	public String CustomerName = ""; //个人客户姓名
	public String CustomerEmail = ""; //个人客户电子邮箱
	public String CustomerIDCard = ""; //个人客户身份证ID

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650151";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650151";
		else
			this.FunctionID = FunctionID;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getLockerID()
	{
		return LockerID;
	}
	public void setLockerID(String LockerID)
	{
		this.LockerID = LockerID;
	}

	public int getMonths()
	{
		return Months;
	}
	public void setMonths(int Months)
	{
		this.Months = Months;
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

	public String getCustomerIDCard()
	{
		return CustomerIDCard;
	}
	public void setCustomerIDCard(String CustomerIDCard)
	{
		this.CustomerIDCard = CustomerIDCard;
	}

}