package com.ee.matkarakendus.fragments;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Track;

public class TracksSearchFragment extends Fragment {
	Spinner openClosed;
	EditText lengthMin;
	EditText lengthMax;
	EditText durationMin;
	EditText durationMax;
	Spinner area;
	Spinner type;
	EditText string;
	Button searchAll;
	Button searchNear;
	Set<Track> results;
	ArrayList<Track> allTracks;
	Resources res;

	public TracksSearchFragment() {
	}

	public TracksSearchFragment(ArrayList<Track> tracks) {
		this.allTracks = tracks;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tracks_search,
				container, false);
		
		res = getResources();

		openClosed = (Spinner) rootView.findViewById(R.id.track_open_closed);
		lengthMin = (EditText) rootView.findViewById(R.id.lengthMin);
		lengthMax = (EditText) rootView.findViewById(R.id.lengthMax);
		durationMin = (EditText) rootView.findViewById(R.id.durationMin);
		durationMax = (EditText) rootView.findViewById(R.id.durationMax);
		area = (Spinner) rootView.findViewById(R.id.area);
		type = (Spinner) rootView.findViewById(R.id.type);
		string = (EditText) rootView.findViewById(R.id.string);
		searchAll = (Button) rootView.findViewById(R.id.searchAll);
		searchNear = (Button) rootView.findViewById(R.id.searchNear);

		if (savedInstanceState != null) {
			openClosed.setSelection((int) savedInstanceState.getLong("open_closed"));
			lengthMin.setText(String.valueOf(savedInstanceState.getDouble("length_min")));
			lengthMax.setText(String.valueOf(savedInstanceState.getDouble("length_max")));
			durationMin.setText(String.valueOf(savedInstanceState.getDouble("duration_min")));
			durationMax.setText(String.valueOf(savedInstanceState.getDouble("duration_max")));
			area.setSelection((int) savedInstanceState.getLong("area"));
			type.setSelection((int) savedInstanceState.getLong("type"));
			string.setText(savedInstanceState.getString("string"));
		}
		
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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putLong("open_closed", openClosed.getSelectedItemId());
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
		filterResults();
		
		if(allTracks.isEmpty()) {
			Toast.makeText(getActivity().getApplicationContext(), "Otsing ei tagastanud tulemusi",
					   Toast.LENGTH_SHORT).show();
		} else {
			TracksSearchResultsFragment fragment = new TracksSearchResultsFragment(allTracks);
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		}
	}

	private void filterResults() {
		results = new HashSet<Track>();
		
		filterOpenClosedTracks();
		
		filterTrackLength();
		
		//TODO
		//filterTrackDuration(); NOT IMPLEMENTED YET
		
		filterCountys();
		
		filterTrackTypes();
		
		filterStringSearch();
	}

	private void filterTrackLength() {
		String minimumLength = lengthMin.getText().toString(); 
		String maximumLength = lengthMax.getText().toString();
		
		if(!(maximumLength.equals("") && minimumLength.equals(""))) {
			if(!maximumLength.equals("") && !minimumLength.equals("")) {
				Double minLength = Double.valueOf(minimumLength);
				Double maxLength = Double.valueOf(maximumLength);
				for(Track track : allTracks) {
					if(track.getLength() < minLength || track.getLength() > maxLength) {
						results.add(track);
					}
				}
			} else if (maximumLength.equals("") && !minimumLength.equals("")) {
				Double minLength = Double.valueOf(minimumLength);
				for(Track track : allTracks) {
					if(track.getLength() < minLength) {
						results.add(track);
					}
				}
			} else if (!maximumLength.equals("") && minimumLength.equals("")) {
				Double maxLength = Double.valueOf(maximumLength);
				for(Track track : allTracks) {
					if(track.getLength() > maxLength) {
						results.add(track);
					}
				}
			}
		}
		
		allTracks.removeAll(results);
		results.clear();
	}

	private void filterStringSearch() {
		String sone = string.getText().toString().toLowerCase();
		Log.i("SONE", sone);
		if(!sone.equals("")) {
			for(Track track : allTracks) {
				Log.i("TRACK", track.getName() + " " + track.getDescription());
				if(!(track.getName().toLowerCase().contains(sone) || track.getDescription().toLowerCase().contains(sone))) {
					results.add(track);
				}
			}
		}
		
		allTracks.removeAll(results);
		results.clear();
	}

	private void filterTrackTypes() {
		if(type.getSelectedItemId() != 0) {
			String[] types = res.getStringArray(R.array.track_type_array);
			for(Track track : allTracks) {
				if(!track.getType().equals(types[(int) type.getSelectedItemId()])) {
					results.add(track);
				}
			}
		}
		
		allTracks.removeAll(results);
		results.clear();
	}

	private void filterCountys() {
		if(area.getSelectedItemId() != 0) {
			String[] countys = res.getStringArray(R.array.county_array);
			Log.i("ASI", countys[(int) area.getSelectedItemId()]);
			for(Track track : allTracks) {
				Log.i("ASI", track.getName() + " " + track.getCounty());
				if(!track.getCounty().equals(countys[(int) area.getSelectedItemId()])) {
					results.add(track);
				}
			}
		}
		allTracks.removeAll(results);
		results.clear();
	}

	private void filterOpenClosedTracks() {
		if(openClosed.getSelectedItemId() == 1) {
			for(Track track : allTracks) {
				if(track.isOpen) {
					results.add(track);
				} 
			}
		} else if(openClosed.getSelectedItemId() == 2) {
			for(Track track : allTracks) {
				if(!track.isOpen) {
					results.add(track);
				} 
			}
		}
		allTracks.removeAll(results);
		results.clear();
	}

	void searchNear() {
		Fragment fragment = new TracksSearchResultsFragment();
		
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
		.replace(R.id.content_frame, fragment).commit();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}