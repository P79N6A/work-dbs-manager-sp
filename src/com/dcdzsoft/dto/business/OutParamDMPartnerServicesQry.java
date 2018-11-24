package com.dcdzsoft.dto.business;

/**
* 商业伙伴可选服务查询
*/

public class OutParamDMPartnerServicesQry implements java.io.Serializable
{
	public String ServiceID = ""; //服务类型编号
	public String ServiceName = ""; //服务名称
	public double ServiceAmtSmall; //小箱价格
	public double ServiceAmtMed; //中箱价格
	public double ServiceAmtBig; //大箱价格

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

	public double getServiceAmtSmall()
	{
		return ServiceAmtSmall;
	}
	public void setServiceAmtSmall(double ServiceAmtSmall)
	{
		this.ServiceAmtSmall = ServiceAmtSmall;
	}

	public double getServiceAmtMed()
	{
		return ServiceAmtMed;
	}
	public void setServiceAmtMed(double ServiceAmtMed)
	{
		this.ServiceAmtMed = ServiceAmtMed;
	}

	public double getServiceAmtBig()
	{
		return ServiceAmtBig;
	}
	public void setServiceAmtBig(double ServiceAmtBig)
	{
		this.ServiceAmtBig = ServiceAmtBig;
	}

}