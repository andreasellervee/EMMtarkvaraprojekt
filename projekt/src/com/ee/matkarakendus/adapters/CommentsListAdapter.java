package com.ee.matkarakendus.adapters;

import java.util.ArrayList;
import com.ee.matkarakendus.R;
import com.ee.matkarakendus.objects.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommentsListAdapter extends ArrayAdapter<Comment> {
	private final Context context;
	private final ArrayList<Comment> comments;

	public CommentsListAdapter(Context context, ArrayList<Comment> comments) {
		super(context, R.layout.comment_cell, comments);

		this.context = context;
		this.comments = comments;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater li = LayoutInflater.from(context);
		ViewHolder holder;
		if (convertView == null) {
			convertView = li.inflate(R.layout.comment_cell, null);
			holder = new ViewHolder();
			holder.body = (TextView) convertView.findViewById(R.id.body);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Comment comment = comments.get(position);

		holder.body.setText(comment.getBody());

		return convertView;
	}

	private static class ViewHolder {
		TextView body;
	}
}