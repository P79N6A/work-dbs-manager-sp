package com.dcdzsoft.dto.business;

/**
* API用户Key及数据完整行验证
*/

public class InParamAPIUserKeyAndDataVerity implements java.io.Serializable
{
	public String FunctionID = "651001"; //功能编号

	public String Keymd5 = ""; //MD5(PrivateKey
	public String Datamd5 = ""; //MD5(RequestData)
	public String RequestData = ""; //API请求数据

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651001";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651001";
		else
			this.FunctionID = FunctionID;
	}

	public String getKeymd5()
	{
		return Keymd5;
	}
	public void setKeymd5(String Keymd5)
	{
		this.Keymd5 = Keymd5;
	}

	public String getDatamd5()
	{
		return Datamd5;
	}
	public void setDatamd5(String Datamd5)
	{
		this.Datamd5 = Datamd5;
	}

	public String getRequestData()
	{
		return RequestData;
	}
	public void setRequestData(String RequestData)
	{
		this.RequestData = RequestData;
	}

}