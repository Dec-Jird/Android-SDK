package com.tnyoo.android.dotaxiyou.vivo;

public class InitEvent {
	
	private String type;
	
	
	private String appid;
	private String orderNumber;
	private String accessKey;
	private long price;
	private String productName;
	private String productDes;
	private int rolelevel;
	private String serviceArea;
	private String serviceAreaName;
	
	// 支付信息
	private String blance; // 元宝数量
	private String vipLevel; // vip 等级
	private String party; // 工会
	private String roleId; // 角色Id
	private String roleName; // 角色名称
	private String extInfo; // 扩展数据
	private boolean logOnOff;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDes() {
		return productDes;
	}

	public void setProductDes(String productDes) {
		this.productDes = productDes;
	}

	public int getRolelevel() {
		return rolelevel;
	}

	public void setRolelevel(int rolelevel) {
		this.rolelevel = rolelevel;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getServiceAreaName() {
		return serviceAreaName;
	}

	public void setServiceAreaName(String serviceAreaName) {
		this.serviceAreaName = serviceAreaName;
	}

	public String getBlance() {
		return blance;
	}

	public void setBlance(String blance) {
		this.blance = blance;
	}

	public String getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public boolean isLogOnOff() {
		return logOnOff;
	}

	public void setLogOnOff(boolean logOnOff) {
		this.logOnOff = logOnOff;
	}
	
	
	

}
