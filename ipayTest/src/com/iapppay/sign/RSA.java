package com.iapppay.sign;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSA 
{
	private static final String  SIGN_ALGORITHMS = "MD5WithRSA";
	//private static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
	* RSA��ǩ����
	* @param content ��ǩ�����
	* @param sign ǩ��ֵ
	* @param ali_public_key  ������Կ
	* @param input_charset �����ʽ
	* @return ����ֵ
	*/
	public static boolean verify(String content, String sign, String iapp_pub_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(iapp_pub_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

	
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			return signature.verify( Base64.decode(sign) );
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	* RSAǩ��
	* @param content ��ǩ�����
	* @param privateKey �̻�˽Կ
	* @param input_charset �����ʽ
	* @return ǩ��ֵ
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
}
