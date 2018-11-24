package com.dcdzsoft.dto.business;

/**
* 待揽件包裹订单列表
*/

public class OutParamAPPostmanMailItemList implements java.io.Serializable
{
	public String PID = ""; //PackageID
	public java.sql.Timestamp CTM; //CreateTime
	public String BPA = ""; //BPartnerAddress
	public String BPM = ""; //BPartnerMobile
	public String BPN = ""; //BPartnerName
	public double LNG; //Longitude
	public double LAT; //Latitude
	public String CUN = ""; //CustomerName
	public String CUM = ""; //CustomerMobile
	public String CUA = ""; //CustomerAddress
	public String STS = ""; //ItemStatus
	public String RNF = ""; //ReturnFlag（1-Y，0-N）

	public String getPID()
	{
		return PID;
	}
	public void setPID(String PID)
	{
		this.PID = PID;
	}

	public java.sql.Timestamp getCTM()
	{
		return CTM;
	}
	public void setCTM(java.sql.Timestamp CTM)
	{
		this.CTM = CTM;
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

	public String getCUN()
	{
		return CUN;
	}
	public void setCUN(String CUN)
	{
		this.CUN = CUN;
	}

	public String getCUM()
	{
		return CUM;
	}
	public void setCUM(String CUM)
	{
		this.CUM = CUM;
	}

	public String getCUA()
	{
		return CUA;
	}
	public void setCUA(String CUA)
	{
		this.CUA = CUA;
	}

	public String getSTS()
	{
		return STS;
	}
	public void setSTS(String STS)
	{
		this.STS = STS;
	}

	public String getRNF()
	{
		return RNF;
	}
	public void setRNF(String RNF)
	{
		this.RNF = RNF;
	}

}