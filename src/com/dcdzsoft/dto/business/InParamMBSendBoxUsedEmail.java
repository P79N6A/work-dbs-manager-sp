package com.dcdzsoft.dto.business;

/**
* 发送邮件
*/

public class InParamMBSendBoxUsedEmail implements java.io.Serializable
{
	public String FunctionID = "150190"; //功能编号

	public String OperID = ""; //管理员编号
	public String EmailType = ""; //邮件类型
	public String SendAsRights = ""; //发送权限
	public java.sql.Timestamp CreateTime; //创建时间
	public java.sql.Timestamp LastModifyTime; //最后修改时间
	public String Remark = ""; //备注
	public String OperName = ""; //管理员姓名
	public String OperPassword = ""; //管理员密码
	public int OperType; //管理员类型
	public String DepartmentID = ""; //运营网点编号
	public String ZoneID = ""; //分拣区域中心编号
	public String OfficeTel = ""; //办公电话
	public String Mobile = ""; //手机
	public String Email = ""; //电子邮件
	public int RoleID; //角色编号

	@Override
	public String toString() {
		return "InParamMBSendBoxUsedEmail [FunctionID=" + FunctionID
				+ ", OperID=" + OperID + ", EmailType=" + EmailType
				+ ", SendAsRights=" + SendAsRights + ", CreateTime="
				+ CreateTime + ", LastModifyTime=" + LastModifyTime
				+ ", Remark=" + Remark + ", OperName=" + OperName
				+ ", OperPassword=" + OperPassword + ", OperType=" + OperType
				+ ", DepartmentID=" + DepartmentID + ", ZoneID=" + ZoneID
				+ ", OfficeTel=" + OfficeTel + ", Mobile=" + Mobile
				+ ", Email=" + Email + ", RoleID=" + RoleID + "]";
	}
	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "150190";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "150190";
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

	public String getEmailType()
	{
		return EmailType;
	}
	public void setEmailType(String EmailType)
	{
		this.EmailType = EmailType;
	}

	public String getSendAsRights()
	{
		return SendAsRights;
	}
	public void setSendAsRights(String SendAsRights)
	{
		this.SendAsRights = SendAsRights;
	}

	public java.sql.Timestamp getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(java.sql.Timestamp CreateTime)
	{
		this.CreateTime = CreateTime;
	}

	public java.sql.Timestamp getLastModifyTime()
	{
		return LastModifyTime;
	}
	public void setLastModifyTime(java.sql.Timestamp LastModifyTime)
	{
		this.LastModifyTime = LastModifyTime;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

	public String getOperName()
	{
		return OperName;
	}
	public void setOperName(String OperName)
	{
		this.OperName = OperName;
	}

	public String getOperPassword()
	{
		return OperPassword;
	}
	public void setOperPassword(String OperPassword)
	{
		this.OperPassword = OperPassword;
	}

	public int getOperType()
	{
		return OperType;
	}
	public void setOperType(int OperType)
	{
		this.OperType = OperType;
	}

	public String getDepartmentID()
	{
		return DepartmentID;
	}
	public void setDepartmentID(String DepartmentID)
	{
		this.DepartmentID = DepartmentID;
	}

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
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

	public int getRoleID()
	{
		return RoleID;
	}
	public void setRoleID(int RoleID)
	{
		this.RoleID = RoleID;
	}

}