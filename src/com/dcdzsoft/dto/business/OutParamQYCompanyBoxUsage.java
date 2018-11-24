package com.dcdzsoft.dto.business;

/**
* 格口使用统计
*/

public class OutParamQYCompanyBoxUsage implements java.io.Serializable
{
	public String PackageID = ""; //订单号
	public java.sql.Timestamp CreateTime; //创建订单时间
	public String TerminalNo = ""; //设备号
	public String BoxNo = ""; //箱号
	public String BoxType = ""; //箱门类型编号
	public String BoxTypeName = ""; //箱门类型名称
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String CompanyID = ""; //包裹服务商编号
	public String CompanyName = ""; //包裹服务商名称
	public String DADFlag = ""; //直投标志
	public int DropNum; //订单投递次数
	public java.sql.Timestamp StoredTime; //存物时间
	public java.sql.Date StoredDate; //存物日期
	public java.sql.Timestamp TakedTime; //取件时间
	public int Days; //在箱时间
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String RunStatus = ""; //订单执行状态
	public String RunStatusName = ""; //订单执行状态名称

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

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public String getBoxType()
	{
		return BoxType;
	}
	public void setBoxType(String BoxType)
	{
		this.BoxType = BoxType;
	}

	public String getBoxTypeName()
	{
		return BoxTypeName;
	}
	public void setBoxTypeName(String BoxTypeName)
	{
		this.BoxTypeName = BoxTypeName;
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

	public String getDADFlag()
	{
		return DADFlag;
	}
	public void setDADFlag(String DADFlag)
	{
		this.DADFlag = DADFlag;
	}

	public int getDropNum()
	{
		return DropNum;
	}
	public void setDropNum(int DropNum)
	{
		this.DropNum = DropNum;
	}

	public java.sql.Timestamp getStoredTime()
	{
		return StoredTime;
	}
	public void setStoredTime(java.sql.Timestamp StoredTime)
	{
		this.StoredTime = StoredTime;
	}

	public java.sql.Date getStoredDate()
	{
		return StoredDate;
	}
	public void setStoredDate(java.sql.Date StoredDate)
	{
		this.StoredDate = StoredDate;
	}

	public java.sql.Timestamp getTakedTime()
	{
		return TakedTime;
	}
	public void setTakedTime(java.sql.Timestamp TakedTime)
	{
		this.TakedTime = TakedTime;
	}

	public int getDays()
	{
		return Days;
	}
	public void setDays(int Days)
	{
		this.Days = Days;
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

	public String getRunStatus()
	{
		return RunStatus;
	}
	public void setRunStatus(String RunStatus)
	{
		this.RunStatus = RunStatus;
	}

	public String getRunStatusName()
	{
		return RunStatusName;
	}
	public void setRunStatusName(String RunStatusName)
	{
		this.RunStatusName = RunStatusName;
	}

}