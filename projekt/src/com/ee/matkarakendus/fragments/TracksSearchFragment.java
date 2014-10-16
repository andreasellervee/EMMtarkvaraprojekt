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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class TracksSearchFragment extends Fragment {
	Switch openClosed;
	EditText lengthMin;
	EditText lengthMax;
	EditText durationMin;
	EditText durationMax;
	Spinner area;
	Spinner type;
	EditText string;
	Button searchAll;
	Button searchNear;

	public TracksSearchFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tracks_search,
				container, false);

		openClosed = (Switch) rootView.findViewById(R.id.openClosed);
		lengthMin = (EditText) rootView.findViewById(R.id.lengthMin);
		lengthMax = (EditText) rootView.findViewById(R.id.lengthMax);
		durationMin = (EditText) rootView.findViewById(R.id.durationMin);
		durationMax = (EditText) rootView.findViewById(R.id.durationMax);
		area = (Spinner) rootView.findViewById(R.id.area);
		type = (Spinner) rootView.findViewById(R.id.type);
		string = (EditText) rootView.findViewById(R.id.string);
		searchAll = (Button) rootView.findViewById(R.id.searchAll);
		searchNear = (Button) rootView.findViewById(R.id.searchNear);

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

		if (savedInstanceState != null) {
			Boolean open_closed = savedInstanceState.getBoolean("open_closed");
			Double length_min = savedInstanceState.getDouble("length_min");
			Double length_max = savedInstanceState.getDouble("length_max");
			Double duration_min = savedInstanceState.getDouble("duration_min");
			Double duration_max = savedInstanceState.getDouble("duration_max");
			long area = savedInstanceState.getLong("area");
			long type = savedInstanceState.getLong("type");
			String string = savedInstanceState.getString("string");
		}

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putBoolean("open_closed", openClosed.isSelected());
		outState.putDouble("length_min",
				Double.parseDouble(lengthMin.getText().toString()));
		outState.putDouble("length_max",
				Double.parseDouble(lengthMax.getText().toString()));
		outState.putDouble("duration_min",
				Double.parseDouble(durationMin.getText().toString()));
		outState.putDouble("duration_max",
				Double.parseDouble(durationMax.getText().toString()));
		outState.putLong("area", area.getSelectedItemId());
		outState.putLong("type", type.getSelectedItemId());
		outState.putString("string", string.getText().toString());
	}

	void searchAll() {
		String json = "";
		ArrayList<Track> tracks = new ArrayList<Track>();

		try {
			json = new ServerTest().execute(
					"http://ec2-54-88-100-57.compute-1.amazonaws.com:8080/matkarakendus-0.1.0/allTracks").get();
			Log.i("EMM", json);
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
		}

		Fragment fragment = new TracksSearchResultsFragment(tracks);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	void searchNear() {
		Fragment fragment = new TracksSearchResultsFragment();

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}
}