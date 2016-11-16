package com.chiyu.shopapp.ui;

import java.util.HashMap;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.chiyu.shopapp.view.CircleImageView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
 * CallBackModelDTO update(java.lang.String token, java.lang.String userId, java.lang.String userName, java.lang.String photoPath, java.lang.String gender, java.lang.String mobile, java.lang.String vcode, javax.servlet.http.HttpServletRequest request) 

  描述：C客修改基本信息接口
 ---------------------------------------- 
 ----------
  提交方式：PUT 
  接口地址：/api/receive/uc/update
  参数列表：[ 
   token :登录token--必填
   userId:C客id--必填
   userName:用户名--必填
   mobile:手机号码--必填
  ] 
 ---------------------------------------- 
 ---------- 
  数据格式：JSON 
  展现形式：{message:'通知信息',result:数据,code：500|200} 
  返回参数列表：[ 
   message ：'通知信息' 
   result ：'数据' 
   code ：状态码 
   500 失败 
   200 成功 
   ]

 * */
public class ChangeActivity extends Activity implements OnClickListener{
	ImageView btn_title_back;
	CircleImageView iv_photo;
	TextView tv_phone_txt;
	private String currentPhone;
	private String username;
	private Dialog dialog;
	private MyApp app;
	private String photoUrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_activity);
		MyApp.getInstance().addActivity(this);
		app = (MyApp) getApplication();
		username = app.getUsername();
		currentPhone = app.getMobile();
		photoUrl = app.getPhotopath();
		initView();
		initEvent();
	}

	private void initEvent() {
		btn_title_back.setOnClickListener(this);
		tv_phone_txt.setOnClickListener(this);
	}

	private void initView() {
		btn_title_back = (ImageView) findViewById(R.id.btn_title_back);
		iv_photo = (CircleImageView) findViewById(R.id.iv_photo);
		tv_phone_txt = (TextView) findViewById(R.id.tv_phone_txt);
		
	}
	@Override
	protected void onStart() {
		super.onStart();
		currentPhone = ShareUtil.getString("currentPhone");
		if(currentPhone != null){
    		if (currentPhone.equals(EMClient.getInstance().getCurrentUser())) {
    			tv_phone_txt.setText(EMClient.getInstance().getCurrentUser());
    			EaseUserUtils.setUserNick(currentPhone, tv_phone_txt);
    		} else {
    			tv_phone_txt.setText(currentPhone);
//    			EaseUserUtils.setUserNick(currentPhone, tv_phone_txt);
//    			asyncFetchUserInfo(currentPhone);
    		}
		}
		
			VolleyUtils.requestImage(URL.IMAGE_DEBUG + ShareUtil.getString("photoPath"), iv_photo, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
		
		if(currentPhone == null){
			tv_phone_txt.setText("");
		}else{
			tv_phone_txt.setText(currentPhone);
		}
	}
	public void asyncFetchUserInfo(String currentPhone){
		DemoHelper.getInstance().getUserProfileManager().asyncGetUserInfo(currentPhone, new EMValueCallBack<EaseUser>() {
			
			@Override
			public void onSuccess(EaseUser user) {
				if (user != null) {
				    DemoHelper.getInstance().saveContact(user);
				    if(isFinishing()){
				        return;
				    }
				    tv_phone_txt.setText(user.getNick());
				}
			}
			
			@Override
			public void onError(int error, String errorMsg) {
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.btn_title_share:
//			ShareUtil.putString("username", currentName);
//    		ShareUtil.putString("phone", currentPhone);
//			Intent intent = new Intent(this,MainActivity.class);
//			startActivity(intent);
//			Toast.makeText(this, "保存完成", Toast.LENGTH_SHORT).show();
//			break;
		case R.id.btn_title_back:
			showShareDialog();
			break;
//		case R.id.tv_name_txt:
//			getName();
//			break;
		case R.id.tv_phone_txt:
			getPhone();
			break;
		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		username = app.getUsername();
//		currentPhone = app.getMobile();
		photoUrl = app.getPhotopath();
	}
	private void getPhone() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String nickString = editText.getText().toString();
						app.setMobile(nickString);
						ShareUtil.putString("currentPhone", nickString);
						if (TextUtils.isEmpty(nickString)) {
							Toast.makeText(ChangeActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
							return;
						}
						tv_phone_txt.setText(nickString);
						updateRemoteNick(nickString);
						EaseUserUtils.setUserNick(nickString, tv_phone_txt);
						asyncFetchUserInfo(nickString);
					}
				})
				.setNegativeButton(R.string.dl_cancel, null).show();
		
	}

	private void getName() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String nickString = editText.getText().toString();
						app.setUsername(nickString);
						if (TextUtils.isEmpty(nickString)) {
							Toast.makeText(ChangeActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
							return;
						}
						updateRemoteNick(nickString);
					}
				})
				.setNegativeButton(R.string.dl_cancel, null).show();
	}

	private void showShareDialog() {
		 LayoutInflater inflater = LayoutInflater.from(this);
	        RelativeLayout layout = (RelativeLayout) inflater.inflate(
	                R.layout.my_dialog_share, null);
	        dialog = new AlertDialog.Builder(ChangeActivity.this).create();
	        dialog.show();
	        dialog.getWindow().setContentView(layout);
	        TextView tv_title = (TextView) layout
	                .findViewById(R.id.tv_title);
	        Button btn_cancle = (Button) layout
	                .findViewById(R.id.btn_cancle);
	        btn_cancle.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
//	                startActivity(MainActivity.class);
	                dialog.dismiss();
	                finish();

	            }
	        });
	        Button btn_ok = (Button) layout
	                .findViewById(R.id.btn_ok);
	        btn_ok.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View arg0) {
//	        		Intent intent2 = new Intent();
//	    			intent2.setAction("xinxihuichuan");
//	    			intent2.putExtra("username", tv_name_txt.getText().toString());
//	    			intent2.putExtra("mobile", tv_phone_txt.getText().toString());
//	    			sendBroadcast(intent2);
//	            	app.setUsername(app.getUsername());
//	            	app.setMobile(app.getMobile());
	            	/*
	            	 * 提交用户信息
					  *  参数列表：[ 
				   token :登录token--必填
				   userId:C客id--必填
				   userName:用户名--必填
				   photoPath:头像路径--必填
				   gender:性别--必填
				   mobile:手机号码--必填
				   vcode:验证码,老用户绑定手机时必传
				  ] 

	            	 * **/
	            	HashMap<String, String>map = new HashMap<String, String>();
	            	map.put("token", ShareUtil.getString("token"));
	            	map.put("userId", ShareUtil.getString("userId"));
	            	map.put("userName",ShareUtil.getString("currentPhone"));
	            	map.put("photoPath","");
	            	map.put("gender", "");
	            	map.put("mobile", ShareUtil.getString("currentPhone"));
	            	map.put("vcode", "");
	            	Log.e("提交的个人信息", app.getToken()+"===" +  app.getUserId()+"===" +app.getUsername() +"===" + app.getMobile());
	            	
	            	VolleyUtils.requestString_PUT(map, URL.DEBUG + URL.COMMIT_PERSONAL, new OnRequest() {
						
						@Override
						public void response(String url, String response) {
							Toast.makeText(ChangeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
							Log.e("提交的个人信息", response.toString());
							Log.e("urlurlurlurl", url);
						}
						
						@Override
						public void errorResponse(String url, VolleyError error) {
							Log.e("提交的个人信息", error.toString() + error);
							Log.e("urlurlurlurl", url);
						}
					});
	                dialog.dismiss();
	                finish();
	            }
	        });
	        dialog.setCancelable(false);
		
	}

	private void updateRemoteNick(final String nickName) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				boolean updatenick = DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(nickName);
				if (ChangeActivity.this.isFinishing()) {
					return;
				}
				if (!updatenick) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(ChangeActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
									.show();
						}
					});
				} else {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(ChangeActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
									.show();
							tv_phone_txt.setText(nickName);
						}
					});
				}
			}
		}).start();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
	
}
