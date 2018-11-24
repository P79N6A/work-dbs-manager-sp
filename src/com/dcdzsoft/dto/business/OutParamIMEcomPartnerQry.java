package com.dcdzsoft.dto.business;

/**
* 电商伙伴信息查询
*/

public class OutParamIMEcomPartnerQry implements java.io.Serializable
{
	public String EPartnerID = ""; //电商伙伴编号
	public String EPartnerName = ""; //电商伙伴名称
	public String Address = ""; //地址
	public String Email = ""; //邮箱
	public String Mobile = ""; //手机
	public String Username = ""; //用户名
	public String Active = ""; //激活状态
	public String ActiveName = ""; //激活状态名称
	public String Remark = ""; //备注

	public String getEPartnerID()
	{
		return EPartnerID;
	}
	public void setEPartnerID(String EPartnerID)
	{
		this.EPartnerID = EPartnerID;
	}

	public String getEPartnerName()
	{
		return EPartnerName;
	}
	public void setEPartnerName(String EPartnerName)
	{
		this.EPartnerName = EPartnerName;
	}

	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String Address)
	{
		this.Address = Address;
	}

	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String Email)
	{
		this.Email = Email;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}

	public String getUsername()
	{
		return Username;
	}
	public void setUsername(String Username)
	{
		this.Username = Username;
	}

	public String getActive()
	{
		return Active;
	}
	public void setActive(String Active)
	{
		this.Active = Active;
	}

	public String getActiveName()
	{
		return ActiveName;
	}
	public void setActiveName(String ActiveName)
	{
		this.ActiveName = ActiveName;
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