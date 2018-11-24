package com.dcdzsoft.dto.business;

/**
* 商业合作伙伴账户充值
*/

public class InParamDMBusinessPartnerTopUP implements java.io.Serializable
{
	public String FunctionID = "340101"; //功能编号
	
	public String OperID = ""; //操作员编号
	public String BPartnerID = ""; //商业伙伴编号
	public String TradeWaterNo = ""; //交易流水号
	public double Amount; //充值金额

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "340101";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "340101";
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

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public double getAmount()
	{
		return Amount;
	}
	public void setAmount(double Amount)
	{
		this.Amount = Amount;
	}
	/**
	 * @return the operID
	 */
	public String getOperID() {
		return OperID;
	}
	/**
	 * @param operID the operID to set
	 */
	public void setOperID(String operID) {
		OperID = operID;
	}

}