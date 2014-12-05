package com.ee.matkarakendus.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.TracksListAdapter;
import com.ee.matkarakendus.objects.Track;

public class TracksSearchResultsActivity extends Activity implements
		OnItemClickListener {

	private ArrayList<Track> tracks = new ArrayList<Track>();

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.tracks);

		setContentView(R.layout.activity_search_results);

		tracks = (ArrayList<Track>)getIntent().getSerializableExtra("tracks");

		ListView list = (ListView) findViewById(android.R.id.list);

		TracksListAdapter adapter = new TracksListAdapter(this, tracks);

		list.setAdapter(adapter);

		list.setOnItemClickListener(this);
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