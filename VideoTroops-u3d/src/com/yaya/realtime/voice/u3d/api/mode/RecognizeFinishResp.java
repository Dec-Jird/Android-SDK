package com.yaya.realtime.voice.u3d.api.mode;

public class RecognizeFinishResp {
	
	private int result;
	private String msg;
	private int errorStatus;
	private int errorSubStatus;
	private String text;
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
	public int getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}
	public int getErrorSubStatus() {
		return errorSubStatus;
	}
	public void setErrorSubStatus(int errorSubStatus) {
		this.errorSubStatus = errorSubStatus;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "RecognizeFinishResp [result=" + result + ", msg=" + msg
				+ ", errorStatus=" + errorStatus + ", errorSubStatus="
				+ errorSubStatus + ", text=" + text + "]";
	}

}
