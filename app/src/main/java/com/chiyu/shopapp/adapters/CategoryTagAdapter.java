package com.chiyu.shopapp.adapters;

import java.util.List;

import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

public class CategoryTagAdapter extends BaseAdapter {
	private Context context;
	private List<CategoryLineEntity> list;
	
	public CategoryTagAdapter(Context context, List<CategoryLineEntity> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub 
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.departure1_item, null);
			holder = new ViewHolder();
			holder.ctv_category = (CheckedTextView) convertView.findViewById(R.id.ctv_departure);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
			holder.ctv_category = (CheckedTextView) convertView.findViewById(R.id.ctv_departure);
		}
		return convertView;
	}
	class ViewHolder{
		CheckedTextView ctv_category;
	}

}
