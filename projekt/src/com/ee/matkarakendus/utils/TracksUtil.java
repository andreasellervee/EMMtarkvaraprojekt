package com.ee.matkarakendus.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.networking.ServerTest;
import com.ee.matkarakendus.objects.Track;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TracksUtil {
	
	private ArrayList<Track> tracks;
	
	public Map<Track, MarkerOptions> markers;
	
	private Context context;
	
	public TracksUtil(Context context) {
		this.context = context;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Track, MarkerOptions> getAllTrackMarkers() {
		if(tracks == null) {
			tracks = getAllTracks();
		}
		if(markers != null) {
			return markers;
		}
		markers = new HashMap<Track, MarkerOptions>();
		
		for(Track track : tracks) {
			markers.put(track, new MarkerOptions()
        	.title(track.getName())
        	.position(new LatLng(track.getLatitude(), track.getLongitude())));
		}
		return markers;
		
	}
	
	public Track getTrackById(int id) {
		ArrayList<Track> allTracks = getAllTracks();
		for(Track track : allTracks) {
			if(track.getId() == id) {
				return track;
			}
		}
		return null;
	}
	
	public Track getTrackByTitle(String title) {
		if(title.isEmpty() || title == null) {
			return null;
		}
		if(tracks == null) {
			tracks = getAllTracks();
		}
		for(Track track : tracks) {
			if(track.getName().equals(title)) {
				return track;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Track> getAllTracks() {
		if(tracks != null) {
			Log.i("TRACKS", "Tracks loaded from memory.");
			return tracks;
		}
		FileIOUtility fileUtil = new FileIOUtility(context);
		tracks = new ArrayList<Track>();
		String json = "";
		String tracksFileName = "tracks4";
		try {
//			try {
//				tracks = (ArrayList<Track>) fileUtil.readObject(context, "ALL_TRACKS");
//				Log.i("EKSISTEERIB", "SEE KAAAAAA");
//				return tracks;
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//			
//			if(fileUtil.fileExists(tracksFileName)) {
//				json = fileUtil.readFromFile(tracksFileName);
//				Log.i("READING", "read from file");
//				
//			} else {
//			fileUtil.writeToFile(json, tracksFileName);
//			Log.i("WRITING", "to file");
//			}
			
			json = new ServerTest().execute(
					context.getString(R.string.db_url) + ":8080/matkarakendus-0.1.0/allTracks").get();
			JSONArray tracksArray = new JSONArray(json);
			for (int i = 0; i < tracksArray.length(); i++) {
				JSONObject track = tracksArray.getJSONObject(i);
				Track t = new Track();
				t.setId(track.getInt("id"));
				t.setName(track.getString("name"));
				t.setDescription(track.getString("description"));
				t.setCounty(track.getString("county"));
				t.setCountry(track.getString("country"));
				t.setLength(track.getDouble("length"));
				t.setLatitude(track.getDouble("lat"));
				t.setLongitude(track.getDouble("lng"));
				t.setIsOpen(track.getBoolean("isOpen"));
				t.setType(track.getString("type"));
				t.setFavourite(false);
				tracks.add(t);
			}
		} catch (Exception e) {
			Log.e("Exception", e.toString());
			return tracks;
		}
		try {
			fileUtil.writeObject(context, "ALL_TRACKS", tracks);
		} catch (IOException e) {
			Log.e("Exception", e.toString());
		}
		return tracks;
	}
}
