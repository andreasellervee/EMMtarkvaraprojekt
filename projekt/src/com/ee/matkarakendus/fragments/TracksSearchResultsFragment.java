package com.ee.matkarakendus.fragments;

import java.util.ArrayList;
import java.util.Comparator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.objects.Track;

public class TracksSearchResultsFragment extends Fragment {

	ArrayList<Track> tracks;
	
	public TracksSearchResultsFragment() {
		this.tracks = new ArrayList<Track>();
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

		adapter.sort(new Comparator<Track>() {

			@Override
			public int compare(Track lhs, Track rhs) {
				return lhs.getId().compareTo(rhs.getId());
			}
		});
		
		list.setAdapter(adapter);

		return rootView;
	}
}