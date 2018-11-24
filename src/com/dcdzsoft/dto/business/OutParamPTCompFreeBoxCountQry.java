package com.dcdzsoft.dto.business;

/**
* 公司可用空箱类型数量查询
*/

public class OutParamPTCompFreeBoxCountQry implements java.io.Serializable
{
	public String CompanyID = ""; //包裹服务商编号
	public String CompanyName = ""; //包裹服务商名称
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public int FreeNumTatal; //空箱总数
	public int FreeSmallNum; //空闲小箱数量
	public int FreeMediumNum; //空闲中箱数量
	public int FreeLargeNum; //空闲大箱数量
	public int FreeSuperNum; //空闲超大箱数量
	public int FreeFreshNum; //空闲生鲜箱数量
	public int FreeUnknownNum; //空闲未知类型箱数量

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

	public int getFreeNumTatal()
	{
		return FreeNumTatal;
	}
	public void setFreeNumTatal(int FreeNumTatal)
	{
		this.FreeNumTatal = FreeNumTatal;
	}

	public int getFreeSmallNum()
	{
		return FreeSmallNum;
	}
	public void setFreeSmallNum(int FreeSmallNum)
	{
		this.FreeSmallNum = FreeSmallNum;
	}

	public int getFreeMediumNum()
	{
		return FreeMediumNum;
	}
	public void setFreeMediumNum(int FreeMediumNum)
	{
		this.FreeMediumNum = FreeMediumNum;
	}

	public int getFreeLargeNum()
	{
		return FreeLargeNum;
	}
	public void setFreeLargeNum(int FreeLargeNum)
	{
		this.FreeLargeNum = FreeLargeNum;
	}

	public int getFreeSuperNum()
	{
		return FreeSuperNum;
	}
	public void setFreeSuperNum(int FreeSuperNum)
	{
		this.FreeSuperNum = FreeSuperNum;
	}

	public int getFreeFreshNum()
	{
		return FreeFreshNum;
	}
	public void setFreeFreshNum(int FreeFreshNum)
	{
		this.FreeFreshNum = FreeFreshNum;
	}

	public int getFreeUnknownNum()
	{
		return FreeUnknownNum;
	}
	public void setFreeUnknownNum(int FreeUnknownNum)
	{
		this.FreeUnknownNum = FreeUnknownNum;
	}

}