package com.dcdzsoft.dto.business;

/**
* 邮递员修改
*/

public class InParamPMPostmanMod implements java.io.Serializable
{
	public String FunctionID = "311002"; //功能编号

	public String OperID = ""; //管理员编号
	public String PostmanID = ""; //邮递员编号
	public String PostmanName = ""; //邮递员名称
	public String PostmanType = ""; //邮递员类型
	public String BindCardID = ""; //绑定卡号
	public String BindMobile = ""; //绑定手机
	public String OfficeTel = ""; //办公电话
	public String Mobile = ""; //手机
	public String Email = ""; //电子邮件
	public String ContactAddress = ""; //联系地址
	public String InputMobileFlag = ""; //转入标志
	public String PostmanStatus = ""; //邮递员状态
	public String DropRight = ""; //投递权限
	public String ReturnRight = ""; //回收权限
	public String CollectRight = ""; //揽件权限
	public String DADRight = ""; //直投权限
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "311002";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "311002";
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

	public String getOfficeTel()
	{
		return OfficeTel;
	}
	public void setOfficeTel(String OfficeTel)
	{
		this.OfficeTel = OfficeTel;
	}

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String Mobile)
	{
		this.Mobile = Mobile;
	}

	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String Email)
	{
		this.Email = Email;
	}

	public String getContactAddress()
	{
		return ContactAddress;
	}
	public void setContactAddress(String ContactAddress)
	{
		this.ContactAddress = ContactAddress;
	}

	public String getInputMobileFlag()
	{
		return InputMobileFlag;
	}
	public void setInputMobileFlag(String InputMobileFlag)
	{
		this.InputMobileFlag = InputMobileFlag;
	}

	public String getPostmanStatus()
	{
		return PostmanStatus;
	}
	public void setPostmanStatus(String PostmanStatus)
	{
		this.PostmanStatus = PostmanStatus;
	}

	public String getDropRight()
	{
		return DropRight;
	}
	public void setDropRight(String DropRight)
	{
		this.DropRight = DropRight;
	}

	public String getReturnRight()
	{
		return ReturnRight;
	}
	public void setReturnRight(String ReturnRight)
	{
		this.ReturnRight = ReturnRight;
	}

	public String getCollectRight()
	{
		return CollectRight;
	}
	public void setCollectRight(String CollectRight)
	{
		this.CollectRight = CollectRight;
	}

	public String getDADRight()
	{
		return DADRight;
	}
	public void setDADRight(String DADRight)
	{
		this.DADRight = DADRight;
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