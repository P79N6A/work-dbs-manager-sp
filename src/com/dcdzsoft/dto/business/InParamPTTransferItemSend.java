package com.dcdzsoft.dto.business;

/**
* 发送订单数量到PPC服务器
*/

public class InParamPTTransferItemSend implements java.io.Serializable
{
	public String FunctionID = "331058"; //功能编号

	public String OperID = ""; //管理员编号
	public long WaterID; //流水号
	public String PackageID = ""; //订单号（流水号集合）
	public String PPCID = ""; //包裹处理中心编号
	public String ZoneID = ""; //分拣区域编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331058";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331058";
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

}