package com.ee.matkarakendus.fragments;

import java.util.List;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.utils.TracksUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapDisplayFragment extends Fragment implements OnCameraChangeListener, OnMyLocationChangeListener {
	
	private PolylineOptions poly;
	
	private GoogleMap map;
	
	private TextView asi;
	
	private Track track;
	
	public MapDisplayFragment() {}
	
	public MapDisplayFragment(PolylineOptions poly, Track track) {
			this.poly = poly;
			this.track = track;
		}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_map, null, false);
		asi = (TextView)v.findViewById(R.id.m66tkava);
		
		map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		
		if(map == null) {
			return v;
		}
		
		map.setOnCameraChangeListener(this);
		
		map.setMyLocationEnabled(true);
		
        if(poly == null) {
        	LatLng estonia = new LatLng(59.0000, 25.5000);
        	
        	map.setMyLocationEnabled(true);
        	map.moveCamera(CameraUpdateFactory.newLatLngZoom(estonia, 6));
        	
        	map.addMarker(new MarkerOptions()
        	.title("Estonia")
        	.snippet("Let the adventure begin.")
        	.position(estonia));
        	
        	List<MarkerOptions> allTracksMarkers = new TracksUtil(getActivity().getApplicationContext()).getAllTrackMarkers();
        	
        	for(MarkerOptions opt : allTracksMarkers) {
        		map.addMarker(opt);
        	}
        } else {
        	map.clear();
        	map.addPolyline(poly);
        	
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
        	
        	if(!poly.getPoints().isEmpty()) {
	        	map.addMarker(new MarkerOptions()
	        	.title(track.getName())
	        	.position(poly.getPoints().get(0)));
	        	
	        	map.animateCamera(CameraUpdateFactory.newLatLngZoom(poly.getPoints().get(0), 11.5F));
        	}
        }
        
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
	    if (map != null) {
	        getFragmentManager().beginTransaction()
	            .remove(getFragmentManager().findFragmentById(R.id.map)).commit();
	        map = null;
	    }
	}

	@Override
	public void onCameraChange(CameraPosition position) {
		Log.i("ZOOOM", String.valueOf(position.zoom));
		asi.setText("|______|" + String.valueOf(position.zoom));
	}

	@Override
	public void onMyLocationChange(Location location) {
		//nothing right now
		
	}
	
}