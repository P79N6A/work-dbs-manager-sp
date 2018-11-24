package com.dcdzsoft.dto.business;

/**
* 服务商箱门权限增加
*/

public class InParamIMCompBoxRightAdd implements java.io.Serializable
{
	public String FunctionID = "251001"; //功能编号

	public String OperID = ""; //管理员编号
	public String CompanyID = ""; //服务商编号
	public String BoxList = ""; //箱门列表

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "251001";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "251001";
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

	public String getBoxList()
	{
		return BoxList;
	}
	public void setBoxList(String BoxList)
	{
		this.BoxList = BoxList;
	}

}