package com.dcdzsoft.dto.business;

/**
* 服务类型列表查询
*/

public class OutParamIMServiceRateListQry implements java.io.Serializable
{
	public String ServiceID = ""; //服务类型编号
	public String ServiceName = ""; //服务名称

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

}