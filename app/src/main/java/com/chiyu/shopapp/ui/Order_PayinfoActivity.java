package com.chiyu.shopapp.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chiyu.shopapp.bean.Order_PayinfoEntity;
import com.chiyu.shopapp.bean.WxPay;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.pay.H5PayDemoActivity;
import com.chiyu.shopapp.pay.PayResult;
import com.chiyu.shopapp.pay.SignUtils;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 支付页面
 */
public class Order_PayinfoActivity extends Activity {
	private static final String TAG = "tripshop";
	private static final int WHAT_DTD_SUCCESS = 0;
	private TextView main_title, main_ivTitleBtnLeft;
	/********** 选择红包按钮、支付宝、微信按钮 ****************/
	private ImageButton order_pay_hongbao_imbt, order_zhifubao_imbt;
	// order_weixin_imbt
	private TextView order_totalprice__txt, order_youhuiprice__txt,
			order_shifuprice__txt;
	private LinearLayout lineDetails_yuding_layout;
	private boolean zhifuflag = true;// 费用明细判断开关
	private String mobile_data;// 从获取支付信息
	private RequestQueue requestQueue;
	private String verify_sign;
	private SendCityBroadCast sCast;
	private String redPacketIds, hongprice;
	private int code;
	private String message;
	// private long result;

	/******************** 支付宝支付配置 ***************************/
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

	/********* 支付参数 **************/
	private String orderId;// 订单id
	private String orderno;// 订单编号
	private double orderamount = 0.0;// 订单价格
	private int companyid;// 公司id
	private int memberid;// 用户id（购买人）
	private String title = "";// 订单标题
	/*********** 支付 ********************/
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
	private int orderid;
	/********* 微信支付参数 ***************/
	private IWXAPI api;
	private String appid;
	private String noncestr;
	private String packages;
	private String partnerid;
	private String prepayid;
	private String sign;
	private String timestamp;
	private String orderquery;
	private int zhifubaocode;
	private int Wxcode;
	private int order_status;
	private MyApp app;
	private Double totalPrice = 0.0;// 订单总金额
	private Double singleRoomAmount = 0.0;// 单房差总金额
	private Double b2bAmount = 0.0;// b2b总金额
	private int lineCategory;// 0 自发产品；1代销产品（b2b采购的产品）
	private Double couponAmount = 0.0;
	private double orderPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_payinfo);
		MyApp.getInstance().addActivity(this);
		app = (MyApp) getApplication();
		api = WXAPIFactory.createWXAPI(this, "wxf5a649f0f190b79a");
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		orderno = intent.getStringExtra("orderno");
		orderPrice = intent.getDoubleExtra("orderPrice", orderPrice);// 订单金额
		order_status = intent.getIntExtra("order_status", order_status);
		orderid = Integer.parseInt(orderId);
		orderamount = intent.getDoubleExtra("orderamount", orderamount);
		couponAmount = intent.getDoubleExtra("couponAmount", couponAmount);
		companyid = Integer.parseInt(intent.getStringExtra("companyid"));
		memberid = Integer.parseInt(intent.getStringExtra("memberid"));
		title = intent.getStringExtra("title");
		totalPrice = intent.getDoubleExtra("totalPrice", totalPrice);// 订单总金额
		singleRoomAmount = intent.getDoubleExtra("singleRoomAmount",
				singleRoomAmount);// 单房差总金额
		b2bAmount = intent.getDoubleExtra("b2bAmount", b2bAmount);// b2b总金额
		lineCategory = intent.getIntExtra("lineCategory", lineCategory);// :是否实时支付0
																		// 否；1是
		RegisterBroadcast();
		initView();
		initListener();
		requestQueue = Volley.newRequestQueue(getApplicationContext());
		String_GetLineDetails(orderId);

	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		main_title.setText("立即支付");
		order_pay_hongbao_imbt = (ImageButton) findViewById(R.id.order_pay_hongbao_imbt);
		order_zhifubao_imbt = (ImageButton) findViewById(R.id.order_zhifubao_imbt);
		// order_weixin_imbt = (ImageButton)
		// findViewById(R.id.order_weixin_imbt);
		order_totalprice__txt = (TextView) findViewById(R.id.order_totalprice__txt);
		order_youhuiprice__txt = (TextView) findViewById(R.id.order_youhuiprice__txt);
		order_shifuprice__txt = (TextView) findViewById(R.id.order_shifuprice__txt);
		lineDetails_yuding_layout = (LinearLayout) findViewById(R.id.lineDetails_yuding_layout);
		order_totalprice__txt.setText("￥" + (orderPrice + singleRoomAmount));
		order_shifuprice__txt.setText("￥" + orderamount);
		order_youhuiprice__txt.setText("￥" + couponAmount);
		if (!"".equals(couponAmount) && couponAmount != null) {

			order_youhuiprice__txt.setText("￥" + couponAmount);
		} else {
			order_youhuiprice__txt.setText("￥" + 0);
		}
		if (couponAmount != 0) {
			order_pay_hongbao_imbt
					.setBackgroundResource(R.mipmap.zhifu_icon_redbag);
		}
	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		order_zhifubao_imbt.setOnClickListener(new viewClickListener());
		// order_weixin_imbt.setOnClickListener(new viewClickListener());
		lineDetails_yuding_layout.setOnClickListener(new viewClickListener());
		order_pay_hongbao_imbt.setOnClickListener(new viewClickListener());
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();

			} else if (id == R.id.lineDetails_yuding_layout) {

				// if (zhifuflag == true) {
				// 调用支付宝sdk进入支付宝页面、
				String_GetLineDetails(orderId);
				if (code == 200) {
					if (lineCategory == 1) {
						if (orderamount - singleRoomAmount - b2bAmount < 0) {
							if (orderamount > 0) {
								order_shifuprice__txt
										.setText("￥" + orderamount);
							} else {

								order_shifuprice__txt.setText("￥" + 0);
							}
							Toast.makeText(Order_PayinfoActivity.this,
									"很抱歉，你选择的红包超过使用红包限额，请选择小额红包抵扣",
									Toast.LENGTH_SHORT).show();
						} else {
							if (orderamount == 0 || orderamount < 0) {
								orderamount = 0.00;
								totalamount = 0.00;
								bankid = "nopay";

								Post_Order_Payment(webid, website, paystyle,
										totalamount, bankid, isticket,
										memberid, companyid, callbackurl,
										ordertype, comment, aerree, orderid,
										orderno, orderamount);
							} else {
								Post_Order_Payment(webid, website, paystyle,
										totalamount, bankid, isticket,
										memberid, companyid, callbackurl,
										ordertype, comment, aerree, orderid,
										orderno, orderamount);
							}
						}

					} else {
						if (orderamount == 0 || orderamount < 0) {
							orderamount = 0.00;
							totalamount = 0.00;
							bankid = "nopay";

							Post_Order_Payment(webid, website, paystyle,
									totalamount, bankid, isticket, memberid,
									companyid, callbackurl, ordertype, comment,
									aerree, orderid, orderno, orderamount);
						} else {
							Post_Order_Payment(webid, website, paystyle,
									totalamount, bankid, isticket, memberid,
									companyid, callbackurl, ordertype, comment,
									aerree, orderid, orderno, orderamount);
						}
					}

					if (zhifubaocode == 200 && "0".equals(mobile_data)) {

						Intent intent = new Intent(Order_PayinfoActivity.this,
								Payinfo_SuccessActivity.class);
						intent.putExtra("order_status", order_status);
						intent.putExtra("orderid", orderid);
						startActivity(intent);
						finish();
					} else if (zhifubaocode == 200) {
						pay(v);
					}
					// Intent intent1 = new
					// Intent(Order_PayinfoActivity.this,
					// H5PayDemoActivity.class);
					// // Bundle extras = new Bundle();
					// /**
					// * url是测试的网站，在app内部打开页面是基于webview打开的，
					// * demo中的webview是H5PayDemoActivity，
					// *
					// demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现
					// * ， 商户可以根据自己的需求来实现
					// */
					// // String url = "https://www.alipay.com/";
					// // //
					// url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
					// // extras.putString("url", url);
					// // intent.putExtras(extras);
					// startActivity(intent1);
				} else {
					Toast.makeText(Order_PayinfoActivity.this, message,
							Toast.LENGTH_SHORT).show();
				}

				// } else {
				// if (code == 200) {
				// if (orderamount == 0) {
				// orderamount = 0.00;
				// totalamount = 0.00;
				// bankid = "nopay";
				// app.setOrder_status(order_status);
				// app.setOrderid(orderid);
				// Post_Order_WXPayment(webid, website, paystyle,
				// totalamount, bankid, isticket, memberid,
				// companyid, callbackurl, ordertype, comment,
				// aerree, orderid, orderno, orderamount);
				// } else {
				// app.setOrder_status(order_status);
				// app.setOrderid(orderid);
				// Post_Order_WXPayment(webid, website, paystyle,
				// totalamount, bankid, isticket, memberid,
				// companyid, callbackurl, ordertype, comment,
				// aerree, orderid, orderno, orderamount);
				// }
				//
				// } else {
				// Toast.makeText(Order_PayinfoActivity.this, message,
				// Toast.LENGTH_SHORT).show();
				// }
				// }
			} else if (id == R.id.order_zhifubao_imbt) {
				zhifuflag = true;
				order_zhifubao_imbt.setBackgroundResource(R.mipmap.zhifu_sel);
				// order_weixin_imbt
				// .setBackgroundResource(R.drawable.zhifu_icon_unsel);

				// } else if (id == R.id.order_weixin_imbt) {
				// zhifuflag = false;
				// order_zhifubao_imbt
				// .setBackgroundResource(R.drawable.zhifu_icon_unsel);
				// //
				// order_weixin_imbt.setBackgroundResource(R.drawable.zhifu_sel);
			} else if (id == R.id.order_pay_hongbao_imbt) {
				if (couponAmount == 0) {
					Intent intent = new Intent(Order_PayinfoActivity.this,
							RedEnvelopeActivity.class);
					startActivityForResult(intent, WHAT_DTD_SUCCESS);
				} else {
					Toast.makeText(Order_PayinfoActivity.this, "很抱歉你已经使用了红包",
							Toast.LENGTH_SHORT).show();
				}

			}
		}
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
				PayTask alipay = new PayTask(Order_PayinfoActivity.this);
				// 调用支付接口，获取支付结果
				System.out.println("payInfo=====" + payInfo);
				String result = alipay.pay(payInfo, true);
				System.out.println("result=======" + result);

				// PayResult payResult = new PayResult(result);
				// /**
				// *
				// 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				// * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				// * docType=1) 建议商户依赖异步通知
				// */
				// String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				//
				// String resultStatus = payResult.getResultStatus();
				// System.out.println("resultInfo===================="
				// + resultInfo);
				// System.out.println("resultStatus===================="
				// + resultStatus);
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
		String url = "https://www.alipay.com/";
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

	private Handler mHandler = new Handler() {
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
					// Toast.makeText(PayDemoActivity1.this, "支付成功",
					// Toast.LENGTH_SHORT).show();
					String_PostPay_CallbackInfo(resultInfo);
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(Order_PayinfoActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(Order_PayinfoActivity.this, "支付失败",
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

	/**************** 接受红包页面发送的广播 *******************/
	private void RegisterBroadcast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.neter.broadcast.receiver.Buyers_Order");
		sCast = new SendCityBroadCast();
		registerReceiver(sCast, filter);
	}

	public class SendCityBroadCast extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() == "com.neter.broadcast.receiver.Buyers_Order") {
				redPacketIds = intent.getStringExtra("redEnvelope");
				hongprice = intent.getStringExtra("hongprice");
				Post_Order_UseRedPackets(orderId, redPacketIds);

			}
		}
	}

	/*********** 支付宝支付前调用后台生成支付参数 *****************/
	private void Post_Order_Payment(int webid, int website, int paystyle,
			double totalamount, String bankid, int isticket, int memberid,
			int companyid, String callbackurl, int ordertype, String comment,
			String aerree, int orderid, String orderno, Double orderamount) {

		JSONObject js = new JSONObject();
		try {
			js.put("webid", webid);
			js.put("website", website);
			js.put("paystyle", paystyle);
			js.put("totalamount", orderamount);
			js.put("bankid", bankid);
			js.put("isticket", isticket);
			js.put("memberid", memberid);
			js.put("companyid", companyid);
			js.put("callbackurl", URL.pay_C_HUITIAOURL);
			js.put("ordertype", ordertype);
			js.put("comment", comment);
			JSONArray array = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("aerree", aerree);
			json.put("orderid", orderid);
			json.put("orderno", orderno);
			json.put("orderamount", orderamount);
			json.put("ordertype", 4);
			array.put(json);
			js.put("details", array);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, URL.pay_URL + "/payment", js,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out
								.println("response======支付返回数据================="
										+ response.toString());
						Order_PayinfoEntity order_PayinfoEntity = ParseUtils
								.getOrderPayinfo(response.toString());

						mobile_data = order_PayinfoEntity.getResult();
						zhifubaocode = order_PayinfoEntity.getCode();

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

		requestQueue.add(jsonObjectRequest);
	}

	/*********** 微信支付支付前调用后台生成支付参数 *****************/
	private void Post_Order_WXPayment(int webid, int website, int paystyle,
			double totalamount, String bankid, int isticket, int memberid,
			int companyid, String callbackurl, int ordertype, String comment,
			String aerree, int orderid, String orderno, Double orderamount) {

		JSONObject js = new JSONObject();
		try {
			js.put("webid", webid);
			js.put("website", website);
			js.put("paystyle", paystyle);
			// orderamount
			js.put("totalamount", 0.01);
			js.put("bankid", "wxpaymobile");
			js.put("isticket", isticket);
			js.put("memberid", memberid);
			js.put("companyid", companyid);
			js.put("callbackurl", URL.pay_C_HUITIAOURL);
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
			System.out.println("566==支付json拼装==============" + js.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, URL.pay_URL + "/payment", js,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println("response======微信支付返回数据====="
								+ response.toString());
						WxPay order_WXPayinfoEntity = ParseUtils
								.PayInfo(response.toString());
						System.out
								.println("order_PayinfoEntity.getAppid()======="
										+ order_WXPayinfoEntity.getAppid());
						appid = order_WXPayinfoEntity.getAppid();
						noncestr = order_WXPayinfoEntity.getNoncestr();
						packages = order_WXPayinfoEntity.getPackages();
						partnerid = order_WXPayinfoEntity.getPartnerid();
						prepayid = order_WXPayinfoEntity.getPrepayid();
						sign = order_WXPayinfoEntity.getSign();
						timestamp = order_WXPayinfoEntity.getTimestamp();
						orderquery = order_WXPayinfoEntity.getOrderquery();
						app.setOrderquery(orderquery);
						if (!"".equals(appid) && appid != null) {
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
								// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
								api.sendReq(req);
							} catch (Exception e) {
								Log.e("PAY_GET", "异常：" + e.getMessage());
								Toast.makeText(Order_PayinfoActivity.this,
										"异常：" + e.getMessage(),
										Toast.LENGTH_SHORT).show();
							}

						} else {
							Toast.makeText(Order_PayinfoActivity.this,
									"获取支付信息失败", Toast.LENGTH_SHORT).show();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

		requestQueue.add(jsonObjectRequest);
	}

	/*********** 支付前校验红包是否可用 *****************/
	private void Post_Order_UseRedPackets(String orderId, String redPacketIds) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		map.put("redPacketIds", redPacketIds);
		System.out.println("map========椒盐红包的=====" + map.toString());

		VolleyUtils.requestString_Post((HashMap<String, String>) map, URL.DEBUG
				+ URL.ORDER_USEREDPACKETS, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "登录成功了！！！" + response.toString());
				System.out.println("response====校验红包返回数据======"
						+ response.toString());
				// {"code":"200","message":"处理成功","result":"0.0"}
				try {
					JSONObject object = new JSONObject(response.toString());
					int code = object.getInt("code");
					// message = object.getString("message");
					// if(code==200){
					// // long result = object.getLong("result");
					// }

					// private long totalPrice;// 订单总金额
					// private long singleRoomAmount;// 单房差总金额
					// private long b2bAmount;// b2b总金额
					// private int isPay;// :是否实时支付 0 否；1 是
					// if(code==200){
					order_youhuiprice__txt.setText("￥" + hongprice);
					orderamount = orderamount - Double.valueOf(hongprice);

					order_totalprice__txt.setText("￥"
							+ (orderPrice + singleRoomAmount));
					System.out.println("orderamount========" + orderamount);
					System.out.println("singleRoomAmount========"
							+ singleRoomAmount);
					System.out.println("b2bAmount========" + b2bAmount);
					System.out.println("fffffffffffffff-------------"
							+ (orderamount - singleRoomAmount - b2bAmount));
					if ((orderamount - singleRoomAmount - b2bAmount) <= 0) {
						System.out.println("1111111111111111111111111111");
						if (orderamount > 0) {
							order_shifuprice__txt.setText("￥" + orderamount);
						} else {

							order_shifuprice__txt.setText("￥" + 0);
						}

					} else if (orderamount < 0) {
						order_shifuprice__txt.setText("￥" + 0);
					} else {
						order_shifuprice__txt.setText("￥" + orderamount);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				Log.i(TAG, "登录失败" + error.getMessage());
			}
		});
	}

	/************ 根据ORDERID订单id获取该订单是否可以支付 *************/
	private void String_GetLineDetails(String orderId) {
		VolleyUtils.requestString_Get(URL.DEBUG + URL.ORDER_GETPAYSTATUS
				+ orderId, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
//				System.out.println("response====校验订单是否可以支付返回数据======="
//						+ response.toString());
				try {
					JSONObject object = new JSONObject(response.toString());
					code = object.getInt("code");
					message = object.getString("message");

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				Log.i(TAG, "====没有拿到验证====" + error.getMessage());
			}
		});
	}

	/*********** 支付宝支付回调 * *****************/
	private void String_PostPay_CallbackInfo(String resultInfo) {

		JSONObject js = new JSONObject();
		try {
			js.put("result", resultInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST,URL.ALIpay_HUITIAOURL,
				js, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println("response=========支付回调=="
								+ response.toString());
						Order_PayinfoEntity order_PayinfoEntity = ParseUtils
								.getOrder_UpdateInfo(response.toString());
						int code = order_PayinfoEntity.getCode();
						if (code == 200) {
							Intent intent = new Intent(
									Order_PayinfoActivity.this,
									Payinfo_SuccessActivity.class);
							intent.putExtra("order_status", order_status);
							intent.putExtra("orderid", orderid);
							startActivity(intent);
							Toast.makeText(Order_PayinfoActivity.this, "支付成功",
									Toast.LENGTH_SHORT).show();
							finish();
						} else {
							// 判断resultStatus 为非"9000"则代表可能支付失败
							// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）

							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(Order_PayinfoActivity.this, "支付失败",
									Toast.LENGTH_SHORT).show();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

		requestQueue.add(jsonObjectRequest);

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
		unregisterReceiver(sCast);
	}
	
	
}
