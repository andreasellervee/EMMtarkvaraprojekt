package com.maptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	
	private GoogleMap mMap; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();         
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);         
		final LatLng CIU = new LatLng(35.21843892856462, 33.41662287712097);         
		Marker ciu = mMap.addMarker(new MarkerOptions()                                   
		.position(CIU).title("My Office"));
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
	}
}
