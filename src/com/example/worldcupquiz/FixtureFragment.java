package com.example.worldcupquiz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.d3bugbd.worldcupQuiz.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.tourism.adapters.FixtureAdapter;
import com.worldcup.Utils.Constants;
import com.worldcup.Utils.Database;
import com.worldcup.Utils.DetectConnection;
import com.worldcup.Utils.JSONParser;
import com.worldcup.javaclass.Answer;
import com.worldcup.javaclass.Fixture;
import com.worldcup.javaclass.Quiz;
import com.worldcup.javaclass.Skip;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FixtureFragment extends Fragment {

	ListView listFixture;

	SharedPreferences prf;
	Database database;
	int get;
	List<Fixture> fixture = new ArrayList<Fixture>();
	SharedPreferences prefs;
	Button b1, b2, b3;
	ProgressDialog Progress;
	public static int length, i = 0;
	List<Quiz> quiz = new ArrayList<Quiz>();
	List<Answer> ans = new ArrayList<Answer>();
	List<Skip> skip = new ArrayList<Skip>();
	String date, time, team1, team2, stadium, round;
	static Context context;

	public FixtureFragment() {
		// TODO Auto-generated constructor stub
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = View.inflate(getActivity(), R.layout.fixture_main, null);

		listFixture = (ListView) v.findViewById(R.id.lvFixture);
		b1 = (Button) v.findViewById(R.id.button1);
		b2 = (Button) v.findViewById(R.id.button2);
		b3 = (Button) v.findViewById(R.id.button3);

		context = this.getActivity();
		b1.setTextColor(Color.WHITE);
		
		
		AdView adView = new AdView(getActivity(), AdSize.BANNER, getResources().getString(R.string.add_id));

		RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.ad);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);

		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				database = new Database(getActivity());
				database.open();
				fixture = database.getFixtureData("Group");
				database.close();

				listFixture.setAdapter(new FixtureAdapter(getActivity(),
						fixture));

				/*listFixture.setDivider(getResources().getDrawable(
						R.drawable.blk));*/

				b1.setTextColor(Color.WHITE);
				b2.setTextColor(Color.BLACK);
				b3.setTextColor(Color.BLACK);

			}
		});

		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				database = new Database(getActivity());
				database.open();
				fixture = database.getFixtureData("Second Round");
				database.close();

				listFixture.setAdapter(new FixtureAdapter(getActivity(),
						fixture));
				/*listFixture.setDivider(getResources().getDrawable(
						R.drawable.blk));*/

				b1.setTextColor(Color.BLACK);
				b2.setTextColor(Color.WHITE);
				b3.setTextColor(Color.BLACK);

			}
		});

		b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				database = new Database(getActivity());
				database.open();
				fixture = database.getFixtureData("finals");
				database.close();

				listFixture.setAdapter(new FixtureAdapter(getActivity(),
						fixture));
				/*listFixture.setDivider(getResources().getDrawable(
						R.drawable.blk));*/

				b1.setTextColor(Color.BLACK);
				b2.setTextColor(Color.BLACK);
				b3.setTextColor(Color.WHITE);

			}
		});

		database = new Database(getActivity());
		database.open();
		fixture = database.getFixtureData("Group");
		database.close();

		listFixture.setAdapter(new FixtureAdapter(getActivity(), fixture));
		//listFixture.setDivider(getResources().getDrawable(R.drawable.blk));

		/** When clicked on login button go to the login page */

		return v;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public class GetFixtureData extends AsyncTask<Void, String, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			prefs = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
			Editor ed = prefs.edit();
			ed.putInt("fixtureTrack", 1);
			ed.commit();

			database = new Database(Constants.context);
			database.open();
			fixture = database.getFixtureData("Group");
			database.close();

			listFixture.setAdapter(new FixtureAdapter(getActivity(), fixture));
		//	listFixture.setDivider(getResources().getDrawable(R.drawable.blk));

			Progress.cancel();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Progress = new ProgressDialog(Constants.context);
			Progress.setMessage("Loading  Fixture...");
			Progress.setCancelable(false);
			Progress.show();

		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(Void... params) {

			try {

				JSONParser js = new JSONParser();

				JSONObject n = js
						.getJSONFromUrl("http://mobioapp.net/apps/hilton_events/json_users.php");

				String rest = n.getString("getAllMember");
				System.out.println(rest);
				database = new Database(getActivity());
				database.open();
				JSONArray array = new JSONArray(rest);
				length = array.length();
				for (int i = 0; i < array.length(); i++) {
					n = array.getJSONObject(i);

					date = n.getString("date");
					time = n.getString("time");
					team1 = n.getString("team1");
					team2 = n.getString("team2");
					stadium = n.getString("stadium");
					round = n.getString("round");

					database.addFixtureData(date, team1, team2, time, stadium,
							round);

				}
				database.close();
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

			if (DetectConnection.checkInternetConnection(Constants.context)) {
				prefs = PreferenceManager
						.getDefaultSharedPreferences(Constants.context);
				get = prefs.getInt("fixtureTrack", 0);
				if (get == 0) {

					database = new Database(Constants.context);
					database.open();

					try {/*
						database.addFixtureData("30-May-2014",
								"Brazil", "Croatia", "02.00 Am",
								"Arena Corinthians", "Group");

						database.addFixtureData("30-May-2014",
								"Mexico", "Cameroon", "10.00 Pm",
								"Estadio das Dunas", "Group");

						database.addFixtureData("28-May-2014",
								"Spain", "Netherlands", "01.00 Am",
								"Arena Fonte Nova", "Group");

						database.addFixtureData("Friday, June 13, 2014",
								"Chile", "Australia", "04.00 Am",
								"Arena Pantanal", "Group");

						database.addFixtureData("Saturday, June 14, 2014",
								"Colombia", "Greece", "10.00 Pm",
								"Estadio Mineir�o", "Group");

						database.addFixtureData("Saturday, June 14, 2014",
								"Uruguay", "Costa Rica", "01.00 Am",
								"Estadio Castel�o", "Group");

						database.addFixtureData("Saturday, June 14, 2014",
								"England", "Italy", "04.00 Am",
								"Arena Amazonia", "Group");

						database.addFixtureData("Saturday, June 14, 2014",
								"Ivory Coast", "Japan", "07.00 Am",
								"Arena Pernambuco", "Group");

						database.addFixtureData("Sunday, June 15, 2014",
								"Switzerland", "Ecuador", "10.00 Pm",
								"Nacional", "Group");

						database.addFixtureData("Sunday, June 15, 2014",
								"France", "Honduras", "01.00 Am",
								"Estadio Beira-Rio", "Group");

						database.addFixtureData("Sunday, June 15, 2014",
								"Argentina", "Bosnia-Herzegovina", "04.00 Am",
								"Estadio do Maracan�", "Group");

						database.addFixtureData("Monday, June 16, 2014",
								"Germany", "Portugal", "10.00 Pm",
								"Arena Fonte Nova", "Group");

						database.addFixtureData("Monday, June 16, 2014",
								"Iran", "Nigeria", "01.00 Am",
								"Arena da Baixada", "Group");

						database.addFixtureData("Monday, June 16, 2014",
								"United States", "Ghana", "04.00 Am",
								"Estadio das Dunas", "Group");

						database.addFixtureData("Tuesday, June 17, 2014",
								"Belgium", "Algeria", "10.00 Pm",
								"Estadio Mineir�o", "Group");

						database.addFixtureData("Tuesday, June 17, 2014",
								"Brazil", "Mexico", "01.00 Am",
								"Estadio Castel�o", "Group");

						database.addFixtureData("Tuesday, June 17, 2014",
								"Russia", "South Korea", "04.00 Am",
								"Arena Pantanal", "Group");

						database.addFixtureData("Wednesday, June 18, 2014",
								"Australia", "Netherlands", "10.00 Pm",
								"Estadio Beira-Rio", "Group");

						database.addFixtureData("Wednesday, June 18, 2014",
								"Spain", "Chile", "01.00 Am",
								"Estadio do Maracan�", "Group");

						database.addFixtureData("Wednesday, June 18, 2014",
								"Cameroon", "Croatia", "04.00 Am",
								"Arena Amazonia", "Group");

						database.addFixtureData("Thursday, June 19, 2014",
								"Colombia", "Ivory Coast", "10.00 Pm",
								"Nacional", "Group");

						database.addFixtureData("Thursday, June 19, 2014",
								"Uruguay", "England", "01.00 Am",
								"Arena Corinthians", "Group");

						database.addFixtureData("Thursday, June 19, 2014",
								"Japan", "Greece", "04.00 Am",
								"Estadio das Dunas", "Group");

						database.addFixtureData("Thursday, June 19, 2014",
								"Colombia", "Ivory Coast", "10.00 Pm",
								"Nacional", "Group");

						database.addFixtureData("Thursday, June 19, 2014",
								"Uruguay", "England", "01.00 Am",
								"Arena Corinthians", "Group");

						database.addFixtureData("Thursday, June 19, 2014",
								"Japan", "Greece", "04.00 Am",
								"Estadio das Dunas", "Group");

						database.addFixtureData("Friday, June 20, 2014",
								"Italy", "Costa Rica", "10.00 Pm",
								"Arena Pernambuco", "Group");

						database.addFixtureData("Friday, June 20, 2014",
								"Switzerland", "France", "01.00 Am",
								"Arena Fonte Nova", "Group");

						database.addFixtureData("Friday, June 20, 2014",
								"Honduras", "Ecuador", "04.00 Am",
								"Arena da Baixada", "Group");

						database.addFixtureData("Saturday, June 21, 2014",
								"Argentina", "Iran", "10.00 Pm",
								"Estadio Mineir�o", "Group");

						database.addFixtureData("Saturday, June 21, 2014",
								"Germany", "Ghana", "01.00 Am",
								"Estadio Castel�o", "Group");

						database.addFixtureData("Saturday, June 21, 2014",
								"Nigeria", "Bosnia-Herzegovina", "04.00 Am",
								"Arena Pantanal", "Group");

						database.addFixtureData("Sunday, June 22, 2014",
								"Belgium", "Russia", "10.00 Pm",
								"Estadio do Maracan�", "Group");

						database.addFixtureData("Sunday, June 22, 2014",
								"South Korea", "Algeria", "01.00 Am",
								"Estadio Beira-Rio", "Group");

						database.addFixtureData("Sunday, June 22, 2014",
								"United States", "Portugal", "04.00 Am",
								"Arena Amazonia", "Group");

						database.addFixtureData("Monday, June 23, 2014",
								"Australia", "Spain", "10.00 Pm",
								"Arena da Baixada", "Group");

						database.addFixtureData("Monday, June 23, 2014",
								"Netherlands", "Chile", "10.00 pm",
								"Arena Corinthians", "Group");

						database.addFixtureData("Monday, June 23, 2014",
								"Croatia", "Mexico", "02.00 Am",
								"Arena Pernambuco", "Group");

						database.addFixtureData("Monday, June 23, 2014",
								"Cameroon", "Brazil", "02.00 Am", "Nacional",
								"Group");

						database.addFixtureData("Tuesday, June 24, 2014",
								"Italy", "Uruguay", "10.00 Pm",
								"Estadio das Dunas", "Group");

						database.addFixtureData("Tuesday, June 24, 2014",
								"Costa Rica", "England", "10.00 pm",
								"Estadio Mineir�o", "Group");

						database.addFixtureData("Tuesday, June 24, 2014",
								"Japan", "Colombia", "02.00 Am",
								"Arena Pantanal", "Group");

						database.addFixtureData("Tuesday, June 24, 2014",
								"Greece", "Ivory Coast", "02.00 Am",
								"Estadio Castel�o", "Group");

						database.addFixtureData("Wednesday, June 25, 2014",
								"Nigeria", "Argentina", "10.00 Pm",
								"Estadio Beira-Rio", "Group");

						database.addFixtureData("Wednesday, June 25, 2014",
								"Bosnia-Herzegovina", "Iran", "10.00 pm",
								"Arena Fonte Nova", "Group");

						database.addFixtureData("Wednesday, June 25, 2014",
								"Honduras", "Switzerland", "02.00 Am",
								"Arena Amazonia", "Group");

						database.addFixtureData("Wednesday, June 25, 2014",
								"Ecuador", "France", "02.00 Am",
								"Estadio do Maracan�", "Group");

						database.addFixtureData("Thursday, June 26, 2014",
								"United States", "Germany", "10.00 Pm",
								"Arena Pernambuco", "Group");

						database.addFixtureData("Thursday, June 26, 2014",
								"Portugal", "Ghana", "10.00 pm", "Nacional",
								"Group");

						database.addFixtureData("Thursday, June 26, 2014",
								"South Korea", "Belgium", "02.00 Am",
								"Arena Corinthians", "Group");

						database.addFixtureData("Thursday, June 26, 2014",
								"Algeria", "Russia", "02.00 Am",
								"Arena da Baixada", "Group");

						database.addFixtureData("Saturday, June 28, 2014",
								"1A", "2B", "10.00 pm", "Estadio Mineir�o",
								"Second Round");

						database.addFixtureData("Saturday, June 28, 2014",
								"1C", "2D", "02.00 Am", "Estadio do Maracan�",
								"Second Round");

						database.addFixtureData("Sunday, June 29, 2014", "1B",
								"2A", "10.00 pm", "Estadio Castel�o",
								"Second Round");

						database.addFixtureData("Sunday, June 29, 2014", "1D",
								"2C", "02.00 Am", "Arena Pernambuco",
								"Second Round");

						database.addFixtureData("Monday, June 30, 2014", "1E",
								"2F", "10.00 pm", "Nacional", "Second Round");

						database.addFixtureData("Monday, June 30, 2014", "1G",
								"2H", "02.00 Am", "Estadio Beira-Rio",
								"Second Round");

						database.addFixtureData("Tuesday, July 1, 2014", "1F",
								"2E", "10.00 pm", "Arena Corinthians",
								"Second Round");

						database.addFixtureData("Tuesday, July 1, 2014", "1H",
								"2G", "02.00 Am", "Arena Fonte Nova",
								"Second Round");

						database.addFixtureData("Friday, July 4, 2014",
								"Winner Match 53", "Winner Match 54",
								"10.00 pm", "Estadio do Maracan�", "finals");

						database.addFixtureData("Friday, July 4, 2014",
								"Winner Match 49", "Winner Match 50",
								"02.00 Am", "Estadio Castel�o", "finals");

						database.addFixtureData("Saturday, July 5, 2014",
								"Winner Match 55", "Winner Match 56",
								"10.00 pm", "Nacional", "finals");

						database.addFixtureData("Saturday, July 5, 2014",
								"Winner Match 51", "Winner Match 52",
								"02.00 Am", "Arena Fonte Nova", "finals");

						database.addFixtureData("Tuesday, July 8, 2014",
								"Winner Match 57", "Winner Match 58",
								"02.00 Am", "Estadio Mineir�o", "finals");

						database.addFixtureData("Wednesday, July 9, 2014",
								"Winner Match 59", "Winner Match 60",
								"02.00 Am", "Arena Corinthians", "finals");

						database.addFixtureData("Sunday, July 13, 2014",
								"Winner Match 61", "Winner Match 62",
								"02.00 Am", "Estadio do Maracan�", "finals");

						*//****
						 * -------------------------------History--------------
						 * ---- ----
						 *//*

						database.addHistoryData("1954", "Uruguay", "Brazil ",
								null);

						database.addHistoryData("1954", "West Germany",
								"Hungary", null);

						database.addHistoryData("1958", "Brazil ", "Uruguay",
								null);

						database.addHistoryData("1962", "Brazil  ",
								"Czechoslovakia", null);

						database.addHistoryData("1970", "Brazil", "Italy", null);

						database.addHistoryData("1974", "West Germany",
								"Netherlands", null);

						database.addHistoryData("1978", "Argentina", "Denmark",
								null);

						database.addHistoryData("1982", "Italy",
								"West Germany", null);

						database.addHistoryData("1986", "Argentina",
								"Netherlands", null);

						database.addHistoryData("1990", "West Germany",
								"Argentina", null);

						database.addHistoryData("1994", "Brazil", "Italy", null);

						database.addHistoryData("1998", "France", "Brazil",
								null);

						database.addHistoryData("2002", "Brazil", "Germany",
								null);

						database.addHistoryData("2006", "Italy", "French", null);

						database.addHistoryData("2010", "Spain", "Netherlands",
								null);

						database.close();
					*/} catch (Exception e) {
						e.printStackTrace();
					} finally {
						Editor ed = prefs.edit();
						ed.putInt("fixtureTrack", 1);
						ed.commit();
						
						database = new Database(Constants.context);
						database.open();
						fixture = database.getFixtureData("Group");
						database.close();

						listFixture.setAdapter(new FixtureAdapter(Constants.context,
								fixture));

						listFixture.setDivider(getResources().getDrawable(
								R.drawable.blk));

					}
				} else {/*
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							Constants.context);

					// set title
					// alertDialogBuilder.setTitle("Your Title");

					// set dialog message
					alertDialogBuilder
							.setMessage("Do you update fixture?")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											database = new Database(
													Constants.context);
											database.open();
											database.deleteAll();
											fixture = database
													.getFixtureData("Group");
											database.close();

											database = new Database(
													Constants.context);
											database.open();

											try {
												database.addFixtureData(
														"Thursday, June 12, 2014",
														"Brazil", "Croatia",
														"02.00 Am",
														"Arena Corinthians",
														"Group");

												database.addFixtureData(
														"Friday, June 13, 2014",
														"Mexico", "Cameroon",
														"10.00 Pm",
														"Estadio das Dunas",
														"Group");

												database.addFixtureData(
														"Friday, June 13, 2014",
														"Spain", "Netherlands",
														"01.00 Am",
														"Arena Fonte Nova",
														"Group");

												database.addFixtureData(
														"Friday, June 13, 2014",
														"Chile", "Australia",
														"04.00 Am",
														"Arena Pantanal",
														"Group");

												database.addFixtureData(
														"Saturday, June 14, 2014",
														"Colombia", "Greece",
														"10.00 Pm",
														"Estadio Mineir�o",
														"Group");

												database.addFixtureData(
														"Saturday, June 14, 2014",
														"Uruguay",
														"Costa Rica",
														"01.00 Am",
														"Estadio Castel�o",
														"Group");

												database.addFixtureData(
														"Saturday, June 14, 2014",
														"England", "Italy",
														"04.00 Am",
														"Arena Amazonia",
														"Group");

												database.addFixtureData(
														"Saturday, June 14, 2014",
														"Ivory Coast", "Japan",
														"07.00 Am",
														"Arena Pernambuco",
														"Group");

												database.addFixtureData(
														"Sunday, June 15, 2014",
														"Switzerland",
														"Ecuador", "10.00 Pm",
														"Nacional", "Group");

												database.addFixtureData(
														"Sunday, June 15, 2014",
														"France", "Honduras",
														"01.00 Am",
														"Estadio Beira-Rio",
														"Group");

												database.addFixtureData(
														"Sunday, June 15, 2014",
														"Argentina",
														"Bosnia-Herzegovina",
														"04.00 Am",
														"Estadio do Maracan�",
														"Group");

												database.addFixtureData(
														"Monday, June 16, 2014",
														"Germany", "Portugal",
														"10.00 Pm",
														"Arena Fonte Nova",
														"Group");

												database.addFixtureData(
														"Monday, June 16, 2014",
														"Iran", "Nigeria",
														"01.00 Am",
														"Arena da Baixada",
														"Group");

												database.addFixtureData(
														"Monday, June 16, 2014",
														"United States",
														"Ghana", "04.00 Am",
														"Estadio das Dunas",
														"Group");

												database.addFixtureData(
														"Tuesday, June 17, 2014",
														"Belgium", "Algeria",
														"10.00 Pm",
														"Estadio Mineir�o",
														"Group");

												database.addFixtureData(
														"Tuesday, June 17, 2014",
														"Brazil", "Mexico",
														"01.00 Am",
														"Estadio Castel�o",
														"Group");

												database.addFixtureData(
														"Tuesday, June 17, 2014",
														"Russia",
														"South Korea",
														"04.00 Am",
														"Arena Pantanal",
														"Group");

												database.addFixtureData(
														"Wednesday, June 18, 2014",
														"Australia",
														"Netherlands",
														"10.00 Pm",
														"Estadio Beira-Rio",
														"Group");

												database.addFixtureData(
														"Wednesday, June 18, 2014",
														"Spain", "Chile",
														"01.00 Am",
														"Estadio do Maracan�",
														"Group");

												database.addFixtureData(
														"Wednesday, June 18, 2014",
														"Cameroon", "Croatia",
														"04.00 Am",
														"Arena Amazonia",
														"Group");

												database.addFixtureData(
														"Thursday, June 19, 2014",
														"Colombia",
														"Ivory Coast",
														"10.00 Pm", "Nacional",
														"Group");

												database.addFixtureData(
														"Thursday, June 19, 2014",
														"Uruguay", "England",
														"01.00 Am",
														"Arena Corinthians",
														"Group");

												database.addFixtureData(
														"Thursday, June 19, 2014",
														"Japan", "Greece",
														"04.00 Am",
														"Estadio das Dunas",
														"Group");

												database.addFixtureData(
														"Thursday, June 19, 2014",
														"Colombia",
														"Ivory Coast",
														"10.00 Pm", "Nacional",
														"Group");

												database.addFixtureData(
														"Thursday, June 19, 2014",
														"Uruguay", "England",
														"01.00 Am",
														"Arena Corinthians",
														"Group");

												database.addFixtureData(
														"Thursday, June 19, 2014",
														"Japan", "Greece",
														"04.00 Am",
														"Estadio das Dunas",
														"Group");

												database.addFixtureData(
														"Friday, June 20, 2014",
														"Italy", "Costa Rica",
														"10.00 Pm",
														"Arena Pernambuco",
														"Group");

												database.addFixtureData(
														"Friday, June 20, 2014",
														"Switzerland",
														"France", "01.00 Am",
														"Arena Fonte Nova",
														"Group");

												database.addFixtureData(
														"Friday, June 20, 2014",
														"Honduras", "Ecuador",
														"04.00 Am",
														"Arena da Baixada",
														"Group");

												database.addFixtureData(
														"Saturday, June 21, 2014",
														"Argentina", "Iran",
														"10.00 Pm",
														"Estadio Mineir�o",
														"Group");

												database.addFixtureData(
														"Saturday, June 21, 2014",
														"Germany", "Ghana",
														"01.00 Am",
														"Estadio Castel�o",
														"Group");

												database.addFixtureData(
														"Saturday, June 21, 2014",
														"Nigeria",
														"Bosnia-Herzegovina",
														"04.00 Am",
														"Arena Pantanal",
														"Group");

												database.addFixtureData(
														"Sunday, June 22, 2014",
														"Belgium", "Russia",
														"10.00 Pm",
														"Estadio do Maracan�",
														"Group");

												database.addFixtureData(
														"Sunday, June 22, 2014",
														"South Korea",
														"Algeria", "01.00 Am",
														"Estadio Beira-Rio",
														"Group");

												database.addFixtureData(
														"Sunday, June 22, 2014",
														"United States",
														"Portugal", "04.00 Am",
														"Arena Amazonia",
														"Group");

												database.addFixtureData(
														"Monday, June 23, 2014",
														"Australia", "Spain",
														"10.00 Pm",
														"Arena da Baixada",
														"Group");

												database.addFixtureData(
														"Monday, June 23, 2014",
														"Netherlands", "Chile",
														"10.00 pm",
														"Arena Corinthians",
														"Group");

												database.addFixtureData(
														"Monday, June 23, 2014",
														"Croatia", "Mexico",
														"02.00 Am",
														"Arena Pernambuco",
														"Group");

												database.addFixtureData(
														"Monday, June 23, 2014",
														"Cameroon", "Brazil",
														"02.00 Am", "Nacional",
														"Group");

												database.addFixtureData(
														"Tuesday, June 24, 2014",
														"Italy", "Uruguay",
														"10.00 Pm",
														"Estadio das Dunas",
														"Group");

												database.addFixtureData(
														"Tuesday, June 24, 2014",
														"Costa Rica",
														"England", "10.00 pm",
														"Estadio Mineir�o",
														"Group");

												database.addFixtureData(
														"Tuesday, June 24, 2014",
														"Japan", "Colombia",
														"02.00 Am",
														"Arena Pantanal",
														"Group");

												database.addFixtureData(
														"Tuesday, June 24, 2014",
														"Greece",
														"Ivory Coast",
														"02.00 Am",
														"Estadio Castel�o",
														"Group");

												database.addFixtureData(
														"Wednesday, June 25, 2014",
														"Nigeria", "Argentina",
														"10.00 Pm",
														"Estadio Beira-Rio",
														"Group");

												database.addFixtureData(
														"Wednesday, June 25, 2014",
														"Bosnia-Herzegovina",
														"Iran", "10.00 pm",
														"Arena Fonte Nova",
														"Group");

												database.addFixtureData(
														"Wednesday, June 25, 2014",
														"Honduras",
														"Switzerland",
														"02.00 Am",
														"Arena Amazonia",
														"Group");

												database.addFixtureData(
														"Wednesday, June 25, 2014",
														"Ecuador", "France",
														"02.00 Am",
														"Estadio do Maracan�",
														"Group");

												database.addFixtureData(
														"Thursday, June 26, 2014",
														"United States",
														"Germany", "10.00 Pm",
														"Arena Pernambuco",
														"Group");

												database.addFixtureData(
														"Thursday, June 26, 2014",
														"Portugal", "Ghana",
														"10.00 pm", "Nacional",
														"Group");

												database.addFixtureData(
														"Thursday, June 26, 2014",
														"South Korea",
														"Belgium", "02.00 Am",
														"Arena Corinthians",
														"Group");

												database.addFixtureData(
														"Thursday, June 26, 2014",
														"Algeria", "Russia",
														"02.00 Am",
														"Arena da Baixada",
														"Group");

												database.addFixtureData(
														"Saturday, June 28, 2014",
														"1A", "2B", "10.00 pm",
														"Estadio Mineir�o",
														"Second Round");

												database.addFixtureData(
														"Saturday, June 28, 2014",
														"1C", "2D", "02.00 Am",
														"Estadio do Maracan�",
														"Second Round");

												database.addFixtureData(
														"Sunday, June 29, 2014",
														"1B", "2A", "10.00 pm",
														"Estadio Castel�o",
														"Second Round");

												database.addFixtureData(
														"Sunday, June 29, 2014",
														"1D", "2C", "02.00 Am",
														"Arena Pernambuco",
														"Second Round");

												database.addFixtureData(
														"Monday, June 30, 2014",
														"1E", "2F", "10.00 pm",
														"Nacional",
														"Second Round");

												database.addFixtureData(
														"Monday, June 30, 2014",
														"1G", "2H", "02.00 Am",
														"Estadio Beira-Rio",
														"Second Round");

												database.addFixtureData(
														"Tuesday, July 1, 2014",
														"1F", "2E", "10.00 pm",
														"Arena Corinthians",
														"Second Round");

												database.addFixtureData(
														"Tuesday, July 1, 2014",
														"1H", "2G", "02.00 Am",
														"Arena Fonte Nova",
														"Second Round");

												database.addFixtureData(
														"Friday, July 4, 2014",
														"Winner Match 53",
														"Winner Match 54",
														"10.00 pm",
														"Estadio do Maracan�",
														"finals");

												database.addFixtureData(
														"Friday, July 4, 2014",
														"Winner Match 49",
														"Winner Match 50",
														"02.00 Am",
														"Estadio Castel�o",
														"finals");

												database.addFixtureData(
														"Saturday, July 5, 2014",
														"Winner Match 55",
														"Winner Match 56",
														"10.00 pm", "Nacional",
														"finals");

												database.addFixtureData(
														"Saturday, July 5, 2014",
														"Winner Match 51",
														"Winner Match 52",
														"02.00 Am",
														"Arena Fonte Nova",
														"finals");

												database.addFixtureData(
														"Tuesday, July 8, 2014",
														"Winner Match 57",
														"Winner Match 58",
														"02.00 Am",
														"Estadio Mineir�o",
														"finals");

												database.addFixtureData(
														"Wednesday, July 9, 2014",
														"Winner Match 59",
														"Winner Match 60",
														"02.00 Am",
														"Arena Corinthians",
														"finals");

												database.addFixtureData(
														"Sunday, July 13, 2014",
														"Winner Match 61",
														"Winner Match 62",
														"02.00 Am",
														"Estadio do Maracan�",
														"finals");

												*//****
												 * ----------------------------
												 * ---History-------------- ----
												 * ----
												 *//*

												database.addHistoryData("1954",
														"Uruguay", "Brazil ",
														null);

												database.addHistoryData("1954",
														"West Germany",
														"Hungary", null);

												database.addHistoryData("1958",
														"Brazil ", "Uruguay",
														null);

												database.addHistoryData("1962",
														"Brazil  ",
														"Czechoslovakia", null);

												database.addHistoryData("1970",
														"Brazil", "Italy", null);

												database.addHistoryData("1974",
														"West Germany",
														"Netherlands", null);

												database.addHistoryData("1978",
														"Argentina", "Denmark",
														null);

												database.addHistoryData("1982",
														"Italy",
														"West Germany", null);

												database.addHistoryData("1986",
														"Argentina",
														"Netherlands", null);

												database.addHistoryData("1990",
														"West Germany",
														"Argentina", null);

												database.addHistoryData("1994",
														"Brazil", "Italy", null);

												database.addHistoryData("1998",
														"France", "Brazil",
														null);

												database.addHistoryData("2002",
														"Brazil", "Germany",
														null);

												database.addHistoryData("2006",
														"Italy", "French", null);

												database.addHistoryData("2010",
														"Spain", "Netherlands",
														null);

												database.close();
											} catch (Exception e) {
												e.printStackTrace();
											}

											listFixture
													.setAdapter(new FixtureAdapter(
															getActivity(),
															fixture));

											listFixture
													.setDivider(getResources()
															.getDrawable(
																	R.drawable.blk));
										}
									})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
					// show it
					alertDialog.show();
				*/}
			} else {
				Toast toast = Toast.makeText(Constants.context,
						"No internet connection!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}

	}

}
