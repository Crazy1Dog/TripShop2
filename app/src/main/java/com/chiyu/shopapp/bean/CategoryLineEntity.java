package com.chiyu.shopapp.bean;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * "receiveGuestId": 4130, 
    "sort": 1, 
    "createDate": 1451836800000, 
     "mark": 1, 
     "categoryName": "亲子游", 
     "isUseable": 0, 
     "icon": "/upload/receive_category/parent.png", 
     "id": 16812
 * @author chiyu
 *
 */
public class CategoryLineEntity implements Parcelable{
	private String receiveGuestId;
	private String sort;
	private String createDate;
	private String mark;
	private String categoryName;
	private String isUseable;
	private String icon;
	private String id;

	public CategoryLineEntity() {
		super();
	}
	@SuppressWarnings("unchecked")
	public static final Creator<CategoryLineEntity> CREATOR = new Creator() {

		@Override
		public CategoryLineEntity createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			
			CategoryLineEntity categoryLineEntity = new CategoryLineEntity();
			categoryLineEntity.setReceiveGuestId(source.readString());
			categoryLineEntity.setSort(source.readString());
			categoryLineEntity.setCreateDate(source.readString());
			categoryLineEntity.setMark(source.readString());
			categoryLineEntity.setCategoryName(source.readString());
			categoryLineEntity.setIsUseable(source.readString());
			categoryLineEntity.setIcon(source.readString());
			categoryLineEntity.setId(source.readString());
			return categoryLineEntity;
		}

		@Override
		public CategoryLineEntity[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CategoryLineEntity[size];
		}
	};
	public CategoryLineEntity(String receiveGuestId, String sort,
			String createDate, String mark, String categoryName,
			String isUseable, String icon, String id) {
		super();
		this.receiveGuestId = receiveGuestId;
		this.sort = sort;
		this.createDate = createDate;
		this.mark = mark;
		this.categoryName = categoryName;
		this.isUseable = isUseable;
		this.icon = icon;
		this.id = id;
	}
	public String getReceiveGuestId() {
		return receiveGuestId;
	}
	public void setReceiveGuestId(String receiveGuestId) {
		this.receiveGuestId = receiveGuestId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getIsUseable() {
		return isUseable;
	}
	public void setIsUseable(String isUseable) {
		this.isUseable = isUseable;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CategoryLineEntity [receiveGuestId=" + receiveGuestId
				+ ", sort=" + sort + ", createDate=" + createDate + ", mark="
				+ mark + ", categoryName=" + categoryName + ", isUseable="
				+ isUseable + ", icon=" + icon + ", id=" + id + "]";
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(categoryName);
		dest.writeString(createDate);
		dest.writeString(icon);
		dest.writeString(id);
		dest.writeString(isUseable);
		dest.writeString(mark);
		dest.writeString(receiveGuestId);
		dest.writeString(sort);
	}
	
}
