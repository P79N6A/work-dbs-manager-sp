package com.dcdzsoft.dto.business;

/**
* 寄件订单查询
*/

public class InParamDMDeliveryItemsQry implements java.io.Serializable
{
	public String FunctionID = "341114"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String BPartnerID = ""; //商业伙伴编号
	public String PackageID = ""; //订单号
	public String ItemStatus = ""; //订单状态
	public String CustomerName = ""; //收件人名称
	public String CustomerMobile = ""; //收件人手机
	public String BeginDate = ""; //开始日期（yyyy-MM-dd）
	public String EndDate = ""; //结束日期（yyyy-MM-dd）


	public int getRecordBegin()
	{
		return recordBegin;
	}
	public void setRecordBegin(int recordBegin)
	{
		this.recordBegin = recordBegin;
	}


	public int getRecordNum()
	{
		return recordNum;
	}
	public void setRecordNum(int recordNum)
	{
		this.recordNum = recordNum;
	}

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "341114";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "341114";
		else
			this.FunctionID = FunctionID;
	}

	public String getBPartnerID()
	{
		return BPartnerID;
	}
	public void setBPartnerID(String BPartnerID)
	{
		this.BPartnerID = BPartnerID;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getItemStatus()
	{
		return ItemStatus;
	}
	public void setItemStatus(String ItemStatus)
	{
		this.ItemStatus = ItemStatus;
	}

	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String CustomerName)
	{
		this.CustomerName = CustomerName;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getBeginDate()
	{
		return BeginDate;
	}
	public void setBeginDate(String BeginDate)
	{
		this.BeginDate = BeginDate;
	}

	public String getEndDate()
	{
		return EndDate;
	}
	public void setEndDate(String EndDate)
	{
		this.EndDate = EndDate;
	}

}