package com.dcdzsoft.dto.business;

/**
* 删除商业伙伴服务权限
*/

public class InParamIMBPartnerServiceRightDel implements java.io.Serializable
{
	public String FunctionID = "251043"; //功能编号

	public String OperID = ""; //管理员编号
	public String BPartnerID = ""; //商业伙伴编号
	public String servicesList = ""; //服务列表列表

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "251043";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "251043";
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

	public String getBPartnerID()
	{
		return BPartnerID;
	}
	public void setBPartnerID(String BPartnerID)
	{
		this.BPartnerID = BPartnerID;
	}

	public String getservicesList()
	{
		return servicesList;
	}
	public void setservicesList(String servicesList)
	{
		this.servicesList = servicesList;
	}

}