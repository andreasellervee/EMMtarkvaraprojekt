package com.ee.matkarakendus.networking;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class CommentsRequest extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
		String url = "http://ec2-54-165-105-107.compute-1.amazonaws.com:8080/matkarakendus-0.1.0/TrackComments?id="
				+ params[0];
		HttpGet httpget = new HttpGet(url);

		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				return EntityUtils.toString(entity);
			}
		} catch (Exception ex) {
			Log.e("Exception", ex.toString());
		}

		return null;
	}

}
