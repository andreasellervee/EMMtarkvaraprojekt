package com.ee.matkarakendus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TracksSearchFragment extends Fragment {
	public TracksSearchFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tracks_search, container,
				false);

		getActivity().setTitle(R.string.search);
		return rootView;
	}
}