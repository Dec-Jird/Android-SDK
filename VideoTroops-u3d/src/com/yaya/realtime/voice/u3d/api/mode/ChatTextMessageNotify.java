package com.yaya.realtime.voice.u3d.api.mode;

public class ChatTextMessageNotify {
	
	private int yunvaId;
	private String chatRoomId;
	private String text;
	private long time;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	@Override
	public String toString() {
		return "ChatTextMessageNotify [yunvaId=" + yunvaId + ", chatRoomId="
				+ chatRoomId + ", text=" + text + ", time=" + time
				+ ", expand=" + expand + "]";
	}

}
