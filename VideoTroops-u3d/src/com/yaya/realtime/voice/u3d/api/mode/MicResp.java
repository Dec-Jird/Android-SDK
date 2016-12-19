package com.yaya.realtime.voice.u3d.api.mode;

public class MicResp {
	
	private int result;
	private String msg;
	private boolean onoff;
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
	public boolean isOnoff() {
		return onoff;
	}
	public void setOnoff(boolean onoff) {
		this.onoff = onoff;
	}
	@Override
	public String toString() {
		return "MicResp [result=" + result + ", msg=" + msg + ", onoff="
				+ onoff + "]";
	}
	
}
