package com.yaya.realtime.voice.u3d.api;

import com.unity3d.player.UnityPlayer;
import com.yaya.gson.Gson;
import com.yaya.realtime.voice.u3d.api.event.AudioToolsInitEvent;
import com.yaya.realtime.voice.u3d.api.mode.LoadOnlineVoiceFinishedNotify;
import com.yaya.realtime.voice.u3d.api.mode.LoadOnlineVoiceProgressNotify;
import com.yaya.realtime.voice.u3d.api.mode.PlayAudioCompleteNotify;
import com.yaya.realtime.voice.u3d.api.mode.RecordCompleteNotify;
import com.yunva.video.sdk.media.voice.AudioAmrFilePlayService;
import com.yunva.video.sdk.media.voice.AudioAmrFileRecordService;
import com.yunva.video.sdk.media.voice.RecordOnCompleteListener;
import com.yunva.video.sdk.media.voice.VoicePlayCompletionListener;

import de.greenrobot.event.EventBus;

public class YvAudioToolsUnity3dExtension implements RecordOnCompleteListener, VoicePlayCompletionListener {
	
//	private final String RESP_METHOD_AudioTools_onIsRecordingResp        = "onIsRecordingResp"; //查询当前是否正在录制 msg:  "1" 或 "0"
	private final String RESP_METHOD_AudioTools_onRecordCompleteNotify   = "onRecordCompleteNotify";  //录制结束回调 msg: {"filePath":(string)filePath, "voiceDuration":(int)voiceDuration}

//	private final String RESP_METHOD_AudioTools_onPlayAudioResp          = "onPlayAudioResp";    //开始播放本地文件,返回播放顺序id
//	private final String RESP_METHOD_AudioTools_onPlayOnlineAudioResp    = "onPlayOnlineAudioResp"; //开始播放在线语音文件,返回播放顺序id
//	private final String RESP_METHOD_AudioTools_onIsPlayingResp          = "onIsPlayingResp"; //查询当前是否正在播放 msg:  "1" 或 "0"

	private final String RESP_METHOD_AudioTools_onPlayAudioCompleteNotify = "onPlayAudioCompleteNotify"; //播放结束通知 msg: {"result":(int)result, "playSequenceId":(int)playSequenceId}
	private final String RESP_METHOD_AudioTools_onLoadOnlineVoiceFinishedNotify = "onLoadOnlineVoiceFinishedNotify"; //下载加载完成通知 msg: {"result":(int)result, "msg":(String)msg, "voiceUrl":(String)voiceUrl, "playSequenceId":(int)playSequenceId}
	private final String RESP_METHOD_AudioTools_onLoadOnlineVoiceProgressNotify = "onLoadOnlineVoiceProgressNotify"; //下载加载进度通知 msg: {"totalSize":(int)totalSize, "progressSize":(int)progressSize, "voiceUrl":(String)voiceUrl, "playSequenceId":(int)playSequenceId}

	
	private static YvAudioToolsUnity3dExtension yvAudioToolsUnity3dExtension;
	
	private String gameObjectName;
	
	private AudioAmrFileRecordService audioAmrFileRecordService;
	private AudioAmrFilePlayService audioAmrFilePlayService;
	
	public static YvAudioToolsUnity3dExtension getInstance(){
		if(yvAudioToolsUnity3dExtension == null){
			yvAudioToolsUnity3dExtension = new YvAudioToolsUnity3dExtension();
		}
		return yvAudioToolsUnity3dExtension;
	}
	
	@SuppressWarnings("deprecation")
	public YvAudioToolsUnity3dExtension(){
		if(EventBus.getDefault().isRegistered(this))
			EventBus.getDefault().unregister(this);
		EventBus.getDefault().register(this, "onAudioToolsInitEvent");
	}
	
	/**
	 * 初始化
	 * @param gameObjectName
	 */
	public void audioTools_Init(String gameObjectName){
		this.gameObjectName = gameObjectName;
		EventBus.getDefault().post(new AudioToolsInitEvent());
	}
	
	public void onAudioToolsInitEventMainThread(AudioToolsInitEvent event){
		audioAmrFileRecordService = new AudioAmrFileRecordService(UnityPlayer.currentActivity);
		audioAmrFilePlayService = new AudioAmrFilePlayService();
	}
	
	/**************** 语音录制相关函数 ******************/
    /**
     * 开始语音录制
     * @param storeDataFilePath 语音录制数据保存的文件路径(文件的绝对路径)
     * @param minMillseconds 识别录音最短时间(录音少于该时间不做处理)
     * @param maxMillseconds 录音最长时间(超过该时间会自动停止录制)
     */
    public void audioTools_startRecord(String storeDataFilePath, int minMillseconds, int maxMillseconds){
    	if(audioAmrFileRecordService!=null){
    		audioAmrFileRecordService.startRecord(storeDataFilePath, this, minMillseconds, maxMillseconds);
    	}
    }
    
    /**
     * 停止语音录制
     */
    public void audioTools_stopRecord(){
    	if(audioAmrFileRecordService!=null){
    		audioAmrFileRecordService.stopRecord();
    	}
    }
    
    /**
     * 查询是否正在录制
     */
    public boolean audioTools_isRecording(){
    	if(audioAmrFileRecordService!=null){
//    		boolean isRecording = audioAmrFileRecordService.isRecording();
//    		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_AudioTools_onIsRecordingResp, isRecording?"1":"0"); 
    		return audioAmrFileRecordService.isRecording();
    	}else{
    		return false;
    	}
    }

	@Override
	public void onComplete(byte[] voiceData, long voiceDuration, String filePath) {
		// 录音完成
		RecordCompleteNotify notify = new RecordCompleteNotify();
		notify.setFilePath(filePath);
		notify.setVoiceDuration((int)voiceDuration);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_AudioTools_onRecordCompleteNotify, new Gson().toJson(notify)); 
	}

	@Override
	public void onMaxDuration() {
		// TODO Auto-generated method stub
		
	}
	
	/**************** 语音播放相关函数 ******************/
	/**
	 * 播放语音文件
	 * @param filePath 语音文件绝对路径
	 */
    public int audioTools_playAudio(String filePath){
    	if(audioAmrFilePlayService!=null){
    		return audioAmrFilePlayService.playAudio(filePath, this);
    	}else{
    		return -1;
    	}
    }
    
    /**
     * 在线播放语音
     * @param fileUrl 语音文件下载url
     */
    public int audioTools_playOnlineAudio(String fileUrl){
    	if(audioAmrFilePlayService!=null){
    		return audioAmrFilePlayService.playOnlineAudio(fileUrl, this);
    	}else{
    		return -1;
    	}
    }
    
    /**
     * 主动停止语音播放
     */
    public void audioTools_stopPlayAudio(){
    	if(audioAmrFilePlayService!=null){
    		audioAmrFilePlayService.stopAudio();
    	}
    }
    
    /**
     * 查询当前是否正在播放
     */
    public boolean audioTools_isPlaying(){
    	if(audioAmrFilePlayService!=null){
    		return audioAmrFilePlayService.isPlaying();
//    		boolean isPlaying = audioAmrFilePlayService.isPlaying();
//    		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_AudioTools_onIsPlayingResp, isPlaying?"1":"0"); 
    	}else{
    		return false;
    	}
    }

	@Override
	public void playCompletion(int result, String msg, int playSequenceId) {
		// 播放完成
		PlayAudioCompleteNotify notify = new PlayAudioCompleteNotify();
		notify.setResult(result);
		notify.setMsg(msg);
		notify.setPlaySequenceId(playSequenceId);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_AudioTools_onPlayAudioCompleteNotify, new Gson().toJson(notify)); 
	}

	@Override
	public void loadOnlineVoiceFinished(int result, String msg, String voiceUrl, int playSequenceId) {
		//下载加载完成
		LoadOnlineVoiceFinishedNotify notify = new LoadOnlineVoiceFinishedNotify();
		notify.setResult(result);
		notify.setMsg(msg);
		notify.setVoiceUrl(voiceUrl);
		notify.setPlaySequenceId(playSequenceId);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_AudioTools_onLoadOnlineVoiceFinishedNotify, new Gson().toJson(notify)); 
	}

	@Override
	public void loadOnlineVoiceProgress(int totalSize, int progressSize, String voiceUrl, int playSequenceId) {
		//下载加载进度
		LoadOnlineVoiceProgressNotify notify = new LoadOnlineVoiceProgressNotify();
		notify.setTotalSize(totalSize);
		notify.setProgressSize(progressSize);
		notify.setVoiceUrl(voiceUrl);
		notify.setPlaySequenceId(playSequenceId);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_AudioTools_onLoadOnlineVoiceProgressNotify, new Gson().toJson(notify)); 
	}
	
}
