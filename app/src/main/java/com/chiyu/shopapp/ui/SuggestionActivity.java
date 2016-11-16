package com.chiyu.shopapp.ui;

import com.hyphenate.easeui.controller.EaseUI;
import com.chiyu.shopapp.constants.MyApp;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestionActivity extends Activity implements OnClickListener{

	EditText edOption;
	Button btn_send;
	private final String TAG = "GetOpinionActivity";
	TextView text_num_size;
	Button btn_title_back;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.suggestion_activity);
			MyApp.getInstance().addActivity(this);
			initViews();
			initEvents();
		}
		protected void initViews() {
			edOption = (EditText) findViewById(R.id.ed_option);
			text_num_size = (TextView) findViewById(R.id.text_num_size);
			btn_title_back = (Button) findViewById(R.id.btn_title_back);
			btn_send = (Button) findViewById(R.id.btn_send);
			
		}
		protected void initEvents() {
			btn_send.setOnClickListener(this);
			edOption.addTextChangedListener(numWatcher);
			btn_title_back.setOnClickListener(this);
		}

		TextWatcher numWatcher = new TextWatcher() {
			private CharSequence temp;
			private int editStart;
			private int editEnd;

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				temp = s;
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				editStart = edOption.getSelectionStart();
				editEnd = edOption.getSelectionEnd();
				int num = 500 - temp.length();
				text_num_size.setText("还剩" + num + "/500个字符");
				if (temp.length() > 500) {
					s.delete(editStart - 1, editEnd);
					int tempSelection = editStart;
					edOption.setText(s);
					edOption.setSelection(tempSelection);
				}
			}
		};

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_send:
				if (!TextUtils.isEmpty(edOption.getText().toString())) {
					feedback();
				} else {
					Toast.makeText(this, "您还未输入呢！", Toast.LENGTH_SHORT).show();
				}

				break;
			case R.id.btn_title_back:
				finish();
				break;
			}
		}

		private void feedback() {
//			if (!HaolinongUtils.isNetworkConnected(context)) {
//				toast(R.string.str_errcode132);
//			} else {
//				RequestParams params = new RequestParams();
//				params.addBodyParameter("act", "feedback");
//				params.addBodyParameter("content", edOption.getText().toString());
//				params.addBodyParameter("dev_type", "安卓");
//				MyApplication.httpUtils.send(HttpMethod.POST,
//						Haolinong.HAOLINNONG_NETWORK,params,
//						new RequestCallBack<String>() {
//
//							@Override
//							public void onFailure(HttpException arg0,
//												  String arg1) {
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onSuccess(ResponseInfo<String> responseInfo) {
//								jsonString = responseInfo.result.toString();
//								Log.e("===", "bjsbean------------->" + jsonString);
//								try {
//									Feedback feedback = json.fromJson(jsonString, Feedback.class);
//									toast(R.string.str_errcode15);
//									startActivity(MainActivity.class);
//									defaultFinish();
//								}
//								catch(Exception e){
//									e.printStackTrace();;
//								}
//							}
//						});
//			}
		}

		public void onResume() {
			super.onResume();
			MobclickAgent.onEvent(SuggestionActivity.this, "_GetOpinionActivity");
			MobclickAgent.onResume(this);
		}

		public void onPause() {
			super.onPause();
			MobclickAgent.onResume(this);
		}
		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			EaseUI.getInstance().getNotifier().reset();
		}
}
