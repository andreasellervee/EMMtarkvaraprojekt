package com.ee.matkarakendus.fragments;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.activities.FavouritesActivity;
import com.ee.matkarakendus.activities.PointsSearchResultsActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PointsSearchFragment extends Fragment {
	Button searchAll, searchNear;
	Spinner area, type;
	EditText string;

	public PointsSearchFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_points_search,
				container, false);

		searchAll = (Button) rootView.findViewById(R.id.searchAll);
		searchNear = (Button) rootView.findViewById(R.id.searchNear);
		area = (Spinner) rootView.findViewById(R.id.area);
		type = (Spinner) rootView.findViewById(R.id.type);
		string = (EditText) rootView.findViewById(R.id.string);

		if (savedInstanceState != null) {
			area.setSelection((int) savedInstanceState.getLong("area"));
			type.setSelection((int) savedInstanceState.getLong("type"));
			string.setText(savedInstanceState.getString("string"));
		}

		searchAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchAll();
			}
		});

		searchNear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchNear();
			}
		});

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putLong("area", area.getSelectedItemId());
		outState.putLong("type", type.getSelectedItemId());
		outState.putString("string", string.getText().toString());
	}

	void searchAll() {
		Intent i = new Intent(getActivity().getApplicationContext(),
				PointsSearchResultsActivity.class);
		startActivity(i);
	}

	void searchNear() {
		Intent i = new Intent(getActivity().getApplicationContext(),
				PointsSearchResultsActivity.class);
		startActivity(i);
	}
}