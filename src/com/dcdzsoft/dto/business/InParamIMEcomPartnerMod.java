package com.dcdzsoft.dto.business;

/**
* 电商伙伴信息修改
*/

public class InParamIMEcomPartnerMod implements java.io.Serializable
{
	public String FunctionID = "250062"; //功能编号

	public String OperID = ""; //管理员编号
	public String EPartnerID = ""; //电商伙伴编号
	public String EPartnerName = ""; //电商伙伴名称
	public String Address = ""; //地址
	public String Email = ""; //邮箱
	public String Mobile = ""; //手机
	public String Username = ""; //用户名
	public String Password = ""; //用户密码
	public String Active = ""; //激活状态
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250062";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250062";
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

	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String Password)
	{
		this.Password = Password;
	}

	public String getActive()
	{
		return Active;
	}
	public void setActive(String Active)
	{
		this.Active = Active;
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