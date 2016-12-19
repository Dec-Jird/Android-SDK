package com.yaya.realtime.voice.u3d.api.mode;

public class RecordCompleteNotify {
	
	private String filePath;
	private int voiceDuration;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getVoiceDuration() {
		return voiceDuration;
	}
	public void setVoiceDuration(int voiceDuration) {
		this.voiceDuration = voiceDuration;
	}
	@Override
	public String toString() {
		return "RecordCompleteNotify [filePath=" + filePath
				+ ", voiceDuration=" + voiceDuration + "]";
	}

}
