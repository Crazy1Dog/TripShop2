package com.chiyu.shopapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.utils.ShareUtil;

public class Invitation_RedFenXiangActivity extends Activity {

	private TextView main_title, main_ivTitleBtnLeft;
	private ImageButton share_btn;
	private String url2 = "/invite/invite.second.html?";
	// "http://pre.shopweb.tripb2b.com/34556/invite/invite.second.html?id=5612";
	private String ImageUrl = "http://static.tripb2b.com/p/buyer.shopweb/images/skt_pic_redbag_04.png";
	private String userId, companyId, url;

	/**
	 * 邀请领取红包
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invitation_redfenxiang);
		MyApp.getInstance().addActivity(this);
		Window window = getWindow();
		window.setGravity(Gravity.TOP);
		window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		initView();
		initListener();
		userId = ShareUtil.getString("userId");
		companyId = ShareUtil.getString("companyId");
		url = URL.FENXIANG_DEBUG + companyId + url2 + "id=" + userId;

	}

	private void initView() {
		// TODO Auto-generated method stub
		main_title = (TextView) findViewById(R.id.main_title);
		main_ivTitleBtnLeft = (TextView) findViewById(R.id.main_ivTitleBtnLeft);
		share_btn = (ImageButton) findViewById(R.id.share_btn);
		main_title.setText("邀请好友");

	}

	private void initListener() {
		main_ivTitleBtnLeft.setOnClickListener(new viewClickListener());
		share_btn.setOnClickListener(new viewClickListener());

	}

	class viewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.main_ivTitleBtnLeft) {
				finish();
			} else if (id == R.id.share_btn) {
				Intent intent = new Intent(Invitation_RedFenXiangActivity.this,
						CustomPlatformActivity1.class);
				intent.putExtra("title", "点击进入活动页面,免费领取旅游红包");
				intent.putExtra("URL", url);
				intent.putExtra("ImageUrl", ImageUrl);
				startActivity(intent);
			}
		}
	}
}
