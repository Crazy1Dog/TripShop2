package com.chiyu.shopapp.bean;

public class StoreEntity {
	/**
	 * {"code":"200","message":"操作成功","result":{"计调ID:memberId":"351190",
	 * "companyId":"305608","微店ID：receiveguestId":"4130"}}
	 */
	private String memberId;
	private String companyId;
	private String receiveguestId;
	private String photopath;

	public String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public StoreEntity(String memberId, String companyId, String receiveguestId) {
		super();
		this.memberId = memberId;
		this.companyId = companyId;
		this.receiveguestId = receiveguestId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getReceiveguestId() {
		return receiveguestId;
	}

	public void setReceiveguestId(String receiveguestId) {
		this.receiveguestId = receiveguestId;
	}

	@Override
	public String toString() {
		return "StoreEntity [memberId=" + memberId + ", companyId=" + companyId
				+ ", receiveguestId=" + receiveguestId + ", photopath="
				+ photopath + "]";
	}

	

}
