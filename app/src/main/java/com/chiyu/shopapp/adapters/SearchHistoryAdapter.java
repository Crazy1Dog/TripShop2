package com.chiyu.shopapp.adapters;

import java.util.List;

import com.chiyu.shopapp.ui.R;

import android.content.Context;
import android.widget.TextView;

public class SearchHistoryAdapter extends MyAbsAdapter<String> {
	private List<String> list;
	public SearchHistoryAdapter(Context context, int[] resid) {
		super(context, resid);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void myBindDatas(
			ViewHolder viewHolder,
			String data, int position) {
		// TODO Auto-generated method stub
		TextView tv_history = (TextView) viewHolder.getView(R.id.tv_history);
		tv_history.setText(data);
	}

	@Override
	public void bindDatas(
			ViewHolder viewHolder,
			String data) {
		// TODO Auto-generated method stub
		
	}

}
