package com.chiyu.shopapp.ui;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.utils.ShareUtil;
import com.hyphenate.easeui.controller.EaseUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

public class AppStartActivity extends Activity {
	private static final String TAG = "tripshopc";
	private Button btn_remainTime;
	private Handler handler;
	private Boolean isFirstIn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyApp.getInstance().addActivity(this);
		setContentView(R.layout.appstart_activity);
		isFirstIn = ShareUtil.getBoolean("isFirstIn");
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				btn_remainTime.setText(msg.arg1 - 1 + "s");
			}
		};
		btn_remainTime = (Button) findViewById(R.id.btn_remaintime);
		CountDownTimer downTimer = new CountDownTimer(5000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				Message message = Message.obtain();
				message.arg1 = (int) millisUntilFinished / 1000;
				handler.sendMessage(message);
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				if(isFirstIn){
					ShareUtil.putBoolean("isFirstIn", false);
					Intent intent = new Intent();
					intent.setClass(AppStartActivity.this, GuideActivity.class);
					startActivity(intent);
					finish();
				}else{
					Intent intent = new Intent();
					System.out.print("memberId=================="+ShareUtil.getString("memberId"));
					Log.e(TAG, "========================="+ShareUtil.getString("memberId"));
					if("".equals(ShareUtil.getString("memberId")) ||ShareUtil.getString("memberId") == null){
						intent.setClass(AppStartActivity.this, InvitationActivity.class);
					}else{
						intent.setClass(AppStartActivity.this, MainActivity.class);
					}
					startActivity(intent);
					finish();
				}
				
			}

		}.start();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
}
