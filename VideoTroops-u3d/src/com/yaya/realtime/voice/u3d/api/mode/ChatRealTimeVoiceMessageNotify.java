package com.yaya.realtime.voice.u3d.api.mode;

public class ChatRealTimeVoiceMessageNotify {
	
	private int yunvaId;
	private String chatRoomId;
	private String expand;
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
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	@Override
	public String toString() {
		return "ChatRealTimeVoiceMessageNotify [yunvaId=" + yunvaId
				+ ", chatRoomId=" + chatRoomId + ", expand=" + expand + "]";
	}

}
