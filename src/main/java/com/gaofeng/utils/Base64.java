package com.gaofeng.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base64编解码算法
 * <p>
 * CreateDate 2006-12-27
 * 
 * @author wuyuan.xie
 * @version 1.0 
 *
 */
public class Base64 {
	
    /**私有构造函数*/
    private Base64() {
    }
    
    /**
     * 编码
     * @param byteArray 	待编码的字节数组
     * @return String 		编码后的base64字符串
     */
    public static String encode(byte[] byteArray) {
    	String s = org.apache.commons.net.util.Base64.encodeBase64String(byteArray);
    	//用org.apache.commons.net.util.Base64.encodeBase64String(byte[] byteArray)编码出来的结果有多出一个回车换行符
    	//下面的操作就是去掉这个多出的回车换行符
    	Pattern p = Pattern.compile("\r|\n");
        Matcher m = p.matcher(s);
        return m.replaceAll("");
    }

    /**   
     * 用默认字符集编码
     * <p>
     * @param s 			待编码的字符串
     * @return String 		编码后的base64字符串
     */
    public static String encode(String s) {
        return encode(s.getBytes());
    }
    
    /**   
     * 用指定字符集编码
     * <p>
     * @param s 			待编码的字符串
     * @return String 		编码后的base64字符串
     */
    public static String encode(String s, String encoding) {
    	String resultValue = "";
        try {
        	resultValue = encode(s.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return resultValue;
    }
    
    /**   
     * 用指定字符集编码
     * <p>
     * @param s 			待编码的字符串
     * @return String 		编码后的base64字符串
     */
    public static String encode(String s, CharacterSet cs) {
        return encode(s, cs.value());
    }
    
    /**   
     * 用指定字符集编码: gbk
     * <p>
     * @param s 			待编码的字符串
     */
    public static String encodeGBK(String s) {
        return encode(s, CharacterSet.GBK.value());
    }
    
    /**   
     * 用指定字符集编码: utf-8
     * <p>
     * @param s 			待编码的字符串
     */
    public static String encodeUTF8(String s) {
        return encode(s, CharacterSet.UTF_8.value());
    }
    
    /**
     * 解码
     * @param s
     * @return
     * @throws IOException
     */
    public static byte[] decode(String s) {
    	return org.apache.commons.net.util.Base64.decodeBase64(s);
    }

    /**
     * 用指定字符集解码
     * <p>
     * @param s 			待解码的base64字符串
     * @param encoding 		字符集
     * @return String 		解码后的字符串
     */
    public static String decode(String s, String encoding) {
    	String returnValue = null;
    	byte[] byteArray = decode(s);
    	if (encoding == null || "".equals(encoding)) {
    		returnValue = new String(byteArray);
        } else {
        	try {
				returnValue = new String(byteArray, encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
    	return returnValue;
    }
    
    /**
     * 用指定字符集解码
     * <p>
     * @param s 			待解码的base64字符串
     * @param cs 			字符集
     * @return String 		解码后的字符串
     */
    public static String decode(String s, CharacterSet cs) {
    	return decode(s, cs.value());
    }
    
    /**
     * 用指定字符集解码: gbk
     * <p>
     * @param s 			待解码的base64字符串
     * @return String 		解码后的字符串
     */
    public static String decodeGBK(String s) {
    	return decode(s, CharacterSet.GBK.value());
    }
    
    /**
     * 用指定字符集解码: utf-8
     * <p>
     * @param s 			待解码的base64字符串
     * @param cs 			字符集
     * @return String 		解码后的字符串
     */
    public static String decodeUTF8(String s) {
    	return decode(s, CharacterSet.UTF_8.value());
    }
    
    /**
     * 编码Url
     * @param s
     * @return
     */
    public static String encodeUrl(String s) {
		String r = encode(s);
		return r == null ? "" : r.replaceAll("\\+", "!").replaceAll("/", "-");
	}
	
    /**
     * 解码Url
     * @param s
     * @return
     */
	public static String decodeUrl(String s) {
		return decode(s.replaceAll("-", "/").replaceAll("\\!", "+"), "");
	}
}
