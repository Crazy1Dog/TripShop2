package com.chiyu.shopapp.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.chiyu.shopapp.bean.TurnedDeparture;
import com.chiyu.shopapp.ui.R;

public class DepartureAdapter extends MyAbsAdapter<TurnedDeparture> {
	int mSelect = 0;
	private Context context;
	public DepartureAdapter(Context context, int[] resid) {
		super(context, resid);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	public void changeSelected(int position){
		if(position != mSelect){
			mSelect = position;
			notifyDataSetChanged();
		}
	}
	@Override
	public void bindDatas(
			ViewHolder viewHolder,
			TurnedDeparture data) {
		// TODO Auto-generated method stub
//		if(data.getType() == 1){
//			CheckedTextView ctv_departure = (CheckedTextView) viewHolder.getView(R.id.ctv_departure);
//			ctv_departure.setText(data.getAddress());
//			
//		}else{
//			TextView tv_departure = (TextView) viewHolder.getView(R.id.tv_departure);
//			tv_departure.setText(data.getAddress());
//		}
		
	}

	@Override
	public void myBindDatas(
			ViewHolder viewHolder,
			TurnedDeparture data, int position) {
		// TODO Auto-generated method stub
		if(data.getType() == 1){
			CheckedTextView ctv_departure = (CheckedTextView) viewHolder.getView(R.id.ctv_departure);
			ctv_departure.setText(data.getAddress());
			if(position == mSelect){
				ctv_departure.setCheckMarkDrawable(R.mipmap.zhifu_sel);
				ctv_departure.setTextColor(Color.parseColor("#50D2C2"));
			}else{
				ctv_departure.setCheckMarkDrawable(R.mipmap.zhifu_icon_unsel);
				ctv_departure.setTextColor(Color.parseColor("#FF454545"));
			}
			
		}else{
			TextView tv_departure = (TextView) viewHolder.getView(R.id.tv_departure);
			tv_departure.setText(data.getAddress());
			/*if(position == mSelect){
							Toast.makeText(context, "caonima", 0).show();
						}*/
		}
	}

	
}
