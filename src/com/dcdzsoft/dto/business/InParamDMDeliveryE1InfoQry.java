package com.dcdzsoft.dto.business;

/**
* 寄件订单信息查询（E1信息）
*/

public class InParamDMDeliveryE1InfoQry implements java.io.Serializable
{
	public String FunctionID = "341024"; //功能编号

	public String PackageID = ""; //包裹单号
	public String BPartnerID = ""; //商业伙伴编号
	
	public java.sql.Timestamp CreateTime;
	public String QryFlag = "";
	
	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "341024";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "341024";
		else
			this.FunctionID = FunctionID;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getBPartnerID()
	{
		return BPartnerID;
	}
	public void setBPartnerID(String BPartnerID)
	{
		this.BPartnerID = BPartnerID;
	}
	public java.sql.Timestamp getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		CreateTime = createTime;
	}
	public String getQryFlag() {
		return QryFlag;
	}
	public void setQryFlag(String qryFlag) {
		QryFlag = qryFlag;
	}

}