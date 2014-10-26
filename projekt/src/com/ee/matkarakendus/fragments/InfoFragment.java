package com.ee.matkarakendus.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Track;

public class InfoFragment extends Fragment {
	
	private Track track;
	
	private TextView location, length, duration, type, isOpen, description; 
	
	public InfoFragment(Track track) {
		this.track = track;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_info, container,
				false);

		getActivity().setTitle(track.getName() + " - " + getString(R.string.info));
		
		location = (TextView) rootView.findViewById(R.id.asukoht);
		length = (TextView) rootView.findViewById(R.id.pikkus);
		duration = (TextView) rootView.findViewById(R.id.kestus);
		type = (TextView) rootView.findViewById(R.id.tuup);
		isOpen = (TextView) rootView.findViewById(R.id.avatud);
		description = (TextView) rootView.findViewById(R.id.kirjeldus);
		
		if(track != null) {
			location.setText("Asukoht: " + track.getCounty());
			length.setText("Pikkus: " + track.getLength());
			type.setText("Tüüp: " + track.getType());
			isOpen.setText("Avatud rada: " + (track.getIsOpen() ? "Jah" : "Ei"));
			description.setText("Kirjeldus: " + track.getDescription());
		}
		
		return rootView;
	}
}
