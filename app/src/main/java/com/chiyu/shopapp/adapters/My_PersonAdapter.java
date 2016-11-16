package com.chiyu.shopapp.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiyu.shopapp.bean.My_Person;
import com.chiyu.shopapp.ui.Order_DetailsActivity;
import com.chiyu.shopapp.ui.Order_PerfectActivity;
import com.chiyu.shopapp.ui.R;

public class My_PersonAdapter extends BaseAdapter {
	private Context context;
	private List<My_Person> list;
	private static LayoutInflater inflater = null;
	private Order_PerfectActivity bActivity;

	public My_PersonAdapter(Context context, Order_PerfectActivity bActivity) {
		this.context = context;
		this.bActivity = bActivity;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public List<My_Person> getData() {
		return list;
	}

	public void setData(List<My_Person> list) {
		notifyDataSetChanged();
		this.list = list;
	}

	public int getCount() {
		if (list == null || list.size() <= 0) {
			return 0;
		}
		return list.size();
	}

	public Object getItem(int position) {
		if (list == null || list.size() <= 0 || position < 0
				|| position >= list.size()) {
			return null;
		}
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;

		if (null == convertView) {

			convertView = inflater.inflate(R.layout.order_infomationitem_item,
					null);
			vh = new ViewHolder();

			vh.usernameTxt = (TextView) convertView
					.findViewById(R.id.order_username_item);
			vh.mobTxt = (TextView) convertView
					.findViewById(R.id.order_mob_item);
			vh.leixingTxt = (TextView) convertView
					.findViewById(R.id.order_xingbie_item);
			vh.order_shanchu_item = (ImageView) convertView
					.findViewById(R.id.order_shanchu_item);
			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String Idcard = list.get(position).getMobile();
		System.out.println("Idcard========" + Idcard);
		String username = list.get(position).getName();
		int Type = list.get(position).getCategory();
		System.out.println("username===========" + username);

		if (username.equals("")  ||username.equals("null")
				) {
			vh.mobTxt.setText("请完善游客信息");
			vh.usernameTxt.setVisibility(View.GONE);
			if (Type == 0) {
				vh.leixingTxt.setText("成人");
			} else if (Type == 1) {
				vh.leixingTxt.setText("儿童");
			} else if (Type == 2) {
				vh.leixingTxt.setText("婴儿");
			}
		} else {
			vh.usernameTxt.setVisibility(View.VISIBLE);
			vh.usernameTxt.setText(username);
			if("".equals(Idcard)||"null".equals(Idcard)){
				vh.mobTxt.setText("");
			}else{
				vh.mobTxt.setText(Idcard);
			}
			if (Type == 0) {
				vh.leixingTxt.setText("成人");
			} else if (Type == 1) {
				vh.leixingTxt.setText("儿童");
			} else if (Type == 2) {
				vh.leixingTxt.setText("婴儿");
			}
		}

		return convertView;
	}

	static class ViewHolder {
		TextView usernameTxt, mobTxt, leixingTxt;
		ImageView order_shanchu_item;
		LinearLayout order_item_list;

	}
}
