package com.ee.matkarakendus.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.fragments.SettingsFragment;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.objects.Tracks;

public class AllTracksActivity extends Activity implements OnItemClickListener {
	private ListView list;
	private DrawerLayout drawer;
	private ActionBarDrawerToggle drawerToggle;
	private String[] optionItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_all_tracks);
		optionItems = getResources().getStringArray(R.array.option_items_array);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		list = (ListView) findViewById(R.id.left_drawer);
		drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		list.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, optionItems));
		list.setOnItemClickListener(new DrawerItemClickListener());
		
		ListView tracksList = (ListView) findViewById(android.R.id.list);
		tracksList.setAdapter(new TracksListAdapter(this, Tracks.List));
		tracksList.setOnItemClickListener(this);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(getTitle());
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(getTitle());
				invalidateOptionsMenu();
			}
		};
		drawer.setDrawerListener(drawerToggle);
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
		case 0:
			i = new Intent(getApplicationContext(), MainActivity.class);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Track track = (Track) parent.getItemAtPosition(position);
		Intent i = new Intent(getApplicationContext(), TrackViewActivity.class);
		i.putExtra("track", track);
		startActivity(i);
	}
}