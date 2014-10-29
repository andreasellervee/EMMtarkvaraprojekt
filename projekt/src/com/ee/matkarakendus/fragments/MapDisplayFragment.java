package com.ee.matkarakendus.fragments;

import java.util.Map;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.TrackViewActivity;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.utils.TracksUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapDisplayFragment extends Fragment implements OnCameraChangeListener, OnMyLocationChangeListener, OnInfoWindowClickListener {
	
	private PolylineOptions poly;
	
	private GoogleMap map;
	
	private Track track;
	
	Map<Track, MarkerOptions> allTracksMarkers;
	
	public MapDisplayFragment() {}
	
	public MapDisplayFragment(PolylineOptions poly, Track track) {
			this.poly = poly;
			this.track = track;
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
		
        if(poly == null) {
        	LatLng estonia = new LatLng(59.0000, 25.5000);
        	
        	map.setMyLocationEnabled(true);
        	map.moveCamera(CameraUpdateFactory.newLatLngZoom(estonia, 6));
        	
        	allTracksMarkers = new TracksUtil(getActivity().getApplicationContext()).getAllTrackMarkers();
        	
        	for(Track track : allTracksMarkers.keySet()) {
        		map.addMarker(allTracksMarkers.get(track));
        	}
        	
        	map.setOnInfoWindowClickListener(this);
        	
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
	        	.position(new LatLng(track.getLatitude(), track.getLongitude())));
	        	
	        	map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(track.getLatitude(), track.getLongitude()), 12F));
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
	        map = null;
	    }
	    getFragmentManager().beginTransaction()
	    .remove(getFragmentManager().findFragmentById(R.id.map)).commit();
	}

	@Override
	public void onCameraChange(CameraPosition position) {
//		Log.i("ZOOOM", String.valueOf(position.zoom));
//		asi.setText("|______|" + String.valueOf(position.zoom));
	}

	@Override
	public void onMyLocationChange(Location location) {
		//nothing right now
		
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		if(allTracksMarkers != null) {
			final Track track = getTrackById(marker);
			buildAlertDialog(track);
		}
		
	}

	private Track getTrackById(Marker marker) {
		return new TracksUtil(getActivity().getApplicationContext()).getTrackByTitle(marker.getTitle());
	}

	private void buildAlertDialog(final Track track) {
		new AlertDialog.Builder(getActivity())
		.setTitle("Raja vaade")
		.setMessage("Kas tahad minna raja vaatesse?")
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) { 
		        Intent i = new Intent(getActivity().getApplicationContext(), TrackViewActivity.class);
		        i.putExtra("track", track);
		        startActivity(i);
		    }
		 })
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) { 
		        // do nothing
		    }
		 })
		.setIcon(android.R.drawable.ic_dialog_alert)
		 .show();
	}
	
}