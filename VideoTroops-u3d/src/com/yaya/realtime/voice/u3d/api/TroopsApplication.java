package com.yaya.realtime.voice.u3d.api;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.yunva.video.sdk.YunvaVideoTroops;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

//棱镜渠道，考虑application的name参数问题，用下面代码.
//import  com.xinmei365.game.proxy.XMApplication;
//public class TroopsApplication extends XMApplication {

//百度渠道，需要继承百度application。
import com.baidu.gamesdk.BDGameApplication;
public class TroopsApplication extends BDGameApplication {

//快发渠道，需要继承快发application。
//import com.cmge.sdkkit.framework.mw.app.SDKBaseApplication;
//public class TroopsApplication extends SDKBaseApplication {

//非棱镜,百度渠道，直接用语音的application，
//import android.app.Application;
//public class TroopsApplication extends Application {
	
//GoogleAnalytics渠道，需要继承Google的application
//import com.tnyoo.googleanalytics.GAAnalyticsApplication;
//public class TroopsApplication extends GAAnalyticsApplication {

//虫虫支付渠道，需要继承CCPay的application
//import com.lion.ccpay.app.application.CCPayApplication;
//public class TroopsApplication extends CCPayApplication {

/**
 * 弃用
 * vivo渠道，由于jar文件过多，无法打包到同一个dex文件中，
 * 必须使application支持multidex，继承MultiDexApplication来解决.
 * 或者在application实现中加入下面attachBaseContext代码。
public class TroopsApplication extends MultiDexApplication {
 */
	

//一般渠道，没有自带Application的
//public class TroopsApplication extends Application {
	
//	@Override 弃用
//	protected void attachBaseContext(Context base) {
//		// TODO Auto-generated method stub
//		super.attachBaseContext(base);
//		MultiDex.install(this);
//	}

	@Override
	public void onCreate() {
		super.onCreate();
		queryUpdate();//利用反射检查热更新。
		String appId = getTroopsAppId(this);
		YunvaVideoTroops.initApplicationOnCreate(this, appId);
	}
	
	public String getTroopsAppId(Context context) {
		try {
			//获取Menifest.xml中的TroppsAppId参数的值
			ApplicationInfo applicationInfo = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			Object object = (Object)applicationInfo.metaData.get("TroopsAppId");
            return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** ---------乐变---------------- **/
	// 利用反射机制，查询更新
	protected void queryUpdate() {
		InvocationHandler myHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return null;
			}
		};
		try {
			Class<?> mCallback = Class
					.forName("com.excelliance.lbsdk.IQueryUpdateCallback");
			Object object = Proxy.newProxyInstance(this.getClassLoader(),
					new Class[] { mCallback }, myHandler);
			Class<?> clazz = Class.forName("com.excelliance.lbsdk.LebianSdk");
			Method m = clazz.getDeclaredMethod("queryUpdate", Context.class,
					mCallback, String.class);
			m.invoke(clazz, this, object, null);
			Log.d("MainActivity", "SDK Successful");
		} catch (Exception e) {
			Log.d("MainActivity", "no lebian sdk");
		}
	}
	/** ---------乐变---------------- **/
	
}
