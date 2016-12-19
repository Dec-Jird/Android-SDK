package com.yaya.realtime.voice.u3d.api.mode;

public class LoginResp {
	
	private int result;
	private String msg;
	private int yunvaId;
	private int modeType; //0自由模式，1抢麦模式，2指挥模式
    private boolean isLeader; //是否是队长
    private int leaderId;
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
	public int getYunvaId() {
		return yunvaId;
	}
	public void setYunvaId(int yunvaId) {
		this.yunvaId = yunvaId;
	}
	public int getModeType() {
		return modeType;
	}
	public void setModeType(int modeType) {
		this.modeType = modeType;
	}
	public boolean isLeader() {
		return isLeader;
	}
	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}
	public int getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}
	@Override
	public String toString() {
		return "LoginResp [result=" + result + ", msg=" + msg + ", yunvaId="
				+ yunvaId + ", modeType=" + modeType + ", isLeader=" + isLeader
				+ ", leaderId=" + leaderId + "]";
	}
	
}
