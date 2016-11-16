package com.chiyu.shopapp.bean;
/**
 * 出发地实体类
 * @author chiyu
 *
 */
public class Departure {
	private String letter;
	private String departure;
	public Departure(String letter, String departure) {
		super();
		this.letter = letter;
		this.departure = departure;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	@Override
	public String toString() {
		return "Departure [letter=" + letter + ", departure=" + departure + "]";
	}
	
}
