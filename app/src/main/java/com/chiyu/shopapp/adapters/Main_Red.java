package com.chiyu.shopapp.adapters;

public class Main_Red {
	private int code;
	private String message;
	private String registerAmount;
	private String forwardAmount;
	private int openRed;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRegisterAmount() {
		return registerAmount;
	}
	public void setRegisterAmount(String registerAmount) {
		this.registerAmount = registerAmount;
	}
	public String getForwardAmount() {
		return forwardAmount;
	}
	public void setForwardAmount(String forwardAmount) {
		this.forwardAmount = forwardAmount;
	}
	public int getOpenRed() {
		return openRed;
	}
	public void setOpenRed(int openRed) {
		this.openRed = openRed;
	}
	public Main_Red(String registerAmount,
			String forwardAmount, int openRed) {
		super();
		this.registerAmount = registerAmount;
		this.forwardAmount = forwardAmount;
		this.openRed = openRed;
	}

}
