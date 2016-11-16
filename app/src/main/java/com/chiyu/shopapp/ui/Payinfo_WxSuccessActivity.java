package com.chiyu.shopapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiyu.shopapp.constants.MyApp;

/**
 * 支付成功页面
 */
public class Payinfo_WxSuccessActivity extends Activity {
	private static final int WHAT_DTD_SUCCESS = 0;
	private TextView main_title, main_ivTitleBtnLeft, reserve_success_txt,
			reserve_wanshan_txt;
	private LinearLayout lineDetails_yuding_layout;
	private int order_status;
	private int orderid;
	private MyApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reserve_success);
		MyApp.getInstance().addActivity(this);
		app = (MyApp) getApplication();
		order_status = app.getOrder_status();
		orderid = app.getOrderid();
		// Intent intent = getIntent();
		// order_status = intent.getIntExtra("order_status",order_status);
		// orderid = intent.getIntExtra("orderid",orderid);
		 System.out.println("order_status========"+order_status);
		 System.out.println("orderid========"+orderid);
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);

		lineDetails_yuding_layout = (LinearLayout) findViewById(R.id.lineDetails_yuding_layout);
		reserve_success_txt = (TextView) findViewById(R.id.reserve_success_txt);
		reserve_wanshan_txt = (TextView) findViewById(R.id.reserve_wanshan_txt);
		main_title.setText("支付成功");

	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		lineDetails_yuding_layout.setOnClickListener(new viewClickListener());
		if (order_status == 1) {
			reserve_success_txt.setText("您可在订单中完善详细信息！");
			reserve_wanshan_txt.setText("完善信息");
		} else {
			reserve_success_txt.setVisibility(View.GONE);
			reserve_wanshan_txt.setText("我的订单");
		}
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();

			} else if (id == R.id.lineDetails_yuding_layout) {

				if (order_status == 1) {
					Intent intent = new Intent(Payinfo_WxSuccessActivity.this,
							Order_PerfectActivity.class);
					intent.putExtra("orderId", orderid + "");
					System.out.println("orderid==========" + orderid);
					startActivityForResult(intent, WHAT_DTD_SUCCESS);
					finish();

				} else {
					Intent intent = new Intent(Payinfo_WxSuccessActivity.this,
							Order_Activity.class);
					startActivity(intent);
					finish();
				}
			}
		}
	}
}
