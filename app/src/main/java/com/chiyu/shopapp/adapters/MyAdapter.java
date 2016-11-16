package com.chiyu.shopapp.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chiyu.shopapp.bean.FeilName;
import com.chiyu.shopapp.fragment.PersonFragmentModle;
import com.chiyu.shopapp.ui.ContactActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.ui.SendFielActivity;
import com.chiyu.shopapp.utils.ShareUtil;

public class MyAdapter extends MyBaseAdapter<FeilName> {
	private Context context;
	private List<FeilName> contactList;
	private FeilName feilName;
	private FeilName feilName2;

	/** 标记CheckBox是否被选中 **/
	private static List<Boolean> mChecked;
	public static final List<FeilName> sendList = new ArrayList<FeilName>();
	@SuppressLint("UseSparseArrays")
	HashMap<Integer, View> map = new HashMap<Integer, View>();
	@SuppressLint("UseSparseArrays")
	public MyAdapter(List<FeilName> contactList, Context context) {
		super(context, contactList);
		this.context = context;
		this.contactList = contactList;
		mChecked = new ArrayList<Boolean>();
		for (int i = 0; i < contactList.size(); i++) {// 遍历且设置CheckBox默认状态为未选中
			mChecked.add(false);
		}
	}	

	public static void clearSendList() {
		sendList.clear();
	}

	public void addDatas(FeilName datas) {
		this.contactList.add(datas);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder mViewHolder = null;
		if (map.get(position) == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mInflater.inflate(R.layout.send_feil_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.checkbox_check = (CheckBox) view
					.findViewById(R.id.checkbox_check);
			mViewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
			mViewHolder.tv_type = (TextView) view.findViewById(R.id.tv_type);
			mViewHolder.tv_card_num = (TextView) view
					.findViewById(R.id.tv_card_num);
			mViewHolder.re_contact_item = (RelativeLayout) view.findViewById(R.id.re_contact_item);
			map.put(position, view);// 存储视图信息
			if(SendFielActivity.personalFragment != null || SendFielActivity.bianji != null){
				mViewHolder.checkbox_check.setVisibility(View.INVISIBLE);
			}else{
				mViewHolder.checkbox_check.setVisibility(View.VISIBLE);
			mViewHolder.checkbox_check
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							CheckBox cb = (CheckBox) v;

							if (cb.isChecked()) {
								feilName2 = contactList.get(position);
								sendList.add(feilName2);
								Log.e("是否选中了", sendList.toString());
							}else if(cb.isChecked()== false){
								feilName2 = contactList.get(position);
								sendList.remove(feilName2);
							}
						}
					});
			}
			if(SendFielActivity.personalFragment != null ||  SendFielActivity.bianji != null){
				mViewHolder.re_contact_item.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,ContactActivity.class);
						intent.putExtra("name", contactList.get(position).getName());
						intent.putExtra("cardnum", contactList.get(position).getIdcard());
						intent.putExtra("category", contactList.get(position).getCategory());
						intent.putExtra("flag", "MyAdapter");
						intent.putExtra("cardcategory", contactList.get(position).getCardcategory());
						intent.putExtra("mobile", contactList.get(position).getMobile());
						intent.putExtra("sex", contactList.get(position).getGender());
						intent.putExtra("Id", contactList.get(position).getId());
						context.startActivity(intent);
					}
				});
			}
			view.setTag(mViewHolder);
		} else {
			view = map.get(position);
			mViewHolder = (ViewHolder) view.getTag();
		}
		
			feilName = contactList.get(position);
		
		mViewHolder.tv_name.setText(feilName.getName());
		if ("0".equals(feilName.getCategory())) {
			mViewHolder.tv_type.setText("成人");
		} else if ("1".equals(feilName.getCategory())) {
			mViewHolder.tv_type.setText("儿童");
		} else {
			mViewHolder.tv_type.setText("婴儿");
		}
//		String cardNum = (feilName.getIdcard()).substring(7, 13);
//		String idcardStart = (feilName.getIdcard()).substring(0, 6);
//		String idcardEnd = (feilName.getIdcard()).substring(14, 18);
//		String idCards = cardNum.replace(cardNum, "********");
		mViewHolder.tv_card_num.setText(feilName.getIdcard());
		return view;
	}

	class ViewHolder {
		CheckBox checkbox_check;
		TextView tv_name;
		TextView tv_type;
		TextView tv_card_num;
		RelativeLayout re_contact_item;
	}
}
