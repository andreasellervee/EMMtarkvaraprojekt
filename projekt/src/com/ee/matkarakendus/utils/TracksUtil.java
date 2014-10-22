package com.ee.matkarakendus.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ee.matkarakendus.networking.ServerTest;
import com.ee.matkarakendus.objects.Track;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TracksUtil {
	
	private ArrayList<Track> tracks;
	
	public List<MarkerOptions> markers;
	
	private Context context;
	
	public TracksUtil(Context context) {
		this.context = context;
	}
	
	public List<MarkerOptions> getAllTrackMarkers() {
		if(tracks == null) {
			tracks = getAllTracks();
		}
		if(markers != null) {
			return markers;
		}
		markers = new ArrayList<MarkerOptions>();
		
		for(Track track : tracks) {
			markers.add(new MarkerOptions()
        	.title(track.getName())
        	.position(new LatLng(track.getLatitude(), track.getLongitude())));
		}
		return markers;
		
	}
	
	public ArrayList<Track> getAllTracks() {
		if(tracks != null) {
			Log.i("TRACKS", "Tracks loaded from memory.");
			return tracks;
		}
		FileIOUtility fileUtil = new FileIOUtility(context);
		tracks = new ArrayList<Track>();
		String json = "";
		try {
			if(fileUtil.fileExists("tracks")) {
				json = fileUtil.readFromFile("tracks");
				Log.i("READING", "read from file");
			} else {
			json = new ServerTest().execute(
					"http://ec2-54-164-116-207.compute-1.amazonaws.com:8080/matkarakendus-0.1.0/allTracks").get();
			fileUtil.writeToFile(json, "tracks");
			Log.i("WRITING", "to file");
			}
			JSONArray tracksArray = new JSONArray(json);
			for (int i = 0; i < tracksArray.length(); i++) {
				JSONObject track = tracksArray.getJSONObject(i);
				Track t = new Track();
				t.setId(track.getInt("id"));
				t.setName(track.getString("name"));
				t.setDescription(track.getString("description"));
				t.setCounty(track.getString("country"));
				t.setLength(track.getDouble("length"));
				t.setLatitude(track.getDouble("lat"));
				t.setLongitude(track.getDouble("lng"));
				t.setTime(track.getDouble("time"));
				t.setIsOpen(track.getBoolean("isOpen"));
				t.setType(track.getString("type"));
				tracks.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return tracks;
		}
		return tracks;
	}
	

}
