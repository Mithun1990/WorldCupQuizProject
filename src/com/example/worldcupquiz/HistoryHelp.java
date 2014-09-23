package com.example.worldcupquiz;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.d3bugbd.worldcupQuiz.R;
import com.worldcup.Utils.Constants;

public class HistoryHelp extends Activity {

	ImageView im;
	TextView des;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_help);

		im = (ImageView) findViewById(R.id.ivHelp);
		des = (TextView) findViewById(R.id.tvHelp);
		try {
			// get input stream
			InputStream ims = getAssets().open(
					Constants.history.get(Constants.helpPos).getmYear()
							+ ".jpg");
			// load image as Drawablese
			Drawable d = Drawable.createFromStream(ims, null);
			// set image to ImageView
			im.setImageDrawable(d);
		} catch (IOException ex) {
			return;
		}

		des.setText(Constants.history.get(Constants.helpPos).getDes());

	}

}
