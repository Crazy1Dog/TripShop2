package com.chiyu.shopapp.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.bean.Departure;
import com.chiyu.shopapp.bean.Destination;
import com.chiyu.shopapp.bean.FeilName;
import com.chiyu.shopapp.bean.Line_DateEntity;
import com.chiyu.shopapp.bean.Line_DetailsEntity;
import com.chiyu.shopapp.bean.Main_Red;
import com.chiyu.shopapp.bean.MemberEntity;
import com.chiyu.shopapp.bean.My_Person;
import com.chiyu.shopapp.bean.OrderEntity;
import com.chiyu.shopapp.bean.Order_PayinfoEntity;
import com.chiyu.shopapp.bean.Order_Refund;
import com.chiyu.shopapp.bean.Order_SuccessEntity;
import com.chiyu.shopapp.bean.RedEnvelope;
import com.chiyu.shopapp.bean.StoreEntity;
import com.chiyu.shopapp.bean.TravelLineEntity;
import com.chiyu.shopapp.bean.UserEntity;
import com.chiyu.shopapp.bean.WxPay;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ParseUtils {
	/**
	 * {"code":"200","message":"操作成功","result":{"memberId":"351190","companyId":
	 * "305608","receiveguestId":"4130"}}
	 */
	public static StoreEntity parseStoreMessage(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject object2 = object.getJSONObject("result");
			StoreEntity storeEntity = new StoreEntity(
					object2.getString("memberId"),
					object2.getString("companyId"),
					object2.getString("receiveguestId"));
			return storeEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 解析登录成功后服务器传回来的token 和 userId;
	 * {"code":"200","message":"登录成功","result":{"token"
	 * :"037524f08bae47ed90dd9d54f7609ce1","userId":"1000003"}}
	 */
	public static UserEntity getUserIdAndToken(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			int code = object.getInt("code");
			if (code == 200) {
				JSONObject object2 = object.getJSONObject("result");
				ShareUtil.putString("token", object2.getString("token"));
				ShareUtil.putString("userId", object2.getString("userId"));
				UserEntity userEntity = new UserEntity(
						object2.getString("userId"),
						object2.getString("token"),
						object2.getString("photoPath"));
				return userEntity;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * {"code":"200","message":"查询成功", "result":{"companyid":"305608",
	 * "address":"田林路487号", "contactname":"微微", "companyname":"where",
	 * "photopath":"/00/o/0/1452656810484.jpg", "mobile":"15136450588"}}
	 * 
	 * @param jsonString
	 * @return
	 */
	public static MemberEntity getMemberDetail(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject object2 = object.getJSONObject("result");
			MemberEntity memberEntity = new MemberEntity(
					object2.getString("companyid"),
					object2.getString("address"),
					object2.getString("contactname"),
					object2.getString("companyname"),
					object2.getString("photopath"),
					object2.getString("mobile"), object2.getString("username"));

			return memberEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 解析线路分类列表1
	 */
	public static List<CategoryLineEntity> parseFirstCategoryList(
			String jsonString) {
		List<CategoryLineEntity> datas = null;
		if (jsonString != null) {
			datas = new ArrayList<CategoryLineEntity>();
			try {
				JSONObject object = new JSONObject(jsonString);
				int code = object.getInt("code");
				if (code == 200) {
					JSONObject object2 = object.getJSONObject("result");
					JSONArray array = object2.getJSONArray("0");
					TypeToken<List<CategoryLineEntity>> tt = new TypeToken<List<CategoryLineEntity>>() {
					};
					datas = new Gson().fromJson(array.toString(), tt.getType());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datas;

	}

	/**
	 * 解析线路分类列表2
	 */
	public static List<CategoryLineEntity> parseSecondCategoryList(
			String jsonString) {
		List<CategoryLineEntity> datas = null;
		if (jsonString != null) {
			datas = new ArrayList<CategoryLineEntity>();
			try {
				JSONObject object = new JSONObject(jsonString);
				int code = object.getInt("code");
				if (code == 200) {
					JSONObject object2 = object.getJSONObject("result");
					JSONArray array = object2.getJSONArray("1");
					TypeToken<List<CategoryLineEntity>> tt = new TypeToken<List<CategoryLineEntity>>() {
					};
					datas = new Gson().fromJson(array.toString(), tt.getType());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datas;

	}

	/**
	 * 解析线路分类列表3
	 */
	public static List<CategoryLineEntity> parseThirdCategoryList(
			String jsonString) {
		List<CategoryLineEntity> datas = null;
		if (jsonString != null) {
			datas = new ArrayList<CategoryLineEntity>();
			try {
				JSONObject object = new JSONObject(jsonString);
				int code = object.getInt("code");
				if (code == 200) {
					JSONObject object2 = object.getJSONObject("result");
					JSONArray array = object2.getJSONArray("2");
					TypeToken<List<CategoryLineEntity>> tt = new TypeToken<List<CategoryLineEntity>>() {
					};
					datas = new Gson().fromJson(array.toString(), tt.getType());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datas;

	}

	/**
	 * 解析具体的线路产品：
	 */
	public static List<TravelLineEntity> parseTravelLineEntity(String jsonString) {
		List<TravelLineEntity> datas = null;
		if (jsonString != null) {
			datas = new ArrayList<TravelLineEntity>();
			try {
				JSONObject object = new JSONObject(jsonString);
				int code = object.getInt("code");
				if (code == 200) {
					JSONObject object2 = object.getJSONObject("result");
					JSONArray array = object2.getJSONArray("list");
					TypeToken<List<TravelLineEntity>> tt = new TypeToken<List<TravelLineEntity>>() {
					};
					datas = new Gson().fromJson(array.toString(), tt.getType());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datas;

	}

	public static List<FeilName> getContactInfo(String jsonString) {
		List<FeilName> datas = null;
		if (jsonString != null) {
			datas = new ArrayList<FeilName>();
			try {
				JSONObject object = new JSONObject(jsonString);
				int code = object.getInt("code");
				if (code == 200) {
					JSONObject object2 = object.getJSONObject("result");
					JSONArray array = object2.getJSONArray("list");
					TypeToken<List<FeilName>> tt = new TypeToken<List<FeilName>>() {
					};
					datas = new Gson().fromJson(array.toString(), tt.getType());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datas;

	}

	/**
	 * 解析出发地
	 */
	public static List<Departure> parseDepartures(String jsonString) {
		List<Departure> datas = null;
		if (jsonString != null) {
			datas = new ArrayList<Departure>();
			try {
				JSONObject object = new JSONObject(jsonString);
				int code = object.getInt("code");
				if (code == 200) {
					JSONObject object2 = object.getJSONObject("result");
					JSONArray array = object2.getJSONArray("departureList");
					TypeToken<List<Departure>> tt = new TypeToken<List<Departure>>() {
					};
					datas = new Gson().fromJson(array.toString(), tt.getType());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return datas;

	}

	/**
	 * 解析目的地
	 */
	public static List<Destination> parseDestinations(String jsonString) {
		List<Destination> datas = null;
		if (jsonString != null) {
			try {
				JSONObject object = new JSONObject(jsonString);
				int code = object.getInt("code");
				if (code == 200) {
					JSONObject object2 = object.getJSONObject("result");
					JSONArray array = object2.getJSONArray("destinationList");
					TypeToken<List<Destination>> tt = new TypeToken<List<Destination>>() {
					};
					datas = new Gson().fromJson(array.toString(), tt.getType());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return datas;

	}

	/**
	 * 解析线路详情数据
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Line_DetailsEntity getLineDetail(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject object2 = object.getJSONObject("result");
			Line_DetailsEntity line_DetailsEntity = new Line_DetailsEntity(
					object2.getString("photoIds"),
					object2.getString("photoPath"), object2.getString("title"),
					object2.getString("lineId"),
					object2.getLong("adultPriceMarket"),
					object2.getLong("childPriceMarket"),
					object2.getLong("babyPriceMarket"),
					object2.optInt("trafficTool"), object2.optInt("goTraffic"),
					object2.optInt("backTraffic"),
					object2.getString("imageUrl"), object2.getInt("days"),
					object2.getString("praiseCount"),
					object2.getString("departure"),
					object2.getString("destination"),
					object2.getString("managerRecommended"),
					object2.getString("recommendedDetail"),
					object2.getInt("category"), object2.getString("isReceive"),
					object2.getInt("isPay"), object2.getString("goTime"),
					object2.getString("backTime"),
					object2.getLong("singleRoom"),
					object2.getLong("childPrice"),
					object2.getLong("adultPrice"),
					object2.getLong("babyPrice"),
					object2.getInt("isTakeAdult"),
					object2.getInt("isTakeChild"),
					object2.getInt("isTakeBaby"),
					object2.optDouble("totalPrice"),
					object2.optDouble("singleRoomAmount"),
					object2.optDouble("b2bAmount"),object2.getString("companyId"));
			return line_DetailsEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析团期数据
	 * 
	 * @param jsonString
	 * @return
	 */
	public static ArrayList<Line_DateEntity> getLineDate(String jsonString) {
		ArrayList<Line_DateEntity> linDateEntitieslist = new ArrayList<Line_DateEntity>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjects = (JSONObject) jsonArray.opt(i);
				Line_DateEntity line_dateEntity = new Line_DateEntity();

				line_dateEntity.setGoTime(jsonObjects.optString("goTime"));
				line_dateEntity.setBabyPrice(jsonObjects.optLong("babyPrice"));
				line_dateEntity
						.setAdultPrice(jsonObjects.optLong("adultPrice"));
				line_dateEntity
						.setChildPrice(jsonObjects.optLong("childPrice"));
				line_dateEntity.setLineId(jsonObjects.optString("lineId"));
				line_dateEntity.setPerson(jsonObjects.optInt("person"));
				line_dateEntity.setPersonOrder(jsonObjects
						.optInt("personOrder"));
				line_dateEntity.setEndTime(jsonObjects.optString("endTime"));
				line_dateEntity.setId(jsonObjects.optString("id"));
				line_dateEntity.setIsReceive(jsonObjects.optInt("isReceive"));
				line_dateEntity.setLeaveseats(jsonObjects.optInt("leaveseats"));

				linDateEntitieslist.add(line_dateEntity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return linDateEntitieslist;
	}

	/**
	 * 解析红包列表
	 * 
	 * @param jsonString
	 * @return
	 */
	public static ArrayList<RedEnvelope> getRedEnvelop(String jsonString) {
		ArrayList<RedEnvelope> redEnvelopes = new ArrayList<RedEnvelope>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjects = (JSONObject) jsonArray.opt(i);
				RedEnvelope redEnvelope = new RedEnvelope();

				redEnvelope.setCreateTime(jsonObjects.optLong("createTime"));
				redEnvelope.setExpiredTime(jsonObjects.optLong("expiredTime"));
				redEnvelope.setUserId(jsonObjects.optString("userId"));
				redEnvelope.setAmount(jsonObjects.optInt("amount"));
				redEnvelope.setId(jsonObjects.optString("id"));
				redEnvelope.setType(jsonObjects.optInt("type"));
				redEnvelope.setStatus(jsonObjects.optInt("status"));

				redEnvelopes.add(redEnvelope);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return redEnvelopes;
	}

	/***
	 * 解析出发城市
	 * 
	 * @param ss
	 * @return
	 */
	public static String jsonTest(String ss) {
		String loginNames = "";
		try {
			String jsonString = "";
			if (ss.indexOf("[") > -1) {
				jsonString = "{" + "result:" + ss + "}";
			} else {
				jsonString = "{" + "result:" + "[" + ss + "]" + "}";
			}
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjects = (JSONObject) jsonArray.opt(i);
				if ("".equals(jsonObjects.optString("city"))
						|| jsonObjects.optString("city") == null) {
					loginNames = jsonObjects.optString("province");
				} else {
					loginNames = jsonObjects.optString("city");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginNames;
	}

	/**
	 * 预定订单数据解析
	 */
	public static Order_SuccessEntity getOrderSuccess(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			Order_SuccessEntity order_SuccessEntity;
			if (object.getInt("code") == 200) {

				JSONObject object2 = object.getJSONObject("result");
				order_SuccessEntity = new Order_SuccessEntity(
						object.getInt("code"), object.getString("message"),
						object2.getString("b2bOrderId"),
						object2.getString("orderId"));
			} else {
				order_SuccessEntity = new Order_SuccessEntity(
						object.getInt("code"), object.getString("message"));
			}
			return order_SuccessEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 预定订单数据解析 {"message":"add success", "result":{"mobile_data":
	 * "partner=\"2088301970153142\"&seller_id=\"2088301970153142 \
	 * "&out_trade_no=\"2010000975\"&subject=\"other-order\"&body=\"other-order
	 * \
	 * "&total_fee=\"0.01\"&notify_url=\"http:\/\/test.pay.service.etu6.org\/not
	 * i f y ? platform=alipaymobile\
	 * "&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&return_url=\"\"&sign=\"YhGiZzsiLbIcRT1Z2%2FgV8zsanSTxgSyJrxfnQ1G3KMXLiESJmudJRvQIXemsfooN1qXFip88sYxpXv2RxrrR2ETMmgHM3U7USDXSa%2FvYrWjcob73%2ByWb0PUFComM9NwGME02XLFA3VfGGkWBpDnyddyT%2FL56LYGZojwQKygx%2BqY%3D\"&sign_type=\"RSA\""
	 * },"code":"200"}
	 */
	// {"code":"200","message":"success","result":0}
	public static Order_PayinfoEntity getOrderPayinfo(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			Order_PayinfoEntity order_PayinfoEntity;
			if (object.getInt("code") == 200) {
				// JSONObject object2 = object.getJSONObject("result");
				System.out.println("jsonString=========" + jsonString);
				order_PayinfoEntity = new Order_PayinfoEntity(
						object.getInt("code"), object.getString("message"),
						object.getString("result"));
			} else {
				order_PayinfoEntity = new Order_PayinfoEntity(
						object.getString("message"), object.getInt("code"));
			}
			return order_PayinfoEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/*****
	 * 解析微信支付数据
	 * 
	 * @param jsonString
	 * @return
	 */
	public static WxPay PayInfo(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject object2 = object.getJSONObject("result");
			WxPay storeEntity = new WxPay(object2.getString("appid"),
					object2.getString("noncestr"),
					object2.getString("package"),
					object2.getString("partnerid"),
					object2.getString("prepayid"), object2.getString("sign"),
					object2.getString("timestamp"),
					object2.getString("orderquery"));
			return storeEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/********
	 * 支付宝支付回调{"code":"200","message":"search success","result":{"verify_sign":
	 * "true"}}
	 ********/
	public static Order_PayinfoEntity getOrder_Payinfo(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			Order_PayinfoEntity order_PayinfoEntity;
			if (object.getInt("code") == 200) {
				JSONObject object2 = object.getJSONObject("result");
				order_PayinfoEntity = new Order_PayinfoEntity(
						object.getInt("code"), object.getString("message"),
						object2.optString("result"),
						object2.getString("verify_sign"));
			} else {
				order_PayinfoEntity = new Order_PayinfoEntity(
						object.getString("message"), object.getInt("code"));
			}
			return order_PayinfoEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/********
	 * 支付宝支付回调{"code":"200","message":"search success"}
	 ********/
	public static Order_PayinfoEntity getOrder_UpdateInfo(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			Order_PayinfoEntity order_PayinfoEntity;

			order_PayinfoEntity = new Order_PayinfoEntity(
					object.getString("message"), object.getInt("code"));

			return order_PayinfoEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 预定订单数据解析
	 */

	public static ArrayList<OrderEntity> getOrderInfo(String jsonString) {
		ArrayList<OrderEntity> orderList = new ArrayList<OrderEntity>();
		try {

			JSONObject object = new JSONObject(jsonString)
					.getJSONObject("result");
			JSONObject lineInfo_object = new JSONObject(object.toString())
					.getJSONObject("lineInfo");

			JSONObject orderinfo_object = new JSONObject(object.toString())
					.getJSONObject("orderInfo");
			JSONObject contactInfo_object = new JSONObject(object.toString())
					.getJSONObject("contactInfo");
			JSONObject priceInfo_object = new JSONObject(object.toString())
					.getJSONObject("priceInfo");

			JSONTokener orderinfo_jsonParser = new JSONTokener(
					orderinfo_object.toString());
			JSONObject orderinfo = (JSONObject) orderinfo_jsonParser
					.nextValue();

			JSONTokener contactInfo_jsonParser = new JSONTokener(
					contactInfo_object.toString());
			JSONObject contactInfo = (JSONObject) contactInfo_jsonParser
					.nextValue();

			JSONTokener priceInfo_jsonParser = new JSONTokener(
					priceInfo_object.toString());
			JSONObject priceInfo = (JSONObject) priceInfo_jsonParser
					.nextValue();

			JSONTokener lineInfo_jsonParser = new JSONTokener(
					lineInfo_object.toString());
			JSONObject lineInfo = (JSONObject) lineInfo_jsonParser.nextValue();
			OrderEntity orderEntity = new OrderEntity(
					orderinfo.getString("createTime"),
					orderinfo.optDouble("couponAmount"),
					orderinfo.getString("orderCode"),
					orderinfo.getInt("lineCategory"),
					orderinfo.getString("backTime"), orderinfo.getInt("child"),
					orderinfo.getInt("isDelete"),
					orderinfo.getString("b2bOrderId"),
					orderinfo.getString("goTraffic"),
					orderinfo.getString("backTraffic"),
					orderinfo.getInt("baby"), orderinfo.getString("lineId"),
					orderinfo.getString("orderId"),
					orderinfo.getString("redPacketsIds"),
					orderinfo.optDouble("totalPrice"),
					orderinfo.getString("goTime"), orderinfo.getInt("isPay"),
					orderinfo.getString("payEndTime"),
					orderinfo.getInt("adult"),
					orderinfo.getString("companyName"),
					orderinfo.getInt("orderStatus"),
					orderinfo.optDouble("singleRoomAmount"),
					orderinfo.getString("responsiblemobile"),
					orderinfo.getInt("payStatus"),
					orderinfo.optDouble("orderPrice"),
					orderinfo.getString("companyId"),
					orderinfo.getString("dateId"),
					orderinfo.getString("b2cUserId"),
					contactInfo.getString("mobile"),
					contactInfo.getString("createDate"),
					contactInfo.getString("tel"),
					contactInfo.getString("email"),
					contactInfo.getString("address"),
					contactInfo.getString("name"), contactInfo.getString("id"),
					priceInfo.getString("b2bAdultPrice"),
					priceInfo.getString("b2bChildPrice"),
					priceInfo.getString("b2bBabyPrice"),
					priceInfo.getLong("adultPrice"),
					priceInfo.getLong("childPrice"),
					priceInfo.getLong("babyPrice"),
					priceInfo.getString("singleRoom"),
					priceInfo.getString("adultPriceMarket"),
					priceInfo.getString("childPriceMarket"),
					priceInfo.getString("babyPriceMarket"),
					lineInfo.getString("subTitle"),
					lineInfo.getString("person"), lineInfo.getString("title"),
					lineInfo.getString("isTakeAdult"),
					lineInfo.getString("days"),
					lineInfo.getString("leaveseats"),
					lineInfo.getString("isTakeBaby"),
					lineInfo.getString("groupNumber"),
					lineInfo.getString("isTakeChild"),
					lineInfo.getString("brandName"),
					orderinfo.optDouble("b2bAmount"));
			orderList.add(orderEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;

	}

	/****************** 解析订单列表信息 ****************************/
	public static ArrayList<OrderEntity> parseOrder_ListInfo(String docListStr) {
		ArrayList<OrderEntity> orderList = new ArrayList<OrderEntity>();
		try {
			JSONObject jsonObject = new JSONObject(docListStr)
					.getJSONObject("result");
			JSONArray jsonArray = jsonObject.getJSONArray("oderList");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjects = (JSONObject) jsonArray.opt(i);
				OrderEntity orderEntity = new OrderEntity();
				orderEntity.setCreateTime(jsonObjects.optString("createTime"));
				orderEntity.setCouponAmount(jsonObjects
						.optDouble("couponAmount"));
				orderEntity.setOrderCode(jsonObjects.optString("orderCode"));
				orderEntity.setBackTime(jsonObjects.optString("backTime"));
				orderEntity.setChild(jsonObjects.optInt("child"));
				orderEntity.setBaby(jsonObjects.optInt("baby"));
				orderEntity.setId(jsonObjects.optString("id"));
				orderEntity.setTotalPrice(jsonObjects.optDouble("totalPrice"));
				orderEntity.setGoTime(jsonObjects.optString("goTime"));
				orderEntity.setIsPay(jsonObjects.optInt("isPay"));
				orderEntity.setAdult(jsonObjects.optInt("adult"));
				orderEntity.setOrderStatus(jsonObjects.optInt("orderStatus"));
				orderEntity.setSingleRoomAmount(jsonObjects
						.optDouble("singleRoomAmount"));
				orderEntity.setPayStatus(jsonObjects.optInt("payStatus"));
				orderEntity.setOrderPrice(jsonObjects.optDouble("orderPrice"));
				orderEntity.setAdultPrice(jsonObjects.optLong("adultPrice"));
				orderEntity.setChildPrice(jsonObjects.optLong("childPrice"));
				orderEntity.setBabyPrice(jsonObjects.optLong("babyPrice"));
				orderEntity.setSingleRoom(jsonObjects.optString("singleRoom"));
				orderEntity.setOtherInfo(jsonObjects.optString("otherInfo"));
				JSONObject object = new JSONObject(
						jsonObjects.optString("otherInfo"));
				orderEntity.setLineTitle(object.optString("lineTitle"));
				orderEntity.setPhoto(object.optString("photo"));
				orderEntity.setB2bAmount(jsonObjects.optDouble("b2bAmount"));
				orderList.add(orderEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}

	/****************** 解析客户名单列表 ****************************/
	public static ArrayList<My_Person> parseOrder_PersonListInfo(
			String docListStr) {
		ArrayList<My_Person> orderList = new ArrayList<My_Person>();
		try {
			JSONObject jsonObject = new JSONObject(docListStr)
					.getJSONObject("result");
			JSONArray jsonArray = jsonObject.getJSONArray("guestInfo");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjects = (JSONObject) jsonArray.opt(i);
				My_Person orderEntity = new My_Person();
				orderEntity.setCategory(jsonObjects.optInt("category"));
				orderEntity.setMobile(jsonObjects.optString("mobile"));
				orderEntity.setDetail(jsonObjects.optString("detail"));
				orderEntity.setGender(jsonObjects.optInt("gender"));
				orderEntity.setOrderId(jsonObjects.optString("orderId"));
				orderEntity.setLineId(jsonObjects.optString("lineId"));
				orderEntity.setB2bId(jsonObjects.optString("b2bId"));
				orderEntity.setCardCategory(jsonObjects.optInt("cardCategory"));
				orderEntity.setIdcard(jsonObjects.optString("idcard"));
				orderEntity.setName(jsonObjects.optString("name"));
				orderEntity.setId(jsonObjects.optString("id"));

				System.out.println("orderId========="
						+ jsonObjects.optString("id"));

				orderList.add(orderEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}

	/********* 解析订单里面的线路信息 ***********/
	public static OrderEntity getOrder_LineInfo(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			OrderEntity order_PayinfoEntity = new OrderEntity(
					object.getString("lineTitle"), object.getString("photo"));

			return order_PayinfoEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 解析退款详情
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Order_Refund getOrder_RederInfo(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject object2 = object.getJSONObject("result");
			Order_Refund order_Refund = new Order_Refund(
					object2.getString("id"), object2.getString("alipay"),
					object2.getInt("status"), object2.getString("bank"),
					object2.getInt("type"), object2.getString("bankCode"));
			return order_Refund;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****
	 * 解析邀请红包json
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Main_Red getMain_RedInfo(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject object2 = object.getJSONObject("result");
			Main_Red order_Refund = null;
			if (object.getInt("code") == 200) {
				order_Refund = new Main_Red(
						object2.getString("registerAmount"),
						object2.getString("forwardAmount"),
						object2.getInt("openRed"));
			}
			return order_Refund;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * 拼接修改订单添加游客信息json
	 * 
	 * @param persons
	 * @return
	 */
	public static String converPersonToJson(List<My_Person> persons) {
		JSONArray array = new JSONArray();
		for (My_Person person : persons) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", person.getId());
				obj.put("name", person.getName());
				obj.put("gender", person.getGender() + "");
				obj.put("category", person.getCategory() + "");
				obj.put("cardCategory", person.getCardCategory() + "");
				obj.put("idcard", person.getIdcard());
				obj.put("mobile", person.getMobile());
				obj.put("detail", person.getDetail());
			} catch (JSONException e) {
			}
			array.put(obj);
		}
		return array.toString();
	}

	public static String getDateFormat(String sourDateFormat, String sourDate) {
		// 分析从 ParsePosition 给定的索引处开始的文本。如果分析成功，则将 ParsePosition
		// 的索引更新为所用最后一个字符后面的索引
		ParsePosition position = new ParsePosition(0);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourDateFormat);
		Date dateValue = simpleDateFormat.parse(sourDate, position);
		String returnString = simpleDateFormat.format(dateValue);
		return returnString;
	}
	/**
	 * 修改通讯录信息 展现形式：{message:'通知信息',result:数据,code：500|200} 
	 */
	public static UserEntity getUpdateContact(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			int code = object.getInt("code");
			if (code == 200) {
				JSONObject object2 = object.getJSONObject("result");
				UserEntity userEntity = new UserEntity(
						object2.getString("userId"),
						object2.getString("token"),
						object2.getString("photoPath"));
				return userEntity;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
