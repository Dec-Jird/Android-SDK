package com.yaya.realtime.voice.u3d.api.mode;

public class LoadOnlineVoiceFinishedNotify {
	private int result;
	private String msg;
	private String voiceUrl;
	private int playSequenceId;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getVoiceUrl() {
		return voiceUrl;
	}
	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	public int getPlaySequenceId() {
		return playSequenceId;
	}
	public void setPlaySequenceId(int playSequenceId) {
		this.playSequenceId = playSequenceId;
	}
	@Override
	public String toString() {
		return "LoadOnlineVoiceFinishedNotify [result=" + result + ", msg=" + msg
				+ ", voiceUrl=" + voiceUrl + ", playSequenceId="
				+ playSequenceId + "]";
	}
	
}
