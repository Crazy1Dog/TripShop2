package com.chiyu.shopapp.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chiyu.shopapp.bean.Order_PayinfoEntity;
import com.chiyu.shopapp.pay.H5PayDemoActivity;
import com.chiyu.shopapp.pay.PayResult;
import com.chiyu.shopapp.pay.SignUtils;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

public class PayDemoActivity1 extends FragmentActivity {

	// 商户PID
	public static final String PARTNER = "2088301970153142";
	// 商户收款账号
	public static final String SELLER = "2088301970153142";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMa6q8wiWGQ21M6pMghIOXikU145xDF+wwqxUt55lcS3Ndv8wu8Tn1OIH7NKAW37opJyeI+FC8lKRVEmsfx+AweMtapmwLzRo4usqch/0k4GSP8iZyy79aRqmHRI351O5VDD0Dej+31EhHgSm4hBVv2Qx2Y/pKLQKjVOBq/z4THjAgMBAAECgYBzONxO+ULip9/vCq6Vmrqnti8YHMoiIGsZkgKIN1qcudUifYTQdUIVfoFSxH/bsuBppLE+FVfmF4flK1hbGvzJp/6zCONzy+sVKLYQt1WP5Sc58cygHvHLOUhU4bjnAFH8dDXQDsz3PGiu8F+Xr4Hog5XkjG6ZN0oh11erWaJEoQJBAOu8S0p2anvpojUC0qMxIvJphjaT3W0BS24ft+j5x0ynHFU9hThNHd/PUb9VK78BhR5kfucqqyPKFurCtdeaivkCQQDXz/+KFwGq/HtQzBmE5VqY1KiSvgw46TQBVsCXeyr04hokXiUfvvx+3Kk4zrw8vcoCv41/BNm1AOM2NeDL+J67AkA+pvqzvakrabrMsAVfjg2ls9oR41a0Q+XSTOfKKaiIfNmQ5hrkDcrk0ur9GRvZVgQVQcxgj/yQNIPGvR0rQk2xAkAuYbiU8A7etba3DbZqVnSbJhE4wHmV/aC8rO1lYQZBbRqbOFSYNw7DIR+JYv0XvN5eqtZ5NwynFxK+AuRWUg+jAkEApHrnfsd8vn/RB/Z4DOSWdULPT5qCha1ATrr+8/pXCwC4wMW4qdKhJNVLJ31Txp8QpDeW0xiLd8HoptZpuFNwLg==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	private static final int SDK_PAY_FLAG = 1;
	// public static final String URL = "http://zgl5.etu6.org";
	public static final String URL = "http://test.pay.service.etu6.org";
	/********* 支付参数 **************/
	private String orderId;// 订单id
	private String orderno;// 订单编号
	private double orderamount;// 订单价格
	private String companyid = "34556";// 公司id
	private String memberid = "1000015";// 用户id（购买人）
	private String title;
	private String mobile_data;// 从获取支付信息
	private RequestQueue requestQueue;
	private String verify_sign;

	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
//					Toast.makeText(PayDemoActivity1.this, "支付成功",
//							Toast.LENGTH_SHORT).show();
					String_PostPay_CallbackInfo(resultInfo);
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity1.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayDemoActivity1.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		Intent intent = getIntent();
//		orderId = intent.getStringExtra("orderId");
//		orderno = intent.getStringExtra("orderno");
//		orderamount = Double.valueOf(intent.getStringExtra("orderamount"));
//		companyid = intent.getStringExtra("companyid");
//		memberid = intent.getStringExtra("memberid");
		requestQueue = Volley.newRequestQueue(getApplicationContext());

		JSONObject js = new JSONObject();
		try {
			js.put("webid", 5194);
			js.put("website", 5);
			js.put("paystyle", 5);
			js.put("totalamount", 0.01);
			js.put("bankid", "alipaymobile");
			js.put("isticket", 1);
			js.put("memberid", 1111);
			js.put("companyid", 1111);
			js.put("callbackurl",
					URLEncoder.encode(URL + "/callback_page", "UTF-8"));
			js.put("ordertype", 4);
			js.put("comment", "111");
			JSONArray array = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("aerree", "json");
			json.put("orderid", 111);
			json.put("orderno", "1111");
			json.put("orderamount", 0.01);
			json.put("ordertype", 4);
			array.put(json);
			js.put("details", array);
			System.out.println("566===" + js.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, URL + "/payment", js,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						Order_PayinfoEntity order_PayinfoEntity = ParseUtils
								.getOrderPayinfo(response.toString());
						mobile_data = order_PayinfoEntity.getResult();
						System.out.println("response==========="
								+ response.toString());

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

		requestQueue.add(jsonObjectRequest);

	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {

		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		String orderInfo = getOrderInfo("杨优泥=====测试的商品", "该测试商品的详细描述", "0.01");

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		// final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
		// + getSignType();
		final String payInfo = mobile_data;

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayDemoActivity1.this);
				// 调用支付接口，获取支付结果
				System.out.println("payInfo=====" + payInfo);
				String result = alipay.pay(payInfo, true);
				System.out.println("result=======" + result);

//				PayResult payResult = new PayResult(result);
//				/**
//				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
//				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
//				 * docType=1) 建议商户依赖异步通知
//				 */
//				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//
//				String resultStatus = payResult.getResultStatus();
//				System.out.println("resultInfo===================="
//						+ resultInfo);
//				System.out.println("resultStatus===================="
//						+ resultStatus);
//				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 */
		String url = "http://m.meituan.com";
		// url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);

	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\""
				+ "http://zgl5.etu6.org/notify_url.aspx" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return mobile_data;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	JsonObjectRequest jsonObjRequest;

	/************ 支付回调 *************/
	private void String_PostPay_CallbackInfo(String resultInfo) {

		try {
			String url = "http://test.pay.service.etu6.org/callback?platform=alipaymobile&"
					+ URLEncoder.encode(resultInfo, "UTF-8");

			VolleyUtils.requestString_Get(url, new OnRequest() {

				@Override
				public void response(String url, String response) {
					// TODO Auto-generated method stub
					// Log.i(TAG, "验证码来了！！！"+ response.toString());
					System.out.println("response=========支付回调=="
							+ response.toString());
					Order_PayinfoEntity order_PayinfoEntity = ParseUtils
							.getOrder_UpdateInfo(response.toString());
					verify_sign = order_PayinfoEntity.getVerify_sign();

					// Toast.makeText(LoginActivity.this, "验证码发送成功！",
					// Toast.LENGTH_SHORT).show();
					if (verify_sign.equals("true")) {
						Toast.makeText(PayDemoActivity1.this, "支付成功",
								Toast.LENGTH_SHORT).show();
					} else {
						// 判断resultStatus 为非"9000"则代表可能支付失败
						// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）

						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayDemoActivity1.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}

				@Override
				public void errorResponse(String url, VolleyError error) {
					// TODO Auto-generated method stub
					// Log.i(TAG, "====没有拿到验证===="+ error.getMessage());
				}
			});
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
}
