package com.chiyu.shopapp.ui;

/**
 * @author yangyouni
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.CalendarGridView;
import com.chiyu.shopapp.adapters.CalendarGridViewAdapter;
import com.chiyu.shopapp.bean.Line_DateEntity;
import com.chiyu.shopapp.constants.HttpUtils;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.DateUtil;
import com.chiyu.shopapp.utils.MyProgress;
import com.chiyu.shopapp.utils.NumberHelper;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

public class CalendarViewActivity extends Activity implements OnTouchListener {
	String UserName = "";
	private static final int WHAT_DID_LOAD_DATA = 0;
	private TextView titletxt, ivTitleBtnLeft;
	private Dialog mDialog;
	/**
	 * 日历布局ID
	 */
	private static final int CAL_LAYOUT_ID = 55;
	// 判断手势用
	private static final int SWIPE_MIN_DISTANCE = 120;

	private static final int SWIPE_MAX_OFF_PATH = 250;

	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static ArrayList<Line_DateEntity> mLogList = new ArrayList<Line_DateEntity>();
	private static ArrayList<Line_DateEntity> liners = new ArrayList<Line_DateEntity>();

	// 动画
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;
	private GestureDetector mGesture = null;
	private Context context;
	private SendCityBroadCast sCast;
	private int typeline;
	private String buyercompanyid = "";
	private MyApp app;

	/**
	 * 今天按钮
	 */
	// private Button mTodayBtn;

	/**
	 * 上一个月按钮
	 */
	private ImageView mPreMonthImg;

	/**
	 * 下一个月按钮
	 */
	private ImageView mNextMonthImg;

	/**
	 * 用于显示今天的日期
	 */
	private TextView mDayMessage;

	/**
	 * 用于装截日历的View
	 */
	private RelativeLayout mCalendarMainLayout;

	// 基本变量
	public Context mContext = CalendarViewActivity.this;
	/**
	 * 上一个月View
	 */
	private GridView firstGridView;

	/**
	 * 当前月View
	 */
	private GridView currentGridView;

	/**
	 * 下一个月View
	 */
	private GridView lastGridView;

	/**
	 * 当前显示的日历
	 */
	private Calendar calStartDate = Calendar.getInstance();

	/**
	 * 选择的日历
	 */
	private Calendar calSelected = Calendar.getInstance();

	/**
	 * 今日
	 */
	private Calendar calToday = Calendar.getInstance();

	/**
	 * 当前界面展示的数据源
	 */
	private CalendarGridViewAdapter currentGridAdapter;

	/**
	 * 预装载上一个月展示的数据源
	 */
	private CalendarGridViewAdapter firstGridAdapter;

	/**
	 * 预装截下一个月展示的数据源
	 */
	private CalendarGridViewAdapter lastGridAdapter;
	//
	/**
	 * 当前视图月
	 */
	private int mMonthViewCurrentMonth = 0;

	/**
	 * 当前视图年
	 */
	private int mMonthViewCurrentYear = 0;

	/**
	 * 起始周
	 */
	private int iFirstDayOfWeek = Calendar.MONDAY;

	private String lineId = "";// 线路id

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGesture.onTouchEvent(event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyApp.getInstance().addActivity(this);
		setContentView(R.layout.date);
		app = (MyApp) getApplication();
		Intent intent = getIntent();
		lineId = intent.getStringExtra("LineId");
		initView();
		updateStartDateForMonth();
		loadData();
		generateContetView(mCalendarMainLayout);
		RegisterBroadcast();
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		slideLeftOut = AnimationUtils
				.loadAnimation(this, R.anim.slide_left_out);
		slideRightIn = AnimationUtils
				.loadAnimation(this, R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);

		slideLeftIn.setAnimationListener(animationListener);
		slideLeftOut.setAnimationListener(animationListener);
		slideRightIn.setAnimationListener(animationListener);
		slideRightOut.setAnimationListener(animationListener);

		mGesture = new GestureDetector(this, new GestureListener());

	}

	private void RegisterBroadcast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.neter.broadcast.receiver.SendDownXMLBroadCast");
		sCast = new SendCityBroadCast();
		registerReceiver(sCast, filter);
	}

	public class SendCityBroadCast extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() == "com.neter.broadcast.receiver.SendDownXMLBroadCast") {
				System.out.println("接受到广播");
			}
		}
	}

	/**
	 * 用于初始化控件
	 */
	private void initView() {
		// mTodayBtn = (Button) findViewById(R.id.today_btn);
		mDayMessage = (TextView) findViewById(R.id.day_message);
		mCalendarMainLayout = (RelativeLayout) findViewById(R.id.calendar_main);
		mPreMonthImg = (ImageView) findViewById(R.id.left_img);
		mNextMonthImg = (ImageView) findViewById(R.id.right_img);
		// mTodayBtn.setOnClickListener(onTodayClickListener);
		mPreMonthImg.setOnClickListener(onPreMonthClickListener);

		mNextMonthImg.setOnClickListener(onNextMonthClickListener);
		titletxt = (TextView) findViewById(R.id.main_title);
		titletxt.setText("更多团期");
		ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		ivTitleBtnLeft.setOnClickListener(ivTitleBtnLeft1);
	}

	/**
	 * 用于加载到当前的日期的事件
	 */
	private View.OnClickListener ivTitleBtnLeft1 = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};
	/**
	 * 用于加载到当前的日期的事件
	 */
	@SuppressWarnings("unused")
	private View.OnClickListener onTodayClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			calStartDate = Calendar.getInstance();
			updateStartDateForMonth();
			generateContetView(mCalendarMainLayout);
		}
	};

	/**
	 * 用于加载上一个月日期的事件
	 */
	private View.OnClickListener onPreMonthClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			viewFlipper.setInAnimation(slideRightIn);
			viewFlipper.setOutAnimation(slideRightOut);
			viewFlipper.showPrevious();
			setPrevViewItem();

		}
	};

	/**
	 * 用于加载下一个月日期的事件
	 */
	private View.OnClickListener onNextMonthClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			viewFlipper.setInAnimation(slideLeftIn);
			viewFlipper.setOutAnimation(slideLeftOut);
			viewFlipper.showNext();
			setNextViewItem();
		}
	};

	/**
	 * 主要用于生成发前展示的日历View
	 * 
	 * @param layout
	 *            将要用于去加载的布局
	 */
	private void generateContetView(RelativeLayout layout) {
		// 创建一个垂直的线性布局（整体内容）
		viewFlipper = new ViewFlipper(this);
		viewFlipper.setId(CAL_LAYOUT_ID);
		calStartDate = getCalendarStartDate();
		CreateGirdView();
		RelativeLayout.LayoutParams params_cal = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layout.addView(viewFlipper, params_cal);

		LinearLayout br = new LinearLayout(this);
		RelativeLayout.LayoutParams params_br = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 1);
		params_br.addRule(RelativeLayout.BELOW, CAL_LAYOUT_ID);
		br.setBackgroundColor(getResources().getColor(R.color.white));
		layout.addView(br, params_br);
	}

	/**
	 * 用于创建当前将要用于展示的View
	 */
	private void CreateGirdView() {

		Calendar firstCalendar = Calendar.getInstance(); // 临时
		Calendar currentCalendar = Calendar.getInstance(); // 临时
		Calendar lastCalendar = Calendar.getInstance(); // 临时
		firstCalendar.setTime(calStartDate.getTime());
		currentCalendar.setTime(calStartDate.getTime());
		lastCalendar.setTime(calStartDate.getTime());

		firstGridView = new CalendarGridView(mContext);
		firstCalendar.add(Calendar.MONTH, -1);
		firstGridAdapter = new CalendarGridViewAdapter(this, firstCalendar);
		firstGridView.setAdapter(firstGridAdapter);// 设置菜单Adapter
		firstGridView.setId(CAL_LAYOUT_ID);

		currentGridView = new CalendarGridView(mContext);
		currentGridAdapter = new CalendarGridViewAdapter(this, currentCalendar);
		currentGridView.setAdapter(currentGridAdapter);// 设置菜单Adapter
		currentGridView.setId(CAL_LAYOUT_ID);

		lastGridView = new CalendarGridView(mContext);
		lastCalendar.add(Calendar.MONTH, 1);
		lastGridAdapter = new CalendarGridViewAdapter(this, lastCalendar);
		lastGridView.setAdapter(lastGridAdapter);// 设置菜单Adapter
		lastGridView.setId(CAL_LAYOUT_ID);

		currentGridView.setOnTouchListener(this);
		firstGridView.setOnTouchListener(this);
		lastGridView.setOnTouchListener(this);

		if (viewFlipper.getChildCount() != 0) {
			viewFlipper.removeAllViews();
		}

		viewFlipper.addView(currentGridView);
		viewFlipper.addView(lastGridView);
		viewFlipper.addView(firstGridView);

		String s = calStartDate.get(Calendar.YEAR)
				+ "-"
				+ NumberHelper.LeftPad_Tow_Zero(calStartDate
						.get(Calendar.MONTH) + 1);
		mDayMessage.setText(s);
	}

	/**
	 * 上一个月4689
	 */
	private void setPrevViewItem() {

		mMonthViewCurrentMonth--;// 当前选择月--
		// 如果当前月为负数的话显示上一年
		if (mMonthViewCurrentMonth == -1) {
			mMonthViewCurrentMonth = 11;
			mMonthViewCurrentYear--;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1); // 设置日为当月1日
		calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth); // 设置月
		calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear); // 设置年
		// try {
		// Thread.sleep(1000);
		loadDataEnd();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	/**
	 * 下一个月
	 */
	private void setNextViewItem() {
		mMonthViewCurrentMonth++;

		if (mMonthViewCurrentMonth == 12) {
			mMonthViewCurrentMonth = 0;
			mMonthViewCurrentYear++;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear);

		loadDataEnd();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	/**
	 * 根据改变的日期更新日历 填充日历控件用
	 */
	private void updateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		mMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月
		mMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);// 得到当前日历显示的年

		String s = calStartDate.get(Calendar.YEAR)
				+ "-"
				+ NumberHelper.LeftPad_Tow_Zero(calStartDate
						.get(Calendar.MONTH) + 1);
		mDayMessage.setText(s);

		// 星期一是2 星期天是1 填充剩余天数
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

	}

	/**
	 * 用于获取当前显示月份的时间
	 * 
	 * @return 当前显示月份的时间
	 */
	private Calendar getCalendarStartDate() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);

		if (calSelected.getTimeInMillis() == 0) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}

		return calStartDate;
	}

	AnimationListener animationListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// 当动画完成后调用
			CreateGirdView();
		}
	};

	class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					viewFlipper.setInAnimation(slideLeftIn);
					viewFlipper.setOutAnimation(slideLeftOut);
					viewFlipper.showNext();
					setNextViewItem();

					return true;

				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					viewFlipper.setInAnimation(slideRightIn);
					viewFlipper.setOutAnimation(slideRightOut);
					viewFlipper.showPrevious();
					setPrevViewItem();
					//
					return true;

				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// 得到当前选中的是第几个单元格
			int pos = currentGridView.pointToPosition((int) e.getX(),
					(int) e.getY());
			LinearLayout txtDay = (LinearLayout) currentGridView
					.findViewById(pos + 5000);
			if (txtDay != null) {
				if (txtDay.getTag() != null) {
					Date date = (Date) txtDay.getTag();
					calSelected.setTime(date);
					currentGridAdapter.setSelectedDate(calSelected);
					firstGridAdapter.setSelectedDate(calSelected);
					lastGridAdapter.setSelectedDate(calSelected);
					String time = DateUtil.getStringDate(date);
					mDayMessage.setText(time);// 把选中的日期填到头部
					liners = getSelectlsitLine(time, mLogList);
					System.out.println("mLogListmLogListmLogList"
							+ mLogList.size());
					ArrayList<String> list = getSelectLine(time, mLogList);
					System.out.println("getSelectLinegetSelectLine"
							+ list.size());
					if (list.size() > 1) {
						Intent intent = new Intent();
						intent.setAction("com.neter.broadcast.receiver.Buyers_Order_DetailsActivity1");// 发出自定义广播
//						System.out
//								.println("11111111111111111111111111111111111111");
						intent.putExtra("lineId", liners.get(0).getLineId());
						intent.putExtra("dateId", liners.get(0).getId());
						intent.putExtra("goTime", liners.get(0).getGoTime());
						sendBroadcast(intent);
						finish();
					} else if (list.size() == 1) {
						try {
							if (typeline == 1) {
//								System.out
//										.println("=======执行执行执行执行执行执行执行执行执行执行=");
								Intent intent = new Intent();
								intent.setAction("com.neter.broadcast.receiver.Buyers_Order_DetailsActivity1");// 发出自定义广播
								intent.putExtra("lineId", liners.get(0)
										.getLineId());
								intent.putExtra("dateId", liners.get(0).getId());
								intent.putExtra("goTime", liners.get(0).getGoTime());
								sendBroadcast(intent);
								finish();
							} else {
//								System.out
//										.println("=======执行执行执行执行执行执行执行执行执行执行=");
//								System.out
//										.println("getGotime()----------========="
//												+ liners.get(0).getEndTime());
//								System.out.println("getId()----------========="
//										+ liners.get(0).getLineId());
//								System.out
//										.println("getEndtime()----------========="
//												+ liners.get(0).getEndTime());
								Intent intent = new Intent();
								intent.setAction("com.neter.broadcast.receiver.Buyers_Order_DetailsActivity1");// 发出自定义广播
								intent.putExtra("lineId", liners.get(0)
										.getLineId());
								intent.putExtra("dateId", liners.get(0).getId());
								intent.putExtra("goTime", liners.get(0).getGoTime());
								sendBroadcast(intent);
								finish();

							}

						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}

			Log.i("TEST", "onSingleTapUp -  pos=" + pos);

			return false;
		}
	}

	private ArrayList<String> getSelectLine(String time,
			ArrayList<Line_DateEntity> mLogList) {
		ArrayList<String> mselArrayList = new ArrayList<String>();
		if (mselArrayList != null && mselArrayList.size() > 0) {
			mselArrayList.removeAll(mselArrayList);
		}
		try {
			for (Line_DateEntity mm : mLogList) {
				String date = mm.getGoTime();
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// long lcc_time = Long.valueOf(date);
				// String re_StrTime = sdf.format(new Date(lcc_time * 1000L));
				// System.out.println("timetimetimetime" + time);
				// System.out.println("datedatedatedate" + re_StrTime);
				if (date.equals(time.trim())) {
					mselArrayList.add(mm.getAdultPrice() + "");
				}
				// String date = SystemInfoUtil.SubString(mm.getGotime(), 0,
				// 10);
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// long lcc_time = Long.valueOf(date);
				// String re_StrTime = sdf.format(new Date(lcc_time * 1000L));
				// System.out.println("timetimetimetime" + time);
				// System.out.println("datedatedatedate" + re_StrTime);
				// if (re_StrTime.equals(time.trim())) {
				// mselArrayList.add(mm.getAdultpricemarket());
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mselArrayList;
	}

	private ArrayList<Line_DateEntity> getSelectlsitLine(String time,
			ArrayList<Line_DateEntity> mLogList) {
		ArrayList<Line_DateEntity> mselArrayList = new ArrayList<Line_DateEntity>();
		if (mselArrayList != null && mselArrayList.size() > 0) {
			mselArrayList.removeAll(mselArrayList);
		}
		for (Line_DateEntity mm : mLogList) {

			try {
				String date = mm.getGoTime();
				// String date = SystemInfoUtil.SubString(mm.getGotime(), 0,
				// 10);
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// long lcc_time = Long.valueOf(date);
				// String re_StrTime = sdf.format(new Date(lcc_time * 1000L));
				if (date.equals(time.trim())) {
					mselArrayList.add(mm);
				}
				// if (re_StrTime.equals(time.trim())) {
				// mselArrayList.add(mm);
				// }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mselArrayList;
	}

	@SuppressWarnings("unchecked")
	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_LOAD_DATA: {
				if (msg.obj != null) {
					closeDialog();
					ArrayList<Line_DateEntity> line_Calendars = (ArrayList<Line_DateEntity>) msg.obj;
					System.out.println("line_Calendars======="
							+ line_Calendars.size());
					currentGridAdapter.setLine_Calendars(line_Calendars);

					break;
				}
			}
			}
		}

	};

	private void closeDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(sCast);
	}

	private void loadData() {
		mDialog = MyProgress.createLoadingDialog(CalendarViewActivity.this,
				"数据加载中......");
		mDialog.show();

		String_GetLineDetails(lineId);
	}

	private void loadDataEnd() {
		mDialog = MyProgress.createLoadingDialog(CalendarViewActivity.this,
				"数据加载中......");
		mDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					String logStr = HttpUtils.getLine_dateinfo(lineId);
					ArrayList<Line_DateEntity> loadDataList = null;
//					if (mLogList != null && mLogList.size() > 0) {
//						mLogList.removeAll(mLogList);
//					}
					if (logStr != null) {
						loadDataList = ParseUtils.getLineDate(logStr);
						for (Line_DateEntity body : loadDataList) {
							mLogList.add(body);
						}
					}
					Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
					msg.obj = mLogList;
					msg.sendToTarget();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void String_GetLineDetails(String lineId) {
		VolleyUtils.requestString_Get(URL.DEBUG + URL.GETLINEDETAIL_GOTIME
				+ lineId, new OnRequest() {
			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub

				ArrayList<Line_DateEntity> loadDataList = null;
				loadDataList = ParseUtils.getLineDate(response.toString());
				if (mLogList != null && mLogList.size() > 0) {
					mLogList.removeAll(mLogList);
				}
				for (Line_DateEntity body : loadDataList) {
					mLogList.add(body);
				}
				Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
				msg.obj = mLogList;
				msg.sendToTarget();

			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}

}
