package com.dcdzsoft.dto.business;

/**
* 订单状态记录查询
*/

public class InParamPTDeliveryItemDetail implements java.io.Serializable
{
	public String FunctionID = "331006"; //功能编号

	public int KeyType; //关键字类型（1-订单号，2-Ref）
	public String Key = ""; //搜索关键字
	public int Mode; //查找模式（0-last，1-full）

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331006";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331006";
		else
			this.FunctionID = FunctionID;
	}

	public int getKeyType()
	{
		return KeyType;
	}
	public void setKeyType(int KeyType)
	{
		this.KeyType = KeyType;
	}

	public String getKey()
	{
		return Key;
	}
	public void setKey(String Key)
	{
		this.Key = Key;
	}

	public int getMode()
	{
		return Mode;
	}
	public void setMode(int Mode)
	{
		this.Mode = Mode;
	}

}