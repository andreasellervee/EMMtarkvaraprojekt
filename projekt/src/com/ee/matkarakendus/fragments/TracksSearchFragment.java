package com.ee.matkarakendus.fragments;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.networking.ServerTest;
import com.ee.matkarakendus.objects.Track;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TracksSearchFragment extends Fragment {
	public TracksSearchFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tracks_search,
				container, false);

		Button searchAll = (Button) rootView.findViewById(R.id.searchAll);
		Button searchNear = (Button) rootView.findViewById(R.id.searchNear);

		searchAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchAll();
			}
		});

		searchNear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchNear();
			}
		});

		return rootView;
	}

	void searchAll() {
		String json = "";
		ArrayList<Track> tracks = new ArrayList<Track>();

		try {
			json = new ServerTest().execute(
					"http://emmtarkvaraprojekt.appspot.com/").get();
			JSONObject tracksJSON = new JSONObject(json);
			JSONArray tracksArray = tracksJSON.getJSONArray("tracks");
			for (int i = 0; i < tracksArray.length(); i++) {
				JSONObject track = tracksArray.getJSONObject(i);
				Track t = new Track();
				t.setId(track.getString("id"));
				t.setDescription(track.getString("description"));
				t.setLength(track.getDouble("length"));
				t.setLatitude(track.getDouble("latitude"));
				t.setLongitude(track.getDouble("longitude"));
				t.setAscend(track.getDouble("ascend"));
				t.setType(track.getInt("type"));
				t.setIsOpen(track.getBoolean("isOpen"));
				tracks.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Fragment fragment = new TracksSearchResultsFragment(tracks);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	void searchNear() {
		Fragment fragment = new TracksSearchResultsFragment();

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}
}