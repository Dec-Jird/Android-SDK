package com.tnyoo.facebook;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.tnyoo.facebook.FacebookU3D;
import com.unity3d.player.UnityPlayerActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

public class PostResultActivity extends UnityPlayerActivity {// UnityPlayerActivity
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		/**--------------------- Facebook ----------------------**/
		if (!FacebookSdk.isInitialized()) {
			FacebookU3D.initFBSdk();
		}
		FacebookU3D.callbackManager.onActivityResult(requestCode, resultCode, data);
		Log.d(FacebookU3D.TAG,"onActivityResult. data: " + data.getDataString());
		/**--------------------- Facebook ----------------------**/
	}
	
	// 为了能持续追踪用户会话时间，必须复写下面方法
	@Override
	public void onResume() {
		super.onResume();
		//facebook 分析
		AppEventsLogger.activateApp(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		//facebook 分析
		AppEventsLogger.deactivateApp(this);
	}
}
