package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.widget.CircleImageView;

/**
 * 大表情(动态表情)
 *
 */
public class EaseChatRowBigExpression extends EaseChatRowText{

    private ImageView imageView;
    private CircleImageView iv_userhead;
	private MyApp app;

    public EaseChatRowBigExpression(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }
    
    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? 
                R.layout.ease_row_received_bigexpression : R.layout.ease_row_sent_bigexpression, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);
        imageView = (ImageView) findViewById(R.id.image);
        iv_userhead = (CircleImageView) findViewById(R.id.iv_userhead);
    }


    @Override
    public void onSetUpView() {
        String emojiconId = message.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null);
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
        EaseEmojicon emojicon = null;
        if(EaseUI.getInstance().getEmojiconInfoProvider() != null){
            emojicon =  EaseUI.getInstance().getEmojiconInfoProvider().getEmojiconInfo(emojiconId);
        }
        if(emojicon != null){
            if(emojicon.getBigIcon() != 0){
                Glide.with(activity).load(emojicon.getBigIcon()).placeholder(R.drawable.ease_default_expression).into(imageView);
            }else if(emojicon.getBigIconPath() != null){
                Glide.with(activity).load(emojicon.getBigIconPath()).placeholder(R.drawable.ease_default_expression).into(imageView);
            }else{
                imageView.setImageResource(R.drawable.ease_default_expression);
            }
        }
        
        handleTextMessage();
    }
}
