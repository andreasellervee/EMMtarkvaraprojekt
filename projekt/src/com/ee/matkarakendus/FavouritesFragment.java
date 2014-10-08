package com.ee.matkarakendus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavouritesFragment extends Fragment {
	public FavouritesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_favourites,
				container, false);

		getActivity().setTitle(R.string.favourites);
		return rootView;
	}
}