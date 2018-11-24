package com.dcdzsoft.dto.business;

/**
* 运营报表统计
*/

public class OutParamQYOperationReport implements java.io.Serializable
{
	public String PackageID = ""; //设备号
	public String OperID = ""; //操作员编号（管理员）
	public String OperName = ""; //管理员姓名
	public String OperType = ""; //管理员类型
	public String OperTypeName = ""; //管理员类型名称
	public String ZoneID = ""; //分拣区域编号
	public java.sql.Timestamp LastModifyTime; //最后修改时间
	public String ItemStatus = ""; //统计包裹状态
	public String ItemStatusName = ""; //包裹状态名称
	public int StatusTime; //状态耗时
	public int StatusTimeOut; //状态超时时间
	public int StatusNum; //在此状态的次数
	public int StatusTimeOutNum; //超时次数
	public int AvgStatusTime; //平均耗时
	public int MaxStatusTime; //最大耗时
	public int MinStatusTime; //最少耗时
	public int MaxStatusTimeOut; //最大超时时间
	public int AvgStatusTimeOut; //平均超时时间

	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
	}

	public String getOperName()
	{
		return OperName;
	}
	public void setOperName(String OperName)
	{
		this.OperName = OperName;
	}

	public String getOperType()
	{
		return OperType;
	}
	public void setOperType(String OperType)
	{
		this.OperType = OperType;
	}

	public String getOperTypeName()
	{
		return OperTypeName;
	}
	public void setOperTypeName(String OperTypeName)
	{
		this.OperTypeName = OperTypeName;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public java.sql.Timestamp getLastModifyTime()
	{
		return LastModifyTime;
	}
	public void setLastModifyTime(java.sql.Timestamp LastModifyTime)
	{
		this.LastModifyTime = LastModifyTime;
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

	public int getStatusTime()
	{
		return StatusTime;
	}
	public void setStatusTime(int StatusTime)
	{
		this.StatusTime = StatusTime;
	}

	public int getStatusTimeOut()
	{
		return StatusTimeOut;
	}
	public void setStatusTimeOut(int StatusTimeOut)
	{
		this.StatusTimeOut = StatusTimeOut;
	}

	public int getStatusNum()
	{
		return StatusNum;
	}
	public void setStatusNum(int StatusNum)
	{
		this.StatusNum = StatusNum;
	}

	public int getStatusTimeOutNum()
	{
		return StatusTimeOutNum;
	}
	public void setStatusTimeOutNum(int StatusTimeOutNum)
	{
		this.StatusTimeOutNum = StatusTimeOutNum;
	}

	public int getAvgStatusTime()
	{
		return AvgStatusTime;
	}
	public void setAvgStatusTime(int AvgStatusTime)
	{
		this.AvgStatusTime = AvgStatusTime;
	}

	public int getMaxStatusTime()
	{
		return MaxStatusTime;
	}
	public void setMaxStatusTime(int MaxStatusTime)
	{
		this.MaxStatusTime = MaxStatusTime;
	}

	public int getMinStatusTime()
	{
		return MinStatusTime;
	}
	public void setMinStatusTime(int MinStatusTime)
	{
		this.MinStatusTime = MinStatusTime;
	}

	public int getMaxStatusTimeOut()
	{
		return MaxStatusTimeOut;
	}
	public void setMaxStatusTimeOut(int MaxStatusTimeOut)
	{
		this.MaxStatusTimeOut = MaxStatusTimeOut;
	}

	public int getAvgStatusTimeOut()
	{
		return AvgStatusTimeOut;
	}
	public void setAvgStatusTimeOut(int AvgStatusTimeOut)
	{
		this.AvgStatusTimeOut = AvgStatusTimeOut;
	}

}