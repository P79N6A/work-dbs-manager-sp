package com.dcdzsoft.dto.business;

/**
* 柜体名称修改（远程）
*/

public class InParamTBTerminalModName implements java.io.Serializable
{
	public String FunctionID = "210010"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String TerminalType = "";//柜体类型
	public String TerminalName = ""; //设备名称
	public String Address = ""; //柜体安装地址
	public String Location = ""; //柜体地理位置
	public double Longitude; //柜体经度
	public double Latitude; //柜体纬度
	public String City = ""; //所在城市
	public String Zipcode = ""; //所在城市邮编
	public String ZoneID = ""; //所属分拣区域
	public int AppRegisterLimit; //有效天数
	public String AppRegisterFlag = ""; //POBox是否有效
	public String MaintMobile = ""; //维修人员手机
	public String MaintEmail = ""; //维修人员电子邮箱
	public String RemoteFlag = ""; //远程操作标志
	public String Latlon = "";
	public String MBDeviceNo = "";
	public String OfBureau   = "";
	public String OfSegment  = "";
	
	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "210010";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "210010";
		else
			this.FunctionID = FunctionID;
	}

	public String getTerminalType() {
		return TerminalType;
	}
	public void setTerminalType(String terminalType) {
		TerminalType = terminalType;
	}
	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
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

	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String Address)
	{
		this.Address = Address;
	}

	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String Location)
	{
		this.Location = Location;
	}

	public double getLongitude()
	{
		return Longitude;
	}
	public void setLongitude(double Longitude)
	{
		this.Longitude = Longitude;
	}

	public double getLatitude()
	{
		return Latitude;
	}
	public void setLatitude(double Latitude)
	{
		this.Latitude = Latitude;
	}

	public String getCity()
	{
		return City;
	}
	public void setCity(String City)
	{
		this.City = City;
	}

	public String getZipcode()
	{
		return Zipcode;
	}
	public void setZipcode(String Zipcode)
	{
		this.Zipcode = Zipcode;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public int getAppRegisterLimit()
	{
		return AppRegisterLimit;
	}
	public void setAppRegisterLimit(int AppRegisterLimit)
	{
		this.AppRegisterLimit = AppRegisterLimit;
	}

	public String getAppRegisterFlag()
	{
		return AppRegisterFlag;
	}
	public void setAppRegisterFlag(String AppRegisterFlag)
	{
		this.AppRegisterFlag = AppRegisterFlag;
	}

	public String getMaintMobile()
	{
		return MaintMobile;
	}
	public void setMaintMobile(String MaintMobile)
	{
		this.MaintMobile = MaintMobile;
	}

	public String getMaintEmail()
	{
		return MaintEmail;
	}
	public void setMaintEmail(String MaintEmail)
	{
		this.MaintEmail = MaintEmail;
	}

	public String getRemoteFlag()
	{
		return RemoteFlag;
	}
	public void setRemoteFlag(String RemoteFlag)
	{
		this.RemoteFlag = RemoteFlag;
	}
	public String getLatlon() {
		return Latlon;
	}
	public void setLatlon(String latlon) {
		Latlon = latlon;
	}
	public String getMBDeviceNo() {
		return MBDeviceNo;
	}
	public void setMBDeviceNo(String mBDeviceNo) {
		MBDeviceNo = mBDeviceNo;
	}
	public String getOfBureau() {
		return OfBureau;
	}
	public void setOfBureau(String ofBureau) {
		OfBureau = ofBureau;
	}
	public String getOfSegment() {
		return OfSegment;
	}
	public void setOfSegment(String ofSegment) {
		OfSegment = ofSegment;
	}

}