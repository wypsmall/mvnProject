package com.neo.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TestHttpRest {

	public static void main(String[] args) {
		try {
			testHttpsGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testHttpGet() throws Exception {
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		// String getURL = GET_URL + "?username=" + URLEncoder.encode("你好",
		// "utf-8");
		String getURL = "http://localhost:8080/myspringmvc/jsonfeed.json";
		URL getUrl = new URL(getURL);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到服务器
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		System.out.println("=============================");
		System.out.println("Contents of get request");
		System.out.println("=============================");
		String lines;
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		System.out.println("=============================");
		System.out.println("Contents of get request ends");
		System.out.println("=============================");
	}

	
	/*
	 * 注意运行的时候jdk1.7
	 * jdk1.6会报错
	 * javax.net.ssl.SSLHandshakeException: Received fatal alert: handshake_failure
			at com.sun.net.ssl.internal.ssl.Alerts.getSSLException(Unknown Source)
			at com.sun.net.ssl.internal.ssl.Alerts.getSSLException(Unknown Source)
	 */
	private static void testHttpsGet() throws Exception {
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		// String getURL = GET_URL + "?username=" + URLEncoder.encode("你好",
		// "utf-8");

		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new DefaultTrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		HttpsURLConnection.setDefaultSSLSocketFactory(ssf);
		HttpsURLConnection.setDefaultHostnameVerifier(new DefaultHostnameVerifier());

		String getURL = "https://localhost:8443/myspringmvc/jsonfeed.json";
		URL getUrl = new URL(getURL);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpsURLConnection connection = (HttpsURLConnection) getUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("GET");
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到服务器
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		System.out.println("=============================");
		System.out.println("Contents of get request");
		System.out.println("=============================");
		String lines;
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		System.out.println("=============================");
		System.out.println("Contents of get request ends");
		System.out.println("=============================");
	}
	
	

	private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}
	
	private static class DefaultHostnameVerifier implements HostnameVerifier {

	    public boolean verify(String hostname, SSLSession session) {
	        System.out.println("Warning: URL Host: " + hostname + " vs. " + session.getPeerHost());
	        return true;
	    }
	}
}
