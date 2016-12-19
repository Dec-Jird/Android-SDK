package com.yaya.realtime.voice.u3d.api.event;

public class SDKInitEvent {

	private String appId;
	private boolean isTest;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public boolean isTest() {
		return isTest;
	}
	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}
	@Override
	public String toString() {
		return "SDKInitEvent [appId=" + appId + ", isTest=" + isTest + "]";
	}
	
}
