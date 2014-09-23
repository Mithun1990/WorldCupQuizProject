package com.example.worldcupquiz;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.d3bugbd.worldcupQuiz.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.tourism.adapters.TodayAdapter;
import com.worldcup.Utils.Constants;
import com.worldcup.Utils.DataStore;
import com.worldcup.Utils.Database;
import com.worldcup.Utils.DetectConnection;
import com.worldcup.Utils.JSONParserWithParameter;
import com.worldcup.javaclass.Answer;
import com.worldcup.javaclass.AnswerTrack;
import com.worldcup.javaclass.Quiz;
import com.worldcup.javaclass.Skip;
import com.worldcup.javaclass.ToadyMatch;
import com.worldcup.javaclass.Today;

public class QuizFragment extends Fragment implements OnCheckedChangeListener {

	RadioGroup rgOp;
	Button rbOp1, rbOp2, rbOp3, rbOp4, rbOp5, rbOp6;
	TextView bNext, bSkip;
	static TextView tvQues;
	ProgressDialog Progress;
	public static int length, i = 0;
	List<Quiz> quiz = new ArrayList<Quiz>();
	List<Answer> ans = new ArrayList<Answer>();
	List<Skip> skip = new ArrayList<Skip>();
	List<Today> today = new ArrayList<Today>();
	List<AnswerTrack> track = new ArrayList<AnswerTrack>();
	List<DataStore> store = new ArrayList<DataStore>();
	List<ToadyMatch> todayMatch = new ArrayList<ToadyMatch>();
	List<String> list = new ArrayList<String>();
	LinearLayout ly;
	static LinearLayout lyAns;
	ListView lv;
	int button_Track = 0;
	static Context context;
	int select_id;
	RadioButton rd;
	String path;
	String quesid;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	int done = 0;
	String ret1;
	String matchid;
	String radioAnswer = "";
	static Spinner spinner;

	public QuizFragment() {

	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = View.inflate(getActivity(), R.layout.quiz_main, null);

		rbOp1 = (Button) v.findViewById(R.id.rbOp1);
		rbOp2 = (Button) v.findViewById(R.id.rbOp2);
		rbOp3 = (Button) v.findViewById(R.id.rbOp3);
		rbOp4 = (Button) v.findViewById(R.id.rbOp4);
		rbOp5 = (Button) v.findViewById(R.id.rbOp5);
		rbOp6 = (Button) v.findViewById(R.id.rbOp6);

		spinner = (Spinner) v.findViewById(R.id.spinnerMatch);

		bNext = (TextView) v.findViewById(R.id.btNext);
		bSkip = (TextView) v.findViewById(R.id.btSkip);
		context = this.getActivity();
		tvQues = (TextView) v.findViewById(R.id.tvQues);
		lv = (ListView) v.findViewById(R.id.lvMtch);
		ly = (LinearLayout) v.findViewById(R.id.lyQuiz);
		lyAns = (LinearLayout) v.findViewById(R.id.lyAns);

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df1 = new SimpleDateFormat("hh:mm aa");
		System.out.println(df1.format(c.getTime()));

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String formattedDate = df.format(c.getTime());

		AdView adView = new AdView(getActivity(), AdSize.BANNER, getResources()
				.getString(R.string.add_id));

		RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.ad);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
		System.out.println(formattedDate);

		todayMatch = Constants.today;

		System.out.println("Size Today" + todayMatch.size());

		/*
		 * if (formattedDate.contentEquals("12-Jun-2014")) { formattedDate =
		 * "13-Jun-2014"; }
		 * 
		 * Database dt = new Database(Constants.context); dt.open();
		 * List<String> list = new ArrayList<String>(); list.clear();
		 * 
		 * for (int j = 0; j < todayMatch.size(); j++) { today =
		 * dt.getTodayFixtureDataTodayMatch(todayMatch.get(j).getId());
		 * 
		 * list.add(today.get(i).getTeam1() + "  VS  " +
		 * today.get(i).getTeam2()); }
		 * 
		 * dt.close(); System.out.println("Size" + today.size());
		 */
		context = this.getActivity();

		done = 0;

		/*
		 * List<String> list = new ArrayList<String>();
		 * 
		 * // list.add("Select Match"); for (int i = 0; i < today.size(); i++) {
		 * list.add(today.get(i).getTeam1() + "  VS  " +
		 * today.get(i).getTeam2()); }
		 */

		/*
		 * if (today.size() <= 0) { list.add("Brazil  Vs  Croatia"); }
		 */

		/*
		 * ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
		 * android.R.layout.simple_spinner_dropdown_item, list); dataAdapter
		 * .setDropDownViewResource
		 * (android.R.layout.simple_spinner_dropdown_item);
		 * spinner.setAdapter(dataAdapter);
		 */

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				if (done == 1) {

					// Log.e("MId", today.get(arg2-1).getMatchid());
					if (arg0.getItemAtPosition(arg2).toString()
							.contentEquals("Select Match")) {
						matchid = "0";

						GetQuizQuestion qz = new GetQuizQuestion();
						qz.execute();

					} else {

						// Log.e("MId", today.get(arg2).getMatchid());
						try {
							Constants.matchid = todayMatch.get(arg2).getId();
							matchid = todayMatch.get(arg2).getId();
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (matchid == null) {
							lyAns.setVisibility(View.INVISIBLE);
							tvQues.setText("No Questions available for now. Questions will be available at least 1 hour prior to the start of the match.");
						} else {
							quiz.clear();
							ans.clear();
							skip.clear();

							Database d = new Database(Constants.context);
							d.open();
							String[] ne = d.ViewTrackId();
							d.close();
							int t = 0;

							for (int k = 0; k < ne.length; k++) {

								if (matchid.contentEquals(ne[k])) {
									t = 1;
									break;
								}

							}

							if (t == 0) {
								GetQuizQuestion qz = new GetQuizQuestion();
								qz.execute();
							} else {

								lyAns.setVisibility(View.INVISIBLE);
								tvQues.setText("You are already answer this questions set!!");
								Toast toast = Toast
										.makeText(
												Constants.context,
												"You are already answer this questions set!!",
												Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();

							}

						}
					}
					done = 1;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		lv.setAdapter(new TodayAdapter(context, today));

		lv.setDivider(getResources().getDrawable(R.drawable.blk_divide));

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.e("MId", today.get(arg2).getMatchid());
				Constants.matchid = today.get(arg2).getMatchid();
				matchid = today.get(arg2).getMatchid();
				quiz.clear();
				ans.clear();
				skip.clear();

				GetQuizQuestion qz = new GetQuizQuestion();
				qz.execute();

			}
		});

		rbOp1.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp2.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp3.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp4.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp5.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp6.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));

		rbOp1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbOp1.setBackgroundColor(getResources().getColor(
						R.color.button_select));
				rbOp2.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp3.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp4.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp5.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp6.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				button_Track = 1;

				radioAnswer = rbOp1.getText().toString();
			}
		});

		rbOp2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbOp1.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp2.setBackgroundColor(getResources().getColor(
						R.color.button_select));
				rbOp3.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp4.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp5.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp6.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				button_Track = 2;
				radioAnswer = rbOp2.getText().toString();
			}
		});

		rbOp3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbOp1.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp2.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp3.setBackgroundColor(getResources().getColor(
						R.color.button_select));
				rbOp4.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp5.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp6.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				button_Track = 3;
				radioAnswer = rbOp3.getText().toString();
			}
		});

		rbOp4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbOp1.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp2.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp3.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp4.setBackgroundColor(getResources().getColor(
						R.color.button_select));
				rbOp5.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp6.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				button_Track = 4;
				radioAnswer = rbOp4.getText().toString();
			}
		});

		rbOp5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbOp1.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp2.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp3.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp4.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp5.setBackgroundColor(getResources().getColor(
						R.color.button_select));
				rbOp6.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				button_Track = 5;
				radioAnswer = rbOp5.getText().toString();
			}
		});

		rbOp6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbOp1.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp2.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp3.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp4.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp5.setBackgroundColor(getResources().getColor(
						R.color.button_not_select));
				rbOp6.setBackgroundColor(getResources().getColor(
						R.color.button_select));
				button_Track = 6;
				radioAnswer = rbOp6.getText().toString();
			}
		});

		bNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("T", Constants.quesWhichAnswed + "");
				Answer an = null;
				/*
				 * select_id = rgOp.getCheckedRadioButtonId();
				 * 
				 * rd = (RadioButton) getActivity().findViewById(select_id);
				 */

				/*
				 * int id = rgOp.getCheckedRadioButtonId(); View radioButton =
				 * rgOp.findViewById(id); int radioId =
				 * rgOp.indexOfChild(radioButton); RadioButton btn =
				 * (RadioButton) rgOp.getChildAt(radioId);
				 */
				an = new Answer(
						quiz.get(Constants.quesWhichAnswed).getQuesid(),
						radioAnswer);

				DataStore dt = new DataStore(matchid, quiz.get(
						Constants.quesWhichAnswed).getQuesid(), button_Track
						+ "", null);

				store.add(dt);

				/*
				 * Database dt = new Database(context); dt.open();
				 * dt.addAnswerData(matchid, quiz.get(Constants.quesWhichAnswed)
				 * .getQuesid(), button_Track + "", null); dt.close();
				 */

				/*
				 * btn .getText().toString()
				 */
				Log.e("NN", radioAnswer);

				ans.add(an);

				int gt = ++Constants.quesWhichAnswed;
				if (Constants.quesWhichAnswed <= (length - 1)) {
					Log.e("T", "H");

					quesid = quiz.get(gt).getQuesid();

					emptyAll();

					tvQues.setText(quiz.get(gt).getQues());
					rbOp1.setText(quiz.get(gt).getOp1());
					rbOp2.setText(quiz.get(gt).getOp2());
					rbOp3.setText(quiz.get(gt).getOp3());
					rbOp4.setText(quiz.get(gt).getOp4());
					rbOp5.setText(quiz.get(gt).getOp5());
					rbOp6.setText(quiz.get(gt).getOp6());

				} else {

					submitAnswer();

					for (int i = 0; i < ans.size(); i++) {
						Log.e("YY", ans.get(i).getQustionId());
					}

				}

			}

		});

		bSkip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("T", Constants.quesWhichAnswed + "");
				Skip k = new Skip(quiz.get(Constants.quesWhichAnswed)
						.getQuesid() + "");
				skip.add(k);
				/*
				 * DataStore dt = new DataStore(matchid, quiz.get(
				 * Constants.quesWhichAnswed).getQuesid(), button_Track + "",
				 * null);
				 * 
				 * store.add(dt);
				 */

				int gt = ++Constants.quesWhichAnswed;

				if (Constants.quesWhichAnswed <= (length - 1)) {
					Log.e("T", "H");
					quesid = quiz.get(gt).getQuesid();

					emptyAll();

					tvQues.setText(quiz.get(gt).getQues());
					rbOp1.setText(quiz.get(gt).getOp1());
					rbOp2.setText(quiz.get(gt).getOp2());
					rbOp3.setText(quiz.get(gt).getOp3());
					rbOp4.setText(quiz.get(gt).getOp4());
					rbOp5.setText(quiz.get(gt).getOp5());
					rbOp6.setText(quiz.get(gt).getOp6());

				} else {

					submitAnswer();
					for (int i = 0; i < skip.size(); i++) {
						Log.e("YY Skip", skip.get(i).skipQuesid);
					}

				}

			}
		});

		return v;
	}

	private void submitAnswer() {
		// TODO Auto-generated method stub

		Constants.skip = skip;
		Constants.ans = ans;
		Constants.quiz = quiz;
		// Constants.today=todayMatch;
		list.clear();

		Database dt = new Database(context);
		dt.open();
		dt.deleteMatch(matchid);

		for (int i = 0; i < store.size(); i++) {
			dt.addAnswerData(store.get(i).getMatch_id(), store.get(i)
					.getQues_id(), store.get(i).getQues(), store.get(i)
					.getExtra());
		}

		dt.close();

		Intent in = new Intent(getActivity(), SkippedQuestion.class);
		getActivity().startActivityForResult(in, 1);
	}

	public class GetQuizQuestion extends AsyncTask<Void, String, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			length = quiz.size();
			Constants.quiz = quiz;

			Log.e("R", "R1");
			Progress.cancel();
			if (length > 0) {

				lyAns.setVisibility(View.VISIBLE);
				// ly.setVisibility(View.VISIBLE);
				tvQues.setText(quiz.get(0).getQues());
				rbOp1.setText(quiz.get(0).getOp1());
				rbOp2.setText(quiz.get(0).getOp2());
				rbOp3.setText(quiz.get(0).getOp3());
				rbOp4.setText(quiz.get(0).getOp4());
				rbOp5.setText(quiz.get(0).getOp5());
				rbOp6.setText(quiz.get(0).getOp6());

				quesid = quiz.get(0).getQuesid();

				track.clear();
				store.clear();

				Database dt = new Database(Constants.context);
				dt.open();
				track = dt.getAnswerData(matchid);
				dt.close();

				emptyAll();

				Log.d("Track_id", track.size() + "");

				Log.e("R", "R2");

				Constants.quesWhichAnswed = 0;
			} else {
				lyAns.setVisibility(View.INVISIBLE);
				tvQues.setText("No Questions available for now. Questions will be available at least 1 hour prior to the start of the match.");
				Toast toast = Toast
						.makeText(
								Constants.context,
								"No Questions available for now. Questions will be available at least 1 hour prior to the start of the match.",
								Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Progress = new ProgressDialog(Constants.context);
			Progress.setMessage("Loading Quiz Question...");
			Progress.setCancelable(true);
			Progress.show();

		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(Void... params) {

			try {

				/*
				 * String jsonString = null; try { jsonString =
				 * AssetJSONFile(path, Constants.context); } catch (IOException
				 * e) { // TODO Auto-generated catch block e.printStackTrace();
				 * }
				 * 
				 * Log.d("json", jsonString);
				 * 
				 * JSONObject jsonObjMain = new JSONObject(jsonString);
				 * 
				 * // Creating JSONArray from JSONObject JSONArray jsonArray =
				 * jsonObjMain.getJSONArray("quiz");
				 */
				/*
				 * try { JSONObject data = new JSONObject(); data.put("q_set",
				 * "1");
				 * 
				 * 
				 * String str = data.toString();
				 * 
				 * HttpClient httpclient = new DefaultHttpClient(); HttpPost
				 * request = new HttpPost(
				 * "http://wcfifa.d3bug.com/question.php"); HttpEntity entity;
				 * StringEntity s = new StringEntity(str); s.setContentType(new
				 * BasicHeader(HTTP.CONTENT_TYPE, "application/json")); entity =
				 * s; request.setEntity(entity); HttpResponse response; response
				 * = httpclient.execute(request); HttpEntity httpEntity =
				 * response.getEntity(); is = httpEntity.getContent();
				 * 
				 * try { BufferedReader reader = new BufferedReader( new
				 * InputStreamReader(is, "iso-8859-1"), 8); StringBuilder sb =
				 * new StringBuilder(); String line = null; while ((line =
				 * reader.readLine()) != null) { sb.append(line + "\n"); }
				 * is.close(); json = sb.toString(); Log.e("UU", json); } catch
				 * (Exception e) { Log.e("Buffer Error",
				 * "Error converting result " + e.toString()); }
				 */

				JSONObject data = new JSONObject();
				data.put("q_set", matchid);

				String str = data.toString();
				/*
				 * HttpClient httpclient = new DefaultHttpClient(); HttpPost
				 * request = new HttpPost(
				 * "http://wcfifa.d3bug.com/user_data_save.php"); HttpEntity
				 * entity; StringEntity s = new StringEntity(str);
				 * s.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
				 * "application/json")); entity = s; request.setEntity(entity);
				 * HttpResponse response; response =
				 * httpclient.execute(request); HttpEntity httpEntity =
				 * response.getEntity(); is = httpEntity.getContent();
				 * 
				 * try { BufferedReader reader = new BufferedReader( new
				 * InputStreamReader(is, "iso-8859-1"), 8); StringBuilder sb =
				 * new StringBuilder(); String line = null; while ((line =
				 * reader.readLine()) != null) { sb.append(line + "\n"); }
				 * is.close(); json = sb.toString(); Log.e("UU", json); } catch
				 * (Exception e) { Log.e("Buffer Error",
				 * "Error converting result " + e.toString()); }
				 */

				// Making HTTP request

				/*
				 * try { // defaultHttpClient DefaultHttpClient httpClient = new
				 * DefaultHttpClient(); HttpPost httpPost = new HttpPost(
				 * "http://wcfifa.d3bug.com/user_data_save.php");
				 * 
				 * HttpResponse httpResponse = httpClient.execute(httpPost);
				 * HttpEntity httpEntity = httpResponse.getEntity(); is =
				 * httpEntity.getContent();
				 * 
				 * } catch (UnsupportedEncodingException e) {
				 * e.printStackTrace(); } catch (ClientProtocolException e) {
				 * e.printStackTrace(); } catch (IOException e) {
				 * e.printStackTrace(); }
				 * 
				 * try { BufferedReader reader = new BufferedReader( new
				 * InputStreamReader(is, "iso-8859-1"), 8); StringBuilder sb =
				 * new StringBuilder(); String line = null; while ((line =
				 * reader.readLine()) != null) { sb.append(line + "\n"); }
				 * is.close(); json = sb.toString(); Log.e("UU", json); } catch
				 * (Exception e) { Log.e("Buffer Error",
				 * "Error converting result " + e.toString()); }
				 * 
				 * // try parse the string to a JSON object try { jObj = new
				 * JSONObject(json); } catch (JSONException e) {
				 * Log.e("JSON Parser", "Error parsing data " + e.toString()); }
				 */

				/*
				 * JSONParserWithParameter jwith = new
				 * JSONParserWithParameter(); ret =
				 * jwith.getJSONFromUrlReturnString(
				 * "http://wcfifa.d3bug.com/user_data_save.php", str, "user");
				 * 
				 * System.out.println(ret);
				 */

				JSONParserWithParameter js = new JSONParserWithParameter();

				ret1 = js
						.getJSONFromUrlReturnString(
								"http://wcfifa.d3bug.com/question.php", str,
								"question");

				if (ret1 != null) {

					JSONObject jsonObjMain = new JSONObject(ret1);

					JSONArray jsonArray = jsonObjMain.getJSONArray("quiz");

					// JSONArray array = new JSONArray(rest);
					length = jsonArray.length();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject n = jsonArray.getJSONObject(i);

						String quesid = n.getString("ques_id");
						String ques = n.getString("ques");
						String op1 = n.getString("op_1");
						String op2 = n.getString("op_2");
						String op3 = n.getString("op_3");
						String op4 = n.getString("op_4");
						String op5 = n.getString("op_5");
						String op6 = n.getString("op_6");

						// Log.e("UU", ques);

						Quiz qz = new Quiz(quesid, ques, op1, op2, op3, op4,
								op5, op6);
						quiz.add(qz);

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
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		if (isVisibleToUser) {

			if (DetectConnection.checkInternetConnection(Constants.context)) {

				if (Constants.trk_1 != 1) {
					path = "json/quiz.txt";
					Calendar c = Calendar.getInstance();
					System.out.println("Current time => " + c.getTime());
					SimpleDateFormat df1 = new SimpleDateFormat("hh:mm aa");
					System.out.println(df1.format(c.getTime()));

					SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					String formattedDate = df.format(c.getTime());

					if (formattedDate.contentEquals("12-Jun-2014")) {
						formattedDate = "13-Jun-2014";
					}

					todayMatch = Constants.today;

					System.out.println("Size Today 114"
							+ Constants.today.size());

					System.out.println("Size Today 112" + todayMatch.size());

					if (formattedDate.contentEquals("12-Jun-2014")) {
						formattedDate = "13-Jun-2014";
					}

					Database dt = new Database(Constants.context);
					dt.open();
					list = new ArrayList<String>();
					list.clear();

					for (int j = 0; j < todayMatch.size(); j++) {
						today = dt.getTodayFixtureDataTodayMatch(todayMatch
								.get(j).getId());

						list.add(today.get(i).getTeam1() + "  VS  "
								+ today.get(i).getTeam2());
					}

					dt.close();
					System.out.println("Today" + today.size());

					done = 0;

					/*
					 * List<String> list = new ArrayList<String>();
					 * 
					 * // list.add("Select Match"); for (int i = 0; i <
					 * today.size(); i++) { list.add(today.get(i).getTeam1() +
					 * "  VS  " + today.get(i).getTeam2()); }
					 */

					/*
					 * if (today.size() <= 0) { list.add("Brazil  Vs  Croatia");
					 * }
					 */

					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
							Constants.context,
							android.R.layout.simple_spinner_dropdown_item, list);
					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner.setAdapter(dataAdapter);
					try {
						matchid = todayMatch.get(0).getId();
					} catch (Exception e) {
						// TODO: handle exception
						//matchid = "1";
					}

					if (matchid == null) {/*
										 * 
										 * matchid = "1";
										 * 
										 * Constants.matchid = matchid;
										 * 
										 * Database d = new
										 * Database(Constants.context);
										 * d.open(); String[] ne =
										 * d.ViewTrackId(); d.close(); int t =
										 * 0;
										 * 
										 * for (int k = 0; k < ne.length; k++) {
										 * 
										 * if (matchid.contentEquals(ne[k])) { t
										 * = 1; break; }
										 * 
										 * }
										 * 
										 * if (t == 0) { GetQuizQuestion qz =
										 * new GetQuizQuestion(); qz.execute();
										 * } else {
										 * lyAns.setVisibility(View.INVISIBLE);
										 * tvQues.setText(
										 * "You are already answer this questions set!!"
										 * ); Toast toast = Toast .makeText(
										 * Constants.context,
										 * "You are already answer this questions set!!"
										 * , Toast.LENGTH_LONG);
										 * toast.setGravity(Gravity.CENTER, 0,
										 * 0); toast.show();
										 * 
										 * }
										 */

						lyAns.setVisibility(View.INVISIBLE);
						tvQues.setText("No Questions available for now. Questions will be available at least 1 hour prior to the start of the match.");
					} else {
						Constants.matchid = matchid;

						Database d = new Database(Constants.context);
						d.open();
						String[] ne = d.ViewTrackId();
						d.close();
						int t = 0;

						for (int k = 0; k < ne.length; k++) {

							if (matchid.contentEquals(ne[k])) {
								t = 1;
								break;
							}

						}

						if (t == 0) {
							GetQuizQuestion qz = new GetQuizQuestion();
							qz.execute();
						} else {
							lyAns.setVisibility(View.INVISIBLE);
							tvQues.setText("You are already answer this questions set!!");
							Toast toast = Toast
									.makeText(
											Constants.context,
											"You are already answer this questions set!!",
											Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();

						}

					}

				}

			} else {

				ly.setVisibility(View.GONE);
				Toast toast = Toast.makeText(Constants.context,
						"No internet connection!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		} else {
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		done = 0;
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df1 = new SimpleDateFormat("hh:mm aa");
		System.out.println(df1.format(c.getTime()));

		System.out.println("Haire hay");

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String formattedDate = df.format(c.getTime());

		todayMatch = Constants.today;

		System.out.println("Size Today" + todayMatch.size());

		if (formattedDate.contentEquals("12-Jun-2014")) {
			formattedDate = "13-Jun-2014";
		}

		Database dt = new Database(Constants.context);
		dt.open();
		list = new ArrayList<String>();
		list.clear();

		for (int j = 0; j < todayMatch.size(); j++) {
			today = dt.getTodayFixtureDataTodayMatch(todayMatch.get(j).getId());

			list.add(today.get(i).getTeam1() + "  VS  "
					+ today.get(i).getTeam2());
		}

		dt.close();
		System.out.println(today.size());

		done = 0;

		/*
		 * * List<String> list = new ArrayList<String>();
		 * 
		 * // list.add("Select Match"); for (int i = 0; i < today.size(); i++) {
		 * list.add(today.get(i).getTeam1() + "  VS  " +
		 * today.get(i).getTeam2()); }
		 * 
		 * 
		 * 
		 * if (today.size() <= 0) { list.add("Brazil  Vs  Croatia"); }
		 */

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				Constants.context,
				android.R.layout.simple_spinner_dropdown_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

	}

	public void emptyAll() {
		// TODO Auto-generated method stub
		int trackTrue = 0;
		String ans_track = null;
		if (track.size() > 0) {

			for (int i = 0; i < track.size(); i++) {
				if (track.get(i).getQues_id().contentEquals(quesid)) {
					trackTrue = 1;
					ans_track = track.get(i).getQues();
				}
			}
		}
		if (trackTrue == 1) {

			rbOp1.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp2.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp3.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp4.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp5.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp6.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));

			Log.d("Ta   mm", track.get(i).getQues());

			if (ans_track.contentEquals("1")) {
				rbOp1.setBackgroundColor(getResources().getColor(
						R.color.button_select));
			} else if (ans_track.contentEquals("2")) {
				rbOp2.setBackgroundColor(getResources().getColor(
						R.color.button_select));
			} else if (ans_track.contentEquals("3")) {
				rbOp3.setBackgroundColor(getResources().getColor(
						R.color.button_select));
			} else if (ans_track.contentEquals("4")) {
				rbOp4.setBackgroundColor(getResources().getColor(
						R.color.button_select));
			} else if (ans_track.contentEquals("5")) {
				rbOp5.setBackgroundColor(getResources().getColor(
						R.color.button_select));
			} else if (ans_track.contentEquals("6")) {
				rbOp6.setBackgroundColor(getResources().getColor(
						R.color.button_select));
			} else {

			}
		} else {
			rbOp1.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp2.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp3.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp4.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp5.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
			rbOp6.setBackgroundColor(getResources().getColor(
					R.color.button_not_select));
		}

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		rbOp1.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp2.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp3.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp4.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp5.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));
		rbOp6.setBackgroundColor(getResources().getColor(
				R.color.button_not_select));

	}

	public static String AssetJSONFile(String filename, Context context)
			throws IOException {
		AssetManager manager = context.getAssets();
		InputStream file = manager.open(filename);
		byte[] formArray = new byte[file.available()];
		file.read(formArray);
		file.close();

		return new String(formArray);
	}

}
