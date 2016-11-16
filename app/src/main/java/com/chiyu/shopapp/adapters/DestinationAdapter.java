package com.chiyu.shopapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.chiyu.shopapp.bean.TurnedDestination;
import com.chiyu.shopapp.ui.R;

public class DestinationAdapter extends MyAbsAdapter<TurnedDestination> {
	int mSelect = 0;
	public DestinationAdapter(Context context, int[] resid) {
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
			TurnedDestination data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void myBindDatas(
			ViewHolder viewHolder,
			TurnedDestination data, int position) {
		// TODO Auto-generated method stub
		if(data.getType() == 1){
			CheckedTextView ctv_departure = (CheckedTextView) viewHolder.getView(R.id.ctv_departure);
			ctv_departure.setText(data.getDestinationAddress());
			if(position == mSelect){
				ctv_departure.setCheckMarkDrawable(R.mipmap.zhifu_sel);
				ctv_departure.setTextColor(Color.parseColor("#50D2C2"));
			}else{
				ctv_departure.setCheckMarkDrawable(R.mipmap.zhifu_icon_unsel);
				ctv_departure.setTextColor(Color.parseColor("#FF454545"));
			}
			
		}else{
			TextView tv_departure = (TextView) viewHolder.getView(R.id.tv_departure);
			tv_departure.setText(data.getDestinationAddress());
		}
	}

	
}
