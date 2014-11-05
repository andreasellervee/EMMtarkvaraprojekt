package com.ee.matkarakendus.utils;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.networking.PointsRequest;
import com.ee.matkarakendus.objects.Point;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class TrackPOIUtil {

	ArrayList<Point> points = new ArrayList<Point>();

	public ArrayList<Point> getTrackPOIsById(int trackId) {
		String json = "";
		try {
			json = new PointsRequest().execute(String.valueOf(trackId)).get();
			JSONArray poiArray = new JSONArray(json);
			for (int i = 0; i < poiArray.length(); i++) {
				JSONObject markerJson = poiArray.getJSONObject(i);
				Point point = new Point();
				point.setLatitude(markerJson.getDouble("lat"));
				point.setLongitude(markerJson.getDouble("lng"));
				point.setName(markerJson.getString("name"));
				point.setCountry(markerJson.getString("country"));
				point.setCounty(markerJson.getString("county"));
				point.setTrackId(markerJson.getInt("track_ID"));

				if (markerJson.getString("description") != null
						&& !markerJson.getString("description").equals("")) {
					point.setDescription(markerJson.getString("description"));
				}

				if (getDrawable(markerJson.getString("type")) != null) {
					point.setType(markerJson.getString("type"));
				}

				points.add(point);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return points;
	}

	public static BitmapDescriptor getDrawable(String name) {
		try {
			if (name.equals("picnic")) {
				return BitmapDescriptorFactory.fromResource(R.drawable.picnic);
			} else if (name.equals("campfire")) {
				return BitmapDescriptorFactory
						.fromResource(R.drawable.campfire);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}