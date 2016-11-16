package com.chiyu.shopapp.bean;

public class Order_Refund {
	private String id;
	private String alipay;
	private int status;
	private String bank;
	private int type;
	private String bankCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public Order_Refund(String id, String alipay, int status, String bank,
			int type, String bankCode) {
		super();
		this.id = id;
		this.alipay = alipay;
		this.status = status;
		this.bank = bank;
		this.type = type;
		this.bankCode = bankCode;
	}
	
}
