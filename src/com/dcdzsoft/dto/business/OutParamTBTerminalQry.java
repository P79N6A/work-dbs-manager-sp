package com.dcdzsoft.dto.business;

/**
* 柜体信息查询
*/

public class OutParamTBTerminalQry implements java.io.Serializable
{
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String TerminalStatus = ""; //柜体状态
	public String TerminalStatusName = ""; //柜体状态名称
	public String Address = ""; //柜体安装地址
	public String Location = ""; //柜体地理位置
	public double Longitude; //柜体经度
	public double Latitude; //柜体纬度
	public String ZoneID = ""; //所属分拣区域
	public String ZoneName = ""; //所属分拣区域
	public String City = ""; //所在城市
	public String Zipcode = ""; //所在城市邮编
	public String RegisterFlag = ""; //注册标志
	public String RegisterFlagName = ""; //注册标志名称
	public int AppRegisterLimit; //有效天数
	public String AppRegisterFlag = ""; //POBox有效标志
	public String AppRegisterFlagName = ""; //POBox有效标志名称
	public String MaintMobile = ""; //维修人员手机
	public String MaintEmail = ""; //维修人员电子邮箱
	public String OfBureau = ""; //所属投递局
	public String OfSegment = ""; //所属投递段
	public String MBDeviceNo = ""; //运营商设备编号
	public int BoxNum; //箱总数量
	public int DeskNum; //扩展柜数量
	public String MacAddr = ""; //柜体Mac地址
	public String Brand = ""; //柜体品牌
	public String Model = ""; //柜体型号
	public String MachineSize = ""; //柜体占地尺寸
	public int MainDeskAddress; //柜体主柜位置
	public String Remark = ""; //备注

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

	public String getTerminalStatus()
	{
		return TerminalStatus;
	}
	public void setTerminalStatus(String TerminalStatus)
	{
		this.TerminalStatus = TerminalStatus;
	}

	public String getTerminalStatusName()
	{
		return TerminalStatusName;
	}
	public void setTerminalStatusName(String TerminalStatusName)
	{
		this.TerminalStatusName = TerminalStatusName;
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

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getZoneName()
	{
		return ZoneName;
	}
	public void setZoneName(String ZoneName)
	{
		this.ZoneName = ZoneName;
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

	public String getRegisterFlag()
	{
		return RegisterFlag;
	}
	public void setRegisterFlag(String RegisterFlag)
	{
		this.RegisterFlag = RegisterFlag;
	}

	public String getRegisterFlagName()
	{
		return RegisterFlagName;
	}
	public void setRegisterFlagName(String RegisterFlagName)
	{
		this.RegisterFlagName = RegisterFlagName;
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

	public String getAppRegisterFlagName()
	{
		return AppRegisterFlagName;
	}
	public void setAppRegisterFlagName(String AppRegisterFlagName)
	{
		this.AppRegisterFlagName = AppRegisterFlagName;
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

	public String getMBDeviceNo()
	{
		return MBDeviceNo;
	}
	public void setMBDeviceNo(String MBDeviceNo)
	{
		this.MBDeviceNo = MBDeviceNo;
	}

	public int getBoxNum()
	{
		return BoxNum;
	}
	public void setBoxNum(int BoxNum)
	{
		this.BoxNum = BoxNum;
	}

	public int getDeskNum()
	{
		return DeskNum;
	}
	public void setDeskNum(int DeskNum)
	{
		this.DeskNum = DeskNum;
	}

	public String getMacAddr()
	{
		return MacAddr;
	}
	public void setMacAddr(String MacAddr)
	{
		this.MacAddr = MacAddr;
	}

	public String getBrand()
	{
		return Brand;
	}
	public void setBrand(String Brand)
	{
		this.Brand = Brand;
	}

	public String getModel()
	{
		return Model;
	}
	public void setModel(String Model)
	{
		this.Model = Model;
	}

	public String getMachineSize()
	{
		return MachineSize;
	}
	public void setMachineSize(String MachineSize)
	{
		this.MachineSize = MachineSize;
	}

	public int getMainDeskAddress()
	{
		return MainDeskAddress;
	}
	public void setMainDeskAddress(int MainDeskAddress)
	{
		this.MainDeskAddress = MainDeskAddress;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}