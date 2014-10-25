package com.ee.matkarakendus.fragments;

import com.ee.matkarakendus.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PicturesFragment extends Fragment {
	public PicturesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pictures, container,
				false);

		getActivity().setTitle(R.string.pictures);
		return rootView;
	}
}
