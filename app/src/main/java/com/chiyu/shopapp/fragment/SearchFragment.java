package com.chiyu.shopapp.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.DepartureAdapter;
import com.chiyu.shopapp.adapters.DestinationAdapter;
import com.chiyu.shopapp.adapters.MySearchFragmentListViewAdapter;
import com.chiyu.shopapp.adapters.OrderAdapter;
import com.chiyu.shopapp.adapters.TagAdapter;
import com.chiyu.shopapp.adapters.TripDaysAdapter;
import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.bean.Departure;
import com.chiyu.shopapp.bean.Destination;
import com.chiyu.shopapp.bean.OrderEntity;
import com.chiyu.shopapp.bean.TravelLineEntity;
import com.chiyu.shopapp.bean.TripDayEntity;
import com.chiyu.shopapp.bean.TurnedDeparture;
import com.chiyu.shopapp.bean.TurnedDestination;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.DatePickerActivity;
import com.chiyu.shopapp.ui.Line_DetailsActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.PullToRefreshView;
import com.chiyu.shopapp.utils.PullToRefreshView.OnFooterRefreshListener;
import com.chiyu.shopapp.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.chiyu.shopapp.utils.ScreenUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;

public class SearchFragment extends Fragment implements OnClickListener,
		OnHeaderRefreshListener, OnFooterRefreshListener {
	private static final String TAG5 = "tripshop5";
	// 定义筛选的条件
	// guestId:微店ID
	// * categoryId:线路类别ID
	// * departure:出发地
	// * destination:目的地
	// * days:行程天数
	// * earlyDay:最早出发时间
	// * lateDay:最晚出发时间
	// * minPrice:最小价格
	// * maxPrice:最大价格
	// * tabId:标签Id
	// * priceSort:价格排序(0:从低到高，1:从高到低)
	// * curpageNo:当前页 pageSize:一页多少条
	// * keyWord:搜索关键字
	// * type:app(从收客通app发出的请求) ]
	// */
	private String departureParam = "";
	private String destinationParam = "";
	private String daysParam = "";
	private String earlyDayParam = "";
	private String lateDayParam = "";
	private String minPriceParam = "";
	private String maxPriceParam = "";
	private String tabIdParam = "";
	private String priceSortParam = "0";
	private String curpageNoParam = "";
	private String keyWordParam = "";
	// ------------------下面是选择时间相关的东西----------------------
	private int mYear;
	private int mMonth;
	private int mDay;
	private Calendar calendar;
	private DatePicker datePicker;
	private View datePickerView;
	private Button earliestGoButton, lastestGoButton;
	// ---------------------上面是选择时间相关的东西---------------------- // 加载更多的相关View
	private int page = 1;
	private PullToRefreshView mPullToRefreshView;
	private PopupWindow mpopPopupWindow1;
	private PopupWindow mpopPopupWindow2;
	private PopupWindow mpopPopupWindow3;
	private PopupWindow mpopPopupWindow4;
	// 定义适配器
	private DepartureAdapter departureAdapter;
	private DestinationAdapter destinationAdapter;
	private TripDaysAdapter tripDaysAdapter;
	private OrderAdapter orderAdapter;
	private TagAdapter tagAdapter;
	// 定义悬浮框的View
	private View departureView;
	private View destinationView;
	private View choicesView;
	private View price_orderView;
	// 定义悬浮框中所涉及到的所有控件
	private ListView lv_departure;
	private ListView lv_destination;
	private Button btn_departure;
	private Button btn_destination;
	private Button btn_choices;
	private Button btn_price_order;
	private ListView lv_priceorder;
	// 定义筛选悬浮框中的控件
	private Button btn_choice_cancelButton;
	private Button btn_choice_clearButton;
	private Button btn_choice_determineButton;
	private RadioGroup rg_choiceGroup;
	private RadioButton rbtn_daysButton;
	private RadioButton rbtn_departuretimeButton;
	private RadioButton rbtn_priceButton;
	private RadioButton rbtn_tagButton;
	private ListView lv_choice_daysListView;
	private LinearLayout ll_choice_departuretimeLayout;
	private LinearLayout ll_choices_priceintervaLayout;
	private EditText et_lowestpriceEditText;
	private EditText et_hightestpriEditText;
	private ListView lv_choice_tagListView;
	// 定义SearchFragment中的控件
	private MySearchFragmentListViewAdapter listAdapter;
	private ListView lv_searchFragment;
	private static final String TAG = "tripshop";
	private static final String TAG1 = "tripshop1";
	private static final String TAG2 = "tripshop2";
	private int i;
	private String keyword;
	private String categoryId;
	// 填充适配器的数据
	private List<OrderEntity> orderDatas;
	private List<CategoryLineEntity> tagDatasFromCategoryActivity;
	private List<CategoryLineEntity> tagDatas;
	private List<TripDayEntity> tripDayDatas;
	private List<TravelLineEntity> travelLineEntities;
	private List<Departure> departuresEntities;
	// 处理过的出发地
	private List<TurnedDeparture> departureDatas;
	private List<Destination> destinationsEntities;
	// 处理过的目的地
	private List<TurnedDestination> destinationDatas;
	private View view;
	private MyApp app;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		calendar = Calendar.getInstance();
		view = inflater.inflate(R.layout.search_fragment, null);
		app = (MyApp) getActivity().getApplication();
		initDatas();
		// 拿到activity传递过来的数据，然后进行数据请求
		getParamsFromCategoryActivity();
		// 初始化控件
		initViews(view, inflater);

		// 绑定控件的各种事件
		bindEvents();

		getDataFromNet();
		return view;
	}

	// 初始化一些基本的数据
	private void initDatas() {
		// TODO Auto-generated method stub
		tripDayDatas = new ArrayList<TripDayEntity>();
		tripDayDatas.add(new TripDayEntity(0, "不限"));
		tripDayDatas.add(new TripDayEntity(0, "1日"));
		tripDayDatas.add(new TripDayEntity(0, "2日"));
		tripDayDatas.add(new TripDayEntity(0, "3-5日"));
		tripDayDatas.add(new TripDayEntity(0, "5-7日"));
		tripDayDatas.add(new TripDayEntity(0, "7日以上"));
		orderDatas = new ArrayList<OrderEntity>();
		orderDatas.add(new OrderEntity(0, "默认排序"));
		orderDatas.add(new OrderEntity(0, "价格从低到高"));
		orderDatas.add(new OrderEntity(0, "价格从高到低"));

	}

	// 拿到activity传递过来的数据，然后进行数据请求
	public void getParamsFromCategoryActivity() {
		tagDatas = new ArrayList<CategoryLineEntity>();
		tagDatas.add(new CategoryLineEntity("", "", "", "", "不限", "", "", ""));
		Bundle bundle = getArguments();
		i = bundle.getInt("i");
		keyword = bundle.getString("keyword","");
		categoryId = bundle.getString("categoryid");
		tagDatasFromCategoryActivity = bundle.getParcelableArrayList("list");
		Log.i(TAG2,
				"从categoryActivity传递过来的数据："
						+ tagDatasFromCategoryActivity.toString());
		tagDatas.addAll(tagDatasFromCategoryActivity);
		Log.i(TAG2, "我处理过的标签数据：" + tagDatas.toString());
	}
	// 根据传递过来的参数，进行数据请求
	public void getDataFromNet() {
		if (i == 0) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("guestId", ShareUtil.getString("receiveguestId"));
			map.put("categoryId", app.getCategoryId());
			map.put("departure", "");
			map.put("destination", "");
			map.put("days", "");
			map.put("earlyDay", "");
			map.put("lateDay", "");
			map.put("minPrice", "");
			map.put("maxPrice", "");
			map.put("tabId", "");
			map.put("priceSort", "");
			map.put("curpageNo", page + "");
			map.put("pageSize", "20");
			map.put("keyWord", keyword);
			map.put("type", "app");
			Log.i(TAG, map.toString());
			System.out.println("线路列表红豆===================请求数据="+map.toString());
			VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GUSTLINE_LIST,
					new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG5, "搜索的结果数据来啦" + i + "============"
									+ response.toString());
							System.out.println("response===========列表数据返回=====青豆==="+response);
							travelLineEntities = ParseUtils
									.parseTravelLineEntity(response.toString());
							departuresEntities = ParseUtils
									.parseDepartures(response.toString());
							if (departuresEntities != null
									&& departuresEntities.size() != 0) {
								Log.i(TAG5,
										"搜索进来的出发地数据"
												+ departuresEntities.toString());
								transformData(departuresEntities);// zheli
								Log.i(TAG5,
										"搜索进来的转换后的出发地数据"
												+ departureDatas.toString());
								departureAdapter = new DepartureAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								departureAdapter.setDatas(departureDatas);
								lv_departure.setAdapter(departureAdapter);
							} else {
								Log.i(TAG5, "搜索进来的没有出发地数据！！！");
							}

							destinationsEntities = ParseUtils
									.parseDestinations(response.toString());
							if (destinationsEntities != null
									&& destinationsEntities.size() != 0) {
								Log.i(TAG5,
										"搜索进来的目的地数据"
												+ destinationsEntities
														.toString());
								transFormDestinationData(destinationsEntities);
								Log.i(TAG5,
										"搜索进来的转换后的目的地数据"
												+ destinationDatas.toString());
								destinationAdapter = new DestinationAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								destinationAdapter.setDatas(destinationDatas);
								lv_destination.setAdapter(destinationAdapter);
							} else {
								Log.i(TAG5, "搜索进来的没有目的地数据！！！");
							}

							listAdapter = new MySearchFragmentListViewAdapter(
									getActivity(),
									new int[] { R.layout.searchfragment_listview_item });
							listAdapter.setDatas(travelLineEntities);
							lv_searchFragment.setAdapter(listAdapter);
							// 数据加载完成，让dialog消失
							lv_searchFragment
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											// TODO Auto-generated method stub
											final TravelLineEntity travelLineEntity = listAdapter
													.getData().get(arg2);
											Intent intent = new Intent();
											intent.putExtra("lineId",
													travelLineEntity
															.getLineId());
											intent.putExtra("dateId",
													travelLineEntity
															.getDateId());
											app.setDateId(travelLineEntity.getDateId());
											app.setLineId(travelLineEntity.getLineId());
											app.setTitle(travelLineEntity.getTitle());
											app.setPrice(travelLineEntity.getPrice());
											app.setDateList(travelLineEntity.getDateList());
											ShareUtil.putString("photopath",travelLineEntity.getPhoto_path());
											intent.setClass(getActivity(),
													Line_DetailsActivity.class);
											startActivity(intent);
										}
									});
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG5, "搜索失败============" + error.getMessage());
						}
					});
		} else {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("guestId", ShareUtil.getString("receiveguestId"));
			map.put("categoryId", categoryId);
			map.put("departure", "");
			map.put("destination", "");
			map.put("days", "");
			map.put("earlyDay", "");
			map.put("lateDay", "");
			map.put("minPrice", "");
			map.put("maxPrice", "");
			map.put("tabId", "");
			map.put("priceSort", "");
			map.put("curpageNo", "1");
			map.put("pageSize", "20");
			map.put("keyWord", "");
			map.put("type", "app");
			Log.i(TAG1, map.toString());
			VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GUSTLINE_LIST,
					new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG1, "第" + i + "页的数据" + response.toString());
							travelLineEntities = ParseUtils
									.parseTravelLineEntity(response.toString());
							departuresEntities = ParseUtils
									.parseDepartures(response.toString());

							if (departuresEntities != null
									&& departuresEntities.size() != 0) {
								Log.i(TAG2, "第" + i + "出发地数据============"
										+ departuresEntities.toString());
								transformData(departuresEntities);
								departureAdapter = new DepartureAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								departureAdapter.setDatas(departureDatas);
								lv_departure.setAdapter(departureAdapter);
							} else {
								Log.i(TAG2, "第" + i + "出发地=======没数据=====");
							}

							destinationsEntities = ParseUtils
									.parseDestinations(response.toString());

							if (destinationsEntities != null
									&& destinationsEntities.size() != 0) {
								Log.i(TAG2, "第" + i + "目的地数据============"
										+ destinationsEntities.toString());
								transFormDestinationData(destinationsEntities);
								destinationAdapter = new DestinationAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								destinationAdapter.setDatas(destinationDatas);
								lv_destination.setAdapter(destinationAdapter);
							} else {
								Log.i(TAG2, "第" + i + "结果目的地======没数据======");
							}
							listAdapter = new MySearchFragmentListViewAdapter(
									getActivity(),
									new int[] { R.layout.searchfragment_listview_item });
							listAdapter.setDatas(travelLineEntities);
							lv_searchFragment.setAdapter(listAdapter);
							lv_searchFragment
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub
											final TravelLineEntity travelLineEntity = listAdapter
													.getData().get(position);
											Intent intent = new Intent();
											intent.putExtra("lineId",
													travelLineEntity
															.getLineId());
											intent.putExtra("dateId",
													travelLineEntity
															.getDateId());
											intent.setClass(getActivity(),
													Line_DetailsActivity.class);
											startActivity(intent);
											// Toast.makeText(getActivity(),
											// "你点击了第" + position + "项！",
											// 0).show();
										}
									});
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG1,
									"请求分类数据失败============" + error.getMessage());
						}
					});
		}

	}

	// 初始化控件
	private void initViews(View view, LayoutInflater inflater) {
		// TODO Auto-generated method stub
		btn_departure = (Button) view.findViewById(R.id.btn_departure);
		btn_destination = (Button) view.findViewById(R.id.btn_destination);
		btn_choices = (Button) view.findViewById(R.id.btn_choices);
		btn_price_order = (Button) view.findViewById(R.id.btn_price_order);
		mPullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.mPullToView);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		lv_searchFragment = (ListView) view
				.findViewById(R.id.lv_searchFragment);
		// 初始化悬浮框中的所有控件
		departureView = inflater.inflate(R.layout.departure, null);
		mpopPopupWindow1 = new PopupWindow(departureView,
				LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight() / 2);
		mpopPopupWindow1.setBackgroundDrawable(getActivity().getResources()
				.getDrawable(R.mipmap.zhifu_icon_unsel));
		destinationView = inflater.inflate(R.layout.destination, null);
		mpopPopupWindow2 = new PopupWindow(destinationView,
				LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight() / 2);
		mpopPopupWindow2.setBackgroundDrawable(getActivity().getResources()
				.getDrawable(R.mipmap.zhifu_icon_unsel));
		choicesView = inflater.inflate(R.layout.choices, null);
		mpopPopupWindow3 = new PopupWindow(choicesView,
				LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight() / 2);
		mpopPopupWindow3.setBackgroundDrawable(getActivity().getResources()
				.getDrawable(R.mipmap.zhifu_icon_unsel));
		mpopPopupWindow3.setFocusable(true);
		mpopPopupWindow3.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		mpopPopupWindow3
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		datePickerView = inflater.inflate(R.layout.date_picker, null);
		earliestGoButton = (Button) choicesView.findViewById(R.id.btn_earliest);
		lastestGoButton = (Button) choicesView.findViewById(R.id.btn_lastest);
		price_orderView = inflater.inflate(R.layout.price_order, null);
		mpopPopupWindow4 = new PopupWindow(price_orderView,
				LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight() / 2);
		mpopPopupWindow4.setBackgroundDrawable(getActivity().getResources()
				.getDrawable(R.mipmap.zhifu_icon_unsel));
		lv_priceorder = (ListView) price_orderView
				.findViewById(R.id.lv_priceorder);
		orderAdapter = new OrderAdapter(getActivity(),
				new int[] { R.layout.departure1_item });
		orderAdapter.setDatas(orderDatas);
		lv_priceorder.setAdapter(orderAdapter);
		lv_priceorder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				orderAdapter.changeSelect(position);
				CheckedTextView ctv_choosed = (CheckedTextView) view
						.findViewById(R.id.ctv_departure);
				btn_price_order.setText(ctv_choosed.getText());
				Drawable TopDrawable = getActivity().getResources()
						.getDrawable(
								R.mipmap.search_toolsbar_pricesearch_price_1);
				TopDrawable.setBounds(0, 0, TopDrawable.getMinimumWidth(),
						TopDrawable.getMinimumHeight());
				btn_price_order.setCompoundDrawables(null, TopDrawable, null,
						null);
				if ("价格从高到低".equals(btn_price_order.getText().toString())) {
					priceSortParam = "1";
				} else {
					priceSortParam = "0";
				}
				reFreshDataWithParams();
				mpopPopupWindow4.dismiss();
			}
		});
		lv_departure = (ListView) departureView.findViewById(R.id.lv_departure);
		lv_destination = (ListView) destinationView
				.findViewById(R.id.lv_destination);
		btn_choice_cancelButton = (Button) choicesView
				.findViewById(R.id.btn_choice_cancel);
		btn_choice_clearButton = (Button) choicesView
				.findViewById(R.id.btn_choice_clear);
		btn_choice_determineButton = (Button) choicesView
				.findViewById(R.id.btn_choice_determine);
		rg_choiceGroup = (RadioGroup) choicesView.findViewById(R.id.rg_choice);
		rbtn_daysButton = (RadioButton) choicesView
				.findViewById(R.id.rbtn_days);
		rbtn_daysButton.setTextColor(Color.parseColor("#50D2C2"));
		rbtn_departuretimeButton = (RadioButton) choicesView
				.findViewById(R.id.rbtn_departuretime);
		rbtn_priceButton = (RadioButton) choicesView
				.findViewById(R.id.rbtn_price);
		rbtn_tagButton = (RadioButton) choicesView.findViewById(R.id.rbtn_tag);
		lv_choice_daysListView = (ListView) choicesView
				.findViewById(R.id.lv_choice_days);
		ll_choice_departuretimeLayout = (LinearLayout) choicesView
				.findViewById(R.id.ll_choices_departuretime);
		ll_choices_priceintervaLayout = (LinearLayout) choicesView
				.findViewById(R.id.ll_choices_priceinterval);
		et_lowestpriceEditText = (EditText) choicesView
				.findViewById(R.id.et_lowestprice);
		et_hightestpriEditText = (EditText) choicesView
				.findViewById(R.id.et_hightestprice);
		lv_choice_tagListView = (ListView) choicesView
				.findViewById(R.id.lv_choice_tag);
		tripDaysAdapter = new TripDaysAdapter(getActivity(),
				new int[] { R.layout.tag_listview_item });
		tripDaysAdapter.setDatas(tripDayDatas);
		lv_choice_daysListView.setAdapter(tripDaysAdapter);
		Log.i(TAG2, "listview的item个数" + lv_choice_daysListView.getChildCount());
		// 为行程天数列表设置选择监听器
		lv_choice_daysListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						tripDaysAdapter.changeSelected(position);
						TextView ctv_days = (TextView) view
								.findViewById(R.id.tv_category);
						daysParam = "" + position;
					}
				});
		tagAdapter = new TagAdapter(getActivity(),
				new int[] { R.layout.tag_listview_item });
		tagAdapter.setDatas(tagDatas);
		lv_choice_tagListView.setAdapter(tagAdapter);
		lv_choice_tagListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tagAdapter.changeSelected(position);
				// TODO Auto-generated method stub
				TextView ctv_tag = (TextView) view
						.findViewById(R.id.tv_category);
				tabIdParam = tagDatas.get(position).getId();
				// Toast.makeText(getActivity(), "你选择了第" + position +
				// "项"+ctv_tag.getText().toString(), 0)
				// .show();
			}
		});
		rg_choiceGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.rbtn_days:
							rbtn_daysButton.setTextColor(Color
									.parseColor("#50D2C2"));
							rbtn_departuretimeButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_priceButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_tagButton.setTextColor(Color
									.parseColor("#FF454545"));
							lv_choice_daysListView.setVisibility(View.VISIBLE);
							ll_choice_departuretimeLayout
									.setVisibility(View.GONE);
							ll_choices_priceintervaLayout
									.setVisibility(View.GONE);
							lv_choice_tagListView.setVisibility(View.GONE);

							break;
						case R.id.rbtn_departuretime:
							rbtn_daysButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_departuretimeButton.setTextColor(Color
									.parseColor("#50D2C2"));
							rbtn_priceButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_tagButton.setTextColor(Color
									.parseColor("#FF454545"));
							ll_choice_departuretimeLayout
									.setVisibility(View.VISIBLE);
							lv_choice_daysListView.setVisibility(View.GONE);
							ll_choices_priceintervaLayout
									.setVisibility(View.GONE);
							lv_choice_tagListView.setVisibility(View.GONE);
							break;
						case R.id.rbtn_price:
							rbtn_daysButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_departuretimeButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_priceButton.setTextColor(Color
									.parseColor("#50D2C2"));
							rbtn_tagButton.setTextColor(Color
									.parseColor("#FF454545"));
							ll_choices_priceintervaLayout
									.setVisibility(View.VISIBLE);
							lv_choice_daysListView.setVisibility(View.GONE);
							ll_choice_departuretimeLayout
									.setVisibility(View.GONE);
							lv_choice_tagListView.setVisibility(View.GONE);
							break;
						case R.id.rbtn_tag:
							rbtn_daysButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_departuretimeButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_priceButton.setTextColor(Color
									.parseColor("#FF454545"));
							rbtn_tagButton.setTextColor(Color
									.parseColor("#50D2C2"));
							lv_choice_tagListView.setVisibility(View.VISIBLE);
							lv_choice_daysListView.setVisibility(View.GONE);
							ll_choice_departuretimeLayout
									.setVisibility(View.GONE);
							ll_choices_priceintervaLayout
									.setVisibility(View.GONE);
							break;

						default:
							break;
						}
					}
				});
	}

	// 将出发地数据转化成标准出发地数据
	private void transformData(List<Departure> departuresEntities) {// zheli
		// TODO Auto-generated method stub
		departureDatas = new ArrayList<TurnedDeparture>();
		departureDatas.add(new TurnedDeparture(1, "全部"));
		List<Departure> departureA = new ArrayList<Departure>();
		List<Departure> departureB = new ArrayList<Departure>();
		List<Departure> departureC = new ArrayList<Departure>();
		List<Departure> departureD = new ArrayList<Departure>();
		List<Departure> departureE = new ArrayList<Departure>();
		List<Departure> departureF = new ArrayList<Departure>();
		List<Departure> departureG = new ArrayList<Departure>();
		List<Departure> departureH = new ArrayList<Departure>();
		List<Departure> departureI = new ArrayList<Departure>();
		List<Departure> departureJ = new ArrayList<Departure>();
		List<Departure> departureK = new ArrayList<Departure>();
		List<Departure> departureL = new ArrayList<Departure>();
		List<Departure> departureM = new ArrayList<Departure>();
		List<Departure> departureN = new ArrayList<Departure>();
		List<Departure> departureO = new ArrayList<Departure>();
		List<Departure> departureP = new ArrayList<Departure>();
		List<Departure> departureQ = new ArrayList<Departure>();
		List<Departure> departureR = new ArrayList<Departure>();
		List<Departure> departureS = new ArrayList<Departure>();
		List<Departure> departureT = new ArrayList<Departure>();
		List<Departure> departureU = new ArrayList<Departure>();
		List<Departure> departureV = new ArrayList<Departure>();
		List<Departure> departureW = new ArrayList<Departure>();
		List<Departure> departureX = new ArrayList<Departure>();
		List<Departure> departureY = new ArrayList<Departure>();
		List<Departure> departureZ = new ArrayList<Departure>();
		// 循环切割数据放在26个集合中
		for (int i = 0; i < departuresEntities.size(); i++) {

			if ("a".equals(departuresEntities.get(i).getLetter())) {
				departureA.add(departuresEntities.get(i));
			} else if ("b".equals(departuresEntities.get(i).getLetter())) {
				departureB.add(departuresEntities.get(i));
			} else if ("c".equals(departuresEntities.get(i).getLetter())) {
				departureC.add(departuresEntities.get(i));
			} else if ("d".equals(departuresEntities.get(i).getLetter())) {
				departureD.add(departuresEntities.get(i));
			} else if ("e".equals(departuresEntities.get(i).getLetter())) {
				departureE.add(departuresEntities.get(i));
			} else if ("f".equals(departuresEntities.get(i).getLetter())) {
				departureF.add(departuresEntities.get(i));
			} else if ("g".equals(departuresEntities.get(i).getLetter())) {
				departureG.add(departuresEntities.get(i));
			} else if ("h".equals(departuresEntities.get(i).getLetter())) {
				departureH.add(departuresEntities.get(i));
			} else if ("i".equals(departuresEntities.get(i).getLetter())) {
				departureI.add(departuresEntities.get(i));
			} else if ("j".equals(departuresEntities.get(i).getLetter())) {
				departureJ.add(departuresEntities.get(i));
			} else if ("k".equals(departuresEntities.get(i).getLetter())) {
				departureK.add(departuresEntities.get(i));
			} else if ("l".equals(departuresEntities.get(i).getLetter())) {
				departureL.add(departuresEntities.get(i));
			} else if ("m".equals(departuresEntities.get(i).getLetter())) {
				departureM.add(departuresEntities.get(i));
			} else if ("n".equals(departuresEntities.get(i).getLetter())) {
				departureN.add(departuresEntities.get(i));
			} else if ("o".equals(departuresEntities.get(i).getLetter())) {
				departureO.add(departuresEntities.get(i));
			} else if ("p".equals(departuresEntities.get(i).getLetter())) {
				departureP.add(departuresEntities.get(i));
			} else if ("q".equals(departuresEntities.get(i).getLetter())) {
				departureQ.add(departuresEntities.get(i));
			} else if ("r".equals(departuresEntities.get(i).getLetter())) {
				departureR.add(departuresEntities.get(i));
			} else if ("s".equals(departuresEntities.get(i).getLetter())) {
				departureS.add(departuresEntities.get(i));
			} else if ("t".equals(departuresEntities.get(i).getLetter())) {
				departureT.add(departuresEntities.get(i));
			} else if ("u".equals(departuresEntities.get(i).getLetter())) {
				departureU.add(departuresEntities.get(i));
			} else if ("v".equals(departuresEntities.get(i).getLetter())) {
				departureV.add(departuresEntities.get(i));
			} else if ("w".equals(departuresEntities.get(i).getLetter())) {
				departureW.add(departuresEntities.get(i));
			} else if ("x".equals(departuresEntities.get(i).getLetter())) {
				departureX.add(departuresEntities.get(i));
			} else if ("y".equals(departuresEntities.get(i).getLetter())) {
				departureY.add(departuresEntities.get(i));
			} else if ("z".equals(departuresEntities.get(i).getLetter())) {
				departureZ.add(departuresEntities.get(i));
			}

		}
		// 在这里开始添加数据
		List<List<Departure>> list = new ArrayList<List<Departure>>();
		list.add(departureA);
		list.add(departureB);
		list.add(departureC);
		list.add(departureD);
		list.add(departureE);
		list.add(departureF);
		list.add(departureG);
		list.add(departureH);
		list.add(departureI);
		list.add(departureJ);
		list.add(departureK);
		list.add(departureL);
		list.add(departureM);
		list.add(departureN);
		list.add(departureO);
		list.add(departureP);
		list.add(departureQ);
		list.add(departureR);
		list.add(departureS);
		list.add(departureT);
		list.add(departureU);
		list.add(departureV);
		list.add(departureW);
		list.add(departureX);
		list.add(departureY);
		list.add(departureZ);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).size() != 0) {
				departureDatas.add(new TurnedDeparture(0, list.get(i).get(0)// zheli
						.getLetter()));
				for (int j = 0; j < list.get(i).size(); j++) {
					departureDatas.add(new TurnedDeparture(1, list.get(i)
							.get(j).getDeparture()));
				}
			}
		}
		Log.i(TAG1, "转换后的出发地列表：" + departureDatas.toString());
	}

	// 将目的地数据转化成标准目的地数据
	private void transFormDestinationData(List<Destination> destinationsEntities) {
		// TODO Auto-generated method stub
		destinationDatas = new ArrayList<TurnedDestination>();
		destinationDatas.add(new TurnedDestination(1, "全部"));
		List<Destination> DestinationNull = new ArrayList<Destination>();
		List<Destination> DestinationA = new ArrayList<Destination>();
		List<Destination> DestinationB = new ArrayList<Destination>();
		List<Destination> DestinationC = new ArrayList<Destination>();
		List<Destination> DestinationD = new ArrayList<Destination>();
		List<Destination> DestinationE = new ArrayList<Destination>();
		List<Destination> DestinationF = new ArrayList<Destination>();
		List<Destination> DestinationG = new ArrayList<Destination>();
		List<Destination> DestinationH = new ArrayList<Destination>();
		List<Destination> DestinationI = new ArrayList<Destination>();
		List<Destination> DestinationJ = new ArrayList<Destination>();
		List<Destination> DestinationK = new ArrayList<Destination>();
		List<Destination> DestinationL = new ArrayList<Destination>();
		List<Destination> DestinationM = new ArrayList<Destination>();
		List<Destination> DestinationN = new ArrayList<Destination>();
		List<Destination> DestinationO = new ArrayList<Destination>();
		List<Destination> DestinationP = new ArrayList<Destination>();
		List<Destination> DestinationQ = new ArrayList<Destination>();
		List<Destination> DestinationR = new ArrayList<Destination>();
		List<Destination> DestinationS = new ArrayList<Destination>();
		List<Destination> DestinationT = new ArrayList<Destination>();
		List<Destination> DestinationU = new ArrayList<Destination>();
		List<Destination> DestinationV = new ArrayList<Destination>();
		List<Destination> DestinationW = new ArrayList<Destination>();
		List<Destination> DestinationX = new ArrayList<Destination>();
		List<Destination> DestinationY = new ArrayList<Destination>();
		List<Destination> DestinationZ = new ArrayList<Destination>();
		// 循环切割目的地数据放在27个集合中，其中有一个第一个目的地集合是热门目的地集合
		for (int i = 0; i < destinationsEntities.size(); i++) {
			if ("null".equals(destinationsEntities.get(i).getLetter())
					|| "".equals(destinationsEntities.get(i).getLetter())
					|| destinationsEntities.get(i).getLetter() == null) {
				DestinationNull.add(destinationsEntities.get(i));
			} else if ("a".equals(destinationsEntities.get(i).getLetter())) {
				DestinationA.add(destinationsEntities.get(i));
			} else if ("b".equals(destinationsEntities.get(i).getLetter())) {
				DestinationB.add(destinationsEntities.get(i));
			} else if ("c".equals(destinationsEntities.get(i).getLetter())) {
				DestinationC.add(destinationsEntities.get(i));
			} else if ("d".equals(destinationsEntities.get(i).getLetter())) {
				DestinationD.add(destinationsEntities.get(i));
			} else if ("e".equals(destinationsEntities.get(i).getLetter())) {
				DestinationE.add(destinationsEntities.get(i));
			} else if ("f".equals(destinationsEntities.get(i).getLetter())) {
				DestinationF.add(destinationsEntities.get(i));
			} else if ("g".equals(destinationsEntities.get(i).getLetter())) {
				DestinationG.add(destinationsEntities.get(i));
			} else if ("h".equals(destinationsEntities.get(i).getLetter())) {
				DestinationH.add(destinationsEntities.get(i));
			} else if ("i".equals(destinationsEntities.get(i).getLetter())) {
				DestinationI.add(destinationsEntities.get(i));
			} else if ("j".equals(destinationsEntities.get(i).getLetter())) {
				DestinationJ.add(destinationsEntities.get(i));
			} else if ("k".equals(destinationsEntities.get(i).getLetter())) {
				DestinationK.add(destinationsEntities.get(i));
			} else if ("l".equals(destinationsEntities.get(i).getLetter())) {
				DestinationL.add(destinationsEntities.get(i));
			} else if ("m".equals(destinationsEntities.get(i).getLetter())) {
				DestinationM.add(destinationsEntities.get(i));
			} else if ("n".equals(destinationsEntities.get(i).getLetter())) {
				DestinationN.add(destinationsEntities.get(i));
			} else if ("o".equals(destinationsEntities.get(i).getLetter())) {
				DestinationO.add(destinationsEntities.get(i));
			} else if ("p".equals(destinationsEntities.get(i).getLetter())) {
				DestinationP.add(destinationsEntities.get(i));
			} else if ("q".equals(destinationsEntities.get(i).getLetter())) {
				DestinationQ.add(destinationsEntities.get(i));
			} else if ("r".equals(destinationsEntities.get(i).getLetter())) {
				DestinationR.add(destinationsEntities.get(i));
			} else if ("s".equals(destinationsEntities.get(i).getLetter())) {
				DestinationS.add(destinationsEntities.get(i));
			} else if ("t".equals(destinationsEntities.get(i).getLetter())) {
				DestinationT.add(destinationsEntities.get(i));
			} else if ("u".equals(destinationsEntities.get(i).getLetter())) {
				DestinationU.add(destinationsEntities.get(i));
			} else if ("v".equals(destinationsEntities.get(i).getLetter())) {
				DestinationV.add(destinationsEntities.get(i));
			} else if ("w".equals(destinationsEntities.get(i).getLetter())) {
				DestinationW.add(destinationsEntities.get(i));
			} else if ("x".equals(destinationsEntities.get(i).getLetter())) {
				DestinationX.add(destinationsEntities.get(i));
			} else if ("y".equals(destinationsEntities.get(i).getLetter())) {
				DestinationY.add(destinationsEntities.get(i));
			} else if ("z".equals(destinationsEntities.get(i).getLetter())) {
				DestinationZ.add(destinationsEntities.get(i));
			}
		}
		// 创建一个总的结合把27个集合装到里面去
		List<List<Destination>> list = new ArrayList<List<Destination>>();
		list.add(DestinationNull);
		list.add(DestinationA);
		list.add(DestinationB);
		list.add(DestinationC);
		list.add(DestinationD);
		list.add(DestinationE);
		list.add(DestinationF);
		list.add(DestinationG);
		list.add(DestinationH);
		list.add(DestinationI);
		list.add(DestinationJ);
		list.add(DestinationK);
		list.add(DestinationL);
		list.add(DestinationM);
		list.add(DestinationN);
		list.add(DestinationO);
		list.add(DestinationP);
		list.add(DestinationQ);
		list.add(DestinationR);
		list.add(DestinationS);
		list.add(DestinationT);
		list.add(DestinationU);
		list.add(DestinationV);
		list.add(DestinationW);
		list.add(DestinationX);
		list.add(DestinationY);
		list.add(DestinationZ);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).size() != 0) {
				if (i == 0) {
					destinationDatas.add(new TurnedDestination(0, "热门"));
				} else {
					destinationDatas.add(new TurnedDestination(0, list.get(i)
							.get(0).getLetter()));
				}
				for (int j = 0; j < list.get(i).size(); j++) {
					destinationDatas.add(new TurnedDestination(1, list.get(i)
							.get(j).getDestination()));
				}
			}
		}
		Log.i(TAG1, "转换后的目的地列表：" + destinationDatas.toString());
	}

	// 为下方筛选按钮绑定监听事件
	private void bindEvents() {
		// TODO Auto-generated method stub
		btn_departure.setOnClickListener(this);
		btn_destination.setOnClickListener(this);
		btn_choices.setOnClickListener(this);
		btn_price_order.setOnClickListener(this);
		btn_choice_cancelButton.setOnClickListener(this);
		btn_choice_clearButton.setOnClickListener(this);
		// 筛选悬浮框里面的按钮定义监听事件
		btn_choice_determineButton.setOnClickListener(this);
		lv_departure.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				departureAdapter.changeSelected(position);
				CheckedTextView ctv_departure = (CheckedTextView) view
						.findViewById(R.id.ctv_departure);
				if (ctv_departure != null) {
					btn_departure.setText(ctv_departure.getText());
					Drawable TopDrawable = getActivity()
							.getResources()
							.getDrawable(R.mipmap.search_toolbar_coordinate_1);
					TopDrawable.setBounds(0, 0, TopDrawable.getMinimumWidth(),
							TopDrawable.getMinimumHeight());
					btn_departure.setCompoundDrawables(null, TopDrawable, null,
							null);
					if ("全部".equals(btn_departure.getText().toString())) {
						departureParam = "";
					} else {
						departureParam = btn_departure.getText().toString();
					}

					// 选择好出发地条件之后请求数据
					reFreshDataWithParams();
					// 到这请求数据完成
				} else {
					// Toast.makeText(getActivity(), "请选择正确的地点", 0).show();
					return;
				}
				mpopPopupWindow1.dismiss();
			}
		});
		lv_destination.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				destinationAdapter.changeSelect(position);
				CheckedTextView ctv_destination = (CheckedTextView) view
						.findViewById(R.id.ctv_departure);
				if (ctv_destination != null) {
					btn_destination.setText(ctv_destination.getText());
					Drawable TopDrawable = getActivity().getResources()
							.getDrawable(R.mipmap.search_toolsbar_oval_1);
					TopDrawable.setBounds(0, 0, TopDrawable.getMinimumWidth(),
							TopDrawable.getMinimumHeight());
					btn_destination.setCompoundDrawables(null, TopDrawable,
							null, null);
					// 目的地选择好以后刷新数据
					if ("全部".equals(btn_destination.getText().toString())) {
						destinationParam = "";
					} else {
						destinationParam = btn_destination.getText().toString();
					}
					reFreshDataWithParams();
					// 数据刷新完成
				} else {
					// Toast.makeText(getActivity(), "请选择正确的地点", 0).show();
					return;
				}
				mpopPopupWindow2.dismiss();
			}

		});
		earliestGoButton.setOnClickListener(this);
		lastestGoButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// 点击事件的监听回调
		switch (v.getId()) {
		case R.id.btn_departure:
			mpopPopupWindow1.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.btn_destination:
			mpopPopupWindow2.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.btn_choices:
			mpopPopupWindow3.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.btn_price_order:
			mpopPopupWindow4.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.btn_choice_determine:
			Drawable TopDrawable = getActivity().getResources().getDrawable(
					R.mipmap.search_toolsbar_shape_1);
			TopDrawable.setBounds(0, 0, TopDrawable.getMinimumWidth(),
					TopDrawable.getMinimumHeight());
			btn_choices.setCompoundDrawables(null, TopDrawable, null, null);
			if ("最早出发".equals(earliestGoButton.getText().toString())) {
				earlyDayParam = "";
			} else {
				earlyDayParam = earliestGoButton.getText().toString();
			}
			if ("最晚出发".equals(lastestGoButton.getText().toString())) {
				lateDayParam = "";
			} else {
				lateDayParam = lastestGoButton.getText().toString();
			}
			minPriceParam = et_lowestpriceEditText.getText().toString();
			maxPriceParam = et_hightestpriEditText.getText().toString();
			Log.w(TAG5, "daysParam=" + daysParam + "earlyDayParam="
					+ earlyDayParam + "lateDayParam=" + lateDayParam
					+ "et_lowestpriceEditText="
					+ et_lowestpriceEditText.getText().toString()
					+ "et_hightestpriEditText="
					+ et_hightestpriEditText.getText().toString()
					+ "tabIdParam=" + tabIdParam);
			reFreshDataWithParams();
			mpopPopupWindow3.dismiss();
			break;
		case R.id.btn_earliest:
			Intent intent = new Intent();
			intent.setClass(getActivity(), DatePickerActivity.class);
			startActivityForResult(intent, 1000);
			break;
		case R.id.btn_lastest:
			Intent intent1 = new Intent();
			intent1.setClass(getActivity(), DatePickerActivity.class);
			startActivityForResult(intent1, 2000);
			break;
		case R.id.btn_choice_cancel:
			mpopPopupWindow3.dismiss();
			break;
		case R.id.btn_choice_clear:
			earliestGoButton.setText("最早出发");
			lastestGoButton.setText("最晚出发");
			et_hightestpriEditText.setText("");
			et_lowestpriceEditText.setText("");
			tripDaysAdapter.changeSelected(0);
			tagAdapter.changeSelected(0);
			break;
		default:
			break;
		}
	}

	private void FooterRefresh() {
		page += 1;
		if (i == 0) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("guestId", ShareUtil.getString("receiveguestId"));
			map.put("categoryId", "");
			map.put("departure", "");
			map.put("destination", "");
			map.put("days", "");
			map.put("earlyDay", "");
			map.put("lateDay", "");
			map.put("minPrice", "");
			map.put("maxPrice", "");
			map.put("tabId", "");
			map.put("priceSort", "");
			map.put("curpageNo", page + "");
			map.put("pageSize", "20");
			map.put("keyWord", keyword);
			map.put("type", "app");
			Log.i(TAG, map.toString());
			VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GUSTLINE_LIST,
					new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG2, "搜索的结果数据来啦" + i + "============"
									+ response.toString());
							travelLineEntities = ParseUtils
									.parseTravelLineEntity(response.toString());
							departuresEntities = ParseUtils
									.parseDepartures(response.toString());
							if (departuresEntities != null
									&& departuresEntities.size() != 0) {
								Log.i(TAG2,
										"搜索进来的出发地数据"
												+ departuresEntities.toString());
								transformData(departuresEntities);// zheli
								Log.i(TAG2,
										"搜索进来的转换后的出发地数据"
												+ departureDatas.toString());
								departureAdapter = new DepartureAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								departureAdapter.setDatas(departureDatas);
								lv_departure.setAdapter(departureAdapter);
							} else {
								Log.i(TAG2, "搜索进来的没有出发地数据！！！");
							}

							destinationsEntities = ParseUtils
									.parseDestinations(response.toString());
							if (destinationsEntities != null
									&& destinationsEntities.size() != 0) {
								Log.i(TAG2,
										"搜索进来的目的地数据"
												+ destinationsEntities
														.toString());
								transFormDestinationData(destinationsEntities);
								Log.i(TAG2,
										"搜索进来的转换后的目的地数据"
												+ destinationDatas.toString());
								destinationAdapter = new DestinationAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								destinationAdapter.setDatas(destinationDatas);
								lv_destination.setAdapter(destinationAdapter);
							} else {
								Log.i(TAG2, "搜索进来的没有目的地数据！！！");
							}
							listAdapter.addDatas(travelLineEntities);
							lv_searchFragment.setAdapter(listAdapter);
							mPullToRefreshView.onFooterRefreshComplete();
							// 数据加载完成，让dialog消失
							lv_searchFragment
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											// TODO Auto-generated method stub
											final TravelLineEntity travelLineEntity = listAdapter
													.getData().get(arg2);
											Intent intent = new Intent();
											intent.putExtra("lineId",
													travelLineEntity
															.getLineId());
											intent.putExtra("dateId",
													travelLineEntity
															.getDateId());
											intent.setClass(getActivity(),
													Line_DetailsActivity.class);
											startActivity(intent);
										}
									});
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG1, "搜索失败============" + error.getMessage());
						}
					});
		} else {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("guestId", ShareUtil.getString("receiveguestId"));
			map.put("categoryId", categoryId);
			map.put("departure", "");
			map.put("destination", "");
			map.put("days", "");
			map.put("earlyDay", "");
			map.put("lateDay", "");
			map.put("minPrice", "");
			map.put("maxPrice", "");
			map.put("tabId", "");
			map.put("priceSort", "");
			map.put("curpageNo", page + "");
			map.put("pageSize", "20");
			map.put("keyWord", "");
			map.put("type", "app");
			Log.i(TAG1, map.toString());
			VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GUSTLINE_LIST,
					new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG1, "第" + i + "页的数据" + response.toString());
							travelLineEntities = ParseUtils
									.parseTravelLineEntity(response.toString());
							departuresEntities = ParseUtils
									.parseDepartures(response.toString());

							if (departuresEntities != null
									&& departuresEntities.size() != 0) {
								Log.i(TAG2, "第" + i + "出发地数据============"
										+ departuresEntities.toString());
								transformData(departuresEntities);
								departureAdapter = new DepartureAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								departureAdapter.setDatas(departureDatas);
								lv_departure.setAdapter(departureAdapter);
							} else {
								Log.i(TAG2, "第" + i + "出发地=======没数据=====");
							}

							destinationsEntities = ParseUtils
									.parseDestinations(response.toString());

							if (destinationsEntities != null
									&& destinationsEntities.size() != 0) {
								Log.i(TAG2, "第" + i + "目的地数据============"
										+ destinationsEntities.toString());
								transFormDestinationData(destinationsEntities);
								destinationAdapter = new DestinationAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								destinationAdapter.setDatas(destinationDatas);
								lv_destination.setAdapter(destinationAdapter);
							} else {
								Log.i(TAG2, "第" + i + "结果目的地======没数据======");
							}
							listAdapter.addDatas(travelLineEntities);
							lv_searchFragment.setAdapter(listAdapter);
							mPullToRefreshView.onFooterRefreshComplete();
							lv_searchFragment
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub
											final TravelLineEntity travelLineEntity = listAdapter
													.getData().get(position);
											Intent intent = new Intent();
											intent.putExtra("lineId",
													travelLineEntity
															.getLineId());
											intent.putExtra("dateId",
													travelLineEntity
															.getDateId());
											intent.setClass(getActivity(),
													Line_DetailsActivity.class);
											startActivity(intent);
											// Toast.makeText(getActivity(),
											// "你点击了第" + position + "项！",
											// 0).show();
										}
									});
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG1,
									"请求分类数据失败============" + error.getMessage());
						}
					});
		}
	}

	private void HeaderRefresh() {
		page = 1;
		if (i == 0) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("guestId", ShareUtil.getString("receiveguestId"));
			map.put("categoryId", "");
			map.put("departure", "");
			map.put("destination", "");
			map.put("days", "");
			map.put("earlyDay", "");
			map.put("lateDay", "");
			map.put("minPrice", "");
			map.put("maxPrice", "");
			map.put("tabId", "");
			map.put("priceSort", "");
			map.put("curpageNo", page + "");
			map.put("pageSize", "20");
			map.put("keyWord", keyword);
			map.put("type", "app");
			Log.i(TAG, map.toString());
			VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GUSTLINE_LIST,
					new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG2, "搜索的结果数据来啦" + i + "============"
									+ response.toString());
							travelLineEntities = ParseUtils
									.parseTravelLineEntity(response.toString());
							departuresEntities = ParseUtils
									.parseDepartures(response.toString());
							if (departuresEntities != null
									&& departuresEntities.size() != 0) {
								Log.i(TAG2,
										"搜索进来的出发地数据"
												+ departuresEntities.toString());
								transformData(departuresEntities);// zheli
								Log.i(TAG2,
										"搜索进来的转换后的出发地数据"
												+ departureDatas.toString());
								departureAdapter = new DepartureAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								departureAdapter.setDatas(departureDatas);
								lv_departure.setAdapter(departureAdapter);
							} else {
								Log.i(TAG2, "搜索进来的没有出发地数据！！！");
							}

							destinationsEntities = ParseUtils
									.parseDestinations(response.toString());
							if (destinationsEntities != null
									&& destinationsEntities.size() != 0) {
								Log.i(TAG2,
										"搜索进来的目的地数据"
												+ destinationsEntities
														.toString());
								transFormDestinationData(destinationsEntities);
								Log.i(TAG2,
										"搜索进来的转换后的目的地数据"
												+ destinationDatas.toString());
								destinationAdapter = new DestinationAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								destinationAdapter.setDatas(destinationDatas);
								lv_destination.setAdapter(destinationAdapter);
							} else {
								Log.i(TAG2, "搜索进来的没有目的地数据！！！");
							}

							listAdapter = new MySearchFragmentListViewAdapter(
									getActivity(),
									new int[] { R.layout.searchfragment_listview_item });
							listAdapter.setDatas(travelLineEntities);
							lv_searchFragment.setAdapter(listAdapter);
							mPullToRefreshView.onHeaderRefreshComplete();
							// 数据加载完成，让dialog消失
							lv_searchFragment
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											// TODO Auto-generated method stub
											final TravelLineEntity travelLineEntity = listAdapter
													.getData().get(arg2);
											Intent intent = new Intent();
											intent.putExtra("lineId",
													travelLineEntity
															.getLineId());
											intent.putExtra("dateId",
													travelLineEntity
															.getDateId());
											intent.setClass(getActivity(),
													Line_DetailsActivity.class);
											startActivity(intent);
										}
									});
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG1, "搜索失败============" + error.getMessage());
						}
					});
		} else {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("guestId", ShareUtil.getString("receiveguestId"));
			map.put("categoryId", categoryId);
			map.put("departure", "");
			map.put("destination", "");
			map.put("days", "");
			map.put("earlyDay", "");
			map.put("lateDay", "");
			map.put("minPrice", "");
			map.put("maxPrice", "");
			map.put("tabId", "");
			map.put("priceSort", "");
			map.put("curpageNo", "1");
			map.put("pageSize", "20");
			map.put("keyWord", "");
			map.put("type", "app");
			Log.i(TAG1, map.toString());
			VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GUSTLINE_LIST,
					new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG1, "第" + i + "页的数据" + response.toString());
							travelLineEntities = ParseUtils
									.parseTravelLineEntity(response.toString());
							departuresEntities = ParseUtils
									.parseDepartures(response.toString());

							if (departuresEntities != null
									&& departuresEntities.size() != 0) {
								Log.i(TAG2, "第" + i + "出发地数据============"
										+ departuresEntities.toString());
								transformData(departuresEntities);
								departureAdapter = new DepartureAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								departureAdapter.setDatas(departureDatas);
								lv_departure.setAdapter(departureAdapter);
							} else {
								Log.i(TAG2, "第" + i + "出发地=======没数据=====");
							}

							destinationsEntities = ParseUtils
									.parseDestinations(response.toString());

							if (destinationsEntities != null
									&& destinationsEntities.size() != 0) {
								Log.i(TAG2, "第" + i + "目的地数据============"
										+ destinationsEntities.toString());
								transFormDestinationData(destinationsEntities);
								destinationAdapter = new DestinationAdapter(
										getActivity(), new int[] {
												R.layout.departure_item,
												R.layout.departure1_item });
								destinationAdapter.setDatas(destinationDatas);
								lv_destination.setAdapter(destinationAdapter);
							} else {
								Log.i(TAG2, "第" + i + "结果目的地======没数据======");
							}
							listAdapter = new MySearchFragmentListViewAdapter(
									getActivity(),
									new int[] { R.layout.searchfragment_listview_item });
							listAdapter.setDatas(travelLineEntities);
							lv_searchFragment.setAdapter(listAdapter);
							mPullToRefreshView.onHeaderRefreshComplete();
							lv_searchFragment
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub
											final TravelLineEntity travelLineEntity = listAdapter
													.getData().get(position);
											Intent intent = new Intent();
											intent.putExtra("lineId",
													travelLineEntity
															.getLineId());
											intent.putExtra("dateId",
													travelLineEntity
															.getDateId());
											intent.setClass(getActivity(),
													Line_DetailsActivity.class);
											startActivity(intent);
											// Toast.makeText(getActivity(),
											// "你点击了第" + position + "项！",
											// 0).show();
										}
									});
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG1,
									"请求分类数据失败============" + error.getMessage());
						}
					});
		}
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		HeaderRefresh();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		FooterRefresh();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1000 && resultCode == 1000) {
			earliestGoButton.setText(data.getStringExtra("date"));
		} else if (requestCode == 2000 && resultCode == 1000) {
			lastestGoButton.setText(data.getStringExtra("date"));
		}
	}

	/**
	 * 根据筛选的条件刷新数据
	 */
	private void reFreshDataWithParams() {
		if (i == 0) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("guestId", ShareUtil.getString("receiveguestId"));
			map.put("categoryId", "");
			map.put("departure", departureParam);
			map.put("destination", destinationParam);
			map.put("days", daysParam);
			map.put("earlyDay", earlyDayParam);
			map.put("lateDay", lateDayParam);
			map.put("minPrice", minPriceParam);
			map.put("maxPrice", maxPriceParam);
			map.put("tabId", tabIdParam);
			map.put("priceSort", priceSortParam);
			map.put("curpageNo", page + "");
			map.put("pageSize", "20");
			map.put("keyWord", keyword);
			map.put("type", "app");
			Log.i(TAG, map.toString());
			VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GUSTLINE_LIST,
					new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG2, "搜索的结果数据来啦" + i + "============"
									+ response.toString());
							travelLineEntities = ParseUtils
									.parseTravelLineEntity(response.toString());
							listAdapter = new MySearchFragmentListViewAdapter(
									getActivity(),
									new int[] { R.layout.searchfragment_listview_item });
							listAdapter.setDatas(travelLineEntities);
							lv_searchFragment.setAdapter(listAdapter);
							// 数据加载完成，让dialog消失
							lv_searchFragment
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											// TODO Auto-generated method stub
											final TravelLineEntity travelLineEntity = listAdapter
													.getData().get(arg2);
											Intent intent = new Intent();
											intent.putExtra("lineId",
													travelLineEntity
															.getLineId());
											intent.putExtra("dateId",
													travelLineEntity
															.getDateId());
											intent.setClass(getActivity(),
													Line_DetailsActivity.class);
											startActivity(intent);
										}
									});
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG1, "搜索失败============" + error.getMessage());
						}
					});
		} else {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("guestId", ShareUtil.getString("receiveguestId"));
			map.put("categoryId", categoryId);
			map.put("departure", departureParam);
			map.put("destination", destinationParam);
			map.put("days", daysParam);
			map.put("earlyDay", earlyDayParam);
			map.put("lateDay", lateDayParam);
			map.put("minPrice", minPriceParam);
			map.put("maxPrice", maxPriceParam);
			map.put("tabId", tabIdParam);
			map.put("priceSort", priceSortParam);
			map.put("curpageNo", "1");
			map.put("pageSize", "20");
			map.put("keyWord", "");
			map.put("type", "app");
			Log.i(TAG1, map.toString());
			VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GUSTLINE_LIST,
					new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG1, "第" + i + "页的数据" + response.toString());
							travelLineEntities = ParseUtils
									.parseTravelLineEntity(response.toString());
							listAdapter = new MySearchFragmentListViewAdapter(
									getActivity(),
									new int[] { R.layout.searchfragment_listview_item });
							listAdapter.setDatas(travelLineEntities);
							lv_searchFragment.setAdapter(listAdapter);
							lv_searchFragment
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub
											final TravelLineEntity travelLineEntity = listAdapter
													.getData().get(position);
											Intent intent = new Intent();
											intent.putExtra("lineId",
													travelLineEntity
															.getLineId());
											intent.putExtra("dateId",
													travelLineEntity
															.getDateId());
											intent.setClass(getActivity(),
													Line_DetailsActivity.class);
											startActivity(intent);
											// Toast.makeText(getActivity(),
											// "你点击了第" + position + "项！",
											// 0).show();
										}
									});
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG1,
									"请求分类数据失败============" + error.getMessage());
						}
					});
		}
	}
}
