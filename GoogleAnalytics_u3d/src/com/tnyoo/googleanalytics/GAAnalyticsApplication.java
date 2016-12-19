package com.tnyoo.googleanalytics;

import java.util.HashMap;
import android.app.Application;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.Tracker;

/**
 * An extension to Application class to provide tracker for analytics purposes.
 * Having the tracker instances here allows all the activities to access the
 * same tracker instances. The trackers can be initialised on startup or when
 * they are required based on performance requirements.
 */
public class GAAnalyticsApplication extends Application {

	// The following line should be changed to include the correct property id.
	public static String PROPERTY_ID;// = "UA-70050428-1";
	private static final String TAG = "Google";
	public static GoogleAnalytics analytics;
	public static Tracker tracker;

	/**
	 * Enum used to identify the tracker that needs to be used for tracking.
	 * 
	 * A single tracker is usually enough for most purposes. In case you do need
	 * multiple trackers, storing them all in Application object helps ensure
	 * that they are created only once per application instance.
	 */
	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company.
						// eg:roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a
							// company.
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public GAAnalyticsApplication() {
		super();
	}

	// /*初始化
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "GAAnalyticsApplication onCreate: " + this);
		// 初始化google分析
		analytics = GoogleAnalytics.getInstance(this);
		analytics.getLogger().setLogLevel(LogLevel.VERBOSE);// 设置log详细程度
		analytics.setLocalDispatchPeriod(180);// 设置调度周期，以秒为单位，默认为1800s = 30m分钟
		// analytics.setDryRun(true);//设置不向Google发送任何信息
		Log.i(TAG, "GAAnalyticsApplication onCreate analytics: " + analytics);
	}

	public void initGoogleApp(String propertyId) {	
		PROPERTY_ID = propertyId;
		Log.i(TAG, "initGoogleApp: " + propertyId);

		// 初始化默认跟踪器
		tracker = getTracker(TrackerName.APP_TRACKER);
		// 展示广告功能
		tracker.enableAdvertisingIdCollection(true);
		// tracker.enableExceptionReporting(true);// 允许发送异常
		// tracker.enableAutoActivityTracking(true);//自动跟踪屏幕(要在xml配置文件为每个Activity指定屏幕名称)
		// tracker.enableAdvertisingIdCollection(true);// 展示广告功能
		//
		// //自动配置崩溃和异常，创建一个新的 ExceptionReporter 对象，并将其设置为新的默认未捕获异常处理程序
		// UncaughtExceptionHandler myHandler = new ExceptionReporter(
		// tracker, // Currently used Tracker.
		// Thread.getDefaultUncaughtExceptionHandler(), // Current default
		// uncaught exception handler.
		// this); // Context of the application.
		// // Make myHandler the new default uncaught exception handler.
		// Thread.setDefaultUncaughtExceptionHandler(myHandler);
	}

	synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {
			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
					.newTracker(R.xml.app_tracker)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
							.newTracker(R.xml.global_tracker) : analytics
							.newTracker(R.xml.ecommerce_tracker);
			// 展示广告功能
			t.enableAdvertisingIdCollection(true);
			mTrackers.put(trackerId, t);
		}
		return mTrackers.get(trackerId);
	}
	// */
}
