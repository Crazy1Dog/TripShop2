package com.chiyu.shopapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ShareUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

public class AboutMineActivity extends Activity implements OnClickListener {
	private String url = "";
    private String ImageUrl = "http://img.happytoo.cn/ic_launcher_app.png";
	private TextView main_title, main_ivTitleBtnLeft,guanyu_banbenhao_txt;
	private String title = "安装我的旅游管家";
	private ImageButton weChat,wechat_circle,qq,qq_circle,email;
	private String companyId="";
	private Context mContext;
	private String curVersionName = "";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApp.getInstance().addActivity(this);
		setContentView(R.layout.guangyu);
		mContext = AboutMineActivity.this;
		companyId = ShareUtil.getString("companyId");
		url = URL.FENXIANG_DEBUG+companyId+"/line/guide.html";
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		weChat = (ImageButton) findViewById(R.id.wechat);
		wechat_circle = (ImageButton) findViewById(R.id.wechat_circle);
		qq = (ImageButton) findViewById(R.id.qq);
		qq_circle = (ImageButton) findViewById(R.id.qq_circle);
		email = (ImageButton) findViewById(R.id.email);
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		guanyu_banbenhao_txt = (TextView) findViewById(R.id.guanyu_banbenhao_txt);
		main_title.setText("关于我们");
		try {
			// 获取当前软件包信息
			PackageInfo pi = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);

			curVersionName = pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guanyu_banbenhao_txt.setText("V"+curVersionName);
	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		weChat.setOnClickListener(this);
		wechat_circle.setOnClickListener(this);
		qq.setOnClickListener(this);
		qq_circle.setOnClickListener(this);
		email.setOnClickListener(this);
	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();
			}
		}
	}
/**
 * 点击事件的监听
 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wechat:
			shareToWeiXin();
			break;
		case R.id.wechat_circle:
			shareToWeiXinCircle();
			break;
		case R.id.qq:
			shareToQQ();
			break;
		case R.id.qq_circle:
			shareToQQCircle();
			break;
		case R.id.email:
			shareToEmail();
			break;

		default:
			break;
		}
	}
	/**
	 * 微信分享
	 */
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
				.withTargetUrl(url).withMedia(new UMImage(this, ImageUrl))
				.share();
	}
	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {

			Toast.makeText(AboutMineActivity.this, platform + " 分享成功啦",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(AboutMineActivity.this, platform + " 分享失败啦",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(AboutMineActivity.this, platform + " 分享取消了",
					Toast.LENGTH_SHORT).show();
		}
	};
	/**
	 * 发送邮件
	 * 
	 * @param emailBody
	 */
	private void sendMail(String emailUrl) {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("plain/text");

		String emailBody = "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:" + emailUrl;
		// 邮件主题
		email.putExtra(Intent.EXTRA_SUBJECT, "哈哈哈");
		// 邮件内容
		email.putExtra(Intent.EXTRA_TEXT, emailBody);

		startActivityForResult(Intent.createChooser(email, "请选择邮件发送内容"), 1001);
	}
	/**
	 * qq分享
	 */
	private void shareToQQ() {
		new ShareAction(this).setPlatform(SHARE_MEDIA.QQ)
				.setCallback(umShareListener).withText(title)
				.withTargetUrl(url).withMedia(new UMImage(this, ImageUrl))
				.share();
	}
	/**
	 * 邮件分享
	 */
	private void shareToEmail() {
		new ShareAction(this).setPlatform(SHARE_MEDIA.EMAIL)
				.setCallback(umShareListener).withText(title)
				.withTargetUrl(url).withMedia(new UMImage(this, ImageUrl))
				.share();
	}
	/**
	 * QQ空间分享
	 */
	private void shareToQQCircle() {
		new ShareAction(this).setPlatform(SHARE_MEDIA.QZONE)
				.setCallback(umShareListener).withText(title).withMedia(new UMImage(this, ImageUrl))
		.withTargetUrl(url)
		.share();
	}
	/**
	 * 微信朋友圈
	 */
	private void shareToWeiXinCircle() {

		new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
				.setCallback(umShareListener).withText(title)
				.withTargetUrl(url).withMedia(new UMImage(this, ImageUrl))
				.share();
	}
}
