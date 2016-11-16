package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.RedEnvelope_ListAdapter;
import com.chiyu.shopapp.bean.RedEnvelope;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

public class RedEnvelopeActivity extends Activity implements
		OnItemClickListener {
	// ** Handler What加载数据完毕 **//
	private static final int WHAT_DID_LOAD_DATA = 0;
	// ** Handler What更新数据完毕 **//
	private static final int WHAT_DID_REFRESH = 1;
	private static final int WHAT_ORDER_LOAD_DATA = 3;
	private static final int WHAT_ORDER_SEARCH_LOAD_DATA = 5;
	private GridView gridview;
	private RedEnvelope_ListAdapter adapter;
	private List<RedEnvelope> listData = new ArrayList<RedEnvelope>();
	private LinearLayout linearLayout;
	private Dialog mDialog;
	private ImageView red_colseImbt;
	private LinearLayout queding;
	private SendCityBroadCast sCast;
	private String token;// :登录token--必填
	private String userId;// :C客ID--必填
	private String url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.redenvelope_br);
		MyApp.getInstance().addActivity(this);
		token = ShareUtil.getString("token");
		userId = ShareUtil.getString("userId");
		RegisterBroadcast();
		String_GetLineDetails(url, token, userId);
		initLayout();
		initListener();
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

	private void initLayout() {
		red_colseImbt = (ImageView) findViewById(R.id.red_colseimbt);
		linearLayout = (LinearLayout) findViewById(R.id.dialog_viewLinearLayout);
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new RedEnvelope_ListAdapter(this);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridview.setAdapter(adapter);
		queding = (LinearLayout) this.findViewById(R.id.queding);

	}

	private void initListener() {
		queding.setOnClickListener(new viewClickListener());
		red_colseImbt.setOnClickListener(new viewClickListener());
		gridview.setOnItemClickListener(this);
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.queding) {
				ArrayList<RedEnvelope> selectredEnvelopes = new ArrayList<RedEnvelope>();
				if (selectredEnvelopes != null && selectredEnvelopes.size() > 0) {
					selectredEnvelopes.removeAll(selectredEnvelopes);
				}
				for (RedEnvelope redEnvelope : listData) {
					if (redEnvelope.getIschoose().equals("1")) {
						selectredEnvelopes.add(redEnvelope);
					}
				}
				if (selectredEnvelopes != null && selectredEnvelopes.size() > 0) {
					int price = 0;
					for (RedEnvelope redEnvelope : selectredEnvelopes) {
						price += redEnvelope.getAmount();
					}
					String redEnvelope = GetEnvelopeID(selectredEnvelopes);
					Intent intent = new Intent();
					intent.setAction("com.neter.broadcast.receiver.Buyers_Order");// 发出自定义广播
					intent.putExtra("redEnvelope", redEnvelope);
					intent.putExtra("hongprice", price+"");
					System.out.println("redEnvelope=====" + redEnvelope);
					System.out.println("price===---------------==" + price);
					sendBroadcast(intent);
					finish();
				} else {
					Toast.makeText(RedEnvelopeActivity.this, "请选择要抵消的红包",
							Toast.LENGTH_SHORT).show();
				}

			} else if (id == R.id.red_colseimbt) {
				finish();
			}
		}
	}

	/**
	 * 将资源的id拼装成字符串
	 * 
	 * @param studentDTOs
	 */
	private static StringBuilder Id;
	private static String RedEnvelopegetId;

	public static String GetEnvelopeID(List<RedEnvelope> redEnvelopes) {
		Id = new StringBuilder();
		for (RedEnvelope lsEnvelope : redEnvelopes) {
			Id.append(lsEnvelope.getId());
			Id.append(",");
		}
		RedEnvelopegetId = Id.toString();
		return RedEnvelopegetId = RedEnvelopegetId
				.substring(0, Id.length() - 1);
	}

	@Override
	public void onDestroy() {
		gridview.setAdapter(null);
		super.onDestroy();
		unregisterReceiver(sCast);
		closeDialog();
	}

	private void closeDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	private Handler mUIHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			closeDialog();
			switch (msg.what) {
			case WHAT_ORDER_SEARCH_LOAD_DATA:
				if (msg.obj != null) {
					if (listData != null && listData.size() > 0) {
						listData.removeAll(listData);
					}
					List<RedEnvelope> logLoad = (ArrayList<RedEnvelope>) msg.obj;
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
				}

				break;
			case WHAT_DID_LOAD_DATA:
				if (msg.obj != null) {
					List<RedEnvelope> logLoad = (ArrayList<RedEnvelope>) msg.obj;
					for (RedEnvelope redEnvelope : logLoad) {
						redEnvelope.setIschoose("2");
					}
					if (logLoad != null && logLoad.size() > 0) {
						linearLayout.setVisibility(View.GONE);
						gridview.setVisibility(View.VISIBLE);
						listData.addAll(logLoad);
						adapter.setData(listData);
					}

					break;
				}
			case WHAT_ORDER_LOAD_DATA: {
				if (msg.obj != null) {
					if (listData != null && listData.size() > 0) {
						listData.removeAll(listData);
					}

					List<RedEnvelope> logLoad = (ArrayList<RedEnvelope>) msg.obj;
					if (logLoad != null && logLoad.size() > 0) {
						linearLayout.setVisibility(View.GONE);
						gridview.setVisibility(View.VISIBLE);
						listData.addAll(logLoad);
						adapter.setData(listData);
					}

				}
				break;
			}
			case WHAT_DID_REFRESH: // 刷新
				List<RedEnvelope> logLoad = (ArrayList<RedEnvelope>) msg.obj;
				if (logLoad != null && logLoad.size() > 0) {
					linearLayout.setVisibility(View.GONE);
					gridview.setVisibility(View.VISIBLE);
					listData.addAll(logLoad);
					adapter.setData(logLoad);
				}
				break;
			case 4:
				linearLayout.setVisibility(View.GONE);
				gridview.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(), "抱歉你没有红包",
						Toast.LENGTH_SHORT).show();
				break;
			case 6:
				adapter.setData(listData);
				break;

			}
		}

	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					RedEnvelope redEnvelope = listData.get(arg2);
					if (redEnvelope.getIschoose().endsWith("1")) {
						redEnvelope.setIschoose("2");
					} else {
						redEnvelope.setIschoose("1");
					}
					System.out.println("发送消息");
					mUIHandler.sendEmptyMessage(6);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/************ 获取红包列表 *************/
	private void String_GetLineDetails(String url, String token, String userId) {
		System.out.println("hongbao======" + URL.DEBUG + URL.UC_REDPACKETS
				+ "/" + token + "/" + userId);
		VolleyUtils.requestString_Get(URL.DEBUG + URL.UC_REDPACKETS + "/"
				+ token + "/" + userId, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				ArrayList<RedEnvelope> loadDataList = null;
				ArrayList<RedEnvelope> mLogList = new ArrayList<RedEnvelope>();
				loadDataList = ParseUtils.getRedEnvelop(response.toString());
				for (RedEnvelope body : loadDataList) {
					mLogList.add(body);
				}
				Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
				msg.obj = mLogList;
				msg.sendToTarget();

			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				// Log.i(TAG, "====没有拿到验证====" + error.getMessage());
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