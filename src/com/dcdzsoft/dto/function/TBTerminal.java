package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TBTerminal implements java.io.Serializable
{
	public String TerminalNo = "";
	public String TerminalName = "";
	public String TerminalStatus = "";
	public String TerminalType = "";
	public String Address = "";
	public String Location = "";
	public double Longitude;
	public double Latitude;
	public String ZoneID = "";
	public String City = "";
	public String Zipcode = "";
	public String RegisterFlag = "";
	public int AppRegisterLimit;
	public String AppRegisterFlag = "";
	public String MaintMobile = "";
	public String MaintEmail = "";
	public String OfBureau = "";
	public String OfSegment = "";
	public String DepartmentID = "";
	public String MBDeviceNo = "";
	public int DeskNum;
	public int BoxNum;
	public String MacAddr = "";
	public String Brand = "";
	public String Model = "";
	public String MachineSize = "";
	public int MainDeskAddress;
	public java.sql.Timestamp CreateTime;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}