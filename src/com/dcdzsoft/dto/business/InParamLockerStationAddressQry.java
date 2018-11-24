package com.dcdzsoft.dto.business;

/**
* 柜体地址信息查询（范围查询）
*/

public class InParamLockerStationAddressQry implements java.io.Serializable
{
	public String FunctionID = "210020"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public double East; //东经度
	public double South; //南纬度
	public double West; //西经度
	public double North; //北纬度

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "210020";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "210020";
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

	public double getEast()
	{
		return East;
	}
	public void setEast(double East)
	{
		this.East = East;
	}

	public double getSouth()
	{
		return South;
	}
	public void setSouth(double South)
	{
		this.South = South;
	}

	public double getWest()
	{
		return West;
	}
	public void setWest(double West)
	{
		this.West = West;
	}

	public double getNorth()
	{
		return North;
	}
	public void setNorth(double North)
	{
		this.North = North;
	}

}