package com.ee.matkarakendus.adapters;

import java.util.ArrayList;
import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Track;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

		id.setText("ID: " + track.getId());
		description.setText("Kirjeldus: " + track.getDescription());

		return row;
	}
}