package com.dcdzsoft.dto.business;

/**
* 接受邮件管理员查询
*/

public class OutParamMBReportEmailRecrQry implements java.io.Serializable
{
	public String OperID = ""; //管理员帐号
	public String EmailType = ""; //邮件类型
	public String SendAsRights = ""; //发送权限
	public java.sql.Timestamp CreateTime; //创建时间
	public java.sql.Timestamp LastModifyTime; //最后修改时间
	public String Remark = ""; //备注

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

}