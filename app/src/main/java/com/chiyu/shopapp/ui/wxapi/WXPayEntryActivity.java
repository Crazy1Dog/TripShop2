package com.chiyu.shopapp.ui.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.Order_PayinfoEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.ui.Constants;
import com.chiyu.shopapp.ui.Payinfo_SuccessActivity;
import com.chiyu.shopapp.ui.Payinfo_WxSuccessActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;
	private String orderquery;
	private MyApp app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);
		app = (MyApp) getApplication();
		orderquery = app.getOrderquery();
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		System.out.println("支付通知----------------------------" + resp.errCode);
		if (resp.errCode == 0) {
			System.out.println("orderquery====回调======"+orderquery);
			String_PostPay_WxCallbackInfo();

		} else {
			finish();
		}

		// if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle(R.string.app_tip);
		// builder.setMessage(getString(R.string.pay_result_callback_msg,
		// String.valueOf(resp.errCode)));
		// builder.show();
		// }
		// System.out.println("resp.errCode============" + resp.errCode);
		// if (resp.errCode == 0) {
		//
		// }
	}

	/************ 微信支付回调 *************/
	private void String_PostPay_WxCallbackInfo() {
		VolleyUtils.requestString_Get(orderquery, new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				System.out.println("response=========支付回调返回信息=="
						+ response.toString());
				Order_PayinfoEntity order_PayinfoEntity = ParseUtils
						.getOrder_UpdateInfo(response.toString());
				String code = order_PayinfoEntity.getCode()+"";
				System.out.println("code===================================="+code);
				if ("SUCCESS".equals(code)) {
					Intent intent = new Intent(WXPayEntryActivity.this,
							Payinfo_WxSuccessActivity.class);
					startActivity(intent);
					Toast.makeText(WXPayEntryActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）

					// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
					Toast.makeText(WXPayEntryActivity.this, "支付失败",
							Toast.LENGTH_SHORT).show();
					finish();

				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				Log.i(TAG, "====没有拿到验证====" + error.getMessage());
			}
		});
	}

}