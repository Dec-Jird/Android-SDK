package com.hg6kwan.sdk;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
//import android.widget.Toast;
import com.hg6kwan.sdk.HG6kwanCode;
import com.hg6kwan.sdk.HG6kwanListener;
import com.hg6kwan.sdk.HG6kwanSDK;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

/**
 * com.tnyoo.android.dotaxiyou.hg6kwan
 * 注意：
 * 本工程的.R文件被自己手动编辑过，加入6kwanSDK中需要用到的资源id，由于6kwanSDK也需要用到该文件，
 * 故该文件所在包名必须和6kwanSDK的主包名一致com.hg6kwan.sdk，因此该工程的报名必须为com.hg6kwan.sdk，否则报错.
 * 为了打包时能打入编辑过的.R文件，主代码包名和Manifest.xml包名不能一样，
 * 因而将Manifest.xml包名改为：com.tnyoo.android.dotaxiyou（导入Unity中也用该包名）
 * 
 * 注意：新版本1.0.2不需要做上面处理。
 * 
 * UnityPlayerActivity  Activity
 * @author Administrator
 */
public class MainActivity extends UnityPlayerActivity {

	private final static String TAG = "Pay.HG6Kwan";
	private final static String CALLBACK_LOGIN_SUC = "OnLoginSuc";
	private final static String CALLBACK_PAY_SUC = "OnPaySuc";
	private final static String CALLBACK_LOGOUT_SUC = "OnLogoutSuc";
	private final static String CALLBACK_SWITCHACCOUNT_SUC = "OnSwitchAccountSuc";
	public static Context mContext;
	private final static HG6kwanSDK SDK = HG6kwanSDK.getInstance();;
	private boolean isdebug = true;
	private static String CALLBACK_GAMEOBJECT_NAME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main_activity);
//		mContext = this;
		mContext = UnityPlayer.currentActivity;
	}

	// 向Unity中发送消息
	public static void sendCallback(String name, String jsonParams) {
		if (jsonParams == null) {
			jsonParams = "";
		}
		UnityPlayer
				.UnitySendMessage(CALLBACK_GAMEOBJECT_NAME, name, jsonParams);
	}
	
	protected void log(String str) {
		if (isdebug)
			Log.i(TAG, str);
	}

	public void init6Kwan(String appId, String appKey, String gameObjName) {
		CALLBACK_GAMEOBJECT_NAME = gameObjName;
		log("init6Kwan gameObj:" + CALLBACK_GAMEOBJECT_NAME);
		
		HG6kwanSDK.setContext(mContext);
		HG6kwanSDK.setAppId(appId);// 游戏id //1000000
		HG6kwanSDK.setAppKey(appKey);// 8a706e870f7afd73411b23e626440365
		HG6kwanSDK.setChannel(SDK.wdGetChannel(mContext));

		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				SDK.wdInital();
			}
		});
	}

	public void login() {
		log("sdk login");
		if (SDK == null) {
//			Toast.makeText(mContext, "sdk未初始化", Toast.LENGTH_SHORT).show();
			log("sdk未初始化");
			return;
		}

		Handler handler1 = new Handler(Looper.getMainLooper());
		handler1.post(new Runnable() {
			@Override
			public void run() {
				/**
				 * 设置登录回调
				 */
				SDK.wdLogin(mContext,
						new HG6kwanListener.OnLoginProcessListener() {
							@Override
							public void onFinishLogin(int code) {
								String text = "";
								switch (code) {
								case HG6kwanCode.COM_PLATFORM_SUCCESS:
									// 登录成功
									text = "登录成功";
									String accountId = SDK.wdGetAccount(mContext);
									sendCallback(CALLBACK_LOGIN_SUC, "{\"statuscode\":" + code  
											+ ",\"accountid\":\"" + accountId
											+"\",\"sessionid\":\"" + SDK.wdGetSessionId(mContext) + "\"}");
									break;
								case HG6kwanCode.COM_PLATFORM_ERROR_LOGIN_FAIL:
									text = "登录失败";
									// 登录失败
									break;
								case HG6kwanCode.COM_PLATFORM_ERROR_CANCELL:
									text = "取消登录";
									// 取消登录
									break;
								default:
									text = "登录失败";
									// 登录失败
									break;
								}
								log(text + ", sessionId: " + SDK.wdGetSessionId(mContext));
//								Toast.makeText(mContext, text,
//										Toast.LENGTH_SHORT).show();
							}

						});
			}
		});
		//
		Handler handler2 = new Handler(Looper.getMainLooper());
		handler2.post(new Runnable() {
			@Override
			public void run() {
				/**
				 * 
				 * 设置注册回调
				 * 
				 */
				SDK.wdSetRegisterListener(new HG6kwanListener.OnCallbackListener() {
					@Override
					public void onCallback(int code) {
						String text = "";
						switch (code) {
						case HG6kwanCode.COM_PLATFORM_SUCCESS:
							text = "注册成功";
							// 注册成功
							break;
						case HG6kwanCode.COM_PLATFORM_ERROR_CANCELL:
							text = "取消注册";
							// 取消注册
						case HG6kwanCode.REGISTER_FAIL:
							text = "注册失败";
							// 注册失败
						default:
							text = "注册失败";
							// 注册失败
							break;
						}
						log(text);
//						//
//						Toast.makeText(mContext, text, Toast.LENGTH_SHORT)
//								.show();
					}
				});
			}
		});
	}

	/**
	 * 支付
	 * @param coinCount 订单价格
	 * @param productDesc 订单描述
	 * @param serverID 服务器ID
	 * @param userID 玩家ID
	 * @param callbackInfo 
	 */
	public void pay(final int coinCount, final String productDesc,
			final String serverID, final String userID,
			final String callbackInfo) {
		log("sdk pay");
		if (SDK == null) {
//			Toast.makeText(mContext, "sdk未初始化", Toast.LENGTH_SHORT).show();
			log("sdk未初始化");
			return;
		}

		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				/**
				 * 进入代币充值界面
				 */
				SDK.wdGetOrderID(mContext,
						new HG6kwanListener.OnCallbackOrderIDListener() {

							// 返回动态订单号OrderID，供支付使用.
							@Override
							public void onGetOderID(String OrderID) {
								//
								if (TextUtils.isEmpty(OrderID)) {
//									Toast.makeText(mContext, "获取订单号失败!!",
//											Toast.LENGTH_LONG).show();
									return;
								}
								// coinCount desc serverID
								int payCode = SDK
										.wdPayForCoin(OrderID, coinCount, productDesc, serverID,
												mContext, new HG6kwanListener.OnPayProcessListener() {
													@Override
													public void onFinishPay(
															int code) {
														String text = "情况不明！";
														switch (code) {
														case HG6kwanCode.COM_PLATFORM_SUCCESS:
															// 充值成功
															text = "本次支付成功!";
															sendCallback(CALLBACK_PAY_SUC, "{\"statuscode\":"+ code + 
																	",\"msg\":\"" + text + "\"}");
															break;
														case HG6kwanCode.COM_PLATFORM_ERROR_PAY_CANCEL:
															// 取消充值
															text = "本次支付被取消!";
															break;
														case HG6kwanCode.COM_PLATFORM_ERROR_PAY_FAIL:
															// 充值失败
															text = "本次支付失败!";
															break;
														case HG6kwanCode.COM_PLATFORM_PAY_ORDER_SUBMITTED:
															// 订单已提交
															text = "本次支付订单已提交，请查收到账情况!";
														default:
															// 充值失败
															break;
														}
														log(text);
														//
//														Toast.makeText(mContext,text,Toast.LENGTH_SHORT).show();
													}
												});
								//
								if (payCode == HG6kwanCode.COM_PLATFORM_SUCCESS) {
									log("支付请求成功====>pay request code:"
											+ payCode);
//									Toast.makeText(mContext, "支付请求成功",
//											Toast.LENGTH_LONG).show();
								} else {
									log("支付请求失败====>pay request code:"
											+ payCode);
//									Toast.makeText(mContext, "支付请求失败",
//											Toast.LENGTH_LONG).show();
								}
							}
						}, serverID, userID, callbackInfo);// 1001区服ServerId.

			}
		});
	}
	
	public void switchAccount() {
		log("sdk switchAccount");
		if (SDK == null) {
//			Toast.makeText(mContext, "sdk未初始化", Toast.LENGTH_SHORT).show();
			log("sdk未初始化");
			return;
		}

		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				/**
				 * 进入注销
				 */
				SDK.wdSwitchAccount(mContext,
						new HG6kwanListener.OnLoginProcessListener() {
							@Override
							public void onFinishLogin(int code) {
								String text = "";
								switch (code) {
								case HG6kwanCode.COM_PLATFORM_SUCCESS:
									// 登录成功
									text = "切换帐号成功";
									sendCallback(CALLBACK_SWITCHACCOUNT_SUC, "{\"statuscode\":"+ code + 
											",\"msg\":\"" + text + "\"}");
									break;
								case HG6kwanCode.COM_PLATFORM_ERROR_LOGIN_FAIL:
									text = "登录失败";
									// 登录失败
									break;
								case HG6kwanCode.COM_PLATFORM_ERROR_CANCELL:
									text = "取消登录";
									// 取消登录
									break;
								default:
									text = "登录失败";
									// 登录失败
									break;
								}
								log(text);
//								Toast.makeText(mContext, text,
//										Toast.LENGTH_SHORT).show();
							}

						});
			}
		});
	}

	public void logout() {
		log("sdk logout");
		if (SDK == null) {
//			Toast.makeText(mContext, "sdk未初始化", Toast.LENGTH_SHORT).show();
			log("sdk未初始化");
			return;
		}

		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				/**
				 * 进入注销
				 */
				SDK.wdLogout(mContext,
						new HG6kwanListener.OnCallbackListener() {
							@Override
							public void onCallback(int code) {
								String text = "";
								if (code == HG6kwanCode.COM_PLATFORM_SUCCESS) {
									text = "注销成功";
									sendCallback(CALLBACK_LOGOUT_SUC, "{\"statuscode\":"+ code + 
											",\"msg\":\"" + text + "\"}");
									// 注销成功
								} else {
									text = "注销失败";
									// 注销失败
								}
								log(text);
//								Toast.makeText(mContext, text,
//										Toast.LENGTH_SHORT).show();
							}
						});

			}
		});
	}

//	public void onClick(View view) {
//		switch (view.getId()) {
//		case R.id.init:
//			init6Kwan("1000001", "abe2f70a8107dc8fe103c04e3934f379", "gameObj");
//			break;
//		case R.id.login:
//			login();
//			break;
//		case R.id.pay:
//			// int coinCount, String productDesc, String serverID, String
//			// playerName,String callbackInfo
//			pay(50, "10元宝", "10086", "小黑", "callbackInfo=tnyoocallbackInfo");
//			break;
//		case R.id.switchAccount:
//			switchAccount();
//			break;
//		case R.id.logout:
//			logout();
//			break;
//		}
//	}

}