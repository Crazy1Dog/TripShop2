package com.chiyu.shopapp.bean;

public class TurnedDeparture {
	private int type;
	private String address;
	public TurnedDeparture(int type, String address) {
		super();
		this.type = type;
		this.address = address;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "TurnedDeparture [type=" + type + ", address=" + address + "]";
	}
	
}
