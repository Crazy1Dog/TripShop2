package com.chiyu.shopapp.ui;

import java.util.Calendar;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.utils.ScreenUtils;
import com.hyphenate.easeui.controller.EaseUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DatePickerActivity extends Activity {
	private int mYear;
	private int mMonth;
	private int mDay;
	private Calendar calendar;
	private DatePicker datePicker;
	private Button cancelButton;
	private Button ensureButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_picker);
		MyApp.getInstance().addActivity(this);
		calendar = Calendar.getInstance();
		datePicker = (DatePicker) findViewById(R.id.date_picker);
		cancelButton = (Button) findViewById(R.id.btn_cancel1);
		ensureButton = (Button) findViewById(R.id.btn_ensure);
		Window window = getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setLayout(LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight() / 2);
		//设置日期简略显示 否则详细显示 包括:星期周
        //datePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
		ensureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 StringBuffer sb = new StringBuffer(); 
                 sb.append(String.format("%d-%02d-%02d",  
                         datePicker.getYear(),  
                         datePicker.getMonth() + 1, 
                         datePicker.getDayOfMonth())); 
                 //赋值后面闹钟使用
                 mYear = datePicker.getYear();
                 mMonth = datePicker.getMonth();
                 mDay = datePicker.getDayOfMonth();
                 Intent intent = new Intent();
                 intent.putExtra("date",sb.toString());
                 setResult(1000, intent);
                 finish();
			}
		});
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
	
}
