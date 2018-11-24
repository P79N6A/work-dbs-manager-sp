package com.dcdzsoft.dto.business;

/**
* 投递员登陆验证
*/

public class InParamPTPostmanLogin implements java.io.Serializable
{
	public String FunctionID = "330001"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String PostmanID = ""; //投递员编号
	public String Password = ""; //投递员密码
	public String VerifyFlag = ""; //认证标志(0：获取动态密码

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "330001";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "330001";
		else
			this.FunctionID = FunctionID;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public String getPostmanID()
	{
		return PostmanID;
	}
	public void setPostmanID(String PostmanID)
	{
		this.PostmanID = PostmanID;
	}

	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String Password)
	{
		this.Password = Password;
	}

	public String getVerifyFlag()
	{
		return VerifyFlag;
	}
	public void setVerifyFlag(String VerifyFlag)
	{
		this.VerifyFlag = VerifyFlag;
	}

}