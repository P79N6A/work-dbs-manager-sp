package com.dcdzsoft.dto.business;

/**
* 投递员修改密码
*/

public class InParamDMBusinessPartnerModPwd implements java.io.Serializable
{
	public String FunctionID = "340011"; //功能编号

	public String BPartnerID = ""; //商业伙伴编号
	public String NewPassword = ""; //新密码
	public String OldPassword = ""; //旧密码

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "340011";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "340011";
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

	public String getNewPassword()
	{
		return NewPassword;
	}
	public void setNewPassword(String NewPassword)
	{
		this.NewPassword = NewPassword;
	}

	public String getOldPassword()
	{
		return OldPassword;
	}
	public void setOldPassword(String OldPassword)
	{
		this.OldPassword = OldPassword;
	}

}