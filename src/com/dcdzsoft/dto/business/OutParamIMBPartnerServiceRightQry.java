package com.dcdzsoft.dto.business;

/**
* 服务商箱体权限查询
*/

public class OutParamIMBPartnerServiceRightQry implements java.io.Serializable
{
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public String ServiceID = ""; //服务编号
	public String ServiceName = ""; //服务名称
	public double ServiceAmt; //服务价格

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

	public String getServiceID()
	{
		return ServiceID;
	}
	public void setServiceID(String ServiceID)
	{
		this.ServiceID = ServiceID;
	}

	public String getServiceName()
	{
		return ServiceName;
	}
	public void setServiceName(String ServiceName)
	{
		this.ServiceName = ServiceName;
	}

	public double getServiceAmt()
	{
		return ServiceAmt;
	}
	public void setServiceAmt(double ServiceAmt)
	{
		this.ServiceAmt = ServiceAmt;
	}

}