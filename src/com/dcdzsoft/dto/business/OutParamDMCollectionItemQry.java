package com.dcdzsoft.dto.business;

/**
* 寄件订单信息查询
*/

public class OutParamDMCollectionItemQry implements java.io.Serializable
{
	public String PackageID = ""; //订单号
	public java.sql.Timestamp CreateTime; //创建订单时间
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public int Discount; //商业伙伴折扣
	public String ServiceID = ""; //包裹寄存服务编号
	public String ServiceName = ""; //包裹寄存服务名称
	public int ServiceAmt; //(16,3)
	public String CollectionFlag = ""; //上门揽件标识
	public int CollectionAmt; //(16,3)
	public String TradeWaterNo = ""; //交易流水序号
	public String PPCID = ""; //包裹处理中心编号
	public String CompanyID = ""; //包裹服务商编号
	public String CompanyName = ""; //包裹服务商名称
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String CustomerID = ""; //取件人编号
	public String CustomerName = ""; //取件人名称
	public String CustomerAddress = ""; //取件人地址
	public String CustomerMobile = ""; //取件人手机
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String BoxNo = ""; //箱号
	public String CollectionType = ""; //揽件方式
	public String CollectionAgentID = ""; //揽件员编号
	public java.sql.Timestamp CollectionTime; //揽件时间
	public String TransferOrderID = ""; //运单号
	public String PrintedR4 = ""; //打印标识R4
	public String PrintedR5 = ""; //打印标识R5
	public java.sql.Timestamp LastModifyTime; //最后修改时间
	public String Remark = ""; //备注

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public java.sql.Timestamp getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(java.sql.Timestamp CreateTime)
	{
		this.CreateTime = CreateTime;
	}

	public String getItemStatus()
	{
		return ItemStatus;
	}
	public void setItemStatus(String ItemStatus)
	{
		this.ItemStatus = ItemStatus;
	}

	public String getItemStatusName()
	{
		return ItemStatusName;
	}
	public void setItemStatusName(String ItemStatusName)
	{
		this.ItemStatusName = ItemStatusName;
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

	public int getDiscount()
	{
		return Discount;
	}
	public void setDiscount(int Discount)
	{
		this.Discount = Discount;
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

	public int getServiceAmt()
	{
		return ServiceAmt;
	}
	public void setServiceAmt(int ServiceAmt)
	{
		this.ServiceAmt = ServiceAmt;
	}

	public String getCollectionFlag()
	{
		return CollectionFlag;
	}
	public void setCollectionFlag(String CollectionFlag)
	{
		this.CollectionFlag = CollectionFlag;
	}

	public int getCollectionAmt()
	{
		return CollectionAmt;
	}
	public void setCollectionAmt(int CollectionAmt)
	{
		this.CollectionAmt = CollectionAmt;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public String getPPCID()
	{
		return PPCID;
	}
	public void setPPCID(String PPCID)
	{
		this.PPCID = PPCID;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String CompanyName)
	{
		this.CompanyName = CompanyName;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getZoneName()
	{
		return ZoneName;
	}
	public void setZoneName(String ZoneName)
	{
		this.ZoneName = ZoneName;
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

	public String getCustomerAddress()
	{
		return CustomerAddress;
	}
	public void setCustomerAddress(String CustomerAddress)
	{
		this.CustomerAddress = CustomerAddress;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getTerminalName()
	{
		return TerminalName;
	}
	public void setTerminalName(String TerminalName)
	{
		this.TerminalName = TerminalName;
	}

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public String getCollectionType()
	{
		return CollectionType;
	}
	public void setCollectionType(String CollectionType)
	{
		this.CollectionType = CollectionType;
	}

	public String getCollectionAgentID()
	{
		return CollectionAgentID;
	}
	public void setCollectionAgentID(String CollectionAgentID)
	{
		this.CollectionAgentID = CollectionAgentID;
	}

	public java.sql.Timestamp getCollectionTime()
	{
		return CollectionTime;
	}
	public void setCollectionTime(java.sql.Timestamp CollectionTime)
	{
		this.CollectionTime = CollectionTime;
	}

	public String getTransferOrderID()
	{
		return TransferOrderID;
	}
	public void setTransferOrderID(String TransferOrderID)
	{
		this.TransferOrderID = TransferOrderID;
	}

	public String getPrintedR4()
	{
		return PrintedR4;
	}
	public void setPrintedR4(String PrintedR4)
	{
		this.PrintedR4 = PrintedR4;
	}

	public String getPrintedR5()
	{
		return PrintedR5;
	}
	public void setPrintedR5(String PrintedR5)
	{
		this.PrintedR5 = PrintedR5;
	}

	public java.sql.Timestamp getLastModifyTime()
	{
		return LastModifyTime;
	}
	public void setLastModifyTime(java.sql.Timestamp LastModifyTime)
	{
		this.LastModifyTime = LastModifyTime;
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