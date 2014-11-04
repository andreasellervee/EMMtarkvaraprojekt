package com.ee.matkarakendus.activities;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.fragments.SettingsFragment;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.objects.Tracks;
import com.ee.matkarakendus.utils.TracksUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends Activity implements OnCameraChangeListener,
		OnMyLocationChangeListener, OnInfoWindowClickListener {
	private DrawerLayout drawer;
	private ListView list;
	private ActionBarDrawerToggle drawerToggle;

	private CharSequence title;
	private String[] optionItems;

	private PolylineOptions poly;

	private GoogleMap map;

	private Track track;

	private List<MarkerOptions> markers;

	Map<Track, MarkerOptions> allTracksMarkers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		TracksUtil tracksUtil = new TracksUtil(getApplicationContext());
		Tracks.List = tracksUtil.getAllTracks();

		title = getTitle();
		optionItems = getResources().getStringArray(R.array.option_items_array);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		list = (ListView) findViewById(R.id.left_drawer);

		drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		list.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, optionItems));
		list.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(title);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(title);
				invalidateOptionsMenu();
			}
		};

		drawer.setDrawerListener(drawerToggle);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		if (map == null) {
			return;
		}

		map.setOnCameraChangeListener(this);

		map.setMyLocationEnabled(true);

		if (poly == null) {
			LatLng estonia = new LatLng(59.0000, 25.5000);

			map.setMyLocationEnabled(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(estonia, 6));

			allTracksMarkers = new TracksUtil(getApplicationContext())
					.getAllTrackMarkers();

			for (Track track : allTracksMarkers.keySet()) {
				map.addMarker(allTracksMarkers.get(track));
			}

			map.setOnInfoWindowClickListener(this);

		} else {
			map.clear();
			map.addPolyline(poly);

			if (!markers.isEmpty()) {
				for (MarkerOptions marker : markers) {
					map.addMarker(marker);
				}
			}

			if (!poly.getPoints().isEmpty()) {
				map.addMarker(new MarkerOptions().title(track.getName())
						.position(
								new LatLng(track.getLatitude(), track
										.getLongitude())));

				map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						track.getLatitude(), track.getLongitude()), 12F));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = drawer.isDrawerOpen(list);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.action_settings:
			Fragment fragment = new SettingsFragment();

			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Intent i = null;

		switch (position) {
		case 1:
			i = new Intent(getApplicationContext(), AllTracksActivity.class);
			break;
		case 2:
			i = new Intent(getApplicationContext(), SearchActivity.class);
			break;
		case 3:
			i = new Intent(getApplicationContext(), FavouritesActivity.class);
			break;
		default:
			return;
		}

		startActivity(i);
		drawer.closeDrawer(list);
	}

	@Override
	public void setTitle(CharSequence title) {
		this.title = title;
		getActionBar().setTitle(title);
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

	public GoogleMap getMap() {
		return this.map;
	}

	public void setPolys(PolylineOptions poly) {
		if (map != null) {
			this.map.addPolyline(poly);
		}
	}

	@Override
	public void onCameraChange(CameraPosition position) {
	}

	@Override
	public void onMyLocationChange(Location location) {
		// nothing right now

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		if (allTracksMarkers != null) {
			final Track track = getTrackById(marker);
			buildAlertDialog(track);
		}

	}

	private Track getTrackById(Marker marker) {
		return new TracksUtil(this).getTrackByTitle(marker.getTitle());
	}

	private void buildAlertDialog(final Track track) {
		new AlertDialog.Builder(this)
				.setTitle("Raja vaade")
				.setMessage("Kas tahad minna raja vaatesse?")
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent(getApplicationContext(),
										TrackViewActivity.class);
								i.putExtra("track", track);
								startActivity(i);
							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
							}
						}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}
}