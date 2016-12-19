package com.tnyoo.XiYou;

import java.net.URLEncoder;

import org.json.JSONObject;

import com.iapppay.interfaces.callback.IPayResultCallback;
import com.iapppay.sdk.main.IAppPay;


import com.unity3d.player.UnityPlayer;




import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.content.Intent;


public class StartIapppay extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartPaypub();
	}
	
	public void StartPaypub() {
		
		Intent intent = getIntent();
		String tranid = intent.getStringExtra("TranID");//接收数据
		String appid = intent.getStringExtra("AppID");
		
		int screenType = IAppPay.LANDSCAPE;//横版
		/**SDK初始化，完成SDK的初始化 */
		IAppPay.init(StartIapppay.this, screenType, appid);
		/**订单信息 */
		String genUrlStr = "transid=" + URLEncoder.encode(tranid) + "&appid=" + URLEncoder.encode(appid);
		Log.i("PAY","StartPaypub url: " + genUrlStr);
		/**发起支付  */
    	IAppPay.startPay(this, genUrlStr, new IPayResultCallback() {
    		
    		/**支付回调*/
			@Override
			public void onPayResult(int resultCode, String signvalue, String resultInfo) {
				String json = "";
				JSONObject obj = new JSONObject();
				try {
					obj.put("ResultCode", resultCode);
					obj.put("SignValue", signvalue);
					obj.put("ResultInfo", resultInfo);
					json = obj.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.i("PAY","支付回调：" + json);
		    	//UnityPlayer.UnitySendMessage(UObjName, "ConveyJSON", json);
				finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}





