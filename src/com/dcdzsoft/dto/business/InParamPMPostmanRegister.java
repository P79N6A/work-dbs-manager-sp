package com.dcdzsoft.dto.business;

/**
* 邮递员注册
*/

public class InParamPMPostmanRegister implements java.io.Serializable
{
	public String FunctionID = "311011"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String Mobile = ""; //手机
	public String CompanyID = ""; //投递公司编号
	public String ZoneID = ""; //分拣区域编号
	public String IDCard = ""; //身份证
	public String CheckCode = ""; //验证码
	public String Password = ""; //登陆密码

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "311011";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "311011";
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

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getIDCard()
	{
		return IDCard;
	}
	public void setIDCard(String IDCard)
	{
		this.IDCard = IDCard;
	}

	public String getCheckCode()
	{
		return CheckCode;
	}
	public void setCheckCode(String CheckCode)
	{
		this.CheckCode = CheckCode;
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