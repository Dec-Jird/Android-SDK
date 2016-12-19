package com.tnyoo.android.dotaxiyou.yunxiaotang.event;

public class SDKPayEvent {

	private float price;
	private String productName;
	private String serverId;
	private String roleId;
	private String orderId;
	private String callbackInfo;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCallbackInfo() {
		return callbackInfo;
	}

	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}

}
