package com.chiyu.shopapp.ui;

import java.util.ArrayList;
import java.util.List;

import com.chiyu.shopapp.adapters.MyGuidePageAdapter;
import com.hyphenate.easeui.controller.EaseUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GuideActivity extends Activity {
	private MyGuidePageAdapter adapter;
	private ViewPager vp_guide;
	private List<ImageView> imageViews;
	private int drawables[] = new int[] { R.mipmap.default_02_1080,
			R.mipmap.default_03_1080, R.mipmap.default_04_1080 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_activity);
		imageViews = new ArrayList<ImageView>();
		vp_guide = (ViewPager) findViewById(R.id.vp_guide);
		initViewPager();
	}

	private void initViewPager() {
		// TODO Auto-generated method stub
		for (int i = 0; i < drawables.length; i++) {
			ImageView imageView = new ImageView(GuideActivity.this);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setImageResource(drawables[i]);
			if( i == 2){
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(GuideActivity.this, InvitationActivity.class);
						startActivity(intent);
						finish();
					}
				});
			}
			imageViews.add(imageView);
		}
		adapter = new MyGuidePageAdapter(imageViews);
		vp_guide.setAdapter(adapter);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
	
}
