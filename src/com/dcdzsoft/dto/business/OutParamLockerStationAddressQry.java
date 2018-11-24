package com.dcdzsoft.dto.business;

/**
* 柜体地址信息查询（范围查询）
*/

public class OutParamLockerStationAddressQry implements java.io.Serializable
{
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String Address = ""; //柜体安装地址
	public String Location = ""; //柜体地理位置
	public double Longitude; //柜体经度
	public double Latitude; //柜体纬度
	public String City = ""; //所在城市
	public String Zipcode = ""; //所在城市邮编

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

}