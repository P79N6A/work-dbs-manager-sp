package com.dcdzsoft.dto.business;

/**
* 发送警报信息
*/

public class InParamMBSendAlarmInfo implements java.io.Serializable
{
	public String FunctionID = "150318"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public int SendMode; //发送方式（1-邮件，2-短信，3-短信&邮件，默认1）
	public String Mobile = ""; //维修人员手机
	public String Email = ""; //维修人员电子邮箱

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "150318";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "150318";
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

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public int getSendMode()
	{
		return SendMode;
	}
	public void setSendMode(int SendMode)
	{
		this.SendMode = SendMode;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}

	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String Email)
	{
		this.Email = Email;
	}

}