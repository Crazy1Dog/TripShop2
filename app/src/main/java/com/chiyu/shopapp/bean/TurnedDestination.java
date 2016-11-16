package com.chiyu.shopapp.bean;

public class TurnedDestination {
	private int type;
	private String destinationAddress;
	public TurnedDestination(int type, String destinationAddress) {
		super();
		this.type = type;
		this.destinationAddress = destinationAddress;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	@Override
	public String toString() {
		return "TurnedDestination [type=" + type + ", destinationAddress="
				+ destinationAddress + "]";
	}
	

}
