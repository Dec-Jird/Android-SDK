package com.yaya.realtime.voice.u3d.api.event;

public class VoiceRecognitionInitEvent {
	
	private int recognizeLanguage;
	private int outputTextLanguageType;
	public int getRecognizeLanguage() {
		return recognizeLanguage;
	}
	public void setRecognizeLanguage(int recognizeLanguage) {
		this.recognizeLanguage = recognizeLanguage;
	}
	public int getOutputTextLanguageType() {
		return outputTextLanguageType;
	}
	public void setOutputTextLanguageType(int outputTextLanguageType) {
		this.outputTextLanguageType = outputTextLanguageType;
	}
	@Override
	public String toString() {
		return "VoiceRecognitionInitEvent [recognizeLanguage="
				+ recognizeLanguage + ", outputTextLanguageType="
				+ outputTextLanguageType + "]";
	}

}
