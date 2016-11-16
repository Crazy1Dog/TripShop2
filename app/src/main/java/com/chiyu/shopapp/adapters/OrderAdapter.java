package com.chiyu.shopapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.widget.CheckedTextView;

import com.chiyu.shopapp.bean.OrderEntity;
import com.chiyu.shopapp.ui.R;

public class OrderAdapter extends MyAbsAdapter<OrderEntity> {
	int mSelect = 0;
	public OrderAdapter(Context context, int[] resid) {
		super(context, resid);
		// TODO Auto-generated constructor stub
	}
	public void changeSelect(int position){
		if(position != mSelect){
			mSelect = position;
			notifyDataSetChanged();
		}
	}
	@Override
	public void bindDatas(
			ViewHolder viewHolder,
			OrderEntity data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void myBindDatas(
			ViewHolder viewHolder,
			OrderEntity data, int position) {
		// TODO Auto-generated method stub
		CheckedTextView ctv_order = (CheckedTextView) viewHolder.getView(R.id.ctv_departure);
		ctv_order.setText(data.getOrder());
		if("默认排序".equals(data.getOrder())){
			ctv_order.setCheckMarkDrawable(R.mipmap.zhifu_sel);
		}
		if(position == mSelect){
			ctv_order.setCheckMarkDrawable(R.mipmap.zhifu_sel);
			ctv_order.setTextColor(Color.parseColor("#50D2C2"));
		}else{
			ctv_order.setCheckMarkDrawable(R.mipmap.zhifu_icon_unsel);
			ctv_order.setTextColor(Color.parseColor("#FF454545"));
		}
	}

	

}
