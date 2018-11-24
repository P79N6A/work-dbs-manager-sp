package com.dcdzsoft.dto.business;

/**
* 消息模板删除
*/

public class InParamIMMsgTemplateDel implements java.io.Serializable
{
	public String FunctionID = "250123"; //功能编号

	public String OperID = ""; //管理员编号
	public String TemplateID = ""; //消息模板编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "250123";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "250123";
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

}