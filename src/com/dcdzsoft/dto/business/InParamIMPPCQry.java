package com.dcdzsoft.dto.business;

/**
* 包裹处理中心查询
*/

public class InParamIMPPCQry implements java.io.Serializable
{
	public String FunctionID = "250044"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String OperID = ""; //管理员编号
	public String PPCID = ""; //包裹处理中心编号
	public String PPCName = ""; //包裹处理中心名称
	public String CompanyID = ""; //包裹服务商编号


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
			return "250044";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250044";
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

	public String getPPCID()
	{
		return PPCID;
	}
	public void setPPCID(String PPCID)
	{
		this.PPCID = PPCID;
	}

	public String getPPCName()
	{
		return PPCName;
	}
	public void setPPCName(String PPCName)
	{
		this.PPCName = PPCName;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

}