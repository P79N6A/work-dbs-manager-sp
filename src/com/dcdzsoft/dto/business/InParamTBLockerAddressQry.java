package com.dcdzsoft.dto.business;

/**
* 柜体信息查询
*/

public class InParamTBLockerAddressQry implements java.io.Serializable
{
	public String FunctionID = "210021"; //功能编号

	public String LockerID = ""; //柜体编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "210021";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "210021";
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