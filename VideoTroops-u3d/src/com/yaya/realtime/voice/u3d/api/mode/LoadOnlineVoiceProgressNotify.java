package com.yaya.realtime.voice.u3d.api.mode;

public class LoadOnlineVoiceProgressNotify {
	private int totalSize;
	private int progressSize;
	private String voiceUrl;
	private int playSequenceId;
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getProgressSize() {
		return progressSize;
	}
	public void setProgressSize(int progressSize) {
		this.progressSize = progressSize;
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
		return "LoadOnlineVoiceProgressNotify [totalSize=" + totalSize
				+ ", progressSize=" + progressSize + ", voiceUrl=" + voiceUrl
				+ ", playSequenceId=" + playSequenceId + "]";
	}
	
}
