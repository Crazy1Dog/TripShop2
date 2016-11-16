package com.chiyu.shopapp.bean;

public class OrderEntity {

	/******* 订单信息 **********/
	private String createTime;
	private Double couponAmount;
	private String orderCode;
	private int lineCategory;
	private String backTime;
	private int child;
	private int isDelete;
	private String b2bOrderId;
	private String goTraffic;
	private String backTraffic;
	private int baby;
	private String lineId;
	private String orderId;
	private String redPacketsIds;
	private Double totalPrice;
	private String goTime;
	private int isPay;
	private String payEndTime;
	private int adult;
	private String companyName;
	private int orderStatus;
	private double singleRoomAmount;
	private String responsiblemobile;
	private int payStatus;
	private double orderPrice;
	private String companyId;
	private String dateId;
	private String b2cUserId;
	/********* 预订人信息 **************/
	private String mobile;
	private String createDate;
	private String tel;
	private String email;
	private String address;
	private String name;
	private String id;
	/********* 订单价格信息 *************/
	// private String createDate;
	private String b2bAdultPrice;
	private String b2bChildPrice;
	private String b2bBabyPrice;
	private long adultPrice;
	private long childPrice;
	private long babyPrice;
	private String singleRoom;
	private String adultPriceMarket;
	private String childPriceMarket;
	private String babyPriceMarket;
	/********* 订单线路信息 *************/
	private String subTitle;
	private String person;
	private String title;
	private String isTakeAdult;
	private String days;
	private String leaveseats;
	private String isTakeBaby;
	private String groupNumber;
	private String isTakeChild;
	private String brandName;
	private String lineTitle;
	private String otherInfo;
	private String photo;
	private double b2bAmount;
	/********** 预订人信息 ************/

	private int category;

	// private String mobile;
	private String detail;
	private String gender;
	// private String orderId;
	// private String lineId;
	private String b2bId;
	private String cardCategory;
	private String idcard;

	// private String name;
	// private String id;
	
	public String getCreateTime() {
		return createTime;
	}


	public double getB2bAmount() {
		return b2bAmount;
	}


	public void setB2bAmount(double b2bAmount) {
		this.b2bAmount = b2bAmount;
	}


	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getLineTitle() {
		return lineTitle;
	}

	public void setLineTitle(String lineTitle) {
		this.lineTitle = lineTitle;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	

	public Double getCouponAmount() {
		return couponAmount;
	}


	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}


	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getLineCategory() {
		return lineCategory;
	}

	public void setLineCategory(int lineCategory) {
		this.lineCategory = lineCategory;
	}

	public String getBackTime() {
		return backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getB2bOrderId() {
		return b2bOrderId;
	}

	public void setB2bOrderId(String b2bOrderId) {
		this.b2bOrderId = b2bOrderId;
	}

	public String getGoTraffic() {
		return goTraffic;
	}

	public void setGoTraffic(String goTraffic) {
		this.goTraffic = goTraffic;
	}

	public String getBackTraffic() {
		return backTraffic;
	}

	public void setBackTraffic(String backTraffic) {
		this.backTraffic = backTraffic;
	}

	public int getBaby() {
		return baby;
	}

	public void setBaby(int baby) {
		this.baby = baby;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRedPacketsIds() {
		return redPacketsIds;
	}

	public void setRedPacketsIds(String redPacketsIds) {
		this.redPacketsIds = redPacketsIds;
	}

	

	

	

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getGoTime() {
		return goTime;
	}

	public void setGoTime(String goTime) {
		this.goTime = goTime;
	}

	public int getIsPay() {
		return isPay;
	}

	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}

	public String getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(String payEndTime) {
		this.payEndTime = payEndTime;
	}

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}





	public double getSingleRoomAmount() {
		return singleRoomAmount;
	}


	public void setSingleRoomAmount(double singleRoomAmount) {
		this.singleRoomAmount = singleRoomAmount;
	}


	public String getResponsiblemobile() {
		return responsiblemobile;
	}

	public void setResponsiblemobile(String responsiblemobile) {
		this.responsiblemobile = responsiblemobile;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	

	public double getOrderPrice() {
		return orderPrice;
	}


	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}


	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDateId() {
		return dateId;
	}

	public void setDateId(String dateId) {
		this.dateId = dateId;
	}

	public String getB2cUserId() {
		return b2cUserId;
	}

	public void setB2cUserId(String b2cUserId) {
		this.b2cUserId = b2cUserId;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getB2bAdultPrice() {
		return b2bAdultPrice;
	}

	public void setB2bAdultPrice(String b2bAdultPrice) {
		this.b2bAdultPrice = b2bAdultPrice;
	}

	public String getB2bChildPrice() {
		return b2bChildPrice;
	}

	public void setB2bChildPrice(String b2bChildPrice) {
		this.b2bChildPrice = b2bChildPrice;
	}

	public String getB2bBabyPrice() {
		return b2bBabyPrice;
	}

	public void setB2bBabyPrice(String b2bBabyPrice) {
		this.b2bBabyPrice = b2bBabyPrice;
	}

	public long getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(long adultPrice) {
		this.adultPrice = adultPrice;
	}

	public long getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(long childPrice) {
		this.childPrice = childPrice;
	}

	public long getBabyPrice() {
		return babyPrice;
	}

	public void setBabyPrice(long babyPrice) {
		this.babyPrice = babyPrice;
	}

	public String getSingleRoom() {
		return singleRoom;
	}

	public void setSingleRoom(String singleRoom) {
		this.singleRoom = singleRoom;
	}

	public String getAdultPriceMarket() {
		return adultPriceMarket;
	}

	public void setAdultPriceMarket(String adultPriceMarket) {
		this.adultPriceMarket = adultPriceMarket;
	}

	public String getChildPriceMarket() {
		return childPriceMarket;
	}

	public void setChildPriceMarket(String childPriceMarket) {
		this.childPriceMarket = childPriceMarket;
	}

	public String getBabyPriceMarket() {
		return babyPriceMarket;
	}

	public void setBabyPriceMarket(String babyPriceMarket) {
		this.babyPriceMarket = babyPriceMarket;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsTakeAdult() {
		return isTakeAdult;
	}

	public void setIsTakeAdult(String isTakeAdult) {
		this.isTakeAdult = isTakeAdult;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getLeaveseats() {
		return leaveseats;
	}

	public void setLeaveseats(String leaveseats) {
		this.leaveseats = leaveseats;
	}

	public String getIsTakeBaby() {
		return isTakeBaby;
	}

	public void setIsTakeBaby(String isTakeBaby) {
		this.isTakeBaby = isTakeBaby;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getIsTakeChild() {
		return isTakeChild;
	}

	public void setIsTakeChild(String isTakeChild) {
		this.isTakeChild = isTakeChild;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getB2bId() {
		return b2bId;
	}

	public void setB2bId(String b2bId) {
		this.b2bId = b2bId;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public OrderEntity(String createTime, Double couponAmount,
			String orderCode, int lineCategory, String backTime, int child,
			int isDelete, String b2bOrderId, String goTraffic,
			String backTraffic, int baby, String lineId, String orderId,
			String redPacketsIds, Double totalPrice, String goTime, int isPay,
			String payEndTime, int adult, String companyName, int orderStatus,
			Double singleRoomAmount, String responsiblemobile, int payStatus,
			Double orderPrice, String companyId, String dateId,
			String b2cUserId, String mobile, String createDate, String tel,
			String email, String address, String name, String id,
			String b2bAdultPrice, String b2bChildPrice, String b2bBabyPrice,
			long adultPrice, long childPrice, long babyPrice,
			String singleRoom, String adultPriceMarket,
			String childPriceMarket, String babyPriceMarket, String subTitle,
			String person, String title, String isTakeAdult, String days,
			String leaveseats, String isTakeBaby, String groupNumber,
			String isTakeChild, String brandName,Double b2bAmount) {
		super();
		this.createTime = createTime;
		this.couponAmount = couponAmount;
		this.orderCode = orderCode;
		this.lineCategory = lineCategory;
		this.backTime = backTime;
		this.child = child;
		this.isDelete = isDelete;
		this.b2bOrderId = b2bOrderId;
		this.goTraffic = goTraffic;
		this.backTraffic = backTraffic;
		this.baby = baby;
		this.lineId = lineId;
		this.orderId = orderId;
		this.redPacketsIds = redPacketsIds;
		this.totalPrice = totalPrice;
		this.goTime = goTime;
		this.isPay = isPay;
		this.payEndTime = payEndTime;
		this.adult = adult;
		this.companyName = companyName;
		this.orderStatus = orderStatus;
		this.singleRoomAmount = singleRoomAmount;
		this.responsiblemobile = responsiblemobile;
		this.payStatus = payStatus;
		this.orderPrice = orderPrice;
		this.companyId = companyId;
		this.dateId = dateId;
		this.b2cUserId = b2cUserId;
		this.mobile = mobile;
		this.createDate = createDate;
		this.tel = tel;
		this.email = email;
		this.address = address;
		this.name = name;
		this.id = id;
		this.b2bAdultPrice = b2bAdultPrice;
		this.b2bChildPrice = b2bChildPrice;
		this.b2bBabyPrice = b2bBabyPrice;
		this.adultPrice = adultPrice;
		this.childPrice = childPrice;
		this.babyPrice = babyPrice;
		this.singleRoom = singleRoom;
		this.adultPriceMarket = adultPriceMarket;
		this.childPriceMarket = childPriceMarket;
		this.babyPriceMarket = babyPriceMarket;
		this.subTitle = subTitle;
		this.person = person;
		this.title = title;
		this.isTakeAdult = isTakeAdult;
		this.days = days;
		this.leaveseats = leaveseats;
		this.isTakeBaby = isTakeBaby;
		this.groupNumber = groupNumber;
		this.isTakeChild = isTakeChild;
		this.brandName = brandName;
		this.b2bAmount = b2bAmount;
	}

	public OrderEntity(String createTime, Double couponAmount, String backTime,
			int child, int baby, String id, Double totalPrice,
			String goTime, int isPay, int adult, int orderStatus,
			Double singleRoomAmount, int payStatus, Double orderPrice,
			long adultPrice, long childPrice, long babyPrice,
			String singleRoom, String otherInfo,String lineTitle,String photo,Double b2bAmount) {
		super();
		this.createTime = createTime;
		this.couponAmount = couponAmount;
		this.backTime = backTime;
		this.child = child;
		this.baby = baby;
		this.id = id;
		this.totalPrice = totalPrice;
		this.goTime = goTime;
		this.isPay = isPay;
		this.adult = adult;
		this.orderStatus = orderStatus;
		this.singleRoomAmount = singleRoomAmount;
		this.payStatus = payStatus;
		this.orderPrice = orderPrice;
		this.adultPrice = adultPrice;
		this.childPrice = childPrice;
		this.babyPrice = babyPrice;
		this.singleRoom = singleRoom;
		this.otherInfo = otherInfo;
		this.lineTitle = lineTitle;
		this.photo = photo;
		this.b2bAmount = b2bAmount;
	}

	public OrderEntity(String lineTitle,String photo) {
		super();
		this.lineTitle = lineTitle;
		this.photo = photo;
	}

	public OrderEntity() {
		super();
	}
	
	private int type;
	private String order;
	public OrderEntity(int type, String order) {
		super();
		this.type = type;
		this.order = order;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "OrderEntity [type=" + type + ", order=" + order + "]";
	}
	
}
