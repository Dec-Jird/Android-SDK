package com.yaya.realtime.voice.u3d.api.event;

public class SDKChatMicEvent {
	private String expand;
	private boolean onOff;
	
	public void setExpand(String str) {
		this.expand = str;
	}
	public void setOnOff(boolean flag) {
		this.onOff = flag;
	}
	
	public String getExpand() {
		return expand;
	}
	public boolean getOnOff() {
		return onOff;
	}
	
}
