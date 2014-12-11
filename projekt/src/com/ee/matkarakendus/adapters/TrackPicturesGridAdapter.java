package com.ee.matkarakendus.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class TrackPicturesGridAdapter extends BaseAdapter {

	Context context;
	ArrayList<String> urls;
	private Bitmap img;

	public TrackPicturesGridAdapter(Context context, ArrayList<String> urls) {
		this.context = context;
		this.urls = urls;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		try {
			img = new BitmapURLUtil().execute(urls.get(position)).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			return null;
		}

		ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(5, 5, 5, 5);

        int width = img.getWidth();
        int height = img.getHeight();
        imageView.setLayoutParams(new GridView.LayoutParams(dpToPx(100),dpToPx(100)));
        
        
		if (img != null) {
//			if (convertView != null) {
//				ImageView imgV = (ImageView) convertView.findViewById(R.id.image);
//				imgV.setImageBitmap(img);
//			} else {
//			}
			imageView.setImageBitmap(img);
		} else {
			Log.e("", "img is null");
			// if (convertView != null) {
			// ((ImageView) (convertView.findViewById(R.id.image)))
			// .setBackgroundColor(0xFFFF0000);
			// } else {
			// imageView = new ImageView(context);
			// imageView.setBackgroundColor(0xFFFF0000);
			// }
		}

		return imageView;
	}

	@Override
	public int getCount() {
		return urls.size();
	}

	@Override
	public Object getItem(int position) {
		return urls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private int dpToPx(int dp)
	{
	    float density = context.getResources().getDisplayMetrics().density;
	    return Math.round((float)dp * density);
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
	

//	public static Bitmap getBitmapFromURL(String src) {
//		try {
//			URL url = new URL(src);
//			HttpURLConnection connection = (HttpURLConnection) url
//					.openConnection();
//			connection.setDoInput(true);
//			connection.connect();
//			InputStream input = connection.getInputStream();
//			Bitmap myBitmap = BitmapFactory.decodeStream(input);
//			return myBitmap;
//		} catch (IOException e) {
//			return null;
//		}
//	}
}