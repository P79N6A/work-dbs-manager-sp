package com.dcdzsoft.dto.business;

/**
* 状态时间限制修改
*/

public class InParamMBTimeLimitMod implements java.io.Serializable
{
	public String FunctionID = "150162"; //功能编号

	public String OperID = ""; //管理员编号
	public String StatusID = ""; //状态ID
	public String StatusName = ""; //状态名称
	public int TimeLimit; //状态时间限制（分钟）

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "150162";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "150162";
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

	public String getStatusID()
	{
		return StatusID;
	}
	public void setStatusID(String StatusID)
	{
		this.StatusID = StatusID;
	}

	public String getStatusName()
	{
		return StatusName;
	}
	public void setStatusName(String StatusName)
	{
		this.StatusName = StatusName;
	}

	public int getTimeLimit()
	{
		return TimeLimit;
	}
	public void setTimeLimit(int TimeLimit)
	{
		this.TimeLimit = TimeLimit;
	}

}