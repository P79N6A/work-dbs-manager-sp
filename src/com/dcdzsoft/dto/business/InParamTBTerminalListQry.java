package com.dcdzsoft.dto.business;

/**
 * 柜体信息列表查询
 */

public class InParamTBTerminalListQry implements java.io.Serializable {
	public String FunctionID = "210016"; // 功能编号

	public String OperID = ""; // 管理员编号
	public String TerminalStatus = ""; // 柜体状态

	public String ZoneID = ""; // 分拣区域编号（分配的分拣中心）
	public String OwnerZoneID = ""; // 所属分拣区域编号（沙特的分拣中心）
	public String CompanyID = "";// 分配服务商编号
	public String TerminalType = "";

	public String getFunctionID() {
		if (this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "210016";
		else
			return FunctionID;
	}

	public void setFunctionID(String FunctionID) {
		if (FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "210016";
		else
			this.FunctionID = FunctionID;
	}

	public String getOperID() {
		return OperID;
	}

	public void setOperID(String OperID) {
		this.OperID = OperID;
	}

	public String getTerminalStatus() {
		return TerminalStatus;
	}

	public void setTerminalStatus(String TerminalStatus) {
		this.TerminalStatus = TerminalStatus;
	}

	public String getZoneID() {
		return ZoneID;
	}

	public void setZoneID(String ZoneID) {
		this.ZoneID = ZoneID;
	}

	public String getOwnerZoneID() {
		return OwnerZoneID;
	}

	public void setOwnerZoneID(String OwnerZoneID) {
		this.OwnerZoneID = OwnerZoneID;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getTerminalType() {
		return TerminalType;
	}

	public void setTerminalType(String terminalType) {
		TerminalType = terminalType;
	}

}