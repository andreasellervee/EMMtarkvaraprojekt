package com.ee.matkarakendus.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.activities.PointViewActivity;
import com.ee.matkarakendus.adapters.PointsListAdapter;
import com.ee.matkarakendus.objects.Point;
import com.ee.matkarakendus.objects.Points;

public class PointsSearchResultsFragment extends Fragment implements
		OnItemClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getActivity().setTitle(R.string.search_results);

		View rootView = inflater.inflate(R.layout.fragment_search_results,
				container, false);

		ListView list = (ListView) rootView.findViewById(android.R.id.list);

		PointsListAdapter adapter = new PointsListAdapter(getActivity(),
				Points.List);

		list.setAdapter(adapter);

		list.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Point point = (Point) parent.getItemAtPosition(position);
		Intent i = new Intent(getActivity().getApplicationContext(),
				PointViewActivity.class);
		i.putExtra("point", point);
		startActivity(i);
	}
}
