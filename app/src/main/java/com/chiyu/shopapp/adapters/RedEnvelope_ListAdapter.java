package com.chiyu.shopapp.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiyu.shopapp.bean.RedEnvelope;
import com.chiyu.shopapp.ui.R;

/**
 * listview adapter
 * 
 * @author Administrator
 * 
 */
public class RedEnvelope_ListAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	private List<RedEnvelope> list;
	private static LayoutInflater inflater = null;
	private boolean[] checkedItem;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;

	public RedEnvelope_ListAdapter(Context context) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public List<RedEnvelope> getData() {
		return list;
	}

	public void setData(List<RedEnvelope> list) {
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

	public boolean[] getCheckedItem() {

		for (int i = 0; i < getCount(); i++) {
			View view = this.getView(i, null, null);
			CheckBox box = (CheckBox) view.findViewById(R.id.ib_check);
			checkedItem[i] = box.isChecked();
		}

		return checkedItem;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.redenvelope_item, null);

			vh = new ViewHolder();

			vh.order_price_tx = (TextView) convertView
					.findViewById(R.id.order_price_tx);

			vh.order_leixing_tx = (TextView) convertView
					.findViewById(R.id.order_leixing_tx);

			vh.ib_check = (ImageView) convertView.findViewById(R.id.ib_check);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		try {
			vh.order_price_tx.setText("￥" + list.get(position).getAmount());
			
			String startDates = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long lcc_time = Long.valueOf(list.get(position).getExpiredTime());
			startDates = sdf.format(new Date(lcc_time));
			vh.order_leixing_tx.setText("有效期至:" + startDates);
			
//			String startDates = null;
//			SimpleDateFormat sdf = new SimpleDateFormat("M");
//			long lcc_time = Long.valueOf(list.get(position).getExpiredTime());
//			startDates = sdf.format(new Date(lcc_time * 1000L));
//			
//			
//
//			vh.order_leixing_tx.setText("失效时间：当年" + startDates+"月份");
			if (list.get(position).getIschoose().endsWith("1")) {
				vh.ib_check.setBackgroundResource(R.mipmap.zhifu_redbag_sel);
			} else {
				vh.ib_check
						.setBackgroundResource(R.mipmap.zhifu_redbag_bg_quan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		RedEnvelope_ListAdapter.isSelected = isSelected;
	}

	static class ViewHolder {

		TextView order_price_tx, order_leixing_tx, order_name_tx;
		// RadioButton order_shi_rb;
		ImageView ib_check;

	}
}