

import com.iapppay.sign.SignHelper;




public class demo
{
	/**
	 *类名：demo
	 *功能  服务器端签名与验签Demo
	 *版本：1.0
	 *日期：2014-06-26
	 '说明：
	 '以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己的需要，按照技术文档编写,并非一定要使用该代码。
	 '该代码仅供学习和研究爱贝云计费接口使用，只是提供一个参考。
	*/
	//
	
	public static void main(String [] argv)
	{
		
		String content = "{\"appid\":\"500000185\",\"count\":1,\"cporderid\":\"1404124310243\",\"cpprivate\":\"cpprivateinfo123456\",\"feetype\":0,\"money\":100,\"paytype\":5,\"result\":\"0\",\"transid\":\"32011406301831300001\",\"transtime\":\"2014-06-30 18:31:32\",\"transtype\":0,\"waresid\":1}";
//		content = "{\"appid\":\"2000000682\",\"waresid\":1,\"cporderid\":\"tn-920-203fa\",\"price\":0.01,\"currency\":\"RMB\",\"appuserid\":\"@ty-nt6439\",\"cpprivateinfo\":\"tnyooprivateinfo\",\"notifyurl\":\"http://192.168.0.140:8094/monizhuang/api?type=100\"}";
		// 私钥
		String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKz0WssMzD9pwfHlEPy8+NFSnsX+CeZoogRyrzAdBkILTVCukOfJeaqS07GSpVgtSk9PcFk3LqY59znddga6Kf6HA6Tpr19T3Os1U3zNeU79X/nT6haw9T4nwRDptWQdSBZmWDkY9wvA28oB3tYSULxlN/S1CEXMjmtpqNw4asHBAgMBAAECgYBzNFj+A8pROxrrC9Ai6aU7mTMVY0Ao7+1r1RCIlezDNVAMvBrdqkCWtDK6h5oHgDONXLbTVoSGSPo62x9xH7Q0NDOn8/bhuK90pVxKzCCI5v6haAg44uqbpt7fZXTNEsnveXlSeAviEKOwLkvyLeFxwTZe3NQJH8K4OqQ1KzxK+QJBANmXzpVdDZp0nAOR34BQWXHHG5aPIP3//lnYCELJUXNB2/JYTN57dv5LlE5/Ckg0Bgak764A/CX62bKhe/b+FMsCQQDLe4F2qHGy7Sa81xatm66mEkG3u88g9qRARdEvgx9SW+F1xBt2k/bU2YI31hB8IYXzL8KW9NzDfQPihBBUFn4jAkEAzbrmq/pLPlo6mHV3qE5QA2+J+hRh0UYVKsVDKkJGLH98gepS45hArbawBne/NP1bJTUVGKP9w7sl0es01hbteQJATzLO/QQq3N15Cl8dMI07uN+6PG0Y/VeCLpH+DWQXuNKSOmgN2GVW2RmfmWP0Hpxdqn2YW3EKy/vIm02TnWbzyQJAXwujUR9u9s8BZI33kw3gQ7bvWVYt8yyiYzWD2Qrnyg08tN5o+JsjW3fEDWHm70jjZIc+l/5FaZ7H5NOYpnVcpA==";
		// 公钥
		String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCs9FrLDMw/acHx5RD8vPjRUp7F/gnmaKIEcq8wHQZCC01QrpDnyXmqktOxkqVYLUpPT3BZNy6mOfc53XYGuin+hwOk6a9fU9zrNVN8zXlO/V/50+oWsPU+J8EQ6bVkHUgWZlg5GPcLwNvKAd7WElC8ZTf0tQhFzI5raajcOGrBwQIDAQAB";
		
		// 签名
		String sign = SignHelper.sign(content, priKey);
		
		System.out.println("sign:"+sign);
		
		// 验签
		if (SignHelper.verify(content, sign, pubKey))
		{
			System.out.println("verify ok");
		}
		else
		{
			System.out.println("verify fail");
		}
		
	}
	
}
