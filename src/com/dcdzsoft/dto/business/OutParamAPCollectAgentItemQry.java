package com.dcdzsoft.dto.business;

/**
* 揽件员查询包裹订单信息
*/

public class OutParamAPCollectAgentItemQry implements java.io.Serializable
{
	public String ItemCode = ""; //订单号
	public String CreateTime = ""; //创建订单时间
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String PartnerID = ""; //商业伙伴编号
	public String PartnerName = ""; //商业伙伴名称
	public String CollectionFlag = ""; //上门揽件标识
	public String CollectionFlagName = ""; //上门揽件标识名称
	public String ReturnFlag = ""; //退件服务标志名称
	public String ReturnFlagName = ""; //退件服务标志名称
	public String CustomerAddress = ""; //取件人地址
	public String CustomerMobile = ""; //取件人手机
	public String ParcelSize = ""; //包裹尺寸
	public String ParcelSizeName = ""; //包裹尺寸名称
	public String CollectRegionID = ""; //揽件区域编号
	public String CollectRegionName = ""; //揽件区域名称

	public String getItemCode()
	{
		return ItemCode;
	}
	public void setItemCode(String ItemCode)
	{
		this.ItemCode = ItemCode;
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

	public String getPartnerID()
	{
		return PartnerID;
	}
	public void setPartnerID(String PartnerID)
	{
		this.PartnerID = PartnerID;
	}

	public String getPartnerName()
	{
		return PartnerName;
	}
	public void setPartnerName(String PartnerName)
	{
		this.PartnerName = PartnerName;
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

	public String getCollectRegionID()
	{
		return CollectRegionID;
	}
	public void setCollectRegionID(String CollectRegionID)
	{
		this.CollectRegionID = CollectRegionID;
	}

	public String getCollectRegionName()
	{
		return CollectRegionName;
	}
	public void setCollectRegionName(String CollectRegionName)
	{
		this.CollectRegionName = CollectRegionName;
	}

}