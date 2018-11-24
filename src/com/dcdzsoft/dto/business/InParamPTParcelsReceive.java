package com.dcdzsoft.dto.business;

/**
* 确认接收包裹
*/

public class InParamPTParcelsReceive implements java.io.Serializable
{
	public String FunctionID = "331021"; //功能编号

	public String OperID = ""; //管理员编号
	public String PackageID = ""; //订单号
	public String ItemStatus = ""; //当前订单状态
	public String ZoneID = ""; //分拣区域编号
	public String PPCID = ""; //包裹处理中心编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331021";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331021";
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

	public String getItemStatus()
	{
		return ItemStatus;
	}
	public void setItemStatus(String ItemStatus)
	{
		this.ItemStatus = ItemStatus;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getPPCID()
	{
		return PPCID;
	}
	public void setPPCID(String PPCID)
	{
		this.PPCID = PPCID;
	}

}