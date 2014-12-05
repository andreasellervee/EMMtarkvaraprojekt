package com.ee.matkarakendus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.PointsListAdapter;
import com.ee.matkarakendus.objects.Point;
import com.ee.matkarakendus.objects.Points;

public class PointsSearchResultsActivity extends Activity implements
		OnItemClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.search_results);

		setContentView(R.layout.activity_search_results);

		ListView list = (ListView) findViewById(android.R.id.list);

		PointsListAdapter adapter = new PointsListAdapter(this, Points.List);

		list.setAdapter(adapter);

		list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Point point = (Point) parent.getItemAtPosition(position);
		Intent i = new Intent(getApplicationContext(), PointViewActivity.class);
		i.putExtra("point", point);
		startActivity(i);
	}
}