package com.ee.matkarakendus.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ee.matkarakendus.R;
import com.ee.matkarakendus.networking.PicturePost;
import com.ee.matkarakendus.objects.Track;

public class PicturesFragment extends Fragment implements OnClickListener {

	private Track track;

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
		takePic = (Button) rootView.findViewById(R.id.takePic);
		savePic = (Button) rootView.findViewById(R.id.savePic);
		discardPic = (Button) rootView.findViewById(R.id.discardPic);
		pictureButtons = (LinearLayout) rootView
				.findViewById(R.id.pictureButtons);

		takePic.setOnClickListener(this);
		savePic.setOnClickListener(this);
		discardPic.setOnClickListener(this);

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
				new PicturePost().execute(takenPic);
			}

			takePic.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 2));
			pictureButtons.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 0));

			pic.setImageBitmap(null);
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