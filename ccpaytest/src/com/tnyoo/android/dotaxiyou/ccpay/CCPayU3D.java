package com.tnyoo.android.dotaxiyou.ccpay;

import com.lion.ccpay.sdk.CCPaySdk;
import com.lion.ccpay.sdk.OnAccountPwdChangeListener;
import com.lion.ccpay.sdk.OnLoginCallBack;
import com.lion.ccpay.sdk.OnLoginOutAction;
import com.lion.ccpay.sdk.OnPayListener;
import com.lion.ccpay.sdk.Stats;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import de.greenrobot.event.EventBus;
import android.os.Bundle;
import android.util.Log;

//import android.widget.Toast;

/********************************************************************************
 * 注意1.切换账号 CCPaySdk.getInstance().login(false, new OnLoginCallBack());
 * 
 * 注意2.下线 CCPaySdk.getInstance().onOffline(false);//调用退出不弹出登录框
 * login(false);//弹出登录框
 * 
 * UnityPlayerActivity Activity
 *******************************************************************************/
public class CCPayU3D extends UnityPlayerActivity {
	private static final String TAG = "PAY.CC";
	public static String m_CCPayGameObj;
	// public Context m_Context;
	private final static String LoginResultRespMethod = "LoginResultResp";
	private final static String PayResultRespMethod = "PayResultResp";
	private final static String LogoutNotifyMethod = "LogoutNotify";
	private String loginToken = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		// initView();
		// m_Context = this;

		if (EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().unregister(this);
		}
		EventBus.getDefault().register(this, "onInitEvent");
	}

	// ------------------- C#调用接口 -------------------
	public void init(String gameObj) {
		m_CCPayGameObj = gameObj;
		InitEvent event = new InitEvent();
		event.setType("init");

		EventBus.getDefault().post(event);
	}

	public void login() {
		InitEvent event = new InitEvent();
		event.setType("login");

		EventBus.getDefault().post(event);
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
	
	/**
	 * 自定义价格支付
	 * 
	 * @param productId
	 *            商品id
	 * @param partnerTransactionNo
	 *            商户订单号
	 */
	public void payCustom(String productId, String partnerTransactionNo, String money) {
		InitEvent event = new InitEvent();
		event.setType("payCustom");
		event.setProductId(productId);
		event.setPartnerTransactionNo(partnerTransactionNo);
		event.setPrice(money);

		EventBus.getDefault().post(event);
	}

	public void clearAccount() {
		InitEvent event = new InitEvent();
		event.setType("clearAccount");

		EventBus.getDefault().post(event);
	}

	public void onInitEventMainThread(InitEvent event) {
		String str = event.getType();
		if (str == "init") {
			doSDKInit();
		} else if (str == "login") {
			doSDKLogin();
		} else if (str == "pay") {
			doSDKPay(event);
		} else if (str == "payCustom") {
			doSDKPayCustom(event);
		} else if (str == "clearAccount") {
			doSDKClearAccount();
		} else if (str == "changeAccount") {
			doSDKChangeAccountSdk();
		}
	}

	// 向Unity中发送消息
	public static void sendCallback(String name, String jsonParams) {
		if (jsonParams == null) {
			jsonParams = "";
		}
		Log.i(TAG, "sendCallback: " + name + ", msg:"+jsonParams);
		
		UnityPlayer.UnitySendMessage(m_CCPayGameObj, name, jsonParams);
	}

	// -------------------sdk 接口 begin------------------
	public void doSDKInit() {
		CCPaySdk.getInstance().init(this);// 初始化
		loginCCPaySDK(true);
		initCCPaySDKChangePwd();
		initCCpaySDKLoginOut();
	}

	/**
	 * 登录
	 */
	public void doSDKLogin() {
		if (!CCPaySdk.getInstance().isLogined()) {
			loginToken = null;
			loginCCPaySDK(true);// 传true直接登录
		} else {
			if (loginToken != null)
				sendCallback(LoginResultRespMethod, loginToken);// 登录成功发送token
			else{
				CCPaySdk.getInstance().onOffline(false);// 调用退出不弹出登录框
				loginCCPaySDK(false);// 传false弹出登录框
			}
			Log.i(TAG, "已登录,请不要重复登录~ loginToken: " + loginToken);
			// Toast.makeText(this, "已登录,请不要重复登录~", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 登出帐号
	 */
	public void doSDKClearAccount() {
		CCPaySdk.getInstance().onOffline(false);// 调用退出不弹出登录框
		// loginSdk(false);// 传false弹出登录框
		if (!CCPaySdk.getInstance().isLogined())
			loginToken = null;
	}

	/**
	 * 切换帐号
	 */
	public void doSDKChangeAccountSdk() {
		loginCCPaySDK(false);// 弹出登录框
		if (!CCPaySdk.getInstance().isLogined())
			loginToken = null;
	}

	/**
	 * 定额支付
	 */
	public void doSDKPay(InitEvent event) {
		if (!CCPaySdk.getInstance().isLogined()) {
			Log.i(TAG, "请登录后操作~");
			// Toast.makeText(this, "请登录后操作~", Toast.LENGTH_LONG).show();
			loginCCPaySDK(false);
			return;
		}
		// String productId = "100045";// 商品id
		// String partnerTransactionNo = "123456789";// 订单号
		CCPaySdk.getInstance().pay(event.getProductId(),
				event.getPartnerTransactionNo(), new OnPayListener() {
					@Override
					public void onPayResult(int status, String tn, String money) {
						String text = "";
						switch (status) {
						case OnPayListener.CODE_SUCCESS:// 支付成功
							text = "支付成功\n";
							break;
						case OnPayListener.CODE_FAIL:// 支付失败
							text = "支付失败\n";
							break;
						case OnPayListener.CODE_CANCEL:// 支付取消
							text = "支付取消\n";
							break;
						case OnPayListener.CODE_UNKNOW:// 支付结果未知
							text = "支付结果未知\n";
							break;
						}
						Log.i(TAG, "PayResult: " + text + "status:" + status
								+ "\ntn:" + tn + "\nmoney:" + money);
						// ToastUtils.showLongToast(mContext, text + "status:"
						// + status + "\ntn:" + tn + "\nmoney:" + money);
					}
				});
	}

	/**
	 * 不定额支付
	 */
	public void doSDKPayCustom(InitEvent event) {
		if (!CCPaySdk.getInstance().isLogined()) {
			Log.i(TAG, "请登录后操作~");
			// Toast.makeText(this, "请登录后操作~", Toast.LENGTH_LONG).show();
			loginCCPaySDK(false);
			return;
		}
		// EditText customMoneyEt = (EditText)
		// findViewById(R.id.activity_custom_money_pay);

		// String productId = "102300";// 商品id
		// String partnerTransactionNo = "123456789";// 订单号
		// String money = customMoneyEt.getText().toString().trim();

		CCPaySdk.getInstance().pay(event.getProductId(),
				event.getPartnerTransactionNo(), event.getPrice(),
				new OnPayListener() {
					@Override
					public void onPayResult(int statusCode, String trade_no,
							String money) {
						String text = "";
						switch (statusCode) {
						case OnPayListener.CODE_SUCCESS:// 支付成功
							text = "支付成功\n";
							break;
						case OnPayListener.CODE_FAIL:// 支付失败
							text = "支付失败\n";
							break;
						case OnPayListener.CODE_CANCEL:// 支付取消
							text = "支付取消\n";
							break;
						case OnPayListener.CODE_UNKNOW:// 支付结果未知
							text = "支付结果未知\n";
							break;
						}
						sendCallback(PayResultRespMethod, "{statusCode:"
								+ statusCode + ",trade_no:" + trade_no
								+ ",money:" + money + ",msg:" + text + "}");
						Log.i(TAG, "onPayResult: " + text + "status:"
								+ statusCode + "\ntn:" + trade_no + "\nmoney:"
								+ money);
						// ToastUtils.showLongToast(mContext, text + "status:"
						// + status + "\ntn:" + tn + "\nmoney:" + money);
					}
				});
	}

	// -------------------sdk 接口 end------------------

	/**
	 * 登录
	 * 
	 * @param flag
	 *            如果是游戏刚进去的时候，请设置成true,请便于可以直接登录（如果已登录的用户）, 如果要弹出登录框，则改成false
	 */
	private void loginCCPaySDK(boolean flag) {
		CCPaySdk.getInstance().login(flag, new OnLoginCallBack() {

			@Override
			public void onLoginSuccess(String uid, String token, String userName) {
				Log.i(TAG, "onLoginSuccess: " + "登录成功\n" + "uid:" + uid
						+ "\ntoken:" + token + "\nuserName:" + userName);
				// ToastUtils.showLongToast(mContext, "登录成功\n" + "uid:" + uid
				// + "\ntoken:" + token + "\nuserName:" + userName);
				loginToken = token;
				sendCallback(LoginResultRespMethod, token);// 登录成功发送token
			}

			@Override
			public void onLoginFail() {
				loginToken = null;
				Log.i(TAG, "onLoginFail: " + "登录失败~");
				// ToastUtils.showLongToast(mContext, "登录失败~");
				sendCallback(LoginResultRespMethod, "");// 登录不成功发送空
			}

			@Override
			public void onLoginCancel() {
				loginToken = null;
				Log.i(TAG, "onLoginCancel: " + "登录取消~");
				// ToastUtils.showLongToast(mContext, "登录取消~");
				sendCallback(LoginResultRespMethod, "");// 登录不成功发送空
			}

		});
	}

	/**
	 * 修改密码监听
	 */
	private void initCCPaySDKChangePwd() {
		CCPaySdk.getInstance().setOnAccountPwdChangeListener(
				new OnAccountPwdChangeListener() {

					@Override
					public void onAccountPwdChange() {
						CCPaySdk.getInstance().onOffline();
					}
				});
	}

	/**
	 * 悬浮窗内，点击帐号退出，退出成功后的回调
	 */
	private void initCCpaySDKLoginOut() {
		CCPaySdk.getInstance().setOnLoginOutAction(new OnLoginOutAction() {

			@Override
			public void onLoginOut() {
				sendCallback(LogoutNotifyMethod, "logout success");// 注销成功
				Log.i(TAG, "onLoginOut: " + "账号注销了~");
				// ToastUtils.showLongToast(mContext, "账号注销了~");
				loginToken = null;
			}
		});
	}

	/**
	 * 退出应用时必须调用
	 */
	@Override
	public void onDestroy() {
		CCPaySdk.getInstance().onLogOutApp();
		super.onDestroy();
	}

	/**
	 * 必须调用
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Stats.onResume(this);
	}

	/**
	 * 必须调用
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Stats.onPause(this);
	}

	// private void initView() {
	// init("gameObj");
	//
	// Button loginBtn = (Button) findViewById(R.id.activity_login_btn);
	// loginBtn.setText("登录");
	// loginBtn.setOnClickListener(mlistener);
	//
	// Button clearAccount = (Button)
	// findViewById(R.id.activity_clear_account_btn);
	// clearAccount.setText("游戏内退出帐号、下线");
	// clearAccount.setOnClickListener(mlistener);
	//
	// Button changeAccount = (Button)
	// findViewById(R.id.activity_change_account_btn);
	// changeAccount.setText("游戏内切换账号");
	// changeAccount.setOnClickListener(mlistener);
	//
	// Button pay = (Button) findViewById(R.id.activity_buy_pay_btn);
	// pay.setText("固定金额购买");
	// pay.setOnClickListener(mlistener);
	//
	// Button payCustom = (Button)
	// findViewById(R.id.activity_buy_pay_custom_btn);
	// payCustom.setText("自定义金额购买");
	// payCustom.setOnClickListener(mlistener);
	// }
	//
	// private OnClickListener mlistener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.activity_login_btn:// 登录
	// login();
	// break;
	// case R.id.activity_clear_account_btn:// 退出帐号
	// clearAccount();
	// break;
	// case R.id.activity_change_account_btn:// 切换账号
	// changeAccount();
	// break;
	// case R.id.activity_buy_pay_btn: // 固定金额购买
	// pay("103418", "029312894");
	// break;
	// case R.id.activity_buy_pay_custom_btn: // 自定义金额购买
	// payCustom("103418", "02983493", "6.00");
	// break;
	// }
	// }
	// };
}
