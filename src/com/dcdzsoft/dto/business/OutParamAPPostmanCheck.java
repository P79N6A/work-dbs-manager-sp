package com.dcdzsoft.dto.business;

/**
* 投递员身份验证
*/

public class OutParamAPPostmanCheck implements java.io.Serializable
{
	public String PostmanID = ""; //投递员编号
	public String PostmanName = ""; //投递员名称
	public String CompanyName = ""; //投递公司名称
	public String ZoneName = ""; //所属分拣中心
	public String Mobile = ""; //手机号码
	public java.sql.Timestamp CreateTime; //注册时间
	public int PostmanRight; //投递员权限1~32位（低）1：投递权限（0

	public String getPostmanID()
	{
		return PostmanID;
	}
	public void setPostmanID(String PostmanID)
	{
		this.PostmanID = PostmanID;
	}

	public String getPostmanName()
	{
		return PostmanName;
	}
	public void setPostmanName(String PostmanName)
	{
		this.PostmanName = PostmanName;
	}

	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String CompanyName)
	{
		this.CompanyName = CompanyName;
	}

	public String getZoneName()
	{
		return ZoneName;
	}
	public void setZoneName(String ZoneName)
	{
		this.ZoneName = ZoneName;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}

	public java.sql.Timestamp getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(java.sql.Timestamp CreateTime)
	{
		this.CreateTime = CreateTime;
	}

	public int getPostmanRight()
	{
		return PostmanRight;
	}
	public void setPostmanRight(int PostmanRight)
	{
		this.PostmanRight = PostmanRight;
	}

}