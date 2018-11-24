package com.dcdzsoft.dto.business;

/**
* 柜体POBox信息查询
*/

public class InParamAPLockerPoBoxQry implements java.io.Serializable
{
	public String FunctionID = "650150"; //功能编号

	public String LockerID = ""; //柜号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650150";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650150";
		else
			this.FunctionID = FunctionID;
	}

	public String getLockerID()
	{
		return LockerID;
	}
	public void setLockerID(String LockerID)
	{
		this.LockerID = LockerID;
	}

}