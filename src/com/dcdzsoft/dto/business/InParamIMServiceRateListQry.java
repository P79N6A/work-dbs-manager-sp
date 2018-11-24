package com.dcdzsoft.dto.business;

/**
* 服务类型列表查询
*/

public class InParamIMServiceRateListQry implements java.io.Serializable
{
	public String FunctionID = "250096"; //功能编号

	public String OperID = ""; //管理员编号
	public String ServiceID = ""; //服务类型编号
	public String Active = ""; //激活状态

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250096";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250096";
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

	public String getServiceID()
	{
		return ServiceID;
	}
	public void setServiceID(String ServiceID)
	{
		this.ServiceID = ServiceID;
	}

	public String getActive()
	{
		return Active;
	}
	public void setActive(String Active)
	{
		this.Active = Active;
	}

}