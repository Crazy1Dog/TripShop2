package com.chiyu.shopapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiyu.shopapp.constants.MyApp;
import com.hyphenate.easeui.controller.EaseUI;

public class Invitation_RedActivity extends Activity {

	private TextView main_title, main_ivTitleBtnLeft;
	
	private String registerAmount;// 新注册抵扣红包
	private String forwardAmount;// 转发下单支付红包
	private TextView invitation_yaoqinghongbao_txt, invitation_jiage_txt,
			order_state_txt;
	private LinearLayout invitation_redfenxiang;

	/**
	 * 邀请领取红包
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invitation_red);
		MyApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		registerAmount = intent.getStringExtra("registerAmount");
		forwardAmount = intent.getStringExtra("forwardAmount");
		initView();
		initListener();

	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		main_title.setText("邀请好友");
		invitation_yaoqinghongbao_txt = (TextView) findViewById(R.id.invitation_yaoqinghongbao_txt);
		invitation_redfenxiang = (LinearLayout) findViewById(R.id.invitation_redfenxiang);
		invitation_jiage_txt = (TextView) findViewById(R.id.invitation_jiage_txt);
		order_state_txt = (TextView) findViewById(R.id.order_state_txt);
		invitation_yaoqinghongbao_txt.setText("两人即可得" + registerAmount
				+ "元现金红包");
		invitation_jiage_txt.setTypeface(Typeface
				.defaultFromStyle(Typeface.BOLD));// 加粗
		invitation_jiage_txt.getPaint().setFakeBoldText(true);// 加粗
		invitation_jiage_txt.setText(registerAmount);
		order_state_txt.setText("推荐人即可在得" + forwardAmount + "元红包");
		invitation_jiage_txt
				.setMovementMethod(LinkMovementMethod.getInstance());

	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		invitation_redfenxiang.setOnClickListener(new viewClickListener());

	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();

			} else if (id == R.id.invitation_redfenxiang) {
				Intent intent = new Intent(Invitation_RedActivity.this,
						Invitation_RedFenXiangActivity.class);
				startActivity(intent);
			}
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}

}
