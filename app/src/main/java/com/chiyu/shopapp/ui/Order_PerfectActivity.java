package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.My_PersonAdapter;
import com.chiyu.shopapp.bean.FeilName;
import com.chiyu.shopapp.bean.My_Person;
import com.chiyu.shopapp.bean.OrderEntity;
import com.chiyu.shopapp.bean.Order_PayinfoEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ListViewRun;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * 订单详情页面
 */
public class Order_PerfectActivity extends Activity {
	private static final String TAG = "tripshop";
	private static final int CLIENTSNAME = 1;
	private TextView main_title, main_ivTitleBtnLeft;
	/******* 游客名单列表 ***************/
	private ListViewRun gridview;
	/******* 订单基础信息组件 **********/
	private TextView lineyuding_biaoti_tx, order_contact_name_txt,
			order_contact_mobile_txt, order_contact_gotime_txt,
			order_contact_adult_txt, order_contact_child_txt,
			order_contact_baby_txt;
	/*********** 提交按钮、 *************/
	private LinearLayout lineDetails_yuding_layout;

	public List<My_Person> listData = new ArrayList<My_Person>();
	private My_PersonAdapter Orderadapter;
	private OrderContactsReceiver receiver = new OrderContactsReceiver();
	private ScrollView scrollView;
	private int total = 5;
	private String tag;
	private int positions;
	/********* 游客参数 **************/
	private String orderId, orderId1;// 订单id
	private String guests;// 游客信息
	/************* 预订人的参数 ***************/
	private String mobile;
	private String createDate;
	private String tel;
	private String email;
	private String address;
	private String name;
	private String id;
	private String detail;
	private String operations;
	private SendCityBroadCast sCast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_perfectinfo);
		MyApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		orderId1 = orderId;
		RegisterBroadcast();
		IntentFilter fliFilter = new IntentFilter();
		fliFilter.addAction("mingdanhuichuan");
		registerReceiver(receiver, fliFilter);
		initView();
		initListener();
		String_GetLineDetails(orderId);

	}
	private void RegisterBroadcast() {
		IntentFilter filter1 = new IntentFilter();
		filter1.addAction("com.neter.broadcast.receiver.SendDownXMLBroadCast");
		sCast = new SendCityBroadCast();
		registerReceiver(sCast, filter1);
	}

	public class SendCityBroadCast extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() == "com.neter.broadcast.receiver.SendDownXMLBroadCast") {
				System.out.println("接受到广播");
			}
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		main_title.setText("完善游客信息");
		lineDetails_yuding_layout = (LinearLayout) findViewById(R.id.lineDetails_yuding_layout);
		lineyuding_biaoti_tx = (TextView) findViewById(R.id.lineyuding_biaoti_tx);
		order_contact_name_txt = (TextView) findViewById(R.id.order_contact_name_txt);
		order_contact_mobile_txt = (TextView) findViewById(R.id.order_contact_mobile_txt);
		order_contact_gotime_txt = (TextView) findViewById(R.id.order_contact_gotime_txt);
		order_contact_adult_txt = (TextView) findViewById(R.id.order_contact_adult_txt);
		order_contact_child_txt = (TextView) findViewById(R.id.order_contact_child_txt);
		order_contact_baby_txt = (TextView) findViewById(R.id.order_contact_baby_txt);
		gridview = (ListViewRun) findViewById(R.id.gridview);
		scrollView = (ScrollView) findViewById(R.id.scroll_view);
		if (listData != null) {
			Orderadapter = new My_PersonAdapter(this,
					Order_PerfectActivity.this);
			gridview.setAdapter(Orderadapter);
			gridview.setMaxHeight(total * 250);
			scrollView.setFocusable(true);
			gridview.setParentScrollView(scrollView);
			setListViewHeightBased(gridview);
		}
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				positions = position;
				final My_Person person = listData.get(positions);
				String personid = listData.get(positions).getId();
				int category = listData.get(positions).getCategory();
				Intent intent = new Intent(Order_PerfectActivity.this,
						SendFielActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putString("tag", personid);
				mBundle.putInt("category", category);
				mBundle.putString("Fromtag", "Order_PerfectActivity");
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		});

	}

	/*********** 接收客户名单通知 ********************/
	class OrderContactsReceiver extends BroadcastReceiver {
		private ArrayList<FeilName> personlist = new ArrayList<FeilName>();

		@Override
		public void onReceive(Context context, Intent intent) {
			tag = intent.getStringExtra("tag");
			Bundle bundle = intent.getBundleExtra("path");
			ArrayList<FeilName> list = bundle.getParcelableArrayList("path");
			for (int i = 0; i < list.size(); i++) {
				FeilName fileName = list.get(i);
				String name = fileName.getName();
				String idCard = fileName.getMobile();
				String category = fileName.getCategory();
				String date = fileName.getCreateDate();
				My_Person person = new My_Person();
				person.setName(name);
				person.setMobile(idCard);
				person.setCategory(Integer.parseInt(category));
				person.setId(tag);
				listData.remove(positions);
				listData.add(positions, person);
				Orderadapter.setData(listData);

			}
		}

		public List<FeilName> getDatas() {
			return personlist;

		}

	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		lineDetails_yuding_layout.setOnClickListener(new viewClickListener());

	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();
			} else if (id == R.id.lineDetails_yuding_layout) {
				guests = ParseUtils.converPersonToJson(listData);
				operations = BookingPesonJson(name, mobile, tel, email, detail,
						address);
				String_Order_UpdateOrderStatus(orderId, guests, operations);
			}
		}
	}

	/************ 根据线路orderId获取订单基础信息 *************/
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
					lineyuding_biaoti_tx.setText(entity.getTitle());
					order_contact_name_txt.setText(entity.getName());
					order_contact_mobile_txt.setText(entity.getMobile());
					order_contact_gotime_txt.setText(entity.getGoTime());
					order_contact_adult_txt.setText(entity.getAdult() + "成人");
					order_contact_child_txt.setText(entity.getChild() + "儿童");
					order_contact_baby_txt.setText(entity.getBaby() + "婴儿");
					mobile = entity.getMobile();
					createDate = entity.getCreateDate();
					tel = entity.getTel();
					email = entity.getEmail();
					address = entity.getAddress();
					name = entity.getName();
					id = entity.getId();
					mLogList.add(entity);

				}
				Clientsname(response);
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				Log.i(TAG, "====没有拿到验证====" + error.getMessage());
			}
		});
	}

	/************* 更新订单信息 ****************/
	private void String_Order_UpdateOrderStatus(String orderId, String guests,
			String operations) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		map.put("guests", guests);
		map.put("operations", operations);
		VolleyUtils.requestString_Post((HashMap<String, String>) map, URL.DEBUG
				+ URL.ORDER_ADDGUES_INFO, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				Order_PayinfoEntity entityOrder_PayinfoEntity = ParseUtils
						.getOrder_UpdateInfo(response.toString());
				int code = entityOrder_PayinfoEntity.getCode();
				String message = entityOrder_PayinfoEntity.getMessage();
				if (code == 200) {
					Intent intent = new Intent();
					intent.setAction("com.neter.broadcast.receiver.Order_PerfectActivity");// 发出自定义广播
					intent.putExtra("orderId", orderId1);
					sendBroadcast(intent);
					Toast.makeText(Order_PerfectActivity.this, message,
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(Order_PerfectActivity.this, message,
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

					if (msg.obj != null) {
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
				}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 封装预订人信息
	 * 
	 * @param name
	 * @param mobile
	 * @param tel
	 * @param email
	 * @param detail
	 * @param address
	 * @return
	 */
	private static String BookingPesonJson(String name, String mobile,
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

	/************ 处理游客名单显示list高度 ******************/
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
		unregisterReceiver(receiver);
		unregisterReceiver(sCast);
		
	}

}
