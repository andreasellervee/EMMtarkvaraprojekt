package com.ee.matkarakendus.adapters;

import java.util.ArrayList;
import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Point;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View row = inflater.inflate(R.layout.track_cell, parent, false);

		TextView id = (TextView) row.findViewById(R.id.id);

		Point point = points.get(position);

		id.setText(point.getId());

		return row;
	}
}