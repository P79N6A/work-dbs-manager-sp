package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMCollectZone implements java.io.Serializable
{
	public String CollectZoneID = "";
	public String ZoneID = "";
	public String CollectZoneName = "";
	public String CollectZoneDesc = "";
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}