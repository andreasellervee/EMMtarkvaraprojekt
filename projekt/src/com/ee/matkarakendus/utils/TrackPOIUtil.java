package com.ee.matkarakendus.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.networking.ServerTest;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackPOIUtil {
	
	private Context context;
	
	public TrackPOIUtil(Context context) {
		this.context = context;
	}
	
	MarkerOptions marker;
	
	List<MarkerOptions> markers = new ArrayList<MarkerOptions>();
	
	public List<MarkerOptions> getTrackPOIsById(int trackId) {
		FileIOUtility fileUtil = new FileIOUtility(context);
		String json = "";
		String polyLineFileName = "trackCoordinates3";
		try {
//			if(fileUtil.fileExists(polyLineFileName)) {
//				json = fileUtil.readFromFile(polyLineFileName);
//			} else {
//			fileUtil.writeToFile(json, polyLineFileName);
//			}
			
			json = new ServerTest().execute(
					"http://ec2-54-164-116-207.compute-1.amazonaws.com:8080/matkarakendus-0.1.0/TrackPOIs?id=" + trackId).get();
			Log.i("EMM", json);
			JSONArray poiArray = new JSONArray(json);
			for (int i = 0; i < poiArray.length(); i++) {
				JSONObject markerJson = poiArray.getJSONObject(i);
				double lat = markerJson.getDouble("lat");
				double lng = markerJson.getDouble("lng");
				marker = new MarkerOptions()
				.title(markerJson.getString("name"))
	        	.position(new LatLng(lat, lng));
				
				if(markerJson.getString("description") != null && !markerJson.getString("description").equals("")) {
					marker.snippet(markerJson.getString("description"));
				}
				
				if(getDrawable(markerJson.getString("type")) != null) {
					marker.icon(getDrawable(markerJson.getString("type")));
				}
				
				markers.add(marker);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return markers;
	}
	
	private BitmapDescriptor getDrawable(String name) {
		try {
			if(name.equals("picnic")) {
				return BitmapDescriptorFactory.fromResource(R.drawable.picnic);
			} else if(name.equals("campfire")) {
				return BitmapDescriptorFactory.fromResource(R.drawable.campfire);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
