package com.chiyu.shopapp.ui;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ShareUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.controller.EaseUI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
public class LoginActivity1 extends Activity implements OnClickListener{
	private Button loginButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 如果登录成功过，直接进入主页面
		setContentView(R.layout.activity_login1);
//		MyApp.getInstance().addActivity(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		loginButton = (Button) findViewById(R.id.bt_login);
		Window lp = getWindow();
		lp.setGravity(Gravity.CENTER);
		lp.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		loginButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.bt_login:
		EMLogserver(ShareUtil.getString("huanxinUserName"),ShareUtil.getString("huanxinpwd"));
		break;
		}
	}

	public void EMLogserver(String currentUsername, String currentPassword) {
		currentUsername = ShareUtil.getString("huanxinUserName");
		currentPassword = ShareUtil.getString("huanxinpwd");
		DemoHelper.getInstance().setCurrentUserName(currentUsername);
		EMClient.getInstance().login(currentUsername, currentPassword,
				new EMCallBack() {

					@Override
					public void onSuccess() {
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
					
							Intent intent = new Intent(LoginActivity1.this,
									MainActivity.class);
							intent.putExtra("login1", true);
							startActivity(intent);

							finish();
						

					}

					@Override
					public void onProgress(int progress, String status) {
					}

					@Override
					public void onError(final int code, final String message) {
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


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}

}
