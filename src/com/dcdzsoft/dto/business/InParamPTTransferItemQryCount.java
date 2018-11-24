package com.dcdzsoft.dto.business;

/**
* 待发送订单数量查询
*/

public class InParamPTTransferItemQryCount implements java.io.Serializable
{
	public String FunctionID = "331055"; //功能编号

	public String OperID = ""; //管理员编号
	public long WaterID; //流水号
	public String PackageID = ""; //订单号
	public String PPCID = ""; //包裹处理中心编号
	public String ZoneID = ""; //分拣区域编号
	public String ItemType = ""; //订单类型
	public String SendStatus = ""; //发送状态
	public String TransferOrderID = ""; //转运清单编号
	public String ItemStatus = ""; //订单状态
	public String QryFlag = ""; //查询标志
	public java.sql.Date BeginDate; //开始日期
	public java.sql.Date EndDate; //结束日期

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331055";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331055";
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

	public String getPPCID()
	{
		return PPCID;
	}
	public void setPPCID(String PPCID)
	{
		this.PPCID = PPCID;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getItemType()
	{
		return ItemType;
	}
	public void setItemType(String ItemType)
	{
		this.ItemType = ItemType;
	}

	public String getSendStatus()
	{
		return SendStatus;
	}
	public void setSendStatus(String SendStatus)
	{
		this.SendStatus = SendStatus;
	}

	public String getTransferOrderID()
	{
		return TransferOrderID;
	}
	public void setTransferOrderID(String TransferOrderID)
	{
		this.TransferOrderID = TransferOrderID;
	}

	public String getItemStatus()
	{
		return ItemStatus;
	}
	public void setItemStatus(String ItemStatus)
	{
		this.ItemStatus = ItemStatus;
	}

	public String getQryFlag()
	{
		return QryFlag;
	}
	public void setQryFlag(String QryFlag)
	{
		this.QryFlag = QryFlag;
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