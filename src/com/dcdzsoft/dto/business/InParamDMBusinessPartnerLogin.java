package com.dcdzsoft.dto.business;

/**
* 用户登录（包括商业合作伙伴，等）
*/

public class InParamDMBusinessPartnerLogin implements java.io.Serializable
{
	public String FunctionID = "340001"; //功能编号

	public String Username = ""; //登录用户名
	public String Password = ""; //登录用户密码

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "340001";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "340001";
		else
			this.FunctionID = FunctionID;
	}

	public String getUsername()
	{
		return Username;
	}
	public void setUsername(String Username)
	{
		this.Username = Username;
	}

	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String Password)
	{
		this.Password = Password;
	}

}