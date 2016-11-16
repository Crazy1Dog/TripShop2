package com.chiyu.shopapp.bean;


public class Order_SuccessEntity  {
	
	private String b2bOrderId;
    private String orderId;
    private int code;
    private String message;
	public String getB2bOrderId() {
		return b2bOrderId;
	}
	public void setB2bOrderId(String b2bOrderId) {
		this.b2bOrderId = b2bOrderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
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
	public Order_SuccessEntity( int code,
			String message,String b2bOrderId, String orderId) {
		super();
		this.code = code;
		this.message = message;
		this.b2bOrderId = b2bOrderId;
		this.orderId = orderId;
	}
	public Order_SuccessEntity(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
}
