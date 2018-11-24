package com.dcdzsoft.dto.business;

/**
* 邮递员列表查询
*/

public class OutParamPMPostmanListQry implements java.io.Serializable
{
	public String PostmanID = ""; //邮递员编号
	public String PostmanName = ""; //邮递员名称

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

}