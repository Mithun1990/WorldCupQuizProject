package com.example.worldcupquiz;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.d3bugbd.worldcupQuiz.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.tourism.adapters.HistoryAdapter;
import com.worldcup.Utils.Constants;
import com.worldcup.Utils.Database;
import com.worldcup.javaclass.History;

public class HistoryFragment extends Fragment {

	ListView listHistory;

	SharedPreferences prf;
	Database database;
	int get;
	List<History> history = new ArrayList<History>();
	SharedPreferences prefs;
	ProgressDialog Progress;
	String path;

	public HistoryFragment() {
		// TODO Auto-generated constructor stub
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = View.inflate(getActivity(), R.layout.history_main, null);

		path = "json/history.txt";

		listHistory = (ListView) v.findViewById(R.id.lvHistory);

		/*
		 * database = new Database(getActivity()); database.open(); history =
		 * database.getHistoryData(); database.close();
		 */
		
		AdView adView = new AdView(getActivity(), AdSize.BANNER,  getResources().getString(R.string.add_id));

		RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.ad);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
		
		/** When clicked on login button go to the login page */

		return v;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public class GetHisroryData extends AsyncTask<Void, String, String> {

		@Override
		protected void onPreExecute() { // TODO Auto-generated method stub
			super.onPreExecute();

			Progress.setMessage("Fetching History Data...");
			Progress.setCancelable(false);
			Progress.show();
		}

		@Override
		protected String doInBackground(Void... params) {

			try {
				// Creating JSONObject from String
				String jsonString = null;
				try {
					jsonString = AssetJSONFile(path, Constants.context);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Log.d("json", jsonString);

				JSONObject jsonObjMain = new JSONObject(jsonString);

				// Creating JSONArray from JSONObject
				JSONArray jsonArray = jsonObjMain.getJSONArray("history");
				history.clear();
				// JSONArray has four JSONObject
				for (int i = 0; i < jsonArray.length(); i++) {

					// Creating JSONObject from JSONArray
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					// Getting data from individual JSONObject
					String year = jsonObj.getString("year");
					String win = jsonObj.getString("win");
					String lose = jsonObj.getString("lose");
					String player = jsonObj.getString("extra");
					String des = jsonObj.getString("des");

					History his = new History(year, win, lose, player,des);
					history.add(his);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			 Progress.cancel();
			Constants.history = history;
			
			listHistory.setAdapter(new HistoryAdapter(getActivity(), history));
			//listHistory.setDivider(getResources().getDrawable(R.drawable.blk));

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

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		
		if(isVisibleToUser){
			path = "json/history.txt";
			Progress = new ProgressDialog(Constants.context);

			GetHisroryData ht = new GetHisroryData();
			ht.execute();

		}
	}
	
	

}
