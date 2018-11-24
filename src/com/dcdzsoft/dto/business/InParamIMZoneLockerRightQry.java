package com.dcdzsoft.dto.business;

/**
* 分拣区域柜体权限查询
*/

public class InParamIMZoneLockerRightQry implements java.io.Serializable
{
	public String FunctionID = "251024"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String OperID = ""; //管理员编号
	public String ZoneID = ""; //分拣区域编号
	public String QryType = ""; //查询类型(1-查可选柜)
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String Location = ""; //自提柜地址
	public String OfBureau = ""; //所属投递局
	public String OfSegment = ""; //所属投递段

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "251024";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "251024";
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

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getQryType()
	{
		return QryType;
	}
	public void setQryType(String QryType)
	{
		this.QryType = QryType;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getTerminalName()
	{
		return TerminalName;
	}
	public void setTerminalName(String TerminalName)
	{
		this.TerminalName = TerminalName;
	}

	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String Location)
	{
		this.Location = Location;
	}

	public String getOfBureau()
	{
		return OfBureau;
	}
	public void setOfBureau(String OfBureau)
	{
		this.OfBureau = OfBureau;
	}

	public String getOfSegment()
	{
		return OfSegment;
	}
	public void setOfSegment(String OfSegment)
	{
		this.OfSegment = OfSegment;
	}

}