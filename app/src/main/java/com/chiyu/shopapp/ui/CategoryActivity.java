package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.AllTitlesAdapter;
import com.chiyu.shopapp.adapters.MySearchFragmentPageAdapter;
import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.fragment.SearchFragment;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ScreenUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.chiyu.shopapp.view.MyViewPager;
import com.hyphenate.easeui.controller.EaseUI;

public class CategoryActivity extends FragmentActivity {
	private boolean isDowning = false;
	private static final String TAG2 = "tripshop2";
	private ImageView btn_more;
	private View allTitleView;
	private View titlebottomView;
	private GridView gv_alltitles;
	private AllTitlesAdapter allTitleAdapter;
	private PopupWindow popupWindow;
	private Button btn_back;
	private EditText et_search_category;
	private TextView tv_category_title;
	private String keyword;
	private ArrayList<Integer>moveToList ;
	private MySearchFragmentPageAdapter adapter;
	private HorizontalScrollView myScrollView;
	private RadioGroup rg_title;
	private int position;//从搜索页面点击选项进来的位置
	private int categoryPosition;//从首页点击进来的位置
	private List<SearchFragment> FragmentLists = new ArrayList<SearchFragment>();
	private List<CategoryLineEntity> FirstDatas = null;
	private List<CategoryLineEntity> SecondDatas = null;
	private List<CategoryLineEntity> ThirdDatas = null;
	private List<CategoryLineEntity> categoryList = new ArrayList<CategoryLineEntity>();
	private MyApp app;
	//定义分类列表页面的控件
	private MyViewPager vp_searchs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_activity);
		MyApp.getInstance().addActivity(this);
		app = (MyApp) getApplication();
		Intent intent = getIntent();
		position = intent.getIntExtra("position", -1);
		categoryPosition = intent.getIntExtra("categoryNum", -1);
		keyword = intent.getStringExtra("keyword");
		Log.i(TAG2, position+"点击的位置传递过来的position");
		initViews();
		//获取分类的种类数据，用于初始化标题栏
		VolleyUtils.requestString_Get(URL.DEBUG + URL.CATEGORY_LIST + ShareUtil.getString("receiveguestId"), new OnRequest() {
			@SuppressWarnings("deprecation")
			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				Log.i(TAG2, "分类数据请求成功++++++"+response.toString());
				System.out.println("分类点击请求的数据=======title==========" + url);
				System.out.println("分类点击请求的数据======title===========" + response.toString());
				FirstDatas = ParseUtils.parseFirstCategoryList(response.toString());
				Log.i(TAG2, FirstDatas.toString());
				SecondDatas = ParseUtils.parseSecondCategoryList(response.toString());
				Log.i(TAG2, SecondDatas.toString());
				ThirdDatas = ParseUtils.parseThirdCategoryList(response.toString());
				//把所有的分类集中到一个集合中
				categoryList.addAll(FirstDatas);
				categoryList.addAll(SecondDatas);
				allTitleAdapter = new AllTitlesAdapter(CategoryActivity.this, new int []{R.layout.alltile_view});
				allTitleAdapter.setDatas(categoryList);
				gv_alltitles.setAdapter(allTitleAdapter);
				initTitle();//初始化标题
				initViewPager();
				RadioButton firstButton;
				if(position != -1 && categoryPosition == -1){
					vp_searchs.setCurrentItem(position + 1);
					myScrollView.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							myScrollView.scrollTo(moveToList.get(position + 1), 0);
						}
					});
					Log.w(TAG2, moveToList.get(position + 1)+"scrollview滚动的位置");
					firstButton = (RadioButton) rg_title.getChildAt(position + 1);
				}else if (categoryPosition != -1 && position == -1){
					vp_searchs.setCurrentItem(categoryPosition);
					myScrollView.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							myScrollView.scrollTo(moveToList.get(categoryPosition), 0);
						}
					});
					Log.w(TAG2, moveToList.get(categoryPosition)+"scrollview滚动的位置");
					firstButton = (RadioButton) rg_title.getChildAt(categoryPosition);
					tv_category_title.setText(firstButton.getText());
				}else{
					vp_searchs.setCurrentItem(0);
					myScrollView.scrollTo(moveToList.get(0), 0);
					Log.w(TAG2, moveToList.get(position + 1)+"scrollview滚动的位置");
					firstButton = (RadioButton) rg_title.getChildAt(0);
				}
				tv_category_title.setText(firstButton.getText());
				//初始化标题栏的状态
				firstButton.setChecked(true);
				firstButton.setTextColor(Color.parseColor("#2CCCB9"));
				//为viewPager设置监听事件
				vp_searchs.setOnPageChangeListener(new OnPageChangeListener() {
					@Override
					public void onPageSelected(int arg0) {
						// TODO Auto-generated method stub
						rg_title.check(arg0);
						for(int i = 0; i < rg_title.getChildCount(); i++){
							RadioButton button = (RadioButton) rg_title.getChildAt(arg0);
								if(button.isChecked()){
									button.setTextColor(Color.parseColor("#2CCCB9"));
									tv_category_title.setText(button.getText());
									
								}else{
									button.setTextColor(Color.parseColor("#ADADAD"));
								}
						}
						myScrollView.scrollTo(moveToList.get(arg0), 0);
						Log.w(TAG2, "滑动过程中moveToList中取出来的值："+ moveToList.get(arg0));
					}
					
					@Override
					public void onPageScrolled(int position, float positionoffset, int arg2) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
					}
				});
			}
			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
			}
		});
	}
	//初始化标题
	
	@SuppressLint("NewApi")
	private void initTitle() {
		// TODO Auto-generated method stub
		moveToList = new ArrayList<Integer>();
		for (int i = 0; i < categoryList.size() + 1; i++) {
			RadioButton radioButton = new RadioButton(CategoryActivity.this);
			if(i == 0){
				radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
				radioButton.setText("全部");
				moveToList.add(0);
				Log.w(TAG2, "moveTolist中加进去的值："+ 0);
			}else if (i == 1 ||i == 2||i == 3||i == 4){
				moveToList.add(0);
				//Log.w(TAG2, "moveTolist中加进去的值："+ ((ScreenUtils.getScreenWidth() / 6) * i));
				radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
				radioButton.setText(categoryList.get(i - 1).getCategoryName());
			}else{
				moveToList.add((ScreenUtils.getScreenWidth() / 6) * i);
				Log.w(TAG2, "moveTolist中加进去的值："+ ((ScreenUtils.getScreenWidth() / 6) * i));
				radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
				radioButton.setText(categoryList.get(i - 1).getCategoryName());
			}
			//为radiobutton设置id
			radioButton.setId(i);
			radioButton.setGravity(Gravity.CENTER);
			radioButton.setTextColor(Color.parseColor("#ADADAD"));
			radioButton.setBackgroundResource(R.drawable.title_back_selector);
			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(new LayoutParams(ScreenUtils.getScreenWidth() / 6, LayoutParams.MATCH_PARENT));
			rg_title.addView(radioButton,params);
		}
		//为RadioGruop设置选中监听
		rg_title.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				for(int i = 0; i < rg_title.getChildCount(); i++){
//					System.out.println("i=======rg_title==="+i+"categoryList=========="+categoryList.get(i).getCategoryName());
					RadioButton button = (RadioButton) rg_title.getChildAt(i);
					if (button.isChecked()){
//						app.setCategoryId(categoryList.get(i-1).getId());
//						System.out.println("存青豆====================="+categoryList.get(i-1).getId());
						button.setTextColor(Color.parseColor("#2CCCB9"));
						tv_category_title.setText(button.getText());
					}else{
						button.setTextColor(Color.parseColor("#ADADAD"));
					}
				}
				vp_searchs.setCurrentItem(checkedId);
			}
		});
	}
	//初始化ViewPager
	private void initViewPager() {
		// TODO Auto-generated method stub
		for (int i = 0; i < categoryList.size() + 1; i++) {
			if (i == 0){
				Bundle bundle = new Bundle();
				bundle.putInt("i", 0);
				bundle.putString("keyword", keyword);
				bundle.putString("categoryid", "");
				ArrayList<CategoryLineEntity> list = new ArrayList<CategoryLineEntity>();
				list.addAll(ThirdDatas);
				bundle.putParcelableArrayList("list", list);
				SearchFragment fragment = new SearchFragment();
				fragment.setArguments(bundle);
				FragmentLists.add(fragment);
			}else{
				Bundle bundle = new Bundle();
				bundle.putInt("i", i);
				bundle.putString("keyword", "");
				bundle.putString("categoryid", categoryList.get(i - 1).getId());//携带分类id到fragment里面去
				ArrayList<CategoryLineEntity> list = new ArrayList<CategoryLineEntity>();
				list.addAll(ThirdDatas);
				bundle.putParcelableArrayList("list", list);
				SearchFragment fragment = new SearchFragment();
				fragment.setArguments(bundle);
				FragmentLists.add(fragment);
			}
		}
		adapter = new MySearchFragmentPageAdapter(getSupportFragmentManager(), FragmentLists);
		vp_searchs.setAdapter(adapter);
	}
	//初始化控件
	private void initViews() {
		// TODO Auto-generated method stub
		//------------------------------------------\
		titlebottomView = findViewById(R.id.titlebottom);
		tv_category_title = (TextView) findViewById(R.id.tv_catergory_title);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_more = (ImageView) findViewById(R.id.btn_change);//弹出和收回所有标题
		allTitleView = getLayoutInflater().inflate(R.layout.titles_view, null);
		popupWindow = new PopupWindow(allTitleView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_world));
		btn_more.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isDowning){
					isDowning = true;
					btn_more.setImageResource(R.drawable.detail_icon_up);
					popupWindow.showAsDropDown(titlebottomView);
				}else{
					isDowning = false;
					btn_more.setImageResource(R.drawable.detail_icon_down);
					popupWindow.dismiss();
				}
				
			}
		});
		gv_alltitles = (GridView) allTitleView.findViewById(R.id.gv_alltitle);
		gv_alltitles.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				isDowning = false;
//				System.out.println("i=======gv_alltitles==="+position+"categoryList=========="+categoryList.get(i).getCategoryName());
				btn_more.setBackground(getResources().getDrawable(R.drawable.detail_icon_down));
				Toast.makeText(CategoryActivity.this, position+"项！", Toast.LENGTH_SHORT).show();
				rg_title.check(position + 1);
//				app.setCategoryId(categoryList.get(position + 1).getId());
//				System.out.println("存青豆=====3333333333333333333================"+categoryList.get(position - 1).getId());
				myScrollView.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						myScrollView.scrollTo(moveToList.get(position + 1), 0);
					}
				});
				popupWindow.dismiss();
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		et_search_category = (EditText) findViewById(R.id.et_search_category);
		et_search_category.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CategoryActivity.this, SearchActivity.class);
				startActivity(intent);
				finish();
			}
		});
		myScrollView = (HorizontalScrollView) findViewById(R.id.hsv_category);
		rg_title = (RadioGroup) findViewById(R.id.rg_title);
		vp_searchs = (MyViewPager) findViewById(R.id.vp_searchs);
		vp_searchs.setNoScroll(false);
		if(categoryPosition != -1 && position == -1){
			et_search_category.setVisibility(View.VISIBLE);
			tv_category_title.setVisibility(View.GONE);
		}else{
			et_search_category.setVisibility(View.VISIBLE);
			tv_category_title.setVisibility(View.GONE);
		}
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
	
}
