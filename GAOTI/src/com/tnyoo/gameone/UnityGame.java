package com.tnyoo.gameone;

import generalClass.GameInfo;
import generalClass.GameSDK;
import generalClass.Layout;
import googlePlayCustom.GoogleBillingPayment;

import java.util.HashMap;

import org.json.JSONException;

import unity_plugin.FacebookInvitePlugin;
import unity_plugin.FacebookSharePlugin;
import unity_plugin.GoogleBillingPlugin;
import unity_plugin.IPlugin;
import LinkedList.GoogleProduct;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class UnityGame {
	// =============IPlugin==========================
	private GameSDK gameSDK = GameSDK.getInstance();
	private Layout layout = new Layout();
	private Context context;
	// =============IPlugin==========================
	// ================GoogleBillingPlugin===========
	private Activity activity;
	private GoogleBillingPayment googleBillingPayment;
	private GameInfo gameInfo;
	private GoogleProduct googleProduct;
	// ================GooglePlayBilling Related====
	private String base64EncodedPublicKey;

	// ================GoogleBillingPlugin===========
	//================FB Activity===================
	//private MGOFacebook mgoFacebook = new MGOFacebook() ;
	//===============FB Activity===================

	public void setContext(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return this.context;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Activity getActivity() {
		return this.activity;
	}

	public void setGoogleBillingPayment(
			GoogleBillingPayment googleBillingPayment) {
		this.googleBillingPayment = googleBillingPayment;
	}

	public GoogleBillingPayment getGoogleBillingPayment() {
		return this.googleBillingPayment;
	}

	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	public GameInfo getGameInfo() {
		return this.gameInfo;
	}

	public void setGoogleProduct(GoogleProduct googleProduct) {
		this.googleProduct = googleProduct;
	}

	public GoogleProduct getGoogleProduct() {
		return this.googleProduct;
	}

	public void setBase64EncodedPublicKey(String base64EncodedPublicKey) {
		this.base64EncodedPublicKey = base64EncodedPublicKey;
	}

	public String getBase64EncodedPublicKey() {
		return this.base64EncodedPublicKey;
	}

	private void initParameter() {
		gameSDK.setContext(this.getContext());

		gameSDK.setLayout(layout);
		gameSDK.setRedirect(gameSDK);
		layout.setGameSDK(gameSDK);
	}

	private void initParameter(IPlugin plugin) {
		gameSDK.setContext(this.getContext());

		gameSDK.setLayout(layout);
		gameSDK.setRedirect(gameSDK);
		gameSDK.setIPlugin(plugin);
		layout.setGameSDK(gameSDK);

	}

	public void initGoogleBillingPlugin(GoogleBillingPlugin googleBillingPlugin) {
		this.setGoogleBillingPayment(new GoogleBillingPayment());

		// ===========GameSDK related============
		gameSDK.setContext(this.getContext());
		gameSDK.setActivity(this.getActivity());
		gameSDK.setGoogleBillingPayment(this.getGoogleBillingPayment());
		gameSDK.setGameInfo(this.getGameInfo());
		// ===========GameSDK related============
		// ===========setProductList============
		this.getGoogleBillingPayment().setBase64EncodedPublicKey(
				this.getBase64EncodedPublicKey());
		this.getGoogleBillingPayment()
				.setGoogleProduct(this.getGoogleProduct());
		// ===========GoogleBilling related============
		googleBillingPayment.setGameSDK(gameSDK);
		googleBillingPayment.setGoogleBillingPlugin(googleBillingPlugin);
		// ===========GoogleBilling related============
	}

	public void start(IPlugin plugin) {
		/*
		 * //changeAcc 
		 * this.initParameter(plugin) ; 
		 * try {
		 * 	gameSDK.getRedirect().redirectToNormalActivity(
		 * Class.forName("com.mgameone.loginsdk.LoginActivity")); } catch
		 * (ClassNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		// Direct Inside to the game
		this.initParameter(plugin);
		gameSDK.authToken();

	}

	public void changeAcc(IPlugin plugin) {
		// changeAcc
		this.initParameter(plugin);
		try {
			gameSDK.getRedirect().redirectToNormalActivity(
					Class.forName("com.mgameone.loginsdk.LoginActivity"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		}
	}

	public void startGoogleBilling(GoogleBillingPlugin googleBillingPlugin) {
		this.initGoogleBillingPlugin(googleBillingPlugin);
		try {
			this.getGoogleBillingPayment().setPayment();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startGoogleBillingBuyItem(
			GoogleBillingPlugin googleBillingPlugin) {
		this.initGoogleBillingPlugin(googleBillingPlugin);
		Log.d("GPProductList", "getInside");
		googleBillingPayment.startPurchase();
	}

	//==============Init Facebook SDK==================
	public void initFBSDK () {
		gameSDK.getMGOFacebook().initFBSdk((Activity) this.getContext());
	}

	// ==============Facebook Share=====================

	public void setFacebookSharePlugin (FacebookSharePlugin facebookSharePlugin) {
		gameSDK.getMGOFacebook().setFacebookSharePlugin(facebookSharePlugin);
	}

	public void facebookShare(Activity activity , HashMap<String, String> shareContent) {
		gameSDK.getMGOFacebook().share(activity, shareContent);
	}


	//==============Facebook Share=====================
	//=============Facebook Invite=====================
	public void setFacebookInvitePlugin(FacebookInvitePlugin facebookInvitePlugin){
		gameSDK.getMGOFacebook().setFacebookInvitePlugin(facebookInvitePlugin);
	}

	public void facebookInvite(Activity activity,HashMap<String,String> inviteContent) {
		gameSDK.getMGOFacebook().inviteFriends(activity, inviteContent) ;
	}

	//===========fb on activityResult===================
	public boolean onFacebookActivityResult (int requestCode, int resultCode,Intent data, Activity activity) {

		return gameSDK.getMGOFacebook().onShareActivityResult(requestCode, resultCode,data, activity);

	}
	//=========Url Schmea for platform Statistic================================
	public void startStatistic (Intent intent) {
		Uri data = intent.getData();

		gameSDK.setContext(this.getContext());

		if (data != null) {
			gameSDK.getStatistic().parseQueryString(intent) ;
		}else {
			this.callPlatformStat();
		}

	}

	public void callPlatformStat () {
		HashMap<String, String> postParam = new HashMap<String, String>();
		this.initParameter();
		gameSDK.isSettingEnable(postParam);
	}


}
