package com.chiyu.shopapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyEditText extends LinearLayout {

	private TextView mEditText;
	public  ImageButton bAdd;
	public  ImageButton bReduce;

	public MyEditText(final Context ctxt, AttributeSet attrs) {
		super(ctxt, attrs);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();

		LayoutInflater.from(getContext()).inflate(R.layout.myedittext, this);
		init_widget();
		addListener();

	}
	
	public void init_widget() {

		mEditText = (TextView) findViewById(R.id.booking_adult_number_txt);
		bAdd = (ImageButton) findViewById(R.id.booking_adult_minus_bt);
		bReduce = (ImageButton) findViewById(R.id.booking_adult_plus_bt);
		mEditText.setText("0");
	}

	public void addListener() {
		bAdd.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num = Integer.valueOf(mEditText.getText().toString());
				if (num > 0) {
					num--;
					mEditText.setText(Integer.toString(num));
				}

			}
		});

		bReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num = Integer.valueOf(mEditText.getText().toString());
				num++;
				mEditText.setText(Integer.toString(num));
			}
		});
	}
	
}
