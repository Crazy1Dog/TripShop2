package com.chiyu.shopapp.view;

import android.content.Context;
import android.widget.ListView;

public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
//		this.setHeaderDividersEnabled(false);
//		this.setFooterDividersEnabled(false);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		    super.onMeasure(widthMeasureSpec, expandSpec);
		
	}
}
