package com.dcdzsoft.dto.business;

/**
* 个人客户信息查询
*/

public class InParamIMCustomerQry implements java.io.Serializable
{
	public String FunctionID = "250074"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String OperID = ""; //管理员编号
	public String CustomerID = ""; //个人客户编号
	public String CustomerName = ""; //个人客户名称
	public String TerminalNo = ""; //设备号
	public String Mobile = ""; //手机
	public String Status = ""; //用户状态


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
			return "250074";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250074";
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

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}

	public String getStatus()
	{
		return Status;
	}
	public void setStatus(String Status)
	{
		this.Status = Status;
	}

}