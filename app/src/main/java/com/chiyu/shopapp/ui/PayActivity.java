package com.chiyu.shopapp.ui;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chiyu.shopapp.bean.WxPay;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.hyphenate.easeui.controller.EaseUI;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class PayActivity extends Activity {

	private IWXAPI api;
	public static final String URL1 = "http://test.pay.service.etu6.org/payment";
	private String appid;
	private String noncestr;
	private String packages;
	private String partnerid;
	private String prepayid;
	private String sign;
	private String timestamp;
	private RequestQueue requestQueue;
	private String orderId;// 订单id
	private String orderno;// 订单编号
	private double orderamount;// 订单价格
	private int companyid;// 公司id
	private int memberid;// 用户id（购买人）
	private String title = "";// 订单标题
	private int order_status;
	private int orderid;
	private int webid = 5194;// B2C站点id
	private int website = 5;// 1-驰誉旅游 2-欢途 4-美程
	private int paystyle = 4;// 是 short 支付类型 1=保险订单; 4=线路订单
	private double totalamount;// 是 double 订单总金额
	private String bankid = "alipaymobile";// int 银行id
	private int isticket = 1;
	private String callbackurl;// 是 string 支付平台回调用的url
	private int ordertype = 4;// 订单类型
	private String comment = "";// 否 string 支付备注信息
	private String details = ""; // 是 string 订单明细信息{订单id，订单价格}
	private String aerree = "json";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		orderno = intent.getStringExtra("orderno");
		order_status = intent.getIntExtra("order_status", order_status);
		orderid = Integer.parseInt(orderId);
//		orderamount = 0.01;
		orderamount = intent.getDoubleExtra("orderamount", orderamount);
		companyid = Integer.parseInt(intent.getStringExtra("companyid"));
		memberid = Integer.parseInt(intent.getStringExtra("memberid"));
		title = intent.getStringExtra("title");
		requestQueue = Volley.newRequestQueue(getApplicationContext());

		JSONObject js = new JSONObject();
		try {
			js.put("webid", webid);
			js.put("website", website);
			js.put("paystyle", paystyle);
			//orderamount
			js.put("totalamount", 0.01);
			js.put("bankid", "wxpaymobile");
			js.put("isticket", isticket);
			js.put("memberid", memberid);
			js.put("companyid", companyid);
//			js.put("callbackurl",URL.pay_HUITIAOURL);
			js.put("callbackurl",
					URLEncoder.encode(URL.pay_C_HUITIAOURL, "UTF-8"));
			js.put("ordertype", ordertype);
			js.put("comment", comment);
			JSONArray array = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("aerree", aerree);
			json.put("orderid", orderid);
			json.put("orderno", orderno);
			json.put("orderamount", 0.01);
			json.put("ordertype", 4);
			array.put(json);
			js.put("details", array);
//			js.put("webid", 5194);
//			js.put("website", 5);
//			js.put("paystyle", 5);
//			js.put("totalamount", 0.01);
//			js.put("bankid", "wxpaymobile");
//			js.put("isticket", 1);
//			js.put("memberid", 1111);
//			js.put("companyid", 1111);
//			js.put("callbackurl",
//					URLEncoder.encode(URL + "/callback_page", "UTF-8"));
//			js.put("ordertype", 4);
//			js.put("comment", "111");
//			JSONArray array = new JSONArray();
//			JSONObject json = new JSONObject();
//			json.put("aerree", "json");
//			json.put("orderid", 111);
//			json.put("orderno", "1111");
//			json.put("orderamount", 0.01);
//			json.put("ordertype", 4);
//			array.put(json);
//			js.put("details", array);
			System.out.println("566===" + js.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, URL1 + "/payment", js,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println("response=========="
								+ response.toString());
						WxPay order_PayinfoEntity = ParseUtils
								.PayInfo(response.toString());
						System.out
								.println("order_PayinfoEntity.getAppid()======="
										+ order_PayinfoEntity.getAppid());
						appid = order_PayinfoEntity.getAppid();
						noncestr = order_PayinfoEntity.getNoncestr();
						packages = order_PayinfoEntity.getPackages();
						partnerid = order_PayinfoEntity.getPartnerid();
						prepayid = order_PayinfoEntity.getPrepayid();
						sign = order_PayinfoEntity.getSign();
						timestamp = order_PayinfoEntity.getTimestamp();

						System.out.println("appid===========" + appid);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

		requestQueue.add(jsonObjectRequest);
		api = WXAPIFactory.createWXAPI(this, "wxf5a649f0f190b79a");

		Button appayBtn = (Button) findViewById(R.id.appay_btn);
		appayBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Button payBtn = (Button) findViewById(R.id.appay_btn);
				payBtn.setEnabled(false);
				try {
					PayReq req = new PayReq();
					req.appId = appid;
					req.partnerId = partnerid;
					req.prepayId = prepayid;
					req.nonceStr = noncestr;
					req.timeStamp = timestamp;
					req.packageValue = packages;
					req.sign = sign;
					System.out.println("appid==========" + appid);
					System.out.println("partnerid===" + partnerid);
					Toast.makeText(PayActivity.this, "正常调起支付",
							Toast.LENGTH_SHORT).show();
					// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
					api.sendReq(req);
				} catch (Exception e) {
					Log.e("PAY_GET", "异常：" + e.getMessage());
					Toast.makeText(PayActivity.this, "异常：" + e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
				payBtn.setEnabled(true);
			}
		});
		Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
		checkPayBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
				Toast.makeText(PayActivity.this,
						String.valueOf(isPaySupported), Toast.LENGTH_SHORT)
						.show();
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
