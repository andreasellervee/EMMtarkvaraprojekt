package com.eematkarakendus.fragments;

import java.util.ArrayList;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.objects.Track;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FavouritesFragment extends Fragment {

	ArrayList<Track> tracks;

	public FavouritesFragment() {
		tracks = new ArrayList<Track>();

		for (int i = 0; i < 10; i++) {
			Track t = new Track();
			t.setId(String.valueOf(i));
			tracks.add(t);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getActivity().setTitle(R.string.favourites);

		View rootView = inflater.inflate(R.layout.fragment_favourites,
				container, false);

		ListView list = (ListView) rootView.findViewById(android.R.id.list);

		TracksListAdapter adapter = new TracksListAdapter(getActivity(), tracks);

		list.setAdapter(adapter);

		return rootView;
	}
}