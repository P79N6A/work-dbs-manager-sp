package com.dcdzsoft.dto.business;

/**
* 消息模板查询
*/

public class OutParamIMMsgTemplateQry implements java.io.Serializable
{
	public String TemplateID = ""; //消息模板编号
	public String TemplateName = ""; //消息模板名称
	public String TemplateType = ""; //消息模板类型
	public String TemplateTypeName = ""; //消息模板类型
	public String SMSTemplate = ""; //短信模板
	public String EmailTemplate = ""; //邮件模板
	public String Remark = ""; //备注

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

	public String getTemplateTypeName()
	{
		return TemplateTypeName;
	}
	public void setTemplateTypeName(String TemplateTypeName)
	{
		this.TemplateTypeName = TemplateTypeName;
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