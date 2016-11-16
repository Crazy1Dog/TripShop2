package com.chiyu.shopapp.ui;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.InviteMessage;
import com.chiyu.shopapp.bean.VersionEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.db.InviteMessgeDao;
import com.chiyu.shopapp.db.UserDao;
import com.chiyu.shopapp.fragment.FindLineFragment;
import com.chiyu.shopapp.fragment.MessageFragment;
import com.chiyu.shopapp.fragment.PersonalFragment;
import com.chiyu.shopapp.utils.Constant;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.EMLog;
@SuppressLint("CutPasteId")
public class MainActivity extends FragmentActivity {
//	在这里添加一行测试代码test、test
	// private Toast toaster;
	Handler handler = new Handler(){  
	      
	    @Override  
	    public void handleMessage(Message msg) {  
	        // TODO Auto-generated method stub  
	        super.handleMessage(msg);  
	        switch (msg.what) {  
	        case 1:  
	            //下载apk失败  
	            Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
	            break;    
	        }  
	    }  
	};  
	//以上是版本更新的类容
	private VersionEntity serverVersionEntity;
	@SuppressWarnings("unused")
	private static boolean isExit = false;
	private static final String TAG6 = "tripshop6";
	
	// 未读消息textview
	private TextView unreadLabel;
	private RadioGroup rg_main;
	private RadioButton rb_message;
	private RadioButton rb_findline;
	private RadioButton rb_personal;
	private FragmentManager fragmentManager = getSupportFragmentManager();
	private Fragment lastFragment;
	private RadioButton lastButton;
	private Fragment findLineFragment;
	private Fragment messageFragment;
	private Fragment personalFragment;
	private String username;
	private String userId;
	private InviteMessgeDao inviteMessgeDao;
	private UserDao userDao;
	private String mobile;
	private String photopath;
	private String jidiaoPhotopath;
	private AlertDialog.Builder conflictBuilder;
	private AlertDialog.Builder accountRemovedBuilder;
	private boolean isAccountRemovedDialogShow;
	private BroadcastReceiver internalDebugReceiver;
	private BroadcastReceiver broadcastReceiver;
	private LocalBroadcastManager broadcastManager;
	// 账号被移除
	private boolean isCurrentAccountRemoved = false;
	private boolean isConflictDialogShow;
	// 账号在别处登录
	public boolean isConflict = false;
	Dialog dialog;
//	boolean EMClientIsLoginIsTrue;
//	boolean EMClientIsLoginIsConnected;
	private boolean messageFragmentTag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyApp.getInstance().addActivity(this);
		if (getIntent().getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
			showConflictDialog();
		}
		/**
		 * 获取服务器版本信息
		 */
		VolleyUtils.requestString_Get(URL.UPDATE_VERSION, new OnRequest() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void response(String url, String response) {
				// TODO 获取服务器版本信息成功
				try {
					String jsonString = new String(response.toString().getBytes("ISO-8859-1"),"UTF-8");
					JSONObject object = new JSONObject(jsonString);
					int code = object.getInt("code");
					if(code == 200){
						JSONObject object2 = object.getJSONObject("result");
						JSONObject object3 = object2.getJSONObject("detail");
						serverVersionEntity = new VersionEntity(object3.getString("mold"),
								object3.getString("category"),
								object3.getString("versionnumber"),
								object3.getString("content"),
								object3.getString("personnel"), 
								object3.getString("state"),
								object3.getString("downloadurl"), 
								object3.getString("updatetime"), 
								object3.getString("notes"));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					PackageInfo info = MainActivity.this.getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), PackageManager.GET_ACTIVITIES);
					int curVersionCode = info.versionCode;
					String curVersionName = info.versionName;
					int [] curVersionArray = new int [3]; 
					for(int i = 0 ; i < curVersionArray.length;i++){
						curVersionArray[i] = Integer.parseInt(curVersionName.split("\\.")[i]);
					}
					int [] serverVersionArray = new int [3];
					for(int i = 0 ; i <serverVersionArray.length;i++){
						serverVersionArray[i] = Integer.parseInt(serverVersionEntity.getVersionNumber().split("\\.")[i]);
					}
					for(int i = 0 ; i < 3;i++ ){
						if(serverVersionArray[i] > curVersionArray[i]){
							AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
							.setTitle("版本更新")
							.setMessage("发现新版本"+serverVersionEntity.getVersionNumber()+",当前版本为"+curVersionName+"，是否更新？\n"+serverVersionEntity.getContent())
							.setNegativeButton("取消", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							})
							.setPositiveButton("确定", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									downLoadApk(serverVersionEntity);
									dialog.dismiss();
								}
							});
							AlertDialog dialog = builder.create();
							dialog.show();
							break;
						}
					}
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO 获取服务器版本失败
				Log.w(TAG6, "获取版本信息失败");
			}
		});
		// setPageTitleVisibility(View.GONE);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		mobile = intent.getStringExtra("mobile");
		photopath = intent.getStringExtra("photopath");
		jidiaoPhotopath = intent.getStringExtra("jidiaoPhotopath");
		username = intent.getStringExtra("username");
		messageFragmentTag = intent.getBooleanExtra("login1", false);
		initView();
		if(messageFragmentTag){
			messageFragment = new MessageFragment();
			fragmentManager.beginTransaction()
			.add(R.id.fl_main, messageFragment, "messageFragment")
			.commit();
			lastFragment = messageFragment;
			rb_message.setChecked(true);
			rb_message.setTextColor(Color.parseColor("#2EBAA9"));
			lastButton = rb_message;
		}else{
			findLineFragment = new FindLineFragment();
			fragmentManager.beginTransaction()
					.add(R.id.fl_main, findLineFragment, "findLineFragment")
					.commit();
			lastFragment = findLineFragment;
			rb_findline.setChecked(true);
			rb_findline.setTextColor(Color.parseColor("#2EBAA9"));
			lastButton = rb_findline;
		}
		inviteMessgeDao = new InviteMessgeDao(this);
		userDao = new UserDao(this);
		// 注册local广播接收者，用于接收demohelper中发出的群组联系人的变动通知
		registerBroadcastReceiver();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		rg_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				for (int i = 0; i < rg_main.getChildCount(); i++) {
					RadioButton radioButton = (RadioButton) rg_main
							.getChildAt(i);
					if (radioButton.isChecked()) {
						radioButton.setTextColor(Color.parseColor("#2EBAA9"));
					} else {
						radioButton.setTextColor(Color.parseColor("#979797"));
					}
				}
				// TODO Auto-generated method stub
				switch (checkedId) {

				case R.id.rb_findLine:
					fragmentManager.beginTransaction().hide(lastFragment)
							.commit();
					findLineFragment = (FindLineFragment) fragmentManager
							.findFragmentByTag("findLineFragment");
					if(findLineFragment == null){
						findLineFragment = new FindLineFragment();
						fragmentManager.beginTransaction().add(R.id.fl_main, findLineFragment, "findLineFragment").commit();
					}else{
						fragmentManager.beginTransaction().show(findLineFragment)
						.commit();
					}
					lastFragment = findLineFragment;
					break;
				case R.id.rb_message:
					userId = ShareUtil.getString("userId");
					if("".equals(userId) || userId == null){
						Intent intent = new Intent(getApplicationContext(),
								LoginActivity2.class);
						startActivity(intent);
						finish();
					}
//					else if (!EMClient.getInstance().isConnected() || !DemoHelper.getInstance().isLoggedIn()) {
//						Intent intent = new Intent(getApplicationContext(),
//								LoginActivity1.class);
//						startActivity(intent);
//					}
					else {
						fragmentManager.beginTransaction().hide(lastFragment)
								.commit();
						messageFragment = (MessageFragment) fragmentManager
								.findFragmentByTag("messageFragment");
						if (messageFragment == null) {
							messageFragment = new MessageFragment();
							fragmentManager
									.beginTransaction()
									.add(R.id.fl_main, messageFragment,
											"messageFragment").commit();
						} else {
							fragmentManager.beginTransaction()
									.show(messageFragment).commit();
						}
						lastFragment = messageFragment;
					}

					break;
				case R.id.rb_personal:
					fragmentManager.beginTransaction().hide(lastFragment)
							.commit();
					personalFragment = (PersonalFragment) fragmentManager
							.findFragmentByTag("personalFragment");
					if (personalFragment == null) {
						personalFragment = new PersonalFragment();
						fragmentManager
								.beginTransaction()
								.add(R.id.fl_main, personalFragment,
										"personalFragment").commit();
					} else {
						fragmentManager.beginTransaction()
								.show(personalFragment).commit();
					}
					lastFragment = personalFragment;
					break;

				default:
					break;
				}

			}

		});
		
	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		rg_main = (RadioGroup) findViewById(R.id.rg_main);
		unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
		rb_message = (RadioButton) findViewById(R.id.rb_message);
		rb_findline = (RadioButton) findViewById(R.id.rb_findLine);
		rb_personal = (RadioButton) findViewById(R.id.rb_personal);
	}

	EMMessageListener messageListener = new EMMessageListener() {

		@Override
		public void onMessageReceived(List<EMMessage> messages) {
			// 提示新消息
			for (EMMessage message : messages) {
				DemoHelper.getInstance().getNotifier().onNewMsg(message);
			}
			refreshUIWithMessage();
		}

		@Override
		public void onCmdMessageReceived(List<EMMessage> messages) {
		}

		@Override
		public void onMessageReadAckReceived(List<EMMessage> messages) {
		}

		@Override
		public void onMessageDeliveryAckReceived(List<EMMessage> message) {
		}

		@Override
		public void onMessageChanged(EMMessage message, Object change) {
		}
	};

	private void refreshUIWithMessage() {
		runOnUiThread(new Runnable() {
			public void run() {
				// 刷新bottom bar消息未读数
				updateUnreadLabel();
				if (rb_message.isChecked()) {
					// 当前页面如果为聊天历史页面，刷新此页面
					if (messageFragment != null) {
						((EaseConversationListFragment) messageFragment)
								.refresh();
					}
				}
			}
		});
	}

	private void registerBroadcastReceiver() {
		broadcastManager = LocalBroadcastManager.getInstance(this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
		intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
		broadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				updateUnreadLabel();
				updateUnreadAddressLable();
				if (rb_message.isChecked()) {
					// 当前页面如果为聊天历史页面，刷新此页面
					if (messageFragment != null) {
						((EaseConversationListFragment) messageFragment)
								.refresh();
					}
				}
			}
		};
		broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
	}

	public class MyContactListener implements EMContactListener {
		@Override
		public void onContactAdded(String username) {
		}

		@Override
		public void onContactDeleted(final String username) {
			runOnUiThread(new Runnable() {
				public void run() {
					if (ChatActivity.activityInstance != null
							&& ChatActivity.activityInstance.toChatUsername != null
							&& username
									.equals(ChatActivity.activityInstance.toChatUsername)) {
						String st10 = getResources().getString(
								R.string.have_you_removed);
						Toast.makeText(
								MainActivity.this,
								ChatActivity.activityInstance
										.getToChatUsername() + st10, Toast.LENGTH_LONG).show();
						ChatActivity.activityInstance.finish();
					}
				}
			});
		}

		@Override
		public void onContactInvited(String username, String reason) {
		}

		@Override
		public void onContactAgreed(String username) {
		}

		@Override
		public void onContactRefused(String username) {
		}
	}

	private void unregisterBroadcastReceiver() {
		broadcastManager.unregisterReceiver(broadcastReceiver);
	}

	/**
	 * 刷新未读消息数
	 */
	public void updateUnreadLabel() {
		int count = getUnreadMsgCountTotal();
		if (count > 0) {
			unreadLabel.setText(String.valueOf(count));
			unreadLabel.setVisibility(View.VISIBLE);
		} else {
			unreadLabel.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 刷新申请与通知消息数
	 */
	public void updateUnreadAddressLable() {
		runOnUiThread(new Runnable() {
			public void run() {
				int count = getUnreadAddressCountTotal();
			}
		});

	}

	/**
	 * 获取未读申请与通知消息
	 * 
	 * @return
	 */
	public int getUnreadAddressCountTotal() {
		int unreadAddressCountTotal = 0;
		unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
		return unreadAddressCountTotal;
	}

	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		int chatroomUnreadMsgCount = 0;
		unreadMsgCountTotal = EMClient.getInstance().chatManager()
				.getUnreadMsgsCount();
		for (EMConversation conversation : EMClient.getInstance().chatManager()
				.getAllConversations().values()) {
			if (conversation.getType() == EMConversationType.ChatRoom)
				chatroomUnreadMsgCount = chatroomUnreadMsgCount
						+ conversation.getUnreadMsgCount();
		}
		return unreadMsgCountTotal - chatroomUnreadMsgCount;
	}

	/**
	 * 保存提示新消息
	 * 
	 * @param msg
	 */
	private void notifyNewIviteMessage(InviteMessage msg) {
		saveInviteMsg(msg);
		// 提示有新消息
		DemoHelper.getInstance().getNotifier().viberateAndPlayTone(null);

		// 刷新bottom bar消息未读数
		updateUnreadAddressLable();
		// 刷新好友页面ui
		// if (rb_findline.isChecked())
		// messageFragment.refresh();
	}

	/**
	 * 保存邀请等msg
	 * 
	 * @param msg
	 */
	private void saveInviteMsg(InviteMessage msg) {
		// 保存msg
		inviteMessgeDao.saveMessage(msg);
		// 保存未读数，这里没有精确计算
		inviteMessgeDao.saveUnreadMessageCount(1);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (!isCurrentAccountRemoved) {
			updateUnreadLabel();
			updateUnreadAddressLable();
		}
		// unregister this event listener when this activity enters the
		// background
		DemoHelper sdkHelper = DemoHelper.getInstance();
		sdkHelper.pushActivity(this);

		EMClient.getInstance().chatManager()
				.addMessageListener(messageListener);
	}

	@Override
	protected void onStop() {
		EMClient.getInstance().chatManager()
				.removeMessageListener(messageListener);
		DemoHelper sdkHelper = DemoHelper.getInstance();
		sdkHelper.popActivity(this);
		EaseUI.getInstance().getNotifier().reset();
		super.onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		 outState.putBoolean("isConflict", isConflict);
		outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)
				&& !isAccountRemovedDialogShow) {
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (conflictBuilder != null) {
			conflictBuilder.create().dismiss();
			conflictBuilder = null;
		}
		unregisterBroadcastReceiver();

		try {
			unregisterReceiver(internalDebugReceiver);
		} catch (Exception e) {
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

	}
	
	
	/**
	 * 退出系统
	 */
	long waitTime = 2000;
	long touchTime = 0;

	@SuppressLint("ShowToast")
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			final long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).cancel();
				touchTime = currentTime;
			} else {
				MyApp.getInstance().exit();
			}
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("unused")
	private long mExitTime;

	/* 
	 * 从服务器中下载APK 
	 */  
	protected void downLoadApk(VersionEntity versionEntity) {  
	    final ProgressDialog pd;    //进度条对话框  
	    pd = new  ProgressDialog(this);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	    pd.setMessage("正在下载更新");  
	    pd.show();  
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	                File file = getFileFromServer(serverVersionEntity, pd);  
	                sleep(3000);  
	                installApk(file);  
	                pd.dismiss(); //结束掉进度条对话框  
	            } catch (Exception e) {  
	                Message msg = new Message();  
	                msg.what = 1;  //下载出现错误
	                handler.sendMessage(msg);  
	                e.printStackTrace();  
	            }  
	        }}.start();  
	}  
	/**
	 * 从服务器下载数据：
	 * @param
	 * @param pd
	 * @return
	 * @throws Exception
	 */
    public static File getFileFromServer(VersionEntity versionEntity, ProgressDialog pd) throws Exception{  
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的  
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
            java.net.URL url = new java.net.URL(versionEntity.getDownloadUrl());  
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            //获取到文件的大小   
            pd.setMax(conn.getContentLength());  
            InputStream is = conn.getInputStream();  
            File file = new File(Environment.getExternalStorageDirectory(), "TripShop.apk");  
            FileOutputStream fos = new FileOutputStream(file);  
            BufferedInputStream bis = new BufferedInputStream(is);  
            byte[] buffer = new byte[1024];  
            int len ;  
            int total=0;  
            while((len =bis.read(buffer))!=-1){  
                fos.write(buffer, 0, len);  
                total+= len;  
                //获取当前下载量  
                pd.setProgress(total);  
            }  
            fos.close();  
            bis.close();  
            is.close();  
            return file;  
        }  
        else{  
            return null;  
        }  
    }  
    /**
     * 安装程序
     * @param file
     */
    protected void installApk(File file) {  
        Intent intent = new Intent();  
        //执行动作  
        intent.setAction(Intent.ACTION_VIEW);  
        //执行的数据类型  
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
        startActivity(intent);  
    }
	/**
	 * 显示帐号在别处登录dialog
	 */
	private void showConflictDialog() {
		isConflictDialogShow = true;
		DemoHelper.getInstance().logout(false, null);
		if (!MainActivity.this.isFinishing()) {
			// clear up global variables
			try {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
				.setTitle("异地登录")
				.setMessage("您的账号异地登录，是否重新登录？")
				.setNegativeButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
//						Intent intent = new Intent(MainActivity.this,
//								LoginActivity.class);
//						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//								| Intent.FLAG_ACTIVITY_NEW_TASK);
//						startActivity(intent);
//						dialog.dismiss();
						EMLogserver(ShareUtil.getString("huanxinUserName"),ShareUtil.getString("huanxinpwd"));
						finish();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
//				if (conflictBuilder == null)
//					conflictBuilder = new AlertDialog.Builder(MainActivity.this);
//					conflictBuilder.setTitle(st);
//					conflictBuilder.setMessage(R.string.connect_conflict);
//					conflictBuilder.setPositiveButton(R.string.ok,
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								dialog.dismiss();
//								conflictBuilder = null;
//								finish();
//								Intent intent = new Intent(MainActivity.this,
//										LoginActivity.class);
//								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//										| Intent.FLAG_ACTIVITY_NEW_TASK);
//								startActivity(intent);
//							}
//						});
//				conflictBuilder.setCancelable(false);
//				conflictBuilder.create().show();
				// isConflict = true;
			} catch (Exception e) {
				EMLog.e("",
						"---------color conflictBuilder error" + e.getMessage());
			}

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
					
//							Intent intent = new Intent(LoginActivity1.this,
//									MainActivity.class);
//							intent.putExtra("login1", true);
//							startActivity(intent);
//
//							finish();
						

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
}
