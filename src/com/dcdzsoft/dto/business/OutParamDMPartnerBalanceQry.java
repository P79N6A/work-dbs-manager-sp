package com.dcdzsoft.dto.business;

/**
* 商业伙伴账户余额查询
*/

public class OutParamDMPartnerBalanceQry implements java.io.Serializable
{
	public String BPartnerID = ""; //商业伙伴编号
	public double Balance; //商业伙伴余额
	public double CreditLimit; //商业伙伴信用额度
	public String ServicesList = ""; //可选服务列表

	public String getBPartnerID()
	{
		return BPartnerID;
	}
	public void setBPartnerID(String BPartnerID)
	{
		this.BPartnerID = BPartnerID;
	}

	public double getBalance()
	{
		return Balance;
	}
	public void setBalance(double Balance)
	{
		this.Balance = Balance;
	}

	public double getCreditLimit()
	{
		return CreditLimit;
	}
	public void setCreditLimit(double CreditLimit)
	{
		this.CreditLimit = CreditLimit;
	}

	public String getServicesList()
	{
		return ServicesList;
	}
	public void setServicesList(String ServicesList)
	{
		this.ServicesList = ServicesList;
	}

}