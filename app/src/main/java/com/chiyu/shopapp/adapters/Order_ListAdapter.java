package com.chiyu.shopapp.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chiyu.shopapp.bean.OrderEntity;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.RoundImageView;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;

/**
 * listview adapter
 * 
 * @author
 * 
 */
public class Order_ListAdapter extends BaseAdapter {

	private Context context;
	private List<OrderEntity> list;
	private static LayoutInflater inflater = null;
//	public PhotoLoader imageLoader;

	public Order_ListAdapter(Context context) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		imageLoader = new PhotoLoader(context.getApplicationContext());
	}

	public List<OrderEntity> getData() {
		return list;
	}

	public void setData(List<OrderEntity> list) {
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

	@SuppressWarnings("unchecked")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (null == convertView) {

			convertView = inflater.inflate(R.layout.activity_order_item, null);

			vh = new ViewHolder();
			vh.order_ordercode_txt = (TextView) convertView
					.findViewById(R.id.order_ordercode_txt);
			vh.order_gotime_txt = (TextView) convertView
					.findViewById(R.id.order_gotime_txt);
			vh.order_title_txt = (TextView) convertView
					.findViewById(R.id.order_title_txt);
			vh.order_orderprice_txt = (TextView) convertView
					.findViewById(R.id.order_orderprice_txt);
			vh.order_image = (RoundImageView) convertView
					.findViewById(R.id.order_image);
			vh.order_zhifu_imbt = (ImageButton) convertView
					.findViewById(R.id.order_zhifu_imbt);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
			resetViewHolder(vh);
		}
		vh.order_ordercode_txt.setText(list.get(position).getOrderCode());
		vh.order_gotime_txt.setText(list.get(position).getGoTime());
		vh.order_orderprice_txt.setText("￥"+(list.get(position).getTotalPrice()));
		
		OrderEntity entity = ParseUtils.getOrder_LineInfo(list.get(position)
				.getOtherInfo());
		vh.order_title_txt.setText(entity.getLineTitle());
		String chanpintxt;
		try {
			if (entity.getLineTitle().length() >= 30) {
				chanpintxt = ShareUtil.SubString(
						entity.getLineTitle().toString(), 0, 28)
						+ "...";
			} else {
				chanpintxt = entity.getLineTitle();
			}

			vh.order_title_txt.setText(chanpintxt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = URL.IMAGE_DEBUG + entity.getPhoto();
		VolleyUtils.requestImage( url, vh.order_image,
				R.mipmap.ic_launcher, R.drawable.ic_launcher);
		int order_status = list.get(position).getOrderStatus();
		int pay_status = list.get(position).getPayStatus();
		if(order_status==5){
			vh.order_zhifu_imbt.setBackgroundResource(R.mipmap.dingdan_btn_quxiao);
		}else if(order_status==4){
			vh.order_zhifu_imbt.setBackgroundResource(R.mipmap.dingdan_btn_tuikuan);
		}else if(pay_status==1){
			vh.order_zhifu_imbt.setBackgroundResource(R.mipmap.dingdan_btn_yizhifu);
		}else{
			vh.order_zhifu_imbt.setBackgroundResource(R.mipmap.dingdan_btn_daizhifu);
		}
		return convertView;
	}

	private void resetViewHolder(ViewHolder vh) {
		// TODO Auto-generated method stub
		// vh.chanpinTxt.setText(null);
//		vh.integralTxt.setText(null);
//		vh.priceTxt.setText(null);
//		vh.line_jiesong_tx.setBackgroundDrawable(null);
//		vh.image.setBackgroundDrawable(null);
		// vh.line_tuanqi_txt.setText(null);
		// vh.line_type_btn.setBackgroundDrawable(null);
	}

	static class ViewHolder {

		TextView order_ordercode_txt, order_gotime_txt, order_title_txt,
				order_orderprice_txt;
		RoundImageView order_image;
		ImageButton order_zhifu_imbt;
	}

}