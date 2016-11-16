package com.chiyu.shopapp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

public class TripShopHandler implements UncaughtExceptionHandler{
	public static final String TAG="com.chiyu.shopapp";
	private UncaughtExceptionHandler mDeExceptionHandler;
	private static TripShopHandler haolinongHandler=new TripShopHandler();
	private Context mContext;
	private Map<String, String>mHaolinongLo=new HashMap<String, String>();
	@SuppressLint("SimpleDateFormat") 
	private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH-mm-ss") ;
	private TripShopHandler() {
	}
	public static TripShopHandler getInstance() {
		return haolinongHandler ;
	}
	public void init(Context paramContext) {
		mContext = paramContext ;
		mDeExceptionHandler = Thread.getDefaultUncaughtExceptionHandler() ;
		Thread.setDefaultUncaughtExceptionHandler(this) ;
	}
	@SuppressWarnings("static-access")
	public void uncaughtException(Thread paramThread , Throwable paramThrowable) {
		if( ! handleException(paramThrowable) && mDeExceptionHandler != null) {
			mDeExceptionHandler.uncaughtException(paramThread , paramThrowable) ;
		}
		else {
			try {
				paramThread.sleep(1000) ;
			}
			catch(InterruptedException e) {
				e.printStackTrace() ;
			}
			android.os.Process.killProcess(android.os.Process.myPid()) ;
			System.exit(1) ;
		}
	}
	public boolean handleException(Throwable paramThrowable) {
		if(paramThrowable == null)
			return false ;
		new Thread() {
			public void run() {
				Looper.prepare() ;
//				Toast.makeText(mContext , "很抱歉,程序出现异常,即将退出" , 0).show() ;
				//此处存在bug
				Looper.loop() ;
			}
		}.start() ;
		getDeviceInfo(mContext) ;
		saveCrashLogToFile(paramThrowable) ;
		return true ;
	}
	public void getDeviceInfo(Context paramContext) {
		try {
			PackageManager mPackageManager = paramContext.getPackageManager() ;
			PackageInfo mPackageInfo = mPackageManager.getPackageInfo(
					paramContext.getPackageName() , PackageManager.GET_ACTIVITIES) ;
			if(mPackageInfo != null) {
				String versionName = mPackageInfo.versionName == null ? "null"
						: mPackageInfo.versionName ;
				String versionCode = mPackageInfo.versionCode + "" ;
				mHaolinongLo.put("versionName" , versionName) ;
				mHaolinongLo.put("versionCode" , versionCode) ;
			}
		}
		catch(NameNotFoundException e) {
			e.printStackTrace() ;
		}
		// 反射机制
		Field[] mFields = Build.class.getDeclaredFields() ;
		// 迭代Build的字段key-value  此处的信息主要是为了在服务器端手机各种版本手机报错的原因
		for(Field field : mFields) {
			try {
				field.setAccessible(true) ;
				mHaolinongLo.put(field.getName() , field.get("").toString()) ;
				Log.d(TAG , field.getName() + ":" + field.get("")) ;
			}
			catch(IllegalArgumentException e) {
				e.printStackTrace() ;
			}
			catch(IllegalAccessException e) {
				e.printStackTrace() ;
			}
		}
	}
	private String saveCrashLogToFile(Throwable paramThrowable) {
		StringBuffer mStringBuffer = new StringBuffer() ;
		for(Map.Entry<String , String> entry : mHaolinongLo.entrySet()) {
			String key = entry.getKey() ;
			String value = entry.getValue() ;
			mStringBuffer.append(key + "=" + value + "\r\n") ;
		}
		Writer mWriter = new StringWriter() ;
		PrintWriter mPrintWriter = new PrintWriter(mWriter) ;
		paramThrowable.printStackTrace(mPrintWriter) ;
		paramThrowable.printStackTrace();
		Throwable mThrowable = paramThrowable.getCause() ;
		while(mThrowable != null) {
			mThrowable.printStackTrace(mPrintWriter) ;
			mPrintWriter.append("\r\n") ;
			mThrowable = mThrowable.getCause() ;
		}
		mPrintWriter.close() ;
		String mResult = mWriter.toString() ;
		mStringBuffer.append(mResult) ;
		String mTime = mSimpleDateFormat.format(new Date()) ;
		String mFileName = "tripshop-" + mTime + ".log" ;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				File mDirectory = new File(Environment.getExternalStorageDirectory()
						+ "/tripshop") ;
				Log.v(TAG , mDirectory.toString()) ;
				if( ! mDirectory.exists())
					mDirectory.mkdir() ;
				FileOutputStream mFileOutputStream = new FileOutputStream(mDirectory + "/"
						+ mFileName) ;
				mFileOutputStream.write(mStringBuffer.toString().getBytes()) ;
				mFileOutputStream.close() ;
				return mFileName ;
			}
			catch(FileNotFoundException e) {
				e.printStackTrace() ;
			}
			catch(IOException e) {
				e.printStackTrace() ;
			}
		}
		return null ;
	}
}
