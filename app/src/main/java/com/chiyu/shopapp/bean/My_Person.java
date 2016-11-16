package com.chiyu.shopapp.bean;

public class My_Person {
	
	private int category;
	private String mobile;
	private String detail;
	private int gender;
    private String orderId;
    private String lineId;
    private String b2bId;
    private int cardCategory;
    private String idcard;
    private String name;
    private String id;
    private String ischoose;//1选中 2不选中
	
	
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getB2bId() {
		return b2bId;
	}
	public void setB2bId(String b2bId) {
		this.b2bId = b2bId;
	}
	public int getCardCategory() {
		return cardCategory;
	}
	public void setCardCategory(int cardCategory) {
		this.cardCategory = cardCategory;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIschoose() {
		return ischoose;
	}
	public void setIschoose(String ischoose) {
		this.ischoose = ischoose;
	}
	public My_Person(int category, String mobile, String detail, int gender,
			String orderId, String lineId, String b2bId, int cardCategory,
			String idcard, String name, String id) {
		super();
		this.category = category;
		this.mobile = mobile;
		this.detail = detail;
		this.gender = gender;
		this.orderId = orderId;
		this.lineId = lineId;
		this.b2bId = b2bId;
		this.cardCategory = cardCategory;
		this.idcard = idcard;
		this.name = name;
		this.id = id;
	}
	public My_Person() {
		super();
	}

	
}
