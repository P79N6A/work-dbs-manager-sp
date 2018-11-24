package com.dcdzsoft.dto.business;

/**
* 从投递柜台取件
*/

public class InParamPTCounterPickup implements java.io.Serializable
{
	public String FunctionID = "331037"; //功能编号

	public String OperID = ""; //管理员编号
	public String PackageID = ""; //订单号
	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String DropAgentID = ""; //投递员编号
	public String DynamicCode = ""; //动态码
	public String CompanyID = ""; //包裹处理中心编号
	public String BoxNo = ""; //箱门编号
	public String ItemStatus = ""; //当前订单状态
	public java.sql.Timestamp StoredTime; //存物时间
	public java.sql.Timestamp OccurTime; //发生时间
	public String CustomerID = ""; //取件人编号
	public String CustomerMobile = ""; //取件人手机
	public String CustomerName = ""; //取件人姓名
	public String OpenBoxKey = ""; //开箱密码
	public String TakedWay = ""; //取件方式

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331037";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331037";
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

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public String getDropAgentID()
	{
		return DropAgentID;
	}
	public void setDropAgentID(String DropAgentID)
	{
		this.DropAgentID = DropAgentID;
	}

	public String getDynamicCode()
	{
		return DynamicCode;
	}
	public void setDynamicCode(String DynamicCode)
	{
		this.DynamicCode = DynamicCode;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public String getItemStatus()
	{
		return ItemStatus;
	}
	public void setItemStatus(String ItemStatus)
	{
		this.ItemStatus = ItemStatus;
	}

	public java.sql.Timestamp getStoredTime()
	{
		return StoredTime;
	}
	public void setStoredTime(java.sql.Timestamp StoredTime)
	{
		this.StoredTime = StoredTime;
	}

	public java.sql.Timestamp getOccurTime()
	{
		return OccurTime;
	}
	public void setOccurTime(java.sql.Timestamp OccurTime)
	{
		this.OccurTime = OccurTime;
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

	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String CustomerName)
	{
		this.CustomerName = CustomerName;
	}

	public String getOpenBoxKey()
	{
		return OpenBoxKey;
	}
	public void setOpenBoxKey(String OpenBoxKey)
	{
		this.OpenBoxKey = OpenBoxKey;
	}

	public String getTakedWay()
	{
		return TakedWay;
	}
	public void setTakedWay(String TakedWay)
	{
		this.TakedWay = TakedWay;
	}

}