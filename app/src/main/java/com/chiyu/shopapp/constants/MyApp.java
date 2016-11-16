package com.chiyu.shopapp.constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.chiyu.shopapp.ui.LoginActivity;
import com.chiyu.shopapp.ui.MainActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.Constant;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ScreenUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.TripShopHandler;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.DemoHelper.DataSyncListener;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.EMLog;
import com.umeng.socialize.PlatformConfig;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
/**
 * 
 * @author xll
 *
 */
public class MyApp extends Application {
	/**
	 * 在这里做初始化工作
	 */
	public static Context applicationContext;
	private static MyApp instance;
	// login user name
	public final String PREF_USERNAME = "username";
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	
	
	/**
	 *分享各平台的appkey 
	 * **/
	/** QQ app id  app key*/
	private static final String QQAppId = "1105225159";
	private static final String QQAppKey = "GFw0v2ekYz0OJjlH";
	/** weixin app id  app key*/
	private final String WXAppId = "wxf5a649f0f190b79a";
	private final String WXAppSecret = "3f3978a3cac4c704a5855f69c3788f39";
	/***
	 * 登录中的用户名
	 * photopath
	 * mobile
	 * **/
	private String username;
	private String photopath;
	private String mobile;
	private String token;
	private String userId;//c客
	private String title;//线路标题
	private String price;//线路标题
	private String dateList;//线路标题
	private String lineId;//线路标题
	private String dateId;//线路标题
	private String orderquery;//微信
	private int order_status;//订单状态
	private int orderid;//订单id
	private String CalendardateId="";
	private String categoryId = "";//分类id
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCalendardateId() {
		return CalendardateId;
	}
	public void setCalendardateId(String calendardateId) {
		CalendardateId = calendardateId;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getOrderquery() {
		return orderquery;
	}
	public void setOrderquery(String orderquery) {
		this.orderquery = orderquery;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDateList() {
		return dateList;
	}
	public void setDateList(String dateList) {
		this.dateList = dateList;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 
	 * 判断接收推送消息的按钮是否打开
	 * **/
	private boolean swicthButtonIsSwitchOpen;
	public boolean isSwicthButtonIsSwitchOpen() {
		return swicthButtonIsSwitchOpen;
	}
	public void setSwicthButtonIsSwitchOpen(boolean swicthButtonIsSwitchOpen) {
		this.swicthButtonIsSwitchOpen = swicthButtonIsSwitchOpen;
	}
	/**
	 * 邀请码页面的
	 * 计调头像
	 * ***/
	private String jidiaoPhotopath;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhotopath() {
		return photopath;
	}
	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getJidiaoPhotopath() {
		return jidiaoPhotopath;
	}
	public void setJidiaoPhotopath(String jidiaoPhotopath) {
		this.jidiaoPhotopath = jidiaoPhotopath;
	}
	
	
	//=========================================================================================
	
	
	public List<Activity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	public static void setInstance(MyApp instance) {
		MyApp.instance = instance;
	}

	public List<Activity> activityList = new LinkedList<Activity>();
	public MyApp() {

	}

	public static MyApp getInstance() {
		if (null == instance) {
			instance = new MyApp();
		}
		return instance;

	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();

		}
		System.exit(0);
	}
	
	private EMConnectionListener connectionListener;
	@Override
	public void onCreate() {
		MultiDex.install(this);
		super.onCreate();
		VolleyUtils.initVolley(this);
		ShareUtil.init(this);
		//BMapManager.init();
		ScreenUtils.initScreenUtils(this);
		 applicationContext = this;
	     instance = this;
	        //init demo helper
	 	DemoHelper.getInstance().init(applicationContext);
	 	TripShopHandler handler = TripShopHandler.getInstance();
        handler.init(this);
//	 	setGlobalListeners();
	 	}
	 	

	 	@Override
	 	protected void attachBaseContext(Context base) {
	 		super.attachBaseContext(base);
	 		MultiDex.install(this);
	 	}
	 	
	 	{PlatformConfig.setWeixin(WXAppId, WXAppSecret);
		 PlatformConfig.setQQZone(QQAppId, QQAppKey);
		}
}
