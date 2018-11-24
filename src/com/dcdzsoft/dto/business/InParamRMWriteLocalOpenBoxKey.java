package com.dcdzsoft.dto.business;

/**
* 写入本地开箱密码
*/

public class InParamRMWriteLocalOpenBoxKey implements java.io.Serializable
{
	public String FunctionID = "550022"; //功能编号

	public String AppealNo = ""; //求助编号
	public String OpenBoxKey = ""; //开箱密码

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "550022";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "550022";
		else
			this.FunctionID = FunctionID;
	}

	public String getAppealNo()
	{
		return AppealNo;
	}
	public void setAppealNo(String AppealNo)
	{
		this.AppealNo = AppealNo;
	}

	public String getOpenBoxKey()
	{
		return OpenBoxKey;
	}
	public void setOpenBoxKey(String OpenBoxKey)
	{
		this.OpenBoxKey = OpenBoxKey;
	}

}