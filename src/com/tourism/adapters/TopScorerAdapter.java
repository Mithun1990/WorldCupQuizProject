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
import com.worldcup.javaclass.TopScore;

public class TopScorerAdapter extends  BaseAdapter {
	public LayoutInflater inflater;
	private TextView mDate, mNum, mScore, mShoe, mVenue;
	List<TopScore> score = new ArrayList<TopScore>();
	Context scontext;
	int len;

	public TopScorerAdapter(Context scontext, List<TopScore> score) {
		super();
		inflater = (LayoutInflater) scontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.scontext = scontext;
		this.score = score;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return score.size();
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

		rowView = inflater.inflate(R.layout.leader_adapter, parent, false);
		
		mDate = (TextView) rowView.findViewById(R.id.tvDateScore);
		mNum = (TextView) rowView.findViewById(R.id.tvNumQues);
		mScore = (TextView) rowView.findViewById(R.id.tvScore);

	//	Log.e("G1", score.get(position).getScore());

		mDate.setText(score.get(position).getName());
		mNum.setText(score.get(position).getTime());
		mScore.setText(score.get(position).getPoint());

	/*	mDate.setTextColor(Color.BLACK);
		mNum.setTextColor(Color.BLACK);
		mScore.setTextColor(Color.BLACK);*/
		/* mtextHotelEml.setTextColor(Color.BLUE); */

		return rowView;

	}


}
