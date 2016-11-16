package com.chiyu.shopapp.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiyu.shopapp.bean.EmojiconExampleGroupData;
import com.chiyu.shopapp.bean.FeilName;
import com.chiyu.shopapp.bean.RobotUser;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.ContextMenuActivity;
import com.chiyu.shopapp.ui.ForwardMessageActivity;
import com.chiyu.shopapp.ui.ImageGridActivity;
import com.chiyu.shopapp.ui.Line_BookingActivity;
import com.chiyu.shopapp.ui.Line_DetailsActivity;
import com.chiyu.shopapp.ui.MainActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.ui.SendFielActivity;
import com.chiyu.shopapp.utils.Constant;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentListener;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;

public class ChatFragment extends EaseChatFragment implements EaseChatFragmentListener{

    //避免和基类定义的常量可能发生的冲突，常量从11开始定义
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;
    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3; 
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;
    private static final int camera_upload_imageRequestCode = 2;
    /**
     * 是否为环信小助手
     */
    private boolean isRobot;
    private String flag;
    private RelativeLayout ll_fla;
    private ImageView iv_protect;
    
    private TextView tv_name;
    private TextView tv_price;
    private Button  btn_order;
    private String title;
    private String price;
    private String photopath;
    private String lineId;
    private String dateId;
    private String dateList;
    private String path;
    private int photoCount;
    private String tag;
    private String tag2;
    private IMContactsReceiver receiver = new IMContactsReceiver();
    ArrayList<FeilName> personlist = new ArrayList<FeilName>();
    private MyApp app;
    private String tagChatFragment ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	IntentFilter fliFilter = new IntentFilter();
		fliFilter.addAction("mingdanhuichuan");
		getActivity().registerReceiver(receiver, fliFilter);
    	Bundle b = getArguments();
    	title = b.getString("title");
    	flag = b.getString("flag");
    	price = b.getString("price");
    	photopath = b.getString("photopath");
    	dateList = b.getString("dateList");
    	dateId = b.getString("dateId");
    	lineId = b.getString("lineId");
    	
    	photoCount = b.getInt("photoCount");
//    	lineId = ShareUtil.getString("lineId");
//    	dateId = ShareUtil.getString("dateId");
    	 	
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.ease_fragment_chat, null);
    	app = (MyApp) getActivity().getApplication();
    	ll_fla = (RelativeLayout) v.findViewById(R.id.ll_fla);
		iv_protect = (ImageView) v.findViewById(R.id.iv_protect);
		tv_name = (TextView) v.findViewById(R.id.tv_name);
		tv_price = (TextView) v.findViewById(R.id.tv_price);
		btn_order = (Button) v.findViewById(R.id.btn_order);
		if(photopath != null && photoCount != 1){
    		path = photopath.split(",")[0];
    	}else if(photopath != null && photoCount == 1){
    		path = photopath;
    	}   
		app.setPrice(price);
    	app.setTitle(title);
    	app.setDateId(dateId);
    	app.setLineId(lineId);
		btn_order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//立即购买
				if(flag.equals("Line_DetailsActivity")){
					Intent intent = new Intent(getActivity(),
							Line_BookingActivity.class);
					intent.putExtra("lineId", app.getLineId());
					intent.putExtra("dateId", app.getDateId());
					startActivity(intent);
				}else{
					Intent intent = new Intent(getActivity(),
							Line_DetailsActivity.class);
					intent.putExtra("lineId", lineId);
					intent.putExtra("dateId", dateId);
					startActivity(intent);
				}
			}
		});
        return v;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(flag != null){
			ll_fla.setVisibility(View.VISIBLE);
			VolleyUtils.requestImage(URL.IMAGE_DEBUG + path, iv_protect, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
			tv_name.setText(app.getTitle());
			tv_price.setText("￥"+app.getPrice());
		}else{
			ll_fla.setVisibility(View.GONE);	
		}
		if(tag != null || SendFielActivity.tagChatFragment != null){
			if(tag != null && tag.equals("ChatFragment") && ((ArrayList<FeilName>) receiver.getDatas()).size()>0|| SendFielActivity.tagChatFragment.equals("ChatFragment") && ((ArrayList<FeilName>) receiver.getDatas()).size()>0){
				personlist = (ArrayList<FeilName>) receiver.getDatas();
				String content = personlist.toString().replace("[", "");
				String content2 = content.replace("]", "").replace(",", "");
//				sendTextMessage("personlist" + content2 + "\n共计:" + personlist.size() +"人\n");
				sendTextMessage(content2 +","+ "\n共计:" + personlist.size() +"人\n");
				personlist.clear();
			}
		}else{
			
		}
		
	}
    

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        if (chatType == Constant.CHATTYPE_SINGLE) { 
            Map<String,RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
            if(robotMap!=null && robotMap.containsKey(toChatUsername)){
                isRobot = true;
            }
        }
        super.setUpView();
        // 设置标题栏点击事件
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                getActivity().finish();
            }
        });
        ((EaseEmojiconMenu)inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
    }
    
    @Override
    protected void registerExtendMenuItem() {
        super.registerExtendMenuItem();
        //增加扩展item
//        inputMenu.registerExtendMenuItem(R.string.attach_location, R.drawable.ease_chat_location_selector, ITEM_LOCATION, extendMenuItemClickListener);
        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);

    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
            case ContextMenuActivity.RESULT_CODE_COPY: // 复制消息
                clipboard.setText(((EMTextMessageBody) contextMenuMessage.getBody()).getMessage());
                break;
            case ContextMenuActivity.RESULT_CODE_DELETE: // 删除消息
                conversation.removeMessage(contextMenuMessage.getMsgId());
                messageList.refresh();
                break;

            case ContextMenuActivity.RESULT_CODE_FORWARD: // 转发消息
                Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                startActivity(intent);
                
                break;

            default:
                break;
            }
        }
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
            case REQUEST_CODE_SELECT_VIDEO: //发送选中的视频
                if (data != null) {
                    int duration = data.getIntExtra("dur", 0);
                    String videoPath = data.getStringExtra("path");
                    File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                        ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                        fos.close();
                        sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
//            case REQUEST_CODE_SELECT_FILE: //发送选中的文件
//            	if(data != null){
//            		Bundle bundle = data.getBundleExtra("path");
//            		ArrayList<FeilName> list = bundle.getParcelableArrayList("path");          		
//            		Log.w("传过来的名单数据00", list.toString());           		
//            		for(int i = 0; i < list.size(); i++){
//            			FeilName feilName = list.get(i);
//            			personlist.add(feilName); 
//            		}
//            		 
//            		String content = personlist.toString().replace("[", "");
//            		String content2 = content.replace("]", "").replace(",", "");
//            		sendTextMessage("名单" + content2 + "\n共计:" + personlist.size() +"人\n");
////            		sendTextMessage("名单"+ "\n共计:" + personlist.size() +"人\n");
//            		personlist.clear();
//            	}
//                break;

            default:
                break;
            }
        }
        
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
//    	getActivity().unregisterReceiver(receiver);
    	
    }
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if(isRobot){
            //设置消息扩展属性
            message.setAttribute("em_robot_message", isRobot);
        }
    }
    
    @Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(receiver);
	}

	@Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        //设置自定义listview item提供者
        return new CustomChatRowProvider();
    }
  

    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //头像点击事件
//        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
    }
    
    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
    	
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        //消息框长按
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message",message),
                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
        case ITEM_VIDEO: //视频
            Intent intent = new Intent(getActivity(), ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
            break;
        case ITEM_FILE: //发送名单
            //demo这里是通过系统api选择文件，实际app中最好是做成qq那种选择发送文件
//            selectFileFromLocal();
        	Intent intent2 = new Intent(getActivity(),SendFielActivity.class);
        	intent2.putExtra("tag", "ChatFragment");
        	intent2.putExtra("tagChatFragment", "ChatFragment");
        	startActivityForResult(intent2, REQUEST_CODE_SELECT_FILE);
//        	startActivity(intent2);
            break;
        case ITEM_VOICE_CALL: //音频通话
            startVoiceCall();
            break;
        case ITEM_VIDEO_CALL: //视频通话
            startVideoCall();
            break;
//        case ITEM_LOCATION:
//        	if(flag != null){
//        		sendTextMessage("flag"+","+ title +"," +price +"," +path +","+dateList +","+dateId +","+lineId);
//        	}else{
//        		//进入线路详情
//        		Intent intent1 = new Intent(getActivity(),Line_DetailsActivity.class);
//        		intent1.putExtra("lineId", lineId);
//        		intent1.putExtra("dateId", dateId);
//        		startActivity(intent1);
//        	}
        	
//        	break;
        default:
            break;
        }
        //不覆盖已有的点击事件
        return false;
    }
    
    /**
     * 选择文件
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //19以后这个api不可用，demo这里简单处理成图库选择图片
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }
    
    /**
     * 拨打语音电话
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
        } else {
//            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }
    
    /**
     * 拨打视频电话
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected()){
//            Toast.makeText(getActivity(), R.string.not_connect_to_server, 0).show();
        }else {
//            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }
    
    /**
     * chat row provider 
     *
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //音、视频通话发送、接收共4种
            return 4;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if(message.getType() == EMMessage.Type.TXT){
                //语音通话类型
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                }else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
                    //视频通话
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
//            if(message.getType() == EMMessage.Type.TXT){
//                // 语音通话,  视频通话
//                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
//                    message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
//                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
//                }
//            }
            return null;
        }

    }
  
    class IMContactsReceiver extends BroadcastReceiver {
    	private ArrayList<FeilName> personlist = new ArrayList<FeilName>();
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		tag = intent.getStringExtra("tag");
    		tag2 = intent.getStringExtra("tag2");
    		tagChatFragment = intent.getStringExtra("tagChatFragment");
    		Bundle bundle = intent.getBundleExtra("path");
    		ArrayList<FeilName> list = bundle.getParcelableArrayList("path");          		
    		for(int i = 0; i < list.size(); i++){
    			FeilName feilName = list.get(i);
    			personlist.add(feilName); 
    		}
    	}
    	public List<FeilName> getDatas() {
    		return personlist;
    		
    	}

    }
}

