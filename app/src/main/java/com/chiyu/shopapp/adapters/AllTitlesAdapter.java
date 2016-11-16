package com.chiyu.shopapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.ui.CategoryActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.ui.SearchActivity;
import com.chiyu.shopapp.utils.ScreenUtils;

public class AllTitlesAdapter extends AbsAdapter<CategoryLineEntity> {
	private Context context;
	public AllTitlesAdapter(Context context, int[] resid) {
		super(context, resid);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void bindDatas(
			ViewHolder viewHolder,
			CategoryLineEntity data, final int position) {
		// TODO Auto-generated method stub
		TextView title = (TextView) viewHolder.getView(R.id.btn_gridview);
//		title.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "点击的位置"+position, 0).show();
//			}
//		});
		title.setMaxWidth(ScreenUtils.getScreenWidth() / 4);
		title.setText(data.getCategoryName());
	}

}
