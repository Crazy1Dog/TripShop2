package com.chiyu.shopapp.bean;
/**
 * 计调实体类：
 * {"code":"200","message":"查询成功",
 * "result":{"companyid":"305608",
 * "address":"田林路487号",
 * "contactname":"微微",
 * "companyname":"where",
 * "photopath":"/00/o/0/1452656810484.jpg",
 * "mobile":"15136450588"}}
 * @author chiyu_xulailie
 *
 */
public class MemberEntity {
	private String companyid;//公司ID
	private String address;//计调地址
	private String contactname;//顾问名字
	private String companyname;//公司名字
	private String photopath;//顾问头像地址
	private String mobile;//顾问联系电话
	private String username;
	
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getPhotopath() {
		return photopath;
	}
	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "MemberEntity [companyid=" + companyid + ", address=" + address
				+ ", contactname=" + contactname + ", companyname="
				+ companyname + ", photopath=" + photopath + ", mobile="
				+ mobile + ", username=" + username + "]";
	}
	public MemberEntity(String companyid, String address, String contactname,
			String companyname, String photopath, String mobile, String username) {
		super();
		this.companyid = companyid;
		this.address = address;
		this.contactname = contactname;
		this.companyname = companyname;
		this.photopath = photopath;
		this.mobile = mobile;
		this.username = username;
	}
	
}
