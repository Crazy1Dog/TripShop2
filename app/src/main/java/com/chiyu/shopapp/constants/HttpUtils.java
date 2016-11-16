package com.chiyu.shopapp.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	/**
	 * 获取httpClient对象
	 * 
	 * @author yangyouni
	 * @return
	 */
	private static HttpClient getClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,
				URL.REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, URL.SO_TIMEOUT);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}

	public static String getLine_dateinfo(String lineId) {
		// TODO Auto-generated method stub
		String responseMsg = null;
		HttpResponse response;
		String USER_URL = URL.DEBUG + URL.GETLINEDETAIL_GOTIME + "?" + lineId;
		HttpGet get = new HttpGet(USER_URL);
		Map<String, String> params = new HashMap<String, String>();
		List<NameValuePair> paarams = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				paarams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		HttpClient httpClient = getClient();
		try {

			response = httpClient.execute(get);

			if (response.getStatusLine().getStatusCode() == 200) {
				responseMsg = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				System.out.println("responseMsg==========="+responseMsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseMsg;
	}

}
