package com.dcdzsoft.dto.business;

/**
* 添加待投递订单
*/

public class InParamPTAddItemsFromPPC implements java.io.Serializable
{
	public String FunctionID = "331010"; //功能编号

	public String OperID = ""; //管理员编号
	public String PackageID = ""; //订单号
	public String PPCID = ""; //包裹处理中心编号
	public String ZoneID = ""; //分拣区域编号
	public String CustomerMobile = ""; //取件人手机
	public String CustomerID = ""; //取件人编号
	public String CustomerName = ""; //取件人名称
	public String CustomerAddress = ""; //取件人地址
	public String CompanyID = ""; //包裹服务商编号
	public String RefNo = ""; //参考编号
	public String TerminalNo = "";//柜号
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331010";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331010";
		else
			this.FunctionID = FunctionID;
	}
	public String getOperID() {
		return OperID;
	}
	public void setOperID(String operID) {
		OperID = operID;
	}
	public String getPackageID() {
		return PackageID;
	}
	public void setPackageID(String packageID) {
		PackageID = packageID;
	}
	public String getPPCID() {
		return PPCID;
	}
	public void setPPCID(String pPCID) {
		PPCID = pPCID;
	}
	public String getZoneID() {
		return ZoneID;
	}
	public void setZoneID(String zoneID) {
		ZoneID = zoneID;
	}
	public String getCustomerMobile() {
		return CustomerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		CustomerMobile = customerMobile;
	}
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getCustomerAddress() {
		return CustomerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		CustomerAddress = customerAddress;
	}
	public String getCompanyID() {
		return CompanyID;
	}
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
	public String getRefNo() {
		return RefNo;
	}
	public void setRefNo(String refNo) {
		RefNo = refNo;
	}
	public String getTerminalNo() {
		return TerminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}

}