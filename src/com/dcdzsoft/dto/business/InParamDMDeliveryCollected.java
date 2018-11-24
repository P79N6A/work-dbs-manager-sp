package com.dcdzsoft.dto.business;

/**
* 寄件包裹由揽件员收件
*/

public class InParamDMDeliveryCollected implements java.io.Serializable
{
	public String FunctionID = "341031"; //功能编号

	public String PackageID = ""; //订单号
	public String PostmanID = ""; //揽件员编号
	public String Password = ""; //投递员密码（MD5）

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "341031";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "341031";
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

	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String Password)
	{
		this.Password = Password;
	}

}