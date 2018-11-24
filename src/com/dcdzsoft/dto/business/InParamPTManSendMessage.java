package com.dcdzsoft.dto.business;

/**
* 消息发送
*/

public class InParamPTManSendMessage implements java.io.Serializable
{
	public String FunctionID = "331093"; //功能编号

	public String OperID = ""; //管理员编号
	public String PackageID = ""; //订单号
	public String MsgType = ""; //消息类型(1-SMS,2-Email)
	public String MsgContent = ""; //消息内容
	public String Phone = ""; //收件人手机
	public String Email = ""; //收件人邮箱
	public java.sql.Timestamp SendingTime; //发送时间

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331093";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331093";
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

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getMsgType()
	{
		return MsgType;
	}
	public void setMsgType(String MsgType)
	{
		this.MsgType = MsgType;
	}

	public String getMsgContent()
	{
		return MsgContent;
	}
	public void setMsgContent(String MsgContent)
	{
		this.MsgContent = MsgContent;
	}

	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String Phone)
	{
		this.Phone = Phone;
	}

	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String Email)
	{
		this.Email = Email;
	}

	public java.sql.Timestamp getSendingTime()
	{
		return SendingTime;
	}
	public void setSendingTime(java.sql.Timestamp SendingTime)
	{
		this.SendingTime = SendingTime;
	}

}