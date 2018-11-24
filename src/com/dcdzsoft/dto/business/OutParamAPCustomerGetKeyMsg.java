package com.dcdzsoft.dto.business;

/**
* 获取验证码短信
*/

public class OutParamAPCustomerGetKeyMsg implements java.io.Serializable
{
	public int Status; //0-失败；1-成功

	public int getStatus()
	{
		return Status;
	}
	public void setStatus(int Status)
	{
		this.Status = Status;
	}

}