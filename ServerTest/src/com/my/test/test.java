package com.my.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import com.sdk.util.Constant;
import com.sdk.util.HttpClientSsl;
import com.sdk.util.SignUtil;

public class test {

	public static void main(String[] args) {
		Date date = new Date();// 2013-1-31 22:17:14
		String dateStr = "2016-06-02 14:24";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		
		String sessionId = "eyJ2IjozLCJnIjpmYWxzZSwidSI6IjEyODQwMjI2NyIsInQiOjE0Nzk4MDIxNzcwNTUsInMiOiJucyIsInIiOiJUZjZGcXBVQ2pKUzlRNjFqa3lMNCIsImEiOiJGNjhFQkZDQkE1NDczODlGMzg4MzBBMTM4NjVGNDE1NSIsImkiOiIxODAuMTMwLjEwLjE1OCJ9";
		String uid = "128402267";
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("app_id",Constant.APP_ID+"");
		params.put("uid", uid);
		params.put("session_id", sessionId);
		params.put("ts", System.currentTimeMillis() + "");
		
		String sign = SignUtil.getSignCode(params, Constant.APP_SECRET);
		
		params.put("sign_type", "md5");
		params.put("sign", sign);
		
		System.out.println("参数："+params);
		
		System.out.println("时间戳："+System.currentTimeMillis());
		
		String delphiStr = "app_id=2841864&uid=128402267&session_id=eyJ2IjozLCJnIjpmYWxzZSwidSI6IjEyODQwMjI2NyIsInQiOjE0Nzk4MDIxNzcwNTUsInMiOiJucyIsInIiOiJUZjZGcXBVQ2pKUzlRNjFqa3lMNCIsImEiOiJGNjhFQkZDQkE1NDczODlGMzg4MzBBMTM4NjVGNDE1NSIsImkiOiIxODAuMTMwLjEwLjE1OCJ9&ts=1479863775379:uWeTtqK66IudkSMVHl4qdnP568YQdrW8";
		System.out.println("MD5："+SignUtil.getSignCode(delphiStr, Constant.APP_SECRET));
	}

}
