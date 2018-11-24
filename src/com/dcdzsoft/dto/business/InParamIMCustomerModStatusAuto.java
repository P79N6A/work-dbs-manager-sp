package com.dcdzsoft.dto.business;

/**
* 自动更新个人客户状态
*/

public class InParamIMCustomerModStatusAuto implements java.io.Serializable
{
	public String FunctionID = "250078"; //功能编号

	public String OperID = ""; //管理员编号
	public String Mode = ""; //业务模式（1-Customer过期，其他-删除过期Customer）
	public String CustomerID = ""; //个人客户编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250078";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250078";
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

	public String getMode()
	{
		return Mode;
	}
	public void setMode(String Mode)
	{
		this.Mode = Mode;
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