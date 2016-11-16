package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.My_Red_ListAdapter;
import com.chiyu.shopapp.bean.RedEnvelope;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

public class My_Red_ListActivity extends Activity {
	// ** Handler What加载数据完毕 **//
	private static final int WHAT_DID_LOAD_DATA = 0;
	// ** Handler What更新数据完毕 **//
	private static final int WHAT_DID_REFRESH = 1;
	private static final int WHAT_ORDER_LOAD_DATA = 3;
	private static final int WHAT_ORDER_SEARCH_LOAD_DATA = 5;
	private TextView main_title, main_ivTitleBtnLeft;
	private GridView gridview;
	private My_Red_ListAdapter adapter;
	private List<RedEnvelope> listData = new ArrayList<RedEnvelope>();
	private LinearLayout linearLayout;
	private Dialog mDialog;
	private int type;
	private String token;// :登录token--必填
	private String userId;// :C客ID--必填
	private String url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_red_list);
		MyApp.getInstance().addActivity(this);
		token = ShareUtil.getString("token");
		userId = ShareUtil.getString("userId");
		
		String_GetLineDetails(url, token, userId);
		initLayout();
		initListener();
	}

	private void initLayout() {
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		main_title.setText("我的优惠劵");
		linearLayout = (LinearLayout) findViewById(R.id.dialog_viewLinearLayout);
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new My_Red_ListAdapter(this);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridview.setAdapter(adapter);

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

	/************ 获取红包列表 *************/
	private void String_GetLineDetails(String url, String token, String userId) {
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