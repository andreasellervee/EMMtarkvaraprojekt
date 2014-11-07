package com.ee.matkarakendus.activities;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.fragments.CommentsFragment;
import com.ee.matkarakendus.fragments.InfoFragment;
import com.ee.matkarakendus.fragments.MapDisplayFragment;
import com.ee.matkarakendus.fragments.PicturesFragment;
import com.ee.matkarakendus.objects.Point;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.objects.Tracks;
import com.ee.matkarakendus.utils.TrackPOIUtil;
import com.ee.matkarakendus.utils.TrackPolylineUtil;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackViewActivity extends Activity implements TabListener,
		OnItemClickListener {
	private String[] optionItems;
	private DrawerLayout drawer;
	private ListView menuList;
	private ActionBarDrawerToggle drawerToggle;

	private Track track;
	private PolylineOptions poly;
	private List<Point> points;

	private ActionBar actionBar;
	private Tab info, map, pictures, comments;

	private Fragment infoFragment, mapFragment, picturesFragment,
			commentsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_track_view);

		track = (Track) getIntent().getSerializableExtra("track");
		poly = new TrackPolylineUtil().getTrackPolylineById(track.getId());
		points = new TrackPOIUtil().getTrackPOIsById(track.getId());

		infoFragment = new InfoFragment(track);
		mapFragment = new MapDisplayFragment(poly, track, points);
		picturesFragment = new PicturesFragment(track);
		commentsFragment = new CommentsFragment(track);

		setTitle(track.getName());

		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, infoFragment).commit();

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

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		info = actionBar.newTab();
		info.setText("Info");
		info.setTabListener(this);

		map = actionBar.newTab();
		map.setText("Kaart");
		map.setTabListener(this);

		pictures = actionBar.newTab();
		pictures.setText("Pildid");
		pictures.setTabListener(this);

		comments = actionBar.newTab();
		comments.setText("Kommentaarid");
		comments.setTabListener(this);

		actionBar.addTab(info);
		actionBar.addTab(map);
		actionBar.addTab(pictures);
		actionBar.addTab(comments);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.track_view, menu);

		if (track.isFavourite()) {
			menu.getItem(0).getIcon().setColorFilter(0xFFFFFF00, Mode.MULTIPLY);
		} else {
			menu.getItem(0).getIcon().setColorFilter(0xFFFFFFFF, Mode.MULTIPLY);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.favourites_menu_button) {
			if (track.isFavourite()) {
				Tracks.List.get(Tracks.List.indexOf(track)).setFavourite(false);
				track.setFavourite(false);
				item.getIcon().setColorFilter(0xFFFFFFFF, Mode.MULTIPLY);
			} else {
				Tracks.List.get(Tracks.List.indexOf(track)).setFavourite(true);
				track.setFavourite(true);
				item.getIcon().setColorFilter(0xFFFFFF00, Mode.MULTIPLY);
			}
		}

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
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		switch (tab.getPosition()) {
		case 0:
			ft.replace(R.id.content_frame, infoFragment);
			return;
		case 1:
			if (poly == null || poly.getPoints().isEmpty()) {
				Toast.makeText(getApplicationContext(),
						track.getName() + getString(R.string.no_coordinates),
						Toast.LENGTH_SHORT).show();
			} else {
				ft.replace(R.id.content_frame, mapFragment);
			}
			return;
		case 2:
			ft.replace(R.id.content_frame, picturesFragment);
			return;
		case 3:
			ft.replace(R.id.content_frame, commentsFragment);
			return;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
}