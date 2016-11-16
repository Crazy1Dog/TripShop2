package com.chiyu.shopapp.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.Line_DetailsEntity;
import com.chiyu.shopapp.bean.Order_SuccessEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.MyProgress;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * 填写订单页面
 */
public class Line_BookingActivity extends Activity {
	private static final String TAG = "tripshop";
	private static final int WHAT_DTD_SUCCESS = 0;
	private TextView main_title, main_ivTitleBtnLeft;
	/*** 线路标题、成人、儿童、婴儿市场价格、团期 ***/
	private TextView lineyuding_biaoti_txt, line_bookingtuanqi_txt;
	public static TextView line_adultprice_txt;
	public static TextView line_childprice_txt;
	public static TextView line_babyprice_txt;
	/*** 预订人姓名、手机号码 ***/
	private EditText line_booking_name_edtxt, line_booking_mobile_edtxt;
	/*** 费用明细成人价格、数量、总额 ***/
	private TextView booking_adult_price, booking_adult_number,
			booking_adult_totalprice_txt;
	/*** 费用明细 儿童价格、数量、总额 ***/
	private TextView booking_child_price, booking_child_number,
			booking_child_totalprice_txt;
	/*** 费用明细 婴儿价格、数量、总额 ***/
	private TextView booking_baby_price, booking_baby_number,
			booking_baby_totalprice_txt;
	/*** 费用明细 单房差价格、数量、总额 ***/
	private TextView booking_singleRoom_price, booking_singleRoom_number,
			booking_singleRoom_totalprice_txt;
	/*** 订单总额组件 ***/
	private TextView line_booking_zongprice_txt, booking_order_totalprice_txt;
	/*** 成人&儿童、婴儿增加、减少按钮 ****/
	private ImageButton booking_adult_minus_bt, booking_adult_plus_bt,
			booking_child_minus_bt, booking_child_plus_bt,
			booking_baby_minus_bt, booking_baby_plus_bt;
	/*** 出行人预定成人、儿童、婴儿人数组件 *****/
	private TextView line_booking_adult_number_txt,
			line_booking_child_number_txt, line_booking_baby_number_txt;
	private LinearLayout lineDetails_yuding_layout, line_feiyong_layout;
	private ImageButton line_booking_feiyong_Imbt, line_booking_dateibt;
	private Intent intent;
	private boolean feiyongflag = true;// 费用明细判断开关
	private String lineId = "";// 线路id
	private String dateId = "";// 线路团旗
	private String url;
	private int person_num = 0;
	/*** 预定成人数、儿童数、婴儿数 ***/
	private String adult_number, child_number, baby_number;

	/****** 费用明细成人、儿童、婴儿总金额 ***********/
	private long booking_adult_totalprice, booking_child_totalprice,
			booking_baby_totalprice, booking_singleRoom_totalprice;
	/******************/
	private long booking_order_totalprice;
	/*** 提交订单需要的参数 *******/
	private String receiveGuestId = "";// :微店ID
	private String companyId = "";// :公司ID
	private String userId = "";// :计调ID
	private String b2cUserId = "";// :用户ID
	private String lineCategory;// :线路类型（0自发；1代销）
	private int adult;// :成人数
	private int child;// :儿童数
	private int baby;// :婴儿数
	private String goTime = "";// :出团日期
	private String backTime = "";// :返团时间
	private String goTraffic = "";// :出发交通
	private String backTraffic = "";// :回团交通
	private String detail = "";// :说明
	private String prices = "";// 价格 json格式
	private String operations = "";// 预定人 json格式
	private long singleRoom;// 单房差
	private long adultPriceMarket;// 成人市场价
	private long adultPrice;// 成人结算价
	private long childPriceMarket;// 儿童市场价
	private long childPrice;// 儿童结算价
	private long babyPriceMarket;// 婴儿市场价
	private long babyPrice;// 婴儿结算价
	private String name = "";// 名字
	private String mobile = "";// 手机
	private String tel = "";// 电话
	private String email = "";// 邮箱
	private String address = "";// 地址
	private String b2bOrderId;
	private String orderId;
	private int code;
	private String message;
	private SendCityBroadCast sCast;
	public long isTakeAdult;
	public long isTakeChild;
	public long isTakeBaby;
	private double totalPrice;// 订单总金额
	private Double singleRoomAmount;// 单房差总金额
	private Double b2bAmount;// b2b总金额
	private int isPay;// :是否实时支付 0 否；1 是
	private Dialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.line_booking);
		MyApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		lineId = intent.getStringExtra("lineId");
		dateId = intent.getStringExtra("dateId");
		receiveGuestId = ShareUtil.getString("receiveguestId");// :微店ID
//		companyId = ShareUtil.getString("companyId");// :公司ID
		userId = ShareUtil.getString("memberId");// :计调ID
		b2cUserId = ShareUtil.getString("userId");
		String_GetLineDetails(url, lineId, dateId);
		RegisterBroadcast();
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		main_title.setText("填写订单");
		lineDetails_yuding_layout = (LinearLayout) findViewById(R.id.lineDetails_yuding_layout);
		line_feiyong_layout = (LinearLayout) findViewById(R.id.line_feiyong_layout);
		lineyuding_biaoti_txt = (TextView) findViewById(R.id.lineyuding_biaoti_txt);
		line_adultprice_txt = (TextView) findViewById(R.id.line_adultprice_txt);
		line_childprice_txt = (TextView) findViewById(R.id.line_childprice_txt);
		line_babyprice_txt = (TextView) findViewById(R.id.line_babyprice_txt);
		line_bookingtuanqi_txt = (TextView) findViewById(R.id.line_bookingtuanqi_txt);
		line_booking_feiyong_Imbt = (ImageButton) findViewById(R.id.line_booking_feiyong_imbt);
		line_booking_dateibt = (ImageButton) findViewById(R.id.line_booking_dateibt);
		line_booking_name_edtxt = (EditText) findViewById(R.id.line_booking_name_edtxt);
		line_booking_mobile_edtxt = (EditText) findViewById(R.id.line_booking_mobile_edtxt);
		/**** 成人 ****/
		booking_adult_price = (TextView) findViewById(R.id.booking_adult_price);
		booking_adult_number = (TextView) findViewById(R.id.booking_adult_number);
		booking_adult_totalprice_txt = (TextView) findViewById(R.id.booking_adult_totalprice);
		/*** 儿童 ******/
		booking_child_price = (TextView) findViewById(R.id.booking_child_price);
		booking_child_number = (TextView) findViewById(R.id.booking_child_number);
		booking_child_totalprice_txt = (TextView) findViewById(R.id.booking_child_totalprice);
		/*** 婴儿 ******/
		booking_baby_price = (TextView) findViewById(R.id.booking_baby_price);
		booking_baby_number = (TextView) findViewById(R.id.booking_baby_number);
		booking_baby_totalprice_txt = (TextView) findViewById(R.id.booking_baby_totalprice);
		/*** 单房差 ******/
		booking_singleRoom_price = (TextView) findViewById(R.id.booking_singleRoom_price);
		booking_singleRoom_number = (TextView) findViewById(R.id.booking_singleRoom_number);
		booking_singleRoom_totalprice_txt = (TextView) findViewById(R.id.booking_singleRoom_totalprice);
		/***** 订单总额 ****/
		line_booking_zongprice_txt = (TextView) findViewById(R.id.line_booking_zonge_txt);
		booking_order_totalprice_txt = (TextView) findViewById(R.id.booking_order_totalprice);
		/*** 成人&儿童、婴儿增加、减少按钮 ****/
		booking_adult_minus_bt = (ImageButton) findViewById(R.id.booking_adult_minus_bt);
		booking_adult_plus_bt = (ImageButton) findViewById(R.id.booking_adult_plus_bt);
		booking_child_minus_bt = (ImageButton) findViewById(R.id.booking_child_minus_bt);
		booking_child_plus_bt = (ImageButton) findViewById(R.id.booking_child_plus_bt);
		booking_baby_minus_bt = (ImageButton) findViewById(R.id.booking_baby_minus_bt);
		booking_baby_plus_bt = (ImageButton) findViewById(R.id.booking_baby_plus_bt);
		/*** 出行人预定成人、儿童、婴儿人数 *****/
		line_booking_adult_number_txt = (TextView) findViewById(R.id.booking_adult_number_txt);
		line_booking_child_number_txt = (TextView) findViewById(R.id.booking_child_number_txt);
		line_booking_baby_number_txt = (TextView) findViewById(R.id.booking_baby_number_txt);

	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		lineDetails_yuding_layout.setOnClickListener(new viewClickListener());
		line_feiyong_layout.setOnClickListener(new viewClickListener());
		line_booking_feiyong_Imbt.setOnClickListener(new viewClickListener());
		booking_adult_minus_bt.setOnClickListener(new viewClickListener());
		booking_adult_plus_bt.setOnClickListener(new viewClickListener());
		booking_child_minus_bt.setOnClickListener(new viewClickListener());
		booking_child_plus_bt.setOnClickListener(new viewClickListener());
		booking_baby_minus_bt.setOnClickListener(new viewClickListener());
		booking_baby_plus_bt.setOnClickListener(new viewClickListener());
		line_booking_dateibt.setOnClickListener(new viewClickListener());
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();
			} else if (id == R.id.lineDetails_yuding_layout) {

				adult = Integer.valueOf(line_booking_adult_number_txt.getText()
						.toString());// :成人数
				child = Integer.valueOf(line_booking_child_number_txt.getText()
						.toString());// :儿童数
				baby = Integer.valueOf(line_booking_baby_number_txt.getText()
						.toString());
				name = line_booking_name_edtxt.getText().toString();// 名字
				mobile = line_booking_mobile_edtxt.getText().toString();// 手机
				if (adult == 0 & child == 0 & baby == 0) {
					Toast.makeText(Line_BookingActivity.this, "请选择出游人数",
							Toast.LENGTH_SHORT).show();
				
				} else if ("".equals(mobile) || mobile == null) {

					Toast.makeText(Line_BookingActivity.this, "请输入预订人手机号码",
							Toast.LENGTH_SHORT).show();
				} else if ("".equals(name) || name == null) {
					Toast.makeText(Line_BookingActivity.this, "请输入预订人姓名",
							Toast.LENGTH_SHORT).show();

				} else if (mobile.length() != 11) {
					Toast.makeText(Line_BookingActivity.this, "请输入正确的手机号码",
							Toast.LENGTH_SHORT).show();
				} else if (isMobileNOs(mobile) == false) {
					Toast.makeText(Line_BookingActivity.this, "请输入正确的手机号码",
							Toast.LENGTH_SHORT).show();
				} else {
					operations = String_BookingPesonJson(name, mobile, tel,
							email, detail, address);
					prices = String_BookingPriceJson(singleRoom,
							adultPriceMarket, adultPrice, childPriceMarket,
							childPrice, babyPriceMarket, babyPrice);// 价格 json格式
					String_PostSaveOrder(receiveGuestId, companyId, userId,
							b2cUserId, lineCategory, lineId, dateId, child,
							baby, isPay, goTime, backTime, goTraffic,
							backTraffic, detail, prices, operations);
				}
			} else if (id == R.id.line_booking_feiyong_imbt) {
				if (feiyongflag) {
					line_feiyong_layout.setVisibility(View.VISIBLE);
					line_booking_feiyong_Imbt
							.setBackgroundResource(R.mipmap.dingdan_btn_up_arrow);
					feiyongflag = false;

				} else {
					line_feiyong_layout.setVisibility(View.GONE);
					line_booking_feiyong_Imbt
							.setBackgroundResource(R.mipmap.dingdan_btn_down_arrow);
					feiyongflag = true;
				}
			} else if (id == R.id.booking_adult_plus_bt) {
				if (isTakeAdult == 1) {
					int num = Integer.valueOf(line_booking_adult_number_txt
							.getText().toString());
					num++;
					line_booking_adult_number_txt
							.setText(Integer.toString(num));
					Line_OrderPrice();
				} else {
					Toast.makeText(Line_BookingActivity.this, "该线路暂时不收成人",
							Toast.LENGTH_SHORT).show();
				}

			} else if (id == R.id.booking_adult_minus_bt) {
				person_num = Integer.valueOf(line_booking_adult_number_txt
						.getText().toString());
				Log.v("tag", "num==" + person_num);
				if (person_num > 0) {
					person_num--;
					line_booking_adult_number_txt.setText(Integer
							.toString(person_num));
				}
				Line_OrderPrice();
			} else if (id == R.id.booking_child_plus_bt) {
				if (isTakeChild == 1) {
					person_num = Integer.valueOf(line_booking_child_number_txt
							.getText().toString());
					person_num++;
					line_booking_child_number_txt.setText(Integer
							.toString(person_num));
					Line_OrderPrice();
				} else {
					Toast.makeText(Line_BookingActivity.this, "该线路暂时不收儿童",
							Toast.LENGTH_SHORT).show();
				}

			} else if (id == R.id.booking_child_minus_bt) {
				person_num = Integer.valueOf(line_booking_child_number_txt
						.getText().toString());
				if (person_num > 0) {
					person_num--;
					line_booking_child_number_txt.setText(Integer
							.toString(person_num));
				}
				Line_OrderPrice();
			} else if (id == R.id.booking_baby_plus_bt) {
				if (isTakeBaby == 1) {
					person_num = Integer.valueOf(line_booking_baby_number_txt
							.getText().toString());
					person_num++;
					line_booking_baby_number_txt.setText(Integer
							.toString(person_num));
					Log.v("tag", "bReduce==" + person_num);
					Line_OrderPrice();
				} else {
					Toast.makeText(Line_BookingActivity.this, "该线路暂时不收婴儿",
							Toast.LENGTH_SHORT).show();
				}

			} else if (id == R.id.booking_baby_minus_bt) {
				person_num = Integer.valueOf(line_booking_baby_number_txt
						.getText().toString());
				if (person_num > 0) {
					person_num--;
					line_booking_baby_number_txt.setText(Integer
							.toString(person_num));
				}
				Line_OrderPrice();
			} else if (id == R.id.line_booking_dateibt) {
				Intent intent = new Intent(Line_BookingActivity.this,
						CalendarViewActivity.class);
				intent.putExtra("LineId", lineId);
				startActivity(intent);
			}
		}
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
					String_GetLineDetails(url, lineId, dateId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/************ 根据线路lineId和团旗dateId获取线路详情数据 *************/
	private void String_GetLineDetails(String url, String lineId, String dateId) {
		VolleyUtils.requestString_Get(URL.DEBUG + URL.GETLINE_BASE + lineId
				+ "/" + dateId, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				Line_DetailsEntity line_DetailsEntity = ParseUtils
						.getLineDetail(response.toString());
				String chanpintxt;
				try {
					if (line_DetailsEntity.getTitle().length() >= 38) {
						chanpintxt = ShareUtil.SubString(line_DetailsEntity
								.getTitle().toString(), 0, 36)
								+ "...";
					} else {
						chanpintxt = line_DetailsEntity.getTitle();
					}

					lineyuding_biaoti_txt.setText(chanpintxt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				line_adultprice_txt.setText(line_DetailsEntity.getAdultPrice()
						+ "");
				line_childprice_txt.setText(line_DetailsEntity.getChildPrice()
						+ "");
				line_babyprice_txt.setText(line_DetailsEntity.getBabyPrice()
						+ "");
				// 设置增加按钮的背景色
				isTakeAdult = line_DetailsEntity.getIsTakeAdult();
				isTakeChild = line_DetailsEntity.getIsTakeChild();
				isTakeBaby = line_DetailsEntity.getIsTakeBaby();
				if (isTakeBaby == 1) {
					booking_baby_plus_bt
							.setBackgroundResource(R.mipmap.dingdan_btn_plus);
				} else {
					booking_baby_plus_bt
							.setBackgroundResource(R.mipmap.dingdan_btn_plus_grey);
				}
				if (isTakeChild == 1) {
					booking_child_plus_bt
							.setBackgroundResource(R.mipmap.dingdan_btn_plus);
				} else {
					booking_child_plus_bt
							.setBackgroundResource(R.mipmap.dingdan_btn_plus_grey);
				}
				if (isTakeAdult == 1) {
					booking_adult_plus_bt
							.setBackgroundResource(R.mipmap.dingdan_btn_plus);
				} else {
					booking_adult_plus_bt
							.setBackgroundResource(R.mipmap.dingdan_btn_plus_grey);
				}
				line_bookingtuanqi_txt.setText(line_DetailsEntity.getGoTime());
				lineCategory = line_DetailsEntity.getIsReceive();// :线路类型（0自发；1代销）
				goTime = line_DetailsEntity.getGoTime();// :出团日期
				backTime = line_DetailsEntity.getBackTime();// :返团时间
				singleRoom = line_DetailsEntity.getSingleRoom();// 单房差
				adultPriceMarket = line_DetailsEntity.getAdultPriceMarket();// 成人市场价
				adultPrice = line_DetailsEntity.getAdultPrice();// 成人结算价
				childPriceMarket = line_DetailsEntity.getChildPriceMarket();// 儿童市场价
				childPrice = line_DetailsEntity.getChildPrice();// 儿童结算价
				babyPriceMarket = line_DetailsEntity.getBabyPriceMarket();// 婴儿市场价
				babyPrice = line_DetailsEntity.getBabyPrice();// 婴儿结算价
				totalPrice = line_DetailsEntity.getTotalPrice();// 订单总金额
				singleRoomAmount = line_DetailsEntity.getSingleRoomAmount();// 单房差总金额
				b2bAmount = line_DetailsEntity.getB2bAmount();// b2b总金额
				isPay = line_DetailsEntity.getIsPay();// :是否实时支付 0 否；1 是
				companyId = line_DetailsEntity.getCompanyId();

			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				Log.i(TAG, "====没有拿到验证====" + error.getMessage());
			}
		});
	}

	/*********** 提交预定订单信息 *****************/
	private void String_PostSaveOrder(String receiveGuestId, String companyId,
			String userId, String b2cUserId, String lineCategory,
			String lineId, String dateId, int child, int baby, int isPay,
			String goTime, String backTime, String goTraffic,
			String backTraffic, String detail, String prices, String operations) {
		mDialog = MyProgress.createLoadingDialog(
				Line_BookingActivity.this, "订单提交中......");
		mDialog.show();
		Map<String, String> map = new HashMap<String, String>();
		map.put("receiveGuestId", receiveGuestId);
		map.put("companyId", companyId);
		map.put("userId", userId);
		map.put("b2cUserId", b2cUserId);
		map.put("lineCategory", lineCategory);
		map.put("lineId", lineId);
		map.put("dateId", dateId);
		map.put("adult", adult + "");
		map.put("child", child + "");
		map.put("baby", baby + "");
		map.put("isPay", isPay + "");
		map.put("goTime", goTime);
		map.put("backTime", backTime);
		map.put("goTraffic", goTraffic);
		map.put("backTraffic", backTraffic);
		map.put("detail", detail);
		map.put("prices", prices);
		map.put("operations", operations);

		VolleyUtils.requestString_Post((HashMap<String, String>) map, URL.DEBUG
				+ URL.POSTORDER_SAVEORDER, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				Order_SuccessEntity order_SuccessEntity = ParseUtils
						.getOrderSuccess(response.toString());
				b2bOrderId = order_SuccessEntity.getB2bOrderId();
				orderId = order_SuccessEntity.getOrderId();
				code = order_SuccessEntity.getCode();
				message = order_SuccessEntity.getMessage();
				if (code == 200) {
					closeDialog();
					Toast.makeText(Line_BookingActivity.this, message,
							Toast.LENGTH_SHORT).show();
					intent = new Intent(Line_BookingActivity.this,
							Line_Confirm_OrderActivity.class);
					intent.putExtra("b2bOrderId", b2bOrderId);
					intent.putExtra("orderId", orderId);
					startActivityForResult(intent, WHAT_DTD_SUCCESS);

				} else {
					closeDialog();
					Toast.makeText(Line_BookingActivity.this, message,
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(sCast);
	}

	/*** 处理添加人数价格问题 *****/
	protected void Line_OrderPrice() {
		adult = Integer.valueOf(line_booking_adult_number_txt.getText()
				.toString());// :成人数
		child = Integer.valueOf(line_booking_child_number_txt.getText()
				.toString());// :儿童数
		baby = Integer.valueOf(line_booking_baby_number_txt.getText()
				.toString());
		booking_adult_totalprice = adult * adultPrice;
		booking_child_totalprice = child * childPrice;
		booking_baby_totalprice = baby * babyPrice;
		booking_singleRoom_totalprice = singleRoom * (adult + child);
		booking_order_totalprice = booking_adult_totalprice
				+ booking_child_totalprice + booking_baby_totalprice
				+ booking_singleRoom_totalprice;

		booking_adult_price.setText(adultPrice + "");
		booking_adult_number.setText(adult + "");
		booking_adult_totalprice_txt.setText(booking_adult_totalprice + "");

		booking_child_price.setText(childPrice + "");
		booking_child_number.setText(child + "");
		booking_child_totalprice_txt.setText(booking_child_totalprice + "");
		/*** 费用明细 婴儿价格、数量、总额 ***/
		booking_baby_price.setText(babyPrice + "");
		booking_baby_number.setText(baby + "");
		booking_baby_totalprice_txt.setText(booking_baby_totalprice + "");
		/*** 费用明细 单房差价格、数量、总额 ***/
		booking_singleRoom_price.setText(singleRoom + "");
		booking_singleRoom_number.setText(adult + child + "");
		booking_singleRoom_totalprice_txt.setText(booking_singleRoom_totalprice
				+ "");
		/*** 订单总额组件 ***/
		line_booking_zongprice_txt.setText("￥" + booking_order_totalprice + "");
		booking_order_totalprice_txt.setText("￥" + booking_order_totalprice
				+ "");

	}

	/*********** 封装预订人信息 *****************/
	private String String_BookingPesonJson(String name, String mobile,
			String tel, String email, String detail, String address) {
		JSONObject operationss = new JSONObject();
		try {
			operationss.put("name", name);
			operationss.put("mobile", mobile);
			operationss.put("tel", tel);
			operationss.put("email", email);
			operationss.put("detail", "detail");
			operationss.put("address", address);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return operationss.toString();
	}

	/*********** 封装预定价格信息 *****************/
	private String String_BookingPriceJson(long singleRoom2,
			long adultPriceMarket2, long adultPrice2, long childPriceMarket2,
			long childPrice2, long babyPriceMarket2, long babyPrice2) {
		JSONObject js = new JSONObject();
		try {
			js.put("singleRoom", singleRoom2);
			js.put("adultPriceMarket", adultPriceMarket2);
			js.put("adultPrice", adultPrice2);
			js.put("childPriceMarket", childPriceMarket2);
			js.put("childPrice", childPrice2);
			js.put("babyPriceMarket", babyPriceMarket2);
			js.put("babyPrice", babyPrice2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return js.toString();
	}
	private void closeDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}
	/**
	 * 验证手机号码格式
	 */
	public static boolean isMobileNOs(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）170 177
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][345870]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
}
