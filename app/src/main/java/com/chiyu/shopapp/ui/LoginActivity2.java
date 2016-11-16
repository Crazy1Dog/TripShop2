package com.chiyu.shopapp.ui;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.Security;
import com.chiyu.shopapp.bean.UserEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.exceptions.HyphenateException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity2 extends Activity implements OnClickListener{
	private static final String TAG = "tripshop";
	private static final String TAG8 = "tripshop8";
	private Button securityButton;
	private Button loginButton;
	private Button backButton;
	private EditText telephoneEditText;
	private EditText securityEditText;
	private Handler handler;
	private String username;
	private String pwd;
	private String currentUsername;
	private String currentPassword;
	private Security security;
	private MyApp myApp;
	// private String userId;
	private String flag;
	private boolean IMIsClick;
	private String title;
	private String photopath;
	private String price;
	private String dateId;
	private String dateList;
	private String lineId;
	private String photoCount;
	private String tag;
	String personTouxiangUrl;
	private String messageFragment;//点击消息是判断是否登录的tag
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 如果登录成功过，直接进入主页面
		setContentView(R.layout.activity_login);
		MyApp.getInstance().addActivity(this);
		myApp = (MyApp) getApplication();
		Intent intent = getIntent();
//		  userId = intent.getStringExtra("memberEntityMobile");
		  flag = intent.getStringExtra("flag");
		  tag = intent.getStringExtra("tag");
		  IMIsClick = intent.getBooleanExtra("IMIsClick", false);
		  title = intent.getStringExtra("title");
		  photopath = intent.getStringExtra("photopath");
		  price = intent.getStringExtra("price");
		  dateId = intent.getStringExtra("dateId");
		  dateList = intent.getStringExtra("dateList");
		  lineId = intent.getStringExtra("lineId");
		  photoCount = intent.getStringExtra("photoCount");
		  messageFragment = intent.getStringExtra("messageFragment");
		
		initView();
		telephoneEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				securityButton
						.setBackgroundResource(R.drawable.security_button_shape2);
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!"".equals(telephoneEditText.getText().toString().trim())) {
					securityButton
							.setBackgroundResource(R.drawable.security_button_shape2);
					
				} else {
					securityButton
							.setBackgroundResource(R.drawable.security_button_shape1);
					
				}
			}
		});
		securityEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
				loginButton
						.setBackgroundResource(R.drawable.button_login_shape2);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!"".equals(securityEditText.getText().toString().trim())) {
					
					loginButton
							.setBackgroundResource(R.drawable.button_login_shape2);
				} else {
					
					loginButton
							.setBackgroundResource(R.drawable.button_login_shape1);
				}
			}
		});
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);

				securityButton.setText((msg.arg1 - 1) + "s");
				if (msg.arg1 == 1) {
					securityButton.setText("重新获取");
				}

			}
		};
		securityButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		loginButton = (Button) findViewById(R.id.bt_login);
		securityButton = (Button) findViewById(R.id.bt_security);
		backButton = (Button) findViewById(R.id.bt_login_back);
		telephoneEditText = (EditText) findViewById(R.id.et_telephone);
		securityEditText = (EditText) findViewById(R.id.et_security);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_security:
			/**
			 * 提交方式：GET 接口地址：/api/receive/uc/sendvcode/{weid}/{mobile}/{action}
			 * 参数列表：[ weid:微店id--必填 mobile:手机号码--必填 action:动作类型 0：注册，
			 * 1：登录，2：忘记密码，3:绑定手机--必填 ]
			 */
			if ("".equals(telephoneEditText.getText().toString().trim())) {
				Toast.makeText(LoginActivity2.this, "请正确输入号码", 0).show();
				return;
			} else if (!isMobileNOs(telephoneEditText.getText().toString())) {
				Toast.makeText(LoginActivity2.this, "请输入正确手机号码", 0).show();
				return;
			}
			ShareUtil.putString("userMobile", telephoneEditText.getText().toString().trim());
			securityButton.setText("已发送");
			CountDownTimer downTimer = new CountDownTimer(60000, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					Message message = Message.obtain();
					message.arg1 = (int) (millisUntilFinished / 1000);
					handler.sendMessage(message);
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
				}
			}.start();
			VolleyUtils.requestString_Get(
					URL.DEBUG + URL.GET_SECURITY_CODE
							+ ShareUtil.getString("receiveguestId") + "/"
							+ telephoneEditText.getText().toString().trim()
							+ "/" + "1", new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.i(TAG, "验证码来了！！！" + response.toString());
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG, "====没有拿到验证====" + error.getMessage());
						}
					});
			break;
		case R.id.bt_login:
			/**
			 * 提交方式：POST 接口地址：/api/receive/uc/login 参数列表：[ weid :微店id--必填 type
			 * :登录方式 0:手机号码登录，1:用户名密码登录--必填 userName :用户名--当 type=1时必填 password
			 * :密码--当 type=1时必填 mobile:手机号码--当 type=0时必填 vcode :验证码--当 type=0时必填
			 * loginsource :登录来源--当 loginsource=app时表明从app接入（app登录时必填） ]
			 */
			if ("".equals(telephoneEditText.getText().toString().trim())
					|| "".equals(securityEditText.getText().toString().trim())) {
				Toast.makeText(LoginActivity2.this, "手机号码或验证码为空", 0).show();
				return;
			}
			loginButton.setText("登录中...");
			Map<String, String> map = new HashMap<String, String>();
			map.put("weid", ShareUtil.getString("receiveguestId"));
			map.put("type", "0");
			map.put("mobile", telephoneEditText.getText().toString().trim());
			map.put("vcode", securityEditText.getText().toString().trim());
			map.put("loginsource", "app");
			map.put("invitationCode", ShareUtil.getString("invitationCode"));
			VolleyUtils.requestString_Post((HashMap<String, String>) map,
					URL.DEBUG + URL.lOGIN, new OnRequest() {

						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							try {
								JSONObject object = new JSONObject(response
										.toString());
								int code = object.getInt("code");
								if (code == 200) {
									ParseUtils.getUserIdAndToken(response
											.toString());
									UserEntity result = ParseUtils
											.getUserIdAndToken(response);
									String userId = result.getUserId();
									ShareUtil.putString("userId", userId);
									ShareUtil.putString("token",
											result.getToken());// 判断是否是已存在的客户在开屏页完成
									ShareUtil.putString("userMobile", telephoneEditText.getText().toString().trim());

//									ShareUtil.putString("token",
//											result.getToken());
//									ShareUtil.putString("userId", userId); // 判断是否是已存在的客户在开屏页完成

									// 判断是否是已存在的客户在开屏页完成
									myApp.setPhotopath(result.getPhotoPath());
									personTouxiangUrl = result.getPhotoPath();
									ShareUtil.putString("photoPath",result.getPhotoPath());
//									ShareUtil.putString("photoPath",
//											result.getPhotoPath());
									myApp.setUsername(telephoneEditText
											.getText().toString().trim());
									myApp.setMobile(telephoneEditText.getText()
											.toString().trim());
									myApp.setToken(result.getToken());
									myApp.setUserId(result.getUserId());

									Log.e("usernameusernameusername",
											"头像地址"+result.getPhotoPath()+","+"用户ID"+ result.getUserId()+","+"用户名"
													+ telephoneEditText
															.getText()
															.toString().trim());
									pwd = telephoneEditText.getText().toString().trim();
									username =ShareUtil.getString("invitationCode")+telephoneEditText.getText().toString().trim();

									if ("".equals(username) || username == null) {

									} else {
										ShareUtil.putString("currentPhone",
												username);
									}
									ShareUtil.putString("token",
											result.getToken());
									register();
									ShareUtil.putString("huanxinUserName", username);
									ShareUtil.putString("huanxinpwd", pwd);
									EMLogserver(ShareUtil.getString("huanxinUserName"),ShareUtil.getString("huanxinpwd"));
								} else {
									Toast.makeText(LoginActivity2.this,
											object.getString("message"), 0)
											.show();
									loginButton.setText("立即登录");
									// 判断是否是已存在的客户在开屏页完成
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.i(TAG, "登录失败" + error.getMessage());
							loginButton.setText("立即登录");

						}
					});

			break;
		case R.id.bt_login_back:
			finish();
		default:
			break;
		}
	}

	public void EMLogserver(String currentUsername, String currentPassword) {
		currentUsername = username;
		currentPassword = pwd;
		DemoHelper.getInstance().setCurrentUserName(currentUsername);
		EMClient.getInstance().login(currentUsername, currentPassword,
				new EMCallBack() {

					@Override
					public void onSuccess() {
						Log.d(TAG, "login: onSuccess");
						// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
						// ** manually load all local groups and
						EMClient.getInstance().groupManager().loadAllGroups();
						EMClient.getInstance().chatManager()
								.loadAllConversations();

						// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
						boolean updatenick = EMClient.getInstance()
								.updateCurrentUserNick(
										MyApp.currentUserNick.trim());
						if (!updatenick) {
							Log.e("LoginActivity",
									"update current user nick fail");
						}
						// 异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
						DemoHelper.getInstance().getUserProfileManager()
								.asyncGetCurrentUserInfo();
						// 进入主页面
						if (IMIsClick) {
							Intent intent = new Intent(LoginActivity2.this,
									ChatActivity.class);
							intent.putExtra("userId",ShareUtil.getString("contactname"));
							intent.putExtra("userId",
									ShareUtil.getString("username"));
							intent.putExtra("flag", flag);
							intent.putExtra("title", title);
							intent.putExtra("photopath", photopath);
							intent.putExtra("price", price);
							intent.putExtra("dateId", dateId);
							intent.putExtra("dateList", dateList);
							intent.putExtra("lineId", lineId);
							intent.putExtra("photoCount", photoCount);
							startActivity(intent);
						}else if(!"".equals(tag) && "PersonalFragment".equals(tag) && tag != null){
							ShareUtil.putString("photoPath",personTouxiangUrl);
							ShareUtil.putString("userMobile", telephoneEditText.getText().toString().trim());
							finish();
						}else {
							Intent intent = new Intent(LoginActivity2.this,
									MainActivity.class);
							intent.putExtra("login1", true);
							startActivity(intent);

							finish();
						}

					}

					@Override
					public void onProgress(int progress, String status) {
						Log.d(TAG, "login: onProgress");
					}

					@Override
					public void onError(final int code, final String message) {
						Log.d(TAG, "login: onError: " + code);
						runOnUiThread(new Runnable() {
							public void run() {
//								Toast.makeText(
//										getApplicationContext(),
//										getString(R.string.Login_failed)
//												+ message, Toast.LENGTH_SHORT)
//										.show();
							}
						});
					}
				});
	}

	public void register() {
		new Thread(new Runnable() {
			public void run() {
				try {
					// 调用sdk注册方法
					EMClient.getInstance().createAccount(username, pwd);
					runOnUiThread(new Runnable() {
						public void run() {
							// 保存用户名
							DemoHelper.getInstance().setCurrentUserName(
									username);
//							Toast.makeText(
//									getApplicationContext(),
//									getResources().getString(
//											R.string.Registered_successfully),
//									0).show();
						}
					});
				} catch (final HyphenateException e) {
					runOnUiThread(new Runnable() {
						public void run() {
							int errorCode = e.getErrorCode();
							if (errorCode == EMError.NETWORK_ERROR) {
//								Toast.makeText(
//										getApplicationContext(),
//										getResources().getString(
//												R.string.network_anomalies),
//										Toast.LENGTH_SHORT).show();
							} else if (errorCode == EMError.USER_ALREADY_EXIST) {
//								Toast.makeText(
//										getApplicationContext(),
//										getResources().getString(
//												R.string.User_already_exists),
//										Toast.LENGTH_SHORT).show();
							} else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
//								Toast.makeText(
//										getApplicationContext(),
//										getResources()
//												.getString(
//														R.string.registration_failed_without_permission),
//										Toast.LENGTH_SHORT).show();
							} else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
//								Toast.makeText(
//										getApplicationContext(),
//										getResources().getString(
//												R.string.illegal_user_name),
//										Toast.LENGTH_SHORT).show();
							} else {
//								Toast.makeText(
//										getApplicationContext(),
//										getResources().getString(
//												R.string.Registration_failed)
//												+ e.getMessage(),
//										Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
			}
		}).start();
	}

//	/**
//	 * 验证手机格式
//	 */
	public static boolean isMobileNOs(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][345870]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}

}
