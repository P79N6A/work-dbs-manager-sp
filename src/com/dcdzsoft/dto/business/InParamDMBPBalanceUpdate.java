package com.dcdzsoft.dto.business;

/**
* 商业伙伴账户余额更新
*/

public class InParamDMBPBalanceUpdate implements java.io.Serializable
{
	public String FunctionID = "340102"; //功能编号

	public String OperID = ""; //管理员编号
	public String AdminPassword = ""; //管理员密码
	public String BPartnerID = ""; //商业伙伴编号
	public double Amount; //充值金额
	public String TradeWaterNo = ""; //交易流水号
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "340102";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "340102";
		else
			this.FunctionID = FunctionID;
	}

	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
	}

	public String getAdminPassword()
	{
		return AdminPassword;
	}
	public void setAdminPassword(String AdminPassword)
	{
		this.AdminPassword = AdminPassword;
	}

	public String getBPartnerID()
	{
		return BPartnerID;
	}
	public void setBPartnerID(String BPartnerID)
	{
		this.BPartnerID = BPartnerID;
	}

	public double getAmount()
	{
		return Amount;
	}
	public void setAmount(double Amount)
	{
		this.Amount = Amount;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
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