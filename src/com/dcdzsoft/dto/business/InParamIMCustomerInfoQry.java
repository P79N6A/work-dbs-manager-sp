package com.dcdzsoft.dto.business;

/**
* 个人客户信息查询
*/

public class InParamIMCustomerInfoQry implements java.io.Serializable
{
	public String FunctionID = "250076"; //功能编号

	public String OperID = ""; //管理员编号
	public String CustomerID = ""; //个人客户编号（或手机号）

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250076";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250076";
		else
			this.FunctionID = FunctionID;
	}

	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
	}

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
	}

}