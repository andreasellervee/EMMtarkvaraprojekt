package com.ee.matkarakendus.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.objects.Tracks;

public class FavouritesActivity extends Activity implements OnItemClickListener {
	private ArrayList<Track> tracks;

	private String[] optionItems;
	private DrawerLayout drawer;
	private ListView menuList;
	private ActionBarDrawerToggle drawerToggle;

	private ListView tracksList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tracks = new ArrayList<Track>();

		for (Track t : Tracks.List) {
			if (t.isFavourite()) {
				tracks.add(t);
			}
		}

		setContentView(R.layout.activity_favourites);

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

		drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		drawer.setDrawerListener(drawerToggle);

		TracksListAdapter adapter = new TracksListAdapter(this, tracks);

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

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent i = null;

			switch (position) {
			case 0:
				i = new Intent(getApplicationContext(), MainActivity.class);
				break;
			case 1:
				i = new Intent(getApplicationContext(), AllTracksActivity.class);
				break;
			case 2:
				i = new Intent(getApplicationContext(), SearchActivity.class);
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
}