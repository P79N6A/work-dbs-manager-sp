package com.dcdzsoft.dto.business;

/**
* 订单详情查询
*/

public class OutParamDMDeliveryItemDetailQry implements java.io.Serializable
{
	public String ItemCode = ""; //订单号
	public String CreateTime = ""; //订单创建时间（yyyy-MM-dd
	public String FromBuff = ""; //订单来源信息
	public String ToBuff = ""; //订单目的信息
	public String ItemDetail = ""; //订单状态详细信息
	public String StoredImgUrl = ""; //投递监控照片路径
	public String TakedImgUrl = ""; //取件监控照片路径
	public String StoredTime = ""; //投递时间（yyyy-MM-dd
	public String TakedTime = ""; //取件时间（yyyy-MM-dd

	public String getItemCode()
	{
		return ItemCode;
	}
	public void setItemCode(String ItemCode)
	{
		this.ItemCode = ItemCode;
	}

	public String getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(String CreateTime)
	{
		this.CreateTime = CreateTime;
	}

	public String getFromBuff()
	{
		return FromBuff;
	}
	public void setFromBuff(String FromBuff)
	{
		this.FromBuff = FromBuff;
	}

	public String getToBuff()
	{
		return ToBuff;
	}
	public void setToBuff(String ToBuff)
	{
		this.ToBuff = ToBuff;
	}

	public String getItemDetail()
	{
		return ItemDetail;
	}
	public void setItemDetail(String ItemDetail)
	{
		this.ItemDetail = ItemDetail;
	}

	public String getStoredImgUrl()
	{
		return StoredImgUrl;
	}
	public void setStoredImgUrl(String StoredImgUrl)
	{
		this.StoredImgUrl = StoredImgUrl;
	}

	public String getTakedImgUrl()
	{
		return TakedImgUrl;
	}
	public void setTakedImgUrl(String TakedImgUrl)
	{
		this.TakedImgUrl = TakedImgUrl;
	}

	public String getStoredTime()
	{
		return StoredTime;
	}
	public void setStoredTime(String StoredTime)
	{
		this.StoredTime = StoredTime;
	}

	public String getTakedTime()
	{
		return TakedTime;
	}
	public void setTakedTime(String TakedTime)
	{
		this.TakedTime = TakedTime;
	}

}