package com.gaofeng.utils;


/**
 * Http响应对象
 * @author Arthur.Xie
 * 2014-12-16
 */
public class HttpResponseBean {
	/**响应代码*/
	private int code = 0;
	/**响应描述*/
	private String text = "";
	/**响应内容 */
	private String content = "";
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}