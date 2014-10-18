package com.ee.matkarakendus.fragments;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.utils.TracksUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapDisplayFragment extends Fragment implements OnCameraChangeListener {
	
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
		
		if(map == null) {
			return v;
		}
		
		map.setOnCameraChangeListener(this);
		
		map.setMyLocationEnabled(true);
		
		LatLng estonia = new LatLng(59.0000, 25.5000);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(estonia, 6));

        map.addMarker(new MarkerOptions()
                .title("Estonia")
                .snippet("Let the adventure begin.")
                .position(estonia));
        
        List<MarkerOptions> allTracksMarkers = new TracksUtil().getAllTrackMarkers();
        
        for(MarkerOptions opt : allTracksMarkers) {
        	map.addMarker(opt);
        }
        
        if(poly != null) {
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
        	
        	map.addMarker(new MarkerOptions()
        	.title("RMK Hüpassaare matkarada")
        	.position(new LatLng(58.52716, 25.26949)));
        	
        	map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(58.52716, 25.26949), 10));
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
	}
	
}