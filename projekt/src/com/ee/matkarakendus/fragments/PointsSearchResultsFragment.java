package com.ee.matkarakendus.fragments;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.PointsListAdapter;
import com.ee.matkarakendus.objects.Points;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PointsSearchResultsFragment extends Fragment {

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

		return rootView;
	}
}