package com.dcdzsoft.dto.business;

/**
* 投递包裹
*/

public class InParamPTDeliveryPackage implements java.io.Serializable
{
	public String FunctionID = "330026"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String PostmanID = ""; //投递员编号
	public String DynamicCode = ""; //动态码
	public String CompanyID = ""; //投递公司编号
	public String PackageID = ""; //订单号
	public String BoxNo = ""; //箱门编号
	public java.sql.Timestamp StoredTime; //存物时间
	public java.sql.Timestamp ExpiredTime; //逾期时间
	public String CustomerID = ""; //取件人编号
	public String CustomerMobile = ""; //取件人手机
	public String OpenBoxKey = "";    //开箱密码
	public String LeftFlag = "";      //寄存标志（投递，寄件）
	public String PosPayFlag = ""; 	  //支付标志
	public String ArticleStatus = ""; //物品状态
	public String DADFlag = ""; //直投标识
	public String Remark = "";//RollBACK

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "330026";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "330026";
		else
			this.FunctionID = FunctionID;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public String getPostmanID()
	{
		return PostmanID;
	}
	public void setPostmanID(String PostmanID)
	{
		this.PostmanID = PostmanID;
	}

	public String getDynamicCode()
	{
		return DynamicCode;
	}
	public void setDynamicCode(String DynamicCode)
	{
		this.DynamicCode = DynamicCode;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public java.sql.Timestamp getStoredTime()
	{
		return StoredTime;
	}
	public void setStoredTime(java.sql.Timestamp StoredTime)
	{
		this.StoredTime = StoredTime;
	}

	public java.sql.Timestamp getExpiredTime()
	{
		return ExpiredTime;
	}
	public void setExpiredTime(java.sql.Timestamp ExpiredTime)
	{
		this.ExpiredTime = ExpiredTime;
	}

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getOpenBoxKey()
	{
		return OpenBoxKey;
	}
	public void setOpenBoxKey(String OpenBoxKey)
	{
		this.OpenBoxKey = OpenBoxKey;
	}

	public String getLeftFlag()
	{
		return LeftFlag;
	}
	public void setLeftFlag(String LeftFlag)
	{
		this.LeftFlag = LeftFlag;
	}

	public String getPosPayFlag()
	{
		return PosPayFlag;
	}
	public void setPosPayFlag(String PosPayFlag)
	{
		this.PosPayFlag = PosPayFlag;
	}

	public String getArticleStatus()
	{
		return ArticleStatus;
	}
	public void setArticleStatus(String ArticleStatus)
	{
		this.ArticleStatus = ArticleStatus;
	}

	public String getDADFlag()
	{
		return DADFlag;
	}
	public void setDADFlag(String DADFlag)
	{
		this.DADFlag = DADFlag;
	}
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String remark) {
        Remark = remark;
    }

}