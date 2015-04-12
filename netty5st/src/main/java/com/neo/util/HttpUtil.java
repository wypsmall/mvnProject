package com.neo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;


/** 
 * desc:封装httpclient调用
 * <p>创建人：wangyunpeng 创建日期：2014-5-24</p>
 * @version V1.0  
 */
public class HttpUtil {
	public final static String CONTENTTYPE_XML="text/xml";
	public final static String CONTENTTYPE_WWWFROM="application/x-www-form-urlencoded";
	public final static String CONTENTTYPE_HTML="text/HTML";
	public final static String CHARSET_UTF8="UTF-8";
	
	private static int CONNECTION_TIMEOUT = 15000;
	private static int SO_TIMEOUT = 15000;
	
	/**
	 * desc:分装http请求类，返回字符床
	 * <p>创建人：wangyunpeng , 2014-7-10上午10:20:52</p>
	 * @param content
	 * @param contentType
	 * @param charset
	 * @param url_str
	 * @return
	 * @throws BusiException
	 */
	public static  String doHttpPost(String content, String contentType, String charset, String url_str) throws Exception{
		String responseStr = null;
		HttpClient httpClient = null;
		PostMethod postMethod = null;
		try {
			RequestEntity entity = new StringRequestEntity(content,contentType, charset);
			httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(SO_TIMEOUT);
			postMethod = new PostMethod(url_str);
			postMethod.setRequestEntity(entity);
			httpClient.getParams().setParameter("http.protocol.content-charset",charset);
			postMethod.addRequestHeader("Connection", "close");
			int statusCode = httpClient.executeMethod(postMethod);
			if(HttpStatus.SC_OK != statusCode){
				System.out.println("> [0x%s] HTTP请求银行返回错误HttpStatus:%d" + statusCode);
			}
//			String statusText = postMethod.getStatusText();
			responseStr = postMethod.getResponseBodyAsString();
//			responseStr = URLDecoder.decode(responseStr, "utf-8");
			System.out.println("> [0x%s] HTTP请求银行返回报文:%s" + responseStr);

		}catch (Exception e) {
			throw e;
		} finally {
			if(postMethod != null) {
				try {
					postMethod.releaseConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//方法-1
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
			//方法-2
			//((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
			
			//http://www.iteye.com/topic/234759
		}
		
		return responseStr;
	}
	
	/**
	 * desc:分装http请求类，返回byte[]
	 * <p>创建人：wangyunpeng , 2014-7-10上午10:21:20</p>
	 * @param content
	 * @param contentType
	 * @param charset
	 * @param url_str
	 * @return
	 * @throws BusiException
	 */
	public static byte[] doHttpPostByByte(String content,String contentType,String charset,String url_str) throws Exception{
		byte[] responseBody = null;
		HttpClient httpClient = null;
		PostMethod postMethod = null;
		try {
			System.out.println("> [0x%s] HTTP请求银行地址:%s" + url_str);
			System.out.println("> [0x%s] HTTP请求银行参数:%s" + content);
			RequestEntity entity = new StringRequestEntity(content,contentType, charset);
			httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(SO_TIMEOUT);
			postMethod = new PostMethod(url_str);
			postMethod.setRequestEntity(entity);
			httpClient.getParams().setParameter("http.protocol.content-charset",charset);
			postMethod.addRequestHeader("Connection", "close");
			int statusCode = httpClient.executeMethod(postMethod);
			if(HttpStatus.SC_OK != statusCode){
				System.out.println("> [0x%s] HTTP请求银行返回错误HttpStatus:%d" + statusCode);
			}
			responseBody = postMethod.getResponseBody();
		}catch (Exception e) {
			throw e;
		} finally {
			if(postMethod != null) {
				try {
					postMethod.releaseConnection(); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//方法-1
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
			//方法-2
			//((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
			
			//http://www.iteye.com/topic/234759
		}
		return responseBody;
	}
	
	/**
	 * desc:将map转换成http请求参数
	 * <p>创建人：wangyunpeng , 2014-7-10上午10:21:59</p>
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String getRequestContent(Map<String, String> map) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		Set<String> keys = map.keySet();
		
//		for(String key : keys){
//			sb.append(key).append("=").append(map.get(key)).append("&");
//		}
		for(Map.Entry<String, String> en : map.entrySet()){
			sb.append(en.getKey()).append("=").append(en.getValue()).append("&");
		}
		sb.deleteCharAt(sb.lastIndexOf("&"));
		//sb = sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
	

	public static void main(String[] args) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("posId", "7001");
			map.put("orderId", "t7001");
			String content = HttpUtil.getRequestContent(map);
			String url_str = "http://127.0.0.1:8080";
			String res = HttpUtil.doHttpPost(content, HttpUtil.CONTENTTYPE_WWWFROM, HttpUtil.CHARSET_UTF8, url_str);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
