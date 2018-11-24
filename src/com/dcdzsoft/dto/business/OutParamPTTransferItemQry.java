package com.dcdzsoft.dto.business;

/**
* 待发送订单查询
*/

public class OutParamPTTransferItemQry implements java.io.Serializable
{
	public long WaterID; //流水号
	public String PackageID = ""; //订单号
	public java.sql.Timestamp CreateTime; //创建订单时间
	public String PPCID = ""; //包裹处理中心编号
	public String PPCName = ""; //包裹处理中心名称
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String ItemType = ""; //订单类型
	public String ItemTypeName = ""; //订单类型名称
	public String SendStatus = ""; //发送状态
	public String SendStatusName = ""; //发送状态名称
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String TransferOrderID = ""; //转运清单编号
	public int ReSendNum; //重发次数

	public long getWaterID()
	{
		return WaterID;
	}
	public void setWaterID(long WaterID)
	{
		this.WaterID = WaterID;
	}

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

	public String getItemType()
	{
		return ItemType;
	}
	public void setItemType(String ItemType)
	{
		this.ItemType = ItemType;
	}

	public String getItemTypeName()
	{
		return ItemTypeName;
	}
	public void setItemTypeName(String ItemTypeName)
	{
		this.ItemTypeName = ItemTypeName;
	}

	public String getSendStatus()
	{
		return SendStatus;
	}
	public void setSendStatus(String SendStatus)
	{
		this.SendStatus = SendStatus;
	}

	public String getSendStatusName()
	{
		return SendStatusName;
	}
	public void setSendStatusName(String SendStatusName)
	{
		this.SendStatusName = SendStatusName;
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

	public String getTransferOrderID()
	{
		return TransferOrderID;
	}
	public void setTransferOrderID(String TransferOrderID)
	{
		this.TransferOrderID = TransferOrderID;
	}

	public int getReSendNum()
	{
		return ReSendNum;
	}
	public void setReSendNum(int ReSendNum)
	{
		this.ReSendNum = ReSendNum;
	}

}