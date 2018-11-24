package com.dcdzsoft.dto.business;

/**
* 柜体信息查询
*/

public class OutParamTBLockerAddressQry implements java.io.Serializable
{
	public String LockerID = ""; //柜体编号
	public String LockerName = ""; //柜体名称
	public String Address = ""; //柜体安装地址
	public String Location = ""; //柜体地理位置
	public String City = ""; //所在城市
	public String Zipcode = ""; //所在城市邮编
	public double Longitude = 0.0;//所在经度
	public double Latitude = 0.0;//所在纬度
	public String getLockerID()
	{
		return LockerID;
	}
	public void setLockerID(String LockerID)
	{
		this.LockerID = LockerID;
	}

	public String getLockerName()
	{
		return LockerName;
	}
	public void setLockerName(String LockerName)
	{
		this.LockerName = LockerName;
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
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return Longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return Latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

}