package com.ee.matkarakendus.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.ee.matkarakendus.R;
import com.ee.matkarakendus.activities.TrackViewActivity;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.objects.Track;

public class TracksSearchResultsFragment extends Fragment implements
		OnItemClickListener {

	private ArrayList<Track> tracks = new ArrayList<Track>();

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

		list.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Track track = (Track) parent.getItemAtPosition(position);
		Intent i = new Intent(getActivity().getApplicationContext(),
				TrackViewActivity.class);
		i.putExtra("track", track);
		startActivity(i);
	}
}