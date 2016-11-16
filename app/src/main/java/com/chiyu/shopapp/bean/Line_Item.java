package com.chiyu.shopapp.bean;

import java.io.Serializable;

public class Line_Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private int gender;
	private int category;
	private String idcard;
	private String mobile;
	private String detail;
	private String price;
	private String tel;
	private String remark;
	private int singleroom;
	private int adjustprice;
	private String takeprice;
	private int Ischoose;
	private int del;
	private String virtualmoney;
	private String siteid;
	
   private String msg;
   private int code;
   
	public int getDel() {
	return del;
}

public void setDel(int del) {
	this.del = del;
}

	public int getIschoose() {
		return Ischoose;
	}

	public void setIschoose(int ischoose) {
		Ischoose = ischoose;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	public int getSingleroom() {
		return singleroom;
	}

	public void setSingleroom(int singleroom) {
		this.singleroom = singleroom;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	

	public int getAdjustprice() {
		return adjustprice;
	}

	public void setAdjustprice(int adjustprice) {
		this.adjustprice = adjustprice;
	}

	public String getTakeprice() {
		return takeprice;
	}

	public void setTakeprice(String takeprice) {
		this.takeprice = takeprice;
	}

	public String getVirtualmoney() {
		return virtualmoney;
	}

	public void setVirtualmoney(String virtualmoney) {
		this.virtualmoney = virtualmoney;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public Line_Item(String msg, int code) {
		super();
		this.msg = msg;
		this.code = code;
	}

	public Line_Item(String title, int category, String mobile, String tel,
			String idcard, int gender, String remark, int singleroom,int adjustprice) {
		super();
		this.title = title;
		this.category = category;
		this.mobile = mobile;
		this.tel = tel;
		this.idcard = idcard;
		this.gender = gender;
		this.remark = remark;
		this.singleroom = singleroom;
		this.adjustprice = adjustprice;
	}

	public Line_Item() {
		super();
	}

	public Line_Item(String id, String title, int gender, int category,
			String idcard, String mobile, String detail, String price,
			int singleroom, int adjustprice, String takeprice) {
		super();
		this.id = id;
		this.title = title;
		this.gender = gender;
		this.category = category;
		this.idcard = idcard;
		this.mobile = mobile;
		this.detail = detail;
		this.price = price;
		this.singleroom = singleroom;
		this.adjustprice = adjustprice;
		this.takeprice = takeprice;
	}

}
