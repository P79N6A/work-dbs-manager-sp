package com.dcdzsoft.dto.business;

/**
* 揽件部门回收寄件包裹
*/

public class OutParamAPCollectedByDepartment implements java.io.Serializable
{
	public int Status; //揽件状态：0-成功；-1失败
	public String Message = ""; //处理消息

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