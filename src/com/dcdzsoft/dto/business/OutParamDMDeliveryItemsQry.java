package com.dcdzsoft.dto.business;

/**
* 寄件订单查询
*/

public class OutParamDMDeliveryItemsQry implements java.io.Serializable
{
	public String PackageID = ""; //订单号
	public String CreateTime = ""; //创建订单时间（yyyy-MM-dd
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String BPartnerID = ""; //商业伙伴编号
	public String BPartnerName = ""; //商业伙伴名称
	public String Address = ""; //商业伙伴地址
	public String Mobile = ""; //商业伙伴手机
	public String ServiceID = ""; //包裹寄存服务编号
	public String ServiceName = ""; //包裹寄存服务名称
	public String ParcelSize = ""; //包裹尺寸
	public String ParcelSizeName = ""; //包裹尺寸名称
	public double ServiceAmt; //包裹寄存服务费用
	public double CollectionAmt; //上门揽件费用
	public String TradeWaterNo = ""; //交易流水序号
	public String CustomerID = ""; //收件人编号
	public String CustomerName = ""; //收件人名称
	public String CustomerAddress = ""; //收件人地址
	public String CustomerMobile = ""; //收件人手机
	public String TerminalNo = ""; //柜体编号
	public String TerminalName = ""; //柜体名称
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String CollectZoneID = ""; //揽件区域编号
	public String CollectZoneName = ""; //揽件区域名称
	public String PostmanID = ""; //揽件员编号
	public String PostmanName = ""; //揽件员名称
	public String CollectionTime = ""; //揽件时间（yyyy-MM-dd
	public String ReturnFlag = ""; //退件服务标识（0-未选；1-选择）
	public String CollectionFlag = ""; //揽件服务选择（0-未选；1-选择）

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(String CreateTime)
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

	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String Address)
	{
		this.Address = Address;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
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

	public double getServiceAmt()
	{
		return ServiceAmt;
	}
	public void setServiceAmt(double ServiceAmt)
	{
		this.ServiceAmt = ServiceAmt;
	}

	public double getCollectionAmt()
	{
		return CollectionAmt;
	}
	public void setCollectionAmt(double CollectionAmt)
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

	public String getCollectionTime()
	{
		return CollectionTime;
	}
	public void setCollectionTime(String CollectionTime)
	{
		this.CollectionTime = CollectionTime;
	}

	public String getReturnFlag()
	{
		return ReturnFlag;
	}
	public void setReturnFlag(String ReturnFlag)
	{
		this.ReturnFlag = ReturnFlag;
	}

	public String getCollectionFlag()
	{
		return CollectionFlag;
	}
	public void setCollectionFlag(String CollectionFlag)
	{
		this.CollectionFlag = CollectionFlag;
	}

}