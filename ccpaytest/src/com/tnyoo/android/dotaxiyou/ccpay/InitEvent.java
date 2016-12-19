package com.tnyoo.android.dotaxiyou.ccpay;

public class InitEvent {
	
	private String type;
	
	
	private String productId; // 商品id
	private String partnerTransactionNo; // 商户订单号
	private String price; // 自定义金额.

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPartnerTransactionNo() {
		return partnerTransactionNo;
	}

	public void setPartnerTransactionNo(String partnerTransactionNo) {
		this.partnerTransactionNo = partnerTransactionNo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
