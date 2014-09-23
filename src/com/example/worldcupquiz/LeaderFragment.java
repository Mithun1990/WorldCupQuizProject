package com.example.worldcupquiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.d3bugbd.worldcupQuiz.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.tourism.adapters.ScoreAdapter;
import com.tourism.adapters.TopScorerAdapter;
import com.worldcup.Utils.ConnectionDetector;
import com.worldcup.Utils.Constants;
import com.worldcup.Utils.Database;
import com.worldcup.Utils.DetectConnection;
import com.worldcup.Utils.JSONParser;
import com.worldcup.Utils.JSONParserWithParameter;
import com.worldcup.javaclass.Answer;
import com.worldcup.javaclass.Score;
import com.worldcup.javaclass.TopScore;

public class LeaderFragment extends Fragment {
	static ProgressDialog Progress;
	String possibleEmail, possiblePhoneNumber;
	TelephonyManager tm;
	String ret;
	RadioGroup rgOp;
	RadioButton rbOp1, rbOp2, rbOp3, rbOp4, rbOp5, rbOp6;
	Button bSub, bSkip;
	TextView tvTotal;
	SharedPreferences pref;
	ConnectionDetector cn;
	int totalRes;
	int length;

	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	String date = " ", num_ques, score;
	String ti = " ";
	ListView lv, lvTop;
	LinearLayout ly;
	List<Score> scoreList = new ArrayList<Score>();
	List<Answer> ans = new ArrayList<Answer>();
	int trackInQuiz;
	String quesid;
	static Context context;
	int firstInstall;
	String phone;

	List<TopScore> scoreTopList = new ArrayList<TopScore>();

	public LeaderFragment() {
		// TODO Auto-generated constructor stub
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = View.inflate(getActivity(), R.layout.leader_main, null);

		lv = (ListView) v.findViewById(R.id.lvLeader);
		lvTop = (ListView) v.findViewById(R.id.lvLeaderTop);

		tvTotal = (TextView) v.findViewById(R.id.tvTotal);
		context = this.getActivity();
		cn = new ConnectionDetector(getActivity());

		// lv.setDivider(getResources().getDrawable(R.drawable.blk_divide));

		AdView adView = new AdView(getActivity(), AdSize.BANNER, getResources()
				.getString(R.string.add_id));

		RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.ad);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);

		return v;
	}

	public class GetLeaderBoard extends AsyncTask<Void, String, String> {

		@Override
		protected void onPreExecute() { // TODO Auto-generated method stub
			super.onPreExecute();

			Progress.setMessage("Fetching Score Board...");
			Progress.setCancelable(false);
			Progress.show();
		}

		@Override
		protected String doInBackground(Void... params) {

			try {

				Log.e("Print", "P1");

				try {

					tm = (TelephonyManager) Constants.context
							.getSystemService(Constants.context.TELEPHONY_SERVICE);
					possiblePhoneNumber = tm.getLine1Number();

				} catch (Exception e) {
					e.printStackTrace();
				}
			//	Log.e("Tag Phone", possiblePhoneNumber);

				pref = PreferenceManager
						.getDefaultSharedPreferences(Constants.context);
				phone = pref.getString("user_phone", null);

				JSONObject data = new JSONObject();
				data.put("phone_number", phone);

				String str = data.toString();
				Log.e("String", str);

				JSONParserWithParameter js = new JSONParserWithParameter();

				ret = js.getJSONFromUrlReturnString(
						"http://wcfifa.d3bug.com/point.php", str,
						"phone_number");

				JSONObject jsonObjMain = new JSONObject(ret);

				if (jsonObjMain != null) {

					JSONArray jsonArray = jsonObjMain.getJSONArray("score");

					scoreList.clear();
					// JSONArray array = new JSONArray(rest);
					length = jsonArray.length();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject n = jsonArray.getJSONObject(i);

						String matchid = n.getString("match_id");
						String ques = n.getString("no_of_ques");
						String op1 = n.getString("point");

						Database db = new Database(Constants.context);
						db.open();
						String da = db.getMatch(matchid);
						db.close();

						Score sv = new Score(da, ques, op1);
						scoreList.add(sv);

						/*
						 * * Quiz qz1 = new Quiz("1", "VBB1", "11", "12", "13",
						 * "14", "15", "16"); quiz.add(qz1); Quiz qz2 = new
						 * Quiz("2", "VBB2", "21", "22", "23", "24", "25",
						 * "26"); quiz.add(qz2); Quiz qz3 = new Quiz("5",
						 * "Who is the first champion of fifa world cup?", "31",
						 * "32", "33", "34", "35", "36"); quiz.add(qz3); Quiz
						 * qz4 = new Quiz("7", "VBB4", "41", "42", "43", "44",
						 * "45", "46"); quiz.add(qz4); Quiz qz5 = new Quiz("9",
						 * "VBB5", "51", "52", "53", "54", "55", "56");
						 * quiz.add(qz5);
						 * 
						 * Log.e("R", "R");
						 */

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < scoreList.size(); i++) {
				totalRes = totalRes
						+ Integer.parseInt(scoreList.get(i).getScore());
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// mProgress.dismiss();

			try {

				if (scoreList.size() > 0) {
					Log.e("L", "L");
					Constants.scoreList = scoreList;
					tvTotal.setText(totalRes + "");

					lv.setAdapter(new ScoreAdapter(Constants.context, scoreList));
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
			
			Progress.cancel();

			/*GetTopLeader gtTop = new GetTopLeader();
			gtTop.execute();*/

		}
	}

	public class GetTopLeader extends AsyncTask<Void, String, String> {

		@Override
		protected void onPreExecute() { // TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(Void... params) {

			try {
				
				JSONObject jb;
				Log.e("Print", "P1");

				try {

					tm = (TelephonyManager) Constants.context
							.getSystemService(Constants.context.TELEPHONY_SERVICE);
					possiblePhoneNumber = tm.getLine1Number();

				} catch (Exception e) {
					e.printStackTrace();
				}
			//	Log.e("Tag Phone", possiblePhoneNumber);
				JSONObject data = new JSONObject();
				data.put("phone_number", possiblePhoneNumber);

				String str = data.toString();
			//	Log.e("String", possiblePhoneNumber);

				JSONParser js = new JSONParser();

				jb = js.getJSONFromUrl(
						"http://wcfifa.d3bug.com/top_scorer.php");

			

				if (jb != null) {

					JSONArray jsonArray = jb
							.getJSONArray("top_scorer");

					scoreTopList.clear();
					// JSONArray array = new JSONArray(rest);
					length = jsonArray.length();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject n = jsonArray.getJSONObject(i);

						String matchid = n.getString("scorer_name");
						String ques = n.getString("id");
						String op1 = n.getString("total_point");

						Database db = new Database(Constants.context);
						db.open();
						String da = db.getMatch(ques);
						db.close();

						TopScore sv = new TopScore(da, matchid, op1);
						scoreTopList.add(sv);

						/*
						 * * Quiz qz1 = new Quiz("1", "VBB1", "11", "12", "13",
						 * "14", "15", "16"); quiz.add(qz1); Quiz qz2 = new
						 * Quiz("2", "VBB2", "21", "22", "23", "24", "25",
						 * "26"); quiz.add(qz2); Quiz qz3 = new Quiz("5",
						 * "Who is the first champion of fifa world cup?", "31",
						 * "32", "33", "34", "35", "36"); quiz.add(qz3); Quiz
						 * qz4 = new Quiz("7", "VBB4", "41", "42", "43", "44",
						 * "45", "46"); quiz.add(qz4); Quiz qz5 = new Quiz("9",
						 * "VBB5", "51", "52", "53", "54", "55", "56");
						 * quiz.add(qz5);
						 * 
						 * Log.e("R", "R");
						 */

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// mProgress.dismiss();

			try {

				if (scoreTopList.size() > 0) {
					Log.e("L", "L");
					Constants.scoreTopList = scoreTopList;

					lvTop.setAdapter(new TopScorerAdapter(Constants.context,
							scoreTopList));
				}

				Progress.cancel();
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		if (isVisibleToUser) {
			pref = PreferenceManager
					.getDefaultSharedPreferences(Constants.context);
			Progress = new ProgressDialog(Constants.context);

			if (DetectConnection.checkInternetConnection(Constants.context)) {
				// if (Constants.trk_score != 1) {

				GetLeaderBoard ld = new GetLeaderBoard();
				ld.execute();

				/*
				 * Constants.trk_score = 1;
				 * 
				 * } else { lv.setAdapter(new ScoreAdapter(Constants.context,
				 * Constants.scoreList)); }
				 */

			} else {
				Toast toast = Toast.makeText(Constants.context,
						"No internet connection!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}

		}
	}
}
