package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chiyu.shopapp.adapters.MyAdapter;
import com.chiyu.shopapp.bean.FeilName;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.fragment.PersonFragment;
import com.hyphenate.easeui.controller.EaseUI;
import com.umeng.analytics.MobclickAgent;

/**
 * 发送名单
 * */
public class SendFielActivity extends FragmentActivity implements
		OnClickListener {
	public static final String PERSEN_FRAGMENT = "person_fragment";
	public static final String CHILD_FRAGMENT = "child_fragment";
	public static final String KIDS_FRAGMENT = "kids_fragment";
	public static final int CONTACT_ADD = 1;
	private Fragment f = new Fragment();
	private FragmentManager manager;
	private FragmentTransaction transaction;
	ImageView iv_title_back;
	ImageView iv_title_contact;
	FrameLayout fragment_content;
	Button btn_ok;
	Bundle bundle = new Bundle();
	private String tag2 = "SendFielActivity";
	IMContactsReceiver receiver;
	String tag;
	public static String category;
	public static String personalFragment;
	public  String personalFragment2;
	public  String Fromtag;
	public String category2;
	private String tongxunlu;
	public  static  String bianji;
	public String bianji2;
	public static String tagChatFragment;
	public static String categoryContact;
	public String xinzeng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_fiel_activity);
		MyApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		
		personalFragment = intent.getStringExtra("personalFragment");
		bianji = intent.getStringExtra("bianji");
		bianji2 = intent.getStringExtra("bianji");
		personalFragment2 = intent.getStringExtra("personalFragment");
		//完善信息里传来的值
		tag = intent.getStringExtra("tag");
		tagChatFragment = intent.getStringExtra("tagChatFragment");
		category = String.valueOf(intent.getIntExtra("category", 0));
		category2 =  String.valueOf(intent.getIntExtra("category", 0));
		categoryContact = intent.getStringExtra("categoryContact");
		Fromtag = intent.getStringExtra("Fromtag");//来自完善信息的tag
		tongxunlu = intent.getStringExtra("tongxunlu");
		xinzeng = intent.getStringExtra("xinzeng");
		initView();
		if (personalFragment != null || bianji != null) {
			btn_ok.setVisibility(View.GONE);
		} else {
			btn_ok.setVisibility(View.VISIBLE);
		}
		initEvent();
		PersonFragment person = new PersonFragment();
		transaction.add(R.id.fg_content, person).commit();
		IntentFilter filter = new IntentFilter();
		filter.addAction("mingdanhuichuan");
		receiver = new IMContactsReceiver();
		registerReceiver(receiver, filter);
	}

	private void initEvent() {
		iv_title_back.setOnClickListener(this);
		iv_title_contact.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	private void initView() {
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
		iv_title_contact = (ImageView) findViewById(R.id.iv_title_contact);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		fragment_content = (FrameLayout) findViewById(R.id.fg_content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_back:// 返回
			finish();
			break;
		case R.id.iv_title_contact:// 通讯录
			Intent intent = new Intent(this, ContactActivity.class);
			intent.putExtra("tag", tag);
			intent .putExtra("tagChatFragment", tagChatFragment);
			intent.putExtra("category", category);
			intent.putExtra("category2", category2);
			intent.putExtra("personalFragment",personalFragment);
			intent.putExtra("personalFragment2",personalFragment2);
			intent.putExtra("Fromtag", Fromtag);
			intent.putExtra("tongxunlu", tongxunlu);
			intent.putExtra("bianji2", bianji2);
			intent.putExtra("xinzeng", xinzeng);
			startActivity(intent);
			finish();
			break;

		case R.id.btn_ok:// 名单回传到聊天页面

			Bundle bundle = new Bundle();
			ArrayList<FeilName> list = new ArrayList<FeilName>();
			list.clear();
			list.addAll(MyAdapter.sendList);
			MyAdapter.clearSendList();
			bundle.putParcelableArrayList("path", list);
			Intent intent2 = new Intent();
			intent2.setAction("mingdanhuichuan");
			intent2.putExtra("path", bundle);
			intent2.putExtra("tag", tag);
			intent2.putExtra("tag2", tag2);
			intent2.putExtra("tagChatFragment", tagChatFragment);
			sendBroadcast(intent2);
			finish();
			break;

		default:
			break;
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onEvent(SendFielActivity.this, "SendFielActivity");
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	class IMContactsReceiver extends BroadcastReceiver {
		private ArrayList<FeilName> personlist = new ArrayList<FeilName>();
		private String tag;
		private String tag2;

		@Override
		public void onReceive(Context context, Intent intent) {
			tag2 = intent.getStringExtra("tag2");
			tag = intent.getStringExtra("tag");
			tagChatFragment = intent.getStringExtra("tagChatFragment");
			Bundle bundle = intent.getBundleExtra("path");
			ArrayList<FeilName> list = bundle.getParcelableArrayList("path");
			for (int i = 0; i < list.size(); i++) {
				FeilName feilName = list.get(i);
				personlist.add(feilName);
			}
		}

		public List<FeilName> getDatas() {
			return personlist;

		}

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
	}
	
}
