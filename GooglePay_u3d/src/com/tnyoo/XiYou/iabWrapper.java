package com.tnyoo.XiYou;

import java.util.List;

import com.unity3d.player.UnityPlayer;
import android.app.Activity;
import android.util.Log;
import android.content.Intent;
import com.tnyoo.XiYou.util.*;
import com.tnyoo.XiYou.util.IabHelper.IabAsyncInProgressException;
import com.tnyoo.appsflyer.AppsFlyerActivity;

/**
 * google支付工具类更新（使用最新发布的2016.4.12的版本），
 * 网址：https://github.com/googlesamples/android
 * -play-billing，工具类在TrivialDrive例子的utils包内。 原版备份在D:/TnyooProject/BackUp内
 * 
 * @author Administrator
 */
public class iabWrapper {
	private Activity mActivity;
	private IabHelper mHelper;
	private String mEventHandler;
	public boolean mDebugLog;
	public static final String TAG = "IabWrapper";

	public iabWrapper(String base64EncodedPublicKey, String strEventHandler) {// ,boolean
																				// enableDebugLog
		mDebugLog = true;
		mActivity = UnityPlayer.currentActivity;
		mEventHandler = strEventHandler;

		if (mHelper != null) {
			dispose();
		}
		// logDebug("iabWrapper ready to create IabHelper");
		mHelper = new IabHelper(mActivity, base64EncodedPublicKey);
		mHelper.enableDebugLogging(mDebugLog);// 是否开启调试日志

		// startSetup的参数为OnIabSetupFinishedListener，会在setup完成后被调用
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			@Override
			public void onIabSetupFinished(IabResult result) {
				logDebug("[IabSetupFinished callback] Iab Setup finished. "
						+ result);
				if (!result.isSuccess()) {// setup失败
					// 回呼 Unity GameObject 之函數 "msgReceiver", 並傳送字串訊息 (JSON 格式)
					UnityPlayer.UnitySendMessage(mEventHandler, "msgReceiver",
							"{\"code\":\"1\",\"ret\":\"false\",\"desc\":\""
									+ result.toString() + "\"}");
					dispose();
					return;
				}

				// setup成功
				// 回呼 Unity GameObject 之函數 "msgReceiver", 並傳送字串訊息 (JSON 格式)
				UnityPlayer.UnitySendMessage(
						mEventHandler,
						"msgReceiver",
						"{\"code\":\"1\",\"ret\":\"true\",\"desc\":\""
								+ result.toString() + "\"}");

				// register mHelper
				// 向 AppsFlyerActivity註冊onActivityResult回呼函數，將資料Relay給mHelper
				AppsFlyerActivity
						.registerOnActivityResultCBFunc(new AppsFlyerActivity.cbEvent() {
							// overrideActivity
							// .registerOnActivityResultCBFunc(new
							// overrideActivity.cbEvent() {
							@Override
							public boolean cbEvent(int requestCode,
									int resultCode, Intent data) {
								logDebug("[onActivityResult] (" + requestCode
										+ "," + resultCode + "," + data);
								// 通过overrideActivity.onActivityResult中的代理函数将Activity返回结果传给IbaHelper来处理
								if (mHelper.handleActivityResult(requestCode,
										resultCode, data)) {
									return true;
								} else {
									return false;
								}
							}
						});
				// IAB is fully set up. 检查本用户已拥有的，可消耗的物品
				logDebug("Setup successful. Querying inventory.");
				try {
					mHelper.queryInventoryAsync(mGotInventoryListener);
				} catch (IabAsyncInProgressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 处理掉对象，释放资源
	 */
	public void dispose() {
		if (mHelper != null) {
			logDebug("[Dispose] dispose mHelper");
			// 处理掉一切IabHelper支付相关的对象，不可再进行支付操作
			try {
				mHelper.dispose();
			} catch (IabAsyncInProgressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mHelper = null;
	}

	public void purchase(String strSKU, String reqCode, String payloadString) {
		try {
			int intVal = Integer.parseInt(reqCode);
			logDebug("start purchase");
			if (mHelper != null) {
				mHelper.launchPurchaseFlow(mActivity, strSKU, intVal,
						mPurchaseFinishedListener, payloadString);
			}
		} catch (IabAsyncInProgressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		@Override
		public void onIabPurchaseFinished(IabResult result, String dataStr,
				String sigStr) {
			logDebug("[PurchaseFinished Callback] Purchase finished. IabResult："
					+ result);
			// logDebug("[PurchaseFinished Callback] Purchase finished. dataStr："
			// + dataStr);
			// logDebug("[PurchaseFinished Callback] Purchase finished. sigStr："
			// + sigStr);
			// logDebug("[PurchaseFinished Callback] Purchase finished. skuDetails："
			// + skuDetails);
			if (result.isFailure()) {
				UnityPlayer
						.UnitySendMessage(mEventHandler, "msgReceiver",
								"{\"code\":\"2\",\"ret\":\"false\",\"desc\":\"\",\"sign\":\"\"}");
				return;
			} else {
				// UnityPlayer.UnitySendMessage(mEventHandler,
				// "msgReceiver","{\"code\":\"2\",\"ret\":\""+result+"\",\"desc\":\""+dataStr+"\",\"sign\":\""+sigStr+"\",\"skuDetails\":\""+skuDetails+"\"}";

				UnityPlayer.UnitySendMessage(mEventHandler, "SendSigData",
						sigStr);
				UnityPlayer
						.UnitySendMessage(mEventHandler, "SendData", dataStr);
				// UnityPlayer.UnitySendMessage(mEventHandler, "SendSkuDetails",
				// skuDetails);
			}
		}
	};

	/** 只有状态为已拥有的物品才能被消耗 **/
	public void consume(final String product_id) {
		try {
			mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
				@Override
				public void onQueryInventoryFinished(IabResult result,
						Inventory inventory) {
					logDebug("[GotInventoryFinished callback] Query inventory finished. "
							+ result
							+ "\n已拥有物品："
							+ inventory.allOwnedSkuString("inapp"));

					// Is it a failure?
					if (result.isFailure()) {
						logDebug("Failed to query inventory: " + result);
						return;
					}
					// Check for gas delivery -- if we own gas, we should fill up the tank immediately
		            Purchase gasPurchase = inventory.getPurchase(product_id);
//		            if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
		            if (gasPurchase != null) {
		                Log.d(TAG, "We have gas. Consuming it.");
		                try {
							mHelper.consumeAsync(inventory.getPurchase(product_id), mConsumeFinishedListener);
						} catch (IabAsyncInProgressException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                return;
		            }
		            
//					/*
//					 * Check for items we own. Notice that for each purchase, we
//					 * check the developer payload to see if it's correct! See
//					 * verifyDeveloperPayload().
//					 */
//					List<String> ownedProducts = inventory
//							.getAllOwnedSkus("inapp");
//					for (String id : ownedProducts) {
//						Purchase gasPurchase = inventory.getPurchase(id);
//						if (gasPurchase != null) {
//							// Log.d(TAG, "We have product " + id +
//							// ". Consuming it.");
//							try {
//								mHelper.consumeAsync(gasPurchase,
//										mConsumeFinishedListener);
//							} catch (IabAsyncInProgressException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							return;
//						}
//					}
				}
			});
		} catch (IabAsyncInProgressException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/** 只有状态为已拥有的物品才能被消耗 **/
	public void consume(String itemType, String jsonPurchaseInfo,
			String signature) {
		
		String transedJSON = jsonPurchaseInfo.replace('\'', '\"');
		if (mHelper == null)
			return;
		Purchase pp = null;
		try {
			pp = new Purchase(itemType, transedJSON, signature);
		} catch (Exception e) {
			pp = null;
		}
		logDebug("[Consume] jsonPurchaseInfo: " + pp);
		if (pp != null) {
			final Purchase currpp = pp;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						mHelper.consumeAsync(currpp, mConsumeFinishedListener);
					} catch (IabAsyncInProgressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		@Override
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			logDebug("[ConsumeFinished callback] Consume finished. " + result);
			if (result.isSuccess()) {
				UnityPlayer.UnitySendMessage(mEventHandler, "msgReceiver",
						"{\"code\":\"3\",\"ret\":\"true\",\"desc\":\""
								+ purchase.getOriginalJson()
										.replace('\"', '\'') + "\",\"sign\":\""
								+ purchase.getSignature() + "\"}");
			} else {
				UnityPlayer
						.UnitySendMessage(mEventHandler, "msgReceiver",
								"{\"code\":\"3\",\"ret\":\"false\",\"desc\":\"\",\"sign\":\"\"}");
			}
		}
	};

	// Listener that's called when we finish querying the items and
	// subscriptions we own
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		@Override
		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {
			logDebug("[GotInventoryFinished callback] Query inventory finished. "
					+ result
					+ "\n已拥有物品："
					+ inventory.allOwnedSkuString("inapp"));

			// Have we been disposed of（处理） in the meantime? If so, quit.
			if (mHelper == null)
				return;

			// Is it a failure?
			if (result.isFailure()) {
				logDebug("Failed to query inventory: " + result);
				return;
			}
			/*
			 * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */
			List<String> ownedProducts = inventory.getAllOwnedSkus("inapp");
			for (String id : ownedProducts) {
				Purchase gasPurchase = inventory.getPurchase(id);
				if (gasPurchase != null) {
					// Log.d(TAG, "We have product " + id + ". Consuming it.");
					try {
						mHelper.consumeAsync(gasPurchase,
								mConsumeFinishedListener);
					} catch (IabAsyncInProgressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
			}
		}
	};

	void logDebug(String msg) {
		if (mDebugLog)
			Log.d(TAG, msg);
	}
}