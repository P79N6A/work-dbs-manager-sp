package com.dcdzsoft.dto.business;

/**
* 查询投递订单周期记录
*/

public class OutParamPTItemLifeCycleQry implements java.io.Serializable
{
	public long WaterID; //流水号
	public String PackageID = ""; //订单号
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String StaffID = ""; //操作员编号
	public String StaffName = ""; //操作员名称
	public java.sql.Timestamp LastModifyTime; //最后修改时间
	public String Remark = ""; //备注

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

	public String getStaffID()
	{
		return StaffID;
	}
	public void setStaffID(String StaffID)
	{
		this.StaffID = StaffID;
	}

	public String getStaffName()
	{
		return StaffName;
	}
	public void setStaffName(String StaffName)
	{
		this.StaffName = StaffName;
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