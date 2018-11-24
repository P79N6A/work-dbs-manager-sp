package com.dcdzsoft.dto.business;

/**
* 获取下一报告单号
*/

public class InParamPTNextReportOrderID implements java.io.Serializable
{
	public String FunctionID = "331060"; //功能编号

	public String OperID = ""; //管理员编号
	public String ReportType = ""; //打印的报告类型
	public String TerminalNo = ""; //设备号
	public String ZoneID = ""; //分拣区域编号
	public String PPCID = ""; //包裹处理中心编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331060";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331060";
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

	public String getReportType()
	{
		return ReportType;
	}
	public void setReportType(String ReportType)
	{
		this.ReportType = ReportType;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
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