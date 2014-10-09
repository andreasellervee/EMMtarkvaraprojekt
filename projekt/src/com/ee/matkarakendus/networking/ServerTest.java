package com.ee.matkarakendus.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class ServerTest extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(params[0]);

		try {
			HttpResponse response = httpclient.execute(httpget);

			Log.i("EMM", response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				instream.close();
				return result;
			}
		} catch (Exception ex) {
			Log.e("EMM", ex.toString());
		}

		return null;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException ex) {
			Log.e("EMM", ex.toString());
		} finally {
			try {
				is.close();
			} catch (IOException ex) {
				Log.e("EMM", ex.toString());
			}
		}
		return sb.toString();
	}
}