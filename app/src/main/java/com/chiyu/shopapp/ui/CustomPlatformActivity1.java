package com.chiyu.shopapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.hyphenate.easeui.controller.EaseUI;
import com.chiyu.shopapp.constants.MyApp;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

public class CustomPlatformActivity1 extends Activity implements
		OnClickListener {
	private ImageButton wechat;
	private ImageButton wechat_circle;
	private ImageButton sms;
	private ImageButton qq;
	private ImageButton qq_circle;
	private ImageButton email;
	private TextView share_cancle;
	// private String url =
	// "http://pre.shopweb.tripb2b.com/34556/line/line.detail.html?";
	// private String url =
	// "http://wei.tripb2c.com/34556/line/line.detail.html?";
	private String title;
	private String url;
	private String dateid;
	private String ImageUrl;
	private UMImage IMAGE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_platform);
		MyApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		ImageUrl = intent.getStringExtra("ImageUrl");
		url = intent.getStringExtra("URL");
		IMAGE = new UMImage(this, ImageUrl);
		initView();
		initEvent();
	}

	private void initEvent() {
		wechat.setOnClickListener(this);
		wechat_circle.setOnClickListener(this);
		sms.setOnClickListener(this);
		qq.setOnClickListener(this);
		qq_circle.setOnClickListener(this);
		email.setOnClickListener(this);
		share_cancle.setOnClickListener(this);
	}

	private void initView() {
		wechat = (ImageButton) findViewById(R.id.wechat);
		wechat_circle = (ImageButton) findViewById(R.id.wechat_circle);
		sms = (ImageButton) findViewById(R.id.sms);
		qq = (ImageButton) findViewById(R.id.qq);
		qq_circle = (ImageButton) findViewById(R.id.qq_circle);
		email = (ImageButton) findViewById(R.id.email);
		share_cancle = (TextView) findViewById(R.id.share_cancle);
		Window lp = getWindow();
		lp.setGravity(Gravity.BOTTOM);
		lp.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wechat:
			shareToWeiXin();
			break;
		case R.id.wechat_circle:
			shareToWeiXinCircle();
			break;
		case R.id.sms:
			sendSMS("http://www.google.com.hk/");
			// shareToSMS();
			break;
		case R.id.qq:
			shareToQQ();
			break;
		case R.id.qq_circle:
			shareToQQCircle();
			break;
		case R.id.email:
			sendMail("http://www.google.com.hk/"); 
//			shareToEmail();
			break;
		case R.id.share_cancle:
			finish();
			break;

		default:
			break;
		}
	}

	/**
	 * 发短信
	 */
	private void sendSMS(String webUrl) {
		String smsBody = title + url;
		Uri smsToUri = Uri.parse("smsto:");
		Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
		// sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
		// sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
		// 短信内容
		sendIntent.putExtra("sms_body", smsBody);
		sendIntent.setType("vnd.android-dir/mms-sms");
		startActivityForResult(sendIntent, 1002);
	}

	/**
	 * 发送邮件
	 * 
	 * @param
	 */
	private void sendMail(String emailUrl) {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("plain/text");

		String emailBody = url;
		// 邮件主题
		email.putExtra(Intent.EXTRA_SUBJECT, title);
		// 邮件内容
		email.putExtra(Intent.EXTRA_TEXT, emailBody);
		startActivityForResult(Intent.createChooser(email, "请选择邮件发送内容"), 1001);
	}

	private void shareToQQ() {
		new ShareAction(this).setPlatform(SHARE_MEDIA.QQ)
				.setCallback(umShareListener).withText(title)
				.withTargetUrl(url).withMedia(IMAGE).share();
	}

	// private void shareToEmail() {
	// new ShareAction(this).setPlatform(SHARE_MEDIA.EMAIL)
	// .setCallback(umShareListener).withText(title)
	// .withTargetUrl(url).withMedia(IMAGE).share();
	// }

	private void shareToQQCircle() {
		new ShareAction(this).setPlatform(SHARE_MEDIA.QZONE)
				.setCallback(umShareListener).withMedia(IMAGE).withText(title)
				.withTargetUrl(url).share();
	}

	// private void shareToSMS() {
	// new ShareAction(this).setPlatform(SHARE_MEDIA.SMS)
	// .setCallback(umShareListener).withText(title)
	// .withTargetUrl(url).withMedia(IMAGE).share();
	// }

	private void shareToWeiXinCircle() {

		new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
				.setCallback(umShareListener).withText(title)
				.withTargetUrl(url).withMedia(IMAGE).share();
	}

	private void shareToWeiXin() {
		/***
		 * new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(
		 * umShareListener) .withMedia(image) .withText("hello umeng")
		 * 
		 * //.withMedia(new UMEmoji(ShareActivity.this,
		 * "http://img.newyx.net/news_img/201306/20/1371714170_1812223777.gif"))
		 * .share();
		 * ***/
		new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN)
				.setCallback(umShareListener).withText(title)
				.withTargetUrl(url).withMedia(IMAGE).share();
	}

	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {

			Toast.makeText(CustomPlatformActivity1.this, platform + " 分享成功啦",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(CustomPlatformActivity1.this, platform + " 分享失败啦",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(CustomPlatformActivity1.this, platform + " 分享取消了",
					Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** attention to this below ,must add this **/
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}

}
