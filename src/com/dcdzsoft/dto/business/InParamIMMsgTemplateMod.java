package com.dcdzsoft.dto.business;

/**
* 消息模板修改
*/

public class InParamIMMsgTemplateMod implements java.io.Serializable
{
	public String FunctionID = "250122"; //功能编号

	public String OperID = ""; //管理员编号
	public String TemplateID = ""; //消息模板编号
	public String TemplateName = ""; //消息模板名称
	public String TemplateType = ""; //消息模板类型
	public String SMSTemplate = ""; //短信模板
	public String EmailTemplate = ""; //邮件模板
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250122";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250122";
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

	public String getTemplateID()
	{
		return TemplateID;
	}
	public void setTemplateID(String TemplateID)
	{
		this.TemplateID = TemplateID;
	}

	public String getTemplateName()
	{
		return TemplateName;
	}
	public void setTemplateName(String TemplateName)
	{
		this.TemplateName = TemplateName;
	}

	public String getTemplateType()
	{
		return TemplateType;
	}
	public void setTemplateType(String TemplateType)
	{
		this.TemplateType = TemplateType;
	}

	public String getSMSTemplate()
	{
		return SMSTemplate;
	}
	public void setSMSTemplate(String SMSTemplate)
	{
		this.SMSTemplate = SMSTemplate;
	}

	public String getEmailTemplate()
	{
		return EmailTemplate;
	}
	public void setEmailTemplate(String EmailTemplate)
	{
		this.EmailTemplate = EmailTemplate;
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