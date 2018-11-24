package com.dcdzsoft.dto.business;

/**
* BP账单查询
*/

public class OutParamDMBPBillsQry implements java.io.Serializable
{
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public String TradeWaterID = ""; //交易流水号
	public java.sql.Timestamp BillTime; //账单日期
	public String BillType = ""; //账单类型标识
	public String BillTypeName = ""; //账单类型名称
	public double BillAmt; //账单金额
	public String BillDetails = ""; //账单明细
	public double Balance; //账户余额
	public String PackageID = ""; //包裹单号
	public String ServiceID = ""; //包裹寄存服务编号
	public String ServiceName = ""; //包裹寄存服务名称

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

	public String getTradeWaterID()
	{
		return TradeWaterID;
	}
	public void setTradeWaterID(String TradeWaterID)
	{
		this.TradeWaterID = TradeWaterID;
	}

	public java.sql.Timestamp getBillTime()
	{
		return BillTime;
	}
	public void setBillTime(java.sql.Timestamp BillTime)
	{
		this.BillTime = BillTime;
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

	public double getBillAmt()
	{
		return BillAmt;
	}
	public void setBillAmt(double BillAmt)
	{
		this.BillAmt = BillAmt;
	}

	public String getBillDetails()
	{
		return BillDetails;
	}
	public void setBillDetails(String BillDetails)
	{
		this.BillDetails = BillDetails;
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

}