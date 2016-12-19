package com.yaya.realtime.voice.u3d.api.mode;

public class TroopsUserStateNotify {
	
	private int yunvaId;
	private String chatRoomId;
	private int type;//type:1是send,2是receiver
	private String state;//state:11表示语音、文字都开启；00表示语音、文字都关闭 ；前面的表示语音，后面的表示文字，1表示开启，0表示关闭
	private String msg;
	public int getYunvaId() {
		return yunvaId;
	}
	public void setYunvaId(int yunvaId) {
		this.yunvaId = yunvaId;
	}
	public String getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(String chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "TroopsUserStateNotify [yunvaId=" + yunvaId + ", chatRoomId="
				+ chatRoomId + ", type=" + type + ", state=" + state + ", msg="
				+ msg + "]";
	}

}
