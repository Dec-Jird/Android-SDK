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
 * ��Ϸ�ͻ���������Ϸ�������µ����Ѷ�����Ϣ���ظ���Ϸ�ͻ��ˣ���������֧��SDKʱ��Ҫ�õ���Щ���ݣ�
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
			//�˴������Ǵ����ݿ��̳Ǳ�鵽��Ӧ��Ʒ�����飨��ͬCP������һ����
			Map<String, String> product = getProduct(productId);
			
			//����һ��CPΨһ�Ķ�����
			String cpOrderId = System.currentTimeMillis() + "";
			
			//uid������uid���µ�ʱ��֪����Ϸ��������Ϣ�ģ�����������Ϣ���ȡ��Ӧ��ҵ��˺��������uid
			String uid = req.getParameter("uid");
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("app_id", Constant.APP_ID + "");
			params.put("cp_order_id", cpOrderId);
			params.put("create_time", System.currentTimeMillis() + "");
			//��pay_type=0ʱ��Ӧ�����壺product_subject="����"+buy_amount+product_unit+��Ʒ����
			//��pay_type=1 ʱ�� total_price������ǩ�� ���û��ڳ�ֵ�����п����޸Ľ�� ���û��ڳ�ֵ�����п����޸Ľ�� ���û��ڳ�ֵ�����п����޸Ľ�� ��
			//�û��ڳ�ֵ�����п����޸Ľ�� ���û��ڳ�ֵ�����п����޸Ľ�� ���û��ڳ�ֵ�����п����޸Ľ�� ���û��ڳ�ֵ�����п����޸Ľ�� ��product_subject=����ֵ��+��Ϸ����
			params.put("pay_type", String.valueOf(0));
			
			//һ������ǳ�ֵ��ʯ��Ԫ���ȵȣ��򵥼�����д1�ͺã���λ���о���
			params.put("product_id", product.get("productId"));//��Ʒ���
			params.put("product_subject", product.get("name"));//��Ʒ����
			params.put("product_body", product.get("description"));//��Ʒ����
			params.put("buy_amount", product.get("amount"));
			params.put("product_per_price", product.get("perPrice"));//����
			params.put("product_unit", "��");//��λ
			params.put("total_price", product.get("totalPrice"));//�ܽ��
			
			params.put("uid", uid);//����UID
			params.put("user_info", "");//�Զ�����Ϣ��͸����Ϣ��֧���ɹ�������SDK�������ص�ʱ��ԭ�ⷵ�أ�
			
			//������Ϣ��װ�ú�CP���������Ƿ�Ѹö�������������SDK������֧���ɹ��ص�ʱ�����Ը��ݶ���CP�����Ų�ѯ�Ķ�����������Ʒ����
			save(params);
			
			String signString = SignUtil.concatMap(params);
			//����ط���ӡ������Ŀ����Ϊ�˿ͻ����õ����������صĲ�����������SDK������֧��������ͻ��˴�����־��ʾ������ǩ������ȷ����˶�ǩ����
			System.out.println("��˶�ǩ������" + signString);
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
		product.put("name", "����10����ʯ");
		product.put("description", "10����ʯ");
		product.put("amount", String.valueOf(10));
		product.put("perPrice", String.valueOf(1));
		product.put("totalPrice", String.valueOf(1));
		return product;
	}
	
	
	private void save(Map<String, String> params) {
		
	}
}
