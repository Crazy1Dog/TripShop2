package com.chiyu.shopapp.bean;

public class VersionEntity {
	private String mold;
	private String category;
	private String versionNumber;
	private String content;
	private String personnel;
	private String state;
	private String downloadUrl;
	private String updatetime;
	private String notes;
	public VersionEntity(String mold, String category, String versionNumber,
			String content, String personnel, String state, String downloadUrl,
			String updatetime, String notes) {
		super();
		this.mold = mold;
		this.category = category;
		this.versionNumber = versionNumber;
		this.content = content;
		this.personnel = personnel;
		this.state = state;
		this.downloadUrl = downloadUrl;
		this.updatetime = updatetime;
		this.notes = notes;
	}
	public String getMold() {
		return mold;
	}
	public void setMold(String mold) {
		this.mold = mold;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPersonnel() {
		return personnel;
	}
	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@Override
	public String toString() {
		return "VersionEntity [mold=" + mold + ", category=" + category
				+ ", versionNumber=" + versionNumber + ", content=" + content
				+ ", personnel=" + personnel + ", state=" + state
				+ ", downloadUrl=" + downloadUrl + ", updatetime=" + updatetime
				+ ", notes=" + notes + "]";
	} 
	
}
