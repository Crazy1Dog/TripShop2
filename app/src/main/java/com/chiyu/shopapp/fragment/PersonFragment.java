package com.chiyu.shopapp.fragment;

import java.util.ArrayList;
import java.util.List;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.ui.SendFielActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
/*
 * 添加fragement
 * **/
public class PersonFragment extends Fragment implements OnPageChangeListener ,OnCheckedChangeListener{
	private RadioGroup rd_group;
	private RadioButton rgb_person,rgb_child,rgb_kids;
	private ViewPager viewpager_content;
	private List<Fragment> fragments;
	private Bundle personFragment;
	private Bundle childFragment;
	private Bundle kidsFragment;
	private MyViewPagerAdapter myPagerAdapter;
	public static String category = "0";
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.send_feil_fragment, null);
		rd_group = (RadioGroup) view.findViewById(R.id.rd_group);
		rgb_person = (RadioButton) view.findViewById(R.id.rgb_person);
		rgb_child = (RadioButton) view.findViewById(R.id.rgb_child);
		rgb_kids = (RadioButton) view.findViewById(R.id.rgb_kids);
		viewpager_content = (ViewPager) view.findViewById(R.id.viewpager_content);
		addFragment();
		myPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
		viewpager_content.setAdapter(myPagerAdapter);
		viewpager_content.setOnPageChangeListener(this);
		completePersonInfo();
		
		rd_group.setOnCheckedChangeListener(this);
		return view;
	}
	private void completePersonInfo() {
		if(SendFielActivity.category != null && SendFielActivity.category.equals("0")){
			((RadioButton)rd_group.getChildAt(0)).setChecked(true);
			category  = "0";
			viewpager_content.setCurrentItem(0);
		}else if(SendFielActivity.category != null && SendFielActivity.category.equals("1")){
			((RadioButton)rd_group.getChildAt(1)).setChecked(true);
			category  = "1";
			viewpager_content.setCurrentItem(1);
		}else if(SendFielActivity.category != null && SendFielActivity.category.equals("2")){
			((RadioButton)rd_group.getChildAt(2)).setChecked(true);
			category  = "2";
			viewpager_content.setCurrentItem(2);
		}else{
			((RadioButton)rd_group.getChildAt(0)).setChecked(true);
			category  = "0";
		}
	}
	private void addFragment() {
		//通过fragment的类型添加实际fragment
		fragments = new ArrayList<Fragment>();
				personFragment = new Bundle();
				personFragment.putString("category", "0");
				PersonFragmentModle myPersonFragment = new PersonFragmentModle();
				myPersonFragment.setArguments(personFragment);
				fragments.add(myPersonFragment);
			
			
				childFragment = new Bundle();
				childFragment.putString("category", "1");
				PersonFragmentModle myPersonFragment1 = new PersonFragmentModle();
				myPersonFragment1.setArguments(childFragment);
				fragments.add(myPersonFragment1);
			
			
				kidsFragment = new Bundle();
				kidsFragment.putString("category", "2");
				PersonFragmentModle myPersonFragment2 = new PersonFragmentModle();
				myPersonFragment2.setArguments(kidsFragment);
				fragments.add(myPersonFragment2);
		
	}
	private class MyViewPagerAdapter extends FragmentPagerAdapter {

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}

	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageSelected(int position) {
		for(int i = 0;i<rd_group.getChildCount();i++){
			RadioButton rgb = (RadioButton) rd_group.getChildAt(i);
			if( i == position){
				if(rgb_person == rd_group.getChildAt(i)){
					category = "0";
				}else if(rgb_child == rd_group.getChildAt(i)){
					category = "1";
				}else{
					category = "2";
				}
				rgb.setChecked(true);
				rgb.setTextColor(Color.parseColor("#29CCB8"));
				
			}else{
				rgb.setChecked(false);
				rgb.setTextColor(Color.parseColor("#252525"));
			}
		}
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
			for(int i = 0; i < rd_group.getChildCount(); i++){
				RadioButton rb = (RadioButton) rd_group.getChildAt(i);
				if(rb.getId() == checkedId ){
					viewpager_content.setCurrentItem(i);
					if(checkedId == rgb_person.getId()){
						category = "0";
					}else if(checkedId == rgb_child.getId()){
						category = "1";
					}else{
						category = "2";
					}
					
					rb.setChecked(true);
					rb.setTextColor(Color.parseColor("#29CCB8"));
				}else{
					rb.setChecked(false);
					rb.setTextColor(Color.parseColor("#252525"));
				}
			}
		}
	
}
