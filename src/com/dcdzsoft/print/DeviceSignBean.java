package com.dcdzsoft.print;

/**
 * 
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: 设备注册信息</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class DeviceSignBean {
	private String TerminalNo ="";
	private String TerminalName = "";
	private String Location = "";
	private String SoftwareVersion = "";
	private String OnlineStatusName = "";
	private java.sql.Timestamp LastModifyTime;
	/**
	 * @return the terminalNo
	 */
	public String getTerminalNo() {
		return TerminalNo;
	}
	/**
	 * @param terminalNo the terminalNo to set
	 */
	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}
	public String getTerminalName() {
		return TerminalName;
	}
	public void setTerminalName(String terminalName) {
		TerminalName = terminalName;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getSoftwareVersion() {
		return SoftwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		SoftwareVersion = softwareVersion;
	}
	public String getOnlineStatusName() {
		return OnlineStatusName;
	}
	public void setOnlineStatusName(String onlineStatusName) {
		OnlineStatusName = onlineStatusName;
	}
	public java.sql.Timestamp getLastModifyTime() {
		return LastModifyTime;
	}
	public void setLastModifyTime(java.sql.Timestamp lastModifyTime) {
		LastModifyTime = lastModifyTime;
	}
}
