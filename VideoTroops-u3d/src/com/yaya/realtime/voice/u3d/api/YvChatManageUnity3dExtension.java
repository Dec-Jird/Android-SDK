package com.yaya.realtime.voice.u3d.api;

import java.util.ArrayList;
import java.util.List;

import com.unity3d.player.UnityPlayer;
import com.yaya.gson.Gson;
import com.yaya.realtime.voice.u3d.api.event.SDKChatMicEvent;
import com.yaya.realtime.voice.u3d.api.event.SDKInitEvent;
import com.yaya.realtime.voice.u3d.api.mode.AuthResp;
import com.yaya.realtime.voice.u3d.api.mode.ChatRealTimeVoiceMessageNotify;
import com.yaya.realtime.voice.u3d.api.mode.ChatRichMessageNotify;
import com.yaya.realtime.voice.u3d.api.mode.ChatTextMessageNotify;
import com.yaya.realtime.voice.u3d.api.mode.ChatVoiceMessageNotify;
import com.yaya.realtime.voice.u3d.api.mode.LoginResp;
import com.yaya.realtime.voice.u3d.api.mode.LogoutResp;
import com.yaya.realtime.voice.u3d.api.mode.MicResp;
import com.yaya.realtime.voice.u3d.api.mode.QueryHistoryChatMsgResp;
import com.yaya.realtime.voice.u3d.api.mode.RecognizeFinishResp;
import com.yaya.realtime.voice.u3d.api.mode.RecordAndRecognizeFinishResp;
import com.yaya.realtime.voice.u3d.api.mode.SendRealTimeVoiceMessageResp;
import com.yaya.realtime.voice.u3d.api.mode.SendVoiceMessageResp;
import com.yaya.realtime.voice.u3d.api.mode.TroopsKickOutNotify;
import com.yaya.realtime.voice.u3d.api.mode.TroopsUserStateNotify;
import com.yunva.video.sdk.VideoTroopsRespondListener;
import com.yunva.video.sdk.YunvaVideoTroops;
import com.yunva.video.sdk.interfaces.logic.model.InviteUserVideoNotify;
import com.yunva.video.sdk.interfaces.logic.model.InviteUserVideoRespNotify;
import com.yunva.video.sdk.interfaces.logic.model.ModeSettingResp;
import com.yunva.video.sdk.interfaces.logic.model.QueryBlackUserResp;
import com.yunva.video.sdk.interfaces.logic.model.QueryHistoryMsgResp;
import com.yunva.video.sdk.interfaces.logic.model.QueryUserListResp;
import com.yunva.video.sdk.interfaces.logic.model.RealTimeVoiceMessageNotify;
import com.yunva.video.sdk.interfaces.logic.model.SendRichMessageResp;
import com.yunva.video.sdk.interfaces.logic.model.SpeechDiscernResp;
import com.yunva.video.sdk.interfaces.logic.model.TextMessageNotify;
import com.yunva.video.sdk.interfaces.logic.model.TextMessageResp;
import com.yunva.video.sdk.interfaces.logic.model.TroopsModeChangeNotify;
import com.yunva.video.sdk.interfaces.logic.model.TroopsUser;
import com.yunva.video.sdk.interfaces.logic.model.UploadPicResp;
import com.yunva.video.sdk.interfaces.logic.model.UploadVoiceResp;
import com.yunva.video.sdk.interfaces.logic.model.UserLoginNotify;
import com.yunva.video.sdk.interfaces.logic.model.UserStateNotify;
import com.yunva.video.sdk.interfaces.logic.model.VideoStateNotify;
import com.yunva.video.sdk.interfaces.logic.model.VoiceMessageNotify;
import com.yunva.video.sdk.interfaces.logic.type.AudioPlayStreamType;
import com.yunva.video.sdk.interfaces.logic.type.MicActionType;
import com.yunva.video.sdk.interfaces.logic.type.RichTextMessageType;

import de.greenrobot.event.EventBus;

/**
 * 呀呀实时语音android版本更新，从原来的SDK_2.0.2版本更新为SDK_2.0.6_201601071656版本。
 * @author Administrator
 *
 */
public class YvChatManageUnity3dExtension implements VideoTroopsRespondListener {
	
	private final String RESP_METHOD_ChatManage_onSDKInitDidFinish              = "onSDKInitDidFinish";  // 初始化成功
//	private final String RESP_METHOD_ChatManage_onIsAutoPlayVoiceMessageResp    = "onIsAutoPlayVoiceMessageResp";//查询是否自动播放收到的语音留言消息 msg:  "1" 或 "0"
//	private final String RESP_METHOD_ChatManage_onIsPausePlayRealAudioResp      = "onIsPausePlayRealAudioResp";//查询是否暂停播放实时语音聊天 msg:  "1" 或 "0"
//	private final String RESP_METHOD_ChatManage_onGetCurrentMicStateResp        = "onGetCurrentMicStateResp";//获取当前是否是上麦状态 msg:  "1" 或 "0"
	
	private final String RESP_METHOD_ChatManage_onAuthResp                      = "onAuthResp"; //登录（只是认证成功）返回 msg:  {"result":(int)result,"msg":(string)msg}

	private final String RESP_METHOD_ChatManage_onLoginResp                     = "onLoginResp"; //登录返回 msg:  {"result":(int)result,"msg":(string)msg,"yunvaId":(int)yunvaId, "modeType":(int)modeType, "isLeader":(boolean)isLeader, "leaderId":(int)leaderId}
	private final String RESP_METHOD_ChatManage_onLogoutResp                    = "onLogoutResp"; //注销返回 msg: {"result":(int)result, @"msg":(string)msg}

	private final String RESP_METHOD_ChatManage_onSendTextMessageResp           = "onSendTextMessageResp";  //发送文本回复 msg: {"result":(int)result, @"msg":(string)msg, "expand":(string)expand};
	private final String RESP_METHOD_ChatManage_onTextMessageNotify             = "onTextMessageNotify";    //收到文本消息 msg: {"yunvaId":(int)yunvaId, "chatRoomId":(string)chatRoomId, "text":(string)text, "time":(UInt64)time, "expand":(string)expand}

	private final String RESP_METHOD_ChatManage_onSendVoiceMessageResp          = "onSendVoiceMessageResp"; //发送语音回复 msg: {"result":(int)result, @"msg":(string)msg,  "voiceUrl":(string)voiceUrl, "voiceDuration":(UInt64)voiceDuration, "filePath":(string)filePath,  "expand":(string)expand};
	private final String RESP_METHOD_ChatManage_onVoiceMessageNotify            = "onVoiceMessageNotify"; //收到语音消息 msg: {"yunvaId":(int)yunvaId, "chatRoomId":(string)chatRoomId, "voiceUrl":(string)voiceUrl,   "voiceTime":(UInt64)voiceTime,    "time":(UInt64)time, "expand":(string)expand}
	
	private final String  RESP_METHOD_ChatManage_onSendRichMessageResp          = "onSendRichMessageResp"; //发送富消息(语音+文本)回复 msg: {"result":(int)result, "msg":(string)msg, "text":(string)text,  "voiceUrl":(string)voiceUrl, "voiceDuration":(UInt64)voiceDuration, "filePath":(string)filePath,  "expand":(string)expand};
	private final String  RESP_METHOD_ChatManage_onRichMessageNotify            = "onRichMessageNotify";  //收到富消息(语音+文本) msg: {"yunvaId":(int)yunvaId, "chatRoomId":(string)chatRoomId, "text":(string)text, "voiceUrl":(string)voiceUrl,   "voiceTime":(UInt64)voiceTime,    "time":(UInt64)time, "expand":(string)expand}

	//实时语音相关回调函数
	private final String RESP_METHOD_ChatManage_onChatMicResp                   = "onChatMicResp";      //上下麦回复 msg: {"result":(int)result, @"msg":(string)msg, "onoff":(boolean)onoff}
	private final String RESP_METHOD_ChatManage_onSendRealTimeVoiceMessageError = "onSendRealTimeVoiceMessageError"; //发送实时语音失败回调 msg: {"result":(int)result, @"msg":(string)msg}
	private final String RESP_METHOD_ChatManage_onRealTimeVoiceMessageNotify    = "onRealTimeVoiceMessageNotify";   //收到实时语音 msg: {"yunvaId":(int)yunvaId,  "chatRoomId":(string)chatRoomId, "expand":(string)expand}
	private final String RESP_METHOD_ChatManage_onKickOutNotify                 = "onKickOutNotify";   //被踢出队伍通知 msg: {"msg":(string)msg}
	private final String RESP_METHOD_ChatManage_onUserStateNotify               = "onUserStateNotify"; //用户状态通知 msg: {"yunvaId":(int)yunvaId, "chatRoomId":(string)chatRoomId, "type":(int)type, "state":(string)state, "msg":(string)msg} //type:1是send,2是receiver   state:11表示语音、文字都开启；00表示语音、文字都关闭 ；前面的表示语音，后面的表示文字，1表示开启，0表示关闭
	private final String RESP_METHOD_ChatManage_onMicModeSettingResp            = "onMicModeSettingResp"; //队伍模式设置接口 msg: {"result":(int)result, @"msg":(string)msg}
	private final String RESP_METHOD_ChatManage_onMicModeChangeNotify           = "onMicModeChangeNotify"; //队伍模式更换通知 msg: {"modeType":(int)modeType, "isLeader":(boolean)isLeader} //modeType:0自由模式, 1抢麦模式, 2指挥模式   isLeader:自己是否是队长

	//查询历史消息回复
	private final String RESP_METHOD_ChatManage_onQueryHistoryMsgResp           = "onQueryHistoryMsgResp"; //查询历史消息回复 msg: {"textMessage":{收到文本消息 msg}, "voiceMessage":{收到语音消息 msg}, "richMessage":{收到富消息(语音+文本) msg}}
	
	private final String RESP_METHOD_ChatManage_onUploadVoiceResp               = "onUploadVoiceResp";//上传语音文件回应 msg: {"result":(Long)result, "msg":(String)msg, "voiceUrl":(String)voiceUrl, "expand":(String)expand}
	
//	private final String RESP_METHOD_ChatManage_onLoginResp                     = "onLoginResp"; //登录返回 msg:  {"result":(int)result,"msg":(string)msg,"yunvaId":(int)yunvaId, "modeType":(int)modeType, "isLeader":(boolean)isLeader, "leaderId":(int)leaderId}
//	private final String RESP_METHOD_ChatManage_onLogoutResp                    = "onLogoutResp"; //注销返回 msg: {"result":(int)result, @"msg":(string)msg}
	
	private static YvChatManageUnity3dExtension yvChatManageUnity3dExtension;
	
	private String gameObjectName;
	private YunvaVideoTroops yunvaVideoTroops;
	

	
	public static YvChatManageUnity3dExtension getInstance(){
		if(yvChatManageUnity3dExtension == null){
			yvChatManageUnity3dExtension = new YvChatManageUnity3dExtension();
		}
		return yvChatManageUnity3dExtension;
	}
	
	@SuppressWarnings("deprecation")
	public YvChatManageUnity3dExtension(){
		if(EventBus.getDefault().isRegistered(this))
			EventBus.getDefault().unregister(this);
		EventBus.getDefault().register(this, "onSDKInitEvent");
		EventBus.getDefault().register(this, "onQueryHistoryMsgRespEvent");
		EventBus.getDefault().register(this, "onSDKChatMicEvent");
	}
	
	/**
	 * 初始化SDK
	 * @param appId appId
	 * @param isTest 是否为测试环境
	 * @param gameObjectName 回调接收对象
	 */
	public void YvChatSDKInit(String appId, boolean isTest, String gameObjectName){

		this.gameObjectName = gameObjectName;
		
//		Log.d("YAYA", appId + ", " + isTest + ", " + gameObjectName);

		SDKInitEvent event = new SDKInitEvent();
		event.setAppId(appId);
		event.setTest(isTest);
		
		EventBus.getDefault().post(event);
	}
	
	public void onSDKInitEventMainThread(SDKInitEvent event){
		yunvaVideoTroops = YunvaVideoTroops.getInstance(UnityPlayer.currentActivity, event.getAppId(),
				this, event.isTest(), false);

	}
	
	/**
	 * 自动登录队伍
	 * @param seq
	 */
	public void loginWithSeq(String seq){
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.login(seq);
		}
	}
	
	/**
	 * 第三方绑定登录队伍
	 * @param tt
	 * @param seq
	 */
	public void loginBindingWithTT(String tt, String seq){
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.loginBinding(tt, seq, false, (byte)0, 0);
		}
	}
	
	/**
	 * 登出队伍
	 */
	public void logout(){
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.logout();
		}
	}
	
	/**
	 * 获取队伍历史消息
	 * @param pageIndex 查询的是第几页(从0开始)
	 * @param pageSize 每页的消息个数
	 */
	public void queryHistoryMsgReqWithPageIndex(int pageIndex, int pageSize){
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.queryHistoryMsgReq(pageIndex, pageSize);
		}
	}
	
	/**
	 * 发送文本信息
	 * @param text 消息内容
	 * @param expand 扩展内容
	 */
	public void sendTextMessage(String text, String expand){
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.sendTextMessage(text, expand);
		}
	}
	
	/**
	 * 设置播放实时语音声源(系统声音/媒体声音)
	 * @param streamType 1:系统声音、3:媒体声音
	 */
	public void setAudioPlayStreamType(int streamType){
		if(yunvaVideoTroops!=null){
			if(AudioPlayStreamType.STREAM_MUSIC != streamType){//默认系统声音
				streamType = AudioPlayStreamType.STREAM_SYSTEM;
			}
			yunvaVideoTroops.setAudioPlayStreamType(streamType);
		}
	}
	
	/**
	 * 发送语音留言
	 * @param voiceFilePath 语音文件路径
	 * @param voiceDuration 语音时长
	 * @param expand 扩展内容
	 */
	public void sendVoiceMessage(String voiceFilePath, int voiceDuration, String expand){
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.uploadVoiceMessage(voiceFilePath, voiceDuration, expand);
		}
	}
	
	/**
	 * 发送文字+语音留言
	 * @param text 文字内容
	 * @param voiceFilePath 语音文件路径
	 * @param voiceDuration 语音时长
	 * @param expand 扩展内容
	 */
	public void sendRichMessage(String text, String voiceFilePath, int voiceDuration, String expand){
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.sendRichMessage(text, voiceFilePath, (long)voiceDuration, expand);
		}
	}
	
	/**
	 * 设置是否自动播放收到的语音留言消息,默认为false
	 * @param isAutoPlayVoiceMessage
	 */
	public void setIsAutoPlayVoiceMessage(boolean isAutoPlayVoiceMessage){
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.setAutoPlayVoiceMessage(isAutoPlayVoiceMessage);
		}
	}
	
	/**
	 * 查询是否自动播放收到的语音留言消息
	 */
	public boolean isAutoPlayVoiceMessage(){
		if(yunvaVideoTroops!=null){
			return yunvaVideoTroops.isAutoPlayVoiceMessage();
//			boolean isAutoPlayVoiceMessage = yunvaVideoTroops.isAutoPlayVoiceMessage();
//    		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onIsAutoPlayVoiceMessageResp, isAutoPlayVoiceMessage?"1":"0"); 
		}else{
			return false;
		}
	}
	
	/**
     *  聊天中的实时语音上麦，下麦
     *
     *  @param onOff         true:上麦  false:下麦
     *  @param expand        用户自定义扩展字符串
     */
//    public void chatMic(boolean onOff, String expand){
//    	if(yunvaVideoTroops!=null){
//    		//开麦:MicActionType.ACTION_TYPE_OPEN_MIC
//   	 		//关麦:MicActionType.ACTION_TYPE_CLOSE_MIC
//    		yunvaVideoTroops.mic(onOff?MicActionType.ACTION_TYPE_OPEN_MIC:MicActionType.ACTION_TYPE_CLOSE_MIC, expand);
//    	}
//    }
    
	public void chatMic(boolean onOff, String expand){
		SDKChatMicEvent event = new SDKChatMicEvent();
		
		event.setExpand(expand);
		event.setOnOff(onOff);
		
		EventBus.getDefault().post(event);
//    	if(yunvaVideoTroops!=null){
//    		//开麦:MicActionType.ACTION_TYPE_OPEN_MIC
//   	 		//关麦:MicActionType.ACTION_TYPE_CLOSE_MIC
//    		yunvaVideoTroops.mic(onOff?MicActionType.ACTION_TYPE_OPEN_MIC:MicActionType.ACTION_TYPE_CLOSE_MIC, expand);
//    	}
    }
	
	public void onSDKChatMicEventMainThread(SDKChatMicEvent event){	
		if(yunvaVideoTroops!=null){
    		//开麦:MicActionType.ACTION_TYPE_OPEN_MIC
   	 		//关麦:MicActionType.ACTION_TYPE_CLOSE_MIC
    		yunvaVideoTroops.mic(event.getOnOff()?MicActionType.ACTION_TYPE_OPEN_MIC:MicActionType.ACTION_TYPE_CLOSE_MIC, event.getExpand());
    	}
	}
	
    /**
     *  设置是否暂停播放实时语音聊天
     *
     *  @param isPause       true:暂停实时播放  false:恢复实时播放
     */
    public void setPausePlayRealAudio(boolean isPause){
    	if(yunvaVideoTroops!=null){
    		yunvaVideoTroops.setPausePlayAudio(isPause);
    	}
    }
    
    /**
     *  查询是否暂停播放实时语音聊天
     */
    public boolean isPausePlayRealAudio(){
    	if(yunvaVideoTroops!=null){
    		return yunvaVideoTroops.isPausePlayAudio();
//    		boolean isPausePlayAudio = yunvaVideoTroops.isPausePlayAudio();
//    		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onIsPausePlayRealAudioResp, isPausePlayAudio?"1":"0"); 
    	}else{
    		return false;
    	}
    }
    
    /**
     *  设置麦模式，mode:0自由模式，1抢麦模式，2指挥模式
     *
     *  @param modeType      0自由模式，1抢麦模式，2指挥模式
     */
    public void micModeSettingReq(int modeType){
    	if(yunvaVideoTroops!=null){
    		if(modeType==0){
    			yunvaVideoTroops.modeSettingReq((byte)0, (byte)0);
    		}else if(modeType==1){
    			yunvaVideoTroops.modeSettingReq((byte)1, (byte)0);
    		}else if(modeType==2){
    			yunvaVideoTroops.modeSettingReq((byte)1, (byte)1);
    		}
    	}
    }
    
//    /**
//     *  获取当前是否是上麦状态
//     */
//    public void getCurrentMicState(){
//    	if(yunvaVideoTroops!=null){
//    		yunvaVideoTroops.
//    	}
//    }
    
    /**
	 * 上传语音文件
	 * @param voiceFilePath 语音文件路径
	 * @param fileRetainTimeType 文件保存时长类型 参见UploadFileRetainTimeType, 如:UploadFileRetainTimeType.type_6_hours 保存6小时
	 * @param expand 扩展内容
	 */
	public void uploadVoiceFile(String voiceFilePath, int fileRetainTimeType, String expand) {
		if(yunvaVideoTroops!=null){
			yunvaVideoTroops.uploadVoiceFile(voiceFilePath, String.valueOf(fileRetainTimeType), expand);
		}
	}
    
    /**
     * 释放资源
     */
    public void releaseResource(){
    	if(yunvaVideoTroops!=null){
    		yunvaVideoTroops.onDestroy();
    	}
    	EventBus.getDefault().unregister(this);
    	yvChatManageUnity3dExtension = null;
    }
	
	@Override
	public void initComplete() {
		// 初始化完成
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onSDKInitDidFinish, "Init Success."); 
	}

	@Override
	public void onBdMineTxtMsg(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInviteUserVideoNotify(InviteUserVideoNotify arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInviteUserVideoRespNotify(InviteUserVideoRespNotify arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onLoginResp(
			com.yunva.video.sdk.interfaces.logic.model.LoginResp resp) {
		onLoginResp((int)(long)resp.getResult(), resp.getMsg(), resp.getYunvaId(), resp.getModeType(), resp.isLeader(), resp.getLeaderId());
	} 

	public void onLoginResp(int result, String msg, Long yunvaId, int modeType, boolean isLeader, Long leaderId) {
		// 登录回应
		LoginResp resp = new LoginResp();
		resp.setResult(result);
		resp.setMsg(msg);
		resp.setModeType(modeType);
		resp.setLeader(isLeader);
		if(yunvaId!=null){
			resp.setYunvaId((int)(long)yunvaId);
		}
		if(leaderId!=null){
			resp.setLeaderId((int)(long)leaderId);
		}
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onLoginResp, new Gson().toJson(resp)); 
	}

	@Override
	public void onLogoutResp(int result, String msg) {
		// 登出回应
		LogoutResp resp = new LogoutResp();
		resp.setResult(result);
		resp.setMsg(msg);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onLogoutResp, new Gson().toJson(resp)); 
	}

	@Override
	public void onMicResp(int result, String msg, String actionType) {
		// 上下麦回应
		//开麦:MicActionType.ACTION_TYPE_OPEN_MIC; 关麦:MicActionType.ACTION_TYPE_CLOSE_MIC
		MicResp resp = new MicResp();
		resp.setResult(result);
		resp.setMsg(msg);
		if(MicActionType.ACTION_TYPE_OPEN_MIC.equals(actionType)){
			resp.setOnoff(true);
		}else{
			resp.setOnoff(false);
		}
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onChatMicResp, new Gson().toJson(resp));
	}

	@Override
	public void onModeSettingResp(ModeSettingResp resp) {
		// 队伍模式设置回应
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onMicModeSettingResp, new Gson().toJson(resp));
	}

	@Override
	public void onOnlyUploadVoiceMessageResp(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueryBlackUserResp(QueryBlackUserResp arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueryHistoryMsgResp(QueryHistoryMsgResp resp) {
		// 历史消息
		EventBus.getDefault().post(resp);
	}
	
	public void onQueryHistoryMsgRespEventAsync(QueryHistoryMsgResp resp){
		List<QueryHistoryChatMsgResp> list = new ArrayList<QueryHistoryChatMsgResp>();
		if(resp.getMsgs()!=null && resp.getMsgs().size()>0){
			for(TextMessageNotify notify : resp.getMsgs()){
				QueryHistoryChatMsgResp queryHistoryChatMsgResp = new QueryHistoryChatMsgResp();
				boolean isHasVoice = false;
				if(notify.getRichText()!=null && notify.getRichText().length()>0){
					String str[] = notify.getRichText().split("\\|");
					if (str != null && str.length > 1) {
						if (RichTextMessageType.TYPE_CHAT_MSG_VOICE.equals(str[0])) {// 语音留言
							String voiceUrl = str[1];
							String voiceTime = str[2];
							isHasVoice = true;
							if(notify.getText()!=null && notify.getText().length()>0){
								//语音+文字
								ChatRichMessageNotify chatRichMessageNotify = new ChatRichMessageNotify();
								if(notify.getYunvaId()!=null){
									chatRichMessageNotify.setYunvaId((int)(long)notify.getYunvaId());
								}
								chatRichMessageNotify.setChatRoomId(notify.getTroopsId());
								chatRichMessageNotify.setText(notify.getText());
								chatRichMessageNotify.setTime(notify.getTime());
								chatRichMessageNotify.setExpand(notify.getExpand());
								chatRichMessageNotify.setVoiceUrl(voiceUrl);
								if (voiceTime != null) {
									try {
										chatRichMessageNotify.setVoiceTime(Long.parseLong(voiceTime));
									} catch (Exception e) {
									}
								}
								queryHistoryChatMsgResp.setRichMessage(chatRichMessageNotify);
							}else{
								//语音
								ChatVoiceMessageNotify chatVoiceMessageNotify = new ChatVoiceMessageNotify();
								if(notify.getYunvaId()!=null){
									chatVoiceMessageNotify.setYunvaId((int)(long)notify.getYunvaId());
								}
								chatVoiceMessageNotify.setChatRoomId(notify.getTroopsId());
								chatVoiceMessageNotify.setTime(notify.getTime());
								chatVoiceMessageNotify.setExpand(notify.getExpand());
								chatVoiceMessageNotify.setVoiceUrl(voiceUrl);
								if (voiceTime != null) {
									try {
										chatVoiceMessageNotify.setVoiceTime(Long.parseLong(voiceTime));
									} catch (Exception e) {
									}
								}
								queryHistoryChatMsgResp.setVoiceMessage(chatVoiceMessageNotify);
							}
						}
					}
				}
				
				if(!isHasVoice){
					//文字
					ChatTextMessageNotify chatTextMessageNotify = new ChatTextMessageNotify();
					if(notify.getYunvaId()!=null){
						chatTextMessageNotify.setYunvaId((int)(long)notify.getYunvaId());
					}
					chatTextMessageNotify.setChatRoomId(notify.getTroopsId());
					chatTextMessageNotify.setText(notify.getText());
					chatTextMessageNotify.setTime(notify.getTime());
					chatTextMessageNotify.setExpand(notify.getExpand());
					
					queryHistoryChatMsgResp.setTextMessage(chatTextMessageNotify);
				}
				
				list.add(queryHistoryChatMsgResp);
			}
		}
		
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onQueryHistoryMsgResp, new Gson().toJson(list));
	}

	@Override
	public void onQueryUserListResp(QueryUserListResp arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRealTimeVoiceMessageNotify(RealTimeVoiceMessageNotify notify) {
		// 收到实时语音
		ChatRealTimeVoiceMessageNotify chatRealTimeVoiceMessageNotify = new ChatRealTimeVoiceMessageNotify();
		if(notify.getYunvaId()!=null){
			chatRealTimeVoiceMessageNotify.setYunvaId((int)(long)notify.getYunvaId());
		}
		chatRealTimeVoiceMessageNotify.setChatRoomId(notify.getTroopsId());
		chatRealTimeVoiceMessageNotify.setExpand(notify.getExpand());
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onRealTimeVoiceMessageNotify, new Gson().toJson(chatRealTimeVoiceMessageNotify));
	}

	@Override
	public void onSendRealTimeVoiceMessageResp(int result, String msg) {
		// 发送实时语音失败回调
		SendRealTimeVoiceMessageResp resp = new SendRealTimeVoiceMessageResp();
		resp.setResult(result);
		resp.setMsg(msg);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onSendRealTimeVoiceMessageError, new Gson().toJson(resp));
	}

	@Override
	public void onSendTextMessageResp(TextMessageResp resp) {
		// 发送文本消息回应
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onSendTextMessageResp, new Gson().toJson(resp));
	}

	@Override
	public void onTextMessageNotify(TextMessageNotify notify) {
		// 文本消息通知
		ChatTextMessageNotify chatTextMessageNotify = new ChatTextMessageNotify();
		if(notify.getYunvaId()!=null){
			chatTextMessageNotify.setYunvaId((int)(long)notify.getYunvaId());
		}
		chatTextMessageNotify.setChatRoomId(notify.getTroopsId());
		chatTextMessageNotify.setText(notify.getText());
		chatTextMessageNotify.setTime(notify.getTime());
		chatTextMessageNotify.setExpand(notify.getExpand());
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onTextMessageNotify, new Gson().toJson(chatTextMessageNotify));
	}

	@Override
	public void onTroopsKickOutNotify(String msg) {
		// 被踢出队伍通知
		TroopsKickOutNotify notify = new TroopsKickOutNotify();
		notify.setMsg(msg);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onKickOutNotify, new Gson().toJson(notify));
	}

	@Override
	public void onTroopsModeChangeNotify(TroopsModeChangeNotify notify) {
		// 队伍模式更换通知
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onMicModeChangeNotify, new Gson().toJson(notify));
	}

	@Override
	public void onUploadBdVoiceMessageResp(int arg0, String arg1, String arg2,
			long arg3, String arg4, String arg5, String arg6) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUploadPicResp(UploadPicResp arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUploadVoiceMessageResp(int result, String msg, String voiceUrl, 
			long voiceDuration, String filePath, String expand) {
		// 上传语音留言消息回应
		SendVoiceMessageResp resp = new SendVoiceMessageResp();
		resp.setResult(result);
		resp.setMsg(msg);
		resp.setVoiceUrl(voiceUrl);
		resp.setVoiceDuration(voiceDuration);
		resp.setFilePath(filePath);
		resp.setExpand(expand);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onSendVoiceMessageResp, new Gson().toJson(resp));
	}

	@Override
	public void onUploadVoiceResp(UploadVoiceResp resp) {
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onUploadVoiceResp, new Gson().toJson(resp));
	}

	@Override
	public void onUserLoginNotify(UserLoginNotify arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserStateNotify(UserStateNotify notify) {
		// 用户状态通知
		TroopsUserStateNotify troopsUserStateNotify = new TroopsUserStateNotify();
		if(notify.getYunvaId()!=null){
			troopsUserStateNotify.setYunvaId((int)(long)notify.getYunvaId());
		}
		troopsUserStateNotify.setChatRoomId(notify.getTroopsId());
		if(notify.getType()!=null){
			troopsUserStateNotify.setType(notify.getType());
		}
		troopsUserStateNotify.setState(notify.getState());
		troopsUserStateNotify.setMsg(notify.getMsg());
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onUserStateNotify, new Gson().toJson(troopsUserStateNotify));
	}

	@Override
	public void onUserVideoManageNotify(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUsersVideoStateNotify(List<TroopsUser> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVideoStateNotify(VideoStateNotify arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVoiceMessageNotify(VoiceMessageNotify notify) {
		// TODO Auto-generated method stub
		if(notify.getText()!=null && notify.getText().length()>0){
			ChatRichMessageNotify chatRichMessageNotify = new ChatRichMessageNotify();
			if(notify.getYunvaId()!=null){
				chatRichMessageNotify.setYunvaId((int)(long)notify.getYunvaId());
			}
			chatRichMessageNotify.setChatRoomId(notify.getTroopsId());
			chatRichMessageNotify.setText(notify.getText());
			chatRichMessageNotify.setVoiceUrl(notify.getVoiceUrl());
			chatRichMessageNotify.setVoiceTime(notify.getVoiceTime());
			chatRichMessageNotify.setTime(notify.getTime());
			chatRichMessageNotify.setExpand(notify.getExpand());
			UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onRichMessageNotify, new Gson().toJson(chatRichMessageNotify));
		}else{
			ChatVoiceMessageNotify chatVoiceMessageNotify = new ChatVoiceMessageNotify();
			if(notify.getYunvaId()!=null){
				chatVoiceMessageNotify.setYunvaId((int)(long)notify.getYunvaId());
			}
			chatVoiceMessageNotify.setChatRoomId(notify.getTroopsId());
			chatVoiceMessageNotify.setVoiceUrl(notify.getVoiceUrl());
			chatVoiceMessageNotify.setVoiceTime(notify.getVoiceTime());
			chatVoiceMessageNotify.setTime(notify.getTime());
			chatVoiceMessageNotify.setExpand(notify.getExpand());
			UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onVoiceMessageNotify, new Gson().toJson(chatVoiceMessageNotify));
		}
	}

	@Override
	public void onVoicePathOrTextResp(int result, String msg, int errorStatus, int errorSubStatus, boolean isVoiceAndText, String text, String voicePath, Long voiceDuration) {
		if(isVoiceAndText){
			RecordAndRecognizeFinishResp resp = new RecordAndRecognizeFinishResp();
			resp.setResult(result);
			resp.setMsg(msg);
			resp.setErrorStatus(errorStatus);
			resp.setErrorSubStatus(errorSubStatus);
			resp.setText(text);
			resp.setVoicePath(voicePath);
			if(voiceDuration!=null){
				resp.setVoiceDuration((int)(long)voiceDuration);
			}
			EventBus.getDefault().post(resp);
		}else{
			RecognizeFinishResp resp = new RecognizeFinishResp();
			resp.setResult(result);
			resp.setMsg(msg);
			resp.setErrorStatus(errorStatus);
			resp.setErrorSubStatus(errorSubStatus);
			resp.setText(text);
			EventBus.getDefault().post(resp);
		}
	}

	@Override
	public void onSendRichMessageResp(SendRichMessageResp resp) {
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onSendRichMessageResp, new Gson().toJson(resp));
	}

	@Override
	public void AudioRecordUnavailableNotify(
			com.yunva.video.sdk.interfaces.logic.model.AudioRecordUnavailableNotify arg0) {
		// TODO Auto-generated method stub
		
	}

	public void httpVoiceRecognizeResp(SpeechDiscernResp arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAuthResp(int result, String msg) {
		AuthResp resp = new AuthResp();
		resp.setResult(result);
		resp.setMsg(msg);
		UnityPlayer.UnitySendMessage(gameObjectName, RESP_METHOD_ChatManage_onAuthResp, new Gson().toJson(resp));
	}

	/*  呀呀实时语音SDK_2.0.6_201601071656 新增加的接口     */
	@Override
	public void onBeginAutoReLoginWithTryTimes(int arg0) {
		// TODO Auto-generated method stub
//		RESP_METHOD_ChatManage_onBeginAutoReLoginWithTryTimes
	}

	@Override
	public void onTroopsIsDisconnectNotify() {
		// TODO Auto-generated method stub
//		RESP_METHOD_ChatManage_onTroopsIsDisconnectNotify
	}

}
