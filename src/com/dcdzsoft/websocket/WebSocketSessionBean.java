package com.dcdzsoft.websocket;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class WebSocketSessionBean implements java.io.Serializable{
	
	private String sessionID;
	private String terminalNo;
	private String appServerID;  //Locker注册的应用服务器ID
	private String appServerIP;  //Locker注册的应用服务器内部IP
	private String appServerPort;//Locker注册的应用服务器内部Port
	private java.util.Date lastActiveDateTime;//
	
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	public String getAppServerID() {
		return appServerID;
	}
	public void setAppServerID(String appServerID) {
		this.appServerID = appServerID;
	}
	public String getAppServerIP() {
		return appServerIP;
	}
	public void setAppServerIP(String appServerIP) {
		this.appServerIP = appServerIP;
	}
	public String getAppServerPort() {
		return appServerPort;
	}
	public void setAppServerPort(String appServerPort) {
		this.appServerPort = appServerPort;
	}
	public java.util.Date getLastActiveDateTime() {
		return lastActiveDateTime;
	}
	public void setLastActiveDateTime(java.util.Date lastActiveDateTime) {
		this.lastActiveDateTime = lastActiveDateTime;
	}
}
