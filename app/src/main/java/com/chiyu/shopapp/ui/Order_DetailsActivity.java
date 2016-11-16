package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.Order_PersonAdapter;
import com.chiyu.shopapp.bean.My_Person;
import com.chiyu.shopapp.bean.OrderEntity;
import com.chiyu.shopapp.bean.Order_PayinfoEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ListViewRun;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * 订单详情页面
 */
public class Order_DetailsActivity extends Activity {
	private static final String TAG = "tripshop";
	private static final int WHAT_DTD_SUCCESS = 0;
	private static final int CLIENTSNAME = 1;
	private TextView main_title, main_ivTitleBtnLeft;
	private ImageButton order_state_Imbt;
	/******* 订单基础信息组件 **********/
	private TextView order_zhifu_txt, order_state_txt, order_quxiao_txt,
			line_ordercode_txt, lineyuding_biaoti_tx, order_contact_name_txt,
			order_contact_mobile_txt, order_contact_adult_txt,
			order_contact_child_txt, order_contact_baby_txt;
	/*********** 提交按钮、费用明细组件 *************/
	private LinearLayout lineDetails_yuding_layout, line_feiyong_layout,
			order_shenqingtuikuan_layout, order_perfect_layout;
	private LinearLayout order_state_layout;
	/**************** 联系方式、私信 *********************/
	private LinearLayout line_lianxi_layout, line_sixin_layout;
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
	private ScrollView scrollView;
	/******** 游客名单列表 *********/
	private ListViewRun gridview;
	/*********** 0 自发产品；1代销产品（b2b采购的产品） *****************/
	private int lineCategory;
	/********* 支付参数 **************/
	private String orderId;// 订单id
	private String orderno;// 订单编号
	private double orderamount;// 订单价格
	private String companyid = "";// 公司id
	private String memberid = "";// 用户id（购买人）
	private String title;
	private int order_status, pay_status;
	private String mark = "0";
	private String detail = "";
	private String operationName = "tt";// 操作人
	public List<My_Person> listData = new ArrayList<My_Person>();
	private int total = 5;
	private Order_PersonAdapter Orderadapter;
	private int isPay;
	private SendCityBroadCast sCast;
	private double totalPrice;// 订单总金额
	private double singleRoomAmount;// 单房差总金额
	private Double b2bAmount;// b2b总金额
	private Double couponAmount;
	private double orderPrice;
	private TextView couponAmount_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_details);
		MyApp.getInstance().addActivity(this);
		memberid = ShareUtil.getString("userId");
		companyid = ShareUtil.getString("companyId");// :公司ID
		RegisterBroadcast();
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		initView();
		initListener();
		String_GetLineDetails(orderId);
	}

	private void RegisterBroadcast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.neter.broadcast.receiver.Order_PerfectActivity");
		sCast = new SendCityBroadCast();
		registerReceiver(sCast, filter);
	}

	public class SendCityBroadCast extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() == "com.neter.broadcast.receiver.Order_PerfectActivity") {
				String orderId = intent.getStringExtra("orderId");
				String_GetLineDetails(orderId);
			}
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		main_title.setText("订单详情");
		lineDetails_yuding_layout = (LinearLayout) findViewById(R.id.line_zhifu_layout);
		line_feiyong_layout = (LinearLayout) findViewById(R.id.line_feiyong_layout);
		order_shenqingtuikuan_layout = (LinearLayout) findViewById(R.id.order_shenqingtuikuan_layout);
		order_perfect_layout = (LinearLayout) findViewById(R.id.order_perfect_layout);
		/****** 订单基础信息组件 **********/
		line_ordercode_txt = (TextView) findViewById(R.id.line_ordercode_txt);
		lineyuding_biaoti_tx = (TextView) findViewById(R.id.lineyuding_biaoti_tx);
		order_contact_name_txt = (TextView) findViewById(R.id.order_contact_name_txt);
		order_contact_mobile_txt = (TextView) findViewById(R.id.order_contact_mobile_txt);
		// order_contact_gotime_txt = (TextView)
		// findViewById(R.id.order_contact_gotime_txt);
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
		order_zhifu_txt = (TextView) findViewById(R.id.order_zhifu_txt);
		order_state_txt = (TextView) findViewById(R.id.order_state_txt);
		order_state_layout = (LinearLayout) findViewById(R.id.order_state_layout);
		order_state_Imbt = (ImageButton) findViewById(R.id.order_state_Imbt);
		order_quxiao_txt = (TextView) findViewById(R.id.order_quxiao_txt);
		/******* 联系方式、私信 ***************/
		line_lianxi_layout = (LinearLayout) findViewById(R.id.line_lianxi_layout);
		line_sixin_layout = (LinearLayout) findViewById(R.id.line_sixin_layout);
		gridview = (ListViewRun) findViewById(R.id.gridview);
		scrollView = (ScrollView) findViewById(R.id.scroll_view);
		couponAmount_txt = (TextView) findViewById(R.id.couponamount_txt);
		if (listData != null) {
			Orderadapter = new Order_PersonAdapter(this,
					Order_DetailsActivity.this);
			gridview.setAdapter(Orderadapter);
			gridview.setMaxHeight(total * 250);
			scrollView.setFocusable(true);
			gridview.setParentScrollView(scrollView);
			setListViewHeightBased(gridview);
		}
	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		lineDetails_yuding_layout.setOnClickListener(new viewClickListener());
		order_quxiao_txt.setOnClickListener(new viewClickListener());
		order_perfect_layout.setOnClickListener(new viewClickListener());
		line_sixin_layout.setOnClickListener(new viewClickListener());
		line_lianxi_layout.setOnClickListener(new viewClickListener());
		order_shenqingtuikuan_layout
				.setOnClickListener(new viewClickListener());
		line_feiyong_layout.setVisibility(View.VISIBLE);
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();

			} else if (id == R.id.line_zhifu_layout) {
				if (lineCategory == 0) {
					if (isPay == 1) {
						Intent intent = new Intent(Order_DetailsActivity.this,
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
						intent.putExtra("b2bAmount", b2bAmount);
						intent.putExtra("couponAmount", couponAmount);
						intent.putExtra("orderPrice", orderPrice);
						startActivityForResult(intent, WHAT_DTD_SUCCESS);
					} else {
						if (order_status == 3) {
							Intent intent = new Intent(
									Order_DetailsActivity.this,
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
							intent.putExtra("singleRoomAmount",
									singleRoomAmount);
							intent.putExtra("b2bAmount", b2bAmount);
							intent.putExtra("couponAmount", couponAmount);
							intent.putExtra("orderPrice", orderPrice);
							startActivityForResult(intent, WHAT_DTD_SUCCESS);
						} else {
							Toast.makeText(
									Order_DetailsActivity.this,
									"*该笔订单需要店主手动确认，确认后，您可立即在线支付，或者您也可以拨打电话进行咨询",
									Toast.LENGTH_SHORT).show();
						}

					}

				} else {
					if (order_status == 8) {
						Intent intent = new Intent(Order_DetailsActivity.this,
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
						intent.putExtra("b2bAmount", b2bAmount);
						intent.putExtra("couponAmount", couponAmount);
						intent.putExtra("orderPrice", orderPrice);
						startActivityForResult(intent, WHAT_DTD_SUCCESS);
					} else {

						Toast.makeText(Order_DetailsActivity.this,
								"*该笔订单需要店主手动确认，确认后，您可立即在线支付，或者您也可以拨打电话进行咨询",
								Toast.LENGTH_SHORT).show();
					}
				}

			} else if (id == R.id.order_perfect_layout) {
				Intent intent = new Intent(Order_DetailsActivity.this,
						Order_PerfectActivity.class);
				intent.putExtra("orderId", orderId);
				startActivityForResult(intent, WHAT_DTD_SUCCESS);

			} else if (id == R.id.order_quxiao_txt) {
				order_status = 5;
				String_Order_UpdateOrderStatus(mark, order_status, detail,
						operationName);
			} else if (id == R.id.line_lianxi_layout) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ ShareUtil.getString("mobile")));
				startActivity(intent);
			} else if (id == R.id.line_sixin_layout) {
				 if(!EMClient.getInstance().isConnected() || !DemoHelper.getInstance().isLoggedIn()){
					 EMLogserver(ShareUtil.getString("huanxinUserName"),ShareUtil.getString("huanxinpwd"));
					}else{
						Intent intent = new Intent(Order_DetailsActivity.this,
								ChatActivity.class);
						intent.putExtra("userId", ShareUtil.getString("username"));
						startActivity(intent);
					}
				
			} else if (id == R.id.order_shenqingtuikuan_layout) {
				Intent intent = new Intent(Order_DetailsActivity.this,
						Order_TuiKuanActivity.class);
				intent.putExtra("orderId", orderId);
				startActivityForResult(intent, WHAT_DTD_SUCCESS);
			}
		}
	}
	//环信登录
	public void EMLogserver(String currentUsername, String currentPassword) {
		currentUsername = ShareUtil.getString("huanxinUserName");
		currentPassword = ShareUtil.getString("huanxinpwd");
		DemoHelper.getInstance().setCurrentUserName(currentUsername);
		EMClient.getInstance().login(currentUsername, currentPassword,
				new EMCallBack() {

					@Override
					public void onSuccess() {
						Log.d(TAG, "login: onSuccess");
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
						Intent intent = new Intent(Order_DetailsActivity.this,
								ChatActivity.class);
						intent.putExtra("userId", ShareUtil.getString("username"));
						startActivity(intent);
					}

					@Override
					public void onProgress(int progress, String status) {
						Log.d(TAG, "login: onProgress");
					}

					@Override
					public void onError(final int code, final String message) {
						Log.d(TAG, "login: onError: " + code);
						runOnUiThread(new Runnable() {
							public void run() {
							}
						});
					}
				});
	}
	/************ 根据线路lineId和团旗dateId获取线路详情数据 *************/
	private void String_GetLineDetails(String orderId) {
		VolleyUtils.requestString_Get(URL.DEBUG + URL.GETORDER_GETORDERINFO
				+ orderId, new OnRequest() {

			@Override
			public void response(String url, String response) {
				ArrayList<OrderEntity> loadDataList = null;
				ArrayList<OrderEntity> mLogList = new ArrayList<OrderEntity>();
				loadDataList = ParseUtils.getOrderInfo(response.toString());
				for (OrderEntity entity : loadDataList) {
					line_ordercode_txt.setText(entity.getOrderCode());
					String chanpintxt;
					try {
						if (entity.getTitle().length() >= 36) {
							chanpintxt = ShareUtil.SubString(entity.getTitle()
									.toString(), 0, 34)
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
					// order_contact_gotime_txt.setText(entity.getGoTime());
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
					booking_singleRoom_price.setText(entity.getSingleRoom()
							+ "");
					booking_singleRoom_number.setText(entity.getAdult()
							+ entity.getChild() + "");
					booking_singleRoom_totalprice_txt.setText(entity
							.getSingleRoomAmount() + "");
					/*** 订单总额组件 ***/
					booking_order_totalprice_txt.setText("￥"
							+ entity.getTotalPrice());
					/******* 0 自发产品；1代销产品（b2b采购的产品） ***********/
					lineCategory = entity.getLineCategory();
					totalPrice = entity.getTotalPrice();// 订单总金额
					singleRoomAmount = entity.getSingleRoomAmount();// 单房差总金额
					b2bAmount = entity.getB2bAmount();// b2b总金额
					orderno = entity.getOrderCode();// 订单编号
					orderamount = entity.getTotalPrice();
					title = entity.getTitle();
					order_status = entity.getOrderStatus();
					pay_status = entity.getPayStatus();
					isPay = entity.getIsPay();
					couponAmount = entity.getCouponAmount();
					orderPrice = entity.getOrderPrice();
					if (couponAmount != 0) {

						couponAmount_txt.setText("("+"红包优惠:" +"￥"+ couponAmount+")");
						couponAmount_txt.setVisibility(View.VISIBLE);
					}
					if (pay_status == 0) {
						order_zhifu_txt.setText("未支付");
					} else if (pay_status == 1) {
						order_zhifu_txt.setText("已支付");
						order_quxiao_txt.setVisibility(View.GONE);
						lineDetails_yuding_layout.setVisibility(View.GONE);
						order_shenqingtuikuan_layout
								.setVisibility(View.VISIBLE);
					}
					if (order_status == 4) {
						order_zhifu_txt.setText("已申请退款");
						order_state_layout.setVisibility(View.GONE);
						order_state_Imbt.setVisibility(View.GONE);
						order_quxiao_txt.setVisibility(View.GONE);
						lineDetails_yuding_layout.setVisibility(View.GONE);
					} else if (order_status == 5) {
						order_zhifu_txt.setText("已取消");
						order_state_layout.setVisibility(View.GONE);
						order_state_Imbt.setVisibility(View.GONE);
						order_quxiao_txt.setVisibility(View.GONE);
						lineDetails_yuding_layout.setVisibility(View.GONE);
					} else if (lineCategory == 0 && order_status == 8) {
						order_state_txt.setText("店主已确认,可立即支付!");
						order_state_layout.setVisibility(View.GONE);
						order_state_Imbt.setVisibility(View.GONE);
					} else {
						order_state_layout.setVisibility(View.GONE);
						order_state_Imbt.setVisibility(View.GONE);
					}
					if (lineCategory == 1 && order_status == 7) {
						order_state_layout.setVisibility(View.VISIBLE);
						order_state_Imbt.setVisibility(View.VISIBLE);
						order_state_txt
								.setText("*该笔订单需要店主手动确认，确认后，您可立即在线支付，或者您也可以拨打电话进行咨询");
					}
					Clientsname(response);
					mLogList.add(entity);
				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
			}
		});
	}

	/************* 更新订单信息 ****************/
	private void String_Order_UpdateOrderStatus(String mark, int order_status,
			String detail, String operationName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		map.put("mark", mark);
		map.put("status", order_status + "");
		map.put("detail", detail);
		map.put("operationName", operationName);
		VolleyUtils.requestString_Post((HashMap<String, String>) map, URL.DEBUG
				+ URL.ORDER_UPDATERSTATUS, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				Order_PayinfoEntity entityOrder_PayinfoEntity = ParseUtils
						.getOrder_UpdateInfo(response.toString());
				int code = entityOrder_PayinfoEntity.getCode();
				String message = entityOrder_PayinfoEntity.getMessage();
				if (code == 200) {
					String_GetLineDetails(orderId);
					Toast.makeText(Order_DetailsActivity.this, message,
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(Order_DetailsActivity.this, message,
							Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				Log.i(TAG, "修改订单失败" + error.getMessage());
			}
		});
	}

	/************** 客户名单 *****************/
	private void Clientsname(String response) {
		ArrayList<My_Person> loadDataList = null;
		ArrayList<My_Person> mLogList = new ArrayList<My_Person>();
		loadDataList = ParseUtils
				.parseOrder_PersonListInfo(response.toString());
		if (loadDataList != null && loadDataList.size() > 0) {
			Message msg = mUIHandler.obtainMessage(CLIENTSNAME);
			msg.obj = loadDataList;
			msg.sendToTarget();
		} else {
			mUIHandler.sendEmptyMessage(4);
		}
	}

	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CLIENTSNAME:

				if (msg.obj != null) {
					if (listData != null && listData.size() > 0) {
						listData.removeAll(listData);
					}
					List<My_Person> orderlogLoad = (ArrayList<My_Person>) msg.obj;
					if (orderlogLoad != null && orderlogLoad.size() > 0) {
						for (My_Person person : orderlogLoad) {
							person.setIschoose("3");
						}
						if (orderlogLoad.size() > 0) {
						}
						listData.addAll(orderlogLoad);
						Orderadapter.setData(listData);
					}
				}
				break;
			default:
				break;
			}
		}
	};

	private void setListViewHeightBased(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(sCast);
	}
}
