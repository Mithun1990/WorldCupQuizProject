package com.tourism.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d3bugbd.worldcupQuiz.R;
import com.example.worldcupquiz.HistoryHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.worldcup.Utils.Constants;
import com.worldcup.javaclass.History;

public class HistoryAdapter extends BaseAdapter {
	public LayoutInflater inflater;
	private TextView mYear, mWin, mLose, mShoe, mVenue;
	List<History> history = new ArrayList<History>();
	Context scontext;
	int len;
	DisplayImageOptions options;

	ImageLoader imageLoader;

	public HistoryAdapter(Context scontext, List<History> history) {
		super();
		inflater = (LayoutInflater) scontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.scontext = scontext;
		this.history = history;

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
		return history.size();
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
	public View getView(final int position, View j, ViewGroup parent) {
		// TODO Auto-generated method stub

		View rowView;

		rowView = inflater.inflate(R.layout.history_adapter, parent, false);

		mYear = (TextView) rowView.findViewById(R.id.tvYear);
		mWin = (TextView) rowView.findViewById(R.id.tvNN);
		mLose = (TextView) rowView.findViewById(R.id.tvRun);

		Log.e("G1", history.get(position).getmLose());

		ImageView img = (ImageView) rowView.findViewById(R.id.ivHistory);
		imageLoader.displayImage("assets://" + history.get(position).getmYear()
				+ ".jpg", img, options, null);

		Log.d("Img", "assets://" + history.get(position).getmYear() + ".jpg");

		mYear.setText(history.get(position).getmWin() + ", Winner of " 
				+ history.get(position).getmYear());
		mWin.setText(history.get(position).getmWin());
		mLose.setText(history.get(position).getmShoe());

		mYear.setTextColor(Color.WHITE);
		// mWin.setTextColor(Color.BLUE);
		// mLose.setTextColor(Color.BLUE);
		/* mtextHotelEml.setTextColor(Color.BLUE); */

		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stubr

				Intent in = new Intent(scontext, HistoryHelp.class);
				Constants.helpPos = position;
				scontext.startActivity(in);

			}
		});

		return rowView;

	}

}
