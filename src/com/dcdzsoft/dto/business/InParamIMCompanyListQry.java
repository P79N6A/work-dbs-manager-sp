package com.dcdzsoft.dto.business;

/**
* 服务商列表查询
*/

public class InParamIMCompanyListQry implements java.io.Serializable
{
	public String FunctionID = "250006"; //功能编号

	public String OperID = ""; //管理员编号
	public String CompanyID = ""; //服务商编号
	public String CompanyName = ""; //服务商名称
	public String CompanyType = ""; //服务商类型
	public String MasterFlag = ""; //主运营标识

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250006";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250006";
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

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String CompanyName)
	{
		this.CompanyName = CompanyName;
	}

	public String getCompanyType()
	{
		return CompanyType;
	}
	public void setCompanyType(String CompanyType)
	{
		this.CompanyType = CompanyType;
	}

	public String getMasterFlag()
	{
		return MasterFlag;
	}
	public void setMasterFlag(String MasterFlag)
	{
		this.MasterFlag = MasterFlag;
	}

}