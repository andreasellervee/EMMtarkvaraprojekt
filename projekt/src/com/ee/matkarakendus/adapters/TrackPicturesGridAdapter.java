package com.ee.matkarakendus.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.ee.matkarakendus.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class TrackPicturesGridAdapter extends BaseAdapter {

	Context context;
	ArrayList<String> urls;

	public TrackPicturesGridAdapter(Context context, ArrayList<String> urls) {
		this.context = context;
		this.urls = urls;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		Bitmap img = getBitmapFromURL(urls.get(position));

		ImageView imageView = new ImageView(context);

		if (img != null) {
			if (convertView != null) {
				((ImageView) (convertView.findViewById(R.id.image)))
						.setImageBitmap(img);
			} else {
				imageView = new ImageView(context);
				imageView.setImageBitmap(img);
			}
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

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
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