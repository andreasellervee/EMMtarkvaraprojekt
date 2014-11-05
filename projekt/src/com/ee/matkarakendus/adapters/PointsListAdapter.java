package com.ee.matkarakendus.adapters;

import java.util.ArrayList;
import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Point;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PointsListAdapter extends ArrayAdapter<Point> {
	private final Context context;
	private final ArrayList<Point> points;

	public PointsListAdapter(Context context, ArrayList<Point> points) {
		super(context, R.layout.point_cell, points);
		this.context = context;
		this.points = points;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater li = LayoutInflater.from(context);
		ViewHolder holder;
		if (convertView == null) {
			convertView = li.inflate(R.layout.point_cell, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.location = (TextView) convertView.findViewById(R.id.location);
			holder.point = (ImageView) convertView.findViewById(R.id.point);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Point point = points.get(position);

		holder.title.setText(point.getName());
		holder.location.setText(point.getCountry()+", "+point.getCounty());
		if (holder.point != null) {
			holder.point.setBackgroundResource(R.drawable.bg3);
		}
		return convertView;

	}
	private static class ViewHolder {
		TextView title;
		TextView location;
		ImageView point;
	}
}