package com.gaofeng.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

	public static String getShortMessage(int dataCoding, byte[] shortMessage) {
		String message = null;
		try {
			if (shortMessage == null)
				message = "";
			else if (dataCoding == 15)
				message = new String(shortMessage, "GBK");
			else if ((dataCoding & 0xC) == 8)
				message = new String(shortMessage, "UnicodeBigUnmarked");
			else
				message = new String(shortMessage, "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return message;
	}

	public static byte[] setShortMessage(int dataCoding, String message) {
		byte[] shortMessage = (byte[]) null;
		try {
			if (message != null)
				if (dataCoding == 15)
					shortMessage = message.getBytes("GBK");
				else if ((dataCoding & 0xC) == 8)
					shortMessage = message.getBytes("UnicodeBigUnmarked");
				else
					shortMessage = message.getBytes("ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return shortMessage;
	}

	public static String encodeHexString(int dataCoding, String realStr) {
		String hexStr = null;
		try {
			if (realStr != null)
				if (dataCoding == 15)
					hexStr = new String(Hex.encodeHex(realStr.getBytes("GBK")));
				else if ((dataCoding & 0xC) == 8)
					hexStr = new String(Hex.encodeHex(realStr.getBytes("UnicodeBigUnmarked")));
				else
					hexStr = new String(Hex.encodeHex(realStr.getBytes("ISO8859-1")));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return hexStr;
	}

	public static String decodeHexString(int dataCoding, String hexStr) throws DecoderException {
		String realStr = null;
		try {
			if (hexStr != null)
				if (dataCoding == 15)
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "GBK");
				else if ((dataCoding & 0xC) == 8)
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "UnicodeBigUnmarked");
				else
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return realStr;
	}

	public static boolean isNotNull(String str) {
		boolean result = false;
		if ((str != null) && (str.trim().length() > 0)) {
			result = true;
		}
		return result;
	}

	public static boolean isNull(String str) {
		boolean result = false;
		if ((str == null) || (str.trim().length() == 0)) {
			result = true;
		}
		return result;
	}

	public static String getSmallSegmentByMobile(String mobile) {
		if (mobile == null) {
			return null;
		}
		String segment = mobile.trim();
		if (segment.startsWith("+86")) {
			return segment.substring(3, 6);
		}
		if (segment.startsWith("86")) {
			return segment.substring(2, 5);
		}
		if (segment.startsWith("1")) {
			return segment.substring(0, 3);
		}
		return segment;
	}

	public static String getSegmentByMobile(String mobile) {
		if (mobile == null) {
			return null;
		}
		String segment = mobile.trim();
		if (segment.startsWith("+86")) {
			return segment.substring(3, 10);
		}
		if (segment.startsWith("86")) {
			return segment.substring(2, 9);
		}
		if (segment.startsWith("1")) {
			return segment.substring(0, 7);
		}
		return segment;
	}

	public static String getMobileNotWith86(String msisdn) {
		String mobile = null;
		try {
			if (msisdn == null) {
				return null;
			}
			if (msisdn.startsWith("+86")) {
				mobile = msisdn.substring(3);
			} else if (msisdn.startsWith("8613")) {
				mobile = msisdn.substring(2);
			} else if (msisdn.startsWith("8615")) {
				mobile = msisdn.substring(2);
			} else if (msisdn.startsWith("8618")) {
				mobile = msisdn.substring(2);
			} else if (msisdn.startsWith("86")) {
				mobile = msisdn.substring(2);
				mobile = "0" + mobile;
			} else {
				mobile = msisdn;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mobile;
	}

	public static String getMobileWith86(String mobile) {
		String msisdn = null;
		try {
			if (mobile == null) {
				return null;
			}
			if (mobile.startsWith("+86"))
				msisdn = mobile.substring(1);
			else if (mobile.startsWith("1"))
				msisdn = "86" + mobile;
			else if (mobile.startsWith("0"))
				msisdn = "86" + mobile.substring(1);
			else
				msisdn = mobile;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return msisdn;
	}

	public static String left(String input, Integer maxlen) {
		if (input == null)
			return null;
		if (maxlen.intValue() <= 0) {
			return input;
		}
		return input.substring(0, input.length() > maxlen.intValue() ? maxlen.intValue() : input.length());
	}

	public static String right(String input, Integer maxlen) {
		if (input == null)
			return null;
		if (maxlen.intValue() <= 0) {
			return input;
		}
		return input.substring(input.length() > maxlen.intValue() ? input.length() - maxlen.intValue() : 0);
	}

	public static String getString(Object obj) {
		if (obj == null) {
			return null;
		}
		StringBuffer stb = new StringBuffer();
		stb.append("[");
		for (Method md : obj.getClass().getDeclaredMethods()) {
			if (!md.getName().startsWith("get"))
				continue;
			try {
				Object o = md.invoke(obj, new Object[0]);
				if (o != null) {
					stb.append(md.getName().substring(3));
					stb.append(":");
					stb.append(o.toString());
					stb.append(",");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		if (stb.length() > 1)
			stb.deleteCharAt(stb.length() - 1);
		stb.append("]");
		return stb.toString();
	}

	public static String getCompareString(Object oldObj, Object newObj) {
		if ((newObj == null) && (oldObj == null)) {
			return "old[null];new[null]";
		}
		if ((newObj != null) && (oldObj == null)) {
			return "old[null];new[" + getString(newObj) + "]";
		}
		if ((newObj == null) && (oldObj != null)) {
			return "old[" + getString(oldObj) + "];new[null]";
		}
		if (!newObj.getClass().equals(oldObj.getClass())) {
			throw new RuntimeException("the class type of two object is not equal!" + newObj.getClass().getName() + "->"
					+ oldObj.getClass().getName());
		}
		StringBuffer stb = new StringBuffer();
		stb.append("Diff[");
		for (Method md : newObj.getClass().getDeclaredMethods()) {
			try {
				if (md.getName().startsWith("get")) {
					Object no = md.invoke(newObj, new Object[0]);
					Object oo = md.invoke(oldObj, new Object[0]);
					String ns = "null";
					String os = "null";
					if (no != null)
						ns = no.toString();
					if (oo != null)
						os = oo.toString();
					if (!ns.equals(os)) {
						stb.append(md.getName().substring(3));
						stb.append(":");
						stb.append(os + "->" + ns);
						stb.append(",");
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (stb.length() > 5)
			stb.deleteCharAt(stb.length() - 1);
		stb.append("]");
		return stb.toString();
	}

	/**
	 * 获取随机生成的六位数
	 * 
	 * @return
	 */
	public static String getRandomCode() {
		String code = "";
		for (int i = 0; i < 6; i++) {
			code += String.valueOf((int) Math.round(Math.random() * 9));
		}
		return code;
	}
}