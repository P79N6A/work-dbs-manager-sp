package com.dcdzsoft.dto.business;

/**
* 公司可用空箱列表查询
*/

public class OutParamPTCompFreeBoxListQry implements java.io.Serializable
{
	public String CompanyID = ""; //包裹服务商编号
	public String TerminalNo = ""; //设备号
	public String BoxType = ""; //箱类型
	public int FreeBoxNum; //空箱数量
	public String FreeBoxList = ""; //(4000)

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getBoxType()
	{
		return BoxType;
	}
	public void setBoxType(String BoxType)
	{
		this.BoxType = BoxType;
	}

	public int getFreeBoxNum()
	{
		return FreeBoxNum;
	}
	public void setFreeBoxNum(int FreeBoxNum)
	{
		this.FreeBoxNum = FreeBoxNum;
	}

	public String getFreeBoxList()
	{
		return FreeBoxList;
	}
	public void setFreeBoxList(String FreeBoxList)
	{
		this.FreeBoxList = FreeBoxList;
	}

}