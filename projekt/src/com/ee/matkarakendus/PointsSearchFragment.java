package com.ee.matkarakendus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PointsSearchFragment extends Fragment {
	public PointsSearchFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_points_search, container,
				false);

		getActivity().setTitle(R.string.search);
		return rootView;
	}
}