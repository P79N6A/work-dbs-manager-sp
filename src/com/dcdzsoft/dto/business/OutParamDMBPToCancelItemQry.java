package com.dcdzsoft.dto.business;

/**
* 寄件订单查询
*/

public class OutParamDMBPToCancelItemQry implements java.io.Serializable
{
	public String PackageID = ""; //订单号
	public java.sql.Timestamp CreateTime; //创建订单时间
	public String BPartnerID = ""; //商业伙伴编号

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public java.sql.Timestamp getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(java.sql.Timestamp CreateTime)
	{
		this.CreateTime = CreateTime;
	}

	public String getBPartnerID()
	{
		return BPartnerID;
	}
	public void setBPartnerID(String BPartnerID)
	{
		this.BPartnerID = BPartnerID;
	}

}