package com.dcdzsoft.dto.business;

/**
* 商业伙伴账户余额查询
*/

public class InParamDMPartnerBalanceQry implements java.io.Serializable
{
	public String FunctionID = "340104"; //功能编号

	public String BPartnerID = ""; //商业伙伴编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "340104";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "340104";
		else
			this.FunctionID = FunctionID;
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