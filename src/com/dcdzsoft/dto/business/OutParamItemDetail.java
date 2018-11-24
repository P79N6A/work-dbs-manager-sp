package com.dcdzsoft.dto.business;

/**
 * 
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: 订单详情</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class OutParamItemDetail {
	private String ItemCode = ""; //订单号
	private String CreateTime = ""; //订单创建时间（yyyy-MM-dd HH:mm:ss
	private String FromBuff = ""; //订单来源信息
	private String ToBuff = ""; //订单目的信息
	private String ItemStatus = "";//当前订单状态
	private String ItemDetail = ""; //订单状态详细信息
	private String StoredImgUrl = ""; //投递监控照片路径
	private String TakedImgUrl = ""; //取件监控照片路径
	private String StoredTime = ""; //投递时间（yyyy-MM-dd HH:mm:ss
	private String TakedTime = ""; //取件时间（yyyy-MM-dd HH:mm:ss 
	public String getItemCode() {
		return ItemCode;
	}
	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getFromBuff() {
		return FromBuff;
	}
	public void setFromBuff(String fromBuff) {
		FromBuff = fromBuff;
	}
	public String getToBuff() {
		return ToBuff;
	}
	public void setToBuff(String toBuff) {
		ToBuff = toBuff;
	}
	public String getItemDetail() {
		return ItemDetail;
	}
	public void setItemDetail(String itemDetail) {
		ItemDetail = itemDetail;
	}
	public String getStoredImgUrl() {
		return StoredImgUrl;
	}
	public void setStoredImgUrl(String storedImgUrl) {
		StoredImgUrl = storedImgUrl;
	}
	public String getTakedImgUrl() {
		return TakedImgUrl;
	}
	public void setTakedImgUrl(String takedImgUrl) {
		TakedImgUrl = takedImgUrl;
	}
	public String getStoredTime() {
		return StoredTime;
	}
	public void setStoredTime(String storedTime) {
		StoredTime = storedTime;
	}
	public String getTakedTime() {
		return TakedTime;
	}
	public void setTakedTime(String takedTime) {
		TakedTime = takedTime;
	}
	public String getItemStatus() {
		return ItemStatus;
	}
	public void setItemStatus(String itemStatus) {
		ItemStatus = itemStatus;
	}
}
