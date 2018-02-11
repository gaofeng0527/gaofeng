package com.gaofeng.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;



/**
 * Http_请求方法_公共类
 * 
 * </p>
 * 发送http请求时，参数里面不能有空格，否则会报400错误。如果有空格，
 * 请先单独将该参数值用java.net.URLEncoder.encode()编码
 * </p>
 * 
 * @author wuyuan.xie
 * CreateDate 2007-01-23
 */
public class HttpMethodUtil {
	/**日志对象*/
	private static Logger _log = (Logger) LoggerFactory.getLogger(HttpMethodUtil.class);
    
    /**
	 * 发送get请求
	 * @param url					请求Url
	 * @return
	 */
	public static HttpResponseBean doGet(String url) {
		return doGet(url, HttpConstant.DEFAULT_ENCODING, HttpConstant.DEFAULT_TIME_OUT);
	}
	
	/**
	 * 发送get请求
	 * @param url					请求Url
	 * @param responseEncoding		响应编码
	 * @return
	 */
	public static HttpResponseBean doGet(String url, String responseEncoding) {
		return doGet(url, responseEncoding, HttpConstant.DEFAULT_TIME_OUT);
	}
	
	/**
	 * 发送get请求
	 * @param url					请求Url
	 * @param timeOut 				单位为毫秒，一般设置为30000毫秒即可
	 * @return
	 */
	public static HttpResponseBean doGet(String url, int timeOut) {
		return doGet(url, HttpConstant.DEFAULT_ENCODING, timeOut);
	}
    
	/**
	 * 发送get请求
	 * @param url					请求Url
	 * @param responseEncoding		响应编码
	 * @param timeOut 				单位为毫秒，一般设置为30000毫秒即可
	 * @return
	 */
	public static HttpResponseBean doGet(String url, String responseEncoding, int timeOut) {
		HttpResponseBean resultValue = new HttpResponseBean();
		try {
			// 打开连接
			URL requestUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
			conn.setRequestMethod("GET");
			//设置超时时间（单位为毫秒，一般设置为30000毫秒即可）
			conn.setConnectTimeout(timeOut);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), responseEncoding));
			int code = conn.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				String content = "";
				String temp = in.readLine();
				//连接成一个字符串
				while (temp != null) {
					if (content != null)
						content += temp;
					else
						content = temp;
					temp = in.readLine();
				}
				resultValue.setContent(content);
			}
		} catch (MalformedURLException e) {
			_log.error("", e);
		} catch (ProtocolException e) {
			_log.error("", e);
		} catch (IOException e) {
			_log.error("", e);
		}
		return resultValue;
	}
	
	/**
	 * 发送post请求
	 * @param url					请求Url
	 * @param data					请求内容
	 * @return
	 */
	public static HttpResponseBean doPost(String url, String data) {
		return doPost(url, data, HttpConstant.DEFAULT_ENCODING, HttpConstant.DEFAULT_ENCODING, HttpConstant.DEFAULT_TIME_OUT);
	}
	
	/**
	 * 发送post请求
	 * @param url					请求Url
	 * @param data					请求内容
	 * @param requestEncoding		请求编码
	 * @param responseEncoding		响应编码
	 * @return
	 */
	public static HttpResponseBean doPost(String url, String data, String requestEncoding, String responseEncoding) {
		return doPost(url, data, requestEncoding, responseEncoding, HttpConstant.DEFAULT_TIME_OUT);
	}
	
	/**
	 * 发送post请求
	 * @param url					请求Url
	 * @param data					请求内容
	 * @param timeOut 				单位为毫秒，一般设置为30000毫秒即可
	 * @return
	 */
	public static HttpResponseBean doPost(String url, String data, int timeOut) {
		return doPost(url, data, HttpConstant.DEFAULT_ENCODING, HttpConstant.DEFAULT_ENCODING, timeOut);
	}

	/**
	 * 发送post请求
	 * @param url					请求Url
	 * @param data					请求内容
	 * @param requestEncoding		请求编码
	 * @param responseEncoding		响应编码
	 * @param timeOut 				单位为毫秒，一般设置为30000毫秒即可
	 * @return
	 */
	public static HttpResponseBean doPost(String url, String data, String requestEncoding, String responseEncoding, int timeOut) {
		HttpResponseBean resultValue = new HttpResponseBean();
		try {
			URL requestUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)requestUrl.openConnection();
			//发送数据
			conn.setRequestMethod("POST");
			//设置超时时间（单位为毫秒，一般设置为30000毫秒即可）
			conn.setConnectTimeout(timeOut);
			conn.setDoOutput(true);
			OutputStream out = conn.getOutputStream() ;
			if (data != null) {
				out.write(data.getBytes(requestEncoding));
			}
			out.flush();
			out.close();
			//输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), responseEncoding));
			int code = conn.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				String content = "";
				String temp = in.readLine();
				//连接成一个字符串
				while (temp != null) {
					if (content != null)
						content += temp;
					else
						content = temp;
					temp = in.readLine();
				}
				resultValue.setContent(content);
			}
		} catch (MalformedURLException e) {
			_log.error("", e);
		} catch (ProtocolException e) {
			_log.error("", e);
		} catch (UnsupportedEncodingException e) {
			_log.error("", e);
		} catch (IOException e) {
			_log.error("", e);
		}		
		return resultValue;
	}
}
