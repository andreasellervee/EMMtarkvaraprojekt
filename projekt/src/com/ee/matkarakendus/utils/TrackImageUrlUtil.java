package com.ee.matkarakendus.utils;

import java.util.ArrayList;

import org.json.JSONArray;

import android.util.Log;

import com.ee.matkarakendus.networking.CommentsRequest;

public class TrackImageUrlUtil {
	
	public ArrayList<String> getPictureUrlsById(int trackId) {
		String json = "";
		ArrayList<String> urlList = new ArrayList<String>();
		try {
			json = new CommentsRequest().execute(String.valueOf(trackId))
					.get();
			Log.i("URLs", json);
			JSONArray urls = new JSONArray(json);
			for (int i = 0; i < urls.length(); i++) {
				String url = urls.getString(i);
				urlList.add(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return urlList;
	}

}
