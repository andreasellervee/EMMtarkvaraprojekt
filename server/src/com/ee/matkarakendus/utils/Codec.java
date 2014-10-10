package com.ee.matkarakendus.utils;

import java.util.List;

import com.ee.matkarakendus.objects.Track;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Codec {
	public static JSONObject EncodeTracksList (List<Track> tracks) {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		
		try {
			for (Track t : tracks) {
				JSONObject j = new JSONObject();
				j.put("id", t.id);
				j.put("description", t.description);
				j.put("latitude", t.latitude);
				j.put("longitude", t.longitude);
				j.put("length", t.length);
				j.put("type", t.type);
				j.put("isOpen", t.isOpen);
				j.put("ascend", t.ascend);
				array.put(j);
			}
			
			json.put("tracks", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
}