package com.nilecon.playmobilesdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
//import android.widget.Toast;

import com.asiasoft.playparksdk.framework.PlayparkSDK;
import com.nilecon.playmobilesdk.Callback.PlayMobileSDKInternal;

import com.praneat.playparksdk.internal.PPSConstants;
import com.praneat.playparksdk.internal.PlayparkSDKInternal;

import java.util.List;

/**
 * Created by NILECONTHAILAND on 27/8/2558. .
 */
public class PlayMobileSDK {

	private static final String TAG = "Playpark";

	// Check Login with PlayMobile
	public static boolean isLogin(Context context) {
		if (SharedPrefs.getInstance(context).getToken() != null
				&& SharedPrefs.getInstance(context).getUserId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public static void initPartnerCode(Context context, String partnerCode) {
		PlayparkSDK.initLogin(context, partnerCode);// initiate for
													// Authentication
	}

	public static void initWalletPayment(Context context, String merchantCode,
			String merchantKey) {
		PlayparkSDK.initWalletPayment(context, merchantCode, merchantKey);
	}

	public static void initPayment(Context context, String merchantCode,
			String merchantKey) {
		PlayparkSDK.initPayment(context, merchantCode, merchantKey);
	}

	public static void initPaymentAndWalletPayment(Context context,
			String paymentMerchantCode, String paymentMerchantKey,
			String walletMerchantCode, String walletMerchantKey) {
		PlayparkSDK.initPaymentAndWalletPayment(context, paymentMerchantCode,
				paymentMerchantKey, walletMerchantCode, walletMerchantKey);
	}

	public static void userSandbox(boolean isSandbox) {
		PlayparkSDK.useSandbox(isSandbox);
	}

	public static boolean isSandbox() {
		if (PlayparkSDK.isSandbox()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInit() {
		if (PlayparkSDK.isInitLogin()) {
			return true;
		} else {
			return false;
		}
	}

	public static int getLoginStatus(Context context) {
		int UNINITIALIZED = 0;
		int LOGGED_IN = 1;
		int LOGGED_OUT = 2;

		if (isInit()) {
			if (isLogin(context)) {
				return LOGGED_IN;
			} else {
				return LOGGED_OUT;
			}
		} else {
			return UNINITIALIZED;
		}
	}

	// Login
	public static void requestLoginPlaymobileWithPackageName(
			final Context context,
			String partnerCode,
			PPSConstants.DomainType domainType,
			String action,
			final PlayMobileSDKInternal.RequestLoginCallback requestLoginCallback) {

		Log.d(TAG, "JR requestLoginPlaymobileWithPackageName, context: "
				+ context);
		// Check Login with PlayparkSDK
		if (isLogin(context)) {
			Log.d(TAG, "JR PlayparkSDK isLogin(context)");
			// Check Login with PlayMobile
			if (SharedPrefs.getInstance(context).getPlayMobile()) {
				Log.v(TAG, "JR PlayparkSDK.gameRequestAutoLogin1 context: "
						+ context);
				PlayparkSDK.initLogin(context, "PLAYMOBILE");
				PlayparkSDK.gameRequestAutoLogin(context, partnerCode,
						new PlayparkSDKInternal.RequestLoginCallback() {

							@Override
							public void onSuccess(String userId, String token) {
								String message = "RequestLogin.onSuccess ==> userId: "
										+ userId + " ,token: " + token;
								Log.v(TAG, message);

								SharedPrefs.getInstance(context)
										.setToken(token);
								SharedPrefs.getInstance(context).setUserId(
										userId);

								requestLoginCallback.onSuccess(userId, token);
							}

							@Override
							public void onFailure(String message) {
								super.onFailure(message);
								String failureMessage = "RequestLogin.onFailure ==> message: "
										+ message;
								Log.v(TAG, failureMessage);
								requestLoginCallback.onFailure(failureMessage);
							}
						});

			} else {
				Log.d(TAG, "JR PlayparkSDK.requestAutoLogin2");
				PlayparkSDK.requestAutoLogin(context,
						new PlayparkSDKInternal.RequestLoginCallback() {
							@Override
							public void onSuccess(String userId, String token) {
								String message = "RequestLogin.onSuccess ==> userId: "
										+ userId + " ,token: " + token;
								Log.v(TAG, message);

								SharedPrefs.getInstance(context)
										.setToken(token);
								SharedPrefs.getInstance(context).setUserId(
										userId);

								requestLoginCallback.onSuccess(userId, token);
							}

							@Override
							public void onFailure(String message) {
								// Here is CMD-1002, onFailure receive json
								// string to display an error code &
								// description. (See on Appendix SDK Error Code)
								super.onFailure(message);
								String failureMessage = "RequestLogin.onFailure ==> message: "
										+ message;
								// Toast.makeText(context, failureMessage,
								// Toast.LENGTH_SHORT).show();
								Log.v(TAG, failureMessage);
								requestLoginCallback.onFailure(failureMessage);
							}
						});
			}
		} else {
			Log.d(TAG, "JR PlayparkSDK.gameRequest GUEST Login");
			// Check App PlayMobile
			if (domainType == PPSConstants.DomainType.GUEST) {
				PlayparkSDK.requestLogin(context,
						PPSConstants.DomainType.GUEST,
						new PlayparkSDKInternal.RequestLoginCallback() {
							@Override
							public void onSuccess(String userId, String token) {
								// Here is CMD-1002, use userId and token to
								// verify at CMD-1003
								String message = "RequestLogin.onSuccess ==> userId: "
										+ userId + " ,token: " + token;
								Log.v(TAG, message);

								SharedPrefs.getInstance(context).isPlayMobile(
										false);
								SharedPrefs.getInstance(context)
										.setToken(token);
								SharedPrefs.getInstance(context).setUserId(
										userId);
								requestLoginCallback.onSuccess(userId, token);
							}

							@Override
							public void onFailure(String message) {
								// Here is CMD-1002, onFailure receive json
								// string to display an error code &
								// description. (See on Appendix SDK Error Code)
								super.onFailure(message);
								String failureMessage = "RequestLogin.onFailure ==> message: "
										+ message;
								// Toast.makeText(context, failureMessage,
								// Toast.LENGTH_SHORT).show();
								Log.v(TAG, failureMessage);
								requestLoginCallback.onFailure(failureMessage);
							}
						});
			} else {
				Log.d(PPSConstants.LOG_TAG,
						"JR startApplication requestLogin action: " + action);
				if (startApplication(context, "com.asiasoft.playpark",
						partnerCode, action)) {
					Log.d(PPSConstants.LOG_TAG,
							"JR startApplication requestLogin Failed");
					requestLoginCallback.onPlayMobile(true, "Success");
				} else {
					// Here is CMD-1001, SDK provide the function to open the
					// Playpark Authen Gateway Url
					// to authenticate the player via WebView and get userId &
					// token for verifying with PlayPark API.
					Log.d(PPSConstants.LOG_TAG,
							"JR call PlayparkSDK.requestLogin");
					PlayparkSDK.requestLogin(context, domainType,
							new PlayparkSDKInternal.RequestLoginCallback() {
								@Override
								public void onSuccess(String userId,
										String token) {
									// Here is CMD-1002, use userId and token to
									// verify at CMD-1003
									String message = "RequestLogin.onSuccess ==> userId: "
											+ userId + " ,token: " + token;
									Log.v(TAG, message);

									SharedPrefs.getInstance(context)
											.isPlayMobile(false);
									SharedPrefs.getInstance(context).setToken(
											token);
									SharedPrefs.getInstance(context).setUserId(
											userId);
									requestLoginCallback.onSuccess(userId,
											token);
								}

								@Override
								public void onFailure(String message) {
									// Here is CMD-1002, onFailure receive json
									// string to display an error code &
									// description. (See on Appendix SDK Error
									// Code)
									super.onFailure(message);
									String failureMessage = "RequestLogin.onFailure ==> message: "
											+ message;
									// Toast.makeText(context, failureMessage,
									// Toast.LENGTH_SHORT).show();
									Log.v(TAG, failureMessage);
									requestLoginCallback
											.onFailure(failureMessage);
								}
							});
				}
			}
		}
	}

	public static void requestAutoLoginPlaymobileWithPackageName(
			final Context context,
			String partnerCode,
			final PlayMobileSDKInternal.RequestLoginCallback requestLoginCallback) {
		// Check Login with PlayparkSDK
		if (isLogin(context)) {
			// Check Login with PlayMobile
			if (SharedPrefs.getInstance(context).getPlayMobile()) {
				PlayparkSDK.initLogin(context, "PLAYMOBILE");
				PlayparkSDK.gameRequestAutoLogin(context, partnerCode,
						new PlayparkSDKInternal.RequestLoginCallback() {

							@Override
							public void onSuccess(String userId, String token) {
								String message = "RequestLogin.onSuccess ==> userId: "
										+ userId + " ,token: " + token;
								Log.v(TAG, message);

								SharedPrefs.getInstance(context)
										.setToken(token);
								SharedPrefs.getInstance(context).setUserId(
										userId);

								requestLoginCallback.onSuccess(userId, token);
							}

							@Override
							public void onFailure(String message) {
								super.onFailure(message);
								String failureMessage = "RequestLogin.onFailure ==> message: "
										+ message;
								requestLoginCallback.onFailure(failureMessage);
							}
						});

			} else {
				PlayparkSDK.requestAutoLogin(context,
						new PlayparkSDKInternal.RequestLoginCallback() {
							@Override
							public void onSuccess(String userId, String token) {
								String message = "RequestLogin.onSuccess ==> userId: "
										+ userId + " ,token: " + token;
								Log.v(TAG, message);

								SharedPrefs.getInstance(context)
										.setToken(token);
								SharedPrefs.getInstance(context).setUserId(
										userId);

								requestLoginCallback.onSuccess(userId, token);
							}

							@Override
							public void onFailure(String message) {
								// Here is CMD-1002, onFailure receive json
								// string to display an error code &
								// description. (See on Appendix SDK Error Code)
								super.onFailure(message);
								String failureMessage = "RequestLogin.onFailure ==> message: "
										+ message;
								// Toast.makeText(context, failureMessage,
								// Toast.LENGTH_SHORT).show();
								Log.v(TAG, failureMessage);
								requestLoginCallback.onFailure(failureMessage);
							}
						});
			}
		}
	}

	// Logout
	public static void requestLogoutPlaymobile(
			final Context context,
			final PlayMobileSDKInternal.RequestLogoutCallback requestLogoutCallback) {

		// Check Login with PlayMobile
		if (SharedPrefs.getInstance(context).getPlayMobile()) {
			PlayparkSDK.initLogin(context, "PLAYMOBILE");
		}

		PlayparkSDK.requestSwitchAccount(context,
				new PlayparkSDKInternal.RequestSwitchAccountCallback() {

					@Override
					public void onSuccess() {
						SharedPrefs.getInstance(context).clearPrefs();
						requestLogoutCallback.onSuccess("Logout");
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
						requestLogoutCallback.onFailure(message);
					}
				});
	}

	// Bind Account
	public static void requestBinderAccount(
			final Context context,
			final PlayMobileSDKInternal.RequestBinderAccountCallback requestBinderAccountCallback) {

		PlayparkSDK.requestBindAccount(context,
				new PlayparkSDKInternal.RequestLoginCallback() {
					@Override
					public void onSuccess(String s, String s1) {
						requestBinderAccountCallback.onSuccess(s, s1);
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
						requestBinderAccountCallback.onFailure(message);
					}
				});

	}

	public static void requestWebTopup(
			final Context context,
			PPSConstants.WebTopUpMode mode,
			final PlayMobileSDKInternal.RequestWebTopupCallback requestWebTopupCallback) {
		PlayparkSDK.requestWebTopUp(context, mode,
				new PlayparkSDKInternal.RequestWebTopUpCallback() {
					@Override
					public void onSuccess() {
						requestWebTopupCallback.onSuccess();
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
						requestWebTopupCallback.onFailure(message);
					}
				});
	}

	public static void requestWalletPayment(
			final Context context,
			String transactionId,
			String customerId,
			String itemName,
			String itemAmount,
			String itemCurrency,
			final PlayMobileSDKInternal.RequestWalletPaymentCallback requestWalletPaymentCallback) {
		PlayparkSDK.requestWalletPayment(context, transactionId, customerId,
				itemName, itemAmount, itemCurrency,
				new PlayparkSDKInternal.RequestWalletPaymentCallback() {
					@Override
					public void onSuccess(String token) {
						requestWalletPaymentCallback.onSuccess(token);
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
						requestWalletPaymentCallback.onFailure(message);
					}
				});
	}

	public static void requestPayment(
			final Context context,
			String transactionId,
			String customerId,
			final PlayMobileSDKInternal.RequestPaymentCallback requestPaymentCallback) {
		PlayparkSDK.requestPayment(context, transactionId, customerId,
				new PlayparkSDKInternal.RequestPaymentCallback() {
					@Override
					public void onSuccess(String token) {
						requestPaymentCallback.onSuccess(token);
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
						requestPaymentCallback.onFailure(message);
					}
				});
	}

	// Receive Parameter From Application Playmobile
	public static void getReceiveParameterWithIntent(Context context,
			PlayMobileSDKInternal.RequestReceiveCallback requestReceiveCallback) {
		Log.d(PPSConstants.LOG_TAG,
				"Call getReceiveParameterWithIntent() in PlayMobileSDK.java: "
						+ context);
		Bundle b = ((Activity) context).getIntent().getExtras();
		if (b != null) {
			Log.d(PPSConstants.LOG_TAG,
					"Bundle not null, bundle:" + b.describeContents());
			String userId = ((Activity) context).getIntent().getStringExtra(
					"userId");
			String token = ((Activity) context).getIntent().getStringExtra(
					"token");
			String apikey = ((Activity) context).getIntent().getStringExtra(
					"apikey");
			Log.d(PPSConstants.LOG_TAG, "From PlayMobile APP userID:" + userId
					+ " token : " + token + " apikey : " + apikey);
			
			if (token != null && userId != null && apikey != null) {
				Log.d(PPSConstants.LOG_TAG,
						"From PlayMobile APP userID: Before save SharedPrefs");
				SharedPrefs.getInstance(context).isPlayMobile(true);
				SharedPrefs.getInstance(context).setToken(token);
				SharedPrefs.getInstance(context).setUserId(userId);
				SharedPrefs.getInstance(context).setApiKey(apikey);
				Log.d(PPSConstants.LOG_TAG,
						"From PlayMobile APP userID: After save SharedPrefs");

				Log.d(PPSConstants.LOG_TAG,
						"From PlayMobile APP userID: Before PlayparkSDK.setAppkey");
				PlayparkSDK.setAppkey(SharedPrefs.getInstance(context)
						.getApiKey());
				Log.d(PPSConstants.LOG_TAG,
						"From PlayMobile APP userID: After PlayparkSDK.setAppkey");

				Log.d(PPSConstants.LOG_TAG,
						"From PlayMobile APP userID: Before clear bundle");
				((Activity) context).getIntent().removeExtra("userId");
				((Activity) context).getIntent().removeExtra("token");
				((Activity) context).getIntent().removeExtra("apikey");
				b.clear();
				Log.d(PPSConstants.LOG_TAG,
						"From PlayMobile APP userID: After clear bundle");

				Log.d(PPSConstants.LOG_TAG,
						"From PlayMobile APP userID: Before trigger");
				requestReceiveCallback.onSuccess(userId, token);
				Log.d(PPSConstants.LOG_TAG,
						"From PlayMobile APP userID: After trigger");
			}
		} else {
			Log.d(PPSConstants.LOG_TAG, "Bundle is null");
			requestReceiveCallback.onFailure("error ReceiveParameter");
		}
	}

	// Start Application Playmobile
	public static boolean startApplication(Context context,
			String application_name, String partnerCode, String action) {
		Log.d(TAG, "startApplication application_name: " + application_name
				+ ", context: " + context);
		try {
			Intent intent = new Intent("my.com.asiasoft.playpark");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			List<ResolveInfo> resolveinfo_list = context.getPackageManager()
					.queryIntentActivities(intent, 0);
			Log.d(TAG, "startApplication resolveinfo_list: " + resolveinfo_list);

			for (ResolveInfo info : resolveinfo_list) {
				if (info.activityInfo.packageName
						.equalsIgnoreCase(application_name)) {
					launchComponent(context, info.activityInfo.packageName,
							info.activityInfo.name, partnerCode, action);
					Log.d(TAG, "startApplication Success");
					return true;
				}
			}
		} catch (ActivityNotFoundException e) {
			Toast.makeText(
					context.getApplicationContext(),
					"There was a problem loading the application: "
							+ application_name, Toast.LENGTH_SHORT).show();
			Log.e(TAG, "There was a problem loading the application: "
					+ application_name);
			return false;
		}
		return false;
	}

	private static void launchComponent(Context context, String packageName,
			String name, String partnerCode, String action) {
		Intent launch_intent = new Intent("my.com.asiasoft.playpark");
		launch_intent.addCategory("android.intent.category.DEFAULT");
		launch_intent.setComponent(new ComponentName(packageName, name));
		launch_intent.setAction(action);
		launch_intent.putExtra("packagename", context.getApplicationContext()
				.getPackageName());
		launch_intent.putExtra("partnerCode", partnerCode);
		launch_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(launch_intent);
	}

}
