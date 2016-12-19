package com.sdk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SignUtil {

	private SignUtil() {
	}

	public static final String concatMap(Map<String, String> params) {
		List<String> keysSet = new ArrayList<String>();
		keysSet.addAll(params.keySet());
		Collections.sort(keysSet);
		StringBuilder sb = new StringBuilder();
		for (String key : keysSet) {
			if (key.equals(Constant.PARAM_SIGN) || key.equals(Constant.PARAM_SIGN_TYPE)) {
				continue;
			}
			Object value = params.get(key);
			sb.append("&").append(key).append("=").append(value.toString());
		}

		if (sb.length() > 0 && sb.toString().startsWith("&")) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}

	public static final String getSignCode(Map map, String appSecret) {
		String queryString = concatMap(map);
		String signText = queryString + ":" + appSecret;
		System.out.println("signText=" + signText);
		String serverSignCode = MD5.sign(signText);
		return serverSignCode;
	}

	public static final String getSignCode(String queryString, String appSecret) {
		String signText = queryString + ":" + appSecret;
		String serverSignCode = MD5.sign(signText);
		return serverSignCode;
	}

	public static final String getQueryString(Map map) {
		return concatMap(map);
	}
}
