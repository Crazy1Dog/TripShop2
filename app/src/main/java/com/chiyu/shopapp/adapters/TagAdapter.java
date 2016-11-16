package com.chiyu.shopapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.ui.R;

public class TagAdapter extends MyAbsAdapter<CategoryLineEntity> {
	int mSelect = 0;
	public TagAdapter(Context context, int[] resid) {
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
	public void bindDatas(com.chiyu.shopapp.adapters.MyAbsAdapter.ViewHolder viewHolder,
			CategoryLineEntity data) {
		// TODO Auto-generated method stub
	}
	@Override
	public void myBindDatas(
			com.chiyu.shopapp.adapters.MyAbsAdapter.ViewHolder viewHolder,
			CategoryLineEntity data, int position) {
		// TODO Auto-generated method stub
		TextView tv_category = (TextView) viewHolder.getView(R.id.tv_category);
		tv_category.setText(data.getCategoryName());
		ImageView iv_tag = (ImageView) viewHolder.getView(R.id.iv_tag);
		iv_tag.setImageResource(R.mipmap.zhifu_icon_unsel);
		if("不限".equals(data.getCategoryName())){
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
