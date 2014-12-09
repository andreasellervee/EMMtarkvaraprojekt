package com.ee.matkarakendus.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.activities.ImageViewerActivity;
import com.ee.matkarakendus.adapters.TrackPicturesGridAdapter;
import com.ee.matkarakendus.networking.PicturePost;
import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.utils.TrackImageUrlUtil;

public class PicturesFragment extends Fragment implements OnClickListener {

	private ArrayList<String> imageUrls;

	private Track track;

	private GridView grid;
	private ImageView pic;
	private Button takePic, savePic, discardPic;
	private LinearLayout pictureButtons;

	private Bitmap takenPic;

	public PicturesFragment(Track track) {
		this.track = track;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_pictures, container,
				false);


		pic = (ImageView) rootView.findViewById(R.id.pic);
		grid = (GridView) rootView.findViewById(R.id.grid);
		takePic = (Button) rootView.findViewById(R.id.takePic);
		savePic = (Button) rootView.findViewById(R.id.savePic);
		discardPic = (Button) rootView.findViewById(R.id.discardPic);
		pictureButtons = (LinearLayout) rootView
				.findViewById(R.id.pictureButtons);

		imageUrls = new TrackImageUrlUtil().getPictureUrlsById(track.id);
		grid.setAdapter(new TrackPicturesGridAdapter(getActivity()
				.getApplicationContext(), imageUrls));
		
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = (String) grid.getAdapter().getItem(position);
				Log.i("URL", url);
				if(url != null) {
					Intent i = new Intent(getActivity(), ImageViewerActivity.class);
					i.putExtra("bitmapImageUrl", url);
					i.putExtra("title", track.getName());
					startActivity(i);
				} else {
					Toast.makeText(getActivity(), "Pilti ei leitud", Toast.LENGTH_SHORT)
					.show();
				}
			}
		});

		takePic.setOnClickListener(this);
		savePic.setOnClickListener(this);
		discardPic.setOnClickListener(this);
		Log.e("", String.valueOf(imageUrls.size()));

		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(takePic)) {
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

			startActivityForResult(cameraIntent, 1337);
		} else {
			if (v.equals(savePic)) {
				new PicturePost(track.getId()).execute(takenPic);
			}

			takePic.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 2));
			pictureButtons.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 0));

			pic.setImageBitmap(null);
			
			//comment out if you want saved pictures to appear in the grid view (takes about 5-6 seconds)
//			imageUrls = new TrackImageUrlUtil().getPictureUrlsById(track.id);
//			grid.setAdapter(new TrackPicturesGridAdapter(getActivity()
//					.getApplicationContext(), imageUrls));
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1337 && data != null) {
			takenPic = (Bitmap) data.getExtras().get("data");
			pic.setImageBitmap(takenPic);

			takePic.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 0));
			pictureButtons.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 2));
		} else {
			Toast.makeText(getActivity(), "Pilti ei leitud", Toast.LENGTH_SHORT)
					.show();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}