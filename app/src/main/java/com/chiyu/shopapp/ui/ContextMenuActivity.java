/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chiyu.shopapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.chiyu.shopapp.utils.Constant;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;


public class ContextMenuActivity extends Activity {
    public static final int RESULT_CODE_COPY = 1;
    public static final int RESULT_CODE_DELETE = 2;
    public static final int RESULT_CODE_FORWARD = 3;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		EMMessage message = getIntent().getParcelableExtra("message");
		
		int type = message.getType().ordinal();
		if (type == EMMessage.Type.TXT.ordinal()) {
//		    if(message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false) ||
//		            message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
//		        setContentView(R.layout.em_context_menu_for_location);
//		    }
		    if(message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
//		        setContentView(R.layout.em_context_menu_for_image);
		    }else{
		    	   
		        setContentView(R.layout.em_context_menu_for_text);	       
		    }
		} 
//		else if (type == EMMessage.Type.LOCATION.ordinal()) {
//		    setContentView(R.layout.em_context_menu_for_location);
//		} else if (type == EMMessage.Type.IMAGE.ordinal()) {
//		    setContentView(R.layout.em_context_menu_for_image);
//		} else if (type == EMMessage.Type.VOICE.ordinal()) {
//		    setContentView(R.layout.em_context_menu_for_voice);
//		} else if (type == EMMessage.Type.VIDEO.ordinal()) {
//			setContentView(R.layout.em_context_menu_for_video);
//		} else if (type == EMMessage.Type.FILE.ordinal()) {
//		    setContentView(R.layout.em_context_menu_for_location);
//		}
//		    
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void copy(View view){
		setResult(RESULT_CODE_COPY);
		finish();
	}
	public void delete(View view){
		setResult(RESULT_CODE_DELETE);
		finish();
	}
	public void forward(View view){
		setResult(RESULT_CODE_FORWARD);
		finish();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
	
}
