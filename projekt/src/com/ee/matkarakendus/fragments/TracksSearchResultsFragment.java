package com.ee.matkarakendus.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.utils.TrackPolylineUtil;
import com.google.android.gms.maps.model.PolylineOptions;

public class TracksSearchResultsFragment extends Fragment implements OnItemClickListener {

	ArrayList<Track> tracks = new ArrayList<Track>();
	
	public TracksSearchResultsFragment() {
	}

	public TracksSearchResultsFragment(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getActivity().setTitle(R.string.tracks);				

		View rootView = inflater.inflate(R.layout.fragment_search_results,
				container, false);

		ListView list = (ListView) rootView.findViewById(android.R.id.list);
		
		TracksListAdapter adapter = new TracksListAdapter(getActivity(), tracks);
		
		list.setAdapter(adapter);
		
		//NOT FUNCTIONAL
		list.setOnItemClickListener(this);
		
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Track track = (Track) parent.getItemAtPosition(position);
		Log.i("TRACK", track.getName() + track.getDescription());
		TrackPolylineUtil util = new TrackPolylineUtil(getActivity().getApplicationContext());
		PolylineOptions poly = util.getTrackPolylineById(track.getId());
		if(poly == null || poly.getPoints().isEmpty()) {
			Toast.makeText(getActivity().getApplicationContext(), track.getName() + 
					" koordinaadid puuduvad :(",
					   Toast.LENGTH_SHORT).show();
		} else {
			MapDisplayFragment map = new MapDisplayFragment(poly, track);
			getFragmentManager().beginTransaction()
			.replace(R.id.content_frame, map ).commit();
		}
		
	}
}