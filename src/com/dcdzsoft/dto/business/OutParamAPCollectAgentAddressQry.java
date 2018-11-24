package com.dcdzsoft.dto.business;

/**
* 投递员所在揽件区域寄件订单地址查询
*/

public class OutParamAPCollectAgentAddressQry implements java.io.Serializable
{
	public String REG = ""; //BP.CollectRegion
	public String BID = ""; //BP.Code
	public String BPA = ""; //BP.Address
	public String BPM = ""; //BP.Mobile
	public String BPN = ""; //BP.Name
	public double LNG; //BP.Longitude
	public double LAT; //BP.Latitude
	public int NUM; //Number

	public String getREG()
	{
		return REG;
	}
	public void setREG(String REG)
	{
		this.REG = REG;
	}

	public String getBID()
	{
		return BID;
	}
	public void setBID(String BID)
	{
		this.BID = BID;
	}

	public String getBPA()
	{
		return BPA;
	}
	public void setBPA(String BPA)
	{
		this.BPA = BPA;
	}

	public String getBPM()
	{
		return BPM;
	}
	public void setBPM(String BPM)
	{
		this.BPM = BPM;
	}

	public String getBPN()
	{
		return BPN;
	}
	public void setBPN(String BPN)
	{
		this.BPN = BPN;
	}

	public double getLNG()
	{
		return LNG;
	}
	public void setLNG(double LNG)
	{
		this.LNG = LNG;
	}

	public double getLAT()
	{
		return LAT;
	}
	public void setLAT(double LAT)
	{
		this.LAT = LAT;
	}

	public int getNUM()
	{
		return NUM;
	}
	public void setNUM(int NUM)
	{
		this.NUM = NUM;
	}

}