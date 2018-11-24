package com.dcdzsoft.dto.business;

/**
* 包裹处理中心删除
*/

public class InParamIMPPCDel implements java.io.Serializable
{
	public String FunctionID = "250043"; //功能编号

	public String OperID = ""; //管理员编号
	public String PPCID = ""; //包裹处理中心编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250043";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250043";
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

}