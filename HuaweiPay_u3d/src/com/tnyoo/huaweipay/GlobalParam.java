package com.tnyoo.huaweipay;

/**
 * 存放联盟参数和插件对象
 * 
 * @author h00193325
 * 
 */
public class GlobalParam {
	/**
	 * 联盟为应用分配的应用ID，浮标初始化和支付要用（必须），release: 10383643
	 */
	public static String APP_ID = "";

	/**
	 * 浮标初始化时要使用  浮标密钥
	 */
	public static String BUO_SECRET = "";

	/**
	 * 支付ID： 浮标初始化 和 支付的使用要用（必须） release: 900086000022031102
	 */
	public static String PAY_ID = "";

	/**
	 * 支付私钥，支付前对订单进行签名，（使用客户端签名时才用）
	 */
//	public static String PAY_RSA_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCwbioS4JYcC/yt2X9KG+6vXRQfBrRXqSDsZzyIw6Yik8ymWiPMUprsXMc6jFV2dNi6NNjs8QA5mvRMuD6qkPIuQrJ2hBdS9nwzbyWOCMj7kQ+xtl37WnpF1Q+Dn4sTSmT87Z4BDFK8oY4owa15JRYV7vGLizR4svddff8pB86uMaqziRq8Uj6Uv61iuQu2oBRXLqP8C2UgLcrp3GV1kP77VcL42/ky19AjtfSAMbUFf3FXgz/rn3F9q9srNqzYBRzWou+6lyfO2i7Eg3Psnmb+ENAAAiYeoFZ5VJmEGhBSrihPjAquxvUR/iODbWaPhPn6urr3UINox1Bfg7+F2JtlAgMBAAECggEBAI9SDo+jLY2/FxZK1qJnKm/+VrgVhC6pz3r7KiuXc8Am/FouY2I5wGw9ugIPueUualhpUca4yAwDmm0wRofckz+NItgv2ZqlbKODqKhG7q2dW3hrwtDK4QUVXTL5Scrz5jGo/W+wiQ1+17K4EqmUvOo4XrOS858REu05yyuTkgviJleBYBcbH9+YsykYtJ+FWRiND7hYN3R4Z4UEdmqea24mqdP/pXnuG0MfgmM9U8BrA9trycGm6TbahmwVIeVOlFjP2MtOYMc3Zxy2VVEXEPY/2hJaZGQxr5lx/E0N9pkIEY+M6kzHFy7dFcrrkh95VVrar6bveQIO0ejx7oth5AkCgYEA51hUi0WZLiVjxMWWieTgtLsPj2GTrPE3p8HNOr1PoNK89Ht9kq58ONHRQBRgRfjZdM1jdsXq7NnoBoXzAAtXwt3iJV3l73hha0Hparq7u7Ff8YpwONjplQ0yytKtgslOD2E/D/ayZMcjbqOuk4kM0bCBk4i66gxwq4oKF0DiVZsCgYEAwzufsqaXaLBAyNbvTAKS/Ld9vb5oS2GAH/Tsuv3cTO5zby4gKRgUb/hVmnN+diXrLkv6xespf1n0S48oFViMG0LLfw3OckEj0rbiJ7QP10Dgh710z+EeIbk3QURr6QKz4ScqNE7rVb4/7J/1KAu7ioJoHMI6spk0291VTojgYv8CgYEAvgXg09DkJHJTw8Hkh/l21Ry9iDFHAovUAjh+4Zu0N05CJLL2jzBBGs7FCNRzy6W+wGEgLOQetlO6cPk/gy7N9GMaqUS6ZHvF0Tr/hkpBf8cPHrBuw1Lwchy4Nr7cBIxjUOC40tz45SxpHyzVbZvp6qcHl+58UVjMhypIZZdHEbcCgYAucfXJA3bLsfqHjRJ6q7YaJvDj/fND05sufw7+YTxSlPujPH7sJ81c4eBDkSxJoTW02uB6yzohEN5cR19KzLjwF1DgFgHCDpseGuHsfhqyo72GrVynw69uaGirv0JIuZrfenjA3vF/gXYlrdwdvcTM678m8FIUgPwhPiN4PnnbMwKBgQC1kV+RT7ik7BucgtOev0z4GBNe8j8QhGkZMrvSTW5zfg2SbLIvGW6fYWbOu/jUy8yfzUdpxsSQoZaxJ4xmaQ2lwVdpruxVDAz6iBp5f98ipUjtB0GL0GCZjxd9i5GXVkACECcRDimEw5UCtjWwlOvgVFZMboBC81F7l0ONaQ2qBw==";
	
	/**
	 * 商户名
	 */
	public static String USER_NAME = "";
	
	/**
	 * 支付页面横竖屏参数：1表示竖屏，2表示横屏，默认竖屏
	 */
	public static int PAY_ORI = 2;
	
	public static boolean SHOW_DEBUG_LOG = false;
	
	public interface PayParams {
		
		public static final String USER_ID = "userID";

		public static final String APPLICATION_ID = "applicationID";

		public static final String AMOUNT = "amount";

		public static final String PRODUCT_NAME = "productName";

		public static final String PRODUCT_DESC = "productDesc";

		public static final String REQUEST_ID = "requestId";
		
		public static final String USER_NAME = "userName";
		
		public static final String EXT_RESERVED = "extReserved";
		
		public static final String SIGN = "sign";

		public static final String NOTIFY_URL = "notifyUrl";

		public static final String SERVICE_CATALOG = "serviceCatalog";

		public static final String SHOW_LOG = "showLog";

		public static final String SCREENT_ORIENT = "screentOrient";

		public static final String SDK_CHANNEL = "sdkChannel";

		public static final String URL_VER = "urlver";
	}
}
