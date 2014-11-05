package com.ee.matkarakendus.activities;

import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Point;
import com.ee.matkarakendus.objects.Points;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.objects.Tracks;
import com.ee.matkarakendus.utils.TrackPOIUtil;
import com.ee.matkarakendus.utils.TracksUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements
		OnInfoWindowClickListener, ListView.OnItemClickListener {
	private String[] optionItems;
	private DrawerLayout drawer;
	private ListView menuList;
	private ActionBarDrawerToggle drawerToggle;

	private GoogleMap map;
	private Map<Track, MarkerOptions> trackMarkers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Tracks.List = new TracksUtil(getApplicationContext()).getAllTracks();
		Points.List = new TrackPOIUtil().getTrackPOIsById(0);

		for (Point p : Points.List) {
			for (Track t : Tracks.List) {
				if (p.getTrackId() == t.getId()) {
					t.getPoints().add(p);
					break;
				}
			}
		}

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		optionItems = getResources().getStringArray(R.array.option_items_array);

		menuList = (ListView) findViewById(R.id.left_drawer);
		menuList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, optionItems));
		menuList.setOnItemClickListener(this);

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

		drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		drawer.setDrawerListener(drawerToggle);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		if (map == null) {
			return;
		}

		LatLng estonia = new LatLng(59.0000, 25.5000);

		trackMarkers = new TracksUtil(getApplicationContext())
				.getAllTrackMarkers();

		map.setMyLocationEnabled(true);
		map.setOnInfoWindowClickListener(this);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(estonia, 6));

		for (Track track : trackMarkers.keySet()) {
			map.addMarker(trackMarkers.get(track));
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
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
		drawer.closeDrawer(menuList);
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
	public void onInfoWindowClick(Marker marker) {
		if (trackMarkers != null) {
			final Track track = new TracksUtil(this).getTrackByTitle(marker
					.getTitle());
			buildAlertDialog(track);
		}
	}

	private void buildAlertDialog(final Track track) {
		new AlertDialog.Builder(this)
				.setTitle("Raja vaade")
				.setMessage("Kas tahad minna raja vaatesse?")
				.setIcon(android.R.drawable.ic_dialog_alert)
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

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();
	}
}