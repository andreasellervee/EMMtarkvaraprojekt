package com.ee.matkarakendus.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Track;

public class TracksListAdapter extends ArrayAdapter<Track> {
	private final Context context;
	private final ArrayList<Track> tracks;

	public TracksListAdapter(Context context, ArrayList<Track> tracks) {
		super(context, R.layout.track_cell, tracks);
		this.context = context;
		this.tracks = tracks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View row = inflater.inflate(R.layout.tracks_list_item, parent, false);

		TextView id = (TextView) row.findViewById(R.id.title);
		TextView description = (TextView) row.findViewById(R.id.description);

		Track track = tracks.get(position);

		id.setText(track.getName());

		if (track.getDescription() == null) {
			description.setText("N/A");
		} else if (track.getDescription().length() > 50) {
			description
					.setText(track.getDescription().substring(0, 47) + "...");
		} else {
			description.setText(track.getDescription());
		}

		return row;
	}
}