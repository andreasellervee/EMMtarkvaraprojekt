package com.ee.matkarakendus.fragments;

import java.util.ArrayList;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.objects.Track;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class TrackFragment extends Fragment {



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.track,
				container, false);
		
		final Button info = (Button) rootView.findViewById(R.id.info);
		final Button kaart = (Button) rootView.findViewById(R.id.kaart);
		final Button pildid = (Button) rootView.findViewById(R.id.pildid);
		
		info.setSelected(true);
		kaart.setSelected(false);
		pildid.setSelected(false);
		
		info.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				info.setSelected(true);
				kaart.setSelected(false);
				pildid.setSelected(false);
				
			}
		});
		kaart.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				info.setSelected(false);
				kaart.setSelected(true);
				pildid.setSelected(false);
				
			}
		});
		pildid.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				info.setSelected(false);
				kaart.setSelected(false);
				pildid.setSelected(true);
				
			}
		});
		
		return rootView;
	}
	
}