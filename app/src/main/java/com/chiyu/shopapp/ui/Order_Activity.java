package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.Order_ListAdapter;
import com.chiyu.shopapp.bean.OrderEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.PullToRefreshView;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.PullToRefreshView.OnFooterRefreshListener;
import com.chiyu.shopapp.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

public class Order_Activity extends Activity implements
		OnHeaderRefreshListener, OnFooterRefreshListener {
	// ** Handler What加载数据完毕 **//
	private static final int WHAT_DID_LOAD_DATA = 0;
	// ** Handler What更新数据完毕 **//
	private static final int WHAT_DID_REFRESH = 1;
	private static final int camera_upload_imageRequestCode = 2;
	private static final int WHAT_LINE_LOAD_DATA = 3;
	private GridView gridview;
	private Dialog mDialog;
	private TextView main_title, main_ivTitleBtnLeft;
	private PullToRefreshView mPullToRefreshView;
	private LinearLayout linearLayout;
	private Order_ListAdapter adapter;
	private List<OrderEntity> listData = new ArrayList<OrderEntity>();
	private String receiveGuestId = "";
	private String b2cUserId = "";
	private String baseCategory = "";
	private String orderCod = "";
	private String lineCategory = "";
	private String orderStatus = "";
	private String payStatus = "";
	private String createTime = "";
	private String goTime = "";
	private String confirmTime = "";
	private int pageNumber = 1;
	private int pageSize = 10;
	private String params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		MyApp.getInstance().addActivity(this);
		receiveGuestId = ShareUtil.getString("receiveguestId");// :微店ID
		b2cUserId = ShareUtil.getString("userId");
		initView();
		initListener();
		params = String_Order_ParamsJson(receiveGuestId, b2cUserId,
				baseCategory, orderCod, lineCategory, orderStatus, payStatus,
				createTime, goTime, confirmTime);
		String_PostSaveOrder(params, pageNumber, pageSize);
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		linearLayout = (LinearLayout) findViewById(R.id.dialog_viewLinearLayout);
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new Order_ListAdapter(this);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridview.setAdapter(adapter);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int positions = arg2;
				final OrderEntity orderEntity = listData.get(positions);
				Intent intent = new Intent(Order_Activity.this,
						Order_DetailsActivity.class);
				String orderId = orderEntity.getId();
				intent.putExtra("orderId", orderId);
				startActivity(intent);
			}
		});

	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		main_title.setText("我的订单");
	

	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());

	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();

			}
		}
	}

	@Override
	public void onDestroy() {
		gridview.setAdapter(null);
		super.onDestroy();
		closeDialog();
	}

	private void closeDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		pageNumber++;
		String_PostSaveOrder_FooterRefresh(params, pageNumber, pageSize);
		
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		String_PostSaveOrder_HeaderRefresh(params, pageNumber, pageSize);
	}

	private Handler mUIHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case WHAT_DID_LOAD_DATA: {
				closeDialog();
				if (msg.obj != null) {
					List<OrderEntity> logLoad = (ArrayList<OrderEntity>) msg.obj;
					if (logLoad != null && logLoad.size() > 0) {
						linearLayout.setVisibility(View.GONE);
						gridview.setVisibility(View.VISIBLE);
						listData.addAll(logLoad);
						adapter.setData(listData);
					}
					mPullToRefreshView.onFooterRefreshComplete();

				}
				break;
			}
			case WHAT_LINE_LOAD_DATA: {
				closeDialog();
				if (msg.obj != null) {
					List<OrderEntity> logLoad = (ArrayList<OrderEntity>) msg.obj;
					if (logLoad != null && logLoad.size() > 0) {
						linearLayout.setVisibility(View.GONE);
						gridview.setVisibility(View.VISIBLE);
						listData.addAll(logLoad);
						adapter.setData(listData);
					} else {
						listData.removeAll(listData);
						adapter.setData(listData);
						Toast.makeText(getApplicationContext(), "暂无数据!",
								Toast.LENGTH_SHORT).show();
					}
					mPullToRefreshView.onFooterRefreshComplete();

				}
				break;
			}
			case WHAT_DID_REFRESH: {// 刷新
				closeDialog();
				List<OrderEntity> logLoad = (ArrayList<OrderEntity>) msg.obj;
				if (logLoad != null && logLoad.size() > 0) {
					linearLayout.setVisibility(View.GONE);
					gridview.setVisibility(View.VISIBLE);
					listData.addAll(logLoad);
					adapter.setData(logLoad);
				}
				mPullToRefreshView.onHeaderRefreshComplete();

			}
				break;
			case 4: {
				closeDialog();
				linearLayout.setVisibility(View.GONE);
				gridview.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(), "暂无数据!",
						Toast.LENGTH_SHORT).show();
				mPullToRefreshView.onFooterRefreshComplete();
				break;
			}

			case 5: {
				closeDialog();
				linearLayout.setVisibility(View.GONE);
				gridview.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(), "数据加载完毕",
						Toast.LENGTH_SHORT).show();
				break;
			}
			}
		}
	};

	/************ 下拉刷新 ********************/
	private void String_PostSaveOrder_HeaderRefresh(String params,
			int pageNumber, int pageSize) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("params", params);
		map.put("pageNumber", pageNumber + "");
		map.put("pageSize", pageSize + "");

		VolleyUtils.requestString_Post((HashMap<String, String>) map, URL.DEBUG
				+ URL.POSTORDERLIST, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				ArrayList<OrderEntity> orderlist = ParseUtils
						.parseOrder_ListInfo(response.toString());
				if (orderlist != null && orderlist.size() > 0) {
					Message msg = mUIHandler.obtainMessage(WHAT_DID_REFRESH);
					msg.obj = orderlist;
					msg.sendToTarget();
				} else {
					mUIHandler.sendEmptyMessage(4);
				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
			}
		});
	}

	/*********** 获取我的订单列表 *****************/

	/****
	 * 
	 * 描述：订单列表 ---------------------------------------- ---------- 提交方式：POST
	 * 接口地址：/api/receive/order/getOrderList/ 参数列表：[ （日期格式：yyyy-MM-dd至yyyy-MM-dd）
	 * params:查询条件 json格式:
	 * {"receiveGuestId":"微店ID（必传）","b2cUserId":"C客Id","baseCategory"
	 * :"基础分类ID","orderCode":"订单编号",
	 * "lineCategory":"线路类型（0自发；1代销）","orderStatus"
	 * :"1 名单不全；2未确认；3已确认；4退款；5取消；6已出票；7供应商未确认；8供应商已确认",
	 * LL"payStatus":"支付状态：0未支付；1已支付；2 支付失败"
	 * ,"createTime":"下单日期","goTime":"出团日期","confirmTime":"确认日期"} pageNumber:页数
	 * pageSize:每页数量 ] ---------------------------------------- ----------
	 * 数据格式：JSON 展现形式：{message:'通知信息',result:数据,code：500|200} 返回参数列表：[ message
	 * ：'通知信息' result ：'数据' code ：状态码 500 失败 200 成功 ]
	 */
	private void String_PostSaveOrder(String params, int pageNumber,
			int pageSize) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("params", params);
		map.put("pageNumber", pageNumber + "");
		map.put("pageSize", pageSize + "");

		VolleyUtils.requestString_Post((HashMap<String, String>) map, URL.DEBUG
				+ URL.POSTORDERLIST, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				ArrayList<OrderEntity> orderlist = ParseUtils
						.parseOrder_ListInfo(response.toString());
				if (orderlist != null && orderlist.size() > 0) {
					Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
					msg.obj = orderlist;
					msg.sendToTarget();
				} else {
					mUIHandler.sendEmptyMessage(4);
				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				// Log.i(TAG, "登录失败" + error.getMessage());
			}
		});
	}

	/************ 上拉刷新加载 ********************/
	private void String_PostSaveOrder_FooterRefresh(String params,
			int pageNumber, int pageSize) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("params", params);
		map.put("pageNumber", pageNumber + "");
		map.put("pageSize", pageSize + "");

		VolleyUtils.requestString_Post((HashMap<String, String>) map, URL.DEBUG
				+ URL.POSTORDERLIST, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				ArrayList<OrderEntity> orderlist = ParseUtils
						.parseOrder_ListInfo(response.toString());
				if (orderlist != null && orderlist.size() > 0) {
					Message msg = mUIHandler.obtainMessage(WHAT_LINE_LOAD_DATA);
					msg.obj = orderlist;
					msg.sendToTarget();
				} else {
					mUIHandler.sendEmptyMessage(4);
				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
			}
		});
	}

	/********* 拼装订单基础信息 *********/
	private String String_Order_ParamsJson(String receiveGuestId,
			String b2cUserId, String baseCategory, String orderCode,
			String lineCategory, String orderStatus, String payStatus,
			String createTime, String goTime, String confirmTime) {
		JSONObject operationss = new JSONObject();
		try {
			operationss.put("receiveGuestId", receiveGuestId);
			operationss.put("b2cUserId", b2cUserId);
			operationss.put("baseCategory", baseCategory);
			operationss.put("orderCode", orderCode);
			operationss.put("lineCategory", lineCategory);
			operationss.put("orderStatus", orderStatus);
			operationss.put("payStatus", payStatus);
			operationss.put("createTime", createTime);
			operationss.put("goTime", goTime);
			operationss.put("confirmTime", confirmTime);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return operationss.toString();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
}