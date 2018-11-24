package com.dcdzsoft.dto.business;

/**
* 推送LED消息
*/

public class InParamMBPushTerminalLedMsg implements java.io.Serializable
{
	public String FunctionID = "150452"; //功能编号

	public String OperID = ""; //操作员编号
	public String TerminalNoStr = ""; //设备列表
	public String LedMsg = ""; //LED信息

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "150452";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "150452";
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

	public String getTerminalNoStr()
	{
		return TerminalNoStr;
	}
	public void setTerminalNoStr(String TerminalNoStr)
	{
		this.TerminalNoStr = TerminalNoStr;
	}

	public String getLedMsg()
	{
		return LedMsg;
	}
	public void setLedMsg(String LedMsg)
	{
		this.LedMsg = LedMsg;
	}

}