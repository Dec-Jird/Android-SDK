package com.example.ccpaytest;

import com.lion.ccpay.sdk.CCPaySdk;
import com.lion.ccpay.sdk.OnAccountPwdChangeListener;
import com.lion.ccpay.sdk.OnLoginCallBack;
import com.lion.ccpay.sdk.OnLoginOutAction;
import com.lion.ccpay.sdk.OnPayListener;
import com.lion.ccpay.sdk.Stats;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/********************************************************************************
 * 注意1.切换账号
 * CCPaySdk.getInstance().login(false, new OnLoginCallBack());
 * 
 * 注意2.下线
 * CCPaySdk.getInstance().onOffline(false);//调用退出不弹出登录框
 * login(false);//弹出登录框
 *
 *******************************************************************************/
public class MainActivity extends Activity implements OnClickListener {
	
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		
		CCPaySdk.getInstance().init(this);//初始化
		login(true);
		initCCPaySDKChangePwd();
		initCCpaySDKLoginOut();
		initView();
	}

	private void initView() {
		Button loginBtn = (Button) findViewById(R.id.activity_login_btn);
		loginBtn.setText("登录");
		loginBtn.setOnClickListener(this);

		Button clearAccount = (Button) findViewById(R.id.activity_clear_account_btn);
		clearAccount.setText("游戏内退出帐号、下线");
		clearAccount.setOnClickListener(this);

		Button changeAccount = (Button) findViewById(R.id.activity_change_account_btn);
		changeAccount.setText("游戏内切换账号");
		changeAccount.setOnClickListener(this);

		Button pay = (Button) findViewById(R.id.activity_buy_pay_btn);
		pay.setText("固定金额购买");
		pay.setOnClickListener(this);

		Button payCustom = (Button) findViewById(R.id.activity_buy_pay_custom_btn);
		payCustom.setText("自定义金额购买");
		payCustom.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_login_btn:// 登录
			if (!CCPaySdk.getInstance().isLogined()) {
				login(true);
			}else{
				Toast.makeText(this, "已登录,请不要重复登录~", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.activity_clear_account_btn:// 退出帐号
			CCPaySdk.getInstance().onOffline(false);
			login(false);
			break;
		case R.id.activity_change_account_btn:// 切换账号
			login(false);
			break;
		case R.id.activity_buy_pay_btn:{// 固定金额购买
			if (!CCPaySdk.getInstance().isLogined()) {
				Toast.makeText(this, "请登录后操作~", Toast.LENGTH_LONG).show();
				login(false);
				return;
			}
			String  productId 			 = "100045";//商品id
			String  partnerTransactionNo = "123456789";//订单号
			CCPaySdk.getInstance().pay(productId, partnerTransactionNo, new OnPayListener() {
				@Override
				public void onPayResult(int status, String tn, String money) {
					String text = "";
					switch (status) {
					case OnPayListener.CODE_SUCCESS://支付成功
						text = "支付成功\n";
						break;
					case OnPayListener.CODE_FAIL://支付失败
						text = "支付失败\n";							
						break;
					case OnPayListener.CODE_CANCEL://支付取消
						text = "支付取消\n";
						break;
					case OnPayListener.CODE_UNKNOW://支付结果未知
						text = "支付结果未知\n";
						break;
					}
					ToastUtils.showLongToast(mContext, text + "status:" + status + "\ntn:" + tn + "\nmoney:" + money);
				}
			});
			}
			break;
		case R.id.activity_buy_pay_custom_btn:{// 自定义金额购买
			if (!CCPaySdk.getInstance().isLogined()) {
				Toast.makeText(this, "请登录后操作~", Toast.LENGTH_LONG).show();
				login(false);
				return;
			}
			EditText customMoneyEt = (EditText) findViewById(R.id.activity_custom_money_pay);
			
			String  productId 			 = "102300";//商品id
			String  partnerTransactionNo = "123456789";//订单号
			String  money 				 = customMoneyEt.getText().toString().trim();
			
			CCPaySdk.getInstance().pay(productId, partnerTransactionNo, money, new OnPayListener() {
				@Override
				public void onPayResult(int status, String tn, String money) {
					String text = "";
					switch (status) {
					case OnPayListener.CODE_SUCCESS://支付成功
						text = "支付成功\n";
						break;
					case OnPayListener.CODE_FAIL://支付失败
						text = "支付失败\n";							
						break;
					case OnPayListener.CODE_CANCEL://支付取消
						text = "支付取消\n";
						break;
					case OnPayListener.CODE_UNKNOW://支付结果未知
						text = "支付结果未知\n";
						break;
					}
					ToastUtils.showLongToast(mContext, text + "status:" + status + "\ntn:" + tn + "\nmoney:" + money);
				}
			});
			}
			break;
		}

	}

	/**
	 * 登录
	 * @param flag 如果是游戏刚进去的时候，请设置成true,请便于可以直接登录（如果已登录的用户）, 如果要弹出登录框，则改成false
	 */
	private void login(boolean flag) {
		CCPaySdk.getInstance().login(flag, new OnLoginCallBack() {

			@Override
			public void onLoginSuccess(String uid, String token, String userName) {
				ToastUtils.showLongToast(mContext, "登录成功\n" + "uid:" + uid + "\ntoken:" + token + "\nuserName:" + userName);
			}
			
			@Override
			public void onLoginFail() {
				ToastUtils.showLongToast(mContext, "登录失败~");
			}
			
			@Override
			public void onLoginCancel() {
				ToastUtils.showLongToast(mContext, "登录取消~");
			}

		});
	}

	/**
	 * 修改密码监听
	 */
	private void initCCPaySDKChangePwd() {
		CCPaySdk.getInstance().setOnAccountPwdChangeListener(new OnAccountPwdChangeListener() {
			
			@Override
			public void onAccountPwdChange() {
				CCPaySdk.getInstance().onOffline();
			}
		});
	}
	
	private void initCCpaySDKLoginOut() {
		CCPaySdk.getInstance().setOnLoginOutAction(new OnLoginOutAction() {
			
			@Override
			public void onLoginOut() {
				ToastUtils.showLongToast(mContext, "账号注销了~");
			}
		});
	}

	/**
	 * 退出应用时必须调用
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		CCPaySdk.getInstance().onLogOutApp();
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

}
