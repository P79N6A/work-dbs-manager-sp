package com.dcdzsoft.dto.business;

/**
* 箱体信息查询数量
*/

public class InParamTBBoxStatusInfoQryCount implements java.io.Serializable
{
	public String FunctionID = "210225"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public int DeskNo; //副柜编号
	public String BoxNo = ""; //箱门编号
	public String BoxStatus = ""; //箱状态
	public String BoxUsedStatus = ""; //箱使用状态

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "210225";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "210225";
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

	public int getDeskNo()
	{
		return DeskNo;
	}
	public void setDeskNo(int DeskNo)
	{
		this.DeskNo = DeskNo;
	}

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public String getBoxStatus()
	{
		return BoxStatus;
	}
	public void setBoxStatus(String BoxStatus)
	{
		this.BoxStatus = BoxStatus;
	}

	public String getBoxUsedStatus()
	{
		return BoxUsedStatus;
	}
	public void setBoxUsedStatus(String BoxUsedStatus)
	{
		this.BoxUsedStatus = BoxUsedStatus;
	}

}