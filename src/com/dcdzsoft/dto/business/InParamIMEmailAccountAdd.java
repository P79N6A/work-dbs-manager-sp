package com.dcdzsoft.dto.business;

/**
* 邮件账户信息增加
*/

public class InParamIMEmailAccountAdd implements java.io.Serializable
{
	public String FunctionID = "250111"; //功能编号

	public String OperID = ""; //管理员编号
	public String EmailID = ""; //邮件账户编号
	public String EmailName = ""; //邮件账户名称
	public String Username = ""; //邮箱用户名
	public String Password = ""; //邮箱密码
	public String EmailParam1 = ""; //邮件接口参数1
	public String EmailParam2 = ""; //邮件接口参数2
	public String EmailParam3 = ""; //邮件接口参数3
	public String EmailParam4 = ""; //邮件接口参数4
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250111";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250111";
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

	public String getEmailID()
	{
		return EmailID;
	}
	public void setEmailID(String EmailID)
	{
		this.EmailID = EmailID;
	}

	public String getEmailName()
	{
		return EmailName;
	}
	public void setEmailName(String EmailName)
	{
		this.EmailName = EmailName;
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

	public String getEmailParam1()
	{
		return EmailParam1;
	}
	public void setEmailParam1(String EmailParam1)
	{
		this.EmailParam1 = EmailParam1;
	}

	public String getEmailParam2()
	{
		return EmailParam2;
	}
	public void setEmailParam2(String EmailParam2)
	{
		this.EmailParam2 = EmailParam2;
	}

	public String getEmailParam3()
	{
		return EmailParam3;
	}
	public void setEmailParam3(String EmailParam3)
	{
		this.EmailParam3 = EmailParam3;
	}

	public String getEmailParam4()
	{
		return EmailParam4;
	}
	public void setEmailParam4(String EmailParam4)
	{
		this.EmailParam4 = EmailParam4;
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