package com.dcdzsoft.dto.business;

/**
* BP账单数量查询
*/

public class InParamDMBPBillsQryCount implements java.io.Serializable
{
	public String FunctionID = "341045"; //功能编号

	public String OperID = ""; //管理员编号
	public String BPartnerID = ""; //商业伙伴编号
	public String BillType = ""; //账单类型：1-充值；2-消费
	public String TradeWaterID = ""; //交易流水号
	public String ServiceID = ""; //服务编号
	public String PackageID = ""; //包裹单号
	public java.sql.Date BeginDate; //开始日期
	public java.sql.Date EndDate; //结束日期

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "341045";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "341045";
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

	public String getBillType()
	{
		return BillType;
	}
	public void setBillType(String BillType)
	{
		this.BillType = BillType;
	}

	public String getTradeWaterID()
	{
		return TradeWaterID;
	}
	public void setTradeWaterID(String TradeWaterID)
	{
		this.TradeWaterID = TradeWaterID;
	}

	public String getServiceID()
	{
		return ServiceID;
	}
	public void setServiceID(String ServiceID)
	{
		this.ServiceID = ServiceID;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public java.sql.Date getBeginDate()
	{
		return BeginDate;
	}
	public void setBeginDate(java.sql.Date BeginDate)
	{
		this.BeginDate = BeginDate;
	}

	public java.sql.Date getEndDate()
	{
		return EndDate;
	}
	public void setEndDate(java.sql.Date EndDate)
	{
		this.EndDate = EndDate;
	}

}