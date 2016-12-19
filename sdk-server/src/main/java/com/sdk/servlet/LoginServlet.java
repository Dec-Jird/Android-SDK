package com.sdk.servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdk.util.Constant;
import com.sdk.util.HttpClientSsl;
import com.sdk.util.SignUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -8431430892643244256L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String sessionId = req.getParameter("session_id");
			String uid = req.getParameter("uid");
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("app_id", Constant.APP_ID + "");
			params.put("uid", uid);
			params.put("session_id", sessionId);
			params.put("ts", System.currentTimeMillis() + "");
			
			String sign = SignUtil.getSignCode(params, Constant.APP_SECRET);
			
			params.put("sign_type", "md5");
			params.put("sign", sign);
			
			String content = HttpClientSsl.doPost(Constant.URL_CHECKSESSION, params);
			if(content != null && !"".equals(content)) {
				//1.����JSON���ݣ����code����200������֤session_id�ɹ�
				JSONObject json = JSON.parseObject(content);
				if(json != null && json.containsKey("code") && json.getIntValue("code") == 200) {
					//2.�ɹ��󣬲�ѯ�˺ű��Ƿ���ڶ�Ӧ����uid���˺ţ��������򴴽����������ȡ���˺���Ϣ
					//3.���ɵ�¼���ƣ����ӦCP�˺�UID�����棺key����¼���ƣ�value��CP�˺�UID
				}
			}
			resp.getWriter().write("OK");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			resp.getWriter().flush();
			resp.getWriter().close();
		}
	}

	
}
