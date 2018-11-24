package com.dcdzsoft.dto.business;

/**
* BP忘记密码
*/

public class InParamDMBusinessPartnerForgetPwd implements java.io.Serializable
{
	public String FunctionID = "340012"; //功能编号

	public String BPartnerID = ""; //商业伙伴编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "340012";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "340012";
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