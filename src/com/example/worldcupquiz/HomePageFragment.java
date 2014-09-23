package com.example.worldcupquiz;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.d3bugbd.worldcupQuiz.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.tourism.adapters.FixtureAdapter;
import com.worldcup.Utils.ConnectionDetector;
import com.worldcup.Utils.Constants;
import com.worldcup.Utils.Database;
import com.worldcup.Utils.DetectConnection;
import com.worldcup.Utils.JSONParser;
import com.worldcup.Utils.JSONParserWithParameter;
import com.worldcup.javaclass.Answer;
import com.worldcup.javaclass.InQuiz;
import com.worldcup.javaclass.Quiz;
import com.worldcup.javaclass.ToadyMatch;

public class HomePageFragment extends Fragment {
	static ProgressDialog Progress;
	String possibleEmail = "mit", possiblePhoneNumber = "123";
	TelephonyManager tm;
	String ret;

	static Button rbOp1, rbOp2, rbOp3, rbOp4, rbOp5, rbOp6;
	static Button bSkip, bSub;
	static TextView tvQues;
	SharedPreferences pref;
	ConnectionDetector cn;
	static String radioAnswerIn = "";
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	int length;

	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	String title = " ", title_new;
	String ti = " ";
	static ListView lv;
	static LinearLayout ly;
	List<InQuiz> quiz = new ArrayList<InQuiz>();
	List<Answer> ans = new ArrayList<Answer>();
	List<ToadyMatch> today = new ArrayList<ToadyMatch>();
	int trackInQuiz;
	String quesid;
	static Context context;
	int firstInstall;
	public static String quesId, inAns;
	String path;

	public HomePageFragment() {
		// TODO Auto-generated constructor stub
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = View.inflate(getActivity(), R.layout.home_main, null);

		rbOp1 = (Button) v.findViewById(R.id.rbOp1);
		rbOp2 = (Button) v.findViewById(R.id.rbOp2);
		rbOp3 = (Button) v.findViewById(R.id.rbOp3);
		rbOp4 = (Button) v.findViewById(R.id.rbOp4);
		rbOp5 = (Button) v.findViewById(R.id.rbOp5);
		rbOp6 = (Button) v.findViewById(R.id.rbOp6);

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

				radioAnswerIn = rbOp1.getText().toString();
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

				radioAnswerIn = rbOp2.getText().toString();
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

				radioAnswerIn = rbOp3.getText().toString();
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

				radioAnswerIn = rbOp4.getText().toString();
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

				radioAnswerIn = rbOp5.getText().toString();
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

				radioAnswerIn = rbOp6.getText().toString();
			}
		});

		lv = (ListView) v.findViewById(R.id.lvNews);
		bSub = (Button) v.findViewById(R.id.bInAns);
		ly = (LinearLayout) v.findViewById(R.id.lyInQuiz);
		tvQues = (TextView) v.findViewById(R.id.tvInQues);
		context = this.getActivity();
		cn = new ConnectionDetector(getActivity());

		// lv.setDivider(getResources().getDrawable(R.drawable.blk_divide));

		AdView adView = new AdView(getActivity(), AdSize.BANNER, getResources()
				.getString(R.string.add_id));

		/*
		 * int diWidth = 320; int diHeight = 52; int density = (int)
		 * getResources().getDisplayMetrics().density;
		 * 
		 * int scaledWidth = (int) (diWidth * density); int scaledHeight = (int)
		 * (diHeight * density);
		 */
		RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.ad);

		/*
		 * layout.setMinimumWidth(scaledWidth);
		 * layout.setMinimumHeight(scaledHeight);
		 */

		layout.addView(adView);
		AdRequest request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df1 = new SimpleDateFormat("hh:mm aa");
		System.out.println(df1.format(c.getTime()));

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String formattedDate = df.format(c.getTime());

		System.out.println(formattedDate);

		pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		/* Progress = new ProgressDialog(getActivity()); */

		firstInstall = pref.getInt("install", 0);

		trackInQuiz = pref.getInt("trackInQues", 0);
		Log.e("III", trackInQuiz + "");

		/*
		 * try {
		 * 
		 * if (quiz.size() > 0) {
		 * 
		 * if (trackInQuiz < Integer.parseInt(Constants.quesid)) {
		 * ly.setVisibility(View.VISIBLE); lv.setVisibility(View.GONE); quiz =
		 * Constants.iquiz; tvQues.setText(quiz.get(0).getQues());
		 * rbOp1.setText(quiz.get(0).getOp1());
		 * rbOp2.setText(quiz.get(0).getOp2());
		 * 
		 * rbOp3.setText(quiz.get(0).getOp3());
		 * 
		 * rbOp4.setText(quiz.get(0).getOp4());
		 * 
		 * rbOp5.setText(quiz.get(0).getOp5());
		 * 
		 * rbOp6.setText(quiz.get(0).getOp6());
		 * 
		 * } else { ly.setVisibility(View.GONE); lv.setVisibility(View.VISIBLE);
		 * } } else { ly.setVisibility(View.GONE);
		 * lv.setVisibility(View.VISIBLE); }
		 * 
		 * SimpleAdapter mNotification = new SimpleAdapter(Constants.context,
		 * Constants.mylist, R.layout.notification_list_item, new String[] {
		 * "notification", "date" }, new int[] { R.id.notification_textView1,
		 * R.id.date_textView2 }); lv.setAdapter(mNotification); } catch
		 * (Exception e) { // TODO: handle exception }
		 */
		bSub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				quesid = quiz.get(0).getQuesid();

				/*
				 * View radioButton = rgOp.findViewById(id); int radioId =
				 * rgOp.indexOfChild(radioButton); RadioButton btn =
				 * (RadioButton) rgOp.getChildAt(radioId);
				 */
				inAns = radioAnswerIn; // btn.getText().toString();

				Log.e("N", inAns);

				if (DetectConnection.checkInternetConnection(getActivity())) {
					SubmitInstantQuestion st = new SubmitInstantQuestion();
					st.execute();
				} else {
					Toast toast = Toast.makeText(getActivity(),
							"No internet connection!", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

			}
		});

		return v;
	}

	public class GetUserInfo extends AsyncTask<Void, String, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Editor ed = pref.edit();
			ed.putInt("install", 1);
			ed.commit();

			NotificationTask nt = new NotificationTask();
			nt.execute();

		}

		@Override
		protected void onPreExecute() { // TODO Auto-generated method stub
			super.onPreExecute();

			Progress.setMessage("Loading Apps Data...");

			Progress.setCancelable(false);
			Progress.show();

		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(Void... params) {
			Account[] accounts;
			try {
				Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
				accounts = AccountManager.get(getActivity()).getAccountsByType(
						"com.google");

				for (Account account : accounts) {

					if (emailPattern.matcher(account.name).matches()) {
						possibleEmail = account.name;

						Log.e("Eail", possibleEmail);
						break;

					}

				}

				tm = (TelephonyManager) getActivity().getSystemService(
						getActivity().TELEPHONY_SERVICE);
				possiblePhoneNumber = tm.getLine1Number();

				Log.d("Phone", possiblePhoneNumber);

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				pref = PreferenceManager
						.getDefaultSharedPreferences(Constants.context);
				/* Progress = new ProgressDialog(getActivity()); */

				firstInstall = pref.getInt("install", 0);

				JSONObject data = new JSONObject();
				data.put("phone", pref.getString("user_phone", null));
				data.put("name", pref.getString("user_name", null));
				data.put("email", possibleEmail);

				String str = data.toString();

				System.out.println(str);
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

				JSONParserWithParameter jwith = new JSONParserWithParameter();
				ret = jwith.getJSONFromUrlReturnString(
						"http://wcfifa.d3bug.com/user_data_save.php", str,
						"user");

				System.out.println(ret);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	public class GetInstantQuestion extends AsyncTask<Void, String, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Constants.iquiz = quiz;

			if (quiz.size() > 0) {
				if (trackInQuiz < Integer.parseInt(quesid)) {
					ly.setVisibility(View.VISIBLE);
					lv.setVisibility(View.GONE);
					tvQues.setText(quiz.get(0).getQues());
					rbOp1.setText(quiz.get(0).getOp1());
					rbOp2.setText(quiz.get(0).getOp2());

					rbOp3.setText(quiz.get(0).getOp3());

					rbOp4.setText(quiz.get(0).getOp4());

					rbOp5.setText(quiz.get(0).getOp5());

					rbOp6.setText(quiz.get(0).getOp6());

				} else {
					ly.setVisibility(View.GONE);
					lv.setVisibility(View.VISIBLE);
				}
			}

			if (Constants.trk != 1) {
				GetTodayMatch gt = new GetTodayMatch();
				gt.execute();
				Constants.trk = 1;

			} else {
				Progress.cancel();
			}

		}

		@Override
		protected void onPreExecute() { // TODO Auto-generated method stub
			super.onPreExecute();

		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(Void... params) {
			try {

				/*
				 * String jsonString = null; try { jsonString =
				 * AssetJSONFile(path, Constants.context); } catch (IOException
				 * e) { // TODO Auto-generated catch block e.printStackTrace();
				 * 
				 * }
				 */
				/*
				 * JSONObject jsonObjMain = new JSONObject(jsonString);
				 * 
				 * Log.d("json", jsonString);
				 */
				// Creating JSONArray from JSONObject

				JSONParser js = new JSONParser();

				JSONObject n = js
						.getJSONFromUrl("http://wcfifa.d3bug.com/instant_question.php");

				String rest = n.getString("in_quiz");
				System.out.println(rest);

				JSONObject jsonOb = new JSONObject(rest);

				if (jsonOb != null) {

					quesid = jsonOb.getString("ques_id");
					String ques = jsonOb.getString("ques");
					String op1 = jsonOb.getString("op_1");
					String op2 = jsonOb.getString("op_2");
					String op3 = jsonOb.getString("op_3");
					String op4 = jsonOb.getString("op_4");
					String op5 = jsonOb.getString("op_5");
					String op6 = jsonOb.getString("op_6");

					// quesid = "4";
					Constants.quesid = quesid;

					// Log.d("Get", "Get");

					InQuiz in = new InQuiz(quesid, ques, op1, op2, op3, op4,
							op5, op6);
					quiz.add(in);
					// Progress.cancel();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	public class NotificationTask extends AsyncTask<Void, String, String> {

		@Override
		protected void onPreExecute() { // TODO Auto-generated method stub
			super.onPreExecute();

			Progress.setMessage("Fetching Latest News...");

			Progress.setCancelable(false);
			Progress.show();
		}

		@Override
		protected String doInBackground(Void... params) {

			try {
				JSONArray notifications = null;
				JSONParser jParser = new JSONParser();
				JSONObject json = jParser
						.getJSONFromUrl("http://wcfifa.d3bug.com/news.php");

				Log.e("News", json.toString());
				if (json != null) {
					// Getting Array of Contacts
					notifications = json.getJSONArray("news");

					mylist.clear();

					// looping through All Contacts
					for (int i = 0; i < notifications.length(); i++) {
						JSONObject c = notifications.getJSONObject(i);

						// Storing each json item in variable
						title = c.getString("description");
						ti = c.getString("date_time");

						title_new = c.getString("title");

						Log.e("title", title);

						map = new HashMap<String, String>();
						map.put("notification", title_new);
						map.put("date", title);
						Log.e("map", map.toString());
						mylist.add(map);
						Log.e("List", mylist.toString());
					}
				}
			} catch (JSONException e) {
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

				if (mylist != null) {
					if (mylist.size() > 0) {

						Constants.mylist = mylist;

						SimpleAdapter mNotification = new SimpleAdapter(
								getActivity(), mylist,
								R.layout.notification_list_item, new String[] {
										"notification", "date" }, new int[] {
										R.id.notification_textView1,
										R.id.date_textView2 });

						lv.setAdapter(mNotification);

					}
				}

				else {
					Toast toast = Toast.makeText(getActivity(),
							"Announcement not available!", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			GetInstantQuestion gt = new GetInstantQuestion();
			gt.execute();
		}
	}

	public class SubmitInstantQuestion extends AsyncTask<Void, String, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Toast toast = Toast.makeText(getActivity(), "Submitted...",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();

			pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
			Editor edit = pref.edit();
			edit.putInt("trackInQues", Integer.parseInt(quesid));
			edit.commit();

			ly.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
			quiz.clear();
			Progress.cancel();

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

				try {

					tm = (TelephonyManager) getActivity().getSystemService(
							getActivity().TELEPHONY_SERVICE);
					possiblePhoneNumber = tm.getLine1Number();

				} catch (Exception e) {
					e.printStackTrace();
				}

				pref = PreferenceManager
						.getDefaultSharedPreferences(Constants.context);
				/* Progress = new ProgressDialog(getActivity()); */

				JSONObject data = new JSONObject();

				data.put("in_ques_id", quesid);
				data.put("in_ans", inAns);
				data.put("phone_number", pref.getString("user_phone", null));

				String str = data.toString();
				System.out.println(str);
				/*
				 * List<NameValuePair> registration = new
				 * ArrayList<NameValuePair>(); registration.add(new
				 * BasicNameValuePair("form", str));
				 * 
				 * Log.e("Tag", new BasicNameValuePair("form", str) + "");
				 */
				JSONParserWithParameter jwith = new JSONParserWithParameter();

				ret = jwith.getJSONFromUrlReturnString(
						"http://wcfifa.d3bug.com/ins_quiz_ans.php", str,
						"instant_answer");

				System.out.println(ret);
				Log.e("Tag", quesid);
				Log.e("Tag", inAns);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		if (isVisibleToUser) {
			path = "json/inquiz.txt";
			pref = PreferenceManager
					.getDefaultSharedPreferences(Constants.context);
			Progress = new ProgressDialog(Constants.context);

			firstInstall = pref.getInt("install", 0);

			trackInQuiz = pref.getInt("trackInQues", 0);
			Log.e("III", trackInQuiz + "");

			if (DetectConnection.checkInternetConnection(Constants.context)) {
				if (firstInstall == 0) {

					GetUserInfo in = new GetUserInfo();
					in.execute();

				} /*
				 * else { if (Constants.trk != 1) {
				 * 
				 * NotificationTask nt = new NotificationTask(); nt.execute();
				 * 
				 * Constants.trk = 1;
				 * 
				 * } else { if (trackInQuiz <
				 * Integer.parseInt(Constants.quesid)) {
				 * ly.setVisibility(View.VISIBLE); quiz = Constants.iquiz;
				 * tvQues.setText(quiz.get(0).getQues());
				 * rbOp1.setText(quiz.get(0).getOp1());
				 * rbOp2.setText(quiz.get(0).getOp2());
				 * 
				 * rbOp3.setText(quiz.get(0).getOp3());
				 * 
				 * rbOp4.setText(quiz.get(0).getOp4());
				 * 
				 * rbOp5.setText(quiz.get(0).getOp5());
				 * 
				 * rbOp6.setText(quiz.get(0).getOp6());
				 * 
				 * } else { ly.setVisibility(View.GONE); }
				 * 
				 * SimpleAdapter mNotification = new SimpleAdapter(
				 * Constants.context, Constants.mylist,
				 * R.layout.notification_list_item, new String[] {
				 * "notification", "date" }, new int[] {
				 * R.id.notification_textView1, R.id.date_textView2 });
				 * lv.setAdapter(mNotification);
				 * 
				 * } }
				 */

				NotificationTask nt = new NotificationTask();
				nt.execute();
			} else {
				Toast toast = Toast.makeText(Constants.context,
						"No internet connection!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}

		}
	}

	public class GetTodayMatch extends AsyncTask<Void, String, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Constants.today = today;
			Progress.cancel();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(Void... params) {

			try {

				JSONParser js = new JSONParser();

				JSONObject ret = js
						.getJSONFromUrl("http://wcfifa.d3bug.com/quiz_active.php");

				if (ret != null) {

					JSONArray jsonArray = ret.getJSONArray("quiz");
					System.out.println(ret);

					length = jsonArray.length();
					System.out.println(length);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject newJson = jsonArray.getJSONObject(i);

						String id = newJson.getString("id");

						System.out.println("Id " + id);

						ToadyMatch tM = new ToadyMatch(id);

						today.add(tM);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		quiz = Constants.iquiz;

		if (quiz.size() < 1) {

			/*
			 * pref =
			 * PreferenceManager.getDefaultSharedPreferences(getActivity());
			 * Progress = new ProgressDialog(getActivity());
			 * 
			 * firstInstall = pref.getInt("install", 0);
			 * 
			 * trackInQuiz = pref.getInt("trackInQues", 0); Log.e("III",
			 * trackInQuiz + "");
			 * 
			 * try {
			 * 
			 * if (quiz.size() > 0) {
			 * 
			 * if (trackInQuiz < Integer.parseInt(Constants.quesid)) {
			 * ly.setVisibility(View.VISIBLE); lv.setVisibility(View.GONE); quiz
			 * = Constants.iquiz; tvQues.setText(quiz.get(0).getQues());
			 * rbOp1.setText(quiz.get(0).getOp1());
			 * rbOp2.setText(quiz.get(0).getOp2());
			 * 
			 * rbOp3.setText(quiz.get(0).getOp3());
			 * 
			 * rbOp4.setText(quiz.get(0).getOp4());
			 * 
			 * rbOp5.setText(quiz.get(0).getOp5());
			 * 
			 * rbOp6.setText(quiz.get(0).getOp6());
			 * 
			 * } else { ly.setVisibility(View.GONE);
			 * lv.setVisibility(View.VISIBLE); } } else {
			 * ly.setVisibility(View.GONE); lv.setVisibility(View.VISIBLE); }
			 * 
			 * SimpleAdapter mNotification = new
			 * SimpleAdapter(Constants.context, Constants.mylist,
			 * R.layout.notification_list_item, new String[] { "notification",
			 * "date" }, new int[] { R.id.notification_textView1,
			 * R.id.date_textView2 }); lv.setAdapter(mNotification); } catch
			 * (Exception e) { // TODO: handle exception }
			 */
			// lv.setVisibility(View.VISIBLE);
			// ly.setVisibility(View.GONE);
		} else {
			// ly.setVisibility(View.VISIBLE);
			// lv.setVisibility(View.GONE);
		}

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
