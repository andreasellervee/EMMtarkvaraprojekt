package com.eematkarakendus.fragments;

import java.util.ArrayList;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.PointsListAdapter;
import com.ee.matkarakendus.objects.Point;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PointsSearchResultsFragment extends Fragment {

	ArrayList<Point> points;

	public PointsSearchResultsFragment() {
		points = new ArrayList<Point>();

		for (int i = 0; i < 10; i++) {
			Point p = new Point();
			p.setId(String.valueOf(i));
			points.add(p);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getActivity().setTitle(R.string.search_results);

		View rootView = inflater.inflate(R.layout.fragment_search_results,
				container, false);

		ListView list = (ListView) rootView.findViewById(android.R.id.list);

		PointsListAdapter adapter = new PointsListAdapter(getActivity(), points);

		list.setAdapter(adapter);

		return rootView;
	}
}