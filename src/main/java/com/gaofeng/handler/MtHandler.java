package com.gaofeng.handler;

import com.gaofeng.utils.HttpMethodUtil;
import com.gaofeng.utils.HttpResponseBean;
import com.gaofeng.utils.StringUtil;

public class MtHandler {
	/**通道：url*/
	private String url = "http://112.74.125.101:8888/sms/mt";
	/**通道：帐号*/
	private String account = "";
	/**通道：密码*/
	private String password = "";
	
	public static void main(String[] args) {
		MtHandler handler = new MtHandler();
		handler.sendMt("8613621925191", "欢迎使用短信平台", "01");
	}
	
	public MtHandler() {
		//password = Base64.encode(password, "utf-8");
	}
	
	/**
	 * 发送下行
	 * http://112.74.125.101:8888/sms/mt?command=MULTI_MT_REQUEST&userid=*****&password=*****&das=8613611112222&sm=%E4%B8%AD%E6%96%87
	 * @param mobiles
	 * @param content
	 * @param ext
	 */
	public void sendMt(String mobiles, String content, String ext) {
		try {
			String strURL = new StringBuffer()
				.append(url)
				.append("?command=MULTI_MT_REQUEST")
				.append("&userid=")
				.append(account)
				.append("&password=")
				.append(password)
				.append("&das=")
				.append(mobiles)
				.append("&dc=15")
				.append("&sm=")
				.append(StringUtil.encodeHexString(15, content))
				.append("&sa=")
				.append(ext)
				.toString();
			System.out.println(strURL);
			HttpResponseBean bean = HttpMethodUtil.doGet(strURL);
			System.out.println(bean.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
