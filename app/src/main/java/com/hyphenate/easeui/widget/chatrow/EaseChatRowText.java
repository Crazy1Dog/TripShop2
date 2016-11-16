package com.hyphenate.easeui.widget.chatrow;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.ChatActivity;
import com.chiyu.shopapp.ui.Line_DetailsActivity;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.CircleImageView;
import com.hyphenate.exceptions.HyphenateException;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;


public class EaseChatRowText extends EaseChatRow {

	private TextView contentView;
	private LinearLayout ll_personlist;
	private ImageView iv_personlist;
	private RelativeLayout re_location;
	private ImageView iv_protect;
	private TextView tv_name;
	private TextView tv_date;
	private TextView tv_price;
	private ImageView iv_back;
	private LinearLayout bubble;
	private ProgressBar progress_bar;
	private TextView tv_delivered;
	private CircleImageView iv_userhead;
	private LinearLayout iv_expaned;
	private ImageView iv_img;
	private MyApp app;
	public EaseChatRowText(Context context, EMMessage message, int position,
			BaseAdapter adapter) {
		super(context, message, position, adapter);
		
	}

	@Override
	protected void onInflatView() {
		inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_received_message
						: R.layout.ease_row_sent_message, this);
	}

	@Override
	protected void onFindViewById() {
		contentView = (TextView) findViewById(R.id.tv_chatcontent);
		ll_personlist = (LinearLayout) findViewById(R.id.ll_personlist);
		iv_personlist = (ImageView) findViewById(R.id.iv_personlist);
		re_location = (RelativeLayout) findViewById(R.id.re_location);
		iv_protect = (ImageView) findViewById(R.id.iv_protect);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_price = (TextView) findViewById(R.id.tv_price);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		bubble = (LinearLayout) findViewById(R.id.bubble);
		progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
		tv_delivered = (TextView) findViewById(R.id.tv_delivered);
		iv_userhead = (CircleImageView) findViewById(R.id.iv_userhead);
		iv_expaned = (LinearLayout) findViewById(R.id.iv_expaned);
		iv_img =(ImageView) findViewById(R.id.iv_img);
	}

	@SuppressLint("NewApi")
	@Override
	public void onSetUpView() {
		EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
		Spannable span = EaseSmileUtils.getSmiledText(context,
				txtBody.getMessage());
		Log.e("消息接受者", EMClient.getInstance().getCurrentUser() + "");
		Log.e("消息发送者", EMClient.getInstance().getCurrentUser() + "");
		// 设置内容
		
		final String txt = txtBody.toString();
		
		app = (MyApp) context.getApplicationContext();
		if(message.direct() == EMMessage.Direct.SEND){
			VolleyUtils.requestImage(URL.IMAGE_DEBUG + ShareUtil.getString("photoPath"),
					iv_userhead, R.drawable.skt_icon_default,
					R.drawable.skt_icon_default);
		}else{
			VolleyUtils.requestImage(URL.IMAGE_DEBUG + app.getJidiaoPhotopath(),
					iv_userhead, R.drawable.skt_icon_default,
					R.drawable.skt_icon_default);
		}
		String[] mess = txt.split(",");
		Log.e("消息的长度========================", mess.length+"");
		if (mess.length != 1 && txt.contains("游客类型")|| mess.length != 1 && txt.contains("flag") ) {
			if (txt.contains("游客类型") && mess.length != 1) {
				ll_personlist.setVisibility(View.VISIBLE);
				iv_personlist.setVisibility(View.VISIBLE); 
				re_location.setVisibility(View.GONE);
				bubble.setVisibility(VISIBLE);
				progress_bar.setVisibility(View.GONE);
				tv_delivered.setVisibility(View.GONE);	
				iv_expaned.setVisibility(View.VISIBLE);
				iv_img.setBackgroundResource(R.drawable.detail_icon_down);
				contentView.setMaxLines(3);
//				contentView.setText(txt.replace("personlist", "").replace("txt:\"", "").replace("\"", ""), BufferType.SPANNABLE);
				contentView.setText(txt.replace(",", "").replace("txt:\"", "").replace("\"", ""), BufferType.SPANNABLE);
				contentView.setLayoutParams(new LayoutParams(600,LayoutParams.WRAP_CONTENT));
				iv_expaned.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(contentView.getMaxLines()==txt.length()){
							iv_img.setBackgroundResource(R.drawable.detail_icon_down);
							contentView.setMaxLines(3);
						}else{
							contentView.setMaxLines(txt.length());
							iv_img.setBackgroundResource(R.drawable.detail_icon_up);
						}
						
					}
				});
			
			} else if (txt.contains("flag") && mess.length != 1) {
				re_location.setVisibility(View.VISIBLE);
				ll_personlist.setVisibility(View.GONE);
				iv_personlist.setVisibility(View.GONE);
				bubble.setVisibility(View.GONE);
				progress_bar.setVisibility(View.GONE);
				tv_delivered.setVisibility(View.GONE);
				iv_expaned.setVisibility(View.GONE);
				iv_img.setBackground(null);
//				contentView.setCompoundDrawables(null, null, null, null);
				
				for (int i = 0; i < mess.length; i++) {
					if(mess.length ==1){
						
					}else{
						final String name = mess[1];
						final String price = mess[3];
						final String photopath = mess[4];
						final String date = mess[7];
						final String dateId = mess[6];
						final String lineId = mess[5];
						
						tv_name.setText(name);
						tv_date.setText("团期:" + date.replace("\"", ""));
						tv_price.setText(price);
	//					String path = photopath.substring(1, photopath.length());
	//					Log.e("接收到预订宝的图片地址=============", path);
						VolleyUtils.requestImage(URL.IMAGE_DEBUG + photopath,iv_protect, R.drawable.skt_icon_default,R.drawable.skt_icon_default);
						re_location.setOnClickListener(new OnClickListener() {
	
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(context,
										Line_DetailsActivity.class);
								app.setDateId(dateId);
								app.setLineId(lineId);
								app.setTitle(name);
								app.setPrice(price);
								app.setDateList(date.replace("\"", ""));
								ShareUtil.putString("photopath",photopath);
								intent.putExtra("lineId", lineId);
								intent.putExtra("dateId", dateId);
								context.startActivity(intent);
	
							}
						});
					}
				}
			}

		} else {

			ll_personlist.setVisibility(View.GONE);
			iv_personlist.setVisibility(View.GONE);
			re_location.setVisibility(View.GONE);
			bubble.setVisibility(View.VISIBLE);
			progress_bar.setVisibility(View.GONE);
			tv_delivered.setVisibility(View.GONE);
			iv_expaned.setVisibility(View.GONE);
			iv_img.setBackground(null);
			contentView.setMaxLines(txt.length());
			contentView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			contentView.setText(span, BufferType.SPANNABLE);

		}

		handleTextMessage();

	}

	protected void handleTextMessage() {
		if (message.direct() == EMMessage.Direct.SEND) {
			setMessageSendCallback();
			switch (message.status()) {
			case CREATE:
				progressBar.setVisibility(View.GONE);
				statusView.setVisibility(View.VISIBLE);
				// 发送消息
				// sendMsgInBackground(message);
				break;
			case SUCCESS: // 发送成功
				progressBar.setVisibility(View.GONE);
				statusView.setVisibility(View.GONE);
				break;
			case FAIL: // 发送失败
				progressBar.setVisibility(View.GONE);
				statusView.setVisibility(View.VISIBLE);
				break;
			case INPROGRESS: // 发送中
				progressBar.setVisibility(View.VISIBLE);
				statusView.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		} else {
			if (!message.isAcked() && message.getChatType() == ChatType.Chat) {
				try {
					EMClient.getInstance()
							.chatManager()
							.ackMessageRead(message.getFrom(),
									message.getMsgId());
				} catch (HyphenateException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onUpdateView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onBubbleClick() {
		// TODO Auto-generated method stub

	}
	
}
