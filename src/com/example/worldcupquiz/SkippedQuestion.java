package com.example.worldcupquiz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.Contacts.Data;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.d3bugbd.worldcupQuiz.R;
import com.tourism.adapters.SkipQusetionAnswer;
import com.worldcup.Utils.Constants;
import com.worldcup.Utils.Database;
import com.worldcup.Utils.DetectConnection;
import com.worldcup.Utils.JSONParserWithParameter;
import com.worldcup.javaclass.Answer;
import com.worldcup.javaclass.Quiz;
import com.worldcup.javaclass.Skip;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SkippedQuestion extends Activity {

	ListView lvSkip;
	String skipQues[];
	List<Skip> skip = new ArrayList<Skip>();
	List<Quiz> quiz = new ArrayList<Quiz>();
	List<Answer> ans = new ArrayList<Answer>();
	Button submit;
	int pos;
	String possibleEmail = "mit", possiblePhoneNumber = "123";
	TelephonyManager tm;
	ProgressDialog Progress;
	SharedPreferences pref;
	String phone;
	TextView t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_answer);

		skip = Constants.skip;
		ans = Constants.ans;
		quiz = Constants.quiz;

		Log.e("T", skip.size() + "");

		lvSkip = (ListView) findViewById(R.id.lvSkip);
		submit = (Button) findViewById(R.id.btnSubmitAll);
		t = (TextView) findViewById(R.id.textV1);

		skipQues = new String[skip.size()];

		Progress = new ProgressDialog(this);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		for (int i = 0; i < skip.size(); i++) {
			pos = Integer.parseInt(skip.get(i).getSkipQuesid());

			for (int j = 0; j < quiz.size(); j++) {
				if (skip.get(i).getSkipQuesid()
						.contentEquals(quiz.get(j).getQuesid())) {
					skipQues[i] = quiz.get(j).getQues();
					Log.e("T m", quiz.get(j).getQues());
				}
			}

		}

		for (int i = 0; i < ans.size(); i++) {
			for (int j = 0; j < quiz.size(); j++) {
				if (ans.get(i).getQustionId()
						.contentEquals(quiz.get(j).getQuesid())) {

					Log.e("T m 1", quiz.get(j).getQues());
				}
			}
		}

		for (int i = 0; i < quiz.size(); i++) {
			for (int j = 0; j < ans.size(); j++) {
				if (quiz.get(i).getQuesid()
						.contentEquals(quiz.get(j).getQuesid())) {

					Log.e("T m 1", quiz.get(j).getQues());
				}
			}
		}

		/*
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, skipQues);
		 */
		lvSkip.setAdapter(new SkipQusetionAnswer(this, skipQues));

		if (skipQues.length <= 0) {
			t.setText("There is no skipped questions. Press the Submit button to submit your answers.");
		}

		lvSkip.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.e("TP", skip.get(arg2).getSkipQuesid());
				Constants.position_rmv = arg2;
				Constants.position = skip.get(arg2).getSkipQuesid();
				Intent in = new Intent(SkippedQuestion.this,
						SkippedAnswer.class);
				startActivity(in);

			}
		});

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SubmitInstantQuestion sub = new SubmitInstantQuestion();
				sub.execute();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		skip = Constants.skip;
		ans = Constants.ans;
		quiz = Constants.quiz;
		try {
			skipQues = new String[skip.size()];
			for (int i = 0; i < skip.size(); i++) {
				pos = Integer.parseInt(skip.get(i).getSkipQuesid());

				for (int j = 0; j < quiz.size(); j++) {
					if (skip.get(i).getSkipQuesid()
							.contentEquals(quiz.get(j).getQuesid())) {
						skipQues[i] = quiz.get(j).getQues();
						Log.e("T m", quiz.get(j).getQues());
						Log.e("T m", quiz.get(j).getQuesid());
					}
				}

			}

			for (int i = 0; i < ans.size(); i++) {
				for (int j = 0; j < quiz.size(); j++) {
					if (ans.get(i).getQustionId()
							.contentEquals(quiz.get(j).getQuesid())) {

						Log.e("T m 1", quiz.get(j).getQues());
						Log.e("T m 1", quiz.get(j).getQuesid());
					}
				}
			}

			/*
			 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			 * android.R.layout.simple_list_item_1, skipQues);
			 * lvSkip.setAdapter(adapter);
			 */
			lvSkip.setAdapter(new SkipQusetionAnswer(this, skipQues));

			if (skipQues.length <= 0) {
				t.setText("There is no skipped questions. Press the Submit button to submit your answers.");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public class SubmitInstantQuestion extends AsyncTask<Void, String, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Toast toast = Toast.makeText(SkippedQuestion.this, "Submitted...",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();

			Database dt = new Database(SkippedQuestion.this);
			dt.open();
			dt.addTrackId(Constants.matchid);
			dt.close();

			Progress.cancel();
			finish();
		}

		@Override
		protected void onPreExecute() { // TODO Auto-generated method stub
			super.onPreExecute();

			Progress.setMessage("Submitting...");
			Progress.setCancelable(false);
			Progress.show();
		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(Void... params) {
			try {

				// TODO Auto-generated method stub

				for (int i = 0; i < skip.size(); i++) {

					Answer an = new Answer(skip.get(i).skipQuesid, "");
					ans.add(an);
				}

				try {

					tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					possiblePhoneNumber = tm.getLine1Number();

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (DetectConnection
						.checkInternetConnection(SkippedQuestion.this)) {
					ans = Constants.ans;
					pref = PreferenceManager
							.getDefaultSharedPreferences(Constants.context);
					phone = pref.getString("user_phone", null);

					JSONArray answer = new JSONArray();
					for (int i = 0; i < ans.size(); i++) {
						Log.e("Id", ans.get(i).getQustionId());
						Log.e("Answer", ans.get(i).getQustionAnswer());

						JSONObject ansList = new JSONObject();
						try {
							ansList.put("ans", ans.get(i).getQustionAnswer());
							ansList.put("ques_id", ans.get(i).getQustionId());
							Log.d("M", Constants.matchid);
							ansList.put("match_id", Constants.matchid);
							ansList.put("phone_number", phone);

							answer.put(ansList);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					JSONObject finalAnswer = new JSONObject();
					try {
						finalAnswer.put("answer", answer);
						Log.d("Ans", finalAnswer.toString());
						String str = finalAnswer.toString();
						JSONParserWithParameter js = new JSONParserWithParameter();
						String ret = js.getJSONFromUrlReturnString(
								"http://wcfifa.d3bug.com/quiz_ans.php", str,
								"answer");

						System.out.println(ret);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Constants.trk_submit_class = 1;
					skip.clear();
					ans.clear();
					quiz.clear();

				} else {
					Toast toast = Toast.makeText(SkippedQuestion.this,
							"No internet connection!", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(this, "Please Submit Answer!!",
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
