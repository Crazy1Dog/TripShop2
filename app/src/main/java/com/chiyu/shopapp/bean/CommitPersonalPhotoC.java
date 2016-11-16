package com.chiyu.shopapp.bean;
/**
 * {"code":"200","message":"更新成功","result":"upload/images/receive_app/20160415/2b102c6468a743b094320f6080a60b0d.png"}

 * **/
public class CommitPersonalPhotoC {
	private String code;
	private String message;
	private String result;
	@Override
	public String toString() {
		return "CommitPersonalPhotoC [code=" + code + ", message=" + message
				+ ", result=" + result + "]";
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
