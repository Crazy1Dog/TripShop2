package com.chiyu.shopapp.bean;

public class Line_DateEntity {

	private String goTime;
	private String endTime;
	private String lineId;
	private int personOrder;
	private int person;
	private long adultPrice;
	private long childPrice;
	private long babyPrice;
	private String id;
    private int isReceive;//判断是否是自发  0 自发 1 采购
    private int leaveseats;//采购线路余位
	
	public int getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(int isReceive) {
		this.isReceive = isReceive;
	}

	public int getLeaveseats() {
		return leaveseats;
	}

	public void setLeaveseats(int leaveseats) {
		this.leaveseats = leaveseats;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGoTime() {
		return goTime;
	}

	public void setGoTime(String goTime) {
		this.goTime = goTime;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public int getPersonOrder() {
		return personOrder;
	}

	public void setPersonOrder(int personOrder) {
		this.personOrder = personOrder;
	}

	public int getPerson() {
		return person;
	}

	public void setPerson(int person) {
		this.person = person;
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

	public Line_DateEntity() {
		super();
	}

	public Line_DateEntity(String goTime, String lineId, int personOrder,
			int person, long adultPrice, long childPrice, long babyPrice,String endTime,String id, int isReceive,
     int leaveseats) {
		super();
		this.goTime = goTime;
		this.lineId = lineId;
		this.personOrder = personOrder;
		this.person = person;
		this.adultPrice = adultPrice;
		this.childPrice = childPrice;
		this.babyPrice = babyPrice;
		this.endTime = endTime;
		this.id = id;
		this.isReceive = isReceive;
		this.leaveseats = leaveseats;
	}

	@Override
	public String toString() {
		return "Line_dateEntity [goTime=" + goTime + ", lineId=" + lineId
				+ ",personOrder=" + personOrder + "," + "person=" + person
				+ ",adultPrice=" + adultPrice + ",childPrice=" + childPrice
				+ ",babyPrice=" + babyPrice+",endTime=" + endTime +",id=" + id+"]";
	}

}
