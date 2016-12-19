package com.tnyoo.android.dotaxiyou.yunxiaotang;

import com.tnyoo.android.dotaxiyou.yunxiaotang.event.SDKEvent;
import com.tnyoo.android.dotaxiyou.yunxiaotang.event.SDKPayEvent;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;
import com.yyjia.sdk.center.GMcenter;
import com.yyjia.sdk.data.Information;
import com.yyjia.sdk.listener.LoginListener;
import com.yyjia.sdk.listener.PayListener;
import de.greenrobot.event.EventBus;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

//UnityPlayerActivity
public class MainActivity extends UnityPlayerActivity {
	private static final String TAG = "YYJIA";
	private GMcenter mCenter = null;
	private String gameObj;
	private boolean isLogin = false;
	public static Context context;
	public boolean showDebug;
	public boolean isToolBarVisible = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		 setContentView(R.layout.activity_yyjia);
		context = this;
		if (EventBus.getDefault().isRegistered(this))
			EventBus.getDefault().unregister(this);

		EventBus.getDefault().register(this, "onSDKEvent");
		EventBus.getDefault().register(this, "onPayEvent");
	}

	public void onSDKEventMainThread(SDKEvent event) {
		String type = event.getType();
		loger("onSDKEventMainThread type: " + type);
		if (type.equals("initYunXiaoTangSDK")) {
			mCenter = GMcenter.getInstance1(context);// 初始化SDK，初始化统计服务	
			isToolBarVisible = true;
		}
		
		if(mCenter == null){
			loger("NullPointer: mCenter can't be null! please init sdk");
//			Toast.makeText(context, "未初始化完成！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (type.equals("login")) {
			mCenter.setLoginListener(loginListener);
			mCenter.checkLogin();// 判断用户是否处于登录状态，未登录则登陆
			
		} else if (type.equals("logout")) {
			mCenter.logout();// 切换账号或者注销
			
		}else if (type.equals("showToolBar")) {
			mCenter.showtoolbar();// 显示浮标
			isToolBarVisible = true;

		}else if (type.equals("hideToolBar")) {
			mCenter.disstoolbar();// 隐藏浮标
			isToolBarVisible = false;

		}
	}

	public void showAndroidToast(String msg){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public void onPayEventMainThread(SDKPayEvent event) {
		loger("onPayEventMainThread ProductName: " + event.getProductName());
		mCenter.pay(context, event.getPrice(), 
				event.getProductName(), event.getServerId(), 
				event.getRoleId(), event.getOrderId(),
				event.getCallbackInfo(), payListener);
	}

	// 初始化
	public void initYunXiaoTangSDK(String gameObjName, boolean showDebug) {
		this.showDebug = showDebug;
		gameObj = gameObjName;
		SDKEvent event = new SDKEvent();
		event.setType("initYunXiaoTangSDK");

		EventBus.getDefault().post(event);
	}

	// 登陆
	public void login() {
		SDKEvent event = new SDKEvent();
		event.setType("login");

		EventBus.getDefault().post(event);
	}

	private LoginListener loginListener = new LoginListener() {
		// 登录监听方法
		@Override
		public void loginSuccessed(String code) {

			if (code == Information.LOGIN_SUSECCEDS) { // 登陆成功
				isLogin = true;
				int uid = mCenter.getUid();// 取得UserId
				String sessionid = mCenter.getSid();
				UnityPlayer.UnitySendMessage(gameObj, "msgReceiver",
					"{\"code\":\"1\",\"ret\":\"true\",\"UserId\":\"" + uid +"\",\"sessionid\":\"" + sessionid + "\"}");

				// Toast.makeText(context, "退出成功", Toast.LENGTH_LONG).show();
				loger("login success UserId: " + uid);
			} else {// 登陆失败
				UnityPlayer.UnitySendMessage(gameObj, "msgReceiver",
						"{\"code\":\"1\",\"ret\":\"true\",\"UserId\":\"\",\"sessionid\":\"\"}");
				// Toast.makeText(context, "登录失败", Toast.LENGTH_LONG).show();
			}
		}

		// 登出监听方法
		public void logoutSuccessed(String code) {
			// TODO Auto-generated method stub
			if (code == Information.LOGOUT_SUSECCED) {
				isLogin = false;
				String sid = mCenter.getSid();// 取得sessionid
				UnityPlayer.UnitySendMessage(gameObj, "msgReceiver",
						"{\"code\":\"3\",\"ret\":\"true\",\"sessionid\":\""
								+ sid + "\"}");

				// Toast.makeText(context, "登录成功", Toast.LENGTH_LONG).show();
				loger("logout success sessionid: " + sid);
			} else {
				UnityPlayer.UnitySendMessage(gameObj, "msgReceiver",
						"{\"code\":\"3\",\"ret\":\"false\",\"UserId\":\"\"}");
				// Toast.makeText(context, "退出失败", Toast.LENGTH_LONG).show();
			}
		}

	};

	/**
	 * 支付
	 * 
	 * @param price
	 *            商品价格
	 * @param productName
	 *            商品名称
	 * @param serverId
	 *            服务器编号
	 * @param roleId
	 *            角色编号
	 * @param orderId
	 *            CP订单号
	 * @param callbackInfo
	 *            扩展信息 当充值成功会回传给游戏服务器
	 */
	public void pay(float price, String productName, String serverId,
			String roleId, String orderId, String callbackInfo) {
		if(!isLogin){
			Toast.makeText(context, "请登陆！", Toast.LENGTH_LONG).show();
			return;
		}
		SDKPayEvent event = new SDKPayEvent();
		event.setPrice(price);
		event.setProductName(productName);
		event.setServerId(serverId);
		event.setRoleId(roleId);
		event.setOrderId(orderId);
		event.setCallbackInfo(callbackInfo);

		EventBus.getDefault().post(event);
	}

	private PayListener payListener = new PayListener() {

		@Override
		public void paySuccessed(String code, String cporderid) {
			// TODO 自动生成的方法存根
			if (code == Information.PAY_SUSECCED) {// 支付成功
				UnityPlayer.UnitySendMessage(gameObj, "msgReceiver",
						"{\"code\":\"2\",\"ret\":\"true\",\"cporderid\":\""
								+ cporderid + "\"}");

//				 Toast.makeText(context, cporderid + "支付成功",Toast.LENGTH_LONG).show();
			} else {// 支付失败
				UnityPlayer
						.UnitySendMessage(gameObj, "msgReceiver",
								"{\"code\":\"2\",\"ret\":\"false\",\"cporderid\":\"\"}");
//				 Toast.makeText(context, "充值失败", Toast.LENGTH_LONG).show();
			}
		}

	};

	// 切换账号或者注销
	public void logout() {
		if(!isLogin){
			Toast.makeText(context, "您未登陆！", Toast.LENGTH_LONG).show();
			return;
		}
		SDKEvent event = new SDKEvent();
		event.setType("logout");

		EventBus.getDefault().post(event);
	}

	public void showToolBar(){
//		loger("showToolBar isToolBarVisible: " + isToolBarVisible);
		if (!isToolBarVisible) {//当没有任何toolbar显示时
			SDKEvent event = new SDKEvent();
			event.setType("showToolBar");

			EventBus.getDefault().post(event);
		}
		else loger("The toolbar is already visible!");
	}
	
	public void hideToolBar(){
//		loger("showToolBar isToolBarVisible: " + isToolBarVisible);
		if (isToolBarVisible) {//当有toolbar显示时
			SDKEvent event = new SDKEvent();
			event.setType("hideToolBar");

			EventBus.getDefault().post(event);
		}
		else loger("The toolbar is already invisible!");
	}
	
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
	}

	@Override
	protected void onResume() {
		showToolBar();
		super.onResume();
	}

	@Override
	protected void onStop() {
		hideToolBar();
		super.onStop();
	}

	public void loger(String msg) {
		if (showDebug)
			Log.i(TAG, msg);
	}

	 /*
	// 初始化-登陆
	public void Init(View view) {
		initYYJIASDK("gameObj");
	}

	public void Login(View view) {
		login();
	}

	public void Pay(View view) {
		DateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd-hh-mm-ss-SSS", Locale.US);
		String requestId = format.format(new Date());
		pay(0.05f, "火娃烈焰皮肤", "3", "110110", requestId, "支付callbackInfo");
	}

	// 登出/切换账号
	public void Logout(View view) {
		logout();
	}
	// */

}
