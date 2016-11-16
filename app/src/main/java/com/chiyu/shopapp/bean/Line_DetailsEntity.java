package com.chiyu.shopapp.bean;

public class Line_DetailsEntity {
	/****
	 * {"code":"200","message":"线路详情基础信息查询成功","result":{"photoIds":"237665",
	 * "destinationId"
	 * :"{\"state\":\"100001\",\"province\":\"100169\",\"city\":\"100170\"}"
	 * ,"goTrafficDetail":"","backTrafficDetail":"","trafficTool":5,"catIds":
	 * "27446,27454,27456,27452,27458,58431,58435,58461,58459,27464,27462,27460,58410,58414,58418,58423,58425"
	 * ,"category":0,"title":"杭州雷峰塔、乌镇古镇、苏州狮子林、无锡太湖四日游","photoPath":
	 * "00/o/0/143833772517.jpg","goTime":"2016-03-18","departureId":
	 * "{\"state\":\"100001\",\"province\":\"102778\",\"city\":\"100135\"}"
	 * ,"managerRecommended"
	 * :"0,1,2,3,4,5,6,15","recommendedDetail":"的进口矿","praiseCount"
	 * :1,"adultPriceMarket"
	 * :878.00,"childPriceMarket":430.00,"babyPriceMarket":0.00
	 * ,"singleRoomMarket"
	 * :150.00,"lineId":579,"leaveseats":30,"personOrder":0,"person"
	 * :30,"companyId":137057,"departure":
	 * "{\"state\":\"国内\",\"province\":\"上海\",\"city\":\"上海\"}"
	 * ,"destination":"{\"state\":\"国内\",\"province\":\"浙江\",\"city\":\"杭州\"}"
	 * ,"adultPrice"
	 * :878.00,"childPrice":430.00,"babyPrice":0.00,"backTime":"2016-03-21"
	 * ,"isPay"
	 * :0,"goTraffic":null,"backTraffic":null,"isTakeAdult":1,"isTakeChild"
	 * :1,"isTakeBaby":1,"singleRoom":150.00,"isReceive":1,"imageUrl":
	 * "00/o/0/143833772517.jpg","days":4,"endTime":"2016-03-18"}}
	 */
	private String photoIds;
	private String photoPath;
	private String title;
	private String lineId;
	private long adultPriceMarket;
	private long childPriceMarket;
	private long babyPriceMarket;
	private int trafficTool;
	private int goTraffic;
	private int backTraffic;
	private String imageUrl;
	private int days;
	private String praiseCount;
	private String departure;
	private String destination;
	private String managerRecommended;// 推荐
	private String recommendedDetail;
	private int category;
	private String date;
	private String isReceive;
	private int isPay;
	private String goTime;
	private String backTime;
	private long singleRoom;
	private long childPrice;
	private long adultPrice;
	private long babyPrice;

	private String destinationId;
	private String goTrafficDetail;
	private String backTrafficDetail;
	private String catIds;
	private String departureId;
	private long singleRoomMarket;
	private String leaveseats;
	private int personOrder;
	private int person;
	private String companyId;
	private int isTakeAdult;
	private int isTakeChild;
	private int isTakeBaby;
	private String endTime;
	private Double totalPrice;// 订单总金额
	private double singleRoomAmount;// 单房差总金额
	private Double b2bAmount;// b2b总金额

	

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	



	public double getSingleRoomAmount() {
		return singleRoomAmount;
	}

	public void setSingleRoomAmount(double singleRoomAmount) {
		this.singleRoomAmount = singleRoomAmount;
	}

	public Double getB2bAmount() {
		return b2bAmount;
	}

	public void setB2bAmount(Double b2bAmount) {
		this.b2bAmount = b2bAmount;
	}

	public String getPhotoIds() {
		return photoIds;
	}

	public void setPhotoIds(String photoIds) {
		this.photoIds = photoIds;
	}

	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	public String getGoTrafficDetail() {
		return goTrafficDetail;
	}

	public void setGoTrafficDetail(String goTrafficDetail) {
		this.goTrafficDetail = goTrafficDetail;
	}

	public String getBackTrafficDetail() {
		return backTrafficDetail;
	}

	public void setBackTrafficDetail(String backTrafficDetail) {
		this.backTrafficDetail = backTrafficDetail;
	}

	public int getTrafficTool() {
		return trafficTool;
	}

	public void setTrafficTool(int trafficTool) {
		this.trafficTool = trafficTool;
	}

	public String getCatIds() {
		return catIds;
	}

	public void setCatIds(String catIds) {
		this.catIds = catIds;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getGoTime() {
		return goTime;
	}

	public void setGoTime(String goTime) {
		this.goTime = goTime;
	}

	public String getDepartureId() {
		return departureId;
	}

	public void setDepartureId(String departureId) {
		this.departureId = departureId;
	}

	public String getManagerRecommended() {
		return managerRecommended;
	}

	public void setManagerRecommended(String managerRecommended) {
		this.managerRecommended = managerRecommended;
	}

	public String getRecommendedDetail() {
		return recommendedDetail;
	}

	public void setRecommendedDetail(String recommendedDetail) {
		this.recommendedDetail = recommendedDetail;
	}

	public String getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(String praiseCount) {
		this.praiseCount = praiseCount;
	}

	public long getAdultPriceMarket() {
		return adultPriceMarket;
	}

	public void setAdultPriceMarket(long adultPriceMarket) {
		this.adultPriceMarket = adultPriceMarket;
	}

	public long getChildPriceMarket() {
		return childPriceMarket;
	}

	public void setChildPriceMarket(long childPriceMarket) {
		this.childPriceMarket = childPriceMarket;
	}

	public long getBabyPriceMarket() {
		return babyPriceMarket;
	}

	public void setBabyPriceMarket(long babyPriceMarket) {
		this.babyPriceMarket = babyPriceMarket;
	}

	public long getSingleRoomMarket() {
		return singleRoomMarket;
	}

	public void setSingleRoomMarket(long singleRoomMarket) {
		this.singleRoomMarket = singleRoomMarket;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLeaveseats() {
		return leaveseats;
	}

	public void setLeaveseats(String leaveseats) {
		this.leaveseats = leaveseats;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public long getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(long childPrice) {
		this.childPrice = childPrice;
	}

	public long getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(long adultPrice) {
		this.adultPrice = adultPrice;
	}

	public long getBabyPrice() {
		return babyPrice;
	}

	public void setBabyPrice(long babyPrice) {
		this.babyPrice = babyPrice;
	}

	public String getBackTime() {
		return backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public int getIsPay() {
		return isPay;
	}

	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}

	public int getGoTraffic() {
		return goTraffic;
	}

	public void setGoTraffic(int goTraffic) {
		this.goTraffic = goTraffic;
	}

	public int getBackTraffic() {
		return backTraffic;
	}

	public void setBackTraffic(int backTraffic) {
		this.backTraffic = backTraffic;
	}

	public long getIsTakeAdult() {
		return isTakeAdult;
	}

	public int getIsTakeChild() {
		return isTakeChild;
	}

	public void setIsTakeChild(int isTakeChild) {
		this.isTakeChild = isTakeChild;
	}

	public int getIsTakeBaby() {
		return isTakeBaby;
	}

	public void setIsTakeBaby(int isTakeBaby) {
		this.isTakeBaby = isTakeBaby;
	}

	public void setIsTakeAdult(int isTakeAdult) {
		this.isTakeAdult = isTakeAdult;
	}

	public long getSingleRoom() {
		return singleRoom;
	}

	public void setSingleRoom(long singleRoom) {
		this.singleRoom = singleRoom;
	}

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Line_DetailsEntity() {
		super();
	}

	public Line_DetailsEntity(String date) {
		super();
		this.date = date;
	}

	public Line_DetailsEntity(String photoIds, String photoPath, String title,
			String lineId, long adultPriceMarket, long childPriceMarket,
			long babyPriceMarket, int trafficTool, int goTraffic,
			int backTraffic, String imageUrl, int days, String praiseCount,
			String departure, String destination, String managerRecommended,
			String recommendedDetail, int category, String isReceive,
			int isPay, String goTime, String backTime, long singleRoom,
			long childPrice, long adultPrice, long babyPrice, int isTakeAdult,
			int isTakeChild, int isTakeBaby, Double totalPrice,
			Double singleRoomAmount, Double b2bAmount,String companyId) {
		super();
		this.photoIds = photoIds;
		this.photoPath = photoPath;
		this.title = title;
		this.lineId = lineId;
		this.adultPriceMarket = adultPriceMarket;
		this.childPriceMarket = childPriceMarket;
		this.babyPriceMarket = babyPriceMarket;
		this.trafficTool = trafficTool;
		this.goTraffic = goTraffic;
		this.backTraffic = backTraffic;
		this.imageUrl = imageUrl;
		this.days = days;
		this.praiseCount = praiseCount;
		this.departure = departure;
		this.destination = destination;
		this.managerRecommended = managerRecommended;
		this.recommendedDetail = recommendedDetail;
		this.category = category;
		this.isReceive = isReceive;
		this.isPay = isPay;
		this.goTime = goTime;
		this.backTime = backTime;
		this.singleRoom = singleRoom;
		this.childPrice = childPrice;
		this.adultPrice = adultPrice;
		this.babyPrice = babyPrice;
		this.isTakeAdult = isTakeAdult;
		this.isTakeChild = isTakeChild;
		this.isTakeBaby = isTakeBaby;
		this.totalPrice = totalPrice;// 订单总金额
		this.singleRoomAmount = singleRoomAmount;// 单房差总金额
		this.b2bAmount = b2bAmount;// b2b总金额
		this.companyId = companyId;// b2b总金额
	}

	@Override
	public String toString() {
		return "Line_DetailsEntity [photoIds=" + photoIds + ", photoPath="
				+ photoPath + ", title=" + title + ", lineId=" + lineId
				+ ", adultPriceMarket=" + adultPriceMarket
				+ ", childPriceMarket=" + childPriceMarket
				+ ", babyPriceMarket=" + babyPriceMarket + ", trafficTool="
				+ trafficTool + ", goTraffic=" + goTraffic + ", backTraffic="
				+ backTraffic + ", imageUrl=" + imageUrl + ", days=" + days
				+ ",praiseCount=" + praiseCount + ",departure=" + departure
				+ ",destination=" + destination + ", managerRecommended="
				+ managerRecommended + ", recommendedDetail="
				+ recommendedDetail + ",category=" + category + ",date=" + date
				+ ",isReceive=" + isReceive + ",isPay=" + isPay + ",goTime="
				+ goTime + ",backTime=" + backTime + ",singleRoom="
				+ singleRoom + ",childPrice=" + childPrice + ",adultPrice="
				+ adultPrice + ",babyPrice=" + babyPrice + "]";

	}

}
