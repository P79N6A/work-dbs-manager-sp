package com.dcdzsoft.dto.business;

/**
* 投递员登陆验证
*/

public class OutParamPTPostmanLogin implements java.io.Serializable
{
	public String PostmanID = ""; //投递员编号
	public String PostmanName = ""; //投递员名称
	public String PostmanType = ""; //投递员类型
	public String CompanyID = ""; //包裹服务商编号
	public String OfBureau = ""; //(100)
	public String InputMobileFlag = ""; //(2)
	public String DynamicCode = ""; //(32)
	public String VerifyFlag = ""; //(2)
	public String BoxList = ""; //(4000)
	public double Balance; //账户余额
	public int ExpiredCount; //逾期包裹数
	public java.sql.Date ExpiredDate; //逾期日期
	public int PostmanRight; //投递员权限1~32位（低）
	public String Remark = ""; //(255)

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

	public String getPostmanType()
	{
		return PostmanType;
	}
	public void setPostmanType(String PostmanType)
	{
		this.PostmanType = PostmanType;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getOfBureau()
	{
		return OfBureau;
	}
	public void setOfBureau(String OfBureau)
	{
		this.OfBureau = OfBureau;
	}

	public String getInputMobileFlag()
	{
		return InputMobileFlag;
	}
	public void setInputMobileFlag(String InputMobileFlag)
	{
		this.InputMobileFlag = InputMobileFlag;
	}

	public String getDynamicCode()
	{
		return DynamicCode;
	}
	public void setDynamicCode(String DynamicCode)
	{
		this.DynamicCode = DynamicCode;
	}

	public String getVerifyFlag()
	{
		return VerifyFlag;
	}
	public void setVerifyFlag(String VerifyFlag)
	{
		this.VerifyFlag = VerifyFlag;
	}

	public String getBoxList()
	{
		return BoxList;
	}
	public void setBoxList(String BoxList)
	{
		this.BoxList = BoxList;
	}

	public double getBalance()
	{
		return Balance;
	}
	public void setBalance(double Balance)
	{
		this.Balance = Balance;
	}

	public int getExpiredCount()
	{
		return ExpiredCount;
	}
	public void setExpiredCount(int ExpiredCount)
	{
		this.ExpiredCount = ExpiredCount;
	}

	public java.sql.Date getExpiredDate()
	{
		return ExpiredDate;
	}
	public void setExpiredDate(java.sql.Date ExpiredDate)
	{
		this.ExpiredDate = ExpiredDate;
	}

	public int getPostmanRight()
	{
		return PostmanRight;
	}
	public void setPostmanRight(int PostmanRight)
	{
		this.PostmanRight = PostmanRight;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}