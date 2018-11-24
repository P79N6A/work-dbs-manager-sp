package com.dcdzsoft.dto.business;

/**
* 格口使用统计
*/

public class InParamQYBoxUsedStat implements java.io.Serializable
{
	public String FunctionID = "350004"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String ZoneID = ""; //分拣区域编号
	public String CompanyID = ""; //服务商编号


	public int getRecordBegin()
	{
		return recordBegin;
	}
	public void setRecordBegin(int recordBegin)
	{
		this.recordBegin = recordBegin;
	}


	public int getRecordNum()
	{
		return recordNum;
	}
	public void setRecordNum(int recordNum)
	{
		this.recordNum = recordNum;
	}

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "350004";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "350004";
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

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

}