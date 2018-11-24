package com.dcdzsoft.dto.business;

/**
* 创建寄件订单
*/

public class OutParamDMDeliveryCreate implements java.io.Serializable
{
	public int Result; //返回值:1-账户余额不足;2-无退件服务;0-成功
	public double Balance; //商业伙伴余额
	public String PackageID = ""; //包裹单号（成功时返回单号，否则为空）

	/**
	 * 1-账户余额不足;2-无退件服务;0-成功
	 * @return
	 */
	public int getResult()
	{
		return Result;
	}
	public void setResult(int Result)
	{
		this.Result = Result;
	}

	public double getBalance()
	{
		return Balance;
	}
	public void setBalance(double Balance)
	{
		this.Balance = Balance;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

}