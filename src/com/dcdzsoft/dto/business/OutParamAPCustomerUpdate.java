package com.dcdzsoft.dto.business;

/**
* 个人信息更新（只有已激活的客户才能更新）
*/

public class OutParamAPCustomerUpdate implements java.io.Serializable
{
	public int Status; //0-失败；1-成功
	public String POBoxAddress = ""; //客户虚拟POBox地址
	public String Validity = ""; //有效期（yyyy-MM-dd）

	public int getStatus()
	{
		return Status;
	}
	public void setStatus(int Status)
	{
		this.Status = Status;
	}

	public String getPOBoxAddress()
	{
		return POBoxAddress;
	}
	public void setPOBoxAddress(String POBoxAddress)
	{
		this.POBoxAddress = POBoxAddress;
	}

	public String getValidity()
	{
		return Validity;
	}
	public void setValidity(String Validity)
	{
		this.Validity = Validity;
	}

}