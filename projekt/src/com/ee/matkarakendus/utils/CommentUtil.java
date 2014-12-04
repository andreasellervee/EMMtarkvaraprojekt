package com.ee.matkarakendus.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.ee.matkarakendus.networking.CommentsRequest;
import com.ee.matkarakendus.networking.CoordinatesRequest;
import com.ee.matkarakendus.objects.Comment;
import com.google.android.gms.maps.model.LatLng;

public class CommentUtil {
	
	public List<Comment> getCommentsById(int trackId) {
		String json = "";
		List<Comment> 
		try {
			json = new CommentsRequest().execute(String.valueOf(trackId))
					.get();
			Log.i("Comments", json);
			JSONArray comments = new JSONArray(json);
			for (int i = 0; i < comments.length(); i++) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
