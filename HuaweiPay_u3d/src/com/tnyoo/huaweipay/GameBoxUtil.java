package com.tnyoo.huaweipay;

import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import com.android.huawei.pay.plugin.IHuaweiPay;
import com.android.huawei.pay.plugin.IPayHandler;
import com.android.huawei.pay.plugin.MobileSecurePayHelper;
import com.android.huawei.pay.util.HuaweiPayUtil;
import com.android.huawei.pay.util.Rsa;
import com.huawei.gamebox.buoy.sdk.util.DebugConfig;

/**
 * 公共类，提供加载插件和支付接口
 * 
 * @author h00193325
 */
public class GameBoxUtil {

	// 日志标签
	public static final String TAG = "Huawei";

 /*
	// 当没有服务器时用：由客户端对订单进行签名。
	public static String getSign(final String price, final String productName,
			final String productDesc, final String requestId) {
		Map<String, String> params = new HashMap<String, String>();
		// 必填字段，不能为null或者""，请填写从联盟获取的支付ID
		params.put(GlobalParam.PayParams.USER_ID, GlobalParam.PAY_ID);
		// 必填字段，不能为null或者""，请填写从联盟获取的应用ID
		params.put(GlobalParam.PayParams.APPLICATION_ID, GlobalParam.APP_ID);
		// 必填字段，不能为null或者""，单位是元，精确到小数点后两位，如1.00
		params.put(GlobalParam.PayParams.AMOUNT, price);
		// 必填字段，不能为null或者""，道具名称
		params.put(GlobalParam.PayParams.PRODUCT_NAME, productName);
		// 必填字段，不能为null或者""，道具描述
		params.put(GlobalParam.PayParams.PRODUCT_DESC, productDesc);
		// 必填字段，不能为null或者""，最长30字节，不能重复，否则订单会失败
		params.put(GlobalParam.PayParams.REQUEST_ID, requestId);

		String noSign = HuaweiPayUtil.getSignData(params);
		DebugConfig.d(TAG, "签名参数noSign：" + noSign);
		// CP必须把参数传递到服务端，在服务端进行签名，然后把sign传递下来使用；服务端签名的代码和客户端一致
		String sign = Rsa.sign(noSign, GlobalParam.PAY_RSA_PRIVATE);
		DebugConfig.d(TAG, "签名后sign： " + sign);
		
		return sign;
	}
// */

	/**
	 * 支付方法，实现参数签名与调起支付服务
	 * @param activity
	 * @param price
	 * @param productName
	 * @param productDesc
	 * @param requestId 订单号
	 * @param extReserved
	 * @param sign
	 * @param handler
	 * @return 
	 */
	public static void pay(final Activity activity, final String price,
			final String productName, final String productDesc, final String requestId, 
			final String extReserved, final String sign, final IPayHandler handler) {
//		String sign1 = getSign(price, productName, productDesc, requestId);// 在客户端签名时用

		Map<String, Object> payInfo = new HashMap<String, Object>();
		// 必填字段，不能为null或者""
		payInfo.put(GlobalParam.PayParams.AMOUNT, price);
		// 必填字段，不能为null或者""
		payInfo.put(GlobalParam.PayParams.PRODUCT_NAME, productName);
		// 必填字段，不能为null或者""
		payInfo.put(GlobalParam.PayParams.REQUEST_ID, requestId);
		// 必填字段，不能为null或者""
		payInfo.put(GlobalParam.PayParams.PRODUCT_DESC, productDesc);
		// 必填字段，不能为null或者""，请填写自己的公司名称
		payInfo.put(GlobalParam.PayParams.USER_NAME, GlobalParam.USER_NAME);// 这里要从服务器获取USER_NAME
		// 必填字段，不能为null或者""
		payInfo.put(GlobalParam.PayParams.APPLICATION_ID, GlobalParam.APP_ID);
		// 必填字段，不能为null或者""
		payInfo.put(GlobalParam.PayParams.USER_ID, GlobalParam.PAY_ID);// //这里要从服务器获取USER_ID
		// 必填字段，不能为null或者""
		payInfo.put(GlobalParam.PayParams.SIGN, sign);

		// 必填字段，不能为null或者""，此处写死X6
		payInfo.put(GlobalParam.PayParams.SERVICE_CATALOG, "X6");

		// 调试期可打开日志，发布时注释掉
		payInfo.put(GlobalParam.PayParams.SHOW_LOG, GlobalParam.SHOW_DEBUG_LOG);

		// 设置支付界面横竖屏，默认竖屏
		payInfo.put(GlobalParam.PayParams.SCREENT_ORIENT, GlobalParam.PAY_ORI);

		payInfo.put(GlobalParam.PayParams.EXT_RESERVED, extReserved);
		
		DebugConfig.d(TAG, "pay 支付请求参数 : " + payInfo.toString());

		IHuaweiPay payHelper = new MobileSecurePayHelper();
		/**
		 * 开始支付
		 */
		boolean paystate = payHelper.startPay(activity, payInfo, handler);
		DebugConfig.d(TAG, "pay paystate : " + paystate);
	}

	/**
	 * 支付方法，实现参数签名与调起支付服务
	 * @param activity
	 * @param price
	 * @param productName
	 * @param productDesc
	 * @param requestId
	 * @param extReserved
	 * @param sign
	 * @param handler
	 */
//	public static void startPay(final Activity activity, final String price,
//			final String productName, final String productDesc, final String requestId, 
//			final String extReserved, final String sign, final IPayHandler handler) {
//		// 尚未获取支付的私钥，需要从服务端获取并保存到内存中
//		if ("".equals(sign)) {
//			DebugConfig.e(TAG, "GameBoxUtil 签名为空！！！");
//			return;
//		} else {// 如果已经获取到签名，则直接调起支付
//			ReqTask getPayPrivate = new ReqTask(new Delegate() {
//
//				@Override
//				public void execute(String privateKey) {
//					pay(activity, price, productName, productDesc, requestId,
//							extReserved, sign, handler);
//				}
//
//			});
//			getPayPrivate.execute();
//			// DebugConfig.d(TAG, "GameBoxUtil 销毁对象");
//			getPayPrivate = null;
//		}
//	}

}
