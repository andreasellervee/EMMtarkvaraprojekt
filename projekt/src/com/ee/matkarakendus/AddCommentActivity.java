package com.ee.matkarakendus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ee.matkarakendus.networking.CommentsPost;
import com.ee.matkarakendus.objects.Comment;

public class AddCommentActivity extends Activity implements OnClickListener {
	
	private Button commentButton;
	private EditText commentText;
	private TextView commentInfo;
	private int trackId;
	private String trackName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_comment);
		Intent i = getIntent();
		trackId = i.getIntExtra("trackId", 0);
		trackName = i.getStringExtra("trackName");
		
		commentButton = (Button) this.findViewById(R.id.addCommentButton);
		commentText = (EditText) this.findViewById(R.id.addCommentField);
		commentInfo = (TextView) this.findViewById(R.id.addCommentInfo);
		
		commentInfo.setText("Lisa kommentaar rajale: " + trackName + ".");
		
		commentButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_comment, menu);
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

	@Override
	public void onClick(View v) {
		String input = commentText.getText().toString().trim();

		if (input.length() > 0 && trackId != 0) {
			Comment comment = new Comment();
			comment.setBody(input);
			
			CommentsPost util = new CommentsPost(trackId, input);
			util.execute("");
		} else {
			Toast.makeText(getApplicationContext(), (CharSequence) "Input length is 0", Toast.LENGTH_SHORT);
		}
		
		
		finish();
	}
}
