package com.dcdzsoft.dto.business;

/**
* 投递员所在揽件区域寄件订单地址查询
*/

public class InParamAPCollectAgentAddressQry implements java.io.Serializable
{
	public String FunctionID = "650061"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String PostmanID = ""; //投递员编号
	public String Mode = ""; //查询模式：1-可以查询所有，不受揽件区域限制


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
			return "650061";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650061";
		else
			this.FunctionID = FunctionID;
	}

	public String getPostmanID()
	{
		return PostmanID;
	}
	public void setPostmanID(String PostmanID)
	{
		this.PostmanID = PostmanID;
	}
	public String getMode() {
		return Mode;
	}
	public void setMode(String mode) {
		Mode = mode;
	}

}