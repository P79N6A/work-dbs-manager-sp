package com.dcdzsoft.dto.business;

/**
* 订单详情查询
*/

public class InParamDMDeliveryItemDetailQry implements java.io.Serializable
{
	public String FunctionID = "341116"; //功能编号

	public String OperID = ""; //操作员编号
	public String ItemCode = ""; //订单号
	public String CustomerID = ""; //收件人编号
	public String CustomerMobile = ""; //收件人手机
	public String RefNo = ""; //参考编号
	public String QryType = ""; //查询类型（预留）
	public String BeginDate = ""; //开始日期（yyyy-MM-dd）
	public String EndDate = ""; //结束日期（yyyy-MM-dd）

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "341116";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "341116";
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

	public String getItemCode()
	{
		return ItemCode;
	}
	public void setItemCode(String ItemCode)
	{
		this.ItemCode = ItemCode;
	}

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getRefNo()
	{
		return RefNo;
	}
	public void setRefNo(String RefNo)
	{
		this.RefNo = RefNo;
	}

	public String getQryType()
	{
		return QryType;
	}
	public void setQryType(String QryType)
	{
		this.QryType = QryType;
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