package com.chiyu.shopapp.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.StoreEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

public class InvitationActivity extends Activity {
	private static final String TAG = "tripshop";
	private Button invitationButton;
	private EditText invitationEditText;
	/**
	 * 提交邀请码
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invitation);
		MyApp.getInstance().addActivity(this);
		initView();
		invitationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				VolleyUtils.requestString_Get(URL.DEBUG + URL.INVIT_CODE + invitationEditText.getText().toString().trim(),new OnRequest() {
					
					@Override
					public void response(String url, String response) {
						// TODO Auto-generated method stub
						if(response != null){
							Log.i(TAG, response.toString());
							try {
								JSONObject object = new JSONObject(response.toString());
								int code = object.getInt("code");
								if(code != 200){
									Toast.makeText(InvitationActivity.this, "邀请码错误", 0).show();
									return;
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							StoreEntity storeEntity = ParseUtils.parseStoreMessage(response.toString());
							/**
							 * {"code":"200","message":"操作成功","result":{"memberId":"351190","companyId":"305608","receiveguestId":"4130"}}
							 */
							ShareUtil.putString("memberId", storeEntity.getMemberId());
							ShareUtil.putString("companyId", storeEntity.getCompanyId());
							ShareUtil.putString("receiveguestId", storeEntity.getReceiveguestId());
							ShareUtil.putString("Photopath", storeEntity.getPhotopath());
							ShareUtil.putString("invitationCode",invitationEditText.getText().toString().trim());
							Intent intent = new Intent();
							intent.setClass(InvitationActivity.this, MainActivity.class);
      							startActivity(intent);
							finish();
						}
					}
					@Override
					public void errorResponse(String url, VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(InvitationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
						Log.i(TAG, "请求失败了！！！"+error.getMessage());
					}
					
				});
				
			}
		});
	}
	private void initView() {
		// TODO Auto-generated method stub
		invitationButton = (Button) findViewById(R.id.login_log_bt);
		invitationEditText = (EditText) findViewById(R.id.et_invitation);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
	
}

