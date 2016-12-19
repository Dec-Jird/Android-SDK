package com.yaya.realtime.voice.u3d.api.mode;

public class QueryHistoryChatMsgResp {
	
	private ChatTextMessageNotify textMessage;
	private ChatVoiceMessageNotify voiceMessage;
	private ChatRichMessageNotify richMessage;
	public ChatTextMessageNotify getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(ChatTextMessageNotify textMessage) {
		this.textMessage = textMessage;
	}
	public ChatVoiceMessageNotify getVoiceMessage() {
		return voiceMessage;
	}
	public void setVoiceMessage(ChatVoiceMessageNotify voiceMessage) {
		this.voiceMessage = voiceMessage;
	}
	public ChatRichMessageNotify getRichMessage() {
		return richMessage;
	}
	public void setRichMessage(ChatRichMessageNotify richMessage) {
		this.richMessage = richMessage;
	}
	@Override
	public String toString() {
		return "QueryHistoryChatMsgResp [textMessage=" + textMessage
				+ ", voiceMessage=" + voiceMessage + ", richMessage="
				+ richMessage + "]";
	}

}
