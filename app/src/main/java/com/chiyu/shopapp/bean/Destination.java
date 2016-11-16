package com.chiyu.shopapp.bean;
/**
 * 目的地实体类
 * @author chiyu
 *
 */
public class Destination {
	private String number;
	private String letter ;
	private String destination;
	public Destination(String number, String letter, String destination) {
		super();
		this.number = number;
		this.letter = letter;
		this.destination = destination;
	}
	

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Destination [number=" + number + ", letter=" + letter
				+ ", destination=" + destination + "]";
	}
	
	
}
