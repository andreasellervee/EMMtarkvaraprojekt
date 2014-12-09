package com.ee.matkarakendus.adapters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Track;

public class TracksListAdapter extends ArrayAdapter<Track> {
	private final ArrayList<Track> tracks;
	private final Context context;

	public TracksListAdapter(Context context, ArrayList<Track> tracks) {
		super(context, R.layout.tracks_list_item);
		this.tracks = tracks;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater li = LayoutInflater.from(context);
		ViewHolder holder;
		if (convertView == null) {
			convertView = li.inflate(R.layout.tracks_list_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.length = (TextView) convertView.findViewById(R.id.length);
			holder.logo = (ImageView) convertView.findViewById(R.id.logo);
			holder.thumb = (ImageView) convertView.findViewById(R.id.thumb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Track track = (Track) tracks.get(position);

		holder.title.setText(track.getName());
		holder.length.setText("Pikkus: " + round(track.getLength(), 1) + " Km");
		if (holder.thumb != null) {
			holder.thumb.setBackgroundResource(R.drawable.bg3);
		}
		return convertView;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	private static class ViewHolder {
		TextView title;
		TextView length;
		ImageView thumb;
		ImageView logo;
	}

	@Override
	public int getCount() {
		if(tracks != null) {
			return tracks.size();
		} 
		return 0;
	}

	@Override
	public Track getItem(int position) {
		return tracks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}