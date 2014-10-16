package com.ee.matkarakendus.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.utils.TrackPolylineUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapDisplayFragment extends Fragment {
	
	private PolylineOptions poly;
	
	private GoogleMap map;
	
	public MapDisplayFragment() {
		
	}
	
public MapDisplayFragment(PolylineOptions poly) {
		this.poly = poly;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_map, null, false);
		
		map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		
		map.setMyLocationEnabled(true);
		
		LatLng estonia = new LatLng(59.0000, 25.5000);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(estonia, 6));

        map.addMarker(new MarkerOptions()
                .title("Estonia")
                .snippet("Let the adventure begin.")
                .position(estonia));
        
        TrackPolylineUtil util = new TrackPolylineUtil();
        map.addPolyline(util.getTrackPolylineById(7));
        
        map.addMarker(new MarkerOptions()
        .title("RMK Hüpassaare lõkkekoht")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.campfire))
        .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
        .position(new LatLng(58.530688, 25.271323)));
        
        map.addMarker(new MarkerOptions()
        .title("RMK Hüpassaare piknikukoht")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.picnic))
        .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
        .position(new LatLng(58.5307998657, 25.24751091)));
        
        map.addMarker(new MarkerOptions()
        .title("RMK Hüpassaare matkarada")
        .position(new LatLng(58.52716, 25.26949)));

		return v;
		}
	
	public GoogleMap getMap() {
		return this.map;
	}
	
	public void setPolys(PolylineOptions poly) {
		if(map != null) {
			this.map.addPolyline(poly);
		}
		
	}
	
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    FragmentManager fm = getActivity().getFragmentManager();
	    Fragment fragment = (fm.findFragmentById(R.id.map));
	    FragmentTransaction ft = fm.beginTransaction();
	    ft.remove(fragment);
	    ft.commit();
	}
	
}