package com.tnyoo.huaweipay.util;

public class SDKPayEvent {

	private String price;
	private String productName;
	private String productDesc;
	private String orderId;
	private String extReserved;
	private String sign;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getExtReserved() {
		return extReserved;
	}

	public void setExtReserved(String extReserved) {
		this.extReserved = extReserved;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
