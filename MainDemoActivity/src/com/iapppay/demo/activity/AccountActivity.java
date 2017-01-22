package com.iapppay.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.iapppay.openid.channel.IpayOpenidApi;
import com.iapppay.openid.channel.LoginResultCallback;
import com.iapppay.sdk.main.IAppPay;
import com.iapppay348.pay.R;

/**
 * 1：支付SDK初始化 ，本界面相当于APP的首界面
 * 2：如果使用了爱贝openid，需要增加openid的初始化
 * 详情 请参考onCreate()
 */
public class AccountActivity extends Activity implements OnClickListener {

	private static final String TAG = AccountActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int screenType = getIntent().getIntExtra("screentype", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏：固定方向，屏幕向左倾斜方向
		}else if (screenType == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);//横屏：根据传感器横向切换
		}else if (screenType == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

        /**
		 * openid sdk 初始化接口
		 * 如果您没有关联openid sdk 可以直接忽略此接口
		 */
        IpayOpenidApi.getInstance().initOpenId(this, screenType, PayConfig.appid);

        /**
		 * SDK初始化 ，请放在游戏启动界面
		 */
		IAppPay.init(AccountActivity.this, screenType, PayConfig.appid);//接入时！不要使用Demo中的appid


        setContentView(R.layout.activity_account);

		findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_goto_goods).setOnClickListener(this);

    }

    @Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_login:
			showPopWindow();
			break;
		case R.id.btn_goto_goods:
			gotoGoods(false);
			break;

		default:
			break;
		}
	}


	private void showPopWindow(){
		final PopupWindow popupWindow = new PopupWindow();
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View mMenuView = inflater.inflate(R.layout.pop_window, null);
		mMenuView.findViewById(R.id.btn_login_force).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				loginForce();
				popupWindow.dismiss();
			}
		});
		mMenuView.findViewById(R.id.btn_login_normal).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				loginNormal();
				popupWindow.dismiss();
			}
		});
		mMenuView.findViewById(R.id.btn_user_center).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				userCenter();
				popupWindow.dismiss();
			}
		});
		mMenuView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				popupWindow.dismiss();
				return false;
			}
		});
		popupWindow.setContentView(mMenuView);
		popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(this.findViewById(R.id.btn_login), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	private void loginForce(){
    	IpayOpenidApi.getInstance().loginOpenId(this, PayConfig.appid, true, new LoginResultCallback() {

			@Override
			public void onLoginResult(int resultCode, String resultInfo) {
				Log.d(TAG, "---------------------loginForce--resultCode：" + resultCode);
				Log.d(TAG, "---------------------loginForce--resultInfo：" + resultInfo);
				showToastAtCenter(resultInfo);
				if(resultCode==0){
					gotoGoods(true);
				}
			}
		});
    }
    private void loginNormal(){
    	IpayOpenidApi.getInstance().loginOpenId(this, PayConfig.appid, false, new LoginResultCallback() {

			@Override
			public void onLoginResult(int resultCode, String resultInfo) {
				Log.d(TAG, "---------------------loginNormal--resultCode：" + resultCode);
				Log.d(TAG, "---------------------loginNormal--resultInfo：" + resultInfo);
				showToastAtCenter(resultInfo);
				if(resultCode==0){
					gotoGoods(true);
				}
			}
		});
    }

    private void userCenter(){
    	IpayOpenidApi.getInstance().settingCenterOpenId(this, PayConfig.appid, new LoginResultCallback() {

			@Override
			public void onLoginResult(int resultCode, String resultInfo) {
				Log.d(TAG, "---------------------userCenter--resultCode：" + resultCode);
				Log.d(TAG, "---------------------userCenter--resultInfo：" + resultInfo);
				showToastAtCenter(resultInfo);
				if(resultCode==0){
					gotoGoods(true);
				}
			}
		});
    }

    private void gotoGoods(boolean isOpenidLogin){
    	Log.d(TAG, "-----------------------gotoGoods");
    	Intent intent = getIntent();
    	intent.setClass(this, GoodsListActivity.class);
    	intent.putExtra("isOpenidLogin", isOpenidLogin);
		startActivity(intent);
    }

    public void showToastAtCenter(String msg){
		Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


}
