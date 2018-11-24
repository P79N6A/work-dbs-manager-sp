package com.dcdzsoft.dto.business;

/**
* R6报告打印（确认接收投递包裹）
*/

public class InParamPTPrintConfirmation implements java.io.Serializable
{
	public String FunctionID = "331086"; //功能编号

	public String OperID = ""; //管理员编号
	public String ReportID = ""; //Report编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331086";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331086";
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

	public String getReportID()
	{
		return ReportID;
	}
	public void setReportID(String ReportID)
	{
		this.ReportID = ReportID;
	}

}