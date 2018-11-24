package com.dcdzsoft.dto.business;

/**
* 用户取件身份认证
*/

public class OutParamPTVerfiyUser implements java.io.Serializable
{
	public String PackageID = ""; //订单号
	public String BoxNo = ""; //箱门编号
	public String PosPayFlag = ""; //支付标志
	public double ExpiredAmt; //超时付费金额

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

	public String getPosPayFlag()
	{
		return PosPayFlag;
	}
	public void setPosPayFlag(String PosPayFlag)
	{
		this.PosPayFlag = PosPayFlag;
	}

	public double getExpiredAmt()
	{
		return ExpiredAmt;
	}
	public void setExpiredAmt(double ExpiredAmt)
	{
		this.ExpiredAmt = ExpiredAmt;
	}

}