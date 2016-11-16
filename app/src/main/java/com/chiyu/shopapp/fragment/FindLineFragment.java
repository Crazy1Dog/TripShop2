package com.chiyu.shopapp.fragment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.NaviParaOption;
import com.chiyu.shopapp.adapters.MyFragmentPageAdapter;
import com.chiyu.shopapp.adapters.TravelLineAdapter;
import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.bean.Main_Red;
import com.chiyu.shopapp.bean.MemberEntity;
import com.chiyu.shopapp.bean.TravelLineEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.HuoDongActivity;
import com.chiyu.shopapp.ui.Invitation_RedActivity;
import com.chiyu.shopapp.ui.Line_DetailsActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.ui.SearchActivity;
import com.chiyu.shopapp.utils.MyPullToRefreshView;
import com.chiyu.shopapp.utils.MyPullToRefreshView.OnFooterRefreshListener;
import com.chiyu.shopapp.utils.MyPullToRefreshView.OnHeaderRefreshListener;
import com.chiyu.shopapp.utils.NotifyingScrollView;
import com.chiyu.shopapp.utils.NotifyingScrollView.OnScrollChangedListener;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.chiyu.shopapp.view.Dots_View;
import com.chiyu.shopapp.view.MyListView;

public class FindLineFragment extends Fragment implements OnClickListener, OnHeaderRefreshListener, OnFooterRefreshListener {
	private MyPullToRefreshView home_refreshView;
	/**
	 * 定位的客户端
	 */
	private LocationClient mLocationClient;
	/**
	 * 定位的监听器
	 */
	public MyLocationListener mMyLocationListener;
	/***
	 * 是否是第一次定位
	 */
	private volatile boolean isFristLocation = true;
	/**
	 * 最新一次的经纬度
	 */
	private double mCurrentLantitude;
	private double mCurrentLongitude;
	/**
	 * 当前的精度
	 */
	private float mCurrentAccracy;
	private LatLng latLngEnd;// 导航的目的地
	private LatLng latLngStart;// 导航的出发地
	private int categorynum;
	private ViewPager vp_home;
	private List<CategoryFragment> fragments = new ArrayList<CategoryFragment>();
	private Dots_View dots_View;
	/**
	 * 最上面的标题栏
	 */
	private NotifyingScrollView sv_home;
	private RelativeLayout rl_topBar;
	private TextView tv_home_toptitle;
	private ImageView iv_home_topsearch;
	// 声明一个存储《更多》按钮的集合，便于为按钮添加点击事件
	private List<Button> moreButtonList = new ArrayList<Button>();
	/**
	 * 分类线路的种类
	 */
	private List<CategoryLineEntity> FirstDatas = null;// 基础分类
	private List<CategoryLineEntity> SecondDatas = null;// 主体分类
	private List<CategoryLineEntity> ThirdDatas = null;// 活动分类
	private List<CategoryLineEntity> datas = null;
	private List<CategoryLineEntity> fragmentsDatas = null;
	private List<TravelLineEntity> travelDadas;
	private MyFragmentPageAdapter fragmentAdapter;
	private MemberEntity memberEntity;// 计调实体
	private static final String TAG = "tripshop";
	private static final String TAG2 = "tripshop2";
	/**
	 * 计调实体的信息描述
	 */
	private TextView tv_storeName;
	private ImageView iv_consultant;
	private TextView tv_consultant;
	private TextView tv_address;
	private TextView tv_telephone;
	private Button bt_navigation;
	private Button bt_call;
	private Button bt_search;
	// 用于添加listview的布局
	private LinearLayout ll_layout;
	private LinearLayout ll_lv_layout;
	private MyApp apps;
	private String registerAmount;// 新注册抵扣红包
	private String forwardAmount;// 转发下单支付红包
	private int openRed;// 是否开启红包奖励 0： 关闭 1：开启
	private String guestId;
	private LinearLayout ll_redbagLayout;
	/**
	 * 活动分类的标题
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		apps = (MyApp) getActivity().getApplication();
		View view = inflater.inflate(R.layout.findlinefragment, null);
		initView(view);
		home_refreshView.setOnHeaderRefreshListener(this);
		home_refreshView.setOnFooterRefreshListener(this);
		initMyLocation();
		VolleyUtils.requestString_Get(
				URL.DEBUG + URL.MEMBER_DTAIL + ShareUtil.getString("memberId"),
				new OnRequest() {

					@Override
					public void response(String url, String response) {
						// TODO Auto-generated method stub
						
						memberEntity = ParseUtils.getMemberDetail(response
								.toString());
						if(memberEntity!=null){
							apps.setJidiaoPhotopath(memberEntity.getPhotopath());
						}
						guestId = ShareUtil.getString("receiveguestId");
						
						initData();
						VolleyUtils.requestImage(
								URL.IMAGE_DEBUG + memberEntity.getPhotopath(),
								iv_consultant, R.mipmap.icon_default,
								R.mipmap.icon_default);
						/**
						 * 开始获取红包开关
						 */
						VolleyUtils.requestString_Get(URL.DEBUG + URL.CONTENT_REDPACKETSETTING
								+ guestId, new OnRequest() {

							@Override
							public void response(String url, String response) {
								// TODO Auto-generated method stub
								Main_Red main_Red = ParseUtils.getMain_RedInfo(response
										.toString());
								registerAmount = main_Red.getRegisterAmount();
								forwardAmount = main_Red.getForwardAmount();
								openRed = main_Red.getOpenRed();
								//成功返回红包开关状态，开始拉取数据
								/**
								 * 描述：获取微店的线路分类列表(取有效分类) ----------------------------------------
								 * ---------- 提交方式：GET
								 * 接口地址：/api/receive/category/listbyreceiveguest/{receiveguestid} 参数列表：[
								 * receiveguestid:微店Id
								 * http://test.receive.service.tripb2b.com/api/receive
								 * /category/listbyreceiveguest/5194 ]
								 */
								VolleyUtils.requestString_Get(
										URL.DEBUG + URL.CATEGORY_LIST
												+ ShareUtil.getString("receiveguestId"),
										new OnRequest() {
											/**
											 * 分类线路数据下载成功
											 */
											@SuppressLint("NewApi")
											@Override
											public void response(String url, String response) {
												// TODO Auto-generated method stub
												FirstDatas = ParseUtils.parseFirstCategoryList(response
														.toString());
												SecondDatas = ParseUtils
														.parseSecondCategoryList(response.toString());
												ThirdDatas = ParseUtils.parseThirdCategoryList(response
														.toString());
												fragmentsDatas = new ArrayList<CategoryLineEntity>();
												fragmentsDatas.addAll(FirstDatas);
												fragmentsDatas.addAll(SecondDatas);
												// 根据fragmentDatas里面的数据个数决定创建多少个categoryFragment;
												initViewPager();
												datas = new ArrayList<CategoryLineEntity>();
												datas.addAll(FirstDatas);
												datas.addAll(SecondDatas);
												datas.addAll(ThirdDatas);
												/**
												 * 根据分类id获取具体的线路列表 参数列表：[ guestId:微店ID categoryId:线路类别ID
												 * departure:出发地 destination:目的地 days:行程天数
												 * earlyDay:最早出发时间 lateDay:最晚出发时间 minPrice:最小价格
												 * maxPrice:最大价格 tabId:标签Id
												 * priceSort:价格排序(0:从低到高，1:从高到低) curpageNo:当前页
												 * pageSize:一页多少条 keyWord:搜索关键字 type:app(从收客通app发出的请求) ]
												 */

												for (categorynum = 0; categorynum < ThirdDatas.size(); categorynum++) {
													HashMap<String, String> map = new HashMap<String, String>();
													map.put("guestId",
															ShareUtil.getString("receiveguestId"));
													map.put("categoryId", ThirdDatas.get(categorynum)
															.getId());
													map.put("departure", "");
													map.put("destination", "");
													map.put("days", "");
													map.put("earlyDay", "");
													map.put("lateDay", "");
													map.put("minPrice", "");
													map.put("maxPrice", "");
													map.put("tabId", "");
													map.put("priceSort", "0");
													map.put("curpageNo", "1");
													map.put("pageSize", "4");
													map.put("keyWord", "");
													map.put("type", "app");
													final MyListView listView = new MyListView(
															getActivity());
													listView.setFocusable(false);
													final TravelLineAdapter adapter = new TravelLineAdapter(
															getActivity(),
															new int[] {
																	R.layout.travelline_listview_item,
																	R.layout.travelline_listview_item2,
																	R.layout.travelline_listview_item3 });
													View headView = LayoutInflater.from(getActivity())
															.inflate(R.layout.travelline_listview_head,
																	null);
													// 找到头布局里面的控件
													ImageView iv_title = (ImageView) headView
															.findViewById(R.id.iv_title);
													if ("特卖精选".equals(ThirdDatas.get(categorynum)
															.getCategoryName())) {
														iv_title.setImageResource(R.mipmap.home_pic_sale);
													} else if ("热卖线路".equals(ThirdDatas
															.get(categorynum).getCategoryName())) {
														iv_title.setImageResource(R.mipmap.home_pic_hot);
													} else if ("最新线路".equals(ThirdDatas
															.get(categorynum).getCategoryName())) {
														iv_title.setImageResource(R.mipmap.home_pic_new);
													} else {
														iv_title.setImageResource(R.mipmap.home_pic_hot);
													}
													TextView tv_category = (TextView) headView
															.findViewById(R.id.tv_category_home);
													tv_category.setText(ThirdDatas.get(categorynum)
															.getCategoryName());
													if ("特卖精选".equals(ThirdDatas.get(categorynum)
															.getCategoryName())) {
														tv_category.setTextColor(Color
																.parseColor("#FFE15151"));
													} else if ("热卖线路".equals(ThirdDatas
															.get(categorynum).getCategoryName())) {
														tv_category.setTextColor(Color
																.parseColor("#FFF68C1F"));
													} else if ("最新线路".equals(ThirdDatas
															.get(categorynum).getCategoryName())) {
														tv_category.setTextColor(Color
																.parseColor("#FF31BCAA"));
													} else {
														tv_category.setTextColor(Color
																.parseColor("#FF31BCAA"));
													}
													final View footView = LayoutInflater.from(getActivity())
															.inflate(R.layout.travelline_listview_foot,
																	null);
													View firstfootView = LayoutInflater.from(
															getActivity()).inflate(
															R.layout.travelline_listview_firstfoot,
															null);
													// 第一个列表的尾布局的里面的控件
													ll_redbagLayout = (LinearLayout) firstfootView.findViewById(R.id.ll_redbag);
													if(openRed == 0){
														ll_redbagLayout.setVisibility(View.GONE);
													}
													Button btn_moreButton = (Button) firstfootView
															.findViewById(R.id.btn_more);
													ImageView redbag = (ImageView) firstfootView
															.findViewById(R.id.iv_redbag);
													redbag.setOnClickListener(new OnClickListener() {

														@Override
														public void onClick(View v) {
															// TODO Auto-generated method stub
																Intent intent = new Intent();
																intent.putExtra("registerAmount",registerAmount);
																intent.putExtra("forwardAmount",forwardAmount);
																intent.setClass(getActivity(),Invitation_RedActivity.class);
																startActivity(intent);
														}
													});
													// 找到尾布局里面的控件
													final Button btn_more = (Button) footView
															.findViewById(R.id.btn_more);
													listView.addHeaderView(headView);
													if (categorynum == 0) {
														btn_moreButton.setTag(categorynum);
														moreButtonList.add(btn_moreButton);
														listView.addFooterView(firstfootView);
														moreButtonList.add(btn_moreButton);
													}
//													else {
//														btn_more.setTag(categorynum);
//														moreButtonList.add(btn_more);
//														listView.addFooterView(footView);
//														moreButtonList.add(btn_more);
//													}
													listView.setFooterDividersEnabled(false);
													listView.setHeaderDividersEnabled(false);
													listView.setDividerHeight(0);
													LayoutParams lv_params = new LayoutParams(
															LayoutParams.MATCH_PARENT,
															LayoutParams.MATCH_PARENT);
													lv_params.setMargins(0, 25, 0, 0);

													ll_lv_layout.addView(listView, lv_params);
													VolleyUtils.requestString_Post(map, URL.DEBUG
															+ URL.GUSTLINE_LIST, new OnRequest() {
														/**
														 * 根据分类ID获取线路列表数据成功
														 */
														@Override
														public void response(String url, String response) {
															// TODO Auto-generated method stub
															/**
															 * 拿到数据之后进行解析哦 拿到数据创建listview添加到布局中
															 */

															List<TravelLineEntity> travelDadas = ParseUtils
																	.parseTravelLineEntity(response
																			.toString());
															

															travelDadas = ParseUtils
																	.parseTravelLineEntity(response
																			.toString());
															
															//判断是否添加listView(更多这个尾布局)
															adapter.setDatas(travelDadas);
															if(travelDadas.size() > 5){
																listView.addFooterView(footView);
																btn_more.setTag(categorynum);
																moreButtonList.add(btn_more);
																moreButtonList.add(btn_more);
															}
															listView.setAdapter(adapter);
															if (adapter.getDatas().size() == 0) {
																listView.setVisibility(View.GONE);
															} else {
																// 为listview添加选择监听事件
																final ArrayList<TravelLineEntity> myTravelLineEntities = new ArrayList<TravelLineEntity>();
																myTravelLineEntities
																		.addAll(travelDadas);
																listView.setOnItemClickListener(new OnItemClickListener() {

																	@Override
																	public void onItemClick(
																			AdapterView<?> parent,
																			View view, int position,
																			long id) {
																		// TODO Auto-generated method
																		// stub
																		if(position == 0){
																			return;
																		}
																	
																		apps.setDateId(myTravelLineEntities
																				.get(position - 1)
																				.getDateId());
																		apps.setLineId(myTravelLineEntities
																				.get(position - 1)
																				.getLineId());
																		apps.setTitle(myTravelLineEntities
																				.get(position - 1)
																				.getTitle());
																		apps.setPrice(myTravelLineEntities
																				.get(position - 1)
																				.getPrice());
																		apps.setDateList(myTravelLineEntities
																				.get(position - 1)
																				.getDateList());
																		ShareUtil
																				.putString(
																						"lineId",
																						myTravelLineEntities
																								.get(position - 1)
																								.getLineId());
																		
																		ShareUtil
																				.putString(
																						"dateId",
																						myTravelLineEntities
																								.get(position - 1)
																								.getDateId());
																		ShareUtil
																				.putString(
																						"title",
																						myTravelLineEntities
																								.get(position - 1)
																								.getTitle());
																		ShareUtil
																				.putString(
																						"photopath",
																						myTravelLineEntities
																								.get(position - 1)
																								.getPhoto_path());
																		ShareUtil
																				.putString(
																						"price",
																						myTravelLineEntities
																								.get(position - 1)
																								.getPrice());
																		ShareUtil
																				.putString(
																						"dateList",
																						myTravelLineEntities
																								.get(position - 1)
																								.getDateList());
																		Intent intent = new Intent();
																		intent.putExtra(
																				"lineId",
																				myTravelLineEntities
																						.get(position - 1)
																						.getLineId());
																		intent.putExtra(
																				"dateId",
																				myTravelLineEntities
																						.get(position - 1)
																						.getDateId());
																		intent.setClass(
																				getActivity(),
																				Line_DetailsActivity.class);
																		startActivity(intent);
																	}
																});
															}
														}

														/**
														 * 根据分类id获取线路列表数据失败
														 */
														@Override
														public void errorResponse(String url,
																VolleyError error) {
															// TODO Auto-generated method stub
														}
													});

												}
												for (int i = 0; i < moreButtonList.size(); i++) {
													final Button button_more = moreButtonList.get(i);
													button_more
															.setOnClickListener(new OnClickListener() {

																@Override
																public void onClick(View v) {
																	// TODO Auto-generated method stub
																	Intent intent = new Intent();
																	intent.putExtra(
																			"categoryname",
																			ThirdDatas
																					.get((Integer) button_more
																							.getTag())
																					.getCategoryName());
																	intent.putExtra(
																			"categoryId",
																			ThirdDatas
																					.get((Integer) button_more
																							.getTag())
																					.getId());
																	intent.setClass(getActivity(),
																			HuoDongActivity.class);
																	startActivity(intent);
																}
															});
												}
											}

											

											/**
											 * 数据下载失败
											 */
											@Override
											public void errorResponse(String url, VolleyError error) {
												// TODO Auto-generated method stub
											}
										});
							}

							@Override
							public void errorResponse(String url, VolleyError error) {
								// TODO Auto-generated method stub
							}
						});
					}
					@Override
					public void errorResponse(String url, VolleyError error) {
						// TODO Auto-generated method stub
					}
				});
		
		
		/**
		 * 给主页面的按钮设置点击监听
		 */
		bt_call.setOnClickListener(this);
		bt_search.setOnClickListener(this);
		bt_navigation.setOnClickListener(this);
		sv_home.setOnScrollChangedListener(new OnScrollChangedListener() {
			
			@Override
			public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
				// TODO Auto-generated method stub
				// 滑动改变标题栏的透明度和文字透明度，图标
				if (t < 0) {
					return;
				}
				int lHeight = 200;
				if (t <= lHeight) {
					float progress =  (new Float(t) / new Float(lHeight) * 1.0f);
					rl_topBar.setAlpha(progress);
				} else {
					rl_topBar.setAlpha(1.0f);
				}
			}
		});
		iv_home_topsearch.setOnClickListener(this);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		home_refreshView = (MyPullToRefreshView) view.findViewById(R.id.home_refreshView);
		sv_home = (NotifyingScrollView) view.findViewById(R.id.sv_home);
		rl_topBar = (RelativeLayout) view.findViewById(R.id.rl_home_top_bar);
		tv_home_toptitle = (TextView) view.findViewById(R.id.tv_home_toptile);
		iv_home_topsearch = (ImageView) view.findViewById(R.id.iv_home_topsearch);
		tv_home_toptitle = (TextView) view.findViewById(R.id.tv_home_toptile);
		rl_topBar.setAlpha(0);
		dots_View = (Dots_View) view.findViewById(R.id.dots_title);
		vp_home = (ViewPager) view.findViewById(R.id.vp_home);
		ll_layout = (LinearLayout) view.findViewById(R.id.ll_layout);
		ll_lv_layout = (LinearLayout) view.findViewById(R.id.ll_lv_layout);
		tv_storeName = (TextView) view.findViewById(R.id.tv_storeName);
		iv_consultant = (ImageView) view.findViewById(R.id.iv_consultant);
		tv_consultant = (TextView) view.findViewById(R.id.tv_consultant);
		tv_address = (TextView) view.findViewById(R.id.tv_address);
		tv_telephone = (TextView) view.findViewById(R.id.tv_telephone);
		bt_navigation = (Button) view.findViewById(R.id.bt_navigation);
		bt_call = (Button) view.findViewById(R.id.bt_call);
		bt_search = (Button) view.findViewById(R.id.bt_search);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_call:// 打电话
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ memberEntity.getMobile()));
			startActivity(intent);
			break;
		case R.id.bt_search:// 进入搜索页面
			Intent intent1 = new Intent();
			intent1.setClass(getActivity(), SearchActivity.class);
			startActivity(intent1);
			break;
		case R.id.iv_home_topsearch:
			Intent intent2 = new Intent();
			intent2.setClass(getActivity(), SearchActivity.class);
			startActivity(intent2);
			break;
		case R.id.bt_navigation:
			// 天安门坐标
			
//			Geocoder geocoder = new Geocoder(getActivity(), Locale.CHINA);  
			Geocoder geocoder = new Geocoder(getActivity());
			try {
//				List<Address> list = geocoder.getFromLocationName(getAddress, 3);
//				memberEntity.getAddress().replace("弄","号");
				String [] address = memberEntity.getAddress().replace("弄","号").split("号");
				List<Address> list = geocoder.getFromLocationName(address[0], 1);
				if ( list.size() > 0) {
					latLngEnd = new LatLng(list.get(0).getLatitude(), list.get(
							0).getLongitude());
				} else {
					double mLat2 = 40.056858;
					double mLon2 = 116.308194;
					latLngEnd = new LatLng(mLat2, mLon2);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// 构建 导航参数
			NaviParaOption para = new NaviParaOption();
			para.startPoint(latLngStart);
			para.startName("从这里开始");
			para.endPoint(latLngEnd);
			para.endName("到这里结束");
			if(isAvilible(getActivity(),"com.baidu.BaiduMap")){//传入指定应用包名  
                Intent intentGo = null;  
                try {  
                    intentGo = Intent.getIntent("intent://map/geocoder?location="+latLngEnd.latitude+","+latLngEnd.longitude+"&coord_type=gcj02&src=驰誉旅游|旅游管家#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");  
                    startActivity(intentGo); //启动调用  
                } catch (URISyntaxException e) {  
                }  
            }else{//未安装  
                //market为路径，id为包名  
                //显示手机上所有的market商店  
                Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");  
                Intent intentDownLoad = new Intent(Intent.ACTION_VIEW, uri);   
                startActivity(intentDownLoad);   
            }
			//-------------------------------------------
			break;
		default:
			break;
		}
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				double mLat1 = 39.915291;
				double mLon1 = 116.403857;
				latLngStart = new LatLng(mLat1, mLon1);
				return;
			} else {
				mCurrentAccracy = location.getRadius();
				// 设置定位数据
				mCurrentLantitude = location.getLatitude();
				mCurrentLongitude = location.getLongitude();
				latLngStart = new LatLng(mCurrentLantitude, mCurrentLongitude);
				// 第一次定位时，将地图位置移动到当前位置
				if (isFristLocation) {
					isFristLocation = false;
					latLngStart = new LatLng(location.getLatitude(),
							location.getLongitude());
				}
			}

		}

	}

	/**
	 * 初始化定位相关代码
	 */
	private void initMyLocation() {
		// 定位初始化
		mLocationClient = new LocationClient(getActivity());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		// 设置定位的相关配置
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setIsNeedAddress(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mLocationClient != null) {
			mLocationClient.stop();
		}

	}
/*
	 * 成功获取计调信息之后。给计调设置店名、地址、计调姓名、以及联系电话等
	 */
	private void initData() {
		// TODO Auto-generated method stub
		tv_home_toptitle.setText(memberEntity.getCompanyname());
		tv_storeName.setText(memberEntity.getCompanyname());
		String [] address = memberEntity.getAddress().split("区");
		tv_address.setText(address[address.length - 1]);
		tv_consultant.setText("您的旅游顾问："
				+ memberEntity.getContactname());
		tv_telephone.setText(memberEntity.getMobile());

		ShareUtil.putString("mobile", memberEntity.getMobile());
		ShareUtil.putString("contactname",
				memberEntity.getContactname());
		ShareUtil.putString("memberEntityMobile",
				memberEntity.getMobile());
		ShareUtil.putString("JidiaoPhotoPath",
				memberEntity.getPhotopath());
		ShareUtil.putString("username", memberEntity.getUsername());
	}
///**
// * 首页的下拉刷新回调方法
// */
//@Override
//public void onHeaderRefresh(PullToRefreshView view) {
//	// TODO Auto-generated method stub
//	ll
//}
//
//@Override
//public void onFooterRefresh(PullToRefreshView view) {
//	// TODO Auto-generated method stub
//	
//}
/**
 * 初始化viewpager
 */
@SuppressWarnings("deprecation")
private void initViewPager() {
	List<CategoryLineEntity> preFragmentDatas = null;
	// TODO Auto-generated method stub
	int totalPage = fragmentsDatas.size() / 4;
	int morePage = fragmentsDatas.size() % 4;
	if (morePage != 0) {
		totalPage += 1;
	}
	dots_View.setCount(0);
	dots_View.setCount(totalPage);
	for (int i = 1; i <= totalPage; i++) {
		// 传递每个fragment的数据
		if (i == totalPage) {
			preFragmentDatas = fragmentsDatas.subList(
					4 * (i - 1), fragmentsDatas.size());
		} else {
			preFragmentDatas = fragmentsDatas.subList(
					4 * (i - 1), 4 * i);
		}
		CategoryFragment fragment = new CategoryFragment();
		Bundle bundle = new Bundle();
		ArrayList<CategoryLineEntity> bundleList = new ArrayList<CategoryLineEntity>();
		bundleList.addAll(preFragmentDatas);
		bundle.putInt("pageNum", i);
		bundle.putParcelableArrayList("list", bundleList);
		fragment.setArguments(bundle);
		// 在这里把数据的带到fragment里面去
		fragments.add(fragment);
	}
	
	 fragmentAdapter = new MyFragmentPageAdapter(
			getChildFragmentManager(), fragments,
			getActivity());
	vp_home.setAdapter(fragmentAdapter);
	vp_home.setOnPageChangeListener(new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1,
				int arg2) {
			// TODO Auto-generated method stub
			dots_View.setOffset(arg0 + arg1);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	});
}
/** 
 * 检查手机上是否安装了指定的软件 
 * @param context 
 * @param packageName：应用包名 
 * @return 
 */  
private boolean isAvilible(Context context, String packageName){   
    //获取packagemanager   
    final PackageManager packageManager = context.getPackageManager();  
  //获取所有已安装程序的包信息   
    List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);  
  //用于存储所有已安装程序的包名   
    List<String> packageNames = new ArrayList<String>();  
    //从pinfo中将包名字逐一取出，压入pName list中   
    if(packageInfos != null){   
        for(int i = 0; i < packageInfos.size(); i++){   
            String packName = packageInfos.get(i).packageName;   
            packageNames.add(packName);   
        }   
    }   
  //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE   
    return packageNames.contains(packageName);  
}

@Override
public void onFooterRefresh(MyPullToRefreshView view) {
	// TODO Auto-generated method stub
	home_refreshView.onFooterRefreshComplete();
	Toast.makeText(getActivity(), "已经到底啦！", Toast.LENGTH_SHORT).show();
}

@Override
public void onHeaderRefresh(MyPullToRefreshView view) {
	// TODO Auto-generated method stub
	/**
	 * 描述：获取微店的线路分类列表(取有效分类) ----------------------------------------
	 * ---------- 提交方式：GET
	 * 接口地址：/api/receive/category/listbyreceiveguest/{receiveguestid} 参数列表：[
	 * receiveguestid:微店Id
	 * http://test.receive.service.tripb2b.com/api/receive
	 * /category/listbyreceiveguest/5194 ]
	 */
	if(!checkNetworkState()){
		Toast.makeText(getActivity(),"网络未连接！",Toast.LENGTH_SHORT).show();
		home_refreshView.onHeaderRefreshComplete();
		return;
	}
	ll_lv_layout.removeAllViews();
	if(fragmentsDatas != null && fragmentsDatas.size() != 0 ){
		fragmentsDatas.clear();
	}
	if(fragments != null && fragments.size() != 0  ){
		fragments.clear();
	}
	if(ThirdDatas != null && ThirdDatas.size() != 0 ){
		ThirdDatas.clear();
	}	
	if(fragmentAdapter != null){
		fragmentAdapter.notifyDataSetChanged();
	}
	
	/**
	 * 开始获取红包开关
	 */
	VolleyUtils.requestString_Get(URL.DEBUG + URL.CONTENT_REDPACKETSETTING
			+ guestId, new OnRequest() {

		@Override
		public void response(String url, String response) {
			// TODO Auto-generated method stub
			Main_Red main_Red = ParseUtils.getMain_RedInfo(response
					.toString());
			if(main_Red != null){
				registerAmount = main_Red.getRegisterAmount();
				forwardAmount = main_Red.getForwardAmount();
				openRed = main_Red.getOpenRed();
			}
			
			//成功返回红包开关状态，开始拉取数据
			/**
			 * 描述：获取微店的线路分类列表(取有效分类) ----------------------------------------
			 * ---------- 提交方式：GET
			 * 接口地址：/api/receive/category/listbyreceiveguest/{receiveguestid} 参数列表：[
			 * receiveguestid:微店Id
			 * http://test.receive.service.tripb2b.com/api/receive
			 * /category/listbyreceiveguest/5194 ]
			 */
			VolleyUtils.requestString_Get(
					URL.DEBUG + URL.CATEGORY_LIST
							+ ShareUtil.getString("receiveguestId"),
					new OnRequest() {
						/**
						 * 分类线路数据下载成功
						 */
						@SuppressLint("NewApi")
						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							FirstDatas = ParseUtils.parseFirstCategoryList(response
									.toString());
							SecondDatas = ParseUtils
									.parseSecondCategoryList(response.toString());
							ThirdDatas = ParseUtils.parseThirdCategoryList(response
									.toString());
							fragmentsDatas = new ArrayList<CategoryLineEntity>();
							fragmentsDatas.addAll(FirstDatas);
							fragmentsDatas.addAll(SecondDatas);
							// 根据fragmentDatas里面的数据个数决定创建多少个categoryFragment;
							initViewPager();
							datas = new ArrayList<CategoryLineEntity>();
							datas.addAll(FirstDatas);
							datas.addAll(SecondDatas);
							datas.addAll(ThirdDatas);
							/**
							 * 根据分类id获取具体的线路列表 参数列表：[ guestId:微店ID categoryId:线路类别ID
							 * departure:出发地 destination:目的地 days:行程天数
							 * earlyDay:最早出发时间 lateDay:最晚出发时间 minPrice:最小价格
							 * maxPrice:最大价格 tabId:标签Id
							 * priceSort:价格排序(0:从低到高，1:从高到低) curpageNo:当前页
							 * pageSize:一页多少条 keyWord:搜索关键字 type:app(从收客通app发出的请求) ]
							 */

							for (categorynum = 0; categorynum < ThirdDatas.size(); categorynum++) {
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("guestId",
										ShareUtil.getString("receiveguestId"));
								map.put("categoryId", ThirdDatas.get(categorynum)
										.getId());
								map.put("departure", "");
								map.put("destination", "");
								map.put("days", "");
								map.put("earlyDay", "");
								map.put("lateDay", "");
								map.put("minPrice", "");
								map.put("maxPrice", "");
								map.put("tabId", "");
								map.put("priceSort", "0");
								map.put("curpageNo", "1");
								map.put("pageSize", "4");
								map.put("keyWord", "");
								map.put("type", "app");

								final MyListView listView = new MyListView(
										getActivity());
								listView.setFocusable(false);
								final TravelLineAdapter adapter = new TravelLineAdapter(
										getActivity(),
										new int[] {
												R.layout.travelline_listview_item,
												R.layout.travelline_listview_item2,
												R.layout.travelline_listview_item3 });
								View headView = LayoutInflater.from(getActivity())
										.inflate(R.layout.travelline_listview_head,
												null);
								// 找到头布局里面的控件
								ImageView iv_title = (ImageView) headView
										.findViewById(R.id.iv_title);
								if ("特卖精选".equals(ThirdDatas.get(categorynum)
										.getCategoryName())) {
									iv_title.setImageResource(R.mipmap.home_pic_sale);
								} else if ("热卖线路".equals(ThirdDatas
										.get(categorynum).getCategoryName())) {
									iv_title.setImageResource(R.mipmap.home_pic_hot);
								} else if ("最新线路".equals(ThirdDatas
										.get(categorynum).getCategoryName())) {
									iv_title.setImageResource(R.mipmap.home_pic_new);
								} else {
									iv_title.setImageResource(R.mipmap.home_pic_hot);
								}
								TextView tv_category = (TextView) headView
										.findViewById(R.id.tv_category_home);
								tv_category.setText(ThirdDatas.get(categorynum)
										.getCategoryName());
								if ("特卖精选".equals(ThirdDatas.get(categorynum)
										.getCategoryName())) {
									tv_category.setTextColor(Color
											.parseColor("#FFE15151"));
								} else if ("热卖线路".equals(ThirdDatas
										.get(categorynum).getCategoryName())) {
									tv_category.setTextColor(Color
											.parseColor("#FFF68C1F"));
								} else if ("最新线路".equals(ThirdDatas
										.get(categorynum).getCategoryName())) {
									tv_category.setTextColor(Color
											.parseColor("#FF31BCAA"));
								} else {
									tv_category.setTextColor(Color
											.parseColor("#FF31BCAA"));
								}
								View footView = LayoutInflater.from(getActivity())
										.inflate(R.layout.travelline_listview_foot,
												null);
								View firstfootView = LayoutInflater.from(
										getActivity()).inflate(
										R.layout.travelline_listview_firstfoot,
										null);
								// 第一个列表的尾布局的里面的控件
								ll_redbagLayout = (LinearLayout) firstfootView.findViewById(R.id.ll_redbag);
								if(openRed == 0){
									ll_redbagLayout.setVisibility(View.GONE);
								}
								Button btn_moreButton = (Button) firstfootView
										.findViewById(R.id.btn_more);
								ImageView redbag = (ImageView) firstfootView
										.findViewById(R.id.iv_redbag);
								redbag.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
											Intent intent = new Intent();
											intent.putExtra("registerAmount",registerAmount);
											intent.putExtra("forwardAmount",forwardAmount);
											intent.setClass(getActivity(),Invitation_RedActivity.class);
											startActivity(intent);
									}
								});
								// 找到尾布局里面的控件
								Button btn_more = (Button) footView
										.findViewById(R.id.btn_more);
								listView.addHeaderView(headView);
								if (categorynum == 0) {
									btn_moreButton.setTag(categorynum);
									moreButtonList.add(btn_moreButton);
									listView.addFooterView(firstfootView);
									moreButtonList.add(btn_moreButton);
								} 
//								else {
//									btn_more.setTag(categorynum);
//									moreButtonList.add(btn_more);
//									listView.addFooterView(footView);
//									moreButtonList.add(btn_more);
//								}
								listView.setFooterDividersEnabled(false);
								listView.setHeaderDividersEnabled(false);
								listView.setDividerHeight(0);
								LayoutParams lv_params = new LayoutParams(
										LayoutParams.MATCH_PARENT,
										LayoutParams.MATCH_PARENT);
								lv_params.setMargins(0, 25, 0, 0);

								ll_lv_layout.addView(listView, lv_params);
								VolleyUtils.requestString_Post(map, URL.DEBUG
										+ URL.GUSTLINE_LIST, new OnRequest() {
									/**
									 * 根据分类ID获取线路列表数据成功
									 */
									@Override
									public void response(String url, String response) {
										// TODO Auto-generated method stub
										/**
										 * 拿到数据之后进行解析哦 拿到数据创建listview添加到布局中
										 */

										List<TravelLineEntity> travelDadas = ParseUtils
												.parseTravelLineEntity(response
														.toString());

										travelDadas = ParseUtils
												.parseTravelLineEntity(response
														.toString());

										adapter.setDatas(travelDadas);
										listView.setAdapter(adapter);
										if (adapter.getDatas().size() == 0) {
											listView.setVisibility(View.GONE);
										} else {
											// 为listview添加选择监听事件
											final ArrayList<TravelLineEntity> myTravelLineEntities = new ArrayList<TravelLineEntity>();
											myTravelLineEntities
													.addAll(travelDadas);
											listView.setOnItemClickListener(new OnItemClickListener() {

												@Override
												public void onItemClick(
														AdapterView<?> parent,
														View view, int position,
														long id) {
													// TODO Auto-generated method
													// stub
													if(position == 0){
														return;
													}
													
													apps.setDateId(myTravelLineEntities
															.get(position - 1)
															.getDateId());
													apps.setLineId(myTravelLineEntities
															.get(position - 1)
															.getLineId());
													apps.setTitle(myTravelLineEntities
															.get(position - 1)
															.getTitle());
													apps.setPrice(myTravelLineEntities
															.get(position - 1)
															.getPrice());
													apps.setDateList(myTravelLineEntities
															.get(position - 1)
															.getDateList());
													ShareUtil
															.putString(
																	"lineId",
																	myTravelLineEntities
																			.get(position - 1)
																			.getLineId());
												
													ShareUtil
															.putString(
																	"dateId",
																	myTravelLineEntities
																			.get(position - 1)
																			.getDateId());
													ShareUtil
															.putString(
																	"title",
																	myTravelLineEntities
																			.get(position - 1)
																			.getTitle());
													ShareUtil
															.putString(
																	"photopath",
																	myTravelLineEntities
																			.get(position - 1)
																			.getPhoto_path());
													ShareUtil
															.putString(
																	"price",
																	myTravelLineEntities
																			.get(position - 1)
																			.getPrice());
													ShareUtil
															.putString(
																	"dateList",
																	myTravelLineEntities
																			.get(position - 1)
																			.getDateList());
													Intent intent = new Intent();
													intent.putExtra(
															"lineId",
															myTravelLineEntities
																	.get(position - 1)
																	.getLineId());
													intent.putExtra(
															"dateId",
															myTravelLineEntities
																	.get(position - 1)
																	.getDateId());
													intent.setClass(
															getActivity(),
															Line_DetailsActivity.class);
													startActivity(intent);
												}
											});
										}
									}

									/**
									 * 根据分类id获取线路列表数据失败
									 */
									@Override
									public void errorResponse(String url,
											VolleyError error) {
										// TODO Auto-generated method stub
									}
								});

							}
							for (int i = 0; i < moreButtonList.size(); i++) {
								final Button button_more = moreButtonList.get(i);
								button_more
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
												Intent intent = new Intent();
												intent.putExtra(
														"categoryname",
														ThirdDatas
																.get((Integer) button_more
																		.getTag())
																.getCategoryName());
												intent.putExtra(
														"categoryId",
														ThirdDatas
																.get((Integer) button_more
																		.getTag())
																.getId());
											
												intent.setClass(getActivity(),
														HuoDongActivity.class);
												startActivity(intent);
											}
										});
							}
						}

						

						/**
						 * 数据下载失败
						 */
						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
						}
					});
		}

		@Override
		public void errorResponse(String url, VolleyError error) {
			// TODO Auto-generated method stub
		}
	});
	
	Toast.makeText(getActivity(), "刷新成功！", Toast.LENGTH_SHORT).show();
	home_refreshView.onHeaderRefreshComplete();
}   
/** 
 * 检测网络是否连接 
 * @return 
 */  
private boolean checkNetworkState() {  
        boolean flag = false;  
        //得到网络连接信息  
        ConnectivityManager manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);  
        //去进行判断网络是否连接  
        if (manager.getActiveNetworkInfo() != null) {  
                flag = manager.getActiveNetworkInfo().isAvailable();  
        }  
        return flag;  
}  
  
 
  
}
