package com.tnyoo.android.dotaxiyou.ccpay;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.lion.ccpay.CCPaySdk;
import com.lion.ccpay.CCPaySdk$Stats;
import com.lion.ccpay.login.LoginListener;
import com.lion.ccpay.pay.PayListener;
import com.lion.ccpay.pay.vo.PayResult;
import com.lion.ccpay.user.vo.LoginResult;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import de.greenrobot.event.EventBus;

public class U3DApi extends UnityPlayerActivity {

	public static String m_CCPayGameObj;
	public Context m_Context;
	private static final String TAG = "PAY";
	private final static String LoginResultRespMethod = "LoginResultResp";
	private final static String PayResultRespMethod = "PayResultResp";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		m_Context = this;// UnityPlayer.currentActivity;
//		setContentView(R.layout.activity_ccpay_u3d);
		
		if (EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().unregister(this);
		}
		EventBus.getDefault().register(this, "onInitEvent");
	}

	// -------------------sdk 接口 ------------------
	public void onInitEventMainThread(InitEvent event) {
		String str = event.getType();

		if (str == "init") {
			doSDKInit();
		} else if (str == "login") {
			doSDKLogin();
		} else if (str == "pay") {
			doSDKPay(event);
		}

	}

	public void init(String gameObj) {
		m_CCPayGameObj = gameObj;

		InitEvent event = new InitEvent();
		event.setType("init");

		EventBus.getDefault().post(event);
	}

	public void doSDKInit() {
		CCPaySdk.getInstance().init(m_Context);
		Log.i(TAG, "doSDKInit");
	}

	public void login() {
		InitEvent event = new InitEvent();
		event.setType("login");

		EventBus.getDefault().post(event);
	}

	public void doSDKLogin() {
		CCPaySdk.getInstance().login(this, new LoginListener() {

			@Override
			public void onComplete(LoginResult result) {
				// JSONObject obj = new JSONObject();
				Log.i(TAG, "onSDKLoginComplete: " + result);
				/** 注意，这里需要使用result.token，连接ccpay服务器进行登录确认 **/
				if (result != null && result.isSuccess) {
					// Toast.makeText(getApplicationContext(),result.toString(),
					// Toast.LENGTH_SHORT).show();
					//
					// try {
					// obj.put("result", result);
					// } catch (JSONException e) {
					// e.printStackTrace();
					// }
					UnityPlayer.UnitySendMessage(m_CCPayGameObj,
							LoginResultRespMethod, result.token);//登录成功发送token
				} else {
					// try {
					// obj.put("result", result);
					// } catch (JSONException e) {
					// e.printStackTrace();
					// }
					UnityPlayer.UnitySendMessage(m_CCPayGameObj,
							LoginResultRespMethod, "");//登录不成功发送空
				}

			}
		}); // end of onComplete

	}

	/**
	 * 支付（不需价格）
	 * 
	 * @param productId
	 *            商品id
	 * @param partnerTransactionNo
	 *            商户订单号
	 */
	public void pay(String productId, String partnerTransactionNo) {
		InitEvent event = new InitEvent();
		event.setType("pay");
		event.setProductId(productId);
		event.setPartnerTransactionNo(partnerTransactionNo);

		EventBus.getDefault().post(event);
	}

	public void doSDKPay(InitEvent event) {
		CCPaySdk.getInstance().pay(this, event.getProductId(),
				event.getPartnerTransactionNo(), new PayListener() {

					@Override
					public void onComplete(PayResult result) {

						UnityPlayer.UnitySendMessage(m_CCPayGameObj,
								PayResultRespMethod, "{statusCode:" + result.statusCode + ",trade_no:"+ 
										result.trade_no + ",body:" + result.body + ",msg:" + result.msg + "}");

						Log.i(TAG, "onSDKPayComplete: " + "{statusCode:" + result.statusCode + ",trade_no:"+
								result.trade_no + ",body:" + result.body + ",msg:" + result.msg + "}");
					}
				});
	}

	@Override
	protected void onPause() {
		super.onPause();

		CCPaySdk$Stats.onResume(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		CCPaySdk$Stats.onResume(this);
	}

//	public void onClick(View view) {
//		switch (view.getId()) {
//		case R.id.init:
//			init("gameObj");
//			break;
//		case R.id.login:
//			login();
//			break;
//		case R.id.pay:
//			pay("100045", "123456789");
//			break;
//		}
//	}

}
