package com.tnyoo.webview;

import com.unity3d.player.UnityPlayerActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;

/**
 * 使用Eclipse大包时的测试类
 * @author Administrator
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void openWebViewPOSTTest(View view){
    	String postData = "AppId=83056997&UserID=tnyoo123&GameID=511&ServerID=1&SystemType=1&Version=49&Sign=68dd41de676dace9c476b11172b2b051"; 
    	openWebViewPOST("http://mapi.asiasoft.vn/dpay/showNotify", postData);
    }
    
    public void openWebViewGETTest(View view){
    	openWebViewGET("https://playmall.playpark.com/", "");
    }
    
	//Unity中会调用这个方法，从而开打WebView
	 @TargetApi(Build.VERSION_CODES.GINGERBREAD) 
	 public void openWebViewPOST(String url, String postData)
	 {
	         Intent intent = new Intent(this,WebViewActivity.class);
	         intent.putExtra("type", "POST");
	         intent.putExtra("url", url);
	         intent.putExtra("postData", postData);
	         this.startActivity(intent);
	 }
	 
		//Unity中会调用这个方法，从而开打WebView
	 @TargetApi(Build.VERSION_CODES.GINGERBREAD) 
	 public void openWebViewGET(String url, String getData)
	 {
	         Intent intent = new Intent(this,WebViewActivity.class);
	         intent.putExtra("type", "GET");
	         intent.putExtra("url", url);
	         intent.putExtra("getData", getData);
	         this.startActivity(intent);
	 }
}
