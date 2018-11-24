package com.dcdzsoft.dto.business;

/**
* 商业伙伴信息查询
*/

public class OutParamIMBusiPartnerQry implements java.io.Serializable
{
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public String CollectZoneID = ""; //揽件区域编号
	public String CollectZoneName = ""; //商业伙伴名称
	public String Address = ""; //地址
	public String Email = ""; //邮箱
	public String Mobile = ""; //电话
	public String Username = ""; //用户名
	public double Balance; //余额
	public String CreditFlag = ""; //授信标志
	public String CreditFlagName = ""; //授信标志名称
	public double CreditLimit; //信用额度
	public int Discount; //折扣
	public String CollectionServiceFlag = ""; //揽件服务标识
	public String CollectServFlagName = ""; //揽件服务名称
	public String ReturnServiceFlag = ""; //退件服务标识
	public String ReturnServFlagName = ""; //退件服务标识名称
	public String Remark = ""; //备注

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

	public String getCollectZoneID()
	{
		return CollectZoneID;
	}
	public void setCollectZoneID(String CollectZoneID)
	{
		this.CollectZoneID = CollectZoneID;
	}

	public String getCollectZoneName()
	{
		return CollectZoneName;
	}
	public void setCollectZoneName(String CollectZoneName)
	{
		this.CollectZoneName = CollectZoneName;
	}

	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String Address)
	{
		this.Address = Address;
	}

	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String Email)
	{
		this.Email = Email;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}

	public String getUsername()
	{
		return Username;
	}
	public void setUsername(String Username)
	{
		this.Username = Username;
	}

	public double getBalance()
	{
		return Balance;
	}
	public void setBalance(double Balance)
	{
		this.Balance = Balance;
	}

	public String getCreditFlag()
	{
		return CreditFlag;
	}
	public void setCreditFlag(String CreditFlag)
	{
		this.CreditFlag = CreditFlag;
	}

	public String getCreditFlagName()
	{
		return CreditFlagName;
	}
	public void setCreditFlagName(String CreditFlagName)
	{
		this.CreditFlagName = CreditFlagName;
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

	public String getCollectionServiceFlag()
	{
		return CollectionServiceFlag;
	}
	public void setCollectionServiceFlag(String CollectionServiceFlag)
	{
		this.CollectionServiceFlag = CollectionServiceFlag;
	}

	public String getCollectServFlagName()
	{
		return CollectServFlagName;
	}
	public void setCollectServFlagName(String CollectServFlagName)
	{
		this.CollectServFlagName = CollectServFlagName;
	}

	public String getReturnServiceFlag()
	{
		return ReturnServiceFlag;
	}
	public void setReturnServiceFlag(String ReturnServiceFlag)
	{
		this.ReturnServiceFlag = ReturnServiceFlag;
	}

	public String getReturnServFlagName()
	{
		return ReturnServFlagName;
	}
	public void setReturnServFlagName(String ReturnServFlagName)
	{
		this.ReturnServFlagName = ReturnServFlagName;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}