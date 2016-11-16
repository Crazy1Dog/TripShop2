package com.chiyu.shopapp.ui;
import java.util.List;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.fragment.ChatFragment;
import com.chiyu.shopapp.utils.Constant;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.DemoModel;
import com.chiyu.shopapp.utils.ShareUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.adapter.EaseMessageAdapter;
import com.hyphenate.easeui.widget.EaseSwitchButton;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SettingActivity extends Activity implements OnClickListener {
	Button btn_title_back;
//	RelativeLayout ll_suggestion;
	RelativeLayout ll_about_mine;
//	RelativeLayout ll_about_switch;
	EaseSwitchButton notifiSwitch;
	Button btn_logout;
	private String userId;
	private DemoModel settingsModel;
	private MyApp app;
	List<EMMessage> messages;
	private  MyApp myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);
		app = (MyApp) getApplication();
		MyApp.getInstance().addActivity(this);
		myApp = (MyApp) getApplication();
		initView();
	}

	private void initView() {
		
		btn_title_back = (Button) findViewById(R.id.btn_title_back);
//		ll_suggestion = (RelativeLayout) findViewById(R.id.ll_suggestion);
//		ll_about_switch = (RelativeLayout) findViewById(R.id.ll_about_switch);
		ll_about_mine = (RelativeLayout) findViewById(R.id.ll_about_mine);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		notifiSwitch = (EaseSwitchButton) findViewById(R.id.switch_notification);
		settingsModel = DemoHelper.getInstance().getModel();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	
			btn_title_back.setOnClickListener(this);
//			ll_suggestion.setOnClickListener(this);
			notifiSwitch.setOnClickListener(this);
			ll_about_mine.setOnClickListener(this);
			btn_logout.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_back:
			finish();
			break;
//		case R.id.ll_suggestion:
//			Intent intent2 = new Intent(this,SuggestionActivity.class);
//			startActivity(intent2);
//			break;
		case R.id.ll_about_mine:
			Intent intent3 = new Intent(this,AboutMineActivity.class);
			startActivity(intent3);
			break;
		case R.id.switch_notification:
			if (notifiSwitch.isSwitchOpen()) {
			    notifiSwitch.closeSwitch();
			    settingsModel.setSettingMsgNotification(false);
			} else {
			    notifiSwitch.openSwitch();
			    settingsModel.setSettingMsgNotification(true);
			}
			break;
		case R.id.btn_logout:
			ShareUtil.getString("userId");
			if(userId == null&&"".equals(userId)){
				Toast.makeText(this, "还没有登录", Toast.LENGTH_SHORT).show();
			}else{
				ShareUtil.putString("userId", "");
				app.setMobile("");
				app.setUserId("");
				app.setUsername("");
				app.setPhotopath("");
				app.setToken("");
				myApp.setUserId("");
				myApp.setMobile("");
				myApp.setPhotopath("");
				ShareUtil.putString("photoPath", "");
//				ShareUtil.putString("photoPath","");
				ShareUtil.putString("mobile", "");
				ShareUtil.putString("userMobile", "");
				logoutIM();
			}
			
			

			break;

		default:
			break;
		}
	}
	private void logoutIM() {
		DemoHelper.getInstance().logout(false,new EMCallBack() {
			
			@Override
			public void onSuccess() {
				runOnUiThread(new Runnable() {
					public void run() {
						Intent intent = new Intent();
						intent.setClass(SettingActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				});
			}
			
			@Override
			public void onProgress(int progress, String status) {
				
			}
			
			@Override
			public void onError(int code, String message) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
					}
				});
			}
		});
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		app = (MyApp) getApplication();
	}
	/**
	 * EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
//获取此会话的所有消息
List<EMMessage> messages = conversation.getAllMessages();
//sdk初始化加载的聊天记录为20条，到顶时需要去db里获取更多
//获取startMsgId之前的pagesize条消息，此方法获取的messages sdk会自动存入到此会话中，app中无需再次把获取到的messages添加到会话中
List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);

	 * 
	 * **/
}
