package com.yaya.realtime.voice.u3d.api.mode;

public class LogoutResp {
	
	private int result;
	private String msg;
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
	@Override
	public String toString() {
		return "LogoutResp [result=" + result + ", msg=" + msg + "]";
	}

}
