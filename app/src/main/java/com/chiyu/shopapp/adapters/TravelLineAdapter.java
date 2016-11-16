package com.chiyu.shopapp.adapters;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.chiyu.shopapp.bean.TravelLineEntity;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.ChatActivity;
import com.chiyu.shopapp.ui.CustomPlatformActivity;
import com.chiyu.shopapp.ui.LoginActivity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.DemoHelper;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TravelLineAdapter extends AbsAdapter<TravelLineEntity>{
	private static final String TAG2 = "tripshop2";
	Context context ;
	int position = 0;
	String flag = "TravelLineAdapter";
	TravelLineEntity data;
	public TravelLineAdapter(Context context, int[] resid) {
		super(context, R.layout.travelline_listview_item,R.layout.travelline_listview_item2,R.layout.travelline_listview_item3);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindDatas(ViewHolder viewHolder,final TravelLineEntity data,int position) {
		// TODO Auto-generated method stub
		/**
		 * 
                "lineId": 577, 
                "dateId": 5273, 
                "title": "赠送保险测试01", 
                "sub_title": "赠送保险", 
                "departure": "呼和浩特", 
                "destination": "北京", 
                "days": 2, 
                "count": null, 
                "photo": "21/o/305621/1456468190438.jpg", 
                "is_receive": 1, 
                "price": 5, 
                "marketPrice": 5, 
                "go_time": "2016-03-16", 
                "dateList": "03-16、03-17、03-22", 
                "photo_path": "21/o/305621/1456468190438.jpg"
            
		 */
	    final Button btn_collect;
		final Button btn_share;
		final Button btn_like;
		final Button btn_chat;
		this.data = data;
		if(getItemViewType(position) == 0){
			View view = viewHolder.getView(R.id.fengexian);
			if(position == 0){
				view.setVisibility(View.GONE);
			}
			TextView tv_item_title = (TextView) viewHolder.getView(R.id.tv_item_tile);
			tv_item_title.setText(data.getDays()+"天前更新");
			TextView tv_item_name = (TextView) viewHolder.getView(R.id.tv_item_name);
			tv_item_name.setText(data.getTitle());
		    Button btn_price = (Button) viewHolder.getView(R.id.btn_price);
		    if(data.getPrice().length() >= 5){
		    	btn_price.setTextSize(12);
		    }
			btn_price.setText("￥"+data.getPrice());
			Button btn_marketprice = (Button) viewHolder.getView(R.id.btn_marketprice);
			btn_marketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			btn_marketprice.setText("￥"+data.getMarketPrice());
			if(data.getPrice().equals(data.getMarketPrice())){
				btn_marketprice.setVisibility(View.GONE);
			}
			ImageView iv_picture1 = (ImageView) viewHolder.getView(R.id.iv_picture1);
			VolleyUtils.requestImage(URL.IMAGE_DEBUG + data.getPhoto(), iv_picture1, R.drawable.ic_launcher, R.drawable.ic_launcher);
			btn_collect = (Button) viewHolder.getView(R.id.btn_collect);
			btn_share = (Button) viewHolder.getView(R.id.btn_share);
			btn_like = (Button) viewHolder.getView(R.id.btn_like);
			btn_like.setText(data.getCount());
			btn_like.setText(data.getCount());
			btn_chat = (Button) viewHolder.getView(R.id.btn_chat);
		}else if(getItemViewType(position) == 1){
			View view = viewHolder.getView(R.id.fengexian);
			if(position == 0){
				view.setVisibility(View.GONE);
			}
			TextView tv_item_title = (TextView) viewHolder.getView(R.id.tv_item_tile);
			tv_item_title.setText(data.getDays()+"天前更新");
			TextView tv_item_name = (TextView) viewHolder.getView(R.id.tv_item_name);
			tv_item_name.setText(data.getTitle());
			Button btn_price = (Button) viewHolder.getView(R.id.btn_price);
			if(data.getPrice().length() >= 5){
		    	btn_price.setTextSize(12);
		    }
			btn_price.setText("￥"+data.getPrice());
			Button btn_marketprice = (Button) viewHolder.getView(R.id.btn_marketprice);
			btn_marketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			btn_marketprice.setText("￥"+data.getMarketPrice());
			if(data.getPrice().equals(data.getMarketPrice())){
				btn_marketprice.setVisibility(View.GONE);
			}
			String [] strArray = null;
			strArray = data.getPhoto_path().split(",");
			ImageView iv_picture1 = (ImageView) viewHolder.getView(R.id.iv_picture1);
			VolleyUtils.requestImage(URL.IMAGE_DEBUG + strArray[0], iv_picture1, R.drawable.ic_launcher, R.drawable.ic_launcher);
			ImageView iv_picture2 = (ImageView) viewHolder.getView(R.id.iv_picture2);
			VolleyUtils.requestImage(URL.IMAGE_DEBUG + strArray[1], iv_picture2, R.drawable.ic_launcher, R.drawable.ic_launcher);
			btn_collect = (Button) viewHolder.getView(R.id.btn_collect);
			btn_share = (Button) viewHolder.getView(R.id.btn_share);
			btn_like = (Button) viewHolder.getView(R.id.btn_like);
			btn_like.setText(data.getCount());
			btn_chat = (Button) viewHolder.getView(R.id.btn_chat);
		}else{
			View view = viewHolder.getView(R.id.fengexian);
			if(position == 0){
				view.setVisibility(View.GONE);
			}
			TextView tv_item_title = (TextView) viewHolder.getView(R.id.tv_item_tile);
			tv_item_title.setText(data.getDays()+"天前更新");
			TextView tv_item_name = (TextView) viewHolder.getView(R.id.tv_item_name);
			tv_item_name.setText(data.getTitle());
			Button btn_price = (Button) viewHolder.getView(R.id.btn_price);
			if(data.getPrice().length() >= 5){
		    	btn_price.setTextSize(12);
		    }
			btn_price.setText("￥"+data.getPrice());
			Button btn_marketprice = (Button) viewHolder.getView(R.id.btn_marketprice);
			btn_marketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			btn_marketprice.setText("￥"+data.getMarketPrice());
			if(data.getPrice().equals(data.getMarketPrice())){
				btn_marketprice.setVisibility(View.GONE);
			}
			String [] strArray = null;
			if(data.getPhoto_path() != null){
				strArray = data.getPhoto_path().split(",");
			}
			
			if(data.getPhoto_path() != null){
				strArray = data.getPhoto_path().split(",");
			}
			ImageView iv_picture1 = (ImageView) viewHolder.getView(R.id.iv_picture1);
			ImageView iv_picture2 = (ImageView) viewHolder.getView(R.id.iv_picture2);
			ImageView iv_picture3 = (ImageView) viewHolder.getView(R.id.iv_picture3);
			if(strArray != null){
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + strArray[0], iv_picture1, R.drawable.ic_launcher, R.drawable.ic_launcher);
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + strArray[1], iv_picture2, R.drawable.ic_launcher, R.drawable.ic_launcher);
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + strArray[2], iv_picture3, R.drawable.ic_launcher, R.drawable.ic_launcher);
			}
			/*if(strArray != null){
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + strArray[0], iv_picture1, R.drawable.back_ground, R.drawable.back_ground);
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + strArray[1], iv_picture2, R.drawable.back_ground, R.drawable.back_ground);
				VolleyUtils.requestImage(URL.IMAGE_DEBUG + strArray[2], iv_picture3, R.drawable.back_ground, R.drawable.back_ground);
			}*/
			btn_collect = (Button) viewHolder.getView(R.id.btn_collect);
			btn_share = (Button) viewHolder.getView(R.id.btn_share);
			btn_like = (Button) viewHolder.getView(R.id.btn_like);
			btn_like.setText(data.getCount());
			btn_chat = (Button) viewHolder.getView(R.id.btn_chat);
		}
		btn_collect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userId = ShareUtil.getString("userId");
				System.out.println("userid=== 首页线路============="+userId);
				if("".equals(userId)|| userId == null){
					Intent intent = new Intent();
					intent.setClass(context, LoginActivity.class);
					context.startActivity(intent);
				}else{
					btn_collect.setTextColor(Color.parseColor("#FF50D2C2"));
					Drawable leftDrawable = context.getResources().getDrawable(R.mipmap.btn_collection2);
					leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
					btn_collect.setCompoundDrawables(leftDrawable, null, null, null);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("lineId", data.getLineId());
					map.put("userId", ShareUtil.getString("userId"));
					VolleyUtils.requestString_Post(map, URL.DEBUG + URL.COLLECT, new OnRequest() {
						
						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							String result = "";
							Log.w(TAG2, "收藏成功"+ response.toString());
							//{"code":"590","message":"已收藏","result":""}
							try {
								JSONObject object = new JSONObject(response.toString());
								result = object.getString("message");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							Log.w(TAG2, "收藏失败"+error.getMessage());
							Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
						}
					});
				}
				
			}
		});
		btn_chat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				String userId = ShareUtil.getString("userId");
				if(userId == null || "".equals(userId)){
					Intent intent1 = new Intent();
//					intent1.putExtra("userId", ShareUtil.getString("memberEntityMobile"));
					intent1.putExtra("flag", "TravelLineAdapter");
					intent1.putExtra("IMIsClick", true);
					intent1.putExtra("title", data.getTitle());
					intent1.putExtra("photopath", data.getPhoto_path());
					intent1.putExtra("price", data.getPrice());
					intent1.putExtra("dateId", data.getDateId());
					intent1.putExtra("dateList", data.getDateList());
					intent1.putExtra("lineId", data.getLineId());
					intent1.putExtra("photoCount", data.getPhoto_count());
					intent1.setClass(context, LoginActivity.class);
					context.startActivity(intent1);
				}
				else if(!EMClient.getInstance().isConnected() || !DemoHelper.getInstance().isLoggedIn()){
					EMLogserver(ShareUtil.getString("huanxinUserName"),ShareUtil.getString("huanxinpwd"));
				}else{
					Drawable leftDrawable1 = context.getResources().getDrawable(R.mipmap.btn_talk2);
					leftDrawable1.setBounds(0, 0, leftDrawable1.getMinimumWidth(), leftDrawable1.getMinimumHeight());
					btn_chat.setCompoundDrawables(leftDrawable1, null, null, null);
					Intent intent2 = new Intent(context,ChatActivity.class);
					intent2.putExtra("userId", ShareUtil.getString("username"));
					intent2.putExtra("flag", "TravelLineAdapter");
					intent2.putExtra("title", data.getTitle());
					intent2.putExtra("photopath", data.getPhoto_path());
					intent2.putExtra("price", data.getPrice());
					intent2.putExtra("dateId", data.getDateId());
					intent2.putExtra("dateList", data.getDateList());
					intent2.putExtra("lineId", data.getLineId());
					intent2.putExtra("photoCount", data.getPhoto_count());
					Log.d("传递photopath", data.getPhoto_path()+"");
					context.startActivity(intent2);
//					Toast.makeText(context, "点击了留言！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		btn_like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userId = ShareUtil.getString("userId");
				if("".equals(userId) || userId == null){
					Intent intent = new Intent();
					intent.setClass(context, LoginActivity.class);
					context.startActivity(intent);
				}else{
					btn_like.setTextColor(Color.parseColor("#FF50D2C2"));
					Drawable leftDrawable = context.getResources().getDrawable(R.mipmap.btn_like2);
					leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
					btn_like.setCompoundDrawables(leftDrawable, null, null, null);
					HashMap<String, String>map1 = new HashMap<String, String>();
					map1.put("lineIds", data.getLineId());
					map1.put("userId", ShareUtil.getString("userId"));
					VolleyUtils.requestString_Post(map1, URL.DEBUG + URL.CHECKLIKE, new OnRequest() {
						
						@Override
						public void response(String url, String response) {
							// TODO Auto-generated method stub
							Log.w(TAG2, "判断是否已经点赞"+response.toString());
							try {
								JSONObject object = new JSONObject(response.toString());
								JSONObject object2 = object.getJSONObject("result");
								int checkFlag = object2.getInt(data.getLineId());
								if(checkFlag == 0){
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("lineId", data.getLineId());
									map.put("userId", ShareUtil.getString("userId"));
									VolleyUtils.requestString_Post(map, URL.DEBUG + URL.lIKE, new OnRequest() {
										
										@Override
										public void response(String url, String response) {
											// TODO Auto-generated method stub
											Log.i(TAG2, "点赞成功了"+response.toString());
											if("".equals(btn_like.getText().toString())||btn_like.getText().toString().equals("null")){
												btn_like.setText("1");
											}else{
												btn_like.setText((Integer.parseInt(btn_like.getText().toString()) + 1)+"");
											}
										}
										
										@Override
										public void errorResponse(String url, VolleyError error) {
											// TODO Auto-generated method stub
											
										}
									});
								}else{
									Toast.makeText(context, "已经赞过了", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						@Override
						public void errorResponse(String url, VolleyError error) {
							// TODO Auto-generated method stub
							
						}
					});
				}
				
				
//				{"code":"200","message":"判断线路是否已点赞成功","result":{"601":"0"}}
			}
		});
		btn_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userId = ShareUtil.getString("userId");
					Drawable leftDrawable = context.getResources().getDrawable(R.mipmap.btn_share2);
					leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
					btn_share.setCompoundDrawables(leftDrawable, null, null, null);
					Intent intent = new Intent(context,
							CustomPlatformActivity.class);
					intent.putExtra("title", data.getTitle());
					intent.putExtra("lineId", data.getLineId());
					intent.putExtra("dateId", data.getDateId());
					intent.putExtra("ImageUrl",URL.IMAGE_DEBUG +  data.getPhoto());
					context.startActivity(intent);
			}
		});
	}
	public void EMLogserver(String currentUsername, String currentPassword) {
		currentUsername = ShareUtil.getString("huanxinUserName");
		currentPassword = ShareUtil.getString("huanxinpwd");
		DemoHelper.getInstance().setCurrentUserName(currentUsername);
		EMClient.getInstance().login(currentUsername, currentPassword,
				new EMCallBack() {

					@Override
					public void onSuccess() {
						// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
						// ** manually load all local groups and
						EMClient.getInstance().groupManager().loadAllGroups();
						EMClient.getInstance().chatManager()
								.loadAllConversations();

						// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
						boolean updatenick = EMClient.getInstance()
								.updateCurrentUserNick(
										MyApp.currentUserNick.trim());
						if (!updatenick) {
							Log.e("LoginActivity",
									"update current user nick fail");
						}
						// 异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
						DemoHelper.getInstance().getUserProfileManager()
								.asyncGetCurrentUserInfo();
						// 进入主页面
						
							Intent intent = new Intent(context,
									ChatActivity.class);
							intent.putExtra("userId",ShareUtil.getString("contactname"));
							intent.putExtra("userId",ShareUtil.getString("username"));
							intent.putExtra("flag", "TravelLineAdapter");
							intent.putExtra("title", data.getTitle());
							intent.putExtra("photopath",data.getPhoto_path());
							intent.putExtra("price",  data.getPrice());
							intent.putExtra("dateId", data.getDateId());
							intent.putExtra("dateList", data.getDateList());
							intent.putExtra("lineId", data.getLineId());
							intent.putExtra("photoCount",  data.getPhoto_count());
							context.startActivity(intent);
						
					}

					@Override
					public void onProgress(int progress, String status) {
					}

					@Override
					public void onError(final int code, final String message) {
						((Activity) context).runOnUiThread(new Runnable() {
							public void run() {
//								Toast.makeText(
//										getApplicationContext(),
//										getString(R.string.Login_failed)
//												+ message, Toast.LENGTH_SHORT)
//										.show();
							}
						});
					}
				});
	}

}
