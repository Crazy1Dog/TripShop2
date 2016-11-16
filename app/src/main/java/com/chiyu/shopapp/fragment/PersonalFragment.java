package com.chiyu.shopapp.fragment;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.LoginActivity;
import com.chiyu.shopapp.ui.MyCollect;
import com.chiyu.shopapp.ui.My_Red_ListActivity;
import com.chiyu.shopapp.ui.Order_Activity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.ui.SendFielActivity;
import com.chiyu.shopapp.ui.SettingActivity;
import com.chiyu.shopapp.utils.BitmapUtil;
import com.chiyu.shopapp.utils.Encryption;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;
import com.chiyu.shopapp.view.CircleImageView;

/**
 *
 * 个人中心
 */
public class PersonalFragment extends Fragment implements OnClickListener {
	private TextView tv_num1,tv_num2,tv_num3;
	private CircleImageView touxiang;
	private static final int REQUEST_CODE_PICK_IMAGE = 1;
	private static final int REQUEST_CODE_CAPTURE_CAMEIA = 2;
	private static final int CROP_PICTURE = 3;
	private static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	private static final String IMAGE_FILE_LOCATION = "file://" + SD_PATH
			+ "/DCIM/Camera/temp.jpg";
	private static final String IMAGE_LOCATION = SD_PATH
			+ "/DCIM/Camera/temp.jpg";
	private static final String IMAGE_LOCATION_PARENT = SD_PATH
			+ "/DCIM/Camera";
	private Uri imageUri;// 图片本地URI地址
	private static final String TAG = "123";
	public static String headURI; // 头像本地地址
	public static boolean ADD_FLAG = false; // 开始装图片
	private String string; // 二进制流
	private String ext = "png"; // 图像扩展
	private TextView telephone;
	private Button setting;
	private RelativeLayout gv_select1;
	private RelativeLayout gv_select2;
	private RelativeLayout gv_select3;
	private RelativeLayout gv_select4;
	private LinearLayout ll_name;
	private PopupWindow mPopupWindow;
	private View mpopview;
	private CharSequence temp;
	private String currentPhone;
	private String username;
	private String photopath;
	private String userId;
	private String token;
	MyApp app;
	private Context mContext;
	Intent intent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (MyApp) getActivity().getApplication();
		username = app.getUsername();
		photopath = app.getPhotopath();
		currentPhone = ShareUtil.getString("userMobile");
		token = app.getToken();
		userId = ShareUtil.getString("userId");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.personfragment, null);

		init();
		tv_num1 = (TextView) view.findViewById(R.id.tv_num1);
		tv_num2 = (TextView) view.findViewById(R.id.tv_num2);
		tv_num3 = (TextView) view.findViewById(R.id.tv_num3);
		touxiang = (CircleImageView) view.findViewById(R.id.touxiang);
		telephone = (TextView) view.findViewById(R.id.telephone);
		setting = (Button) view.findViewById(R.id.setting);
		gv_select1 = (RelativeLayout) view.findViewById(R.id.gv_select1);
		gv_select2 = (RelativeLayout) view.findViewById(R.id.gv_select2);
		gv_select3 = (RelativeLayout) view.findViewById(R.id.gv_select3);
		gv_select4 = (RelativeLayout) view.findViewById(R.id.gv_select4);
		ll_name = (LinearLayout) view.findViewById(R.id.ll_name);
		/**
		 *   描述：个人中心    GET     /api/receive/uc/center/{token}/{userId}
		 *
		 *  @param token        登录token--必填
		 *  @param userId       C客ID--必填
		 *  @param successBlock 成功
		 *  @param faildBlock   失败
		 */
		VolleyUtils.requestString_Get(URL.DEBUG + URL.DESCRIBE + ShareUtil.getString("token")+"/"+ShareUtil.getString("userId"), new OnRequest() {

			@Override
			public void response(String url, String response) {
				// TODO Auto-generated method stub
				Log.w(TAG,"个人中心个数请求成功"+response.toString());

				if(response != null){
					try {
						JSONObject object = new JSONObject(response);
						int code = object.getInt("code");
						if(code == 200){
							JSONObject object2 = object.getJSONObject("result");
							ShareUtil.putString("photoPath", object2.getString("photoPath"));
							app.setPhotopath(object2.getString("photoPath"));
							VolleyUtils.requestImage(URL.IMAGE_DEBUG + object2.getString("photoPath"), touxiang,
									R.drawable.skt_icon_default, R.drawable.skt_icon_default);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void errorResponse(String url, VolleyError error) {
			}
		});
		VolleyUtils.requestImage(URL.IMAGE_DEBUG + ShareUtil.getString("photoPath"), touxiang,
				R.drawable.skt_icon_default, R.drawable.skt_icon_default);
		imageUri = Uri.parse(IMAGE_FILE_LOCATION);
		touxiang.setOnClickListener(this);
		setting.setOnClickListener(this);
		gv_select1.setOnClickListener(this);
		gv_select2.setOnClickListener(this);
		gv_select3.setOnClickListener(this);
		gv_select4.setOnClickListener(this);
	//	telephone.setOnClickListener(this);

		return view;
	}
	@Override
	public void onStart() {
		super.onStart();
		currentPhone = ShareUtil.getString("userMobile");
		userId = ShareUtil.getString("userId");
		if ("".equals(userId) || userId == null) {
			telephone.setText("");
			ll_name.setVisibility(View.VISIBLE);
			ll_name.setOnClickListener(this);
			touxiang.setVisibility(View.GONE);
		} else {
			telephone.setText(currentPhone);
			ll_name.setVisibility(View.GONE);
			touxiang.setVisibility(View.VISIBLE);
		}
	}

	private void init() {
		File f = new File(IMAGE_LOCATION_PARENT);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	@Override
	public void onClick(View v) {
		userId = ShareUtil.getString("userId");
		switch (v.getId()) {

		case R.id.touxiang:
			changePhoto();
			break;
	//	case R.id.telephone:
	//		if ("".equals(userId) || userId == null) {
	//			intent = new Intent();
	//			intent.setClass(getActivity(), LoginActivity.class);
	//			getActivity().startActivity(intent);
	//		} else {
	//			Intent intent2 = new Intent(getActivity(), ChangeActivity.class);
	//			startActivity(intent2);
		//	}
	//		break;
		case R.id.setting:
			Intent intent = new Intent(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.gv_select1:
			if ("".equals(userId) || userId == null) {
				intent = new Intent();
				intent.putExtra("tag", "PersonalFragment");

				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
			} else {
				Intent intent3 = new Intent(getActivity(), Order_Activity.class);
				startActivity(intent3);
			}
			break;
		case R.id.gv_select2:
			if ("".equals(userId) || userId == null) {
				intent = new Intent();
				intent.putExtra("tag", "PersonalFragment");
				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
			} else {
				Intent intent4 = new Intent(getActivity(), MyCollect.class);
				startActivity(intent4);
			}

			break;
		case R.id.gv_select3:
			if ("".equals(userId) || userId == null) {
				intent = new Intent();
				intent.putExtra("tag", "PersonalFragment");
				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
			} else {
				Intent intent5 = new Intent(getActivity(), My_Red_ListActivity.class);
				startActivity(intent5);
			}
			break;
		case R.id.gv_select4:
			if ("".equals(userId) || userId == null) {
				intent = new Intent();
				intent.putExtra("tag", "PersonalFragment");
				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
			} else {
				Intent intent5 = new Intent(getActivity(), SendFielActivity.class);
				intent5.putExtra("personalFragment", "PersonalFragment");
				intent5.putExtra("tongxunlu", "tongxunlu");
				startActivity(intent5);
			}
			break;
		case R.id.ll_name:
			Intent intent7 = new Intent(getActivity(), LoginActivity.class);
			intent7.putExtra("tag", "PersonalFragment");
			startActivity(intent7);
			break;
		default:
			break;
		}
	}

	// 修改头像
	private void changePhoto() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		mpopview = inflater.inflate(R.layout.layout_login_choose_photo, null);
		mPopupWindow = new PopupWindow(mpopview, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
//		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
//				R.drawable.ic_launcher));

		mPopupWindow.showAtLocation(touxiang, Gravity.BOTTOM, 0, 0);
		Button mbuttonTakePhoto = (Button) mpopview
				.findViewById(R.id.button_take_photo);
		Button mbuttonChoicePhoto = (Button) mpopview
				.findViewById(R.id.button_choice_photo);
		Button mbuttonChoicecannce = (Button) mpopview
				.findViewById(R.id.button_choice_cancer);
		// 拍照
		mbuttonTakePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
				getImageFromCamera();

			}
		});
		// 从相册中获取
		mbuttonChoicePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
				getImageFromAlbum();
			}
		});
		// 取消
		mbuttonChoicecannce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
	}

	// 拍照获取照片
	protected void getImageFromCamera() {
		if (imageUri == null)
			Log.e("", "image uri can't be null");
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
																	// capture
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
	}

	// 从本地相册获取照片
	protected void getImageFromAlbum() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false); // no face detection
		startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {// result is not correct
//			Log.e(TAG, "requestCode = " + requestCode);
//			Log.e(TAG, "resultCode = " + resultCode);
//			Log.e(TAG, "data = " + data);
			return;
		} else {
			switch (requestCode) {
			case REQUEST_CODE_CAPTURE_CAMEIA:
				cropImageUri(imageUri, 200, 200, CROP_PICTURE);
				break;
			case CROP_PICTURE:
				if (imageUri != null) {
					Bitmap srcBitmap = BitmapUtil.getBitmapFromUri(imageUri,
							getActivity());// 得到图片
					Bitmap descBitmap = BitmapUtil.compressImage(srcBitmap);// 压缩图片
					BitmapUtil.saveBitmap(IMAGE_LOCATION, descBitmap);// 保存图片
					headURI = IMAGE_LOCATION;
					ADD_FLAG = true;// 返回添加孩子页面

					// getActivity().finish();
				}
			case REQUEST_CODE_PICK_IMAGE:
				Log.i(TAG, "imageUri = " + imageUri);
				if (imageUri != null) {
					Bitmap srcBitmap = BitmapUtil.getBitmapFromUri(imageUri,
							getActivity());// 得到图片
					Bitmap descBitmap = BitmapUtil.compressImage(srcBitmap);// 压缩图片
					BitmapUtil.saveBitmap(IMAGE_LOCATION, descBitmap);// 保存图片
					headURI = IMAGE_LOCATION;
					ADD_FLAG = true;// 返回添加孩子页面

					// getActivity().finish();
				}
			default:
				break;
			}
		}
	}

	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");// 开始剪切
		intent.putExtra("aspectX", 1);// 截取框，X方向比例
		intent.putExtra("aspectY", 1);// 截取框，Y方向比例
		intent.putExtra("outputX", outputX);// 输出图片，X方向像素
		intent.putExtra("outputY", outputY);// 输出图片，Y方向像素
		intent.putExtra("scale", true);// 是否保留比例
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);// 保存的路径
		intent.putExtra("return-data", false);// 是否返回数据
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, requestCode);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		token = app.getToken();
		username = app.getUsername();
		photopath = app.getPhotopath();
		token = app.getToken();
		if (ADD_FLAG) {// 添加孩子照片
			Bitmap descBitmap = BitmapUtil.getBitmap(headURI);
			touxiang.setImageBitmap(descBitmap);
			ADD_FLAG = false;
			string = Encryption.sendPhoto(touxiang);
			Cancellations();
		} else {
			headURI = "";
		}

		VolleyUtils.requestString_Get(URL.DEBUG + URL.DESCRIBE + ShareUtil.getString("token")+"/"+ShareUtil.getString("userId"), new OnRequest() {

					@Override
					public void response(String url, String response) {
						// TODO Auto-generated method stub
						//{"code":"200","message":"","result":{"orderNum":"0","userName":null,"collectionNum":"0","redPacketTotalAmount":"0","photoPath":null}}
						if(response != null){
							try {
								JSONObject object = new JSONObject(response);
								int code = object.getInt("code");
								if(code == 200){
									JSONObject object2 = object.getJSONObject("result");
									tv_num1.setText(object2.getString("orderNum"));
									tv_num2.setText(object2.getString("collectionNum"));
									tv_num3.setText(object2.getString("redPacketTotalAmount"));
									ShareUtil.putString("photoPath", object2.getString("photoPath"));
									app.setPhotopath(object2.getString("photoPath"));
									VolleyUtils.requestImage(URL.IMAGE_DEBUG + object2.getString("photoPath"), touxiang,
											R.drawable.skt_icon_default, R.drawable.skt_icon_default);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void errorResponse(String url, VolleyError error) {
						// TODO Auto-generated method stub
					}
				});
	}

	private void Cancellations() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("imgstr", string);
		map.put("ext", ext);
		map.put("userId", ShareUtil.getString("userId"));
		map.put("token", ShareUtil.getString("token"));
		VolleyUtils.requestString_PUT(map, URL.DEBUG + URL.COMMIT_PHOTO_C,
				new OnRequest() {

					@Override
					public void response(String url, String response) {
						// {"code":"200","message":"更新成功","result":"upload/images/receive_app/20160415/2b102c6468a743b094320f6080a60b0d.png"}
						try {
							JSONObject object = new JSONObject(response
									.toString());
							int code = object.getInt("code");
							if (code == 200) {
								String touxiangUrl = object.getString("result");
								app.setPhotopath(touxiangUrl);
								ShareUtil.putString("photoPath", touxiangUrl);
								VolleyUtils.requestImage(URL.IMAGE_DEBUG + touxiangUrl, touxiang,
										R.drawable.skt_icon_default, R.drawable.skt_icon_default);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void errorResponse(String url, VolleyError error) {
						Log.e("上传头像", error.toString());
					}
				});
	}

}
