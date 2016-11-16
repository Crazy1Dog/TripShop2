package com.chiyu.shopapp.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chiyu.shopapp.bean.Line_DateEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.ParseUtils;

public class Line_DateAdapter extends BaseAdapter {
	private ArrayList<Line_DateEntity> zhoubiansell = new ArrayList<Line_DateEntity>();
	private Context mContext;

	public Line_DateAdapter(Context mContext,
			ArrayList<Line_DateEntity> zhoubiansell) {
		super();
		this.zhoubiansell = zhoubiansell;
		this.mContext = mContext;
	}

	public ArrayList<Line_DateEntity> getZhoubiansell() {
		return zhoubiansell;
	}

	public void setZhoubiansell(ArrayList<Line_DateEntity> zhoubiansell) {
		this.zhoubiansell = zhoubiansell;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (zhoubiansell == null || zhoubiansell.size() <= 0) {
			return 0;
		}
		return zhoubiansell.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return zhoubiansell.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Line_DateEntity line_dateEntity = zhoubiansell.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.line_date_griditem, null);
		}
//		String sourDateFormat = "MM-DD";   String sourDate =line_dateEntity.getGoTime() ;
//		String date = ParseUtils.getDateFormat(sourDateFormat, sourDate);
		String date = line_dateEntity.getGoTime().substring(5, 10);
		System.out.println("date======日期返回数据==="+date);
		TextView biaotiTxt = (TextView) convertView
				.findViewById(R.id.line_date_tx);
		biaotiTxt.setText(date.replaceAll("-", "/"));
		return convertView;
	}
}