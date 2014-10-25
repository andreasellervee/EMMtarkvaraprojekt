package com.ee.matkarakendus.fragments;

import java.util.ArrayList;
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

import com.ee.matkarakendus.R;

public class TrackFragment extends Fragment implements TabListener {
	
	Fragment infoFrag = new InfoFragment();
	Fragment kaartFrag = new MapDisplayFragment();
	Fragment pildidFrag = new PicturesFragment(); 		
	

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
		return rootView;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		switch (tab.getPosition()) {
		case 0:
			ft.replace(android.R.id.content, infoFrag);
			break;
		case 1:
			ft.replace(android.R.id.content, kaartFrag);
			break;
		case 2:
			ft.replace(android.R.id.content, pildidFrag);
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