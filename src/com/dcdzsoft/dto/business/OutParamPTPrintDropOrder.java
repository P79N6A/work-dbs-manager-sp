package com.dcdzsoft.dto.business;

/**
* R2报告打印（投递清单报告）
*/

public class OutParamPTPrintDropOrder implements java.io.Serializable
{
	public String ReportName = ""; //报告名称
	public String CompanyID = ""; //服务商编号
	public String CompanyName = ""; //服务商名称
	public String DocumentID = ""; //报表编号
	public String DocumentName = ""; //报表名称
	public java.sql.Date DocumentDate; //报表生成日期
	public String From = ""; //来源地址编号
	public String To = ""; //目标地址编号
	public String Staff = ""; //操作员名称
	public String ItemCodeList = ""; //订单列表(ID+BoxSize)
	public int ItemTotal; //订单总数

	public String getReportName()
	{
		return ReportName;
	}
	public void setReportName(String ReportName)
	{
		this.ReportName = ReportName;
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

	public String getDocumentID()
	{
		return DocumentID;
	}
	public void setDocumentID(String DocumentID)
	{
		this.DocumentID = DocumentID;
	}

	public String getDocumentName()
	{
		return DocumentName;
	}
	public void setDocumentName(String DocumentName)
	{
		this.DocumentName = DocumentName;
	}

	public java.sql.Date getDocumentDate()
	{
		return DocumentDate;
	}
	public void setDocumentDate(java.sql.Date DocumentDate)
	{
		this.DocumentDate = DocumentDate;
	}

	public String getFrom()
	{
		return From;
	}
	public void setFrom(String From)
	{
		this.From = From;
	}

	public String getTo()
	{
		return To;
	}
	public void setTo(String To)
	{
		this.To = To;
	}

	public String getStaff()
	{
		return Staff;
	}
	public void setStaff(String Staff)
	{
		this.Staff = Staff;
	}

	public String getItemCodeList()
	{
		return ItemCodeList;
	}
	public void setItemCodeList(String ItemCodeList)
	{
		this.ItemCodeList = ItemCodeList;
	}

	public int getItemTotal()
	{
		return ItemTotal;
	}
	public void setItemTotal(int ItemTotal)
	{
		this.ItemTotal = ItemTotal;
	}

}