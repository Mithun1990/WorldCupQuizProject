package com.tourism.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.d3bugbd.worldcupQuiz.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.worldcup.javaclass.Today;

public class TodayAdapter extends BaseAdapter {
	public LayoutInflater inflater;
	private TextView mYear, mWin, mLose, mShoe, mVenue;
	List<Today> tod = new ArrayList<Today>();
	Context scontext;
	int len;
	DisplayImageOptions options;

	ImageLoader imageLoader;

	public TodayAdapter(Context scontext, List<Today> tod) {
		super();
		inflater = (LayoutInflater) scontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.scontext = scontext;
		this.tod = tod;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tod.size();
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

		rowView = inflater.inflate(R.layout.today_match, parent, false);

		mYear = (TextView) rowView.findViewById(R.id.tv1);
		mWin = (TextView) rowView.findViewById(R.id.tv2);
		mLose = (TextView) rowView.findViewById(R.id.tv3);

		mYear.setText(tod.get(position).getTeam1());

		mLose.setText(tod.get(position).getTeam2());

		return rowView;

	}

}
