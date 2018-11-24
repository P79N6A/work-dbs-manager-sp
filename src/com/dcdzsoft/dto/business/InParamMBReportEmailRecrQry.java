package com.dcdzsoft.dto.business;

/**
* 接受邮件管理员查询
*/

public class InParamMBReportEmailRecrQry implements java.io.Serializable
{
	public String FunctionID = "150184"; //功能编号

	public String OperID = ""; //管理员编号
	public String UserID = ""; //管理员帐号
	public String EmailType = ""; //邮件类型
	public String SendAsRights = ""; //发送权限
	public java.sql.Timestamp CreateTime; //创建时间
	public java.sql.Timestamp LastModifyTime; //最后修改时间
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "150184";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "150184";
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

	public String getUserID()
	{
		return UserID;
	}
	public void setUserID(String UserID)
	{
		this.UserID = UserID;
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

}