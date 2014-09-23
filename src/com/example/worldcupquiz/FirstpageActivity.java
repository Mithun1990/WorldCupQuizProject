package com.example.worldcupquiz;

import com.d3bugbd.worldcupQuiz.R;
import com.tourism.menudrawer.MainActivity;
import com.worldcup.Utils.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class FirstpageActivity extends Activity {
	SharedPreferences pref;
	EditText in, in1;
	TextView ft;

	int first = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_page);

		in = (EditText) findViewById(R.id.editText1);
		in1 = (EditText) findViewById(R.id.editText2);
		ft = (TextView) findViewById(R.id.textView1);

		pref = PreferenceManager.getDefaultSharedPreferences(this);
		/* Progress = new ProgressDialog(getActivity()); */

		first = pref.getInt("first", 0);

		if (first == 0) {

		} else {
			Intent in = new Intent(this, MainActivity.class);
			startActivity(in);
			finish();
		}

		ft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = in.getText().toString();
				String number = in1.getText().toString();

				if (name.isEmpty() || number.isEmpty()) {
					Toast ts = Toast
							.makeText(
									FirstpageActivity.this,
									"Please Enter Your Phone Number For Quiz Reward As Flexiload!",
									Toast.LENGTH_LONG);
					ts.setGravity(Gravity.CENTER, 0, 0);
					ts.show();

				} else {
					
					int len = number.length();
					
				Log.d("Nam",len+"");
					
					if(len==11){
						Constants.user_name = name;

						Editor ed = pref.edit();
						ed.putInt("first", 1);
						ed.putString("user_name", name);
						ed.putString("user_phone", number);
						Intent in = new Intent(FirstpageActivity.this,
								MainActivity.class);
						startActivity(in);

						ed.commit();

						finish();
					}else{
						Toast ts = Toast
								.makeText(
										FirstpageActivity.this,
										"Invalid Number Format Please Follow The Format As 01XXXXXXXXX",
										Toast.LENGTH_LONG);
						ts.setGravity(Gravity.CENTER, 0, 0);
						ts.show();
					}
					
					
				}
			}
		});

	}

}
