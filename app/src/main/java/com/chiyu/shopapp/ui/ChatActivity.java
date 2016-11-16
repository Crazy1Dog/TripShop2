package com.chiyu.shopapp.ui;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.fragment.ChatFragment;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ShareUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;


/**
 * 聊天页面，需要fragment的使用{@link #}
 *
 */
public class ChatActivity extends FragmentActivity{
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;
    String flag;
    Button  btn_order;
    String title;
    String price;
    String photopath;
    String dateId;
    String dateList;
    String lineId;
    int photoCount;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        if (!EMClient.getInstance().isConnected() || !DemoHelper.getInstance().isLoggedIn()){
        	EMLogserver(ShareUtil.getString("huanxinUserName"),ShareUtil.getString("huanxinpwd"));
        }
        setContentView(R.layout.em_activity_chat);
        MyApp.getInstance().addActivity(this);
        activityInstance = this;
        //聊天人或群id
        
        toChatUsername = getIntent().getExtras().getString("userId");
        flag = getIntent().getExtras().getString("flag");
        title = getIntent().getExtras().getString("title");
        System.out.println("title=======liaotian========="+title);
        price = getIntent().getExtras().getString("price");
        photopath = getIntent().getExtras().getString("photopath");
        dateId = getIntent().getExtras().getString("dateId");
        dateList = getIntent().getExtras().getString("dateList");
        lineId = getIntent().getExtras().getString("lineId");
        photoCount = getIntent().getExtras().getInt("photoCount");
        
        //聊天内容区域    可以直接new EaseChatFratFragment使用		
        chatFragment = new ChatFragment();
        //传入参数
        Bundle data = new Bundle();
        data.putString("flag", flag);
        data.putString("title", title);
        System.out.println("title=======liaotianFragment 1========="+title);
        data.putString("price", price);
        data.putString("photopath", photopath);
        data.putString("dateId", dateId);
        data.putString("dateList", dateList);
        data.putString("lineId", lineId);
        data.putInt("photoCount", photoCount);
        chatFragment.setArguments(data);
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();    
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
					}

					@Override
					public void onProgress(int progress, String status) {
					}

					@Override
					public void onError(final int code, final String message) {
						runOnUiThread(new Runnable() {
							public void run() {
							}
						});
					}
				});
	}
	@Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }
    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
	
}
