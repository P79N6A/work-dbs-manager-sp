package com.dcdzsoft.dto.business;

/**
* 寄件包裹由揽件员收件
*/

public class OutParamDMDeliveryCollected implements java.io.Serializable
{
	public int Status; //揽件状态：0-成功；1-包裹单号无效；2-包裹单号过期；3-无揽件权限或密码错误
	public String Message = ""; //处理成功消息

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