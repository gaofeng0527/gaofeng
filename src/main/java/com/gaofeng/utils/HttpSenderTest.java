package com.gaofeng.utils;

public class HttpSenderTest {
	public static void main(String[] args) {
		String mobile = "18510740386";// 手机号码，多个号码使用","分割
		String msg = StringUtil.encodeHexString(15, "【久邦数码】尊敬的用户你好，这是你的验证码180116，有效期是20分钟，请尽快输入验证码");// 短信内容

		try {
			String returnString = HttpSender.batchSend(mobile, msg);
			System.out.println(returnString);
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			// TODO 处理异常
			e.printStackTrace();
		}
	}
}
