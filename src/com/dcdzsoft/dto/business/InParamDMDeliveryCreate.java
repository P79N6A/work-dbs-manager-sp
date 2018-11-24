package com.dcdzsoft.dto.business;

/**
* 创建寄件订单
*/

public class InParamDMDeliveryCreate implements java.io.Serializable
{
	public String FunctionID = "341011"; //功能编号

	public String BPartnerID = ""; //商业伙伴编号
	public String ServiceID = ""; //服务类型编号
	public String CollectionOption = ""; //揽件服务选择（0-未选；1-选择）
	public String ReturnOption = ""; //退件服务选择（0-未选；1-选择）
	public String ParcelSize = ""; //包裹尺寸
	public String TerminalNo = ""; //自提柜编号（收件地）
	public String CustomerID = ""; //收件人编号
	public String CustomerName = ""; //收件人名称
	public String CustomerMobile = ""; //收件人手机
	public String CustomerAddress = ""; //收件人地址

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "341011";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "341011";
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

	public String getServiceID()
	{
		return ServiceID;
	}
	public void setServiceID(String ServiceID)
	{
		this.ServiceID = ServiceID;
	}

	public String getCollectionOption()
	{
		return CollectionOption;
	}
	public void setCollectionOption(String CollectionOption)
	{
		this.CollectionOption = CollectionOption;
	}

	public String getReturnOption()
	{
		return ReturnOption;
	}
	public void setReturnOption(String ReturnOption)
	{
		this.ReturnOption = ReturnOption;
	}

	public String getParcelSize()
	{
		return ParcelSize;
	}
	public void setParcelSize(String ParcelSize)
	{
		this.ParcelSize = ParcelSize;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
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

	public String getCustomerAddress()
	{
		return CustomerAddress;
	}
	public void setCustomerAddress(String CustomerAddress)
	{
		this.CustomerAddress = CustomerAddress;
	}

}