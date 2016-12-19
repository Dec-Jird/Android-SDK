package com.sdk.servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.sdk.util.Constant;
import com.sdk.util.SignUtil;

/**
 * 游戏客户端请求游戏服务器下单，把订单信息返回给游戏客户端（调用魅族支付SDK时需要用到这些数据）
 * @author liaopeng
 *
 */
@WebServlet("/order/buy")
public class OrderServlet extends HttpServlet {

	private static final long serialVersionUID = 8043322603992380738L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			
			resp.setContentType("text/plain;charset=UTF-8");
			String productId = req.getParameter("product_id");
			//此处可能是从数据库商城表查到对应商品的详情（不同CP做法不一样）
			Map<String, String> product = getProduct(productId);
			
			//生成一个CP唯一的订单号
			String cpOrderId = System.currentTimeMillis() + "";
			
			//uid：魅族uid，下单时是知道游戏玩家相关信息的，从玩家相关信息里获取对应玩家的账号在魅族的uid
			String uid = req.getParameter("uid");
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("app_id", Constant.APP_ID + "");
			params.put("cp_order_id", cpOrderId);
			params.put("create_time", System.currentTimeMillis() + "");
			//当pay_type=0时，应当定义：product_subject="购买"+buy_amount+product_unit+产品名称
			//当pay_type=1 时， total_price不参与签名 ，用户在充值过程中可以修改金额 ，用户在充值过程中可以修改金额 ，用户在充值过程中可以修改金额 ，
			//用户在充值过程中可以修改金额 ，用户在充值过程中可以修改金额 ，用户在充值过程中可以修改金额 ，用户在充值过程中可以修改金额 ，product_subject=“充值”+游戏名称
			params.put("pay_type", String.valueOf(0));
			
			//一般如果是充值钻石（元宝等等）则单价自行写1就好，单位自行决定
			params.put("product_id", product.get("productId"));//商品编号
			params.put("product_subject", product.get("name"));//商品名称
			params.put("product_body", product.get("description"));//商品描述
			params.put("buy_amount", product.get("amount"));
			params.put("product_per_price", product.get("perPrice"));//单价
			params.put("product_unit", "个");//单位
			params.put("total_price", product.get("totalPrice"));//总金额
			
			params.put("uid", uid);//魅族UID
			params.put("user_info", "");//自定义信息（透传信息，支付成功后，魅族SDK服务器回调时会原封返回）
			
			//订单信息组装好后，CP开发看看是否把该订单存库里，当魅族SDK服务器支付成功回调时，可以根据订单CP订单号查询改订单，进行商品发放
			save(params);
			
			String signString = SignUtil.concatMap(params);
			//这个地方打印出来的目的是为了客户端拿到服务器返回的参数请求魅族SDK服务器支付，如果客户端错误日志提示：参数签名不正确，请核对签名串
			System.out.println("请核对签名串：" + signString);
			String sign = SignUtil.getSignCode(signString, Constant.APP_SECRET);
			
			params.put("sign_type", "md5");
			params.put("sign", sign);
			
			JSONObject json = new JSONObject();
			json.put("code", 200);
			json.put("value", params);
			resp.getWriter().write(json.toJSONString());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			resp.getWriter().flush();
			resp.getWriter().close();
		}
	}

	private Map<String, String> getProduct(String productId) {
		Map<String, String> product = new HashMap<String, String>();
		product.put("productId", String.valueOf(1001));
		product.put("name", "购买10个钻石");
		product.put("description", "10个钻石");
		product.put("amount", String.valueOf(10));
		product.put("perPrice", String.valueOf(1));
		product.put("totalPrice", String.valueOf(1));
		return product;
	}
	
	
	private void save(Map<String, String> params) {
		
	}
}
