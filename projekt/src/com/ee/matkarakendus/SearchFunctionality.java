package com.ee.matkarakendus;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SearchFunctionality extends Activity implements OnClickListener,
		OnEditorActionListener, OnItemClickListener {
	ListView mListView;
	MySimpleSearchAdapter mAdapter;
	Button btnSearch;
	EditText mtxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tracks_view);
		mListView = (ListView) findViewById(R.id.tracks_view);
		mAdapter = new MySimpleSearchAdapter(this);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		mtxt = (EditText) findViewById(R.id.edSearch);
		mtxt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (0 != mtxt.getText().length()) {
					String spnId = mtxt.getText().toString();
					setSearchResult(spnId);
				} else {
					setData();
				}
			}
		});
		btnSearch.setOnClickListener(this);
		setData();
	}

	ArrayList<String> mAllData;

	String[] str = { "Hit me Hard", "GIJ, Rise Of Cobra", "Troy",
			"A walk To remember", "DDLJ", "Tom Peter Nmae", "David Miller",
			"Kings Eleven Punjab", "Kolkata Knight Rider", "Rest of Piece" };

	public void setData() {
		mAllData = new ArrayList<String>();
		mAdapter = new MySimpleSearchAdapter(this);
		for (int i = 0; i < str.length; i++) {
			mAdapter.addItem(str[i]);
			mAllData.add(str[i]);
		}
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSearch:
			mtxt.setText("");
			setData();
			break;
		}
	}

	public void setSearchResult(String str) {
		mAdapter = new MySimpleSearchAdapter(this);
		for (String temp : mAllData) {
			if (temp.toLowerCase().contains(str.toLowerCase())) {
				mAdapter.addItem(temp);
			}
		}
		mListView.setAdapter(mAdapter);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		return false;
	}

	@Override
	public void onBackPressed() {
		setResult(Activity.RESULT_CANCELED);
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		String str = mAdapter.getItem(position);
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}
}