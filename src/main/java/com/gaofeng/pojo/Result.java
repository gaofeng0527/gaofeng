package com.gaofeng.pojo;

public class Result {

	private boolean success;
	private String message;
	private Object data;
	private int code;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "Result [success=" + success + ", message=" + message + ", data=" + data + ", code=" + code + "]";
	}
	
	

}
