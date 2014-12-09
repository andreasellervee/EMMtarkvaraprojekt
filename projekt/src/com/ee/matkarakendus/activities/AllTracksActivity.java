package com.ee.matkarakendus.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.fragments.TracksSearchFragment;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.objects.Tracks;

public class AllTracksActivity extends Activity implements OnItemClickListener {
	private String[] optionItems;
	private DrawerLayout drawer;
	private ListView menuList;
	private ActionBarDrawerToggle drawerToggle;

	private ListView tracksList;
	private TracksListAdapter adapter;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent i = getIntent();
		double mapLat = i.getDoubleExtra("lat", 0d);
		double mapLng = i.getDoubleExtra("lng", 0d);
		
		if(mapLat != 0d && mapLng != 0d) {
			location = new Location(location);
			location.setLatitude(mapLng);
			location.setLongitude(mapLng);
		}

		setContentView(R.layout.activity_all_tracks);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		optionItems = getResources().getStringArray(R.array.option_items_array);

		menuList = (ListView) findViewById(R.id.left_drawer);
		menuList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, optionItems));
		menuList.setOnItemClickListener(new DrawerItemClickListener());

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		
		LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
		
		LocationListener locationListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				AllTracksActivity.this.location = location;
			}
		};
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

		drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		drawer.setDrawerListener(drawerToggle);
		
		if(location != null) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.using_user_location), Toast.LENGTH_SHORT)
					.show();
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			
			for(Track track : Tracks.List) {
				double lat1 = track.getLatitude();
				double lng1 = track.getLongitude();
				double distance = distance(lat, lng, lat1, lng1);
				track.setFromUserLocation(distance);
			}
		}

		adapter = new TracksListAdapter(this, Tracks.List);

		tracksList = (ListView) findViewById(android.R.id.list);
		tracksList.setAdapter(adapter);
		tracksList.setOnItemClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return false;
		}
	}

	private class DrawerItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent i = null;

			switch (position) {
			case 0:
				i = new Intent(getApplicationContext(), MainActivity.class);
				break;
			case 2:
				i = new Intent(getApplicationContext(), SearchActivity.class);
				break;
			case 3:
				i = new Intent(getApplicationContext(),
						FavouritesActivity.class);
				break;
			default:
				return;
			}

			startActivity(i);
			drawer.closeDrawer(menuList);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Track track = (Track) parent.getItemAtPosition(position);
		Intent i = new Intent(getApplicationContext(), TrackViewActivity.class);
		i.putExtra("track", track);
		startActivity(i);
	}
	
	private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
      }

      private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }

      private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }
}