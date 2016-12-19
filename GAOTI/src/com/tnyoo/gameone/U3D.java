package com.tnyoo.gameone;

import generalClass.GameInfo;
import generalClass.GameSDK;
import generalClass.MResource;
import googlePlayBilling.Inventory;
import googlePlayBilling.Purchase;
import googlePlayBilling.SkuDetails;
import googlePlayCustom.GoogleBillingPayment;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;
import unity_plugin.FacebookInvitePlugin;
import unity_plugin.FacebookSharePlugin;
import unity_plugin.GoogleBillingPlugin;
import unity_plugin.IPlugin;
import LinkedList.GoogleProduct;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.widget.AppInviteDialog;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import de.greenrobot.event.EventBus;

public class U3D extends UnityPlayerActivity {

	UnityGame unityGame = new UnityGame();
	GameSDK gameSDK = GameSDK.getInstance();
	GoogleBillingPayment googleBillingPayment = new GoogleBillingPayment();
	GameInfo gameInfo = new GameInfo();
	private String gameObj = "";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// U3D mInstance = this;
		//
		// //ADD MULTIDEX.INSTALL(THIS) SOLVED MY SIMILAR PROBLEM
		// MultiDex.install(this);
		CalligraphyConfig.initDefault(new Builder()
				.setDefaultFontPath("fonts/custom_font.ttf")
				.setFontAttrId(MResource.getIdByName(this, "attr", "fontPath"))
				.build());
		unityGame.setContext(U3D.this);
		unityGame.startStatistic(getIntent());
		unityGame.initFBSDK();
		if (EventBus.getDefault().isRegistered(this))
			EventBus.getDefault().unregister(this);
		EventBus.getDefault().register(this, "onInitEvent");
	}

	public void GameOneInit(String gameObject) {
		this.gameObj = gameObject;

	}

	public void GameOneLogin() {

		InitEvent event = new InitEvent();
		event.setType("login");

		EventBus.getDefault().post(event);
	}

	public void GameOneChangeUser() {

		InitEvent event = new InitEvent();
		event.setType("change");

		EventBus.getDefault().post(event);
	}

	public void GameOnePay(String PayUserId, String PayServerId,
			String PayItemId, String PayOrderId, String PayPubKey) {

		InitEvent event = new InitEvent();
		event.setType("pay");
		event.setRoleId(PayUserId);// 用户id
		event.setServer(PayServerId);// 服务器id
		event.setPayItemId(PayItemId);// 商品id
		event.setPayOrderId(PayOrderId);// 订单号
		event.setPayPubKey(PayPubKey);// google公钥
		EventBus.getDefault().post(event);
	}

	public void onInitEventMainThread(InitEvent Event) {
		String str = "";
		str = Event.getType();

		if (str == "login")
			sdkDoLoign();
		else if (str == "pay")
			doPay(Event);
		else if (str == "change")
			ChangeUser();
	}

	// 登录
	public void sdkDoLoign() {
		unityGame.setContext(U3D.this);
		unityGame.start(new plugin());
	}

	// 切换账号
	public void ChangeUser() {
		unityGame.setContext(U3D.this);
		unityGame.changeAcc(new plugin());
	}

	public class plugin implements IPlugin {

		@Override
		public void onResult(String mgoUid, String mgoAcc, String source,
				String token, Context context) {

			JSONObject obj = new JSONObject();
			try {
				obj.put("flag", 0);
				obj.put("MgoUid", mgoUid);
				// obj.put("MgoAcc", mgoAcc);
				// obj.put("source", source);
				obj.put("token", token);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			String JsonStr = obj.toString();
			UnityPlayer.UnitySendMessage(U3D.this.gameObj, "loginNotify",
					JsonStr);
		}

		@Override
		public void onResult() {
			// TODO Auto-generated method stub
			// .d("DEBUG_MSG" , "onBackPressed in OnResult") ;
			// finish() ;

		}

	}

	// setTransactionNumber 交易编号，setId 产品id.
	// 支付
	public void doPay(InitEvent Event) {

		GoogleProduct googleProduct = new GoogleProduct();

		googleProduct.setId(new String[] { Event.getPayItemId() });

		googleProduct.setGamepoint(0);

		googleProduct.setTransactionNumber(Event.getPayOrderId());

		googleProduct.setType("purchase");

		this.setUnityGame(googleProduct, Event.getRoleId(), Event.getServer(),
				Event.getPayPubKey());
	}

	public void setUnityGame(GoogleProduct googleProduct, String roleId,
			String serverId, String pubKey) {
		GameInfo gameInfo = new GameInfo();
        
		gameInfo.setRoleId(roleId);
		gameInfo.setServerId(serverId);

		unityGame = new UnityGame();

		unityGame.setGoogleBillingPayment(googleBillingPayment);

		unityGame.setContext(this);
		unityGame.setActivity(this);

		unityGame.setBase64EncodedPublicKey(pubKey);// google支付公钥。

		unityGame.setGoogleProduct(googleProduct);

		unityGame.setGameInfo(gameInfo);

		unityGame.startGoogleBilling(new googleBillingPlugin());

	}

	// // 获得金额
	// public void getIapPricePlugin() {
	// GoogleProduct googleProduct = new GoogleProduct();
	// googleProduct.setId(new String[] { "test_unmanaged_product11" });
	// googleProduct.setType("getLocalPrice");
	//
	// this.setUnityGame(googleProduct);
	//
	// }

	// 支付回调
	public class googleBillingPlugin implements GoogleBillingPlugin {

		@Override
		public void getInventory(Inventory inventory) {
			SkuDetails skuDetails;

			skuDetails = inventory.getSkuDetails("test_unmanaged_product11");

			Log.d("GP_getPrice", "local price : " + skuDetails.getPrice());

		}

		// Google处理失败
		@Override
		public String buyItem(int status, String message, String paymentType,
				GameSDK gameSDK) {
			Log.d("ReturnBuyItem", "status: " + status + "message : " + message
					+ " platform : " + paymentType);

			return null;
		}

		// Gameone支付回调
		@Override
		public String buyItem(int status, String message, String paymentType,
				Purchase purchase, GameSDK gameSDK) {
			Log.d("GPProductList", "status: " + status + "message : " + message
					+ " platform : " + paymentType);
			Log.d("GProductList", "ProductId : " + gameSDK.getProductId());
			Log.d("GPProductList", "gamepoint" + gameSDK.getGamepoint());
			if (paymentType.equals("Mgo")) {
				switch (status) {
				case 1:
					// 支付成功

					break;
				case -1:
					// google playment has error

					break;

				default:
					// the other error

					break;

				}
			}

			return null;
		}

	}

	// facebook统计。
	@Override
	protected void onResume() {
		super.onResume();
		// AppEventsLogger.activateApp(this, "1634342930111968");
		AppEventsLogger.activateApp(this);
	}

	// facebook分享
	public void FBShare() {
		HashMap<String, String> shareContent = new HashMap<String, String>();
		U3D.this.unityGame.setFacebookSharePlugin(new FacebookSharePlugin() {
			public void onSuccess(Sharer.Result result) {
				Log.d("DEBUG_MSG", "onSuccess from GameActivity");
				Log.e("facebook_share",result.toString());
			}
			public void onCancel() {
				Log.d("DEBUG_MSG", "onCancel from GameActivity");
			}

			public void onError(FacebookException e) {
				Log.d("DEBUG_MSG", "FacebookException : " + e.toString());
			}
		});
		U3D.this.unityGame.facebookShare(U3D.this, shareContent);
	}

	// 邀请facebook好友
	public void FBInvite() {
		HashMap<String, String> inviteContent = new HashMap<String, String>();
		U3D.this.unityGame.setFacebookInvitePlugin(new FacebookInvitePlugin() {
			public void onSuccess(AppInviteDialog.Result result) {
				Log.d("DEBUG_MSG", "App Invite Success");
				Log.e("facebook_invite",result.toString());
			}

			public void onCancel() {
				Log.d("DEBUG_MSG", "onCancel from GameActivity");
			}

			public void onError(FacebookException e) {
				Log.d("DEBUG_MSG", "FacebookException : " + e.toString());
			}
		});

		U3D.this.unityGame.facebookInvite(U3D.this, inviteContent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("DEBUG_MSG", "onActivityResult(" + requestCode + "," + resultCode
				+ "," + data);

		// Pass on the activity result to the helper for handling
		// ==============FB OnActivityResult ==========================

		// ==============FB OnActivityResult ==========================
		unityGame.onFacebookActivityResult(requestCode, resultCode, data, this);

		if (unityGame.onFacebookActivityResult(requestCode, resultCode, data,
				this)) {
			Log.i("DEBUG_MSG", "Handle by fb");
		} else if (!unityGame.getGoogleBillingPayment().getIabHelper()
				.handleActivityResult(requestCode, resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.i("DEBUG_MSG", "onActivityResult handled by IABUtil.");
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (googleBillingPayment.getIabHelper() != null)
			googleBillingPayment.getIabHelper().dispose();
		googleBillingPayment.setIabHelper(null);
	}

	@Override
	public void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		// Log.d("DEBUG_MSG" , "onNewIntent") ;
		// unityGame.startStatistic(intent);//会产生一直回调的现象。

		// ...do what you need with the parameters
	}

	@Override
	protected void onStart() {
		super.onStart();
		// this.initParameter()

	}

	public void onBackPressed() {

		// super.onBackPressed();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
