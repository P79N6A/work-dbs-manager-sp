package com.dcdzsoft.dto.business;

/**
* 黑名单查询
*/

public class OutParamMBMobileBlackListQry implements java.io.Serializable
{
	public String CustomerMobile = ""; //取件人手机
	public java.sql.Timestamp LastModifyTime; //最后修改时间

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public java.sql.Timestamp getLastModifyTime()
	{
		return LastModifyTime;
	}
	public void setLastModifyTime(java.sql.Timestamp LastModifyTime)
	{
		this.LastModifyTime = LastModifyTime;
	}

}