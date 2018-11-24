package com.dcdzsoft.dto.business;

/**
* 短信接口信息查询
*/

public class InParamIMGatewayQry implements java.io.Serializable
{
	public String FunctionID = "250084"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String OperID = ""; //管理员编号
	public String GatewayID = ""; //短信接口编号
	public String GatewayName = ""; //短信接口名称

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250084";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250084";
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

	public String getGatewayID()
	{
		return GatewayID;
	}
	public void setGatewayID(String GatewayID)
	{
		this.GatewayID = GatewayID;
	}

	public String getGatewayName()
	{
		return GatewayName;
	}
	public void setGatewayName(String GatewayName)
	{
		this.GatewayName = GatewayName;
	}

}