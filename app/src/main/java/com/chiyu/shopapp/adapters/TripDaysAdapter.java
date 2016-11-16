package com.chiyu.shopapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiyu.shopapp.bean.TripDayEntity;
import com.chiyu.shopapp.ui.R;

public class TripDaysAdapter extends MyAbsAdapter<TripDayEntity> {
	int mSelect = 0;
	public TripDaysAdapter(Context context, int[] resid) {
		super(context, resid);
		// TODO Auto-generated constructor stub
	}
	public void changeSelected(int position){
		if(position != mSelect ){
			mSelect = position;
			notifyDataSetChanged();
		}
	}
	@Override
	public void bindDatas(
			ViewHolder viewHolder,
			TripDayEntity data) {
		// TODO Auto-generated method stub
//		CheckedTextView ctv_days = (CheckedTextView) viewHolder.getView(R.id.ctv_departure);
//		ctv_days.setText(data.getDays());
//		if("不限".equals(data.getDays())){
//			ctv_days.setChecked(true);
//			ctv_days.setCheckMarkDrawable(R.drawable.zhifu_sel);
//		}
		
	}

	@Override
	public void myBindDatas(
			ViewHolder viewHolder,
			TripDayEntity data, int position) {
		// TODO Auto-generated method stub
		TextView tv_category = (TextView) viewHolder.getView(R.id.tv_category);
		tv_category.setText(data.getDays());
		ImageView iv_tag = (ImageView) viewHolder.getView(R.id.iv_tag);
		iv_tag.setImageResource(R.mipmap.zhifu_icon_unsel);
		if("不限".equals(data.getDays())){
			iv_tag.setImageResource(R.mipmap.zhifu_sel);
		}
		if(mSelect == position){
			iv_tag.setImageResource(R.mipmap.zhifu_sel);
			tv_category.setTextColor(Color.parseColor("#50D2C2"));
		}else{
			iv_tag.setImageResource(R.mipmap.zhifu_icon_unsel);
			tv_category.setTextColor(Color.parseColor("#FF454545"));
		}
	}

	

}
