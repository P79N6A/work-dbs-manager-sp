package com.dcdzsoft.dto.function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IMGateway implements java.io.Serializable
{
	public String GatewayID = "";
	public String GatewayName = "";
	public String SMSUsername = "";
	public String SMSPassword = "";
	public String SMSUsercode = "";
	public String SMSField1 = "";
	public String SMSField2 = "";
	public String SMSField3 = "";
	public String SMSTemplate = "";
	public String GatewayURL = "";
	public String EmailParam1 = "";
	public String EmailParam2 = "";
	public String EmailParam3 = "";
	public String EmailParam4 = "";
	public String EmailTemplate = "";
	public String TemplateType = "";
	public java.sql.Timestamp LastModifyTime;
	public String Remark = "";
	public String toString () {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}