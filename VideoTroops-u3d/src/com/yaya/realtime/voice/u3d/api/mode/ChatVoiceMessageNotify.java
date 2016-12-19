package com.yaya.realtime.voice.u3d.api.mode;

public class ChatVoiceMessageNotify {
	
	private int yunvaId;
	private String chatRoomId;
	private String voiceUrl;
	private long voiceTime;
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
	public String getVoiceUrl() {
		return voiceUrl;
	}
	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	public long getVoiceTime() {
		return voiceTime;
	}
	public void setVoiceTime(long voiceTime) {
		this.voiceTime = voiceTime;
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
		return "ChatVoiceMessageNotify [yunvaId=" + yunvaId + ", chatRoomId="
				+ chatRoomId + ", voiceUrl=" + voiceUrl + ", voiceTime="
				+ voiceTime + ", time=" + time + ", expand=" + expand + "]";
	}

}
