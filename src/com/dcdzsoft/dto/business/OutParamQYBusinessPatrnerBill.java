package com.dcdzsoft.dto.business;

/**
* 商业伙伴账单查询
*/

public class OutParamQYBusinessPatrnerBill implements java.io.Serializable
{
	public String TradeWaterID = ""; //交易流水号
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public java.sql.Timestamp BillTime; //账单生成时间
	public double BillAmt; //账单金额
	public String BillType = ""; //账单类型（1-充值，2-消费）
	public String BillTypeName = ""; //账单类型名称
	public double Balance; //账户余额
	public String PackageID = ""; //订单编号
	public String ServiceID = ""; //服务编号
	public String ServiceName = ""; //服务名称
	public String BillDetails = ""; //账单详情

	public String getTradeWaterID()
	{
		return TradeWaterID;
	}
	public void setTradeWaterID(String TradeWaterID)
	{
		this.TradeWaterID = TradeWaterID;
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

	public java.sql.Timestamp getBillTime()
	{
		return BillTime;
	}
	public void setBillTime(java.sql.Timestamp BillTime)
	{
		this.BillTime = BillTime;
	}

	public double getBillAmt()
	{
		return BillAmt;
	}
	public void setBillAmt(double BillAmt)
	{
		this.BillAmt = BillAmt;
	}

	public String getBillType()
	{
		return BillType;
	}
	public void setBillType(String BillType)
	{
		this.BillType = BillType;
	}

	public String getBillTypeName()
	{
		return BillTypeName;
	}
	public void setBillTypeName(String BillTypeName)
	{
		this.BillTypeName = BillTypeName;
	}

	public double getBalance()
	{
		return Balance;
	}
	public void setBalance(double Balance)
	{
		this.Balance = Balance;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
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

	public String getBillDetails()
	{
		return BillDetails;
	}
	public void setBillDetails(String BillDetails)
	{
		this.BillDetails = BillDetails;
	}

}