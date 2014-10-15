package com.ee.matkarakendus.fragments;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.R.layout;
import com.ee.matkarakendus.R.string;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {
	public SettingsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);

		getActivity().setTitle(R.string.settings);
		return rootView;
	}
}