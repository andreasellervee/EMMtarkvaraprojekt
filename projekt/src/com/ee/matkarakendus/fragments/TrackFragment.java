package com.ee.matkarakendus.fragments;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.utils.TrackPOIUtil;
import com.ee.matkarakendus.utils.TrackPolylineUtil;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackFragment extends Fragment implements TabListener {
	
	private PolylineOptions poly;
	private List<MarkerOptions> markers;
	private Track track;
	

	public TrackFragment(Track track, PolylineOptions poly) {
		this.poly = poly;
		this.track = track;
	}

	public TrackFragment() {
		// TODO Auto-generated constructor stub
	}

	public TrackFragment(Track track) {
		this.track = track;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);  
	    ActionBar bar = getActivity().getActionBar();
	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	     
	    Tab info = bar.newTab();
	    Tab kaart = bar.newTab();
	    Tab pildid = bar.newTab();
	    
        info.setText(" Info ");
        kaart.setText(" Kaart ");
        pildid.setText(" Pildid ");
        
        info.setTabListener(this);
        kaart.setTabListener(this);
        pildid.setTabListener(this);
        
        bar.addTab(info);
        bar.addTab(kaart);
        bar.addTab(pildid);        
	

		View rootView = inflater.inflate(R.layout.track,
				container, false);
		
		getTrackPolylineOptions(track.getId());
		
<<<<<<< HEAD
		getTrackPOIs(track.getId());
		
		/***
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
		*/
=======
>>>>>>> FETCH_HEAD
		return rootView;
	}

	private void getTrackPolylineOptions(int trackId) {
		this.poly = new TrackPolylineUtil(getActivity().getApplicationContext())
			.getTrackPolylineById(trackId);
	}
	
	private void getTrackPOIs(int trackId) {
		this.markers = new TrackPOIUtil(getActivity().getApplicationContext()).getTrackPOIsById(trackId);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		switch (tab.getPosition()) {
		case 0:
			ft.replace(android.R.id.content, new InfoFragment(track));
			break;
		case 1:
			if(poly == null ||  poly.getPoints().isEmpty()) {
				Toast.makeText(getActivity().getApplicationContext(), track.getName() + 
						getString(R.string.no_coordinates),
						   Toast.LENGTH_SHORT).show();
			} else {
				ft.replace(android.R.id.content, new MapDisplayFragment(poly, track, markers));
			}
			break;
		case 2:
			ft.replace(android.R.id.content, new PicturesFragment(track));
			break;
		}
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
}