package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MBMonitorPictureInfo implements java.io.Serializable
{
	public String TerminalNo = "";
	public long WaterID;
	public String PictureName = "";
	public String PicturePath = "";
	public String PictureType = "";
	public String PackageID = "";
	public java.sql.Timestamp CreateTime;
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}