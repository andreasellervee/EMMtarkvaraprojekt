package com.ee.matkarakendus.fragments;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.PointsListAdapter;
import com.ee.matkarakendus.objects.Track;

public class InfoFragment extends Fragment {

	private Track track;

	private TextView location, length, isOpen, description, noPoints;
	private ListView pointsList;

	public InfoFragment(Track track) {
		this.track = track;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_info, container,
				false);

		location = (TextView) rootView.findViewById(R.id.asukoht);
		length = (TextView) rootView.findViewById(R.id.pikkus);
		isOpen = (TextView) rootView.findViewById(R.id.avatud);
		description = (TextView) rootView.findViewById(R.id.kirjeldus);
		pointsList = (ListView) rootView.findViewById(R.id.pointsList);
		noPoints = (TextView) rootView.findViewById(R.id.noPoints);

		if (track != null) {
			location.setText("Asukoht: " + track.getCountry() + ", "
					+ track.getCounty());
			length.setText("Pikkus: " + round(track.getLength(), 1) + " Km");
			isOpen.setText("Avatud rada: " + (track.getIsOpen() ? "Jah" : "Ei"));
			description.setText("Kirjeldus: " + track.getDescription());
		}

		pointsList.setAdapter(new PointsListAdapter(getActivity()
				.getApplicationContext(), track.getPoints()));

		if (track.getPoints().size() > 0) {
			LayoutParams lp = pointsList.getLayoutParams();
			lp.height = track.points.size() * 80;
			pointsList.setLayoutParams(lp);
			noPoints.setVisibility(View.INVISIBLE);
		} else {
			pointsList.setVisibility(View.INVISIBLE);
		}

		return rootView;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
