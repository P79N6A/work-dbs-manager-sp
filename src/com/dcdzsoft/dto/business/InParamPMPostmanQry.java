package com.dcdzsoft.dto.business;

/**
* 邮递员查询
*/

public class InParamPMPostmanQry implements java.io.Serializable
{
	public String FunctionID = "311004"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String OperID = ""; //管理员编号
	public String PostmanID = ""; //邮递员编号
	public String CompanyID = ""; //包裹服务商编号
	public String ZoneID = ""; //分拣区域编号
	public String CollectZoneID = ""; //揽件区域编号
	public String PostmanName = ""; //邮递员名称
	public String PostmanType = ""; //邮递员类型
	public String PostmanStatus = ""; //邮递员状态
	public String BindCardID = ""; //绑定卡号
	public String BindMobile = ""; //绑定手机

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "311004";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "311004";
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

	public String getPostmanID()
	{
		return PostmanID;
	}
	public void setPostmanID(String PostmanID)
	{
		this.PostmanID = PostmanID;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getCollectZoneID()
	{
		return CollectZoneID;
	}
	public void setCollectZoneID(String CollectZoneID)
	{
		this.CollectZoneID = CollectZoneID;
	}

	public String getPostmanName()
	{
		return PostmanName;
	}
	public void setPostmanName(String PostmanName)
	{
		this.PostmanName = PostmanName;
	}

	public String getPostmanType()
	{
		return PostmanType;
	}
	public void setPostmanType(String PostmanType)
	{
		this.PostmanType = PostmanType;
	}

	public String getPostmanStatus()
	{
		return PostmanStatus;
	}
	public void setPostmanStatus(String PostmanStatus)
	{
		this.PostmanStatus = PostmanStatus;
	}

	public String getBindCardID()
	{
		return BindCardID;
	}
	public void setBindCardID(String BindCardID)
	{
		this.BindCardID = BindCardID;
	}

	public String getBindMobile()
	{
		return BindMobile;
	}
	public void setBindMobile(String BindMobile)
	{
		this.BindMobile = BindMobile;
	}

}