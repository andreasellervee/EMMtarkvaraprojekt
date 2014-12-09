package com.ee.matkarakendus.fragments;

import java.util.ArrayList;
import java.util.Locale;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.activities.PointsSearchResultsActivity;
import com.ee.matkarakendus.activities.TracksSearchResultsActivity;
import com.ee.matkarakendus.objects.Point;
import com.ee.matkarakendus.objects.Points;
import com.ee.matkarakendus.objects.Track;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PointsSearchFragment extends Fragment {
	Resources res;

	Button searchAll, searchNear;
	Spinner area, type;
	EditText string, radius;

	private Location location;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_points_search,
				container, false);
		
		LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);
		
		LocationListener locationListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				PointsSearchFragment.this.location = location;
			}
		};
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

		res = getResources();

		searchAll = (Button) rootView.findViewById(R.id.searchAll);
		searchNear = (Button) rootView.findViewById(R.id.searchNear);
		area = (Spinner) rootView.findViewById(R.id.area);
		type = (Spinner) rootView.findViewById(R.id.type);
		string = (EditText) rootView.findViewById(R.id.string);
		radius = (EditText) rootView.findViewById(R.id.searchPOIRadius);

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

	private ArrayList<Point> filterResults() {
		ArrayList<Point> results = Points.List;

		results = filterCounty(results);
		results = filterType(results);
		results = filterString(results);

		return results;
	}

	private ArrayList<Point> filterString(ArrayList<Point> results) {
		ArrayList<Point> temp = new ArrayList<Point>();

		String sone = string.getText().toString().toLowerCase(Locale.ENGLISH);

		if (!sone.equals("")) {
			for (Point p : results) {
				if (p.getName().toLowerCase(Locale.ENGLISH).contains(sone)
						|| p.getDescription().toLowerCase(Locale.ENGLISH)
								.contains(sone)) {
					temp.add(p);
				}
			}
		} else {
			temp = results;
		}

		return temp;
	}

	private ArrayList<Point> filterType(ArrayList<Point> results) {
		ArrayList<Point> temp = new ArrayList<Point>();

		if (type.getSelectedItemId() != 0) {
//			String[] types = res.getStringArray(R.array.point_type_array);
//			for (Point p : results) {
//				if (p.getType().equals(types[(int) type.getSelectedItemId()])) {
//					temp.add(p);
//				}
//			}
			temp = results;
		} else {
			temp = results;
		}

		return temp;
	}

	private ArrayList<Point> filterCounty(ArrayList<Point> results) {
		ArrayList<Point> temp = new ArrayList<Point>();

		if (area.getSelectedItemId() != 0) {
			String[] countys = res.getStringArray(R.array.county_array);
			for (Point p : results) {
				if (p.getCounty().equals(
						countys[(int) area.getSelectedItemId()])) {
					temp.add(p);
				}
			}
		} else {
			temp = results;
		}

		return temp;
	}

	void searchAll() {
		ArrayList<Point> results = filterResults();

		if (results.isEmpty()) {
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.no_search_results), Toast.LENGTH_SHORT)
					.show();
		} else {
			Intent i = new Intent(getActivity().getApplicationContext(),
					PointsSearchResultsActivity.class);
			i.putExtra("points", results);
			startActivity(i);
		}
	}

	void searchNear() {
		
		ArrayList<Point> results = filterResults();
		
		String text = radius.getText().toString();
		
		double searchRadiusValue = 0;
		
		ArrayList<Point> nearResults = new ArrayList<Point>();
		
		if(!text.equals("")) {
			try{
				searchRadiusValue = Double.valueOf(text);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				Toast.makeText(getActivity().getApplicationContext(),
						getString(R.string.no_number_value), Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.no_radius_value), Toast.LENGTH_SHORT)
					.show();
		}
		
		if(location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			
			if(results.isEmpty()) {
				Toast.makeText(getActivity().getApplicationContext(),
						getString(R.string.no_search_results), Toast.LENGTH_SHORT)
						.show();
			} else {
				for(Point point : results) {
					double lat1 = point.getLatitude();
					double lng1 = point.getLongitude();
					double distance = distance(lat, lng, lat1, lng1);
					if(distance <= searchRadiusValue) {
						nearResults.add(point);
					}
				}
			}
		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.no_location_found), Toast.LENGTH_SHORT)
					.show();
		}
		
		if(nearResults != null) {
			if(nearResults.isEmpty()) {
				Toast.makeText(getActivity().getApplicationContext(),
						getString(R.string.no_search_results), Toast.LENGTH_SHORT)
						.show();
			} else {
				Intent i = new Intent(getActivity().getApplicationContext(),
						PointsSearchResultsActivity.class);
				i.putExtra("points", nearResults);
				startActivity(i);
			}
		}
		
	}
	
	private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
      }

      private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }

      private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }
}