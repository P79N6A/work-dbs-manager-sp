package com.dcdzsoft.dto.business;

/**
* 包裹处理中心列表查询
*/

public class OutParamIMPPCListQry implements java.io.Serializable
{
	public String PPCID = ""; //包裹处理中心编号
	public String PPCName = ""; //包裹处理中心名称
	public String CompanyID = ""; //包裹服务商编号
	public String CompanyName = ""; //服务商名称

	public String getPPCID()
	{
		return PPCID;
	}
	public void setPPCID(String PPCID)
	{
		this.PPCID = PPCID;
	}

	public String getPPCName()
	{
		return PPCName;
	}
	public void setPPCName(String PPCName)
	{
		this.PPCName = PPCName;
	}

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