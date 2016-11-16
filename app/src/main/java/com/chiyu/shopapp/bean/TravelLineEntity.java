package com.chiyu.shopapp.bean;
/**
 * 产品线路实体
 * @author chiyu
 *
 */
public class TravelLineEntity {
	private int photo_count;
	private String lineId;
	private String dateId;
	private String title;
	private String sub_title;
	private String departure;
	private String destination;
	private String days;
	private String count;
	private String photo;
	private String is_receive;
	private String price;
	private String marketPrice;
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	private String go_time;
	private String dateList;
	private String photo_path;
	public TravelLineEntity(int photo_count, String lineId, String dateId,
			String title, String sub_title, String departure,
			String destination, String days, String count, String photo,
			String is_receive, String price, String marketPrice,
			String go_time, String dateList, String photo_path) {
		super();
		this.photo_count = photo_count;
		this.lineId = lineId;
		this.dateId = dateId;
		this.title = title;
		this.sub_title = sub_title;
		this.departure = departure;
		this.destination = destination;
		this.days = days;
		this.count = count;
		this.photo = photo;
		this.is_receive = is_receive;
		this.price = price;
		this.marketPrice = marketPrice;
		this.go_time = go_time;
		this.dateList = dateList;
		this.photo_path = photo_path;
	}
	public int getPhoto_count() {
		return photo_count;
	}
	public void setPhoto_count(int photo_count) {
		this.photo_count = photo_count;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getDateId() {
		return dateId;
	}
	public void setDateId(String dateId) {
		this.dateId = dateId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getIs_receive() {
		return is_receive;
	}
	public void setIs_receive(String is_receive) {
		this.is_receive = is_receive;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getGo_time() {
		return go_time;
	}
	public void setGo_time(String go_time) {
		this.go_time = go_time;
	}
	public String getDateList() {
		return dateList;
	}
	public void setDateList(String dateList) {
		this.dateList = dateList;
	}
	public String getPhoto_path() {
		return photo_path;
	}
	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}
	@Override
	public String toString() {
		return "TravelLineEntity [photo_count=" + photo_count + ", lineId="
				+ lineId + ", dateId=" + dateId + ", title=" + title
				+ ", sub_title=" + sub_title + ", departure=" + departure
				+ ", destination=" + destination + ", days=" + days
				+ ", count=" + count + ", photo=" + photo + ", is_receive="
				+ is_receive + ", price=" + price + ", marketPrice="
				+ marketPrice + ", go_time=" + go_time + ", dateList="
				+ dateList + ", photo_path=" + photo_path + "]";
	}
	
	
	
}
