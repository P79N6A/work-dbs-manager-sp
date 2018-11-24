package com.dcdzsoft.dto.business;

/**
* 导出交易账单
*/

public class InParamDMExportTransactions implements java.io.Serializable
{
	public String FunctionID = "341041"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String BPartnerID = ""; //商业伙伴编号
	public java.sql.Date BeginDate; //开始日期
	public java.sql.Date EndDate; //结束日期


	public int getRecordBegin()
	{
		return recordBegin;
	}
	public void setRecordBegin(int recordBegin)
	{
		this.recordBegin = recordBegin;
	}


	public int getRecordNum()
	{
		return recordNum;
	}
	public void setRecordNum(int recordNum)
	{
		this.recordNum = recordNum;
	}

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "341041";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "341041";
		else
			this.FunctionID = FunctionID;
	}

	public String getBPartnerID()
	{
		return BPartnerID;
	}
	public void setBPartnerID(String BPartnerID)
	{
		this.BPartnerID = BPartnerID;
	}

	public java.sql.Date getBeginDate()
	{
		return BeginDate;
	}
	public void setBeginDate(java.sql.Date BeginDate)
	{
		this.BeginDate = BeginDate;
	}

	public java.sql.Date getEndDate()
	{
		return EndDate;
	}
	public void setEndDate(java.sql.Date EndDate)
	{
		this.EndDate = EndDate;
	}

}