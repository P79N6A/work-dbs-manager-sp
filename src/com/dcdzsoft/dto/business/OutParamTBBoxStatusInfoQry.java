package com.dcdzsoft.dto.business;

/**
* 箱体状态信息查询
*/

public class OutParamTBBoxStatusInfoQry implements java.io.Serializable
{
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String BoxNo = ""; //箱门编号
	public String BoxName = ""; //箱门名称
	public int DeskNo; //副柜编号
	public int DeskBoxNo; //箱门副柜编号
	public String BoxType = ""; //箱类型编号
	public String BoxTypeName = ""; //箱类型名称
	public String BoxStatus = ""; //箱状态
	public String BoxStatusName = ""; //箱状态名称
	public String BoxUsedStatus = ""; //箱使用状态
	public String BoxUsedStatusName = ""; //箱使用状态名称
	public String PackageID = ""; //订单号
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String CompanyID = ""; //包裹服务商编号
	public String CompanyName = ""; //包裹服务商名称

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

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public String getBoxName()
	{
		return BoxName;
	}
	public void setBoxName(String BoxName)
	{
		this.BoxName = BoxName;
	}

	public int getDeskNo()
	{
		return DeskNo;
	}
	public void setDeskNo(int DeskNo)
	{
		this.DeskNo = DeskNo;
	}

	public int getDeskBoxNo()
	{
		return DeskBoxNo;
	}
	public void setDeskBoxNo(int DeskBoxNo)
	{
		this.DeskBoxNo = DeskBoxNo;
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

	public String getBoxStatus()
	{
		return BoxStatus;
	}
	public void setBoxStatus(String BoxStatus)
	{
		this.BoxStatus = BoxStatus;
	}

	public String getBoxStatusName()
	{
		return BoxStatusName;
	}
	public void setBoxStatusName(String BoxStatusName)
	{
		this.BoxStatusName = BoxStatusName;
	}

	public String getBoxUsedStatus()
	{
		return BoxUsedStatus;
	}
	public void setBoxUsedStatus(String BoxUsedStatus)
	{
		this.BoxUsedStatus = BoxUsedStatus;
	}

	public String getBoxUsedStatusName()
	{
		return BoxUsedStatusName;
	}
	public void setBoxUsedStatusName(String BoxUsedStatusName)
	{
		this.BoxUsedStatusName = BoxUsedStatusName;
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

}