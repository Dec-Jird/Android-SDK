package com.yaya.realtime.voice.u3d.api;

import com.baidu.voicerecognition.android.SpeechLogger;
import com.unity3d.player.UnityPlayer;
import com.yaya.gson.Gson;
import com.yaya.realtime.voice.u3d.api.event.VoiceRecognitionInitEvent;
import com.yaya.realtime.voice.u3d.api.mode.RecognizeFinishResp;
import com.yaya.realtime.voice.u3d.api.mode.RecordAndRecognizeFinishResp;
import com.yunva.video.sdk.recognition.RecognitionVoiceService;
import com.yunva.video.sdk.recognition.RecognizeChineseType;
import com.yunva.video.sdk.recognition.RecognizeLanguageType;

import de.greenrobot.event.EventBus;

public class YvVoiceRecognitionUnity3dExtension {
	
	private final String RESP_METHOD_RECOGNIZE_onRecognizeFinishResp             = "onRecognizeFinishResp";  //只返回文本的语音识别 msg : {"text":(string)text}
	private final String RESP_METHOD_RECOGNIZE_onRecordAndRecognizeFinishResp    = "onRecordAndRecognizeFinishResp"; //返回录制文件 + 语音识别文本 msg: {"text":(string)text, "voicePath":(string)voicePath, "voiceDuration":(int)voiceDuration};

//	private final String RESP_METHOD_RECOGNIZE_onRecognizeError                  = "onRecognizeError"; //语音识别错误 msg: {"errorStatus":(int)errorStatus, "errorSubStatus":(int)errorSubStatus}
	
	private static YvVoiceRecognitionUnity3dExtension yvVoiceRecognitionUnity3dExtension;
	
	private String gameObjectName;
	
	private RecognitionVoiceService recognitionVoiceService;
	
	public static YvVoiceRecognitionUnity3dExtension getInstance(){
		if(yvVoiceRecognitionUnity3dExtension == null){
			yvVoiceRecognitionUnity3dExtension = new YvVoiceRecognitionUnity3dExtension();
		}
		return yvVoiceRecognitionUnity3dExtension;
	}
	
	@SuppressWarnings("deprecation")
	public YvVoiceRecognitionUnity3dExtension(){
		if(EventBus.getDefault().isRegistered(this))
			EventBus.getDefault().unregister(this);
		EventBus.getDefault().register(this, "onVoiceRecognitionInitEvent");
		EventBus.getDefault().register(this, "onRecognizeFinishResp");
		EventBus.getDefault().register(this, "onRecordAndRecognizeFinishResp");
	}
	
	/**
	 * 初始化语音识别
	 * @param recognizeLanguage 语音识别语言 (即您说话的语言 0: 普通话, 1:广东话, 2:美式英语)
	 * @param outputTextLanguageType 输出文字的语言 (0:简体字  1:繁体字)
	 */
    public void voiceRecognitionInit(int recognizeLanguage, int outputTextLanguageType, String gameObjectName){
    	this.gameObjectName = gameObjectName;
    	VoiceRecognitionInitEvent event = new VoiceRecognitionInitEvent();
    	event.setRecognizeLanguage(recognizeLanguage);
    	event.setOutputTextLanguageType(outputTextLanguageType);
    	EventBus.getDefault().post(event);
//    	Log.i("YAYA","RECO gameObjectName: "+gameObjectName);
    }
    
    public void onVoiceRecognitionInitEventMainThread(VoiceRecognitionInitEvent event){
    	int recognizeLanguage = event.getRecognizeLanguage();
    	int outputTextLanguageType = event.getOutputTextLanguageType();
    	RecognizeLanguageType languageType = RecognizeLanguageType.LANGUAGE_CHINESE;
    	RecognizeChineseType outChineseType = RecognizeChineseType.SIMPLE_CHINESE;
    	if(recognizeLanguage == 0){//普通话
    		languageType = RecognizeLanguageType.LANGUAGE_CHINESE;
    	}else if(recognizeLanguage == 1){//广东话
    		languageType = RecognizeLanguageType.LANGUAGE_CANTONESE;
    	}else if(recognizeLanguage == 2){//美式英语
    		languageType = RecognizeLanguageType.LANGUAGE_ENGLISH;
    	}
    	if(outputTextLanguageType == 0){//简体字
    		outChineseType = RecognizeChineseType.SIMPLE_CHINESE;
    	}else if(outputTextLanguageType == 1){//繁体字
    		outChineseType = RecognizeChineseType.TRADITIONAL_CHINESE;
    	}
    	recognitionVoiceService = RecognitionVoiceService.getInstance(UnityPlayer.currentActivity, languageType, outChineseType);
    	SpeechLogger.setLogLevel(SpeechLogger.SPEECH_LOG_LEVEL_OFF);
    }
    
    /**开始语音识别，只返回文字*/
    public void startRecognize(){
    	if(recognitionVoiceService!=null){
    		recognitionVoiceService.startRecognize();
    	}
    }

    /**结束语音识别，只返回文字*/
    public void finishRecognize(){
    	if(recognitionVoiceService!=null){
    		recognitionVoiceService.finishRecognize();
    	}
    }


    
    /**开始录制+语音识别*/
    public void startRecordAndRecognize(){
    	if(recognitionVoiceService!=null){
    		recognitionVoiceService.startRecordAndRecognize();
    	}
    }

    /**录制+语音识别结束*/
    public void finishRecordAndRecognize(){
    	if(recognitionVoiceService!=null){
    		recognitionVoiceService.finishRecordAndRecognize();
    	}
    }
    
    /**
     * 释放资源
     */
    public void releaseResource(){
    	if(recognitionVoiceService!=null){
    		recognitionVoiceService.realseRecognitionService();
    	}
    	recognitionVoiceService = null;
    	yvVoiceRecognitionUnity3dExtension = null;
    	EventBus.getDefault().unregister(this);
    }
    
    /**
     * 只返回文本的语音识别
     * @param resp
     */
    public void onRecognizeFinishResp(RecognizeFinishResp resp){
    	UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_RECOGNIZE_onRecognizeFinishResp, new Gson().toJson(resp));
    }
    
    /**
     * 返回录制文件 + 语音识别文本
     * @param resp
     */
    public void onRecordAndRecognizeFinishResp(RecordAndRecognizeFinishResp resp){
    	UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_RECOGNIZE_onRecordAndRecognizeFinishResp, new Gson().toJson(resp));
    }
    
}
