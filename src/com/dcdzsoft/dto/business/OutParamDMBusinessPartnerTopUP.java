package com.dcdzsoft.dto.business;

/**
* 商业合作伙伴账户充值
*/

public class OutParamDMBusinessPartnerTopUP implements java.io.Serializable
{
	public String UserID = ""; //商业伙伴编号
	public String UserCode = ""; //商业伙伴名称
	public String UserName = ""; //商业伙伴用户名
	public String TradeWaterNo = ""; //交易流水号
	public double Amount; //充值金额
	public double Balance; //当前账户余额
	public int Status = 0;//0-Success,1-商业伙伴编号不存在,2-交易流水号不存在，3-充值金额与请求时不一致,4-超时
	
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

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
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

}