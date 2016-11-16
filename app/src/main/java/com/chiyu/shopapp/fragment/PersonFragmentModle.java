package com.chiyu.shopapp.fragment;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.chiyu.shopapp.adapters.MyAdapter;
import com.chiyu.shopapp.bean.FeilName;
import com.chiyu.shopapp.constants.URL;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.ParseUtils;
import com.chiyu.shopapp.utils.ShareUtil;
import com.chiyu.shopapp.utils.VolleyUtils;
import com.chiyu.shopapp.utils.VolleyUtils.OnRequest;

/**
 * 实际fragment显示
 *
 * */
public class PersonFragmentModle extends Fragment{
	private ListView lv_content;
	private MyAdapter adapter;
	private String category;//确定fragment的具体字段
	private String userId;
	public List<FeilName> contactList;
	private String pageSize = "100";
	private String pageNumber = "1";
	private String sort = "desc";
	public static List<FeilName>list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.person_fragment_modle, null);
		lv_content = (ListView) view.findViewById(R.id.lv_content);
			getDatas(category);	
		return view;
	}
	
	private void getDatas(String category) {
		// 请求数据
		/**
		 *    userId:当前用户id 必填
   			pageSize: 每页显示数量
   			pageNumber: 要查询的目标页码
   			orderBy: 排序字段
   			sort: 排序方式 asc/desc
		 * */
//		String url = "http://test.receive.service.tripb2b.com/api/receive/contacts/getContactsUserList/111111/20/1/desc/0";
		//"URL.DEBUG + URL.CONTACTLIST+userId+"/"+pageSize+"/"+pageNumber+"/"+sort+"/"+category"
		VolleyUtils.requestString_Get(URL.DEBUG + URL.CONTACTLIST+userId+"/"+pageSize+"/"+pageNumber+"/"+sort+"/"+category , new OnRequest() {
			
			@Override
			public void response(String url, String response) {
				contactList = ParseUtils.getContactInfo(response.toString());
				list = contactList;
					adapter = new MyAdapter(contactList,getActivity());
					lv_content.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					lv_content.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
					
				 
			}
			
			@Override
			public void errorResponse(String url, VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		category = b.getString("category");
		userId = ShareUtil.getString("userId");
	}
}

