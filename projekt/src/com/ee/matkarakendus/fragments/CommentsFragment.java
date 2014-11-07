package com.ee.matkarakendus.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.adapters.CommentsListAdapter;
import com.ee.matkarakendus.objects.Comment;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.objects.Tracks;

public class CommentsFragment extends Fragment implements OnClickListener {

	private Track track;

	private CommentsListAdapter adapter;

	private Button commentButton;
	private EditText commentField;
	private TextView noComments;
	private ListView commentsList;

	public CommentsFragment() {
	}

	public CommentsFragment(Track track) {
		this.track = track;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_comments, container,
				false);

		commentButton = (Button) rootView.findViewById(R.id.addCommentButton);
		commentField = (EditText) rootView.findViewById(R.id.addCommentField);
		commentsList = (ListView) rootView.findViewById(R.id.commentsList);
		noComments = (TextView) rootView.findViewById(R.id.noComments);

		adapter = new CommentsListAdapter(
				getActivity().getApplicationContext(), track.getComments());

		commentsList.setAdapter(adapter);
		
		if (track.getComments().size() > 0) {
			noComments.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, 0, 0));
		} else {
			commentsList.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, 0, 0));
		}

		rootView.invalidate();

		commentButton.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		String input = commentField.getText().toString().trim();

		if (input.length() > 0) {
			Comment comment = new Comment();
			comment.setBody(input);

			Tracks.List.get(Tracks.List.indexOf(track)).comments.add(comment);
			track.comments.add(comment);

			adapter.notifyDataSetChanged();

			noComments.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 0));
			commentsList.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 5));

			commentsList.setSelection(adapter.getCount() - 1);
		}

		commentField.setText("");
		commentField.clearFocus();

		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(commentField.getWindowToken(), 0);
	}
}