package com.tnyoo.webview;

import org.apache.http.util.EncodingUtils;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 给定url和要传输的数据，使用webkit浏览器打开url 进行网页支付的Activity
 * （越南VN版本的网页支付和缅甸MM版本的网页支付）
 * @author Administrator
 */
public class WebViewActivity extends Activity {
	public static final String LOG_TAG = "PAY";
	private WebView webView;
	private ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(getResourcesId("pay_web_view" ,"layout"));
		initWebView();

		Intent intent = this.getIntent();
		String type = intent.getStringExtra("type");
		String url = intent.getStringExtra("url");
		Log.i(LOG_TAG, "URL:" + url + "type: " + type);
		
		if(type.equalsIgnoreCase("POST")){
			
			String postData = intent.getStringExtra("postData");
			webView.postUrl(url, EncodingUtils.getBytes(postData, "utf-8"));// post方式传送参数
			
		} else if(type.equalsIgnoreCase("GET")){
			
			String getData = intent.getStringExtra("getData");
			if(getData == "" || getData == null)
				webView.loadUrl(url);
			else
				webView.loadUrl(url + "?" + getData);
		}
	}
	
	private void initWebView(){
		webView = (WebView) findViewById(getResourcesId("webview" ,"id"));
		webView.getSettings().setJavaScriptEnabled(true);
		webView.requestFocus();
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient());

		WebSettings webviewSettings = webView.getSettings();
		webviewSettings.setAppCacheEnabled(false);
		webviewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webviewSettings.setLoadWithOverviewMode(true);
	}
	
//  //手写的布局设置
//	private void initView() {
//
//		RelativeLayout.LayoutParams full = new RelativeLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.MATCH_PARENT);// 新建match_parent布局参数
//
//		RelativeLayout rootView = new RelativeLayout(this);// 新建帧布局FrameLayout作为根布局
//
//		webView = new WebView(this);// 新建视图 SurfaceView
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.requestFocus();
//		webView.setWebChromeClient(new WebChromeClient());
//		webView.setWebViewClient(new WebViewClient());
//
//		WebSettings webviewSettings = webView.getSettings();
//		webviewSettings.setAppCacheEnabled(false);
//		webviewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//		webviewSettings.setLoadWithOverviewMode(true);
//
//		rootView.addView(webView, full);
//		setContentView(rootView);// 设置Activity的布局为刚才设置好的帧布局
//	}

	/**
	 * 从resType资源目录获取给定resName的资源id
	 * @param resName 资源名
	 * @param resType 资源类型
	 * @return 资源id
	 */
	private int getResourcesId(String resName, String resType) {
		return getResources().getIdentifier(resName, resType, getPackageName());
	}

	/**
	 * 创建显示进度对话框
	 */
	private void showProgressDialog() {

		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(WebViewActivity.this, "",
					"Loading...", true);
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);

			progressDialog
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							finish();
						}
					});
		}

	}

	/**
	 * 关闭进度对话框
	 */
	private void hideProgressDialog() {

		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	private class WebViewClient extends android.webkit.WebViewClient {
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// 这里实现的目标是在网页中继续点开一个新链接，还是停留在当前程序中
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {//开始加载网页时显示进度框
			// TODO Auto-generated method stub just a second
			showProgressDialog();
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {//加载完成后关闭进度框
			// TODO Auto-generated method stub
			hideProgressDialog();
			super.onPageFinished(view, url);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 为ActionBar添加扩展菜单项
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(getResourcesId("pay_webview_actionbar" ,"menu"), menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 处理动作按钮的点击事件
		if(item.getItemId() == getResourcesId("payweb_action_close" ,"id")){//点击关闭按钮
			
			finish();  //关闭Activity
			return true;
		}else
			return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		hideProgressDialog();
		finish();
	}

}
