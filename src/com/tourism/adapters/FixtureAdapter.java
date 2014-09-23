package com.tourism.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d3bugbd.worldcupQuiz.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.worldcup.javaclass.Fixture;

public class FixtureAdapter extends BaseAdapter {
	public LayoutInflater inflater;
	private TextView mDate,  mTime, mVenue;
	ImageView mTeam1, mTeam2;
	TextView m1, m2;
	List<Fixture> fixture = new ArrayList<Fixture>();
	Context scontext;
	int len;
	DisplayImageOptions options;

	ImageLoader imageLoader;

	public FixtureAdapter(Context scontext, List<Fixture> fixture) {
		super();
		inflater = (LayoutInflater) scontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.scontext = scontext;
		this.fixture = fixture;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(scontext));
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fixture.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View j, ViewGroup parent) {
		// TODO Auto-generated method stub

		View rowView;

		rowView = inflater.inflate(R.layout.fixture_adapter, parent, false);

		mDate = (TextView) rowView.findViewById(R.id.tvDate);
		mTeam1 = (ImageView) rowView.findViewById(R.id.tvTeam1);
		mTeam2 = (ImageView) rowView.findViewById(R.id.tvTeam2);
		mTime = (TextView) rowView.findViewById(R.id.tvTime);
		mVenue = (TextView) rowView.findViewById(R.id.tvVenue);
		m1 = (TextView) rowView.findViewById(R.id.tvTeam1T1);
		m2 = (TextView) rowView.findViewById(R.id.tvTeam2T2);
		Log.e("G", fixture.get(position).getmTeam1());

		mDate.setText(fixture.get(position).getmDate() + "   "
				+ fixture.get(position).getmTime());
		m1.setText(fixture.get(position).getmTeam1());
		m2.setText(fixture.get(position).getmTeam2());
		
		
		imageLoader.displayImage("assets://flag/" + fixture.get(position).getmTeam1().toLowerCase()
				+ ".png", mTeam1, options, null);
		
		
		imageLoader.displayImage("assets://flag/" + fixture.get(position).getmTeam2().toLowerCase()
				+ ".png", mTeam2, options, null);

		Log.d("Img", "assets://" + fixture.get(position).getmTeam1() + ".jpg");

		if (fixture.get(position).getmRound().contentEquals("finals")) {
			if (position <= 4) {
				mTime.setText("Quarterfinals");
			} else if (position < 6 && position <= 5) {
				mTime.setText("Semifinals");
			} else {
				mTime.setText("Final");
			}

		} else {
			mTime.setText(fixture.get(position).getmRound());
		}

		mVenue.setText(fixture.get(position).getmVenue());

		/*
		 * mtextHotelName.setTextColor(Color.RED);
		 * mtextHotelAdd.setTextColor(Color.MAGENTA);
		 * mtextHotelPhone.setTextColor(Color.BLUE);
		 * mtextHotelEml.setTextColor(Color.BLUE);
		 */

		return rowView;

	}

}
