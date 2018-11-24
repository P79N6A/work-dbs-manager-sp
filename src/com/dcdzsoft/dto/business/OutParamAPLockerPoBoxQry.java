package com.dcdzsoft.dto.business;

/**
* 柜体POBox信息查询
*/

public class OutParamAPLockerPoBoxQry implements java.io.Serializable
{
	public String LockerID = ""; //柜号
	public String LockerName = ""; //柜体名称
	public String Location = ""; //柜体位置
	public int AppRegisterFlag; //应用注册标记（0-不提供注册；1-可用于注册）
	public int AppRegisterLimit; //激活期限（天数）-
	public String Message = ""; //AppRegisterFlag=0：不支持注册消息；AppRegisterFlag=1：用户注册操作指南

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

	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String Location)
	{
		this.Location = Location;
	}

	public int getAppRegisterFlag()
	{
		return AppRegisterFlag;
	}
	public void setAppRegisterFlag(int AppRegisterFlag)
	{
		this.AppRegisterFlag = AppRegisterFlag;
	}

	public int getAppRegisterLimit()
	{
		return AppRegisterLimit;
	}
	public void setAppRegisterLimit(int AppRegisterLimit)
	{
		this.AppRegisterLimit = AppRegisterLimit;
	}

	public String getMessage()
	{
		return Message;
	}
	public void setMessage(String Message)
	{
		this.Message = Message;
	}

}