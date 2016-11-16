package com.chiyu.shopapp.bean;

public class TripDayEntity {
	private int type;
	private String days;
	public TripDayEntity(int type, String days) {
		super();
		this.type = type;
		this.days = days;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	@Override
	public String toString() {
		return "TripDayEntity [type=" + type + ", days=" + days + "]";
	}
	
}
