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
		
		//����ͨ��������ʽ����ȡ���в�������Ϊ�Ժ�ص����ܻ������������Ҳ����ǩ������ʱ������Ǽ�����������ǩ������ô���Ǿ���ǩʧ����
		Enumeration<?> enums = req.getParameterNames();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement().toString();
			postParam.put(key, req.getParameter(key));
		}
		//һ��������ط���ӡ���ص����������в���
		
		String serverSign = SignUtil.getSignCode(postParam, Constant.APP_SECRET);
		
		String sign = req.getParameter("sign");
		String tradeStatus = req.getParameter("trade_status");
		//1.ǩ����֤ͨ����trade_status����3
		if(serverSign.equals(sign) && "3".equals(tradeStatus)) {
			//2.���ѯ�������飬���ö��������Ƿ�û��ֵ�ɹ���û���޸Ķ���״̬Ϊ�ɹ�״̬���������������
			//3.����ɹ������߶����Ѿ��ɹ�����ظ�
			JSONObject responseJson = new JSONObject();
			responseJson.put("code", 200);
			resp.getWriter().write(responseJson.toJSONString());
		}
	}

}
