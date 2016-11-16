package com.chiyu.shopapp.bean;

public class UserEntity {
	private String userId;
	private String token;
	private String photoPath;
	
	public UserEntity(String userId, String token, String photoPath) {
		super();
		this.userId = userId;
		this.token = token;
		this.photoPath = photoPath;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", token=" + token
				+ ", photopath=" + photoPath + "]";
	}
	
	
}
