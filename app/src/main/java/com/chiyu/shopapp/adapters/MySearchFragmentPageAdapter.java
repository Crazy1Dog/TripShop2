package com.chiyu.shopapp.adapters;

import java.util.List;
import com.chiyu.shopapp.fragment.SearchFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MySearchFragmentPageAdapter extends FragmentPagerAdapter {
	private List<SearchFragment>list;
	
	public MySearchFragmentPageAdapter(FragmentManager fm,
			List<SearchFragment> list) {
		super(fm);
		this.list = list;
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
