package com.chiyu.shopapp.bean;

import java.io.Serializable;

public class RedEnvelope implements Serializable {

	/**
	 * 红包列表基础类
	 */
	private static final long serialVersionUID = 1L;
	private String ischoose;//1選中 2不選中
	 private long createTime;
	 private long expiredTime;
	 private String userId;
     private long amount;
     private String id;
     private int type;
     private int status;
	public String getIschoose() {
		return ischoose;
	}
	public void setIschoose(String ischoose) {
		this.ischoose = ischoose;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public RedEnvelope( long createTime, long expiredTime,
			String userId, long amount, String id, int type, int status) {
		super();
		this.createTime = createTime;
		this.expiredTime = expiredTime;
		this.userId = userId;
		this.amount = amount;
		this.id = id;
		this.type = type;
		this.status = status;
	}
	public RedEnvelope() {
		super();
	}
}
