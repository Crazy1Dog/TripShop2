package com.chiyu.shopapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 名单实体 {"category":0, "mobile":null, "createDate":1459217477000, "gender":2,
 * "userId":111111, "idcard":"342623198001015524", "cardcategory":1,
 * "name":"test2", "id":9},
 * 
 * 
 * {"category":0, "mobile":"15966654441", "createDate":1459217428000,
 * "gender":1, "userId":111111, "idcard":"342623198001015521", "cardcategory":1,
 * "name":"test1", "id":7}
 * */
public class FeilName implements Parcelable {
	private String category;
	private String mobile;
	private String createDate;
	private String gender;
	private String userId;
	private String idcard;
	private String cardcategory;
	private String name;
	private String id;
	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
	// android.os.BadParcelableException:
	// Parcelable protocol requires a Parcelable.Creator object called CREATOR
	// on class com.um.demo.Person
	// 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
	// 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
	// 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
	// 5.反序列化对象
	@SuppressWarnings("unchecked")
	public static final Creator<FeilName> CREATOR = new Creator() {

		@Override
		public FeilName createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			FeilName feilName = new FeilName();
			feilName.setCategory(source.readString());
			feilName.setMobile(source.readString());
			feilName.setCreateDate(source.readString());
			feilName.setGender(source.readString());
			feilName.setUserId(source.readString());
			feilName.setIdcard(source.readString());
			feilName.setCardcategory(source.readString());
			feilName.setName(source.readString());
			feilName.setId(source.readString());
			return feilName;
		}

		@Override
		public FeilName[] newArray(int size) {
			// TODO Auto-generated method stub
			return new FeilName[size];
		}
	};

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getCardcategory() {
		return cardcategory;
	}

	public void setCardcategory(String cardcategory) {
		this.cardcategory = cardcategory;
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

	@Override
	public String toString() {
		// return "FeilName [category=" + category + ", mobile=" + mobile
		// + ", createDate=" + createDate + ", gender=" + gender
		// + ", userId=" + userId + ", idcard=" + idcard
		// + ", cardcategory=" + cardcategory + ", name=" + name + ", id="
		// + id + "]";
		if (category.equals("0")) {
			String category = "成人";
			return "游客类型:" + category + "\n游客姓名:"+ name + "\n证件号:"
					+ idcard + "\n";
		} else if (category.equals("1")) {
			String category = "儿童";
			return "游客类型:" + category + "\n游客姓名:" + name + "\n证件号:"
					+ idcard + "\n";
		} else {
			String category = "婴儿";
			return "游客类型:" + category + "\n游客姓名:" + name + "\n证件号:"
					+ idcard + "\n";
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		// 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错
		// 2.序列化对象
		/**
		 * private String category; private String mobile; private String
		 * createDate; private String gender; private String userId; private
		 * String idcard; private String cardcategory; private String name;
		 * private String id;
		 */
		dest.writeString(category);
		dest.writeString(mobile);
		dest.writeString(createDate);
		dest.writeString(gender);
		dest.writeString(userId);
		dest.writeString(idcard);
		dest.writeString(cardcategory);
		dest.writeString(name);
		dest.writeString(id);
	}

}
