package com.ee.matkarakendus.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.ee.matkarakendus.networking.ServerTest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackPolylineUtil {
	
	PolylineOptions poly;
	
	public PolylineOptions getTrackPolylineById(int trackId) {
		poly = new PolylineOptions();
		poly.geodesic(true);
		String json = "";
		
		try {
			json = new ServerTest().execute(
					"http://ec2-54-164-116-207.compute-1.amazonaws.com:8080/matkarakendus-0.1.0/TrackCoordinates?id=" + trackId).get();
			Log.i("EMM", json);
			JSONArray tracksArray = new new JSONObject(json).getJSONArray("7");
			for (int i = 0; i < tracksArray.length(); i++) {
				JSONObject latlng = tracksArray.getJSONObject(i);
				LatLng ll = new LatLng(latlng.getDouble("lat"), latlng.getDouble("lng"));
				poly.add(ll);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poly;
	}

}
