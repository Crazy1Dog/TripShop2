package com.chiyu.shopapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chiyu.shopapp.bean.CategoryLineEntity;
import com.chiyu.shopapp.ui.CategoryActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.ui.SearchActivity;
import com.chiyu.shopapp.utils.ScreenUtils;

public class GridViewAdapter extends AbsAdapter<CategoryLineEntity> {
	private Context context;
	public GridViewAdapter(Context context, int[] resid) {
		super(context, resid);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void bindDatas(
			ViewHolder viewHolder,
			CategoryLineEntity data, final int position) {
		// TODO Auto-generated method stub
		Button button = (Button) viewHolder.getView(R.id.btn_gridview);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("position", position);
				intent.setClass(context, CategoryActivity.class);
				context.startActivity(intent);
			}
		});
		button.setMaxWidth(ScreenUtils.getScreenWidth() / 4);
		button.setText(data.getCategoryName());
	}

}
