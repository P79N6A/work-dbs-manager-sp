package com.dcdzsoft.dto.business;

/**
* 邮件账户信息查询
*/

public class InParamIMEmailAccountQry implements java.io.Serializable
{
	public String FunctionID = "250114"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String OperID = ""; //管理员编号
	public String EmailID = ""; //邮件账户编号
	public String EmailName = ""; //邮件账户名称

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250114";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250114";
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

}