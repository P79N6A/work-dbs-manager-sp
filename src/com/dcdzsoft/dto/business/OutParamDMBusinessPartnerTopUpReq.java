package com.dcdzsoft.dto.business;

/**
* 商业伙伴账户充值请求
*/

public class OutParamDMBusinessPartnerTopUpReq implements java.io.Serializable
{
	public String OperID = ""; //操作员编号
	public String UserID = ""; //商业伙伴编号
	public String UserCode = ""; //商业伙伴名称
	public String TradeWaterNo = ""; //交易流水号
	public double Amount; //充值金额
	private double Balance;//商业伙伴当前余额

	public String getUserID()
	{
		return UserID;
	}
	public void setUserID(String UserID)
	{
		this.UserID = UserID;
	}

	public String getUserCode()
	{
		return UserCode;
	}
	public void setUserCode(String UserCode)
	{
		this.UserCode = UserCode;
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
	 * @return the balance
	 */
	public double getBalance() {
		return Balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		Balance = balance;
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