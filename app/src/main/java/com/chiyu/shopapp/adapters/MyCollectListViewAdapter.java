package com.chiyu.shopapp.adapters;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chiyu.shopapp.bean.TravelLineEntity;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.CustomPlatformActivity;
import com.chiyu.shopapp.ui.LoginActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.chiyu.shopapp.view.XCRoundRectImageView;

public class MyCollectListViewAdapter extends MyAbsAdapter<TravelLineEntity> {
	Context context;
	public MyCollectListViewAdapter(Context context, int[] resid) {
		super(context, resid);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	
	@Override
	public void bindDatas(
			ViewHolder viewHolder,
			final TravelLineEntity data) {
		// TODO Auto-generated method stub
		XCRoundRectImageView iv_picture = (XCRoundRectImageView) viewHolder.getView(R.id.iv_picture);
//		iv_picture.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "弹出分享和删除", 0).show();
//			}
//		});
		VolleyUtils.requestImage(URL.IMAGE_DEBUG + data.getPhoto(), iv_picture, R.mipmap.pic_default, R.mipmap.pic_default);
		TextView tv_title = (TextView) viewHolder.getView(R.id.tv_title);
		tv_title.setText(data.getTitle());
		TextView tv_dateList = (TextView) viewHolder.getView(R.id.tv_dateList);
		if(data.getDateList() == null || data.getDateList() == "null"||"".equals(data.getDateList())){
			tv_dateList.setTextColor(Color.parseColor("#ff0000"));
			tv_dateList.setText("该线路团期已过期");
		}else{
			tv_dateList.setTextColor(Color.parseColor("#000000"));
			tv_dateList.setText("团期："+data.getDateList());
		}
		TextView tv_days = (TextView) viewHolder.getView(R.id.tv_days);
		tv_days.setText(data.getDays());
		TextView tv_zan = (TextView) viewHolder.getView(R.id.tv_zan);
		if(data.getCount() == null || "".equals(data.getCount())){
			tv_zan.setText(0+"");
		}else{
			tv_zan.setText(data.getCount());
		}
		
		//此处差赞
		TextView tv_price = (TextView) viewHolder.getView(R.id.tv_price);
		tv_price.setText(data.getPrice());
		TextView tv_departure = (TextView) viewHolder.getView(R.id.tv_departure);
		tv_departure.setText(data.getDeparture());
		ImageView collectOrShare = (ImageView) viewHolder.getView(R.id.iv_share_or_collect);
		final LinearLayout ll_contentLayout  = (LinearLayout) viewHolder.getView(R.id.ll_content);
		final LinearLayout ll_shareOrCollectLayout  = (LinearLayout) viewHolder.getView(R.id.ll_shareOrCollect);
		TextView tv_click_share = (TextView) ll_shareOrCollectLayout.findViewById(R.id.tv_click_share);
		tv_click_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_shareOrCollectLayout.setVisibility(View.GONE);
				ObjectAnimator animator = ObjectAnimator.ofFloat(ll_contentLayout, "translationX", 0.0f);
				animator.setDuration(300);
				animator.start();
				Intent intent = new Intent(context,CustomPlatformActivity.class);
				intent.putExtra("dateId", data.getDateId());
				intent.putExtra("lineId", data.getLineId());
				intent.putExtra("title", data.getTitle());
				intent.putExtra("ImageUrl", URL.IMAGE_DEBUG +data.getPhoto());
				context.startActivity(intent);
			}
		});
		collectOrShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(context, "分享或者收藏", 0).show();
				ll_shareOrCollectLayout.setVisibility(View.VISIBLE);
//				ll_contentLayout.setTranslationX(ll_shareOrCollectLayout.getWidth() * -1.0f);
				ObjectAnimator animator = ObjectAnimator.ofFloat(ll_contentLayout, "translationX", 0.0f,ll_shareOrCollectLayout.getWidth() * -1.0f);
				animator.setDuration(300);
				animator.start();
			}
		});
	}


	@Override
	public void myBindDatas(
			ViewHolder viewHolder,
			TravelLineEntity data, int position) {
		// TODO Auto-generated method stub
		
	}


	
}
