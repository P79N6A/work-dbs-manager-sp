package com.dcdzsoft.dto.business;

/**
* 个人客户信息查询
*/

public class InParamAPCustomerInfoQry implements java.io.Serializable
{
	public String FunctionID = "650153"; //功能编号

	public String CustomerID = ""; //客户编号（或手机号）

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650153";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650153";
		else
			this.FunctionID = FunctionID;
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