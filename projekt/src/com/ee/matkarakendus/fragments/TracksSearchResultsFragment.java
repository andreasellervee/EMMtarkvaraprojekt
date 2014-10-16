package com.ee.matkarakendus.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.utils.TrackPolylineUtil;
import com.google.android.gms.maps.model.PolylineOptions;

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
		
		list.setAdapter(adapter);
		
		//NOT FUNCTIONAL
//		list.setOnItemClickListener(new TracksListOnClickListener(getFragmentManager()));

		return rootView;
	}
	
	private static final class TracksListOnClickListener implements OnItemClickListener {
		
		private FragmentManager fragmentManager;
		
		public TracksListOnClickListener(FragmentManager manager) {
			this.fragmentManager = manager;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Track track = (Track) parent.getItemAtPosition(position);
			
			TrackPolylineUtil util = new TrackPolylineUtil();
			PolylineOptions poly = util.getTrackPolylineById(track.getId());
			MapDisplayFragment map = new MapDisplayFragment();
			map.setPolys(poly);
		}

	}
}