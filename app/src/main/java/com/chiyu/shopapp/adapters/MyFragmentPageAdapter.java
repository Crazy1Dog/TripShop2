package com.chiyu.shopapp.adapters;

import java.util.List;

import com.chiyu.shopapp.fragment.CategoryFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {
	List<CategoryFragment> list;
	Context context;
	public MyFragmentPageAdapter(FragmentManager fm,
			List<CategoryFragment> list, Context context) {
		super(fm);
		this.list = list;
		this.context = context;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
