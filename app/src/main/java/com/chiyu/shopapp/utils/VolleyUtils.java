package com.chiyu.shopapp.utils;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chiyu.shopapp.constants.URL;

public class VolleyUtils {
	private static final String TAG_TOKEN = "token";
	public static RequestQueue mQueue;
	public static LruCache<String, Bitmap> lruCache;
	public static void initVolley(Context context){
		if(mQueue == null){
			mQueue = Volley.newRequestQueue(context);
		}
		lruCache = new LruCache<String, Bitmap>((int)(Runtime .getRuntime().maxMemory() / 8)){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				return value.getRowBytes() * value.getHeight();
			}
		};
	}
	/**
	 * 字符串Get请求
	 */
	public static void requestString_Get(final String url,final OnRequest onRequest){
		StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				//下载成功的回调
				onRequest.response(url, response);
			}
		},new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				//下载失败的回调
				onRequest.errorResponse(url, error);
			}
		});
		
		/***************跳过https***************************/
		if(!"".equals(ShareUtil.getString("token")) && ShareUtil.getString("token") != null
				&& !url.contains(URL.DEBUG + URL.INVIT_CODE)
				&& !url.contains(URL.DEBUG + URL.MEMBER_DTAIL)
				&& !url.contains(URL.DEBUG + URL.GUSTLINE_LIST)
				&& !url.contains(URL.DEBUG + URL.CATEGORY_LIST)
				&& !url.contains(URL.DEBUG + URL.GETLINE_BASE)
				&& !url.contains(URL.DEBUG + URL.GETLINEDETAIL_GOTIME)
				&& !url.contains(URL.DEBUG + URL.CONTENT_REDPACKETSETTING)
				&& !url.contains(URL.DEBUG + URL.lOGIN)
				&& !url.contains(URL.DEBUG + URL.GET_SECURITY_CODE)){
			HashMap<String, String> header = new HashMap<String, String>();
			header.put("token", ShareUtil.getString("token"));
			Log.w(TAG_TOKEN, "上传的用户token==========="+ShareUtil.getString("token")+"header===="+header.toString()+"接口==="+url+"这么奇怪？？？");
			stringRequest.setHeaders(header);
		}
		mQueue.add(stringRequest);
	}
	/**
	 * 字符串Post请求
	 * @author chiyu
	 *
	 */
	public static void requestString_Post(HashMap<String, String> map,final String url,final OnRequest onRequest){
		
		StringRequest stringRequest = new StringRequest(Method.POST, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				onRequest.response(url, response);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				onRequest.errorResponse(url, error);
			}
		});
		if(map != null){
			stringRequest.setParams(map);
		}
		if(!"".equals(ShareUtil.getString("token")) && ShareUtil.getString("token") != null
				&& !url.contains(URL.DEBUG + URL.INVIT_CODE)
				&& !url.contains(URL.DEBUG + URL.MEMBER_DTAIL)
				&& !url.contains(URL.DEBUG + URL.GUSTLINE_LIST)
				&& !url.contains(URL.DEBUG + URL.CATEGORY_LIST)
				&& !url.contains(URL.DEBUG + URL.GETLINE_BASE)
				&& !url.contains(URL.DEBUG + URL.GETLINEDETAIL_GOTIME)
				&& !url.contains(URL.DEBUG + URL.CONTENT_REDPACKETSETTING)
				&& !url.contains(URL.DEBUG + URL.lOGIN)
				&& !url.contains(URL.DEBUG + URL.GET_SECURITY_CODE)){
			HashMap<String, String> header = new HashMap<String, String>();
			header.put("token", ShareUtil.getString("token"));
			Log.w(TAG_TOKEN, "上传的用户token==========="+ShareUtil.getString("token")+"header===="+header.toString()+"接口==="+url+"这么奇怪？？？");
			stringRequest.setHeaders(header);
		}
				mQueue.add(stringRequest);
	}
	/**
	 * 字符串put请求
	 * @author chiyu
	 *
	 */
	public static void requestString_PUT(HashMap<String, String> map,final String url,final OnRequest onRequest){
		
		StringRequest stringRequest = new StringRequest(Method.PUT, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				onRequest.response(url, response);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				onRequest.errorResponse(url, error);
			}
		});
		if(map != null){
			stringRequest.setParams(map);
		}
		if(!"".equals(ShareUtil.getString("token")) && ShareUtil.getString("token") != null
				&& !url.contains(URL.DEBUG + URL.INVIT_CODE)
				&& !url.contains(URL.DEBUG + URL.MEMBER_DTAIL)
				&& !url.contains(URL.DEBUG + URL.GUSTLINE_LIST)
				&& !url.contains(URL.DEBUG + URL.CATEGORY_LIST)
				&& !url.contains(URL.DEBUG + URL.GETLINE_BASE)
				&& !url.contains(URL.DEBUG + URL.GETLINEDETAIL_GOTIME)
				&& !url.contains(URL.DEBUG + URL.CONTENT_REDPACKETSETTING)
				&& !url.contains(URL.DEBUG + URL.lOGIN)
				&& !url.contains(URL.DEBUG + URL.GET_SECURITY_CODE)
				&& !url.contains(URL.DEBUG + URL.pay_URL)
				&& !url.contains(URL.DEBUG + URL.pay_C_HUITIAOURL)){
			HashMap<String, String> header = new HashMap<String, String>();
			header.put("token", ShareUtil.getString("token"));
			Log.w(TAG_TOKEN, "上传的用户token==========="+ShareUtil.getString("token")+"header===="+header.toString()+"接口==="+url+"这么奇怪？？？");
			stringRequest.setHeaders(header);
		}
				mQueue.add(stringRequest);
	}
	public interface OnRequest{
		void response(String url, String response);
		void errorResponse(String url, VolleyError error);
	}
	/**
	 * 图片请求
	 */
	public static void requestImage(String url,ImageView iv,int resid,int errorResid){
		ImageLoader imageLoader = new ImageLoader(mQueue, new ImageCache() {
			
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				// TODO Auto-generated method stub
				lruCache.put(url, bitmap);
			}
			
			@Override
			public Bitmap getBitmap(String url) {
				// TODO Auto-generated method stub
				return lruCache.get(url);
			}
		});
		imageLoader.get(url, ImageLoader.getImageListener(iv, resid, errorResid));
	}
}
