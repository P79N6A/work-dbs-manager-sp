package com.dcdzsoft.dto.business;

/**
* 订单状态记录查询
*/

public class OutParamPTDeliveryItemDetail implements java.io.Serializable
{
	public String ItemCode = ""; //订单号
	public java.sql.Timestamp CreateTime; //创建时间
	public String ItemStatus = ""; //订单状态
	public String ItemStatusName = ""; //订单状态名称
	public String OperatorName = ""; //操作员名称
	public String Activity = ""; //活动描述

	public String getItemCode()
	{
		return ItemCode;
	}
	public void setItemCode(String ItemCode)
	{
		this.ItemCode = ItemCode;
	}

	public java.sql.Timestamp getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(java.sql.Timestamp CreateTime)
	{
		this.CreateTime = CreateTime;
	}

	public String getItemStatus()
	{
		return ItemStatus;
	}
	public void setItemStatus(String ItemStatus)
	{
		this.ItemStatus = ItemStatus;
	}

	public String getItemStatusName()
	{
		return ItemStatusName;
	}
	public void setItemStatusName(String ItemStatusName)
	{
		this.ItemStatusName = ItemStatusName;
	}

	public String getOperatorName()
	{
		return OperatorName;
	}
	public void setOperatorName(String OperatorName)
	{
		this.OperatorName = OperatorName;
	}

	public String getActivity()
	{
		return Activity;
	}
	public void setActivity(String Activity)
	{
		this.Activity = Activity;
	}

}