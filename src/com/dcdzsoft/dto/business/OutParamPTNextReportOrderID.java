package com.dcdzsoft.dto.business;

/**
* 获取下一报告单号
*/

public class OutParamPTNextReportOrderID implements java.io.Serializable
{
	public String ReportOrderID = ""; //报告清单编号

	public String getReportOrderID()
	{
		return ReportOrderID;
	}
	public void setReportOrderID(String ReportOrderID)
	{
		this.ReportOrderID = ReportOrderID;
	}

}