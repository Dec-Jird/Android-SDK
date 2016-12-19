package com.tnyoo.baidugame_u3d;

import java.util.UUID;

import com.baidu.gamesdk.BDGameSDK;
import com.baidu.gamesdk.BDGameSDKSetting;
import com.baidu.gamesdk.BDGameSDKSetting.Domain;
import com.baidu.gamesdk.IResponse;
import com.baidu.gamesdk.ResultCode;
import com.baidu.platformsdk.PayOrderInfo;
import com.tnyoo.baidugame_u3d.utils.Utils;
import de.greenrobot.event.EventBus;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	public static final String TAG = "PAY";
	private String gameObj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setSuspendWindowChangeAccountListener();// 设置切换账号事件监听（个人中心界面切换账号）
		setSessionInvalidListener();// 设置会话失效监听
		
		if (EventBus.getDefault().isRegistered(this))
			EventBus.getDefault().unregister(this);
		EventBus.getDefault().register(this, "onBDEventMainThread");
	}

	public void onBDEventMainThread(BDEvent event) {
		String type = event.getType();
		
		if (type == "init") {
			initBDSDK(event.getAppId(), event.getAppKey());
		} else if (type == "login") {
			loginBDSDK();
		} else if (type == "pay") {
			payBDSDK(event.getCpOrderId(), event.getGoodsName(),
					event.getTotalAmount(), 0, event.getCallbackInfo());
		} else if (type == "logout") {
			logoutBDSDK();
		} else if (type == "show") {
			showFloatView();
		} else if (type == "hide") {
			closeFloatView();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void init(int appId, String appKey, String gameObjName){
		gameObj = gameObjName;
		BDEvent event = new BDEvent();
		event.setType("init");
		event.setAppId(appId);
		event.setAppKey(appKey);
		
		EventBus.getDefault().post(event);
	}
	
	private void initBDSDK(int appId, String appKey) {// 初始化游戏SDK
		
		BDGameSDKSetting mBDGameSDKSetting = new BDGameSDKSetting();
		mBDGameSDKSetting.setAppID(appId);// APPID设置 3067515
		mBDGameSDKSetting.setAppKey(appKey);// APPKEY设置 f3Os4GAOqxgm79GqbnkT9L8T
		mBDGameSDKSetting.setDomain(Domain.DEBUG);// 设置为调试模式，发布时必须改为Domin.RELEASE模式
		mBDGameSDKSetting.setOrientation(Utils.getOrientation(this));
		
		BDGameSDK.init(this, mBDGameSDKSetting, new IResponse<Void>() {

			@Override
			public void onResponse(int resultCode, String resultDesc,
					Void extraData) {
				switch (resultCode) {
				case ResultCode.INIT_SUCCESS:
					// 初始化成功
					Toast.makeText(getBaseContext(), "初始化成功", Toast.LENGTH_LONG)
							.show();
					Log.i(TAG, "初始化成功");
					/** 确保初始化不出错 **/
//					UnityPlayer.UnitySendMessage(U3DApi.this.gameObj, "DebugResult", resultDesc);
					break;

				case ResultCode.INIT_FAIL:
				default:
					// 初始化失败
					Toast.makeText(getBaseContext(), "启动失败", Toast.LENGTH_LONG)
							.show();
					finish();
				}

			}

		});
	}

	public void login(){
		BDEvent event = new BDEvent();
		event.setType("login");
		
		EventBus.getDefault().post(event);
	}
	
	// 登录
	private void loginBDSDK() {
		BDGameSDK.login(new IResponse<Void>() {

			@Override
			public void onResponse(int resultCode, String resultDesc,
					Void extraData) {
				String hint = "";
				String accessToken = "";
				String uid = "";

				switch (resultCode) {
				case ResultCode.LOGIN_SUCCESS:
					showFloatView();// 显示悬浮窗 报错：BDPlatformUser 为空
					// 登录成功，此时玩家登录的账号有可能跟失效前登的账号不一致，开发者此时可通过BDGameSDK.getLoginUid获取当前登录的用户ID跟会话失效前的ID比对是否一致，
					// 如果一致则玩家继续游戏，如果不一致则开发者需要更改玩家在游戏中的游戏数据。

					// 登录用户accessToken,可用于百度移动游戏SDK服务器支付结果通知CP应用服务器的数据校验。
					accessToken = BDGameSDK.getLoginAccessToken();
					uid = BDGameSDK.getLoginUid();// 登录用户UID

					Log.i(TAG, "login success. uid: " + uid + ", accessToken: " + accessToken);
					/** 需要将accessToken经由客户端发送到服务器，进行登录验证 **/
//					 UnityPlayer.UnitySendMessage(U3DApi.this.gameObj, "LoginResultSuccess", access);
					hint = "登录成功 uid: " + uid;
					break;
				case ResultCode.LOGIN_CANCEL:
					hint = "取消登录";
					break;
				case ResultCode.LOGIN_FAIL:
				default:
					hint = "登录失败";
				}
				Log.i(TAG, "login hint:" + hint);
			}
		});
	}

	public void showFloat(){
		BDEvent event = new BDEvent();
		event.setType("show");
		
		EventBus.getDefault().post(event);
	}
	
	// 显示悬浮窗
	private void showFloatView() {
		BDGameSDK.showFloatView(this);// 显示悬浮窗
	}

	public void hideFloat(){
		BDEvent event = new BDEvent();
		event.setType("hide");
		
		EventBus.getDefault().post(event);
	}
	
	// 隐藏悬浮窗
	private void closeFloatView() {
		BDGameSDK.closeFloatView(this);// 关闭悬浮窗
	}

	private PayOrderInfo buildOrderInfo(String cpOrderId, String goodsName,
			String totalAmount, int ratio, String extInfo) {
		if (TextUtils.isEmpty(totalAmount)) {
			totalAmount = "0";
		}

		PayOrderInfo payOrderInfo = new PayOrderInfo();
		payOrderInfo.setCooperatorOrderSerial(cpOrderId);
		payOrderInfo.setProductName(goodsName);
		long p = Long.parseLong(totalAmount);
		payOrderInfo.setTotalPriceCent(p);// 以分为单位
		payOrderInfo.setRatio(ratio);
		payOrderInfo.setExtInfo(extInfo);// 该字段将会在支付成功后原样返回给CP(不超过500个字符)

		return payOrderInfo;
	}

	public void pay(String cpOrderId, String goodsName,
			String totalAmount, String callbackInfo){
		BDEvent event = new BDEvent();
		event.setType("pay");
		event.setCpOrderId(cpOrderId);
		event.setGoodsName(goodsName);
		event.setTotalAmount(totalAmount);
		event.setCallbackInfo(callbackInfo);
		
		EventBus.getDefault().post(event);
	}
	
	/**
	 * 支付
	 * 
	 * @param cpOrderId
	 *            CP订单号
	 * @param goodsName
	 *            商品名
	 * @param totalAmount
	 *            支付总金额 （以分为单位）当传入支付金额等于0时，即为非定额支付。
	 * @param ratio
	 *            兑换比例(每100分=1元可以兑换的商品数，例如ratio=10则1元可兑换10个商品）
	 *            (非定额支付时必须传入该参数，否则订单无效。)
	 * @param callbackInfo
	 *            扩展字段，该信息在支付成功后原样返回给CP
	 * @return 订单信息
	 */
	private void payBDSDK(String cpOrderId, String goodsName,
			String totalAmount, int ratio, String callbackInfo) {
		PayOrderInfo payOrderInfo = buildOrderInfo(cpOrderId, goodsName,
				totalAmount, ratio, callbackInfo);
		if (payOrderInfo == null) {
			return;
		}

		BDGameSDK.pay(payOrderInfo, null, new IResponse<PayOrderInfo>() {

			@Override
			public void onResponse(int resultCode, String resultDesc,
					PayOrderInfo extraData) {
				String resultStr = "";
				switch (resultCode) {
				case ResultCode.PAY_SUCCESS:// 支付成功
					resultStr = "支付成功:" + resultDesc;
					break;
				case ResultCode.PAY_CANCEL:// 订单支付取消
					resultStr = "取消支付";
					break;
				case ResultCode.PAY_FAIL:// 订单支付失败
					resultStr = "支付失败：" + resultDesc;
					break;
				case ResultCode.PAY_SUBMIT_ORDER:// 订单已经提交，支付结果未知（比如：已经请求了，但是查询超时）
					resultStr = "订单已经提交，支付结果未知";
					break;
				}
				
				/** 需要将支付状态，返回游戏客户端 **/
//				UnityPlayer.UnitySendMessage(U3DApi.this.gameObj, "DebugResult", resultStr);
				Toast.makeText(getApplicationContext(), resultStr,
						Toast.LENGTH_LONG).show();
			}

		});
	}

	public void logout(){
		BDEvent event = new BDEvent();
		event.setType("logout");
		
		EventBus.getDefault().post(event);
	}
	
	// 登出
	private void logoutBDSDK() {
		BDGameSDK.logout();
	}

	// 设置切换账号事件监听（个人中心界面切换账号）
	private void setSuspendWindowChangeAccountListener() {
		BDGameSDK.setSuspendWindowChangeAccountListener(new IResponse<Void>() {

			@Override
			public void onResponse(int resultCode, String resultDesc,
					Void extraData) {
				String access = "";
				Log.i(TAG, "切换帐号 ChangeAccountListener");
				switch (resultCode) {
				case ResultCode.LOGIN_SUCCESS:
					
			          access = BDGameSDK.getLoginAccessToken();
			          /** 切换帐号登录后回调，游戏将回调access发送给服务器进行登录验证 **/
//			          UnityPlayer.UnitySendMessage(gameObj, "ChangeAccountCallBack", access);
			          
					// TODO 登录成功，不管之前是什么登录状态，游戏内部都要切换成新的用户
					Toast.makeText(getApplicationContext(), "登录成功",
							Toast.LENGTH_LONG).show();
					break;
				case ResultCode.LOGIN_FAIL:
					// 登录失败，游戏内部之前如果是已经登录的，要清除自己记录的登录状态，设置成未登录。如果之前未登录，不用处理。
					Toast.makeText(getApplicationContext(), "登录失败",
							Toast.LENGTH_LONG).show();
					break;
				case ResultCode.LOGIN_CANCEL:
					// TODO 取消，操作前后的登录状态没变化
					break;

				}
			}

		});
	}

	/**
	 * 设置会话失效监听接口，用来在登录会话失效时捕获到失效通知， 以便保存玩家的游戏数据或者重新调用登录接口走登录逻辑。
	 */
	private void setSessionInvalidListener() {
		BDGameSDK.setSessionInvalidListener(new IResponse<Void>() {

			@Override
			public void onResponse(int resultCode, String resultDesc,
					Void extraData) {
				if (resultCode == ResultCode.SESSION_INVALID) {
					// 会话失效，开发者需要重新登录或者重启游戏
					loginBDSDK();
				}

			}

		});
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		BDGameSDK.closeFloatView(this);// 关闭悬浮窗
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BDGameSDK.destroy();
	}

	public void onClick(View view) {
		int baidu_appId = 3067515;// 6925292;
		String baidu_appKey = "f3Os4GAOqxgm79GqbnkT9L8T";// "pNGGoF3PcPO7cukWEFs07vz8";

		switch (view.getId()) {
		case R.id.Init:
			init(baidu_appId, baidu_appKey, "gameObj");
			break;
		case R.id.Login:
			loginBDSDK();
			break;
		case R.id.Pay:
			String orderId = UUID.randomUUID().toString();
			pay(orderId, "元宝", "100", "");
			break;
		case R.id.Logout:
			logout();
			break;
		case R.id.showFloatView:
			showFloat();
			break;
		case R.id.hideFloatView:
			hideFloat();
			break;
		}
	}

}
