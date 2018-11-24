package com.dcdzsoft.dto.business;

/**
* 服务商增加
*/

public class InParamIMCompanyAdd implements java.io.Serializable
{
	public String FunctionID = "250001"; //功能编号

	public String OperID = ""; //管理员编号
	public String CompanyID = ""; //服务商编号
	public String CompanyName = ""; //服务商名称
	public String CompanyType = ""; //服务商类型
	public String MasterFlag = ""; //主运营标识
	public String Address = ""; //地址
	public String Email = ""; //邮箱
	public String Mobile = ""; //电话
	public String Feedback = ""; //反馈标识
	public String LogoUrl = ""; //LOGO地址
	public String Slogan = ""; //标语
	public String SMS_Notification = ""; //短信通告
	public int ExpiredDays; //逾期天数
	public int ReminderDays; //催领间隔天数
	public String ReminderTime = ""; //催领时间（每天）
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250001";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250001";
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

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String CompanyName)
	{
		this.CompanyName = CompanyName;
	}

	public String getCompanyType()
	{
		return CompanyType;
	}
	public void setCompanyType(String CompanyType)
	{
		this.CompanyType = CompanyType;
	}

	public String getMasterFlag()
	{
		return MasterFlag;
	}
	public void setMasterFlag(String MasterFlag)
	{
		this.MasterFlag = MasterFlag;
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

	public String getFeedback()
	{
		return Feedback;
	}
	public void setFeedback(String Feedback)
	{
		this.Feedback = Feedback;
	}

	public String getLogoUrl()
	{
		return LogoUrl;
	}
	public void setLogoUrl(String LogoUrl)
	{
		this.LogoUrl = LogoUrl;
	}

	public String getSlogan()
	{
		return Slogan;
	}
	public void setSlogan(String Slogan)
	{
		this.Slogan = Slogan;
	}

	public String getSMS_Notification()
	{
		return SMS_Notification;
	}
	public void setSMS_Notification(String SMS_Notification)
	{
		this.SMS_Notification = SMS_Notification;
	}

	public int getExpiredDays()
	{
		return ExpiredDays;
	}
	public void setExpiredDays(int ExpiredDays)
	{
		this.ExpiredDays = ExpiredDays;
	}

	public int getReminderDays()
	{
		return ReminderDays;
	}
	public void setReminderDays(int ReminderDays)
	{
		this.ReminderDays = ReminderDays;
	}

	public String getReminderTime()
	{
		return ReminderTime;
	}
	public void setReminderTime(String ReminderTime)
	{
		this.ReminderTime = ReminderTime;
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