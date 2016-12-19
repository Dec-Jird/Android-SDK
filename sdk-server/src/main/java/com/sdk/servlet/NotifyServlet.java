package com.sdk.servlet;
import java.io.IOException;
import java.util.Enumeration;
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

@WebServlet("/order/notify")
public class NotifyServlet extends HttpServlet {

	private static final long serialVersionUID = 9099908500632577204L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Map<String, String> postParam = new HashMap<String, String>();
		
		//建议通过这种形式来获取所有参数，因为以后回调可能会加其他参数，也参与签名，到时如果我们加了其他参数签名，那么你们就验签失败了
		Enumeration<?> enums = req.getParameterNames();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement().toString();
			postParam.put(key, req.getParameter(key));
		}
		//一般在这个地方打印出回调过来的所有参数
		
		String serverSign = SignUtil.getSignCode(postParam, Constant.APP_SECRET);
		
		String sign = req.getParameter("sign");
		String tradeStatus = req.getParameter("trade_status");
		//1.签名验证通过并trade_status等于3
		if(serverSign.equals(sign) && "3".equals(tradeStatus)) {
			//2.则查询订单详情，看该订单详情是否没充值成功，没则修改订单状态为成功状态，发货；否则忽略
			//3.处理成功，或者订单已经成功，则回复
			JSONObject responseJson = new JSONObject();
			responseJson.put("code", 200);
			resp.getWriter().write(responseJson.toJSONString());
		}
	}

}
