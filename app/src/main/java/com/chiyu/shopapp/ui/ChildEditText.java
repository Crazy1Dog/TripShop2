package com.chiyu.shopapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChildEditText extends LinearLayout {

	private TextView child_mEditTexter;
	private ImageButton child_bAdder;
	private ImageButton child_bReduceer;

	// 这里的构造一定是两个参数�?
	public ChildEditText(final Context ctxt, AttributeSet attrs) {
		super(ctxt, attrs);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();

		LayoutInflater.from(getContext()).inflate(R.layout.child_edittext, this);
		init_widget();
		addListener();

	}

	public void init_widget() {

		child_mEditTexter = (TextView) findViewById(R.id.booking_child_number_txt);
		child_bAdder = (ImageButton) findViewById(R.id.booking_child_minus_bt);
		child_bReduceer = (ImageButton) findViewById(R.id.booking_child_plus_bt);
		child_mEditTexter.setText("0");
	}

	public void addListener() {
		child_bAdder.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num = Integer.valueOf(child_mEditTexter.getText().toString());
				if (num > 0) {
					num--;
					child_mEditTexter.setText(Integer.toString(num));
				}

			}
		});

		child_bReduceer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num = Integer.valueOf(child_mEditTexter.getText().toString());
				num++;
				child_mEditTexter.setText(Integer.toString(num));
			}
		});
	}
}
