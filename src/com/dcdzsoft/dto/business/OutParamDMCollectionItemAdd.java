package com.dcdzsoft.dto.business;

/**
* 寄件订单信息添加
*/

public class OutParamDMCollectionItemAdd implements java.io.Serializable
{
	public String PackageID = ""; //包裹单号

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

}