////////////////////////////////////////////////////////////////////
//                            _ooOoo_                             //
//                           o8888888o                            //    
//                           88" . "88                            //    
//                           (| ^_^ |)                            //    
//                           O\  =  /O                            //
//                        ____/`---'\____                         //                        
//                      .'  \\|     |//  `.                       //
//                     /  \\|||  :  |||//  \                      //    
//                    /  _||||| -:- |||||-  \                     //
//                    |   | \\\  -  /// |   |                     //
//                    | \_|  ''\---/''  |   |                     //        
//                    \  .-\__  `-`  ___/-. /                     //        
//                  ___`. .'  /--.--\  `. . ___                   //    
//                ."" '<  `.___\_<|>_/___.'  >'"".                //
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |               //    
//              \  \ `-.   \_ __\ /__ _/   .-` /  /               //
//        ========`-.____`-.___\_____/___.-`____.-'========       //    
//                             `=---='                            //
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^      //         
//                       佛祖镇楼                  BUG辟易						  //
//          	佛曰:          									  //
//                  	写字楼里写字间，写字间里程序员；                  				  //
//                  	程序人员写程序，又拿程序换酒钱。						  //
//                  	酒醒只在网上坐，酒醉还来网下眠；						  //
//                  	酒醉酒醒日复日，网上网下年复年。                                                                            //
//                  	但愿老死电脑间，不愿鞠躬老板前；                                                                            //
//                  	奔驰宝马贵者趣，公交自行程序员。                                                                            //
//                  	别人笑我忒疯癫，我笑自己命太贱；                                                                            //
//                  	不见满街漂亮妹，哪个归得程序员？                                                                            //
//                                                                //
////////////////////////////////////////////////////////////////////
package com.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @copyright SHENZHEN RONG WANG HUI ZHI TECHNOLOGY CORP
 * @author Lyon.liao
 * 创建时间：2014年11月12日
 * 类说明：
 * 
 * 最后修改时间：2014年11月12日
 * 修改内容： 新建此类
 *************************************************************
 *                                    .. .vr       
 *                                qBMBBBMBMY     
 *                              8BBBBBOBMBMv    
 *                            iMBMM5vOY:BMBBv        
 *            .r,             OBM;   .: rBBBBBY     
 *            vUL             7BB   .;7. LBMMBBM.   
 *           .@Wwz.           :uvir .i:.iLMOMOBM..  
 *            vv::r;             iY. ...rv,@arqiao. 
 *             Li. i:             v:.::::7vOBBMBL.. 
 *             ,i7: vSUi,         :M7.:.,:u08OP. .  
 *               .N2k5u1ju7,..     BMGiiL7   ,i,i.  
 *                :rLjFYjvjLY7r::.  ;v  vr... rE8q;.:,, 
 *               751jSLXPFu5uU@guohezou.,1vjY2E8@Yizero.    
 *               BB:FMu rkM8Eq0PFjF15FZ0Xu15F25uuLuu25Gi.   
 *             ivSvvXL    :v58ZOGZXF2UUkFSFkU1u125uUJUUZ,   
 *           :@kevensun.      ,iY20GOXSUXkSuS2F5XXkUX5SEv.  
 *       .:i0BMBMBBOOBMUi;,        ,;8PkFP5NkPXkFqPEqqkZu.  
 *     .rqMqBBMOMMBMBBBM .           @Mars.KDIDS11kFSU5q5   
 *   .7BBOi1L1MM8BBBOMBB..,          8kqS52XkkU1Uqkk1kUEJ   
 *   .;MBZ;iiMBMBMMOBBBu ,           1OkS1F1X5kPP112F51kU   
 *     .rPY  OMBMBBBMBB2 ,.          rME5SSSFk1XPqFNkSUPZ,.
 *            ;;JuBML::r:.:.,,        SZPX0SXSP5kXGNP15UBr.
 *                L,    :@huhao.      :MNZqNXqSqXk2E0PSXPE .
 *            viLBX.,,v8Bj. i:r7:,     2Zkqq0XXSNN0NOXXSXOU 
 *          :r2. rMBGBMGi .7Y, 1i::i   vO0PMNNSXXEqP@Secbone.
 *          .i1r. .jkY,    vE. iY....  20Fq0q5X5F1S2F22uuv1M;
 *
 ***************************************************************
 */
public class HttpClientSsl {

	private final static Logger log = Logger.getLogger("HttpClientSsl");
	
	private static final String METHOD_POST = "POST";  
    private static final String DEFAULT_CHARSET = "utf-8";  
      
    public static String doPost(String url, Map<String, String> params) throws Exception {
    	String content = mapToUrl(params);
    	return doPost(url, content, "utf-8", 30000, 30000);
    }
    public static String doPost(String url, String params, String charset, int connectTimeout, int readTimeout) throws Exception {  
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;  
        byte[] content = {};  
        if(params != null){  
            content = params.getBytes(charset);  
        }  
          
        return doPost(url, ctype, content, connectTimeout, readTimeout);  
    }  
    public static String doPost(String url, String ctype, byte[] content,int connectTimeout,int readTimeout) throws Exception {  
        HttpsURLConnection conn = null;  
        OutputStream out = null;  
        String rsp = null;  
        try {  
            try{  
                SSLContext ctx = SSLContext.getInstance("TLS");  
                ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());  
                SSLContext.setDefault(ctx);  
  
                conn = getConnection(new URL(url), METHOD_POST, ctype);   
                conn.setHostnameVerifier(new HostnameVerifier() {  
                    @Override  
                    public boolean verify(String hostname, SSLSession session) {  
                        return true;  
                    }  
                });  
                conn.setConnectTimeout(connectTimeout);  
                conn.setReadTimeout(readTimeout);  
            }catch(Exception e){  
                log.info("GET_CONNECTOIN_ERROR, URL = " + url);  
                throw e;  
            }  
            try{  
                out = conn.getOutputStream();  
                out.write(content);  
                rsp = getResponseAsString(conn);  
            }catch(IOException e){  
                log.info("REQUEST_RESPONSE_ERROR, URL = " + url);  
                throw e;  
            }  
              
        }finally {  
            if (out != null) {  
                out.close();  
            }  
            if (conn != null) {  
                conn.disconnect();  
            }  
        }  
          
        return rsp;  
    }  
  
    private static class DefaultTrustManager implements X509TrustManager {  
  
        @Override  
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        	System.out.println(arg0 + "authType=" + arg1);
        }  
  
        @Override  
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        	System.out.println(arg0 + "authType=" + arg1);
        }  
  
        @Override  
        public X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
  
    }  
      
    private static HttpsURLConnection getConnection(URL url, String method, String ctype)  
            throws IOException {  
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();  
        conn.setRequestMethod(method);  
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");  
        conn.setRequestProperty("User-Agent", "stargate");  
        conn.setRequestProperty("Content-Type", ctype);  
        return conn;  
    }  
  
    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {  
        String charset = getResponseCharset(conn.getContentType());  
        InputStream es = conn.getErrorStream();  
        if (es == null) {  
            return getStreamAsString(conn.getInputStream(), charset);  
        } else {  
            String msg = getStreamAsString(es, charset);  
            if (msg == null || "".equals(msg)) {  
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());  
            } else {  
                throw new IOException(msg);  
            }  
        }  
    }  
  
    private static String getStreamAsString(InputStream stream, String charset) throws IOException {  
        try {  
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));  
            StringWriter writer = new StringWriter();  
  
            char[] chars = new char[256];  
            int count = 0;  
            while ((count = reader.read(chars)) > 0) {  
                writer.write(chars, 0, count);  
            }  
  
            return writer.toString();  
        } finally {  
            if (stream != null) {  
                stream.close();  
            }  
        }  
    }  
  
    private static String getResponseCharset(String ctype) {  
        String charset = DEFAULT_CHARSET;  
  
        if (ctype != null && !"".equals(ctype)) {  
            String[] params = ctype.split(";");  
            for (String param : params) {  
                param = param.trim();  
                if (param.startsWith("charset")) {  
                    String[] pair = param.split("=", 2);  
                    if (pair.length == 2) {  
                        if (pair[1] != null && !"".equals(pair[1])) {  
                            charset = pair[1].trim();  
                        }  
                    }  
                    break;  
                }  
            }  
        }  
  
        return charset;  
    }  
    private static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (isFirst) {
                sb.append(key + "=" + URLEncoder.encode(value, "utf-8"));
                isFirst = false;
            } else {
                if (value != null) {
                    sb.append("&" + key + "=" + URLEncoder.encode(value, "utf-8"));
                } else {
                    sb.append("&" + key + "=");
                }
            }
        }
        return sb.toString();
    }
}
