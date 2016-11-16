package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.GridViewAdapter;
import com.chiyu.shopapp.adapters.SearchHistoryAdapter;
import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.DbHelper;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;
public class SearchActivity extends Activity implements OnClickListener {
	private DbHelper dbHelper;
	private SQLiteDatabase database;
	private List<String> history;
	private static final String TAG = "tripshop";
	private static final String TAG3 = "tripshop3";
	private SearchHistoryAdapter historyAdapter;
	private boolean isShowing = false;//GridView是否全部显示
	List<CategoryLineEntity> showingDatas = null;
	private GridViewAdapter myaAdapter = null;
	private List<CategoryLineEntity> FirstDatas = null;
	private List<CategoryLineEntity> SecondDatas = null;
	private List<CategoryLineEntity> hot_searchDatas = new ArrayList<CategoryLineEntity>();
	//定义搜索页面的控件
	private Button btn_search;
	private EditText et_search;
	private GridView hot_search;
	private Button btn_clearhistory;
	private ListView lv_history;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		MyApp.getInstance().addActivity(this);
		dbHelper = new DbHelper(SearchActivity.this);
		database = dbHelper.getWritableDatabase();
		history = new ArrayList<String>();
		Cursor cursor = database.query("history", null, null, null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){   
              history.add(cursor.getString(1));}
		Log.w(TAG3, "这就是搜索历史++++++++++++++++++"+history.toString());
		historyAdapter = new SearchHistoryAdapter(SearchActivity.this, new int []{R.layout.search_history});
		historyAdapter.setDatas(history);
		initViews();
		bindEvent();
		//请求热门搜索的数据
		VolleyUtils.requestString_Get(URL.DEBUG + URL.CATEGORY_LIST + ShareUtil.getString("receiveguestId"), new OnRequest() {
			
			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "获取热门搜索数据成功啦！！！"+response.toString());
				FirstDatas = ParseUtils.parseFirstCategoryList(response.toString());
				Log.i(TAG, FirstDatas.toString());
				SecondDatas = ParseUtils.parseSecondCategoryList(response.toString());
				Log.i(TAG, SecondDatas.toString());
				hot_searchDatas.addAll(FirstDatas);
				hot_searchDatas.addAll(SecondDatas);
				Log.i(TAG, "hot_searchdatas======="+hot_searchDatas.toString());
				myaAdapter = new GridViewAdapter(SearchActivity.this, new int []{R.layout.girdview_item});
				if(hot_searchDatas.size() > 8){
					showingDatas = hot_searchDatas.subList(0, 8);
				}else{
					showingDatas = hot_searchDatas;
				}
				myaAdapter.setDatas(showingDatas);
				hot_search.setAdapter(myaAdapter);
			}
			
			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	//为页面上的控件绑定事件
	private void bindEvent() {
		// TODO Auto-generated method stub
		btn_search.setOnClickListener(this);
		btn_clearhistory.setOnClickListener(this);
	}
	//初始化控件
	private void initViews() {
		btn_search = (Button) findViewById(R.id.btn_search);
		et_search = (EditText) findViewById(R.id.et_search);
		hot_search = (GridView) findViewById(R.id.hot_search);
		btn_clearhistory = (Button) findViewById(R.id.btn_clearhistory);
		lv_history = (ListView) findViewById(R.id.lv_history);
		lv_history.setVisibility(View.VISIBLE);
		lv_history.setAdapter(historyAdapter);
		lv_history.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SearchActivity.this, CategoryActivity.class);
				intent.putExtra("keyword",history.get(position));
				intent.setClass(SearchActivity.this, CategoryActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	//点击事件的回调
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_search://触发搜索
			if("".equals(et_search.getText().toString().trim())){
				Toast.makeText(SearchActivity.this, "请输入关键字！", Toast.LENGTH_SHORT).show();
			}else{
				String keyword = et_search.getText().toString().trim();
				ContentValues values = new ContentValues();
				values.put("history", keyword);
				database.insert("history", "", values);
				Intent intent = new Intent();
				intent.putExtra("keyword", keyword);
				intent.setClass(SearchActivity.this, CategoryActivity.class);
				startActivity(intent);
				finish();
			}
			break;
		case R.id.btn_clearhistory://清除历史搜索
			database.delete("history", null, null);
			lv_history.setVisibility(View.INVISIBLE);
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
