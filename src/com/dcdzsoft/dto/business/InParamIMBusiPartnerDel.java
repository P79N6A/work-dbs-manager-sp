package com.dcdzsoft.dto.business;

/**
* 商业伙伴删除
*/

public class InParamIMBusiPartnerDel implements java.io.Serializable
{
	public String FunctionID = "250053"; //功能编号

	public String OperID = ""; //管理员编号
	public String BPartnerID = ""; //商业伙伴编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250053";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250053";
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

}