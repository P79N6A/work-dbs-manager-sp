package com.dcdzsoft.dto.business;

/**
* 商业伙伴信息修改
*/

public class InParamIMBusiPartnerMod implements java.io.Serializable
{
	public String FunctionID = "250052"; //功能编号

	public String OperID = ""; //管理员编号
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public String CollectZoneID = ""; //揽件区域编号
	public String Address = ""; //地址
	public String Email = ""; //邮箱
	public String Mobile = ""; //电话
	public String Username = ""; //用户名
	public double Balance; //余额
	public String CreditFlag = ""; //授信标志
	public double CreditLimit; //信用额度
	public int Discount; //折扣
	public String CollectionServiceFlag = ""; //揽件服务标识
	public String ReturnServiceFlag = ""; //退件服务标识
	public double Latitude; //纬度
	public double Longitude; //经度
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250052";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250052";
		else
			this.FunctionID = FunctionID;
	}

	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
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

	public String getCollectZoneID()
	{
		return CollectZoneID;
	}
	public void setCollectZoneID(String CollectZoneID)
	{
		this.CollectZoneID = CollectZoneID;
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

	public String getReturnServiceFlag()
	{
		return ReturnServiceFlag;
	}
	public void setReturnServiceFlag(String ReturnServiceFlag)
	{
		this.ReturnServiceFlag = ReturnServiceFlag;
	}

	public double getLatitude()
	{
		return Latitude;
	}
	public void setLatitude(double Latitude)
	{
		this.Latitude = Latitude;
	}

	public double getLongitude()
	{
		return Longitude;
	}
	public void setLongitude(double Longitude)
	{
		this.Longitude = Longitude;
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