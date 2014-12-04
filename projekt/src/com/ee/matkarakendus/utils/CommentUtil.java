package com.ee.matkarakendus.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.ee.matkarakendus.networking.CommentsRequest;
import com.ee.matkarakendus.objects.Comment;

public class CommentUtil {
	
	public ArrayList<Comment> getCommentsById(int trackId) {
		String json = "";
		ArrayList<Comment> commentsList = new ArrayList<Comment>();
		try {
			json = new CommentsRequest().execute(String.valueOf(trackId))
					.get();
			Log.i("Comments", json);
			JSONArray comments = new JSONArray(json);
			for (int i = 0; i < comments.length(); i++) {
				JSONObject comment = comments.getJSONObject(i);
				Comment cmt = new Comment();
				cmt.setBody(comment.getString("comment"));
				commentsList.add(cmt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return commentsList;
	}

}
