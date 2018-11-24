package com.dcdzsoft.dto.business;

/**
* 个人信息注册
*/

public class OutParamAPCustomerRegister implements java.io.Serializable
{
	public int Status; //0-注册失败；1-注册成功
	public String Message = ""; //Status=1：注册成功，提醒用户激活消息；

	public int getStatus()
	{
		return Status;
	}
	public void setStatus(int Status)
	{
		this.Status = Status;
	}

	public String getMessage()
	{
		return Message;
	}
	public void setMessage(String Message)
	{
		this.Message = Message;
	}

}