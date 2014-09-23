package com.tourism.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.d3bugbd.worldcupQuiz.R;
import com.worldcup.javaclass.Score;

public class SkipQusetionAnswer extends BaseAdapter {
	public LayoutInflater inflater;
	private TextView mDate, mNum, mScore, mShoe, mVenue;
	List<Score> score = new ArrayList<Score>();
	Context scontext;
	int len;
	String skipQues[];

	public SkipQusetionAnswer(Context scontext, String skipQues[]) {
		super();
		inflater = (LayoutInflater) scontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.scontext = scontext;
		this.skipQues = skipQues;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return skipQues.length;
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

		rowView = inflater.inflate(R.layout.skip_ques_set, parent, false);

		mDate = (TextView) rowView.findViewById(R.id.tvSkipQues);

		mDate.setText(skipQues[position]);
		
		mDate.setTextColor(Color.WHITE);

		return rowView;

	}

}
