package com.chiyu.shopapp.bean;

public class Security {
	//03-17 10:36:43.220: I/tripshop(28020): 验证码来了！！！{"code":"200","message":"验证码发送成功","result":"583785"}
	private String code;
	private String message;
	private String result;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "Security [code=" + code + ", message=" + message + ", result="
				+ result + "]";
	}
}
