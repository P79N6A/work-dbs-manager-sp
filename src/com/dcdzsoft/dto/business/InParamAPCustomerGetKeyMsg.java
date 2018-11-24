package com.dcdzsoft.dto.business;

/**
* 获取验证码短信
*/

public class InParamAPCustomerGetKeyMsg implements java.io.Serializable
{
	public String FunctionID = "650154"; //功能编号

	public String CustomerMobile = ""; //个人客户手机

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650154";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650154";
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

}