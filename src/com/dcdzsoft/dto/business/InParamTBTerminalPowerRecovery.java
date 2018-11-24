package com.dcdzsoft.dto.business;

/**
* 柜体电源恢复市电供电
*/

public class InParamTBTerminalPowerRecovery implements java.io.Serializable
{
	public String FunctionID = "210025"; //功能编号

	public String TerminalNo = ""; //柜体编号
	public int DurationMins; //掉电状态持续分钟，超过该时间，可由系统更新掉电状态

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "210025";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "210025";
		else
			this.FunctionID = FunctionID;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public int getDurationMins()
	{
		return DurationMins;
	}
	public void setDurationMins(int DurationMins)
	{
		this.DurationMins = DurationMins;
	}

}