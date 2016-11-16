package com.chiyu.shopapp.ui;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.FeilName;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.fragment.PersonFragment;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * 扫描身份证，联系人
 * */
public class ContactActivity extends FragmentActivity implements
		OnClickListener, TextWatcher{
	ImageView iv_title;
	// RelativeLayout re_scan;
	// ImageView iv_scan;
	TextView ed_tourist_type_content;
	EditText ed_name_content;
	TextView ed_sex_content;
	TextView ed_card_content;
	EditText ed_card_num_content;
	EditText ed_phone_nums;
//	ImageButton category_check;
//	ImageButton sex_check;
//	ImageButton cardtype_check;
	RadioGroup re_category;
	RadioGroup ll_sex;
	RadioGroup re_cardtype;
	View fengexian1;
	View fengexian2;
	View fengexian3;
	Button btn_finish;
	String touristType;
	String name;
	String sex;
	String cartType;
//	String cardNum;
	String phoneNum;
	FeilName feilName = new FeilName();
	String flag = "ContactActivity";
	String categorys;
	String gender;
	String cardcategory;
	List<FeilName> list;
	String tag;
	String category;
	String flagMyAdapter;
	private static String personalFragment;
	private String Fromtag;
	private RelativeLayout re_tourist_type,re_sex,re_card;
	public String category2;
	public String personalFragment2;
	private String tongxunlu;
	private String cardnum;
	private String cardcategoryMyAdapter;
	private String mobileMyAdapter;
	private String sexMyAdapter;
	private String userIdMyAdapter;
	String Id;//通讯录编辑时传递的
	private String bianji2;
	private String tagChatFragment;
	private String xinzeng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_activity);
		Intent intent = getIntent();
		tag = intent.getStringExtra("tag");
		tag = intent.getStringExtra("tagChatFragment");
		name = intent.getStringExtra("name");
		cardnum = intent.getStringExtra("cardnum");
		Fromtag = intent.getStringExtra("Fromtag");
		personalFragment = intent.getStringExtra("personalFragment");
		personalFragment2 = intent.getStringExtra("personalFragment2");
		category = intent.getStringExtra("category");
		category2 = intent.getStringExtra("category2");
		flagMyAdapter = intent.getStringExtra("flag");
		tongxunlu = intent.getStringExtra("tongxunlu");
		mobileMyAdapter = intent.getStringExtra("mobile");
		sexMyAdapter = intent.getStringExtra("sex");
		bianji2 = intent.getStringExtra("bianji2");
		xinzeng = intent.getStringExtra("xinzeng");
		cardcategoryMyAdapter = intent.getStringExtra("cardcategory");
		Id = intent.getStringExtra("Id");
		initView();
		initEvent();
		touristType = ed_tourist_type_content.getText().toString().trim();
	}

	private void initEvent() {
		// ed_tourist_type_content.addTextChangedListener(this);
		ed_name_content.addTextChangedListener(this);
		// ed_sex_content.addTextChangedListener(this);
		// ed_card_content.addTextChangedListener(this);
		ed_card_num_content.addTextChangedListener(this);
		ed_phone_nums.addTextChangedListener(this);
		iv_title.setOnClickListener(this);
		btn_finish.setOnClickListener(this);
		re_tourist_type.setOnClickListener(this);
		re_sex.setOnClickListener(this);
		re_card.setOnClickListener(this);		
	}

	private void initView() {
		iv_title = (ImageView) findViewById(R.id.iv_title);
		ed_tourist_type_content = (TextView) findViewById(R.id.ed_tourist_type_content);
		ed_name_content = (EditText) findViewById(R.id.ed_name_content);
		ed_sex_content = (TextView) findViewById(R.id.ed_sex_content);
		ed_card_content = (TextView) findViewById(R.id.ed_card_content);
		ed_card_num_content = (EditText) findViewById(R.id.ed_card_num_content);
		ed_phone_nums = (EditText) findViewById(R.id.ed_phone_nums);
		btn_finish = (Button) findViewById(R.id.btn_finish);
		re_tourist_type = (RelativeLayout) findViewById(R.id.re_tourist_type);
		re_sex = (RelativeLayout) findViewById(R.id.re_sex);
		re_card = (RelativeLayout) findViewById(R.id.re_card);
		re_category = (RadioGroup) findViewById(R.id.re_category);
		ll_sex = (RadioGroup) findViewById(R.id.ll_sex);
		re_cardtype = (RadioGroup) findViewById(R.id.re_cardtype);
		getContactCategory();
		if(flagMyAdapter != null && Fromtag == null){
			
		}else{
			ed_card_content.setText("身份证");
		}
		
//		touristType = ed_tourist_type_content.getText().toString().trim();
		cartType = ed_card_content.getText().toString().trim();
		 fengexian1 = findViewById(R.id.fengexian1);
		 fengexian2 = findViewById(R.id.fengexian2);
		 fengexian3 = findViewById(R.id.fengexian3);
	}

	private void getContactCategory() {
		//通讯录中是否可编辑的判断
		if(flagMyAdapter != null && Fromtag == null){
			userIdMyAdapter = Id;
			ed_name_content.setText(name);
			ed_card_num_content.setText(cardnum);
			ed_phone_nums.setText(mobileMyAdapter);
			if (cardcategoryMyAdapter.equals("1")) {
				ed_card_content.setText("身份证");
			}else if(cardcategoryMyAdapter.equals("2")){
				ed_card_content.setText("护照");
			}else if(cardcategoryMyAdapter.equals("3")){
				ed_card_content.setText("港澳通行证");
			}else if(cardcategoryMyAdapter.equals("4")){
				ed_card_content.setText("台胞证");
			}else if(cardcategoryMyAdapter.equals("5")){
				ed_card_content.setText("海员证");
			}else if(cardcategoryMyAdapter.equals("6")){
				ed_card_content.setText("旅行证");
			}else{
				ed_card_content.setText("其他");
			}
			if(sexMyAdapter.equals("1")){
				ed_sex_content.setText("男");
			}else if(sexMyAdapter.equals("2")){
				ed_sex_content.setText("女");
			}else{
				ed_sex_content.setText("保密");
			}
			if(PersonFragment.category.equals("0")){
				ed_tourist_type_content.setText("成人");
			}else if(PersonFragment.category.equals("1")){
				ed_tourist_type_content.setText("儿童");
			}else if(PersonFragment.category.equals("2")){
				ed_tourist_type_content.setText("婴儿");
			}
			
			return;
		}
		if(bianji2 != null || personalFragment2 != null){
			if(PersonFragment.category != null && Fromtag == null){
				if(PersonFragment.category.equals("0")){
					ed_tourist_type_content.setText("成人");
				}else if(PersonFragment.category.equals("1")){
					ed_tourist_type_content.setText("儿童");
				}else if(PersonFragment.category.equals("2")){
					ed_tourist_type_content.setText("婴儿");
				}
			}
			return;
		}
		if(xinzeng != null || personalFragment != null){
			if(PersonFragment.category != null && Fromtag == null){
				if(PersonFragment.category.equals("0")){
					ed_tourist_type_content.setText("成人");
				}else if(PersonFragment.category.equals("1")){
					ed_tourist_type_content.setText("儿童");
				}else if(PersonFragment.category.equals("2")){
					ed_tourist_type_content.setText("婴儿");
				}
			}
			return;
		}
		//通讯录的判断
		if(bianji2 != null && Fromtag == null && tongxunlu != null || personalFragment2 != null && Fromtag == null && tongxunlu != null){
			
			ed_tourist_type_content.setText("成人");
			if(PersonFragment.category != null && Fromtag == null){
				if(PersonFragment.category.equals("0")){
					ed_tourist_type_content.setText("成人");
				}else if(PersonFragment.category.equals("1")){
					ed_tourist_type_content.setText("儿童");
				}else if(PersonFragment.category.equals("2")){
					ed_tourist_type_content.setText("婴儿");
				}
			}
			return;
		}
		//chat点击发名单的判断
		if(tag != null && tag.equals("ChatFragment") && flagMyAdapter == null){
			ed_tourist_type_content.setText("成人");
			if(PersonFragment.category.equals("0")){
				ed_tourist_type_content.setText("成人");
			}else if(PersonFragment.category.equals("1")){
				ed_tourist_type_content.setText("儿童");
			}else if(PersonFragment.category.equals("2")){
				ed_tourist_type_content.setText("婴儿");
			}
		}
		
		else{
			//完善信息里的判断
			if(category2 != null && category2.equals("0") && Fromtag != null){
				ed_tourist_type_content.setText("成人");
				if(PersonFragment.category != null && Fromtag != null){
					if(PersonFragment.category.equals("1")){
						ed_tourist_type_content.setText("儿童");
					}else if(PersonFragment.category.equals("0")){
						ed_tourist_type_content.setText("成人");
					}else if(PersonFragment.category.equals("2")){
						ed_tourist_type_content.setText("婴儿");
					}
				}
				
			}else if(category2 != null && category2.equals("1") && Fromtag != null ){
				ed_tourist_type_content.setText("儿童");
				if(PersonFragment.category != null && Fromtag != null){
					if(PersonFragment.category.equals("0")){
						ed_tourist_type_content.setText("成人");
					}else if(PersonFragment.category.equals("1")){
						ed_tourist_type_content.setText("儿童");
					}else if(PersonFragment.category.equals("2")){
						ed_tourist_type_content.setText("婴儿");
					}
				}
				
			}else{
				ed_tourist_type_content.setText("婴儿");
				if(PersonFragment.category != null && Fromtag != null){
					if(PersonFragment.category.equals("0")){
						ed_tourist_type_content.setText("成人");
					}else if(PersonFragment.category.equals("1")){
						ed_tourist_type_content.setText("儿童");
					}else if(PersonFragment.category.equals("2")){
						ed_tourist_type_content.setText("婴儿");
					}
				}
			}
		}
		
	}

	private void getUpadteContact() {
		
		if (touristType.equals("成人")) {
			categorys = "0";
		} else if (touristType.equals("儿童")) {
			categorys = "1";
		} else {
			categorys = "2";
		}
		if ("男".equals(ed_sex_content.getText().toString().trim())) {
			gender = "1";
		} else if ("女".equals(ed_sex_content.getText().toString().trim())) {
			gender = "2";
		}else{
			gender = "0";
		}
		if (ed_card_content.getText().toString().trim().equals("身份证")) {
			cardcategory = "1";
		}else if(ed_card_content.getText().toString().trim().equals("护照")){
			cardcategory = "2";
		}else if(ed_card_content.getText().toString().trim().equals("港澳通行证")){
			cardcategory = "3";
		}else if(ed_card_content.getText().toString().trim().equals("台胞证")){
			cardcategory = "4";
		}else if(ed_card_content.getText().toString().trim().equals("海员证")){
			cardcategory = "5";
		}else if(ed_card_content.getText().toString().trim().equals("旅行证")){
			cardcategory = "6";
		}else{
			cardcategory = "7";
		}
		/**
		 *  参数列表：[ 
		   id: 待删除记录用户id 表主键 必填
		   userId: 当前用户id 必填
		   name: 目标用户姓名 
		   gender: 目标用户性别 
		   category: 目标用户类型 
		   cardcategory: 目标用户证件类型 
		   idcard: 目标用户证件号 
		   mobile: 目标用户手机号 
		 * **/
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", userIdMyAdapter);
		map.put("userId", ShareUtil.getString("userId"));
		map.put("name", name);
		map.put("gender", gender);
		map.put("category", categorys);
		map.put("cardcategory", cardcategory);
		map.put("idcard", cardnum);
		map.put("mobile", ed_phone_nums.getText().toString().trim());
		VolleyUtils.requestString_Post(map, URL.DEBUG + URL.UPDATE_CONTACT, new OnRequest() {
			
			@Override
			public void response(String url, String response) {
				list = ParseUtils.getContactInfo(response.toString());
			}
			
			@Override
			public void errorResponse(String url, VolleyError error) {
			}
		});
	}

	@Override
	public void afterTextChanged(Editable s) {	
		name = ed_name_content.getText().toString().trim();	
		cardnum = ed_card_num_content.getText().toString().trim();
		phoneNum = ed_phone_nums.getText().toString().trim();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		CharSequence c = s;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title:
			finish();
			break;
		case R.id.btn_finish:
			getTrueOrFalse();
			break;
		case R.id.re_tourist_type:

			if (re_category.getVisibility() == View.GONE) {
				re_category.setVisibility(View.VISIBLE);
				getCategoryCheck();
				fengexian1.setVisibility(View.VISIBLE);
			} else {
				re_category.setVisibility(View.GONE);
				fengexian1.setVisibility(View.GONE);
			}
			break;
		case R.id.re_sex:
			if (ll_sex.getVisibility() == View.GONE) {
				ll_sex.setVisibility(View.VISIBLE);
				fengexian2.setVisibility(View.VISIBLE);
				getSexCheck();
			} else {
				ll_sex.setVisibility(View.GONE);
				fengexian2.setVisibility(View.GONE);
			}

			break;
		case R.id.re_card:
			if (re_cardtype.getVisibility() == View.GONE) {
				re_cardtype.setVisibility(View.VISIBLE);
				fengexian3.setVisibility(View.VISIBLE);
				getCardTypeCheck();
				
			} else {
				re_cardtype.setVisibility(View.GONE);
				fengexian3.setVisibility(View.GONE);
			}

			break;

		default:
			break;
		}
	}

	private void getTrueOrFalse() {
		if(ed_tourist_type_content.getText().equals("成人")){
			 if (ed_tourist_type_content.getText().equals("请输入游客姓名")
						|| ed_name_content.getText().toString() == null
						|| "".equals(ed_name_content.getText().toString())) {
					Toast.makeText(this, "游客姓名不能为空,请输入", Toast.LENGTH_SHORT)
							.show();
				} else if ("".equals(ed_sex_content.getText().toString().trim()) || ed_sex_content.getText().toString().trim() == null) {
					Toast.makeText(this, "游客性别不能为空,请选择", Toast.LENGTH_SHORT)
							.show();
				} else if (ed_card_num_content.getText().toString() == null
						|| "".equals(ed_card_num_content.getText().toString())) {
					Toast.makeText(this, "证件号码不能为空,请输入", Toast.LENGTH_SHORT)
							.show();
				} else if (ed_card_content.getText().equals("身份证")
						&& ed_card_num_content.getText().toString().trim().length() != 18) {
					Toast.makeText(this, "身份证号码为18位,请核对", Toast.LENGTH_SHORT)
							.show();
				} else if (ed_card_content.getText().equals("护照")
						&& ed_card_num_content.getText().toString().trim().length() != 9) {
					Toast.makeText(this, "护照号码为9位,请核对", Toast.LENGTH_SHORT)
							.show();
				}else {
					if(flagMyAdapter != null && Fromtag == null){
						getUpadteContact();
						sendContact1();
					}else{
						getContact();
						sendContact();
					}
					
					
				}
		
			return;
		}
		if (ed_tourist_type_content.getText().equals("儿童")){
			if (ed_tourist_type_content.getText().equals("请输入游客姓名")
					|| ed_name_content.getText().toString() == null
					|| "".equals(ed_name_content.getText().toString())) {
				Toast.makeText(this, "游客姓名不能为空,请输入", Toast.LENGTH_SHORT)
						.show();
			} else if ("".equals(ed_sex_content.getText().toString().trim()) || ed_sex_content.getText().toString().trim() == null) {
				Toast.makeText(this, "游客性别不能为空,请选择", Toast.LENGTH_SHORT)
						.show();
			} else if (ed_card_num_content.getText().toString() == null
					|| "".equals(ed_card_num_content.getText().toString())) {
				Toast.makeText(this, "证件号码不能为空,请输入", Toast.LENGTH_SHORT)
						.show();
			} else if (ed_card_content.getText().equals("身份证")
					&& ed_card_num_content.getText().toString().trim().length() != 18) {
				Toast.makeText(this, "身份证号码为18位,请核对", Toast.LENGTH_SHORT)
						.show();
			} else if (ed_card_content.getText().equals("护照")
					&& ed_card_num_content.getText().toString().trim().length() != 9) {
				Toast.makeText(this, "护照号码为9位,请核对", Toast.LENGTH_SHORT)
						.show();
			}

			else {
				if(flagMyAdapter != null && Fromtag == null){
					getUpadteContact();
					sendContact1();
				}else{
					getContact();
					sendContact();
				}
				
			}
			return;
		
		}
		if (ed_tourist_type_content.getText().equals("婴儿")){
			
			if (ed_tourist_type_content.getText().equals("请输入游客姓名")
					|| ed_name_content.getText().toString() == null
					|| "".equals(ed_name_content.getText().toString())) {
				Toast.makeText(this, "游客姓名不能为空,请输入", Toast.LENGTH_SHORT)
						.show();
			} else if ("".equals(ed_sex_content.getText().toString().trim()) || ed_sex_content.getText().toString().trim() == null) {
				Toast.makeText(this, "游客性别不能为空,请选择", Toast.LENGTH_SHORT)
						.show();
			} else if (ed_card_content.getText().equals("护照")
					&& ed_card_num_content.getText().toString().trim().length() != 9) {
				Toast.makeText(this, "护照号码为9位,请核对", Toast.LENGTH_SHORT)
						.show();
			}

			else {
				if(flagMyAdapter != null && Fromtag == null){
					getUpadteContact();
					sendContact1();
				}else{
					getContact();
					sendContact();
				}
				
			}
			return;
	
	}
		
	}

	private void getCategoryCheck() {
		 re_category.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					for(int i = 0; i <re_category.getChildCount();i++){
						RadioButton rb = (RadioButton) re_category.getChildAt(i);
						if(rb.getId() == checkedId){
							rb.setChecked(true);
							if(rb.isChecked()){
								ed_tourist_type_content.setText(rb.getText());
								touristType = ed_tourist_type_content.getText().toString().trim();
								re_category.setVisibility(View.GONE);
								fengexian1.setVisibility(View.GONE);
							}
							
							
						}else{
							rb.setChecked(false);
						}
					}
					
				}
			});
		
	}

	private void getSexCheck() {
		ll_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				for(int i = 0; i <ll_sex.getChildCount();i++){
					RadioButton rb = (RadioButton) ll_sex.getChildAt(i);
					if(rb.getId() == checkedId){
						rb.setChecked(true);
						if(rb.isChecked()){
							ed_sex_content.setText(rb.getText().toString());
							sex = ed_sex_content.getText().toString().trim();
							ll_sex.setVisibility(View.GONE);
							fengexian2.setVisibility(View.GONE);
						}
						
					}else{
						rb.setChecked(false);
					}
				}
				
			}
		});
		
	}

	private void getCardTypeCheck() {
		 re_cardtype.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					for(int i = 0; i <re_cardtype.getChildCount();i++){
						RadioButton rb = (RadioButton) re_cardtype.getChildAt(i);
						if(rb.getId() == checkedId){
							rb.setChecked(true);
							if(rb.isChecked()){
								ed_card_content.setText(rb.getText());
								cartType = ed_card_content.getText().toString().trim();
								re_cardtype.setVisibility(View.GONE);
								fengexian3.setVisibility(View.GONE);
							}
						}else{
							rb.setChecked(false);
						}
					}
					
				}
			});
	}

	private void getContact() {
		if (touristType.equals("成人")) {
			categorys = "0";
		} else if (touristType.equals("儿童")) {
			categorys = "1";
		} else {
			categorys = "2";
		}
		if ("男".equals(sex)) {
			gender = "1";
		} else if ("女".equals(sex)) {
			gender = "2";
		}else{
			gender = "0";
		}
		if (cartType.equals("身份证")) {
			cardcategory = "1";
		}else if(cartType.equals("护照")){
			cardcategory = "2";
		}else if(cartType.equals("港澳通行证")){
			cardcategory = "3";
		}else if(cartType.equals("台胞证")){
			cardcategory = "4";
		}else if(cartType.equals("海员证")){
			cardcategory = "5";
		}else if(cartType.equals("旅行证")){
			cardcategory = "6";
		}else{
			cardcategory = "7";
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", ShareUtil.getString("userId"));
		map.put("name", name);
		map.put("gender", gender);
		map.put("category", categorys);
		map.put("cardcategory", cardcategory);
		map.put("idcard", cardnum);
		map.put("mobile", phoneNum);
	
		VolleyUtils.requestString_Post(map, URL.DEBUG + URL.COMMIT_CONTACTS,
				new OnRequest() {

					@Override
					public void response(String url, String response) {
						
						list = ParseUtils.getContactInfo(response.toString());

					}

					@Override
					public void errorResponse(String url, VolleyError error) {
					}
				});

	}

	private void sendContact() {
		Intent intent = new Intent(getApplicationContext(),
				SendFielActivity.class);
		intent.putExtra("tag", tag);
		intent.putExtra("tagChatFragment", tagChatFragment);
		intent.putExtra("personalFragment", personalFragment);
		intent.putExtra("xinzeng", "xinzeng");
		intent.putExtra("category", Integer.parseInt(categorys));
		startActivity(intent);
		finish();
	}
	private void sendContact1() {
		Intent intent = new Intent(getApplicationContext(),
				SendFielActivity.class);
		intent.putExtra("tag", tag);
		intent.putExtra("tagChatFragment", tagChatFragment);
		intent.putExtra("personalFragment", personalFragment);
		intent.putExtra("bianji", "bianji");
		intent.putExtra("category", Integer.parseInt(categorys));
		startActivity(intent);
		finish();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EaseUI.getInstance().getNotifier().reset();
	}
	
}

