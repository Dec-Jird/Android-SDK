package com.tnyoo.gameone;

public class InitEvent {
	private  String PayReturnUrl = "";
	private String PayItemDesc ="";
	private String PayCallBackInfo = "";
	private  String PayItemName = "";
	private  int PayPriceInt;
	private String type = "";
	private String appID = "";
	private String submitTime = "";
	private String orderId = "";
	private int money = 0;
	private String info = "";

	private String balance = "";
	private String vip = "";
	private String level = "";
	private String party = "";
	private String roleName = "";
	private String roleId = "";
	private String server = "";
	private String PayOrderId="";
	

	private String PayItemId = "";
	private String PayPubKey = "";
	public void setPayItemId(String str) {
		this.PayItemId = str;
	}

	public String getPayItemId() {
		return this.PayItemId;
	}
	public void setPayPubKey(String str) {
		this.PayPubKey = str;
	}

	public String getPayPubKey() {
		return this.PayPubKey;
	}
	
	
	public void setType(String str) {
		this.type = str;
	}

	public String getType() {
		return this.type;
	}
	
	public void setAppID(String str) {
		this.appID = str;
	}

	public String getAppID() {
		return this.appID;
	}

	public String getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(String str) {
		this.submitTime = str;
	}
//	String PayOrderId,int PayPriceInt,String PayItemName,String PayItemDesc,String PayReturnUrl,String PayCallBackInfo
	public String getPayCallBackInfo() {
		return this.PayCallBackInfo;
	}

	public void setPayCallBackInfo(String str) {
		this.PayCallBackInfo = str;
	}
	public String getPayReturnUrl() {
		return this.PayReturnUrl;
	}

	public void setPayReturnUrl(String str) {
		this.PayReturnUrl = str;
	}
	public String getPayItemDesc() {
		return this.PayItemDesc;
	}

	public void setPayItemDesc(String str) {
		this.PayItemDesc = str;
	}
	public String getPayItemName() {
		return this.PayItemName;
	}

	public void setPayItemName(String str) {
		this.PayItemName = str;
	}
	public int getPayPriceInt() {
		return this.PayPriceInt;
	}

	public void setPayPriceInt(int str) {
		this.PayPriceInt = str;
	}
	public String getPayOrderId() {
		return this.PayOrderId;
	}

	public void setPayOrderId(String str) {
		this.PayOrderId = str;
	}

	public void setOrderId(String str) {
		this.orderId = str;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setMoney(int str) {
		this.money = str;
	}

	public int getMoney() {
		return this.money;
	}

	public void setInfo(String str) {
		this.info = str;
	}

	public String getInfo() {
		return this.info;
	}

	public void setBalance(String str) {
		this.balance = str;
	}

	public String getBalance() {
		return this.balance;
	}

	public void setVip(String str) {
		this.vip = str;
	}

	public String getVip() {
		return this.vip;
	}

	public void setLevel(String str) {
		this.level = str;
	}

	public String getLevel() {
		return this.level;
	}

	public void setParty(String str) {
		this.party = str;
	}

	public String getParty() {
		return this.party;
	}

	public void setRoleName(String str) {
		this.roleName = str;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleId(String str) {
		this.roleId = str;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setServer(String str) {
		this.server = str;
	}

	public String getServer() {
		return this.server;
	}
}