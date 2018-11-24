package com.dcdzsoft.dto.business;

/**
* 服务商列表查询
*/

public class OutParamIMCompanyListQry implements java.io.Serializable
{
	public String CompanyID = ""; //服务商编号
	public String CompanyName = ""; //服务商名称

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String CompanyName)
	{
		this.CompanyName = CompanyName;
	}

}