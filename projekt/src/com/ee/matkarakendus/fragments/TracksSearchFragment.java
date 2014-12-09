package com.ee.matkarakendus.fragments;

import java.util.ArrayList;
import java.util.Locale;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.activities.TracksSearchResultsActivity;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.objects.Tracks;

public class TracksSearchFragment extends Fragment {
	Resources res;

	Button searchAll, searchNear;
	Spinner openClosed, area, type;
	EditText lengthMin, lengthMax, durationMin, durationMax, string, searchRadius;
	
	Location location;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tracks_search,
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
				TracksSearchFragment.this.location = location;
			}
		};
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		
		res = getResources();

		searchAll = (Button) rootView.findViewById(R.id.searchAll);
		searchNear = (Button) rootView.findViewById(R.id.searchNear);
		openClosed = (Spinner) rootView.findViewById(R.id.track_open_closed);
		area = (Spinner) rootView.findViewById(R.id.area);
		type = (Spinner) rootView.findViewById(R.id.type);
		lengthMin = (EditText) rootView.findViewById(R.id.lengthMin);
		lengthMax = (EditText) rootView.findViewById(R.id.lengthMax);
		string = (EditText) rootView.findViewById(R.id.string);
		searchRadius = (EditText) rootView.findViewById(R.id.searchRadius);

		if (savedInstanceState != null) {
			openClosed.setSelection((int) savedInstanceState
					.getLong("open_closed"));
			lengthMin.setText(String.valueOf(savedInstanceState
					.getDouble("length_min")));
			lengthMax.setText(String.valueOf(savedInstanceState
					.getDouble("length_max")));
			durationMin.setText(String.valueOf(savedInstanceState
					.getDouble("duration_min")));
			durationMax.setText(String.valueOf(savedInstanceState
					.getDouble("duration_max")));
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

	private ArrayList<Track> filterResults() {
		ArrayList<Track> results = Tracks.List;

		results = filterTrackLength(results);
		results = filterOpenClosedTracks(results);
		results = filterCountys(results);
		results = filterTrackTypes(results);
		results = filterStringSearch(results);

		return results;
	}

	private ArrayList<Track> filterTrackLength(ArrayList<Track> results) {
		ArrayList<Track> temp = new ArrayList<Track>();

		String minimumLength = lengthMin.getText().toString();
		String maximumLength = lengthMax.getText().toString();

		if (!maximumLength.equals("") && !minimumLength.equals("")) {
			Double minLength = Double.valueOf(minimumLength);
			Double maxLength = Double.valueOf(maximumLength);
			for (Track t : results) {
				if (t.getLength() >= minLength && t.getLength() <= maxLength) {
					temp.add(t);
				}
			}
		} else if (maximumLength.equals("") && !minimumLength.equals("")) {
			Double minLength = Double.valueOf(minimumLength);
			for (Track t : results) {
				if (t.getLength() >= minLength) {
					temp.add(t);
				}
			}
		} else if (!maximumLength.equals("") && minimumLength.equals("")) {
			Double maxLength = Double.valueOf(maximumLength);
			for (Track t : results) {
				if (t.getLength() <= maxLength) {
					temp.add(t);
				}
			}
		} else {
			temp = results;
		}

		return temp;
	}

	private ArrayList<Track> filterStringSearch(ArrayList<Track> results) {
		ArrayList<Track> temp = new ArrayList<Track>();

		String sone = string.getText().toString().toLowerCase(Locale.ENGLISH);

		if (!sone.equals("")) {
			for (Track t : results) {
				if (t.getName().toLowerCase(Locale.ENGLISH).contains(sone)
						|| t.getDescription().toLowerCase(Locale.ENGLISH)
								.contains(sone)) {
					temp.add(t);
				}
			}
		} else {
			temp = results;
		}

		return temp;
	}

	private ArrayList<Track> filterTrackTypes(ArrayList<Track> results) {
		ArrayList<Track> temp = new ArrayList<Track>();

		if (type.getSelectedItemId() != 0) {
			String[] types = res.getStringArray(R.array.track_type_array);
			for (Track t : results) {
				if (t.getType().equals(types[(int) type.getSelectedItemId()])) {
					temp.add(t);
				}
			}
		} else {
			temp = results;
		}

		return temp;
	}

	private ArrayList<Track> filterCountys(ArrayList<Track> results) {
		ArrayList<Track> temp = new ArrayList<Track>();

		if (area.getSelectedItemId() != 0) {
			String[] countys = res.getStringArray(R.array.county_array);
			for (Track t : results) {
				if (t.getCounty().equals(
						countys[(int) area.getSelectedItemId()])) {
					temp.add(t);
				}
			}
		} else {
			temp = results;
		}

		return temp;
	}

	private ArrayList<Track> filterOpenClosedTracks(ArrayList<Track> results) {
		ArrayList<Track> temp = new ArrayList<Track>();

		if (openClosed.getSelectedItemId() == 1) {
			for (Track t : results) {
				if (!t.isOpen) {
					temp.add(t);
				}
			}
		} else if (openClosed.getSelectedItemId() == 2) {
			for (Track t : results) {
				if (t.isOpen) {
					temp.add(t);
				}
			}
		} else {
			temp = results;
		}

		return temp;
	}

	void searchAll() {

		ArrayList<Track> results = filterResults();

		if (results.isEmpty()) {
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.no_search_results), Toast.LENGTH_SHORT)
					.show();
		} else {
			Intent i = new Intent(getActivity().getApplicationContext(),
					TracksSearchResultsActivity.class);
			if(location != null) {
				Toast.makeText(getActivity().getApplicationContext(),
						getString(R.string.using_user_location), Toast.LENGTH_SHORT)
						.show();
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				
				if(results.isEmpty()) {
					Toast.makeText(getActivity().getApplicationContext(),
							getString(R.string.no_search_results), Toast.LENGTH_SHORT)
							.show();
				} else {
					for(Track track : results) {
						double lat1 = track.getLatitude();
						double lng1 = track.getLongitude();
						double distance = distance(lat, lng, lat1, lng1);
						track.setFromUserLocation(distance);
					}
				}
			}
			i.putExtra("tracks", results);
			startActivity(i);
		}
	}

	void searchNear() {
		
		String text = searchRadius.getText().toString();
		
		double searchRadiusValue = 0;
		
		ArrayList<Track> nearResults = new ArrayList<Track>();
		
		if(!text.equals("")) {
			try{
				searchRadiusValue = Double.valueOf(searchRadius.getText().toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				Toast.makeText(getActivity().getApplicationContext(),
						getString(R.string.no_number_value), Toast.LENGTH_SHORT)
						.show();
				return;
			}
		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.no_radius_value), Toast.LENGTH_SHORT)
					.show();
		}
		
		if(location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			
			ArrayList<Track> results = filterResults();
			
			if(results.isEmpty()) {
				Toast.makeText(getActivity().getApplicationContext(),
						getString(R.string.no_search_results), Toast.LENGTH_SHORT)
						.show();
			} else {
				for(Track track : results) {
					double lat1 = track.getLatitude();
					double lng1 = track.getLongitude();
					double distance = distance(lat, lng, lat1, lng1);
					track.setFromUserLocation(distance);
					if(distance <= searchRadiusValue) {
						nearResults.add(track);
					}
				}
			}
		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.no_location_found), Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		if(nearResults.isEmpty()) {
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.no_search_results), Toast.LENGTH_SHORT)
					.show();
			return;
		} else {
			Intent i = new Intent(getActivity().getApplicationContext(),
					TracksSearchResultsActivity.class);
			i.putExtra("tracks", nearResults);
			startActivity(i);
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