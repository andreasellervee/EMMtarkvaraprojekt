package com.ee.matkarakendus.activities;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
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
import com.ee.matkarakendus.fragments.PointsSearchFragment;
import com.ee.matkarakendus.fragments.TracksSearchFragment;

public class SearchActivity extends Activity implements TabListener,
		OnItemClickListener {
	private String[] optionItems;
	private DrawerLayout drawer;
	private ListView menuList;
	private ActionBarDrawerToggle drawerToggle;

	private ActionBar actionBar;
	private Tab tracks, points;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);

		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new TracksSearchFragment())
				.commit();

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

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		tracks = actionBar.newTab();
		tracks.setText(R.string.tracks);
		tracks.setTabListener(this);

		points = actionBar.newTab();
		points.setText(R.string.pointsOfInterest);
		points.setTabListener(this);

		actionBar.addTab(tracks);
		actionBar.addTab(points);
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
		case 0:
			i = new Intent(getApplicationContext(), MainActivity.class);
			break;
		case 1:
			i = new Intent(getApplicationContext(), AllTracksActivity.class);
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
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getPosition() == 1) {
			ft.replace(R.id.content_frame, new PointsSearchFragment());
		} else if (tab.getPosition() == 0) {
			ft.replace(R.id.content_frame, new TracksSearchFragment());
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
}