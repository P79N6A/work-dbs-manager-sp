package com.dcdzsoft.dto.business;

/**
* 寄件订单信息查询（E1信息）
*/

public class OutParamDMDeliveryE1InfoQry implements java.io.Serializable
{
	public String PackageID = ""; //包裹单号
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public String Address = ""; //商业伙伴地址
	public String Mobile = ""; //商业伙伴手机
	public String CustomerName = ""; //收件人名称
	public String CustomerMobile = ""; //收件人手机
	public String CustomerAddress = ""; //收件人地址
	public String ReturnFlag = ""; //退件服务选择（0-未选；1-选择）
	public String Service = "";
	public String ParcelSize = "";
	public String OfBureau = "";

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getBPartnerID()
	{
		return BPartnerID;
	}
	public void setBPartnerID(String BPartnerID)
	{
		this.BPartnerID = BPartnerID;
	}

	public String getBPartnerName()
	{
		return BPartnerName;
	}
	public void setBPartnerName(String BPartnerName)
	{
		this.BPartnerName = BPartnerName;
	}

	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String Address)
	{
		this.Address = Address;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}

	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String CustomerName)
	{
		this.CustomerName = CustomerName;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getCustomerAddress()
	{
		return CustomerAddress;
	}
	public void setCustomerAddress(String CustomerAddress)
	{
		this.CustomerAddress = CustomerAddress;
	}

	public String getReturnFlag()
	{
		return ReturnFlag;
	}
	public void setReturnFlag(String ReturnFlag)
	{
		this.ReturnFlag = ReturnFlag;
	}
	public String getService() {
		return Service;
	}
	public void setService(String service) {
		Service = service;
	}
	public String getParcelSize() {
		return ParcelSize;
	}
	public void setParcelSize(String parcelSize) {
		ParcelSize = parcelSize;
	}

}