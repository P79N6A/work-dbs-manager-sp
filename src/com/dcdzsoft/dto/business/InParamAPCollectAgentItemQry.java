package com.dcdzsoft.dto.business;

/**
* 揽件员查询包裹订单信息
*/

public class InParamAPCollectAgentItemQry implements java.io.Serializable
{
	public String FunctionID = "650062"; //功能编号

	public String PackageID = ""; //包裹单号
	public String PostmanID = ""; //揽件员编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650062";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650062";
		else
			this.FunctionID = FunctionID;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getPostmanID()
	{
		return PostmanID;
	}
	public void setPostmanID(String PostmanID)
	{
		this.PostmanID = PostmanID;
	}

}