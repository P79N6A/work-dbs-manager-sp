package com.dcdzsoft.dto.business;

/**
* 获取设备系统日志
*/

public class InParamMBGetDeviceSysLog implements java.io.Serializable
{
	public String FunctionID = "150336"; //功能编号

	public String OperID = ""; //操作员编号
	public String TerminalNo = ""; //设备编号
	public int LogID; //日志序号
	public String UploadKey = ""; //上传Key

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "150336";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "150336";
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

	public int getLogID()
	{
		return LogID;
	}
	public void setLogID(int LogID)
	{
		this.LogID = LogID;
	}

	public String getUploadKey()
	{
		return UploadKey;
	}
	public void setUploadKey(String UploadKey)
	{
		this.UploadKey = UploadKey;
	}

}