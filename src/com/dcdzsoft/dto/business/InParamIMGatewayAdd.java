package com.dcdzsoft.dto.business;

/**
* 短信接口信息增加
*/

public class InParamIMGatewayAdd implements java.io.Serializable
{
	public String FunctionID = "250081"; //功能编号

	public String OperID = ""; //管理员编号
	public String GatewayID = ""; //短信接口编号
	public String GatewayName = ""; //短信邮件接口名称
	public String SMSUsername = ""; //短信接入用户名
	public String SMSPassword = ""; //短信接入密码
	public String SMSUsercode = ""; //短信用户编码
	public String SMSField1 = ""; //短信接口字段1
	public String SMSField2 = ""; //短信接口字段2
	public String SMSField3 = ""; //短信接口字段3
	public String GatewayURL = ""; //短信接口地址
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250081";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250081";
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

	public String getGatewayID()
	{
		return GatewayID;
	}
	public void setGatewayID(String GatewayID)
	{
		this.GatewayID = GatewayID;
	}

	public String getGatewayName()
	{
		return GatewayName;
	}
	public void setGatewayName(String GatewayName)
	{
		this.GatewayName = GatewayName;
	}

	public String getSMSUsername()
	{
		return SMSUsername;
	}
	public void setSMSUsername(String SMSUsername)
	{
		this.SMSUsername = SMSUsername;
	}

	public String getSMSPassword()
	{
		return SMSPassword;
	}
	public void setSMSPassword(String SMSPassword)
	{
		this.SMSPassword = SMSPassword;
	}

	public String getSMSUsercode()
	{
		return SMSUsercode;
	}
	public void setSMSUsercode(String SMSUsercode)
	{
		this.SMSUsercode = SMSUsercode;
	}

	public String getSMSField1()
	{
		return SMSField1;
	}
	public void setSMSField1(String SMSField1)
	{
		this.SMSField1 = SMSField1;
	}

	public String getSMSField2()
	{
		return SMSField2;
	}
	public void setSMSField2(String SMSField2)
	{
		this.SMSField2 = SMSField2;
	}

	public String getSMSField3()
	{
		return SMSField3;
	}
	public void setSMSField3(String SMSField3)
	{
		this.SMSField3 = SMSField3;
	}

	public String getGatewayURL()
	{
		return GatewayURL;
	}
	public void setGatewayURL(String GatewayURL)
	{
		this.GatewayURL = GatewayURL;
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