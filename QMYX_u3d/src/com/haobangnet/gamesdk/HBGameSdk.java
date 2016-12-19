package com.haobangnet.gamesdk;

import android.app.Activity;
import android.util.Log;
//import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.haobangnet.gamesdk.QMYXCallbackListener;
import com.haobangnet.gamesdk.QMYXCallbackListenerNullException;
import com.haobangnet.gamesdk.QMYXGameSDKStatusCode;
import com.haobangnet.gamesdk.QMYXLogLevel;
import com.haobangnet.gamesdk.QMYXPaymentInfo;
import com.haobangnet.gamesdk.QMYXSDK;
import com.haobangnet.gamesdk.bean.OrderInfo;

/**
 *版本已在2016/6/8号跟新
 */
public class HBGameSdk {
	private final static String TAG = "HBGameSdk";
	public final static String CALLBACK_GAMEOBJECT_NAME = "(HBsdk_callback)"; // unity中接收回调通知的GameObject的名称
	public final static String CALLBACK_INIT = "OnInitSuc"; // SDK初始化成功的回调方法名称和Unity中一致
	public final static String CALLBACK_LOGIN = "OnLoginSuc"; // SDK登录成功的回调方法名称和Unity中一致
	public final static String CALLBACK_LOGOUT = "OnLogout"; // SDK登出的回调方法名称和Unity中一致
	public final static String CALLBACK_PAY = "OnPaySuc"; // SDK支付成功回调方法名称和Unity中一致
	public final static String CALLBACK_EXIT = "OnExitSuc"; // SDK退出回调方法名称和Unity中一致
	public static boolean isDebugMode;// 为true连接测试环境

	// 向Unity中发送消息
	public static void sendCallback(String name, String jsonParams) {
		if (jsonParams == null) {
			jsonParams = "";
		}
		UnityPlayer
				.UnitySendMessage(CALLBACK_GAMEOBJECT_NAME, name, jsonParams);
	}

	private static void log(String str) {
		if (isDebugMode)
			Log.i(TAG, str);
	}

	// HBSDK 初始化，isTestMode = true为测试模式
	public static void init(final boolean isTestMode) {
		log("init calling...");
		isDebugMode = isTestMode;
		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// ////////////////////////
					try {
						// 第2个参数 是否打开调试模式。在为true的时候会连接测试环境，当为false的时候则会连接到正式环境
						QMYXSDK.initSDK(mActivity, isTestMode,
								QMYXLogLevel.DEBUG,
								new QMYXCallbackListener<String>() {
									@Override
									public void callback(int statuscode,
											String data) {
										log("java init callback statuscode:" + statuscode + ", data:" + data);
										switch (statuscode) {
										case QMYXGameSDKStatusCode.SUCCESS:
											// 初始化成功，可以执行后续的登录充值操作
											sendCallback(CALLBACK_INIT, "{\"statuscode\":" + statuscode + "}");
											// Toast.makeText( mActivity, "init success v1 "  + statuscode, Toast.LENGTH_SHORT).show();
											break;
										case QMYXGameSDKStatusCode.INIT_FAIL:
											// 初始化失败，不能进行后续操作
											// Toast.makeText(  mActivity, "init INIT_FAIL" + statuscode, Toast.LENGTH_SHORT).show();
											break;
										default:
											break;
										}
									}
								});
					} catch (QMYXCallbackListenerNullException e) {
						e.printStackTrace();
						Log.e(TAG, e.getMessage(), e);
						log(e.getMessage());
						// TODO 捕获listener为null的异常
					}
					// /////////////////////
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			Log.e(TAG, e.getMessage(), e);
		}
	}

	// HBSDK 获得session_id
	public static String getSessionId() {
		String sessionid = QMYXSDK.getSessionId();
		log("getSessionId..." + sessionid);
		return sessionid;
	}

	// HBSDK 获得ver 版本
	public static String getVer() {
		String ver = "1.1";
		log("java   getVer..." + ver);
		return ver;
	}

	// HBSDK 登录
	public static void login() {
		log("java login calling...");
		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// ////////////////////////
					try {
						QMYXSDK.login(mActivity,
								new QMYXCallbackListener<String>() {
									@Override
									public void callback(int code, String msg) {
										log("java login callback statuscode:" + code + ", msg:" + msg);
										switch (code) {
										case QMYXGameSDKStatusCode.SUCCESS:
											// 登录成功，可以执行后续操作
											String sessionid = QMYXSDK.getSessionId();
											sendCallback(CALLBACK_LOGIN, "{\"statuscode\":"
															+ code + ",\"sessionid\":\"" + sessionid + "\"}");
											log("java login SUCCESS... sessionid: " + sessionid);
											break;
										case QMYXGameSDKStatusCode.LOGIN_EXIT:
											log("java login exit...");
											// 登录界面关闭，游戏需判断此时是否已登录成功进行相应操作
											// Toast.makeText(mActivity,"玩家退出了登录界面",Toast.LENGTH_SHORT).show();
											break;
										case QMYXGameSDKStatusCode.NO_INIT:
											// 没有初始化就进行登录调用，需要游戏调用SDK初始化方法
											log("java login NO_INIT...");
											// Toast.makeText(mActivity,"没有初始化",Toast.LENGTH_SHORT).show();
											break;
											
										case QMYXGameSDKStatusCode.FAIL:// API调用失败
											log("java login FAIL..."+ msg);
											// Toast.makeText(mActivity, "FAIL",
											// Toast.LENGTH_SHORT).show();
											String sessionid2 = QMYXSDK.getSessionId();
											sendCallback(CALLBACK_LOGIN,"{\"statuscode\":"
														+ code+ ",\"sessionid\":\""+ sessionid2+ "\"}");
											break;
										default:
											break;
										}
									}
								});
					} catch (QMYXCallbackListenerNullException e) {
						e.printStackTrace();
						Log.e(TAG, e.getMessage(), e);
						log(e.getMessage());
						System.out.print(e.getStackTrace().toString());
						// 异常处理
					}
					// /////////////////////
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
			log(e.getMessage());
			System.out.print(e.getStackTrace().toString());
		}
	}

	// HBSDK 注销，注意：注销成功需要回调给游戏，通知游戏退出登录。
	public static void logout() {
		log("java logout calling...");
		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// ////////////////////////
					try {
						QMYXSDK.logout(new QMYXCallbackListener<String>() {
							@Override
							public void callback(int statuscode, String data) {
								log("java logout callback statuscode:"
										+ statuscode + ", data:" + data);
								switch (statuscode) {
								case QMYXGameSDKStatusCode.NO_INIT:
									// QMYXSDK未初始化
//									Toast.makeText(mActivity, "没有初始化",Toast.LENGTH_SHORT).show();
									break;
								case QMYXGameSDKStatusCode.NO_LOGIN:
									// 未登录
//									Toast.makeText(mActivity, "没有登录",Toast.LENGTH_SHORT).show();
									break;
								case QMYXGameSDKStatusCode.SUCCESS:
									// 退出账号成功
//									Toast.makeText(mActivity, "注销成功",Toast.LENGTH_SHORT).show();
									sendCallback(CALLBACK_LOGOUT,"{\"statuscode\":" + statuscode
												+ ",\"sessionid\":\""+ getSessionId() + "\"}");
									break;
								case QMYXGameSDKStatusCode.FAIL:
									// 退出账号失败
//									Toast.makeText(mActivity, "注销失败",Toast.LENGTH_SHORT).show();
									break;
								default:
									break;
								}
							}
						});
					} catch (QMYXCallbackListenerNullException e) {
						e.printStackTrace();
						Log.e(TAG, e.getMessage(), e);
						log(e.getMessage());
						System.out.print(e.getStackTrace().toString());
						// 异常处理
					}
					// /////////////////////
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
			System.out.print(e.getStackTrace().toString());
		}
	}

	// HBSDK支付
//	public static void pay(final String data) {
	public static void pay(final float money, final String desc, final
		 int serverId, final String callbackInfo) {
//		log("java pay calling..." + data);
		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// ////////////////////////
					try {
//						final float money;
//						final String desc;
//						final int serverId;
//						final String callbackInfo;
//						JSONObject json = new JSONObject(data);
//						money = json.getInt("money");
//						desc = json.getString("desc");
//						serverId = json.getInt("serverId");
//						callbackInfo = json.getString("callbackInfo");

						// 充值参数总共4个，金额，区服id，充值描述，充值自定义参数callbackInfo
						QMYXPaymentInfo paymentInfo = new QMYXPaymentInfo();// 创建Payment对象，用于传递充值信息
						paymentInfo.setAmount(money);// 单位：元 设置允许充值的金额，此为可选参数，默认为0；
						paymentInfo.setServerId(serverId);// 设置区服的ID
						paymentInfo.setPayDesc(desc);// 设置充值描述
						paymentInfo.setCustomInfo(callbackInfo);// 设置用户信息"custInfo=546876545648984465455645875645"
						log("java pay calling money:" + paymentInfo.getAmount());

						QMYXSDK.pay(mActivity, paymentInfo,
								new QMYXCallbackListener<OrderInfo>() {
									@Override
									public void callback(int statuscode,
											OrderInfo data) {
										log("java pay callback statuscode:"+ statuscode + ", orderId:"
												+ data.getOrderId());
										switch (statuscode) {
										case QMYXGameSDKStatusCode.NO_INIT:
//											Toast.makeText(mActivity, "没有初始化",Toast.LENGTH_SHORT).show();
											break;
										case QMYXGameSDKStatusCode.SUCCESS:
											log("pay success orderId=" + data.getOrderId()+ " amount="+ data.getOrderAmount());// sdk服务器返回的订单号
//											Toast.makeText(mActivity, "支付成功",Toast.LENGTH_SHORT).show();
											sendCallback(CALLBACK_PAY,"{\"statuscode\":"+ statuscode+ ",\"orderId\":\""
													+ data.getOrderId()+ ",\"orderAmount\":"+ data.getOrderAmount()+ "}");
											break;
										case QMYXGameSDKStatusCode.FAIL:
//											Toast.makeText(mActivity, "支付失败",Toast.LENGTH_SHORT).show();
											sendCallback(CALLBACK_PAY,"{\"statuscode\":"+ statuscode + "}");
											break;
										case QMYXGameSDKStatusCode.NO_LOGIN:
//											Toast.makeText(mActivity, "没有登录",Toast.LENGTH_SHORT).show();
											sendCallback(CALLBACK_PAY,"{\"statuscode\":"+ statuscode+ "\"}");
											break;
										case QMYXGameSDKStatusCode.PAY_USER_EXIT:
//											Toast.makeText(mActivity,"用户退出支付界面",Toast.LENGTH_SHORT).show();
											sendCallback(CALLBACK_PAY,"{\"statuscode\":"+ statuscode+ "\"}");
											break;
										}
									}
								});
					} catch (QMYXCallbackListenerNullException e) {
						e.printStackTrace();
						Log.e(TAG, e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
					// /////////////////////
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
			log(e.getMessage());
		}
	}

	/**
	 * HBSDK 退出SDK 该接口需要游戏在退出前调用，游戏等待该接口的回调通知后，再执行游戏的退出。
	 * exitSDK方法进行清理工作。如果游戏直接退出，而不调用该方法，可能会出现未知错误，导致程序崩溃。
	 */
	public static void exitSDK() {
		log("java exitSDK calling...");
		// ///////////////////
		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
//						final Activity mActivity = (Activity) UnityPlayer.currentActivity;
						QMYXSDK.exitSDK(mActivity, new QMYXCallbackListener<String>() {
							@Override
							public void callback(int statuscode, String data) {
								log("java exitSDK callback statuscode:"+ statuscode + ", data:" + data);
								switch (statuscode) {
								case QMYXGameSDKStatusCode.NO_INIT:
									// QMYXSDK未初始化
//									Toast.makeText(mActivity, "没有初始化",Toast.LENGTH_SHORT).show();
									break;
								case QMYXGameSDKStatusCode.NO_LOGIN:
									// 未登录
//									Toast.makeText(mActivity, "没有登录",Toast.LENGTH_SHORT).show();
									break;
								case QMYXGameSDKStatusCode.SUCCESS:
									// 退出账号成功
//									Toast.makeText(mActivity, "退出SDK成功",Toast.LENGTH_SHORT).show();
									sendCallback(CALLBACK_EXIT,"{\"statuscode\":" + statuscode+ "}");
									break;
								case QMYXGameSDKStatusCode.FAIL:
									// 退出账号失败
//									Toast.makeText(mActivity, "退出SDK失败",Toast.LENGTH_SHORT).show();
									sendCallback(CALLBACK_EXIT,"{\"statuscode\":" + statuscode+ "}");
									break;
								default:
									break;
								}
							}

						});
					} catch (QMYXCallbackListenerNullException e) {
						// 异常处理
						e.printStackTrace();
						Log.e(TAG, e.getMessage(), e);
						log(e.getMessage());
						System.out.print(e.getStackTrace().toString());
					}
					// ///////////////////
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
			log(e.getMessage());
			System.out.print(e.getStackTrace().toString());
		}
	}

}
