package com.ee.matkarakendus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Point;

public class PointViewActivity extends Activity {
	private Point point;
	private TextView name,location,description;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		point = (Point) getIntent().getSerializableExtra("point");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_point_view);
		
		name = (TextView) findViewById(R.id.pname);
		location = (TextView) findViewById(R.id.plocation);
		description = (TextView) findViewById(R.id.pkirjeldus);
		
		if (point != null) {
			name.setText("Huvipunkt: " + point.getName()) ;
			location.setText("Asukoht: " + point.getCountry() + "," + point.getCounty());
			description.setText(point.getDescription());
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.point_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
