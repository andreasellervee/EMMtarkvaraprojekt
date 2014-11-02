package com.ee.matkarakendus;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ee.matkarakendus.fragments.TrackFragment;
import com.ee.matkarakendus.objects.Track;

public class TrackViewActivity extends Activity {

	private Track track;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_view);
		Intent i = getIntent();
		track = (Track) i.getSerializableExtra("track");
		TrackFragment fragment = new TrackFragment(track);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame_track, fragment).commit();
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
}