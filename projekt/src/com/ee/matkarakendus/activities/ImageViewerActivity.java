package com.ee.matkarakendus.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.ee.matkarakendus.R;

public class ImageViewerActivity extends Activity {
	
	private Bitmap img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewer);
		Intent i = getIntent();
		String url = i.getStringExtra("bitmapImageUrl");
		String title = i.getStringExtra("title");
		if(title != null && title.equals("")) {
			setTitle(title);
		} else {
			setTitle("Pilt");
		}
		try {
			img = new BitmapURLUtil().execute(url).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		if(img != null) {
			ImageView imageView = (ImageView) findViewById(R.id.imageViewer);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setImageBitmap(img);
		}
        
	}
	
	private static class BitmapURLUtil extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				return myBitmap;
			} catch (IOException e) {
				return null;
			}
		}
		
	}
}
