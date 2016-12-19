package com.yaya.realtime.voice.u3d.api.mode;

public class PlayAudioCompleteNotify {
	
	private int result;
	private int playSequenceId;
	private String msg;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getPlaySequenceId() {
		return playSequenceId;
	}
	public void setPlaySequenceId(int playSequenceId) {
		this.playSequenceId = playSequenceId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "PlayAudioCompleteNotify [result=" + result
				+ ", playSequenceId=" + playSequenceId + ", msg=" + msg + "]";
	}

}
