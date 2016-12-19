package com.tnyoo.huaweipay;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import com.android.huawei.pay.plugin.IPayHandler;
import com.huawei.gamebox.buoy.sdk.impl.BuoyOpenSDK;
import com.huawei.gamebox.buoy.sdk.inter.UserInfo;
import com.huawei.gamebox.buoy.sdk.util.DebugConfig;
import com.huawei.gamebox.buoy.sdk.util.RoleInfo;
import com.huawei.hwid.openapi.OpenHwID;
import com.huawei.opensdk.OpenSDK;
import com.huawei.opensdk.RetCode;
import com.tnyoo.huaweipay.util.SDKEvent;
import com.tnyoo.huaweipay.util.SDKPayEvent;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;
import de.greenrobot.event.EventBus;

/**
 * @author Administrator UnityPlayerActivity
 */
public class HuaweiPayActivity extends UnityPlayerActivity {

	public final static String RESP_METHOD_Msg_Receiver = "msgReceiver";
	public static final String TAG = "Huawei";
	public static String gameObjName;
	private int retCode;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_huawei_pay);
		DebugConfig.d(TAG, "HuaweiPayActivity [onCreate]: " + this.toString());
		
		if (EventBus.getDefault().isRegistered(this))
			EventBus.getDefault().unregister(this);

		EventBus.getDefault().register(this, "onSDKEvent");
		EventBus.getDefault().register(this, "onPayEvent");
	}

	public void onSDKEventMainThread(SDKEvent event) {
		String type = event.getType();
		DebugConfig.d(TAG, "onSDKEventMainThread type: " + type);
		if (type.equals("initHuawei")) {
			// 浮标初始化
			retCode = OpenSDK.init(HuaweiPayActivity.this,
					GlobalParam.APP_ID, GlobalParam.PAY_ID,
					GlobalParam.BUO_SECRET,// privateKey,
					userInfo);

			DebugConfig.d(TAG, "OpenSDK init retCode is:" + retCode);
			
		} else if (type.equals("login")) {
			// 初始化成功，进行登录调用
			if (RetCode.SUCCESS == retCode) {
				OpenSDK.start();
				DebugConfig.d(TAG, "OpenSDK login success");
			}
			
		} else if (type.equals("displayBuoy")) {
			// 显示浮标
			BuoyOpenSDK.getIntance().setShowType(1);// 浮标类型参数, 1：应用级别, 2：页面级别
			BuoyOpenSDK.getIntance().showSmallWindow(this);
			DebugConfig.d(TAG, "HuaweiPayActivity displayBuoy");
			
		} else if (type.equals("hideBuoy")) {
			// 隐藏浮标
			BuoyOpenSDK.getIntance().hideSmallWindow(this);
			BuoyOpenSDK.getIntance().hideBigWindow(this);
			DebugConfig.d(TAG, "HuaweiPayActivity hideBuoy");
			
		}
	}
	
	public void onPayEventMainThread(SDKPayEvent event) {
		DebugConfig.d(TAG,
				"onPayEventMainThread ProductName: " + event.getProductName());

//		String sign = GameBoxUtil.getSign(event.getPrice(),
//				event.getProductName(), event.getProductDesc(),
//				event.getOrderId());
		
		// 调用公共方法进行支付
		GameBoxUtil.pay(HuaweiPayActivity.this, event.getPrice(),
				event.getProductName(), event.getProductDesc(), event.getOrderId(),
				event.getExtReserved(), event.getSign(), payHandler);
	}
	
	/**
	 * 初始化SDK并登陆
	 * 
	 * @param gameObj
	 *            游戏对象
	 * @param debugLog
	 *            是否开启调试日志（true开启）
	 * @param payOrientation
	 *            支付界面横竖屏（1: 横屏，2: 竖屏）
	 * @param appId
	 *            应用ID
	 * @param buoySecret
	 *            浮标密钥
	 * @param payId
	 *            支付ID
	 * @param payRsaPrivate
	 *            支付私钥
	 */
	public void initHuawei(String gameObj, boolean debugLog,
			int payOrientation, String appId, String buoySecret, String payId) {
		gameObjName = gameObj;
		GlobalParam.APP_ID = appId;
		GlobalParam.BUO_SECRET = buoySecret;
		GlobalParam.PAY_ID = payId;
		GlobalParam.PAY_ORI = payOrientation;// 设置横竖屏，1竖屏，2横屏
		GlobalParam.SHOW_DEBUG_LOG = debugLog;
		// 开启浮标的日志功能，发布时去掉
		DebugConfig.setLog(GlobalParam.SHOW_DEBUG_LOG);
		
		SDKEvent event = new SDKEvent();
		event.setType("initHuawei");

		EventBus.getDefault().post(event);
	}

	public void login() {
		SDKEvent event = new SDKEvent();
		event.setType("login");

		EventBus.getDefault().post(event);
	}

	// 显示浮标
	public void displayBuoy() {
		SDKEvent event = new SDKEvent();
		event.setType("displayBuoy");

		EventBus.getDefault().post(event);
	}

	// 隐藏浮标
	public void hideBuoy() {
		SDKEvent event = new SDKEvent();
		event.setType("hideBuoy");

		EventBus.getDefault().post(event);
	}

	/**
	 * 存储用户的游戏信息
	 * 
	 * @param rank
	 *            等级
	 * @param roleName
	 *            角色名
	 * @param area
	 *            区名
	 * @param sociaty
	 *            公会
	 */
	public void saveRoleInfo(String rank, String roleName, String area,
			String sociaty) {
		// if (!isLogin) {
		// Toast.makeText(getBaseContext(), "请登录", Toast.LENGTH_SHORT).show();
		// return;
		// }
		HashMap<String, String> role = new HashMap<String, String>();

		/**
		 * 将用户的等级等信息保存起来，必须的参数为RoleInfo.GAME_RANK(等级)/RoleInfo.GAME_ROLE(角色名称)
		 * /RoleInfo.GAME_AREA(角色所属区)/RoleInfo.GAME_SOCIATY(角色所属公会名称)
		 * 全部使用String类型存放
		 */
		role.put(RoleInfo.GAME_RANK, rank);
		role.put(RoleInfo.GAME_ROLE, roleName);
		role.put(RoleInfo.GAME_AREA, area);
		role.put(RoleInfo.GAME_SOCIATY, sociaty);

		// 存储用户当前的角色信息
		BuoyOpenSDK.getIntance().saveRoleInfo(role, HuaweiPayActivity.this);
		DebugConfig.d(TAG, "MainActivity saveRoleInfo");
	}

	/**
	 * 上面getBuoyPrivate中登录：OpenSDK.start()，
	 * 登陆完成后会主动调用初始化时设置的UserInfo的dealUserInfo方法
	 */
	private UserInfo userInfo = new UserInfo() {

		@Override
		public void dealUserInfo(HashMap<String, String> userInfo) {

			DebugConfig.d(TAG, "userInfo.dealUserInfo()");
			// 用户信息为空，登录失败
			if (null == userInfo) {
				// Toast.makeText(getApplicationContext(), "用户信息为空，登录失败",
				// Toast.LENGTH_SHORT).show();
				DebugConfig.d(TAG, "user info is empty，login failed. return");
				// 登陆失败，accesstoken传空，code=1为登录
				UnityPlayer
						.UnitySendMessage(gameObjName,
								RESP_METHOD_Msg_Receiver,
								"{\"code\":\"1\",\"ret\":\"false\",\"accesstoken\":\"\"}");
				return;
			}

			// 使用华为账号登录且成功，进行accessToken验证
			if ("1".equals(userInfo.get("loginStatus"))) {
				displayBuoy();// 登陆成功，显示浮标
				// Toast.makeText(getApplicationContext(),
				// "使用华为账号登录，进行accessToken校验",
				// Toast.LENGTH_SHORT).show();

				// 进行accessToken校验
				Map<String, String> param = new HashMap<String, String>();
				param.put("accessToken", userInfo.get("accesstoken"));
				// 登陆成功，传accesstoken，code=1为登录
				UnityPlayer.UnitySendMessage(gameObjName,
						RESP_METHOD_Msg_Receiver,
						"{\"code\":\"1\",\"ret\":\"true\",\"accesstoken\":\""
								+ (String) userInfo.get("accesstoken") + "\"}");

				DebugConfig.d(
						TAG,
						"login sussess accessToken: "
								+ userInfo.get("accesstoken"));

				/*** KKK这里将accessToken返回给Unity，游戏中会对accessToken进行验证，验证成功进入游戏 **/
				// /*
				// * 此处禁用返回键，防止校验accessToken完成前退出当前Activity，CP可以用等待框等其他方式处理
				// * 校验accessToken完成以后恢复返回键可用 isBackKeyEnable = false;
				// */
				// ReqTask checkAccessToken = new ReqTask(new ReqTask.Delegate()
				// {
				//
				// @Override
				// public void execute(String ret) {
				// // 向服务端发起校验accessToken的请求， 服务端向华为https:api.vmall.com/rest.php
				// //进行校验accessToken，并返回校验结果由于没有部署最终的服务端，
				// //
				// 所以这里写死一个校验结果，服务端代码参考华为Demo,服务器校验网址：HuaweiServerDemo/validtoken
				// // ret = "200";
				// DebugConfig.d(TAG, "验证accessToken ret = " + ret);
				// if ("200".equals(ret)) {
				// Toast.makeText(getBaseContext(),
				// "验证accessToken成功，进入游戏", Toast.LENGTH_SHORT)
				// .show();
				// isAccess = true;
				// } else {
				// Toast.makeText(getBaseContext(), "验证accessToken失败",
				// Toast.LENGTH_SHORT).show();
				// }
				//
				// // 校验accessToken完成以后恢复返回键可用
				// isBackKeyEnable = true;
				// }
				// }, param, GlobalParam.VALID_TOKEN_ADDR);
				// checkAccessToken.execute();

			} else {
				// 登陆失败，accesstoken传空，code=1为登录
				UnityPlayer
						.UnitySendMessage(gameObjName,
								RESP_METHOD_Msg_Receiver,
								"{\"code\":\"1\",\"ret\":\"false\",\"accesstoken\":\"\"}");
			}
			DebugConfig.d(TAG, "loginStatus = " + userInfo.get("loginStatus"));
		}
	};

	/**
	 * 支付调用
	 * 
	 * @param price
	 *            价格
	 * @param productName
	 *            道具名
	 * @param productDesc
	 *            道具描述
	 * @param orderId
	 *            订单号
	 * @param companyName
	 *            公司名称
	 * @param extReserved
	 *            商户侧保留信息，输入的话在回调接口中原样返回
	 * @param sign
	 *            签名
	 */
	public void pay(String price, String productName, String productDesc,
			String orderId, String companyName, String extReserved, String sign) {
		GlobalParam.USER_NAME = companyName;

		DebugConfig.d(TAG, "sign = " + sign);

		// 价格必须精确到小数点后两位，使用正则进行匹配
		boolean priceChceckRet = Pattern.matches("^\\d+.\\d{2}$", price);
		if (!priceChceckRet) {
			// Toast.makeText(this, "价格必填且精确到小数点后两位",
			// Toast.LENGTH_SHORT).show();
			return;
		}
		
		if ("".equals(productName)) {
			// Toast.makeText(this, "道具名称必填", Toast.LENGTH_SHORT)
			// .show();
			return;
		}
		// 禁止输入：# " & / ? $ ^ *:) \ < > | , =
		else if (Pattern.matches("[#\\$\\^&*)=|\",/<>\\?:]", productName)) {
			// Toast.makeText(this, "道具名称不能存在  #$^&*)=|\",/<>\\?:",
			// Toast.LENGTH_LONG).show();
			DebugConfig.i(TAG, "道具名称不能存在  #$^&*)=|\",/<>\\?:");
			return;
		}
		
		// 禁止输入：# " & / ? $ ^ *:) \ < > | , =
		if ("".equals(productDesc)) {
			// Toast.makeText(this, "道具描述必填", Toast.LENGTH_SHORT)
			// .show();
			DebugConfig.i(TAG, "道具描述必填");
			return;
		}
		// 禁止输入：# " & / ? $ ^ *:) \ < > | , =
		else if (Pattern.matches("[#\\$\\^&*)=|\",/<>\\\\?\\^:]", productDesc)) {
			// Toast.makeText(this, "道具描述不能存在  #$^&*)=|\",/<>\\?:",
			// Toast.LENGTH_LONG).show();
			DebugConfig.i(TAG, "道具描述不能存在  #$^&*)=|\",/<>\\?:");
			return;
		}
		
		SDKPayEvent event = new SDKPayEvent();
		event.setPrice(price);
		event.setProductName(productName);
		event.setProductDesc(productDesc);
		event.setOrderId(orderId);
		event.setExtReserved(extReserved);
		event.setSign(sign);

		EventBus.getDefault().post(event);
	}

	/**
	 * 支付回调handler，不用进行验签，验签直接走服务器 支付结果会在这里返回， JSON 格式
	 */
	private IPayHandler payHandler = new IPayHandler() {

		@Override
		public void onFinish(Map<String, String> payResp) {
//			isHomeKeyEnable = true;
			// 返回值 returnCode 为 0 并且 errMsg 为 success 时， 本次支付成功
			DebugConfig.d(TAG, "pay callback：payResp= " + payResp);
			// 支付结果回调，传payResp，code=2为支付
			UnityPlayer.UnitySendMessage(
					gameObjName,
					RESP_METHOD_Msg_Receiver,
					"{\"code\":\"2\",\"ret\":\"true\",\"payResp\":\""
							+ payResp.toString() + "\"}");
			
			// // 处理支付结果
			// String pay = "支付未成功！";
			// // 支付成功，进行验签
			// if ("0".equals(payResp.get(PayParameters.returnCode))) {
			// if ("success".equals(payResp.get(PayParameters.errMsg))) {
			// //
			// 支付成功，验证信息的安全性；待验签字符串中如果有isCheckReturnCode参数且为yes，则去除isCheckReturnCode参数
			// if (payResp.containsKey("isCheckReturnCode")
			// && "yes".equals(payResp.get("isCheckReturnCode"))) {
			// payResp.remove("isCheckReturnCode");
			//
			// } else {//
			// 支付成功，验证信息的安全性；待验签字符串中如果没有isCheckReturnCode参数活着不为yes，则去除isCheckReturnCode和returnCode参数
			// payResp.remove("isCheckReturnCode");
			// payResp.remove(PayParameters.returnCode);
			// }
			// // 支付成功，验证信息的安全性；待验签字符串需要去除sign参数
			// String sign = payResp.remove(PayParameters.sign);
			//
			// String noSigna = HuaweiPayUtil.getSignData(payResp);
			//
			// /** KKK这里改为将待验签字符发送给Unity，再由游戏发送给服务器，由服务器进行验签 **/
			// // 使用公钥进行验签
			// boolean s = Rsa.doCheck(noSigna, sign,
			// GlobalParam.PAY_RSA_PUBLIC);
			//
			// if (s) {
			// pay = "支付成功！";
			// } else {
			// 支付成功但验签失败的订单，需要用服务器查询此笔订单的支付状态，并且以服务器查询结果为准
			// pay = "支付成功，但验签失败！";
			// }
			// DebugConfig.d(TAG, "支付结束：sign= " + sign + "，待验证字段："
			// + noSigna + "，Rsa.doChec = " + s);
			// } else {
			// DebugConfig.d(TAG, "支付失败 errMsg= "
			// + payResp.get(PayParameters.errMsg));
			// }
			// } else if ("30002".equals(payResp.get(PayParameters.returnCode)))
			// {
			// pay = "支付结果查询超时！";
			// }
			// DebugConfig.d(TAG, " 支付结果 result = " + pay);
			// Toast.makeText(HuaweiPayActivity.this, "支付结果 result = " + pay,
			// Toast.LENGTH_SHORT).show();
			//
			// // 重新生成订单号，订单编号不能重复，所以使用时间的方式，CP可以根据实际情况进行修改，最长30字符
			// DateFormat format = new java.text.SimpleDateFormat(
			// "yyyy-MM-dd-hh-mm-ss-SSS", Locale.US);
			// String requestId = format.format(new Date());
			// Toast.makeText(getBaseContext(), "重新生成订单号：" + requestId,
			// Toast.LENGTH_SHORT).show();
		}
	};

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		DebugConfig.d(TAG, "onKeyDown: " + keyCode);
//		//在支付过程中不允许按home键，避免出问题
//		if (keyCode == KeyEvent.KEYCODE_HOME && isHomeKeyEnable) {
//			super.onKeyDown(keyCode, event);
//		}
//		return true;
//	}
	
	/*
	 * 在界面恢复的时候可以显示浮标
	 */
	@Override
	protected void onResume() {
		super.onResume();
		DebugConfig.d(TAG, "onResume");
		// 在界面恢复的时候又显示浮标，和onPause配合使用
//		displayBuoy();
		BuoyOpenSDK.getIntance().showSmallWindow(this);
	}

	/*
	 * 在界面暂停的时候可以隐藏浮标
	 */
	@Override
	protected void onPause() {
		super.onPause();
		DebugConfig.d(TAG, "onPause");
		// 在界面暂停的时候，隐藏浮标，和onResume配合使用
//		hideBuoy();
		BuoyOpenSDK.getIntance().hideSmallWindow(this);
		BuoyOpenSDK.getIntance().hideBigWindow(this);
	}

	//资源释放可以在SDKManager中的SDKRelease中调用进行资源释放
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
	protected void onDestroy() {
		super.onDestroy();
		// 仅当MainActivity为最后一个Activity时，才释放资源
		if (isTaskRoot()) {
			// 清空帐号资源
			OpenHwID.releaseResouce();
			// 在退出的时候销毁浮标
			BuoyOpenSDK.getIntance().destroy(this);
			DebugConfig.d(TAG, "onDestroy");
		}
	}
/*
	// 初始化-登陆
	public void Init(View view) { // 联盟为应用分配的应用ID
		String APP_ID = "10383643";// release: 10383643 // 浮标密钥
		String BUO_SECRET = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIP8pEozPFtymeGBIvZjc7yZQx0VAuqD6SKiMgGC1U12MwMfAt2y/f7W4T4Mz0ROvPDILsE4razxgTlAvERVqdfFzZaQHa4lV1sw1BXNEr9v2O4uatvnJ+EDoRuBpOmsQDF+m8fYZDTgPDyqYBP4lmnaM1mjSQyFyqSrgE1/CGZxAgMBAAECgYAcriyBFysZcAiyMN6JDclbhS5JRoSQs0NYzEfzfhudrn6dQgLcbAW3d7gQRLZRUI7L/6PxRsCuoPgEmeWPL9ItChxZrj2guXABvx2oE09Krf7GoQzBZ93L2UDEmndjrTfbdq1dso+JJSmpRDGI/L0ClabMEceM8rcL0dVUKdz+8QJBALxeJKMbP0l8ye8EDqUCkTOuEtQGuoKP3CrV3Ojf3rSkpCd+SIA/lnaeNDhlcLWKN5yjbGJQVBjYKqFyC2KSJF0CQQCzYD2ofxTCtbZ3OxdV8zP7dm+SDoTvL+xqjWijl286WAn6ZY67ujRgQVJZKEtRLVIZuokY/8o8O0n5EMHLC2klAkAxXhsMBGAZynLCCbYs2PdlLAITP5AyF7IEJ/i1r7aWcW/0ScyGkCPHAr986FkZuxfIHaySN6tSt5S0q0cAlnyhAkALydBjiW6A2VZTgaOn92K8LO1qKOAdpAWifwaYcbRSnkSRmykFN1iH1uMljttcS2Ew5axq4yyURsU2xB4oTOitAkABP+COzXInLUsxvJCrVy57Pw10z1Ll3MjebSRP7cAW/d+hcTx2z0Md1riUNIxraXPmh17Fzgv6WLez4m2g+8bF";
		// 支付ID： 浮标初始化 和 支付的使用要用release: 900086000022031102
		String PAY_ID = "900086000022031102";
		initHuawei("gameObj", true, 1, APP_ID, BUO_SECRET, PAY_ID);
	}

	public void Login(View view) {
		login();
	}

	// 存储用户的游戏信息
	public void EnterGame(View view) {
		saveRoleInfo("28级", "猎人K", "九十五区 雄霸天下", "决战天下二");
	}

	public void Pay(View view) {
		DateFormat format = new java.text.SimpleDateFormat("yyyyMMddhhmmssSSS",
				Locale.US);
		String requestId = format.format(new Date());

		// requestId = "201603071423";
		String sign = "lyH1BF3X0n7cM6wWRrBb4Rb92w2y6hiFRxprrrHdmnpRhf7vY02zJ9udSdrhsl+vPBUTYYjaV9x816Mc349cloWO6HFHBm+BTbAyZEP1rxmzwZWHD0OXBicSAEF9CaOVzaAKdUULFQENMvp7T8SCEHXXWhYs00FxJvkSI23JoknafIMo9OrtZ4c+NpG35kqMFU6yFDlUpYlZepsgFju7GKZbPdXCSClejcW5FUaUzhQEZ7IDT/RkWMOn81tfHhDv/Z7idle8NyjZollLxFUpLqDZnU+ywYF7tk8vC23+pnuP7AV0O75eFeBUns4fZiaI0/XVZO+mj9pLEIMaUivoOg==";
		sign = GameBoxUtil.getSign("0.01", "元宝", "元宝", requestId);
		pay("6.66", "元宝", "元宝", requestId, "天游科技", "", sign);
	}

	// 显示浮标
	public void displayBuoy(View view) {
		displayBuoy();
	}

	// 隐藏浮标
	public void hideBuoy(View view) {
		hideBuoy();
	}
//	*/
}
