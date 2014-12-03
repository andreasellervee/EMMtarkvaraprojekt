package com.ee.matkarakendus.networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class PicturePost extends AsyncTask<Bitmap, Void, Void> {

	protected Void doInBackground(Bitmap... bitmaps) {
		if (bitmaps[0] == null)
			return null;

		Bitmap bitmap = bitmaps[0];
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

		InputStream in = new ByteArrayInputStream(stream.toByteArray());

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(
					"http://ec2-54-165-105-107.compute-1.amazonaws.com/UploadToServer.php");

			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity
					.addPart("myFile", System.currentTimeMillis() + ".jpg", in);
			httppost.setEntity(reqEntity);

			Log.e("Request", httppost.getRequestLine().toString());
			HttpResponse response = null;
			try {
				response = httpclient.execute(httppost);
			} catch (Exception ex) {
				Log.e("Exception", ex.toString());
			}
			try {
				if (response != null)
					Log.e("Response",
							EntityUtils.toString(response.getEntity()));
			} catch (Exception ex) {
				Log.e("Exception", ex.toString());
			}
		} finally {

		}

		if (in != null) {
			try {
				in.close();
			} catch (IOException ex) {
				Log.e("Exception", ex.toString());
			}
		}

		if (stream != null) {
			try {
				stream.close();
			} catch (IOException ex) {
			}
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
	}

	@Override
	protected void onPostExecute(Void result) {
	}
}