package com.dcdzsoft.dto.business;

/**
* 商业伙伴账户充值请求
*/

public class InParamDMBusinessPartnerTopUpReq implements java.io.Serializable
{
	public String FunctionID = "340100"; //功能编号

	public String BPartnerID = ""; //商业伙伴编号
	public String TradeWaterNo = "";//交易流水号
	public double Amount; //充值金额
	public String Remark = "";

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "340100";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "340100";
		else
			this.FunctionID = FunctionID;
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
	public String getTradeWaterNo() {
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String tradeWaterNo) {
		TradeWaterNo = tradeWaterNo;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}

}