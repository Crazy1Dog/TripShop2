package com.chiyu.shopapp.bean;

public class Order_PayinfoEntity {
	private String message;
	private int code;
	private String result;
	private String verify_sign;

	public String getVerify_sign() {
		return verify_sign;
	}

	public void setVerify_sign(String verify_sign) {
		this.verify_sign = verify_sign;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Order_PayinfoEntity(int code,String message,  String result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public Order_PayinfoEntity(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}

	public Order_PayinfoEntity(int code, String message, String result,
			String verify_sign) {
		super();
		this.message = message;
		this.code = code;
		this.result = result;
		this.verify_sign = verify_sign;
	}



}
