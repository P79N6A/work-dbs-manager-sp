package com.dcdzsoft.dto.business;

/**
* 包裹服务中心增加
*/

public class InParamIMPPCAdd implements java.io.Serializable
{
	public String FunctionID = "250041"; //功能编号

	public String OperID = ""; //管理员编号
	public String PPCID = ""; //包裹处理中心编号
	public String PPCName = ""; //包裹处理中心名称
	public String CompanyID = ""; //包裹服务商编号
	public String Username = ""; //接入用户名
	public String Password = ""; //接入方秘钥
	public String PPCServerIP = ""; //PPC服务器IP
	public String PPCServerURL = ""; //PPC服务器URL
	public String PPCServerUsername = ""; //PPC服务器接入用户名
	public String PPCServerPassword = ""; //PPC服务器接入用户密码
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250041";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250041";
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

	public String getPPCID()
	{
		return PPCID;
	}
	public void setPPCID(String PPCID)
	{
		this.PPCID = PPCID;
	}

	public String getPPCName()
	{
		return PPCName;
	}
	public void setPPCName(String PPCName)
	{
		this.PPCName = PPCName;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
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

	public String getPPCServerIP()
	{
		return PPCServerIP;
	}
	public void setPPCServerIP(String PPCServerIP)
	{
		this.PPCServerIP = PPCServerIP;
	}

	public String getPPCServerURL()
	{
		return PPCServerURL;
	}
	public void setPPCServerURL(String PPCServerURL)
	{
		this.PPCServerURL = PPCServerURL;
	}

	public String getPPCServerUsername()
	{
		return PPCServerUsername;
	}
	public void setPPCServerUsername(String PPCServerUsername)
	{
		this.PPCServerUsername = PPCServerUsername;
	}

	public String getPPCServerPassword()
	{
		return PPCServerPassword;
	}
	public void setPPCServerPassword(String PPCServerPassword)
	{
		this.PPCServerPassword = PPCServerPassword;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}