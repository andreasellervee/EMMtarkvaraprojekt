package com.ee.matkarakendus.activities;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.fragments.InfoFragment;
import com.ee.matkarakendus.fragments.MapDisplayFragment;
import com.ee.matkarakendus.fragments.PicturesFragment;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.utils.TrackPOIUtil;
import com.ee.matkarakendus.utils.TrackPolylineUtil;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackViewActivity extends Activity implements TabListener {

	private Track track;
	private PolylineOptions poly;
	private List<MarkerOptions> markers;

	private ActionBar actionBar;
	private Tab info, map, pictures;

	private Fragment infoFragment, mapFragment, picturesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_track_view);

		track = (Track) getIntent().getSerializableExtra("track");
		poly = new TrackPolylineUtil(getApplicationContext())
				.getTrackPolylineById(track.getId());
		markers = new TrackPOIUtil(getApplicationContext())
				.getTrackPOIsById(track.getId());

		infoFragment = new InfoFragment(track);
		mapFragment = new MapDisplayFragment(poly, track, markers);
		picturesFragment = new PicturesFragment(track);

		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame_track, infoFragment).commit();

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

		actionBar.addTab(info);
		actionBar.addTab(map);
		actionBar.addTab(pictures);
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
				track.setFavourite(false);
				item.getIcon().setColorFilter(0xFFFFFFFF, Mode.MULTIPLY);
			} else {
				track.setFavourite(true);
				item.getIcon().setColorFilter(0xFFFFFF00, Mode.MULTIPLY);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		switch (tab.getPosition()) {
		case 0:
			ft.replace(R.id.content_frame_track, infoFragment);
			return;
		case 1:
			if (poly == null || poly.getPoints().isEmpty()) {
				Toast.makeText(getApplicationContext(),
						track.getName() + getString(R.string.no_coordinates),
						Toast.LENGTH_SHORT).show();
			} else {
				ft.replace(R.id.content_frame_track, mapFragment);
			}
			return;
		case 2:
			ft.replace(R.id.content_frame_track, picturesFragment);
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