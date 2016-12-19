package com.iapppay.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		//String content = "{\"appid\":\"500000185\",\"count\":1,\"cporderid\":\"1404124310243\",\"cpprivate\":\"cpprivateinfo123456\",\"feetype\":0,\"money\":100,\"paytype\":5,\"result\":\"0\",\"transid\":\"32011406301831300001\",\"transtime\":\"2014-06-30 18:31:32\",\"transtype\":0,\"waresid\":1}";
		
		 //私钥
	//String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKz0WssMzD9pwfHlEPy8+NFSnsX+CeZoogRyrzAdBkILTVCukOfJeaqS07GSpVgtSk9PcFk3LqY59znddga6Kf6HA6Tpr19T3Os1U3zNeU79X/nT6haw9T4nwRDptWQdSBZmWDkY9wvA28oB3tYSULxlN/S1CEXMjmtpqNw4asHBAgMBAAECgYBzNFj+A8pROxrrC9Ai6aU7mTMVY0Ao7+1r1RCIlezDNVAMvBrdqkCWtDK6h5oHgDONXLbTVoSGSPo62x9xH7Q0NDOn8/bhuK90pVxKzCCI5v6haAg44uqbpt7fZXTNEsnveXlSeAviEKOwLkvyLeFxwTZe3NQJH8K4OqQ1KzxK+QJBANmXzpVdDZp0nAOR34BQWXHHG5aPIP3//lnYCELJUXNB2/JYTN57dv5LlE5/Ckg0Bgak764A/CX62bKhe/b+FMsCQQDLe4F2qHGy7Sa81xatm66mEkG3u88g9qRARdEvgx9SW+F1xBt2k/bU2YI31hB8IYXzL8KW9NzDfQPihBBUFn4jAkEAzbrmq/pLPlo6mHV3qE5QA2+J+hRh0UYVKsVDKkJGLH98gepS45hArbawBne/NP1bJTUVGKP9w7sl0es01hbteQJATzLO/QQq3N15Cl8dMI07uN+6PG0Y/VeCLpH+DWQXuNKSOmgN2GVW2RmfmWP0Hpxdqn2YW3EKy/vIm02TnWbzyQJAXwujUR9u9s8BZI33kw3gQ7bvWVYt8yyiYzWD2Qrnyg08tN5o+JsjW3fEDWHm70jjZIc+l/5FaZ7H5NOYpnVcpA==";
		 //公钥
		//String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCs9FrLDMw/acHx5RD8vPjRUp7F/gnmaKIEcq8wHQZCC01QrpDnyXmqktOxkqVYLUpPT3BZNy6mOfc53XYGuin+hwOk6a9fU9zrNVN8zXlO/V/50+oWsPU+J8EQ6bVkHUgWZlg5GPcLwNvKAd7WElC8ZTf0tQhFzI5raajcOGrBwQIDAQAB";
		
		
		
				
		String content = "{\"appid\":\"3001819184\",\"waresid\":1,\"cporderid\":\"8787546214\",\"price\":0.01,\"currency\":\"RMB\", \"appuserid\":\"dota148423658\"}";
		
		//String content = "{\"transid\":\"32021503311631042216\"}";
		
		//String content = "{\"appid\":\"3001819184\",\"waresid\":1,\"cporderid\":\"14278055890000\",\"price\":100,\"currency\":\"RMB\", \"appuserid\":\"76634262\"}";
		//String content = "{"appid":"3001819184","waresid":1,"cporderid":"56565656","price":0.01,"currency":"RMB", "appuserid":"dota148423658"}";
		String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIvxEcxf1IFHz20LEtYoOwcFOr0+cFycWM0NaZbSi8ZXrIM6HPYVEEUo9iSaAV38D/yNK4IgnTqJa3GgpEtwF/bfInO2CJm+QypeskVRrHha4sBtrkqwvlYwE6C0OijHgMpUsRK/BV+hpjp7jO+nGcq/EklJQVZqc4tedV78JclXAgMBAAECgYEAil2manwKInV9O2TqkAMhMfTSulDlSjxwdPahAzZUa50+flXUUuBKwQcaMOBxH5tdtylh7n0O2NUM/kGGkOxZVAG6zjCDbLnGpWGvswKYKVEPk6HczXyzccV3XdPZckY21X5r9mm+Dvt4KmQSaq0QSE6EkjzYV51kbs+n/cDF1XECQQD9cTmbILPIGrInoXHckn12V3N7VI99EJfjMbq8UZXoM01kyyD+/NG+2FqMOd+e0SOWjnhRlShZ24TQluuv6pBPAkEAjVqc1M98cS1B1zG9hKWP0xUjEltwxebGAyyPnxumzADRgRqFRwvFbCkp4vy8wxNzoFv1d1Ue+GdWLgOxGmYseQJAPIB0DQlQgbuFvho3UT2TP9X+lnmIS4XTjIQJnP0o+SaOGjq3pMd71sBVVVZXgNVSVpWqOWePG2xhF15/4x3u1wJAMlWm7RzpWj/cZy2+J8Lw4SBwubO4B7CyqCSxC95D2fcco1V3SiJF7csGsF6BNRHzJZI5aYH1Ik1h4cbMxPLHOQJAFAHXXl59ZMPg/bXHsesu3hxRivy7neE9DHt0acJSNWfjn3kabEO0W2MNu5ib2MfwrIGIVv6JIKCDfcdqavCyKQ==";
		String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrRfnQicAAq9gpBiCGsSi43rHop4h+/KvPYZnDif2WuuZpqR/YlGyLs9iG2RKhG5c3uvneCezThKy6MoDlofFYndbLu/5TpLPKSVFy7uFrB6O2HOG9VAXqCRnwRV6q0aJM+lHT9xnMftXtDwTDNV/dy8Q8oTEf1mHVueis82KpXQIDAQAB";
		
		
		// 签名
		String sign = SignHelper.sign(content, priKey);
		//sign =  "H0tN6w6Py9lRTY6UeQMoLWgl/KCrOzJJpsDEkigM1223EHm3UY9sI730TiU1zJnbM+iICySfSknXGZEMWw3vMXSD3kotynadj36ItN4ENMBPuy182ev2RZQfQtn8qkUAh4DwRp2idtyWdJ4hDH17wkVlyoBiR1SYdCiS5CpSrmM=";
//		System.out.println("content: "+content);
		System.out.println("sign: "+sign);
		//System.out.println("pubKey: "+pubKey);

		//google验证.
		//pubKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1+nxBV4xGUF5+gDS/UBd2GY0+ZcGiYR9tqL6oiJiXscgd9lzP8CgZQUJ5e9VII0RRkiLXty5yMKi6iH1iGXoHiiAwByH86eabLEA4iMX6AEUdqo45cWqoLTIaPZLUm2Nht5dPWhiKOGUbGz1vocQ+jEFFUUzaedUnQMgNK1esI6JjVoUgtlXNPfLuumCA0zzlL6X3uwkCBYcmxpkNixyNKJTMEJu442Rf7st2Hb1V3VFV+6nvpU56jST3vKz/LQ9vbDCYX02DBl7nHWnMJnQ8JlZuXUZ4Xt+1/BHyMgzReo7+rTmbC3nmzA+/7FvbGB8l0LIHff2JbhETUWsPnX5jQIDAQAB";
		
		
		
		//content="{\"orderId\":\"12999763169054705758.1379164821647879\",\"packageName\":\"com.Tnyoo.xf\",\"productId\":\"com.tnyoo.xf.5\",\"purchaseTime\":1420009400328,\"purchaseState\":0,\"developerPayload\":\"tnyoo\",\"purchaseToken\":\"lhndemkjbijifbmogbhfeojc.AO-J1OxUoRYInvzM_X2r5mn7Pb37u02HfXAiImtpJAkpX8ZGIYOOFFv3LJhUlgJkibyIWp7Cj68QYRdRc3tgFlCq77bW8TfTGAVg1T6FhmAMtpjqAqRADEA\"}";
		//sign="AJW3BGCmTVWnpXM+coJaHvgFO4F0q3EWhdmw35rX1jvySWSBNAfl6OQWASiPa/aGXSOWm4a35reu2Ddp8aX28DXIjy/li/ZmgJVJ3/tehvLskvro7gyo8fEdyxSoFoZLd85pbqV0TkA+p6HFZxWlBxpB+L6rNy7yMbv9T/ewmPHZ3hL/6Z13CrtigZ5GaxCjTXM1nhSWt+3KbI+ZR4BQl0PhGpgbhHUZ5rJqbcFvhotxitVUd+M/Dns9Vp9FypLxcEzdsZZF7IeVZO7C/xxVpL5kauajGooZEXsTDkUb4M8nWQqDxean7qx14UacsFwc3v3iycV+dPDj8J1bPQ4syQ==";
		// 验签
//		if (SignHelper.verify(content, sign, pubKey))
//		{
//			System.out.println("verify ok");
//		}
//		else
//		{
//			System.out.println("verify fail");
//		}
		
		
//		String filetext = "张小名=25分|李小花=43分|王力=100分|";
//		filetext = "(userId=284879535,userName=xbx284879535,isVIP=0,supportSmsPrices=,timestamp=1439293358577,sign=38GRaGewiZKlEHL2fLvg/g==)";
//		//Pattern p = Pattern.compile("\\=(.*?)\\|");//正则表达式，取=和|之间的字符串，不包括=和|
//		String str1,str2;
//		str1 = "username=";
//		str2 = ",";
//		Pattern p = Pattern.compile("(?i)(?<="+str1+")(.*?)\\"+str2+"");//正则表达式，(?i)表示不区分大小写,(?<="+str1+")匹配但不包含后面str1。
//		
//		Matcher m = p.matcher(filetext);
//		
//		 while(m.find()) {
//		       System.out.println(m.group(1));//m.group(1)不包括这两个字符
//
//		        }
		
	}
	
}



