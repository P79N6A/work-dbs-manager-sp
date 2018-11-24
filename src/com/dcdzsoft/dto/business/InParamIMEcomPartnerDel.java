package com.dcdzsoft.dto.business;

/**
* 电商伙伴删除
*/

public class InParamIMEcomPartnerDel implements java.io.Serializable
{
	public String FunctionID = "250063"; //功能编号

	public String OperID = ""; //管理员编号
	public String EPartnerID = ""; //电商伙伴编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250063";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250063";
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

	public String getEPartnerID()
	{
		return EPartnerID;
	}
	public void setEPartnerID(String EPartnerID)
	{
		this.EPartnerID = EPartnerID;
	}

}