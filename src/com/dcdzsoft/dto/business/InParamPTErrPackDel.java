package com.dcdzsoft.dto.business;

/**
* 从PPC发送到AZC的订单，在pane1已经显示，但是ppc已经作别的处理，不用再在e-Locker系统中处理的订单，增加按钮来让管理员从pane1中移除。
*/

public class InParamPTErrPackDel implements java.io.Serializable
{
	public String FunctionID = "331075"; //功能编号

	public String OperID = ""; //管理员编号
	public String PackageID = ""; //订单号（流水号集合）

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331075";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331075";
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

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

}