package com.chiyu.shopapp.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.db.InviteMessgeDao;
import com.chiyu.shopapp.ui.ChatActivity;
import com.chiyu.shopapp.ui.MainActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.Constant;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ShareUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;

/**
 * 
 * 消息模块
 */
public class MessageFragment extends EaseConversationListFragment{

	 private TextView errorText;

	    @Override
	    protected void initView() {
	        super.initView();
	        View errorView = (LinearLayout) View.inflate(getActivity(),R.layout.em_chat_neterror_item, null);
	        errorItemContainer.addView(errorView);
	        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
	    }
	    
	    @Override
	    protected void setUpView() {
	        super.setUpView();
	        // 注册上下文菜单
	        registerForContextMenu(conversationListView);
	        conversationListView.setOnItemClickListener(new OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                EMConversation conversation = conversationListView.getItem(position);
	                String username = conversation.getUserName();
	                if (username.equals(EMClient.getInstance().getCurrentUser())){
//	                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, 0).show();
	                } else {
	                    // 进入聊天页面
	                    Intent intent = new Intent(getActivity(), ChatActivity.class);
	                    if(conversation.isGroup()){
	                        if(conversation.getType() == EMConversationType.ChatRoom){
	                            // it's group chat
	                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
	                        }else{
	                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
	                        }
	                        
	                    }
	                    // it's single chat
	                    intent.putExtra(Constant.EXTRA_USER_ID, username);
	                    startActivity(intent);
	                }
	            }
	        });
	    }

	    @Override
	    protected void onConnectionDisconnected() {
	        super.onConnectionDisconnected();
	        if (NetUtils.hasNetwork(getActivity())){
	         errorText.setText("聊天服务器已断开,点击重新连接");
	         errorText.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					EMLogserver(ShareUtil.getString("huanxinUserName"),ShareUtil.getString("huanxinpwd"));
				}
			});
	        } else {
	          errorText.setText(R.string.the_current_network);
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
						}

						@Override
						public void onProgress(int progress, String status) {
						}

						@Override
						public void onError(final int code, final String message) {
							getActivity().runOnUiThread(new Runnable() {
								public void run() {
								}
							});
						}
					});
		}  
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu); 
	    }

	    @Override
	    public boolean onContextItemSelected(MenuItem item) {
	        boolean deleteMessage = false;
	        if (item.getItemId() == R.id.delete_message) {
	            deleteMessage = true;
	        } else if (item.getItemId() == R.id.delete_conversation) {
	            deleteMessage = false;
	        }
	    	EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
	    	if (tobeDeleteCons == null) {
	    	    return true;
	    	}
	        try {
	            // 删除此会话
	            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), deleteMessage);
	            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
	            inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        refresh();

	        // 更新消息未读数
	        ((MainActivity) getActivity()).updateUnreadLabel();
	        return true;
	    }

}