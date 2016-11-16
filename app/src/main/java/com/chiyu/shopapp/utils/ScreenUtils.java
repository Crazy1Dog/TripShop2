package com.chiyu.shopapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {
	private static WindowManager manager = null;
	public  static void initScreenUtils(Context content){
		manager = (WindowManager) content.getSystemService(Context.WINDOW_SERVICE);
	}
	public static int getScreenWidth(){
		DisplayMetrics dm = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
		
	}
	public static int getScreenHeight(){
		DisplayMetrics dm = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
		
	}
}
