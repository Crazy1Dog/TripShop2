package com.chiyu.shopapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.Order_Refund;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * 退款页面
 */
public class Order_TuiKuanTiXingActivity extends Activity {
	private static final String TAG = "tripshop";
	private TextView main_title, main_ivTitleBtnLeft;

	private LinearLayout tixing_yinhang_layout;
	private TextView tixing_yinhangxinxi_txt, tixing_kaihu_txt,
			tixing_yinhangkahao_txt;
	private TextView order_kaihuhang_edtxt, order_yinhanghangka_edtxt;

	/********* 退款参数 **************/
	private String orderid;// 订单id-必填
	private int type;// (1:支付宝;2:银行转账)-必填
	private String alipay;// 支付宝账号
	private String bank;// 银行名称
	private String bankcode;// 银行卡号
	private String id;
	private int status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_tuikuantixing);
		MyApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		orderid = intent.getStringExtra("orderId");
		String_GetOrderTuiKuan(orderid);
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);

		tixing_yinhangxinxi_txt = (TextView) findViewById(R.id.tixing_yinhangxinxi_txt);
		tixing_kaihu_txt = (TextView) findViewById(R.id.tixing_kaihu_txt);
		tixing_yinhangkahao_txt = (TextView) findViewById(R.id.tixing_yinhangkahao_txt);

		order_kaihuhang_edtxt = (TextView) findViewById(R.id.order_kaihuhang_edtxt);
		order_yinhanghangka_edtxt = (TextView) findViewById(R.id.order_yinhanghangka_edtxt);
		main_title.setText("退款提醒");
		tixing_yinhang_layout = (LinearLayout) findViewById(R.id.tixing_yinhang_layout);

	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();

			}
		}
	}

	/*********** 查看退款信息 *****************/

	private void String_GetOrderTuiKuan(String orderid) {
		VolleyUtils.requestString_Get(URL.DEBUG + URL.REFUND_INFO +"/"+ orderid,
				new OnRequest() {

					@Override
					public void response(String url, String response) {
						// TODO Auto-generated method stub
						Order_Refund order_Refund = ParseUtils
								.getOrder_RederInfo(response.toString());
						id = order_Refund.getId();
						alipay = order_Refund.getAlipay();
						status = order_Refund.getStatus();
						bank = order_Refund.getBank();
						type = order_Refund.getType();
						bankcode = order_Refund.getBankCode();
						tixing_yinhangxinxi_txt = (TextView) findViewById(R.id.tixing_yinhangxinxi_txt);
						tixing_kaihu_txt = (TextView) findViewById(R.id.tixing_kaihu_txt);
						tixing_yinhangkahao_txt = (TextView) findViewById(R.id.tixing_yinhangkahao_txt);

						order_kaihuhang_edtxt = (TextView) findViewById(R.id.order_kaihuhang_edtxt);
						order_yinhanghangka_edtxt = (TextView) findViewById(R.id.order_yinhanghangka_edtxt);

						if (type == 1) {
							tixing_yinhangxinxi_txt.setText("退款账号");
							tixing_kaihu_txt.setText("支付宝帐号");
							order_kaihuhang_edtxt.setText(alipay);
							tixing_yinhang_layout.setVisibility(View.GONE);

						} else {
							tixing_yinhangxinxi_txt.setText("退款银行账号");
							tixing_kaihu_txt.setText("开户行");
							tixing_yinhangkahao_txt.setText("银行开户");
							order_kaihuhang_edtxt.setText(bank);
							order_yinhanghangka_edtxt.setText(bankcode);
						}

					}

					@Override
					public void errorResponse(String url, VolleyError error) {
						// TODO Auto-generated method stub
						Log.i(TAG, "====没有拿到验证====" + error.getMessage());
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
