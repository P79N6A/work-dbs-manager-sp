package com.dcdzsoft.dto.business;

/**
* 格口使用统计
*/

public class OutParamQYBoxUsageStat implements java.io.Serializable
{
	public String DateFromTo = ""; //统计开始结束日期
	public String UserInfo = ""; //箱使用方信息
	public String TerminalNo = ""; //柜体编号
	public String TerminalName = ""; //柜体名称
	public String Location = ""; //柜体位置
	public String Coverage = ""; //统计范围
	public String OccurYear = ""; //统计年份
	public String OccurQuarter = ""; //统计季度
	public String OccurMonth = ""; //统计月份
	public String OccurWeek = ""; //统计星期
	public String OccurDate = ""; //统计日期
	public int SmallCnt; //小箱使用次数
	public int MediumCnt; //中箱使用次数
	public int LargeCnt; //大箱使用次数

	public String getDateFromTo()
	{
		return DateFromTo;
	}
	public void setDateFromTo(String DateFromTo)
	{
		this.DateFromTo = DateFromTo;
	}

	public String getUserInfo()
	{
		return UserInfo;
	}
	public void setUserInfo(String UserInfo)
	{
		this.UserInfo = UserInfo;
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

	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String Location)
	{
		this.Location = Location;
	}

	public String getCoverage()
	{
		return Coverage;
	}
	public void setCoverage(String Coverage)
	{
		this.Coverage = Coverage;
	}

	public String getOccurYear()
	{
		return OccurYear;
	}
	public void setOccurYear(String OccurYear)
	{
		this.OccurYear = OccurYear;
	}

	public String getOccurQuarter()
	{
		return OccurQuarter;
	}
	public void setOccurQuarter(String OccurQuarter)
	{
		this.OccurQuarter = OccurQuarter;
	}

	public String getOccurMonth()
	{
		return OccurMonth;
	}
	public void setOccurMonth(String OccurMonth)
	{
		this.OccurMonth = OccurMonth;
	}

	public String getOccurWeek()
	{
		return OccurWeek;
	}
	public void setOccurWeek(String OccurWeek)
	{
		this.OccurWeek = OccurWeek;
	}

	public String getOccurDate()
	{
		return OccurDate;
	}
	public void setOccurDate(String OccurDate)
	{
		this.OccurDate = OccurDate;
	}

	public int getSmallCnt()
	{
		return SmallCnt;
	}
	public void setSmallCnt(int SmallCnt)
	{
		this.SmallCnt = SmallCnt;
	}

	public int getMediumCnt()
	{
		return MediumCnt;
	}
	public void setMediumCnt(int MediumCnt)
	{
		this.MediumCnt = MediumCnt;
	}

	public int getLargeCnt()
	{
		return LargeCnt;
	}
	public void setLargeCnt(int LargeCnt)
	{
		this.LargeCnt = LargeCnt;
	}

}