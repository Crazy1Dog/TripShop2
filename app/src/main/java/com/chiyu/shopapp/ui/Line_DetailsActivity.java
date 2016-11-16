package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.Line_DateAdapter;
import com.chiyu.shopapp.bean.Line_DateEntity;
import com.chiyu.shopapp.bean.Line_DetailsEntity;
import com.chiyu.shopapp.bean.My_Person;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.NotifyingScrollView;
import com.chiyu.shopapp.utils.NotifyingScrollView.OnScrollChangedListener;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ScreenUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.controller.EaseUI;

@SuppressLint("JavascriptInterface")
public class Line_DetailsActivity extends Activity {
	private static final String TAG = "tripshop";
	private static final int WHAT_DATELOAD_DATA = 1;
	private static final int camera_upload_imageRequestCode = 2;
	private static int webviewContentWidth = 0;
	private NotifyingScrollView scrollview;
	private RelativeLayout topbar;
	private ImageButton line_lianxi_layout, line_sixin_layout;
	private ImageView topimage;
	private ImageView iv_back;
	private ImageView share_btn;
	private ImageView collection_Imgbt;
	private ImageView iv_consultant, line_details_top;

	private TextView tv_title;
	private TextView line_leixing_txt, line_title_txt, line_departure_txt,
			line_day_txt, line_dianzan_txt, line_detaild_dianzan_txt;
	private TextView line_adultprice_test, line_childprice_test,
			line_babyprice_test, line_traffic_txt, line_dianzhangtuijian_txt;
	private LinearLayout line_yuding_layout, lineDetails_gduodate_tx;
	private ImageButton line_detaild_ibt;
	private GridLayout line_gridtuijian;
	private GridView mListView;
	private WebView webview;
	private int category = 0;// // 线路分类 0:New,1:热销,2:推荐,3:特价,4:豪华,5:纯玩,6:预约,7:品质
	private String line_category_txt;
	private String line_departure;
	private Line_DateAdapter adapter;
	private ArrayList<Line_DateEntity> mendLogData = new ArrayList<Line_DateEntity>();
	public List<Line_DateEntity> listData = new ArrayList<Line_DateEntity>();
	private Intent intent;
	private String potourl = "";
	private String lineId = "";
	private String dateId = "";
	private String potourls = "";
	private String url;
	private Context mContext;
	private SendCityBroadCast sCast;
	private String flag = "Line_DetailsActivity";
	private String userId;
	private String title;
	private String line_tuijian;
	private String managerRecommended;
	private MyApp app;
	private boolean feiyongflag = true;// 判断tuijian开关
	private String ImageUrl;
	// private String DetailsUrl = "http://wei.tripb2c.com/";
	// private String DetailsUrl = "http://pre.shopweb.tripb2b.com/";
	private String DetailsUrl1 = "/line/line.details.html?";
	private String companyId = "";
	private TextView line_date_tx;

	/**
	 * 线路详情
	 */
	@SuppressLint("JavascriptInterface")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.line_details_title);
		MyApp.getInstance().addActivity(this);
		app = (MyApp) getApplication();
		mContext = Line_DetailsActivity.this;
		Intent intent = getIntent();
		lineId = intent.getStringExtra("lineId");
		dateId = intent.getStringExtra("dateId");
		userId = ShareUtil.getString("userId");
		companyId = ShareUtil.getString("companyId");
		RegisterBroadcast();
		initView();
		initListener();
		String_GetLineDetails(url, lineId, dateId);

		VolleyUtils.requestString_Get(URL.DEBUG + URL.GETLINEDETAIL_GOTIME
				+ lineId, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				ArrayList<Line_DateEntity> loadDataList = null;
				ArrayList<Line_DateEntity> mLogList = new ArrayList<Line_DateEntity>();
				loadDataList = ParseUtils.getLineDate(response.toString());
				for (Line_DateEntity body : loadDataList) {
					mLogList.add(body);
				}
				Message msg = mUIHandler.obtainMessage(WHAT_DATELOAD_DATA);
				msg.obj = mLogList;
				msg.sendToTarget();
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
			}
		});
		webview = (WebView) findViewById(R.id.wv_oauth);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setSaveEnabled(true);
		webview.addJavascriptInterface(new JavaScriptInterface(), "HTMLOUT");
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				webview.loadUrl("javascript:window.HTMLOUT.getContentWidth(document.getElementsByTagName('html')[0].scrollWidth);");
			}
		});

		webview.loadUrl(URL.FENXIANG_DEBUG + companyId
				+ "/line/line.details.html?" + "dateid=" + dateId + "&"
				+ "lineid=" + lineId);
	}

	private void String_GetLineDetails(String url, String lineId, String dateId) {
		VolleyUtils.requestString_Get(URL.DEBUG + URL.GETLINE_BASE + lineId
				+ "/" + dateId, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				try {
					Line_DetailsEntity line_DetailsEntity = ParseUtils
							.getLineDetail(response.toString());
					String_GetLineShouCang1();
					title = line_DetailsEntity.getTitle();
					line_title_txt.setText(line_DetailsEntity.getTitle());
					line_day_txt.setText(line_DetailsEntity.getDays() + "天");
					// line_day_txt.setTextColor(Color.argb(255, 0, 255, 0));
					line_adultprice_test.setText(line_DetailsEntity
							.getAdultPrice() + "");
					line_childprice_test.setText(line_DetailsEntity
							.getChildPrice() + "");
					line_babyprice_test.setText(line_DetailsEntity
							.getBabyPrice() + "");
					line_dianzan_txt.setText(line_DetailsEntity
							.getPraiseCount());
					// line_dianzan_txt.setTextColor(Color.argb(255, 0, 255,
					// 0));

					String chanpintxt;
					line_tuijian = line_DetailsEntity.getRecommendedDetail();

					if (line_tuijian.length() >= 59) {
						chanpintxt = ShareUtil.SubString(line_tuijian, 0, 58)
								+ "...";
						line_details_top
								.setBackgroundResource(R.mipmap.detail_icon_down);
					} else {
						chanpintxt = line_tuijian;
					}
					if ("".equals(chanpintxt) || "null".equals(chanpintxt)
							|| chanpintxt == null) {

					} else {
						line_dianzhangtuijian_txt.setText(chanpintxt);
					}

					potourl = line_DetailsEntity.getPhotoPath();
					managerRecommended = line_DetailsEntity
							.getManagerRecommended();
					if (!"".equals(managerRecommended)
							& managerRecommended != null) {
						String[] url1 = managerRecommended.split(",");
						int i;
						// 0=New；1=热销；2=推荐；3=特价；4=豪华；5=纯玩；6=预约；15=预约）',
						line_gridtuijian.removeAllViews();
						for (i = 0; i < url1.length; i++) {
							String tuijian = url1[i];
							if ("0".equals(tuijian)) {
								TextView textView = new TextView(
										Line_DetailsActivity.this);
								textView.setBackgroundResource(R.mipmap.color_8);
								textView.setTextColor(Color
										.parseColor("#F8A3A5"));
								GridLayout.LayoutParams params = new GridLayout.LayoutParams();
								params.setMargins(8, 20, 8, 0);
								textView.setLayoutParams(params);
								textView.setWidth(ScreenUtils.getScreenWidth() / 5);
								textView.setHeight(LayoutParams.WRAP_CONTENT);
								textView.setGravity(Gravity.CENTER);
								textView.setText("New");
								line_gridtuijian.addView(textView);
							} else if ("1".equals(tuijian)) {
								TextView textView = new TextView(
										Line_DetailsActivity.this);
								GridLayout.LayoutParams params = new GridLayout.LayoutParams();
								params.setMargins(8, 20, 8, 0);
								textView.setLayoutParams(params);
								textView.setBackgroundResource(R.mipmap.color_1);
								textView.setTextColor(Color
										.parseColor("#FFF4AE3C"));
								textView.setWidth(ScreenUtils.getScreenWidth() / 5);
								textView.setHeight(LayoutParams.WRAP_CONTENT);
								textView.setGravity(Gravity.CENTER);
								textView.setText("热销");
								line_gridtuijian.addView(textView);
							} else if ("2".equals(tuijian)) {
								TextView textView = new TextView(
										Line_DetailsActivity.this);
								GridLayout.LayoutParams params = new GridLayout.LayoutParams();
								params.setMargins(8, 20, 8, 0);
								textView.setLayoutParams(params);
								textView.setBackgroundResource(R.mipmap.color_2);
								textView.setTextColor(Color
										.parseColor("#FF50D4E5"));
								textView.setWidth(ScreenUtils.getScreenWidth() / 5);
								textView.setHeight(LayoutParams.WRAP_CONTENT);
								textView.setText("推荐");
								textView.setGravity(Gravity.CENTER);
								line_gridtuijian.addView(textView);
							} else if ("3".equals(tuijian)) {
								TextView textView = new TextView(
										Line_DetailsActivity.this);
								GridLayout.LayoutParams params = new GridLayout.LayoutParams();
								params.setMargins(8, 20, 8, 0);
								textView.setLayoutParams(params);
								textView.setBackgroundResource(R.mipmap.color_3);
								textView.setTextColor(Color
										.parseColor("#FFFAA580"));
								textView.setWidth(ScreenUtils.getScreenWidth() / 5);
								textView.setHeight(LayoutParams.WRAP_CONTENT);
								textView.setGravity(Gravity.CENTER);
								textView.setText("特价");
								line_gridtuijian.addView(textView);
							} else if ("4".equals(tuijian)) {
								TextView textView = new TextView(
										Line_DetailsActivity.this);
								GridLayout.LayoutParams params = new GridLayout.LayoutParams();
								params.setMargins(8, 20, 8, 0);
								textView.setLayoutParams(params);
								textView.setBackgroundResource(R.mipmap.color_4);
								textView.setTextColor(Color
										.parseColor("#FFF886C4"));
								textView.setWidth(ScreenUtils.getScreenWidth() / 5);
								textView.setHeight(LayoutParams.WRAP_CONTENT);
								textView.setText("豪华");
								textView.setGravity(Gravity.CENTER);
								line_gridtuijian.addView(textView);
							} else if ("5".equals(tuijian)) {
								TextView textView = new TextView(
										Line_DetailsActivity.this);
								GridLayout.LayoutParams params = new GridLayout.LayoutParams();
								params.setMargins(8, 20, 8, 0);
								textView.setLayoutParams(params);
								textView.setBackgroundResource(R.mipmap.color_5);
								textView.setTextColor(Color
										.parseColor("#FF4DE8B8"));
								textView.setWidth(ScreenUtils.getScreenWidth() / 5);
								textView.setHeight(LayoutParams.WRAP_CONTENT);
								textView.setText("纯玩");
								textView.setGravity(Gravity.CENTER);
								line_gridtuijian.addView(textView);
							} else if ("6".equals(tuijian)) {
								TextView textView = new TextView(
										Line_DetailsActivity.this);
								GridLayout.LayoutParams params = new GridLayout.LayoutParams();
								params.setMargins(8, 20, 8, 0);
								textView.setLayoutParams(params);
								textView.setBackgroundResource(R.mipmap.color_6);
								textView.setTextColor(Color
										.parseColor("#FFB1A5F1"));
								textView.setWidth(ScreenUtils.getScreenWidth() / 6);
								textView.setHeight(LayoutParams.WRAP_CONTENT);
								textView.setText("预约");
								textView.setGravity(Gravity.CENTER);
								line_gridtuijian.addView(textView);
							} else if ("15".equals(tuijian)) {
								TextView textView = new TextView(
										Line_DetailsActivity.this);
								GridLayout.LayoutParams params = new GridLayout.LayoutParams();
								params.setMargins(8, 20, 8, 0);
								textView.setLayoutParams(params);
								textView.setBackgroundResource(R.mipmap.color_7);
								textView.setTextColor(Color
										.parseColor("#FF50D4E5"));
								textView.setWidth(ScreenUtils.getScreenWidth() / 5);
								textView.setHeight(LayoutParams.WRAP_CONTENT);
								textView.setText("品质");
								textView.setGravity(Gravity.CENTER);
								line_gridtuijian.addView(textView);
							}
						}
					}

					if (!"".equals(potourl) & potourl != null) {
						String[] url1 = potourl.split(",");
						potourls = url1[0];
					}
					ImageUrl = URL.IMAGE_DEBUG + potourls;
					VolleyUtils.requestImage(
							URL.IMAGE_DEBUG + app.getJidiaoPhotopath(),
							iv_consultant, R.mipmap.skt_icon_default,
							R.mipmap.skt_icon_default);
					VolleyUtils.requestImage(URL.IMAGE_DEBUG + potourls,
							topimage, R.mipmap.ic_videobg,
							R.mipmap.ic_videobg);
					String didastr = line_DetailsEntity.getDeparture();
					if ("".equals(didastr) || didastr == null
							|| didastr.equals("null")) {

					} else {
						line_departure = ParseUtils.jsonTest(didastr);

					}
					line_departure_txt.setText(line_departure + "出发");
					category = line_DetailsEntity.getCategory();
					// 线路分类 0:New,1:热销,2:推荐,3:特价,4:豪华,5:纯玩,6:预约,7:品质
					switch (category) {
					case 0:
						line_category_txt = "New";
						break;
					case 1:
						line_category_txt = "热销";
						break;
					case 2:
						line_category_txt = "推荐";
						break;
					case 3:
						line_category_txt = "特价";
						break;
					case 4:
						line_category_txt = "豪华";
						break;
					case 5:
						line_category_txt = "纯玩";
						break;
					case 6:
						line_category_txt = "预约";
						break;
					case 7:
						line_category_txt = "火车专列";
						break;

					case 8:
						line_category_txt = "自由行";
						break;
					case 9:
						line_category_txt = "国庆线";
						break;
					case 10:
						line_category_txt = "春节线";
						break;
					case 11:
						line_category_txt = "夏令营";
						break;
					case 12:
						line_category_txt = "夕阳红";
						break;
					case 13:
						line_category_txt = "亲子游";
						break;
					case 14:
						line_category_txt = "自驾游";
						break;
					case 15:
						line_category_txt = "品质";
						break;
					default:
						break;
					}
					line_leixing_txt.setText(line_category_txt);
					int goTraffic = line_DetailsEntity.getGoTraffic();
					int BackTraffic = line_DetailsEntity.getBackTraffic();
					String goTraffics = "";
					String BackTraffics = "";
					switch (goTraffic) {
					case 0:
						goTraffics = "飞机";
						break;
					case 1:
						goTraffics = "汽车";
						break;
					case 2:
						goTraffics = "火车";
						break;
					case 3:
						goTraffics = "高铁";
						break;
					case 4:
						goTraffics = "动车";
						break;
					case 5:
						goTraffics = "轮渡";
						break;

					default:
						break;
					}
					switch (BackTraffic) {
					case 0:
						BackTraffics = "飞机";
						break;
					case 1:
						BackTraffics = "汽车";
						break;
					case 2:
						BackTraffics = "火车";
						break;
					case 3:
						BackTraffics = "高铁";
						break;
					case 4:
						BackTraffics = "动车";
						break;
					case 5:
						BackTraffics = "轮渡";
						break;

					default:
						break;
					}

					line_traffic_txt.setText(goTraffics + "/" + BackTraffics);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
			}
		});

	}

	private void RegisterBroadcast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.neter.broadcast.receiver.Buyers_Order_DetailsActivity1");
		sCast = new SendCityBroadCast();
		registerReceiver(sCast, filter);
	}

	public class SendCityBroadCast extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() == "com.neter.broadcast.receiver.Buyers_Order_DetailsActivity1") {
				try {
					lineId = intent.getStringExtra("lineId");
					dateId = intent.getStringExtra("dateId");
					String goTime = intent.getStringExtra("goTime");
					if ("".equals(goTime) || "null".equals(goTime)) {

					} else {
						Line_DateEntity line_DateEntity = new Line_DateEntity();
						line_DateEntity.setGoTime(goTime);
						if (mendLogData != null && mendLogData.size() > 0) {
							mendLogData.removeAll(mendLogData);
						}
						mendLogData.add(line_DateEntity);
						adapter.setZhoubiansell(mendLogData);
					}
					String_GetLineDetails(potourl, lineId, dateId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		topbar = (RelativeLayout) findViewById(R.id.topbar);
		topimage = (ImageView) findViewById(R.id.topimage);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		share_btn = (ImageView) findViewById(R.id.share_btn);
		collection_Imgbt = (ImageView) findViewById(R.id.collection_Imgbt);
		line_lianxi_layout = (ImageButton) findViewById(R.id.line_lianxi_layout);
		line_sixin_layout = (ImageButton) findViewById(R.id.line_sixin_layout);
		tv_title = (TextView) findViewById(R.id.tv_title);
		line_leixing_txt = (TextView) findViewById(R.id.line_leixing_txt);
		line_title_txt = (TextView) findViewById(R.id.line_title_txt);
		line_departure_txt = (TextView) findViewById(R.id.line_jiaotong_txt);
		line_day_txt = (TextView) findViewById(R.id.line_day_txt);
		line_dianzan_txt = (TextView) findViewById(R.id.line_dianzan_txt);
		line_adultprice_test = (TextView) findViewById(R.id.line_adultprice_test);
		line_childprice_test = (TextView) findViewById(R.id.line_childprice_test);
		line_babyprice_test = (TextView) findViewById(R.id.line_babyprice_test);
		line_traffic_txt = (TextView) findViewById(R.id.line_traffic_txt);
		line_dianzhangtuijian_txt = (TextView) findViewById(R.id.line_dianzhangtuijian_txt);
		mListView = (GridView) findViewById(R.id.line_date_gridview);
		line_yuding_layout = (LinearLayout) findViewById(R.id.line_yuding_layout);
		lineDetails_gduodate_tx = (LinearLayout) findViewById(R.id.lineDetails_gduodate_tx);
		iv_consultant = (ImageView) findViewById(R.id.iv_consultant);
		line_gridtuijian = (GridLayout) findViewById(R.id.line_gridtuijian);
		line_detaild_ibt = (ImageButton) findViewById(R.id.line_detaild_ibt);
		line_detaild_dianzan_txt = (TextView) findViewById(R.id.line_detaild_dianzan_txt);
		line_details_top = (ImageView) findViewById(R.id.line_details_top);
		line_date_tx = (TextView) findViewById(R.id.line_date_tx);
		mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// 设置属性
		mListView.setBackgroundColor(getResources().getColor(R.color.white));
		mListView.setCacheColorHint(getResources().getColor(R.color.white));
		mListView.setScrollingCacheEnabled(false);
		scrollview = (NotifyingScrollView) findViewById(R.id.scrollview);
		if (mendLogData != null) {
			adapter = new Line_DateAdapter(this, mendLogData);
			mListView.setAdapter(adapter);
		}
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int positions = arg2;
				final Line_DateEntity orderEntity = mendLogData.get(positions);
				Intent intent = new Intent(Line_DetailsActivity.this,
						CalendarViewActivity.class);
				lineId = orderEntity.getLineId();
				intent.putExtra("LineId", lineId);
				startActivity(intent);

			}
		});

	}

	private void initListener() {
		iv_back.setOnClickListener(new viewClickListener());
		line_yuding_layout.setOnClickListener(new viewClickListener());
		lineDetails_gduodate_tx.setOnClickListener(new viewClickListener());
		share_btn.setOnClickListener(new viewClickListener());
		collection_Imgbt.setOnClickListener(new viewClickListener());
		line_lianxi_layout.setOnClickListener(new viewClickListener());
		line_sixin_layout.setOnClickListener(new viewClickListener());
		line_detaild_ibt.setOnClickListener(new viewClickListener());
		line_details_top.setOnClickListener(new viewClickListener());
		// 初始化标题栏相关的色值，为透明
		topbar.getBackground().setAlpha(0);
		tv_title.setTextColor(Color.argb(0, 255, 255, 255));

		scrollview.setOnScrollChangedListener(new OnScrollChangedListener() {
			@SuppressLint("ResourceAsColor")
			@Override
			public void onScrollChanged(ScrollView who, int l, int t, int oldl,
					int oldt) {
				// 滑动改变标题栏的透明度和文字透明度，图标
				if (topimage == null) {
					return;
				}
				if (t < 0) {
					return;
				}
				int lHeight = topimage.getHeight();
				if (t <= lHeight) {
					int progress = (int) (new Float(t) / new Float(lHeight) * 255);
					topbar.getBackground().setAlpha(progress);
					tv_title.setTextColor(Color.argb(progress, 255, 255, 255));
					iv_back.setImageResource(R.mipmap.nav_back_white);
					share_btn.setImageResource(R.mipmap.nav_share_white);
					collection_Imgbt
							.setImageResource(R.mipmap.nav_collect_white);
				} else {
					topbar.getBackground().setAlpha(252);
					tv_title.setTextColor(Color.parseColor("#ff0000"));
					iv_back.setImageResource(R.mipmap.nav_back_green);
					share_btn.setImageResource(R.mipmap.nav_share_green);
					topbar.setBackgroundResource(R.mipmap.ic_launcher);
					collection_Imgbt
							.setImageResource(R.mipmap.nav_collect_green);
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(sCast);
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.iv_back) {
				finish();
			} else if (id == R.id.line_details_top) {

				if (feiyongflag) {
					line_details_top
							.setBackgroundResource(R.mipmap.detail_icon_up);
					if ("".equals(line_tuijian) || "null".equals(line_tuijian)
							|| line_tuijian == null) {

					} else {
						line_dianzhangtuijian_txt.setText(line_tuijian);
					}
					
					feiyongflag = false;

				} else {
					line_details_top
							.setBackgroundResource(R.mipmap.detail_icon_down);
					String chanpintxt;
					try {
						if (line_tuijian.length() >= 67) {
							chanpintxt = ShareUtil.SubString(line_tuijian, 0,
									56) + "...";
						} else {
							chanpintxt = line_tuijian;
						}
						if ("".equals(chanpintxt) || "null".equals(chanpintxt)
								|| chanpintxt == null) {

						} else {
							line_dianzhangtuijian_txt.setText(chanpintxt);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					feiyongflag = true;
				}

			} else if (id == R.id.line_yuding_layout) {

				if ("".equals(userId) || userId == null) {
					Intent intent3 = new Intent(Line_DetailsActivity.this,
							LoginActivity.class);
					startActivity(intent3);
				} else {
					intent = new Intent(Line_DetailsActivity.this,
							Line_BookingActivity.class);
					intent.putExtra("lineId", lineId);
					intent.putExtra("dateId", dateId);
					startActivityForResult(intent,
							camera_upload_imageRequestCode);
				}

			} else if (id == R.id.lineDetails_gduodate_tx) {
				Intent intent = new Intent(Line_DetailsActivity.this,
						CalendarViewActivity.class);
				intent.putExtra("LineId", lineId);
				startActivity(intent);
			} else if (id == R.id.share_btn) {
				Intent intent = new Intent(Line_DetailsActivity.this,
						CustomPlatformActivity.class);
				intent.putExtra("title", title);
				intent.putExtra("lineId", lineId);
				intent.putExtra("dateId", dateId);
				intent.putExtra("ImageUrl", ImageUrl);
				startActivity(intent);
			} else if (id == R.id.collection_Imgbt) {
				if ("".equals(userId) || userId == null) {
					Intent intent3 = new Intent(Line_DetailsActivity.this,
							LoginActivity.class);
					startActivity(intent3);
				} else {
					String_GetLineShouCang();
				}

			} else if (id == R.id.line_lianxi_layout) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ ShareUtil.getString("mobile")));
				//startActivity(intent);
			} else if (id == R.id.line_sixin_layout) {
				if (userId == null || "".equals(userId)) {
					Intent intent3 = new Intent(Line_DetailsActivity.this,
							LoginActivity.class);
					intent3.putExtra("title",  app.getTitle());
					intent3.putExtra("IMIsClick", true);
					intent3.putExtra("photopath",
							ShareUtil.getString("photopath"));
					intent3.putExtra("price", app.getPrice());
					intent3.putExtra("dateList", app.getDateList());
					intent3.putExtra("lineId", app.getLineId());
					intent3.putExtra("dateId", app.getDateId());
					intent3.putExtra("flag", flag);
					startActivity(intent3);
				} else if (!EMClient.getInstance().isConnected()
						|| !DemoHelper.getInstance().isLoggedIn()) {
					EMLogserver(ShareUtil.getString("huanxinUserName"),
							ShareUtil.getString("huanxinpwd"));

				} else {
					Intent intent = new Intent(Line_DetailsActivity.this,
							ChatActivity.class);
					intent.putExtra("userId", ShareUtil.getString("username"));
					intent.putExtra("title", app.getTitle());
					intent.putExtra("photopath",
							ShareUtil.getString("photopath"));
					intent.putExtra("price", app.getPrice());
					intent.putExtra("dateList", app.getDateList());
					intent.putExtra("lineId", app.getLineId());
					intent.putExtra("dateId", app.getDateId());
					intent.putExtra("flag", flag);
					startActivity(intent);

				}

			} else if (id == R.id.line_detaild_ibt) {
				line_detaild_ibt
						.setBackgroundResource(R.mipmap.detail_tab_zan_pre);
				line_detaild_dianzan_txt.setText("已点赞");
				if ("".equals(userId) || userId == null) {
					Intent intent3 = new Intent(Line_DetailsActivity.this,
							LoginActivity.class);
					startActivity(intent3);
				} else {
					HashMap<String, String> map1 = new HashMap<String, String>();
					map1.put("lineIds", lineId);
					map1.put("userId", ShareUtil.getString("userId"));
					VolleyUtils.requestString_Post(map1, URL.DEBUG
							+ URL.CHECKLIKE, new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							// Log.w(TAG2, "判断是否已经点赞"+response.toString());
							try {
								JSONObject object = new JSONObject(response
										.toString());
								JSONObject object2 = object
										.getJSONObject("result");
								int checkFlag = object2.getInt(lineId);
								if (checkFlag == 0) {
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("lineId", lineId);
									map.put("userId",
											ShareUtil.getString("userId"));
									VolleyUtils.requestString_Post(map,
											URL.DEBUG + URL.lIKE,
											new OnRequest() {

												@Override
												public void response(
														String url,
														String response) {
													Toast.makeText(mContext,
															"点赞成功", Toast.LENGTH_SHORT).show();
												}

												@Override
												public void errorResponse(
														String url,
														VolleyError error) {
													// TODO Auto-generated
													// method stub

												}
											});
								} else {
									Toast.makeText(mContext, "已经赞过了", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub

						}
					});
				}

			}
		}
	}

	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DATELOAD_DATA: {
				setProgressBarIndeterminateVisibility(false);
				if (msg.obj != null) {
					if (mendLogData != null && mendLogData.size() > 0) {
						mendLogData.removeAll(mendLogData);
					}
					ArrayList<Line_DateEntity> logLoad = (ArrayList<Line_DateEntity>) msg.obj;
					mendLogData.addAll(logLoad);
				}
				adapter.setZhoubiansell(mendLogData);
				break;
			}
			}
		}
	};

	class JavaScriptInterface {
		public void getContentWidth(String value) {
			if (value != null) {
				webviewContentWidth = Integer.parseInt(value);
			}
		}
	}

	private void String_GetLineShouCang() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("lineId", lineId);
		map.put("userId", ShareUtil.getString("userId"));
		VolleyUtils.requestString_Post(map, URL.DEBUG + URL.COLLECT,
				new OnRequest() {

					@Override
					public void response(String url, String response) {
						// TODO Auto-generated method stub
						String result = "";
						Log.w(TAG, "收藏成功" + response.toString());
						System.out.println("shoucang------------fanhui----"
								+ response.toString());

						try {
							JSONObject object = new JSONObject(response
									.toString());
							int code = object.getInt("code");
							result = object.getString("message");
							if (code == 590) {
								collection_Imgbt
										.setBackgroundResource(R.mipmap.nav_collected);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// Toast.makeText(Line_DetailsActivity.this, result, 0)
						// .show();
					}

					@Override
					public void errorResponse(String url, VolleyError error) {
						// TODO Auto-generated method stub
						Log.w(TAG, "收藏失败" + error.getMessage());
						Toast.makeText(Line_DetailsActivity.this, "收藏失败", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	private void String_GetLineShouCang1() {
		VolleyUtils.requestString_Get(URL.DEBUG + URL.COLLECT1 + "/"
				+ ShareUtil.getString("userId") + "/" + lineId,
				new OnRequest() {

					@Override
					public void response(String url, String response) {

						// TODO Auto-generated method stub
						String result = "";
						// {"code":"200","message":"","result":"false"}
						try {
							JSONObject object = new JSONObject(response
									.toString());
							int code = object.getInt("code");
							result = object.getString("result");
							if (result.equals("false")) {

							} else {
								collection_Imgbt
										.setBackgroundResource(R.mipmap.nav_collected);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// Toast.makeText(Line_DetailsActivity.this, result, 0)
						// .show();
					}

					@Override
					public void errorResponse(String url, VolleyError error) {
						// TODO Auto-generated method stub
						Log.w(TAG, "收藏失败" + error.getMessage());
						Toast.makeText(Line_DetailsActivity.this, "收藏失败", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}

	public void EMLogserver(String currentUsername, String currentPassword) {
		currentUsername = ShareUtil.getString("huanxinUserName");
		currentPassword = ShareUtil.getString("huanxinpwd");
		DemoHelper.getInstance().setCurrentUserName(currentUsername);
		EMClient.getInstance().login(currentUsername, currentPassword,
				new EMCallBack() {

					@Override
					public void onSuccess() {
						// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
						// ** manually load all local groups and
						EMClient.getInstance().groupManager().loadAllGroups();
						EMClient.getInstance().chatManager()
								.loadAllConversations();

						// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
						boolean updatenick = EMClient.getInstance()
								.updateCurrentUserNick(
										MyApp.currentUserNick.trim());
						if (!updatenick) {
							Log.e("LoginActivity",
									"update current user nick fail");
						}
						// 异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
						DemoHelper.getInstance().getUserProfileManager()
								.asyncGetCurrentUserInfo();
						// 进入主页面
						Intent intent = new Intent(Line_DetailsActivity.this,
								ChatActivity.class);
						intent.putExtra("userId",
								ShareUtil.getString("contactname"));
						intent.putExtra("userId",
								ShareUtil.getString("username"));
						intent.putExtra("flag", flag);
						intent.putExtra("title", app.getTitle());
						intent.putExtra("photopath",
								ShareUtil.getString("photopath"));
						intent.putExtra("price", app.getPrice());
						intent.putExtra("dateId", app.getDateId());
						intent.putExtra("dateList", app.getDateList());
						intent.putExtra("lineId", app.getLineId());
						startActivity(intent);

					}

					@Override
					public void onProgress(int progress, String status) {
					}

					@Override
					public void onError(final int code, final String message) {
						runOnUiThread(new Runnable() {
							public void run() {
								// Toast.makeText(
								// getApplicationContext(),
								// getString(R.string.Login_failed)
								// + message, Toast.LENGTH_SHORT)
								// .show();
							}
						});
					}
				});
	}
}
