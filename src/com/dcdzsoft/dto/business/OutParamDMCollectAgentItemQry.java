package com.dcdzsoft.dto.business;

/**
* 揽件员查询包裹订单信息
*/

public class OutParamDMCollectAgentItemQry implements java.io.Serializable
{
	public int Status; //查询状态：0-成功；1-包裹单号无效；2-包裹单号过期；3-未选择上门取件的订单；4-无查询权限
	public String Message = ""; //当Status=0，寄件人信息；其他为异常消息

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