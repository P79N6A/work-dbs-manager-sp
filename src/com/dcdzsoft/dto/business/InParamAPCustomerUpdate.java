package com.dcdzsoft.dto.business;

/**
* 个人信息更新（只有已激活的客户才能更新）
*/

public class InParamAPCustomerUpdate implements java.io.Serializable
{
	public String FunctionID = "650152"; //功能编号

	public String CustomerMobile = ""; //个人客户手机
	public String Keyword = ""; //验证码
	public int Months; //注册使用时间
	public String LockerID = ""; //柜号
	public String CustomerName = ""; //个人客户姓名
	public String CustomerEmail = ""; //个人客户电子邮箱

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650152";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650152";
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

	public String getKeyword()
	{
		return Keyword;
	}
	public void setKeyword(String Keyword)
	{
		this.Keyword = Keyword;
	}

	public int getMonths()
	{
		return Months;
	}
	public void setMonths(int Months)
	{
		this.Months = Months;
	}

	public String getLockerID()
	{
		return LockerID;
	}
	public void setLockerID(String LockerID)
	{
		this.LockerID = LockerID;
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