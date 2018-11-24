package com.dcdzsoft.dto.business;

/**
* 包裹处理中心查询
*/

public class OutParamIMPPCQry implements java.io.Serializable
{
	public String PPCID = ""; //包裹处理中心编号
	public String PPCName = ""; //包裹处理中心名称
	public String CompanyID = ""; //包裹服务商编号
	public String CompanyName = ""; //服务商名称
	public String Username = ""; //接入用户名
	public String Password = ""; //接入方秘钥
	public String PPCServerIP = ""; //PPC服务器IP
	public String PPCServerURL = ""; //PPC服务器URL
	public String PPCServerUsername = ""; //PPC服务器接入用户名
	public String PPCServerPassword = ""; //PPC服务器接入用户密码
	public String Remark = ""; //备注

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

	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String CompanyName)
	{
		this.CompanyName = CompanyName;
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