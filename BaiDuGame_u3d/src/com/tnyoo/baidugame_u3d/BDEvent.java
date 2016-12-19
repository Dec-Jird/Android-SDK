package com.tnyoo.baidugame_u3d;

public class BDEvent {
	
	private String type;
	private int appId;
	private String appKey;
	private String cpOrderId;
	private String goodsName;
	private String totalAmount;
//	private int ratio;//这里不使用非定额支付，这个参数直接设为0
	private String callbackInfo;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getCpOrderId() {
		return cpOrderId;
	}
	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCallbackInfo() {
		return callbackInfo;
	}
	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}
	
}
