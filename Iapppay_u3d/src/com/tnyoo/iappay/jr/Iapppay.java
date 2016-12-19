package com.tnyoo.iappay.jr;

import java.net.URLEncoder;

import org.json.JSONObject;
import com.iapppay.interfaces.callback.IPayResultCallback;
import com.iapppay.sdk.main.IAppPay;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;

public class Iapppay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartPaypub();
	}

	public String requestTransID(){
		/* 用户在商户应用的唯一标识，建议为用户帐号。对于需要区分到不同区服，#号分隔；比如帐号abc在01区 ="abc#01" */
//		String appuserid = "dota148433328"; // 108412312312310 dota148433328
//		String waresname = "BLOOD_SWORD"; // BLOOD_SWORD //本地生成商品名（可选）
//		String cporderid = "3456345345"; // 3456345345 //本地生成的订单号，保证系统唯一
//		float price = 0.03f; // 0.1f 商品价格（可选）
//		String cpprivateinfo = "094978347203";// 本地生成商户私有信息（可选） 1231231231
//		 String notifyurl = "http://kkk.fff.aaa:323424/IAppPay";// 支付结果通知地址（可选）
//		String notifyurl = "http://182.254.148.221:3358/iapppay";// 支付结果通知地址（可选）
		String transId = null;
		
		return transId;
	}
	
	public void StartPaypub() {

		Intent intent = getIntent();
		String tranid = intent.getStringExtra("TranID");// 接收数据
		String appid = intent.getStringExtra("AppID");

		int screenType = IAppPay.PORTRAIT;// 横屏： LANDSCAPE，竖屏：PORTRAIT
		
		/** SDK初始化，完成SDK的初始化 */
		IAppPay.init(Iapppay.this, screenType, appid);
		/** 订单信息 */
		String genUrlStr = "transid=" + URLEncoder.encode(tranid) + "&appid="
				+ URLEncoder.encode(appid);
		
		Log.i("PAY", "[Iappay] 开始支付：" + genUrlStr);
		/** 发起支付 */
		IAppPay.startPay(this, genUrlStr, new IPayResultCallback() {

			/** 支付回调 */
			@Override
			public void onPayResult(int resultCode, String signvalue,
					String resultInfo) {
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
				Log.i("PAY", "支付回调：" + json);
				// UnityPlayer.UnitySendMessage(UObjName, "ConveyJSON", json);
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
