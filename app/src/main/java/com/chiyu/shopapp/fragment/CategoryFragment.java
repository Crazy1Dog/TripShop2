package com.chiyu.shopapp.fragment;

import java.util.ArrayList;
import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.CategoryActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.VolleyUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryFragment extends Fragment implements OnClickListener {
	private ImageView picture1;
	private ImageView picture2;
	private ImageView picture3;
	private ImageView picture4;
	private TextView title1;
	private TextView title2;
	private TextView title3;
	private TextView title4;
	private int pageNum;
	ArrayList<CategoryLineEntity> datas = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.category_fragment, null);
		initView(view);
		Bundle bundle = getArguments();
		pageNum = bundle.getInt("pageNum");
		datas = bundle.getParcelableArrayList("list");
//		Log.e(TAG, "传传传传传传传传传传传"+datas.toString());
		initDatas();
		return view;
	}
	//填充数据
	private void initDatas() {
		// TODO Auto-generated method stub
		if(datas != null){
			if(datas.size() == 1){
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(0).getIcon(), picture1, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title1.setText(datas.get(0).getCategoryName());
				title2.setVisibility(View.GONE);
				title3.setVisibility(View.GONE);
				title4.setVisibility(View.GONE);
				picture2.setVisibility(View.GONE);
				picture3.setVisibility(View.GONE);
				picture4.setVisibility(View.GONE);
				return;
			}
			 if (datas.size() == 2){
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(0).getIcon(), picture1, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title1.setText(datas.get(0).getCategoryName());
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(1).getIcon(), picture2, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title2.setText(datas.get(1).getCategoryName());
				title3.setVisibility(View.GONE);
				title4.setVisibility(View.GONE);
				picture3.setVisibility(View.GONE);
				picture4.setVisibility(View.GONE);
				return;
			}
			 if(datas.size() == 3){
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(0).getIcon(), picture1, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title1.setText(datas.get(0).getCategoryName());
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(1).getIcon(), picture2, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title2.setText(datas.get(1).getCategoryName());
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(2).getIcon(), picture3, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title3.setText(datas.get(2).getCategoryName());
				title4.setVisibility(View.GONE);
				picture4.setVisibility(View.GONE);
				return;
			}else if(datas.size() == 4){
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(0).getIcon(), picture1, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title1.setText(datas.get(0).getCategoryName());
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(1).getIcon(), picture2, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title2.setText(datas.get(1).getCategoryName());
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(2).getIcon(), picture3, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title3.setText(datas.get(2).getCategoryName());
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + datas.get(3).getIcon(), picture4, R.drawable.skt_icon_default, R.drawable.skt_icon_default);
				title4.setText(datas.get(3).getCategoryName());
			}
			
		}
	}
	//初始化Fragment中的各个控件
	private void initView(View view) {
		// TODO Auto-generated method stub
		picture1 = (ImageView) view.findViewById(R.id.iv_imageview1);
		picture2 = (ImageView) view.findViewById(R.id.iv_imageview2);
		picture3 = (ImageView) view.findViewById(R.id.iv_imageview3);
		picture4 = (ImageView) view.findViewById(R.id.iv_imageview4);
		title1 = (TextView) view.findViewById(R.id.tv_first);
		title2 = (TextView) view.findViewById(R.id.tv_second);
		title3 = (TextView) view.findViewById(R.id.tv_third);
		title4 = (TextView) view.findViewById(R.id.tv_fourth);
		picture1.setOnClickListener(this);
		picture2.setOnClickListener(this);
		picture3.setOnClickListener(this);
		picture4.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_imageview1:		
			Intent intent = new Intent();
			intent.putExtra("categoryNum", (pageNum - 1) * 4 + 1);
			intent.setClass(getActivity(), CategoryActivity.class);
//			Toast.makeText(getActivity(), "你点击了第个分类"+((pageNum - 1) * 4 + 1), Toast.LENGTH_SHORT).show();
			startActivity(intent);
			break;
		case R.id.iv_imageview2:
			Intent intent1 = new Intent();
			intent1.putExtra("categoryNum", (pageNum - 1) * 4 + 2);
			intent1.setClass(getActivity(), CategoryActivity.class);
//			Toast.makeText(getActivity(), "你点击了第个分类"+((pageNum - 1) * 4 + 2), Toast.LENGTH_SHORT).show();
			startActivity(intent1);
			break;
		case R.id.iv_imageview3:
			Intent intent2 = new Intent();
			intent2.putExtra("categoryNum", (pageNum - 1) * 4 + 3);
			intent2.setClass(getActivity(), CategoryActivity.class);
//			Toast.makeText(getActivity(), "你点击了第个分类"+((pageNum - 1) * 4 + 3), Toast.LENGTH_SHORT).show();
			startActivity(intent2);
			break;
		case R.id.iv_imageview4:
			Intent intent3 = new Intent();
			intent3.putExtra("categoryNum", (pageNum - 1) * 4 + 4);
			intent3.setClass(getActivity(), CategoryActivity.class);
//			Toast.makeText(getActivity(), "你点击了第个分类"+((pageNum - 1) * 4 + 4), Toast.LENGTH_SHORT).show();
			startActivity(intent3);
			break;

		default:
			break;
		}
	}

}
