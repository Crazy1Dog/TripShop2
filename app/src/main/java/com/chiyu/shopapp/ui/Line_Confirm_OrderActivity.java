package com.chiyu.shopapp.ui;

import java.util.ArrayList;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.Line_DetailsEntity;
import com.chiyu.shopapp.bean.OrderEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 确认订单页面
 */
@SuppressWarnings("unused")
public class Line_Confirm_OrderActivity extends Activity {
	private static final String TAG = "tripshop";
	private static final int WHAT_DTD_SUCCESS = 0;
	private TextView main_title,main_ivTitleBtnLeft;
	/******* 订单基础信息组件 **********/
	private TextView line_ordercode_txt, lineyuding_biaoti_tx,
			order_contact_name_txt, order_contact_mobile_txt,
			order_contact_gotime_txt, order_contact_adult_txt,
			order_contact_child_txt, order_contact_baby_txt;
	/*********** 提交按钮、费用明细组件 *************/
	private LinearLayout lineDetails_yuding_layout, line_feiyong_layout;
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
	private TextView booking_order_totalprice_txt;
	/*********** 0 自发产品；1代销产品（b2b采购的产品） *****************/
	private int lineCategory;
	private String b2bOrderId;// b2b订单id
	/********* 支付参数 **************/
	private String orderId;// 订单id
	private String orderno;// 订单编号
	private Double orderamount;// 订单价格
	private String companyid = "";// 公司id
	private String memberid = "";// 用户id（购买人）
//	private String companyid = "34556";// 公司id
//	private String memberid = "1000015";// 用户id（购买人）
	private String title;
	private double totalPrice;// 订单总金额
	private double singleRoomAmount;// 单房差总金额
	private double b2bAmount;// b2b总金额
	private int isPay;// :是否实时支付 0 否；1 是
	private int order_status;
	private Double orderPrice;//订单金额（不包含单房差）
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.line_confirm_order);
		MyApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		b2bOrderId = intent.getStringExtra("b2bOrderId");
		orderId = intent.getStringExtra("orderId");
		companyid = ShareUtil.getString("companyId");// :公司ID
		memberid = ShareUtil.getString("userId");
		
		initView();
		initListener();
		String_GetLineDetails(orderId);
	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		main_title.setText("确认订单");
		lineDetails_yuding_layout = (LinearLayout) findViewById(R.id.lineDetails_yuding_layout);
		line_feiyong_layout = (LinearLayout) findViewById(R.id.line_feiyong_layout);
		/****** 订单基础信息组件 **********/
		line_ordercode_txt = (TextView) findViewById(R.id.line_ordercode_txt);
		lineyuding_biaoti_tx = (TextView) findViewById(R.id.lineyuding_biaoti_tx);
		order_contact_name_txt = (TextView) findViewById(R.id.order_contact_name_txt);
		order_contact_mobile_txt = (TextView) findViewById(R.id.order_contact_mobile_txt);
		order_contact_gotime_txt = (TextView) findViewById(R.id.order_contact_gotime_txt);
		order_contact_adult_txt = (TextView) findViewById(R.id.order_contact_adult_txt);
		order_contact_child_txt = (TextView) findViewById(R.id.order_contact_child_txt);
		order_contact_baby_txt = (TextView) findViewById(R.id.order_contact_baby_txt);
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
		booking_order_totalprice_txt = (TextView) findViewById(R.id.booking_order_totalprice);
	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		lineDetails_yuding_layout.setOnClickListener(new viewClickListener());
		line_feiyong_layout.setVisibility(View.VISIBLE);
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();

			} else if (id == R.id.lineDetails_yuding_layout) {
				if (lineCategory == 0) {
					if(isPay ==1){
						Intent intent = new Intent(Line_Confirm_OrderActivity.this,
								Order_PayinfoActivity.class);
						intent.putExtra("orderId", orderId);
						intent.putExtra("orderno", orderno);
						intent.putExtra("orderamount", orderamount);
						intent.putExtra("companyid", companyid);
						intent.putExtra("memberid", memberid);
						intent.putExtra("title", title);
						intent.putExtra("order_status", order_status);
						intent.putExtra("lineCategory", lineCategory);
						intent.putExtra("totalPrice", totalPrice);
						intent.putExtra("singleRoomAmount", singleRoomAmount);
						intent.putExtra("orderPrice", orderPrice);
						intent.putExtra("b2bAmount", b2bAmount);
						
						startActivityForResult(intent, WHAT_DTD_SUCCESS);
					}else{
						Intent intent = new Intent(Line_Confirm_OrderActivity.this,
								Order_Activity.class);
						startActivityForResult(intent, WHAT_DTD_SUCCESS);
						finish();
					}
				} else {
					Intent intent = new Intent(Line_Confirm_OrderActivity.this,
							Order_Activity.class);
					startActivityForResult(intent, WHAT_DTD_SUCCESS);
					finish();
				}
				
			}
		}
	}

	/************ 根据线路lineId和团旗dateId获取线路详情数据 *************/
	private void String_GetLineDetails(String orderId) {
		VolleyUtils.requestString_Get(URL.DEBUG + URL.GETORDER_GETORDERINFO
				+ orderId, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				ArrayList<OrderEntity> loadDataList = null;
				ArrayList<OrderEntity> mLogList = new ArrayList<OrderEntity>();
				loadDataList = ParseUtils.getOrderInfo(response.toString());
				for (OrderEntity entity : loadDataList) {
					line_ordercode_txt.setText(entity.getOrderCode());
					String chanpintxt;
					try {
						if (entity.getTitle().length() >= 38) {
							chanpintxt = ShareUtil.SubString(
									entity.getTitle().toString(), 0, 36)
									+ "...";
						} else {
							chanpintxt = entity.getTitle();
						}

						lineyuding_biaoti_tx.setText(chanpintxt);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					order_contact_name_txt.setText(entity.getName());
					order_contact_mobile_txt.setText(entity.getMobile());
					order_contact_gotime_txt.setText(entity.getGoTime());
					order_contact_adult_txt.setText(entity.getAdult() + "成人");
					order_contact_child_txt.setText(entity.getChild() + "儿童");
					order_contact_baby_txt.setText(entity.getBaby() + "婴儿");
					/*** 费用明细 成人价格、数量、总额 ***/
					booking_adult_price.setText(entity.getAdultPrice() + "");
					booking_adult_number.setText(entity.getAdult() + "");
					booking_adult_totalprice_txt.setText(entity.getAdult()
							* entity.getAdultPrice() + "");
					/*** 费用明细 儿童价格、数量、总额 ***/
					booking_child_price.setText(entity.getChildPrice() + "");
					booking_child_number.setText(entity.getChild() + "");
					booking_child_totalprice_txt.setText(entity.getChildPrice()
							* entity.getChild() + "");
					/*** 费用明细 婴儿价格、数量、总额 ***/
					booking_baby_price.setText(entity.getBabyPrice() + "");
					booking_baby_number.setText(entity.getBaby() + "");
					booking_baby_totalprice_txt.setText(entity.getBaby()
							* entity.getBabyPrice() + "");
					/*** 费用明细 单房差价格、数量、总额 ***/
					booking_singleRoom_price.setText(entity.getSingleRoom() + "");
					booking_singleRoom_number.setText(entity.getAdult()
							+ entity.getChild() + "");
					booking_singleRoom_totalprice_txt.setText(entity
							.getSingleRoomAmount() + "");
					/*** 订单总额组件 ***/
					booking_order_totalprice_txt.setText("￥"+entity.getTotalPrice()
							+ "");
					/******* 0 自发产品；1代销产品（b2b采购的产品） ***********/
					lineCategory = entity.getLineCategory();
					orderno = entity.getOrderCode();// 订单编号
					orderamount = entity.getTotalPrice();
					title = entity.getTitle();
					isPay = entity.getIsPay();
					totalPrice = entity.getTotalPrice();// 订单总金额
					singleRoomAmount = entity.getSingleRoomAmount();// 单房差总金额
					orderPrice = entity.getOrderPrice();
					b2bAmount = entity.getB2bAmount();// b2b总金额
					order_status = entity.getOrderStatus();
					mLogList.add(entity);
				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				Log.i(TAG, "====没有拿到验证====" + error.getMessage());
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
