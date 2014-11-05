package com.ee.matkarakendus.fragments;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.activities.MapViewActivity;
import com.ee.matkarakendus.objects.Track;

public class InfoFragment extends Fragment {
	
	private Track track;
	
	private Button map_button;
	
	private TextView location, length, type, isOpen, description; 
	
	public InfoFragment(Track track) {
		this.track = track;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_info, container,
				false);

		getActivity().setTitle(track.getName() + " - " + getString(R.string.info));		
		
		map_button = (Button) rootView.findViewById(R.id.map_button);
		map_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getApplicationContext(), MapViewActivity.class);
				startActivity(i);
			}
		});
		
		location = (TextView) rootView.findViewById(R.id.asukoht);
		length = (TextView) rootView.findViewById(R.id.pikkus);
		type = (TextView) rootView.findViewById(R.id.tuup);
		isOpen = (TextView) rootView.findViewById(R.id.avatud);
		description = (TextView) rootView.findViewById(R.id.kirjeldus);
		
		description.setMovementMethod(new ScrollingMovementMethod());
		
		if(track != null) {
			location.setText("Asukoht: " + track.getCountry() + ", " + track.getCounty());
			length.setText("Pikkus: " + round(track.getLength(),1) + " Km");
			type.setText("T��p: " + track.getType());
			isOpen.setText("Avatud rada: " + (track.getIsOpen() ? "Jah" : "Ei"));
			description.setText("Kirjeldus: " + track.getDescription());
		}
		
		return rootView;
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}