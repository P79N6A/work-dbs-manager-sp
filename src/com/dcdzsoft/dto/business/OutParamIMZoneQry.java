package com.dcdzsoft.dto.business;

/**
* 分拣区域信息查询
*/

public class OutParamIMZoneQry implements java.io.Serializable
{
	public String ZoneID = ""; //分拣区域编号
	public String ZoneName = ""; //分拣区域名称
	public String CompanyID = ""; //服务商编号
	public String CompanyName = ""; //服务商名称
	public String Description = ""; //分拣区域描述
	public double CollectCharge; //揽件费用
	public String R1Mandatory = ""; //R1打印开关
	public String R2Mandatory = ""; //R2打印开关
	public String R3Mandatory = ""; //R3打印开关
	public String R4Mandatory = ""; //R4打印开关
	public String R5Mandatory = ""; //R5打印开关
	public String R6Mandatory = ""; //R6打印开关
	public String R7Mandatory = ""; //R7打印开关
	public String R8Mandatory = ""; //R8打印开关
	public String Remark = ""; //备注

	public String getZoneID()
	{
		return ZoneID;
	}
	public void setZoneID(String ZoneID)
	{
		this.ZoneID = ZoneID;
	}

	public String getZoneName()
	{
		return ZoneName;
	}
	public void setZoneName(String ZoneName)
	{
		this.ZoneName = ZoneName;
	}

	public String getCompanyID()
	{
		return CompanyID;
	}
	public void setCompanyID(String CompanyID)
	{
		this.CompanyID = CompanyID;
	}

	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String CompanyName)
	{
		this.CompanyName = CompanyName;
	}

	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String Description)
	{
		this.Description = Description;
	}

	public double getCollectCharge()
	{
		return CollectCharge;
	}
	public void setCollectCharge(double CollectCharge)
	{
		this.CollectCharge = CollectCharge;
	}

	public String getR1Mandatory()
	{
		return R1Mandatory;
	}
	public void setR1Mandatory(String R1Mandatory)
	{
		this.R1Mandatory = R1Mandatory;
	}

	public String getR2Mandatory()
	{
		return R2Mandatory;
	}
	public void setR2Mandatory(String R2Mandatory)
	{
		this.R2Mandatory = R2Mandatory;
	}

	public String getR3Mandatory()
	{
		return R3Mandatory;
	}
	public void setR3Mandatory(String R3Mandatory)
	{
		this.R3Mandatory = R3Mandatory;
	}

	public String getR4Mandatory()
	{
		return R4Mandatory;
	}
	public void setR4Mandatory(String R4Mandatory)
	{
		this.R4Mandatory = R4Mandatory;
	}

	public String getR5Mandatory()
	{
		return R5Mandatory;
	}
	public void setR5Mandatory(String R5Mandatory)
	{
		this.R5Mandatory = R5Mandatory;
	}

	public String getR6Mandatory()
	{
		return R6Mandatory;
	}
	public void setR6Mandatory(String R6Mandatory)
	{
		this.R6Mandatory = R6Mandatory;
	}

	public String getR7Mandatory()
	{
		return R7Mandatory;
	}
	public void setR7Mandatory(String R7Mandatory)
	{
		this.R7Mandatory = R7Mandatory;
	}

	public String getR8Mandatory()
	{
		return R8Mandatory;
	}
	public void setR8Mandatory(String R8Mandatory)
	{
		this.R8Mandatory = R8Mandatory;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}