package com.gaofeng.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class UrlUtil {
	/** 日志对象 */
	private static Logger _log = (Logger) LoggerFactory.getLogger(UrlUtil.class);

	/**
	 * 处理url
	 * 
	 * @param url
	 * @return
	 */
	public static String dealUrl(String url) {
		url = url == null ? "" : url;
		url = url.replaceAll("\\\\", "/");
		return url;
	}

	/**
	 * Url编码：utf-8
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeUTF8(String s) {
		return encode(s, CharacterSet.UTF_8.value());
	}

	/**
	 * Url编码：gbk
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeGBK(String s) {
		return encode(s, CharacterSet.GBK.value());
	}

	/**
	 * Url编码
	 * 
	 * @param s
	 * @param encoding
	 * @return
	 */
	public static String encode(String s, String encoding) {
		String resultValue = "";
		try {
			resultValue = URLEncoder.encode(s, encoding);
		} catch (UnsupportedEncodingException e) {
			_log.error("s : " + s);
			_log.error("encoding : " + encoding);
			_log.error("", e);
		}
		return resultValue;
	}

	/**
	 * Url解码：utf-8
	 * 
	 * @param s
	 * @return
	 */
	public static String decodeUTF8(String s) {
		return decode(s, CharacterSet.UTF_8.value());
	}

	/**
	 * Url解码：gbk
	 * 
	 * @param s
	 * @return
	 */
	public static String decodeGBK(String s) {
		return decode(s, CharacterSet.GBK.value());
	}

	/**
	 * Url解码
	 * 
	 * @param s
	 * @param encoding
	 * @return
	 */
	public static String decode(String s, String encoding) {
		String resultValue = "";
		try {
			resultValue = URLDecoder.decode(s, encoding);
		} catch (UnsupportedEncodingException e) {
			_log.error("s : " + s);
			_log.error("encoding : " + encoding);
			_log.error("", e);
		}
		return resultValue;
	}
}
