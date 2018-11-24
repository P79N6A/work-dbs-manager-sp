package com.dcdzsoft.dto.business;

/**
* 寄件订单查询
*/

public class OutParamDMDeliveryQry implements java.io.Serializable
{
	public String PackageID = ""; //订单号
	public java.sql.Timestamp CreateTime; //创建订单时间
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public String ServiceID = ""; //包裹寄存服务编号
	public String ServiceName = ""; //包裹寄存服务名称
	public int ServiceAmt; //(16,3)
	public String CollectionFlag = ""; //上门揽件标识
	public String CollectionFlagName = ""; //上门揽件标识名称
	public int CollectionAmt; //(16,3)
	public String ReturnFlag = ""; //退件服务标志名称
	public String ReturnFlagName = ""; //退件服务标志名称
	public String TradeWaterNo = ""; //交易流水序号
	public String CompanyID = ""; //包裹服务商编号
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String PPCID = ""; //包裹处理中心编号
	public String PPCName = ""; //包裹处理中心名称
	public String CustomerID = ""; //取件人编号
	public String CustomerName = ""; //取件人名称
	public String CustomerAddress = ""; //取件人地址
	public String CustomerMobile = ""; //取件人手机
	public String ParcelSize = ""; //包裹尺寸
	public String ParcelSizeName = ""; //包裹尺寸名称
	public String CollectZoneID = ""; //揽件区域编号
	public String CollectZoneName = ""; //揽件区域名称
	public String TerminalNo = ""; //柜体编号
	public String TerminalName = ""; //柜体名称
	public String PostmanID = ""; //揽件员编号
	public String PostmanName = ""; //揽件员名称
	public java.sql.Timestamp CollectionTime; //揽件时间
	public String TransferOrderID = ""; //运单号
	public java.sql.Timestamp LastModifyTime; //最后修改时间
	public String PrintedR4 = ""; //打印标识R4
	public String PrintedR5 = ""; //打印标识R5
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

	public String getCollectionFlagName()
	{
		return CollectionFlagName;
	}
	public void setCollectionFlagName(String CollectionFlagName)
	{
		this.CollectionFlagName = CollectionFlagName;
	}

	public int getCollectionAmt()
	{
		return CollectionAmt;
	}
	public void setCollectionAmt(int CollectionAmt)
	{
		this.CollectionAmt = CollectionAmt;
	}

	public String getReturnFlag()
	{
		return ReturnFlag;
	}
	public void setReturnFlag(String ReturnFlag)
	{
		this.ReturnFlag = ReturnFlag;
	}

	public String getReturnFlagName()
	{
		return ReturnFlagName;
	}
	public void setReturnFlagName(String ReturnFlagName)
	{
		this.ReturnFlagName = ReturnFlagName;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
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

	public String getPPCID()
	{
		return PPCID;
	}
	public void setPPCID(String PPCID)
	{
		this.PPCID = PPCID;
	}

	public String getPPCName()
	{
		return PPCName;
	}
	public void setPPCName(String PPCName)
	{
		this.PPCName = PPCName;
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

	public String getParcelSize()
	{
		return ParcelSize;
	}
	public void setParcelSize(String ParcelSize)
	{
		this.ParcelSize = ParcelSize;
	}

	public String getParcelSizeName()
	{
		return ParcelSizeName;
	}
	public void setParcelSizeName(String ParcelSizeName)
	{
		this.ParcelSizeName = ParcelSizeName;
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

	public String getPostmanID()
	{
		return PostmanID;
	}
	public void setPostmanID(String PostmanID)
	{
		this.PostmanID = PostmanID;
	}

	public String getPostmanName()
	{
		return PostmanName;
	}
	public void setPostmanName(String PostmanName)
	{
		this.PostmanName = PostmanName;
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

	public java.sql.Timestamp getLastModifyTime()
	{
		return LastModifyTime;
	}
	public void setLastModifyTime(java.sql.Timestamp LastModifyTime)
	{
		this.LastModifyTime = LastModifyTime;
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

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}