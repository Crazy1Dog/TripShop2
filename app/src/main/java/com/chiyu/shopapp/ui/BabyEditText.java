package com.chiyu.shopapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BabyEditText extends LinearLayout {

	private TextView baby_mEditTexter;
	private ImageButton baby_bAdder;
	private ImageButton baby_bReduceer;

	public BabyEditText(final Context ctxt, AttributeSet attrs) {
		super(ctxt, attrs);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();

		LayoutInflater.from(getContext()).inflate(R.layout.baby_edittext, this);
		init_widget();
		addListener();

	}

	public void init_widget() {

		baby_mEditTexter = (TextView) findViewById(R.id.booking_baby_number_txt);
		baby_bAdder = (ImageButton) findViewById(R.id.booking_baby_minus_bt);
		baby_bReduceer = (ImageButton) findViewById(R.id.booking_baby_plus_bt);
		baby_mEditTexter.setText("0");
	}

	public void addListener() {
		baby_bAdder.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num = Integer.valueOf(baby_mEditTexter.getText().toString());
				if (num > 0) {
					num--;
					baby_mEditTexter.setText(Integer.toString(num));
				}

			}
		});

		baby_bReduceer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num = Integer.valueOf(baby_mEditTexter.getText().toString());
				num++;
				baby_mEditTexter.setText(Integer.toString(num));
			}
		});
	}
}
