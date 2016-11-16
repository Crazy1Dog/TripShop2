package com.chiyu.shopapp.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.Order_SuccessEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * 退款页面
 */
public class Order_TuiKuanActivity extends Activity {
	private static final String TAG = "tripshop";
	private static final int WHAT_DTD_SUCCESS = 0;
	private TextView main_title, main_ivTitleBtnLeft;
	private EditText order_zhifubao_edtxt, order_kaihuhang_edtxt,
			order_yinhanghangka_edtxt;

	/*********** 提交按钮、费用明细组件 *************/
	private LinearLayout lineDetails_yuding_layout;
	/********* 退款参数 **************/
	private int type;// (1:支付宝;2:银行转账)-必填
	private String alipay;// 支付宝账号
	private String bank;// 银行名称
	private String bankcode;// 银行卡号
	private String orderid;// 订单id-必填
	private String memberid;// 计调id-必填
	private String receiveguestid;// 微店id-必填
	private String orderid1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_tuikuan);
		MyApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		orderid = intent.getStringExtra("orderId");
		memberid = ShareUtil.getString("memberId");
		orderid1 = orderid;
		receiveguestid = ShareUtil.getString("receiveguestId");
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		order_zhifubao_edtxt = (EditText) findViewById(R.id.order_zhifubao_edtxt);
		order_kaihuhang_edtxt = (EditText) findViewById(R.id.order_kaihuhang_edtxt);
		order_yinhanghangka_edtxt = (EditText) findViewById(R.id.order_yinhanghangka_edtxt);
		main_title.setText("申请退款");
		lineDetails_yuding_layout = (LinearLayout) findViewById(R.id.lineDetails_yuding_layout);

	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		lineDetails_yuding_layout.setOnClickListener(new viewClickListener());
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();

			} else if (id == R.id.lineDetails_yuding_layout) {
				alipay = order_zhifubao_edtxt.getText().toString();
				bank = order_kaihuhang_edtxt.getText().toString();
				bankcode = order_yinhanghangka_edtxt.getText().toString();
				if (!"".equals(alipay) && alipay != null) {
					type = 1;
				} else if (!"".equals(bank) && bank != null
						&& !"".equals(bankcode) && bankcode != null) {
					type = 2;
				} else {
					Toast.makeText(Order_TuiKuanActivity.this, "请输入退款帐号信息",
							Toast.LENGTH_SHORT).show();

				}
				String_PostOrderTuiKuan(type, alipay, bank, bankcode, orderid,
						memberid, receiveguestid);

			}
		}
	}

	/*********** 提交申请退款信息 *****************/
	private void String_PostOrderTuiKuan(int type, String alipay, String bank,
			String bankcode, String orderid, String memberid,
			String receiveguestid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type + "");
		map.put("alipay", alipay);
		map.put("bank", bank);
		map.put("bankcode", bankcode);
		map.put("orderid", orderid);
		map.put("memberid", memberid);
		map.put("receiveguestid", receiveguestid);

		VolleyUtils.requestString_Post((HashMap<String, String>) map, URL.DEBUG
				+ URL.REFUND_APPLY, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				try {
					JSONObject object = new JSONObject(response.toString());
					int code = object.getInt("code");
					String message = object.getString("message");
					if (code == 200) {
						Intent intent = new Intent(Order_TuiKuanActivity.this,
								Order_TuiKuanTiXingActivity.class);
						intent.putExtra("orderId", orderid1);
						startActivityForResult(intent, WHAT_DTD_SUCCESS);
						Toast.makeText(Order_TuiKuanActivity.this, message,
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(Order_TuiKuanActivity.this, message,
								Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				Log.i(TAG, "退款失败" + error.getMessage());
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
