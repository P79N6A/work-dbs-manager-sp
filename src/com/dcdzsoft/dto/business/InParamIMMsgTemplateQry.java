package com.dcdzsoft.dto.business;

/**
* 消息模板查询
*/

public class InParamIMMsgTemplateQry implements java.io.Serializable
{
	public String FunctionID = "250124"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String OperID = ""; //管理员编号
	public String TemplateID = ""; //消息模板编号
	public String TemplateName = ""; //消息模板名称
	public String TemplateType = ""; //消息模板类型

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250124";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250124";
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

}