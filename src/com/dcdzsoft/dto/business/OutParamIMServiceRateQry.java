package com.dcdzsoft.dto.business;

/**
* 服务类型信息查询
*/

public class OutParamIMServiceRateQry implements java.io.Serializable
{
	public String ServiceID = ""; //服务类型编号
	public String ServiceName = ""; //服务名称
	public double ServiceAmt; //服务价格
	public double ServiceAmtSmall; //小箱价格
	public double ServiceAmtMed; //中箱价格
	public double ServiceAmtBig; //大箱价格
	public String ServicePrefix = ""; //服务类型简称
	public String ExtraServiceID = ""; //额外服务编号
	public double ReturnAmt; //退单服务类型
	public String Active = ""; //激活状态
	public String ActiveName = ""; //激活状态名称
	public int Counter; //计数器
	public String Remark = ""; //备注

	public String getServiceID()
	{
		return ServiceID;
	}
	public void setServiceID(String ServiceID)
	{
		this.ServiceID = ServiceID;
	}

	public String getServiceName()
	{
		return ServiceName;
	}
	public void setServiceName(String ServiceName)
	{
		this.ServiceName = ServiceName;
	}

	public double getServiceAmt()
	{
		return ServiceAmt;
	}
	public void setServiceAmt(double ServiceAmt)
	{
		this.ServiceAmt = ServiceAmt;
	}

	public double getServiceAmtSmall()
	{
		return ServiceAmtSmall;
	}
	public void setServiceAmtSmall(double ServiceAmtSmall)
	{
		this.ServiceAmtSmall = ServiceAmtSmall;
	}

	public double getServiceAmtMed()
	{
		return ServiceAmtMed;
	}
	public void setServiceAmtMed(double ServiceAmtMed)
	{
		this.ServiceAmtMed = ServiceAmtMed;
	}

	public double getServiceAmtBig()
	{
		return ServiceAmtBig;
	}
	public void setServiceAmtBig(double ServiceAmtBig)
	{
		this.ServiceAmtBig = ServiceAmtBig;
	}

	public String getServicePrefix()
	{
		return ServicePrefix;
	}
	public void setServicePrefix(String ServicePrefix)
	{
		this.ServicePrefix = ServicePrefix;
	}

	public String getExtraServiceID()
	{
		return ExtraServiceID;
	}
	public void setExtraServiceID(String ExtraServiceID)
	{
		this.ExtraServiceID = ExtraServiceID;
	}

	public double getReturnAmt()
	{
		return ReturnAmt;
	}
	public void setReturnAmt(double ReturnAmt)
	{
		this.ReturnAmt = ReturnAmt;
	}

	public String getActive()
	{
		return Active;
	}
	public void setActive(String Active)
	{
		this.Active = Active;
	}

	public String getActiveName()
	{
		return ActiveName;
	}
	public void setActiveName(String ActiveName)
	{
		this.ActiveName = ActiveName;
	}

	public int getCounter()
	{
		return Counter;
	}
	public void setCounter(int Counter)
	{
		this.Counter = Counter;
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