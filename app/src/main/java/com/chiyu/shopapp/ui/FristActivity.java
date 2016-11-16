package com.chiyu.shopapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hyphenate.easeui.controller.EaseUI;

/**
 * 启动页面
 */
public class FristActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_toast);
    }

    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}

	
}
