package com.ee.matkarakendus.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.ee.matkarakendus.networking.CoordinatesRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackPolylineUtil {

	PolylineOptions poly;

	public PolylineOptions getTrackPolylineById(int trackId) {
		poly = new PolylineOptions();
		poly.geodesic(true);
		String json = "";
		try {
			json = new CoordinatesRequest().execute(String.valueOf(trackId))
					.get();
			Log.i("EMM", json);
			JSONArray tracksArray = new JSONArray(json);
			for (int i = 0; i < tracksArray.length(); i++) {
				JSONObject latlng = tracksArray.getJSONObject(i);
				LatLng ll = new LatLng(latlng.getDouble("lat"),
						latlng.getDouble("lng"));
				poly.add(ll);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poly;
	}
}