package com.dcdzsoft.dto.business;

/**
* 用户登录（包括商业合作伙伴，等）
*/

public class OutParamDMBusinessPartnerLogin implements java.io.Serializable
{
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public String Username = ""; //登录用户名
	public String UserType = ""; //登录用户类型（4-BusinessPartner，）
	public double Balance; //商业伙伴余额
	public double CreditLimit; //商业伙伴信用额度
	public int Discount; //商业伙伴折扣（%）
	public String CollectionFlag = ""; //揽件服务标识
	public String ReturnFlag = ""; //退件服务标识

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

	public String getUsername()
	{
		return Username;
	}
	public void setUsername(String Username)
	{
		this.Username = Username;
	}

	public String getUserType()
	{
		return UserType;
	}
	public void setUserType(String UserType)
	{
		this.UserType = UserType;
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

	public int getDiscount()
	{
		return Discount;
	}
	public void setDiscount(int Discount)
	{
		this.Discount = Discount;
	}

	public String getCollectionFlag()
	{
		return CollectionFlag;
	}
	public void setCollectionFlag(String CollectionFlag)
	{
		this.CollectionFlag = CollectionFlag;
	}

	public String getReturnFlag()
	{
		return ReturnFlag;
	}
	public void setReturnFlag(String ReturnFlag)
	{
		this.ReturnFlag = ReturnFlag;
	}

}