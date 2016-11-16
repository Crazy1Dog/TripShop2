package com.chiyu.shopapp.bean;

public class WxPay {
	private String appid;
	private String noncestr;
	private String packages;
	private String partnerid;
	private String prepayid;
	private String sign;
	private String timestamp;
	private String orderquery;
	
	public String getOrderquery() {
		return orderquery;
	}
	public void setOrderquery(String orderquery) {
		this.orderquery = orderquery;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public WxPay(String appid, String noncestr, String packages,
			String partnerid, String prepayid, String sign, String timestamp,String orderquery) {
		super();
		this.appid = appid;
		this.noncestr = noncestr;
		this.packages = packages;
		this.partnerid = partnerid;
		this.prepayid = prepayid;
		this.sign = sign;
		this.timestamp = timestamp;
		this.orderquery = orderquery;
	}
}
