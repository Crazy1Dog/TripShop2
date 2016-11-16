package com.chiyu.shopapp.ui;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.MyCollectListViewAdapter;
import com.chiyu.shopapp.adapters.MySearchFragmentListViewAdapter;
import com.chiyu.shopapp.bean.TravelLineEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

public class MyCollect extends Activity implements OnClickListener {
	private static final String TAG2 = "tripshop2";
	private MyCollectListViewAdapter collectAdapter;
	private List<TravelLineEntity> myCollectList;
	private Button btn_title_back;
	private ListView lv_mycollect;
	private RelativeLayout rl_mycollect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_collect_activity);
		MyApp.getInstance().addActivity(this);
		initView();
		InitEvent();
		getDataFromNet();
	}

	/**
	 * 从网络获取收藏列表 提交方式：POST 接口地址：/api/receive/uc/collections 参数列表：[ token
	 * :登录token--必填 userId :C客ID--必填 pageSize :每页记录数--必填 curPageNo :当前页码--必填 ]
	 */
	private void getDataFromNet() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("token", ShareUtil.getString("token"));
		map.put("userId", ShareUtil.getString("userId"));
		map.put("pageSize", "20");
		map.put("curPageNo", "");
		VolleyUtils.requestString_Post(map, URL.DEBUG + URL.GETMYCOLLECTLIST,
				new OnRequest() {

					@Override
					public void response(String url, String response) {
						// TODO Auto-generated method stub
						if (response != null) {
							rl_mycollect.setVisibility(View.GONE);
							lv_mycollect.setVisibility(View.VISIBLE);
						}
						myCollectList = ParseUtils
								.parseTravelLineEntity(response.toString());
						collectAdapter = new MyCollectListViewAdapter(
								MyCollect.this,
								new int[] { R.layout.mycollect_listview_item });
						collectAdapter.setDatas(myCollectList);
						lv_mycollect.setAdapter(collectAdapter);
					}

					@Override
					public void errorResponse(String url, VolleyError error) {
						// TODO Auto-generated method stub
						Log.w(TAG2, "拉去收藏数据失败" + error.getMessage());
					}
				});
	}

	private void InitEvent() {
		btn_title_back.setOnClickListener(this);
		lv_mycollect = (ListView) findViewById(R.id.lv_mycollect);
		rl_mycollect = (RelativeLayout) findViewById(R.id.rl_mycollect);
		lv_mycollect.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String dateList = collectAdapter.getData().get(position).getDateList();
				if(dateList == null || "".equals(dateList)||dateList == "null"){
					Toast.makeText(MyCollect.this, "该线路已过期!", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("lineId", myCollectList.get(position)
						.getLineId());
				intent.putExtra("dateId", myCollectList.get(position)
						.getDateId());
				intent.setClass(MyCollect.this,
						Line_DetailsActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		btn_title_back = (Button) findViewById(R.id.btn_title_back);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_back:
			finish();
			break;

		default:
			break;
		}
	}
 
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
}
