package com.chiyu.shopapp.ui;

import com.hyphenate.easeui.controller.EaseUI;
import com.chiyu.shopapp.constants.MyApp;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyCoupon extends Activity implements OnClickListener{
	Button btn_title_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_coupon_activity);
		MyApp.getInstance().addActivity(this);
		initView();
		InitEvent();
	}

	private void InitEvent() {
		btn_title_back.setOnClickListener(this);
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
