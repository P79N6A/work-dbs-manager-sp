package com.dcdzsoft.dto.business;

/**
* 投递量统计
*/

public class OutParamQYStatDeliverOrder implements java.io.Serializable
{
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String CompanyID = ""; //投递公司编号
	public String CompanyName = ""; //投递公司名称
	public String PostmanID = ""; //投递员编号
	public String PostmanName = ""; //投递员名称
	public String Period = ""; //统计区间
	public int DropNum; //投递量

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getTerminalName()
	{
		return TerminalName;
	}
	public void setTerminalName(String TerminalName)
	{
		this.TerminalName = TerminalName;
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

	public String getPeriod()
	{
		return Period;
	}
	public void setPeriod(String Period)
	{
		this.Period = Period;
	}

	public int getDropNum()
	{
		return DropNum;
	}
	public void setDropNum(int DropNum)
	{
		this.DropNum = DropNum;
	}

}