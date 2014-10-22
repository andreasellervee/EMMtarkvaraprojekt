package com.ee.matkarakendus.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ee.matkarakendus.networking.ServerTest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackPolylineUtil {
	
private Context context;
	
	public TrackPolylineUtil(Context context) {
		this.context = context;
	}
	
	PolylineOptions poly;
	
	public PolylineOptions getTrackPolylineById(int trackId) {
		poly = new PolylineOptions();
		poly.geodesic(true);
		FileIOUtility fileUtil = new FileIOUtility(context);
		String json = "";
		String polyLineFileName = "trackCoordinates3";
		try {
			if(fileUtil.fileExists(polyLineFileName)) {
				json = fileUtil.readFromFile(polyLineFileName);
			} else {
			json = new ServerTest().execute(
					"http://ec2-54-164-116-207.compute-1.amazonaws.com:8080/matkarakendus-0.1.0/allCoordinates").get();
			fileUtil.writeToFile(json, polyLineFileName);
			}
			Log.i("EMM", json);
			JSONArray tracksArray = new JSONObject(json).getJSONArray(Integer.toString(trackId));
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
