package com.ee.matkarakendus.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Track;

public class PicturesFragment extends Fragment {

	private Track track;

	public PicturesFragment(Track track) {
		this.track = track;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pictures, container,
				false);

		ListView pictures = (ListView) rootView.findViewById(android.R.id.list);

		return rootView;
	}
}