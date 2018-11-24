package com.dcdzsoft.dto.business;

/**
* 待投递订单信息查询
*/

public class OutParamPTDeliveryItemQry implements java.io.Serializable
{
	public String PackageID = ""; //订单号
	public java.sql.Timestamp CreateTime; //创建订单时间
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String RunStatus = ""; //订单执行状态
	public String RunStatusName = ""; //订单执行状态名称
	public String PPCID = ""; //包裹处理中心编号
	public String PPCName = ""; //包裹处理中心名称
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String CompanyID = ""; //包裹服务商编号
	public String CompanyName = ""; //包裹服务商名称
	public String RefNo = ""; //参考编号
	public String CustomerID = ""; //取件人编号
	public String CustomerName = ""; //取件人名称
	public String CustomerAddress = ""; //取件人地址
	public String CustomerMobile = ""; //取件人手机
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String BoxType = ""; //箱门类型编号
	public String BoxTypeName = ""; //箱门类型名称
	public String BoxNo = ""; //箱号
	public String DropAgentID = ""; //投递员编号
	public String ReturnAgentID = ""; //回收员编号
	public String PostmanID = ""; //邮递员编号
	public String PostmanName = ""; //邮递员名称
	public java.sql.Timestamp StoredTime; //存物时间
	public java.sql.Date StoredDate; //存物日期
	public java.sql.Timestamp ExpiredTime; //逾期时间
	public java.sql.Timestamp ReminderDateTime; //催领时间
	public java.sql.Timestamp TakedTime; //取件时间
	public String TradeWaterNo = ""; //交易流水序号
	public String PosPayFlag = ""; //支付标志
	public String UploadFlag = ""; //上传标志
	public String UploadFlagName = ""; //上传标志名称
	public String DADFlag = ""; //直投标志
	public int DropNum; //订单投递次数
	public String ReportOrderID = ""; //报告单号（DropOrderID
	public String DropOrderID = ""; //投递单号
	public String ReturnOrderID = ""; //退单号
	public String PrintedR1 = ""; //打印标识R1
	public String PrintedR2 = ""; //打印标识R2
	public String PrintedR3 = ""; //打印标识R3
	public String PrintedR4 = ""; //打印标识R4
	public String PrintedR5 = ""; //打印标识R5
	public String PrintedR6 = ""; //打印标识R6
	public String PrintedR7 = ""; //打印标识R7
	public String PrintedR8 = ""; //打印标识R8
	public String Remark = ""; //备注

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

	public String getRefNo()
	{
		return RefNo;
	}
	public void setRefNo(String RefNo)
	{
		this.RefNo = RefNo;
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

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public String getDropAgentID()
	{
		return DropAgentID;
	}
	public void setDropAgentID(String DropAgentID)
	{
		this.DropAgentID = DropAgentID;
	}

	public String getReturnAgentID()
	{
		return ReturnAgentID;
	}
	public void setReturnAgentID(String ReturnAgentID)
	{
		this.ReturnAgentID = ReturnAgentID;
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

	public java.sql.Timestamp getExpiredTime()
	{
		return ExpiredTime;
	}
	public void setExpiredTime(java.sql.Timestamp ExpiredTime)
	{
		this.ExpiredTime = ExpiredTime;
	}

	public java.sql.Timestamp getReminderDateTime()
	{
		return ReminderDateTime;
	}
	public void setReminderDateTime(java.sql.Timestamp ReminderDateTime)
	{
		this.ReminderDateTime = ReminderDateTime;
	}

	public java.sql.Timestamp getTakedTime()
	{
		return TakedTime;
	}
	public void setTakedTime(java.sql.Timestamp TakedTime)
	{
		this.TakedTime = TakedTime;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public String getPosPayFlag()
	{
		return PosPayFlag;
	}
	public void setPosPayFlag(String PosPayFlag)
	{
		this.PosPayFlag = PosPayFlag;
	}

	public String getUploadFlag()
	{
		return UploadFlag;
	}
	public void setUploadFlag(String UploadFlag)
	{
		this.UploadFlag = UploadFlag;
	}

	public String getUploadFlagName()
	{
		return UploadFlagName;
	}
	public void setUploadFlagName(String UploadFlagName)
	{
		this.UploadFlagName = UploadFlagName;
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

	public String getReportOrderID()
	{
		return ReportOrderID;
	}
	public void setReportOrderID(String ReportOrderID)
	{
		this.ReportOrderID = ReportOrderID;
	}

	public String getDropOrderID()
	{
		return DropOrderID;
	}
	public void setDropOrderID(String DropOrderID)
	{
		this.DropOrderID = DropOrderID;
	}

	public String getReturnOrderID()
	{
		return ReturnOrderID;
	}
	public void setReturnOrderID(String ReturnOrderID)
	{
		this.ReturnOrderID = ReturnOrderID;
	}

	public String getPrintedR1()
	{
		return PrintedR1;
	}
	public void setPrintedR1(String PrintedR1)
	{
		this.PrintedR1 = PrintedR1;
	}

	public String getPrintedR2()
	{
		return PrintedR2;
	}
	public void setPrintedR2(String PrintedR2)
	{
		this.PrintedR2 = PrintedR2;
	}

	public String getPrintedR3()
	{
		return PrintedR3;
	}
	public void setPrintedR3(String PrintedR3)
	{
		this.PrintedR3 = PrintedR3;
	}

	public String getPrintedR4()
	{
		return PrintedR4;
	}
	public void setPrintedR4(String PrintedR4)
	{
		this.PrintedR4 = PrintedR4;
	}

	public String getPrintedR5()
	{
		return PrintedR5;
	}
	public void setPrintedR5(String PrintedR5)
	{
		this.PrintedR5 = PrintedR5;
	}

	public String getPrintedR6()
	{
		return PrintedR6;
	}
	public void setPrintedR6(String PrintedR6)
	{
		this.PrintedR6 = PrintedR6;
	}

	public String getPrintedR7()
	{
		return PrintedR7;
	}
	public void setPrintedR7(String PrintedR7)
	{
		this.PrintedR7 = PrintedR7;
	}

	public String getPrintedR8()
	{
		return PrintedR8;
	}
	public void setPrintedR8(String PrintedR8)
	{
		this.PrintedR8 = PrintedR8;
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