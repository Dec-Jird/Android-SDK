package com.yaya.realtime.voice.u3d.api.mode;

public class SendVoiceMessageResp {
	
	private int result;
	private String msg;
	private String voiceUrl;
	private long voiceDuration;
	private String filePath;
	private String expand;
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
	public long getVoiceDuration() {
		return voiceDuration;
	}
	public void setVoiceDuration(long voiceDuration) {
		this.voiceDuration = voiceDuration;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	@Override
	public String toString() {
		return "SendVoiceMessageResp [result=" + result + ", msg=" + msg
				+ ", voiceUrl=" + voiceUrl + ", voiceDuration=" + voiceDuration
				+ ", filePath=" + filePath + ", expand=" + expand + "]";
	}

}
