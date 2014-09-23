package com.worldcup.Utils;

import java.util.ArrayList;
import java.util.List;

import com.worldcup.javaclass.AnswerTrack;
import com.worldcup.javaclass.Fixture;
import com.worldcup.javaclass.History;
import com.worldcup.javaclass.Today;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Database {

	private static final int DATABASE_VERSION = 2;

	private static final String DATABASE_NAME = "WorldCup_manager";

	private static final String DATABASE_TABLE = "fixture";

	private static final String DATABASE_TABLE_3 = "answer";

	private static final String DATABASE_TABLE_4 = "match";

	private static final String DATABASE_TABLE_2 = "history";

	public static final String KEY_ID = "id";
	public static final String KEY_ID_2 = "id2";
	public static final String KEY_ID_3 = "id3";

	public static final String KEY_MATCH_ID = "match_id";
	public static final String KEY_QUES_ID = "ques_id";
	public static final String KEY_QUES = "ques";
	public static final String KEY_EXTRA = "extra";

	public static final String KEY_DATE = "datem";
	public static final String KEY_TEAM_1 = "team1";
	public static final String KEY_TEAM_2 = "team2";
	public static final String KEY_TIME = "timem";
	public static final String KEY_VENUE = "venue";

	public static final String KEY_YEAR = "year";
	public static final String KEY_WIN = "win";
	public static final String KEY_LOSE = "lose";
	public static final String KEY_SHOE = "shoe";
	public static final String KEY_ROUND = "round";

	public static final String KEY_ID_TRACK = "track_id";

	private DatabaseHelper ourhelper;
	private Context ourcontext;
	private SQLiteDatabase ourdatabase;

	public class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			/*
			 * db.execSQL(" CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID +
			 * " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, " +
			 * KEY_CODE + " TEXT, " + KEY_FROM + " TEXT, " + KEY_TO + " TEXT, "
			 * + KEY_NDAY + " TEXT, " + KEY_PHONE + " TEXT, " + KEY_REASON +
			 * " TEXT, " + KEY_SUPERVISOR + " TEXT );");
			 */

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE_2 + " (" + KEY_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_YEAR
					+ " TEXT, " + KEY_WIN + " TEXT, " + KEY_LOSE + " TEXT, "
					+ KEY_SHOE + " TEXT );");

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE
					+ " TEXT, " + KEY_TEAM_1 + " TEXT, " + KEY_TEAM_2
					+ " TEXT, " + KEY_TIME + " TEXT ," + KEY_VENUE + " TEXT, "
					+ KEY_ROUND + " TEXT );");

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE_3 + " (" + KEY_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MATCH_ID
					+ " TEXT, " + KEY_QUES_ID + " TEXT, " + KEY_QUES
					+ " TEXT, " + KEY_EXTRA + " TEXT );");

			db.execSQL(" CREATE TABLE " + DATABASE_TABLE_4 + " (" + KEY_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID_TRACK
					+ " TEXT );");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			if (oldVersion < newVersion) {
				db.execSQL(" CREATE TABLE " + DATABASE_TABLE_4 + " (" + KEY_ID
						+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID_TRACK
						+ " TEXT );");
			}

			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public Database(Context c) {
		ourcontext = c;
	}

	public Database(OnItemSelectedListener a) {
		// TODO Auto-generated constructor stub
		ourcontext = (Context) a;
	}

	public Database(OnItemClickListener a) {
		// TODO Auto-generated constructor stub
		ourcontext = (Context) a;
	}

	public Database open() throws SQLException {
		ourhelper = new DatabaseHelper(ourcontext);
		ourdatabase = ourhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourhelper.close();
	}

	public long addFixtureData(String a, String b, String c, String d,
			String e, String f) {

		ContentValues values = new ContentValues();

		Log.e("D", "D");

		values.put(KEY_DATE, a);
		values.put(KEY_TEAM_1, b);
		values.put(KEY_TEAM_2, c);
		values.put(KEY_TIME, d);
		values.put(KEY_VENUE, e);
		values.put(KEY_ROUND, f);

		return ourdatabase.insert(DATABASE_TABLE, null, values);

	}

	public long addAnswerData(String a, String b, String c, String d) {

		ContentValues values = new ContentValues();

		Log.e("D", "D");

		values.put(KEY_MATCH_ID, a);
		values.put(KEY_QUES_ID, b);
		values.put(KEY_QUES, c);
		values.put(KEY_EXTRA, d);

		return ourdatabase.insert(DATABASE_TABLE_3, null, values);

	}

	public long addHistoryData(String a, String b, String c, String d) {

		ContentValues values = new ContentValues();

		Log.e("D", "D");

		values.put(KEY_YEAR, a);
		values.put(KEY_WIN, b);
		values.put(KEY_LOSE, c);
		values.put(KEY_SHOE, d);

		return ourdatabase.insert(DATABASE_TABLE_2, null, values);

	}

	public long addTrackId(String a) {

		ContentValues values = new ContentValues();

		Log.e("D", "D");

		values.put(KEY_ID_TRACK, a);

		return ourdatabase.insert(DATABASE_TABLE_4, null, values);

	}

	public String[] ViewTrackId() {

		String[] cloumns = new String[] { KEY_ID_TRACK };
		Cursor cursor = ourdatabase.query(DATABASE_TABLE_4, cloumns, null,
				null, null, null, null);
		String[] str = new String[cursor.getCount()];
		int i = 0;

		int name = cursor.getColumnIndex(KEY_ID_TRACK);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			str[i] = cursor.getString(name);
			i++;

		}

		return str;
	}

	public List<Fixture> getFixtureData(String round) {

		String[] cloumns = new String[] { KEY_DATE, KEY_TEAM_1, KEY_TEAM_2,
				KEY_TIME, KEY_VENUE, KEY_ROUND };
		Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, KEY_ROUND
				+ "='" + round + "'", null, null, null, null);

		List<Fixture> fixtureList = new ArrayList<Fixture>();
		Fixture fixture;

		int dt = cursor.getColumnIndex(KEY_DATE);
		int t1 = cursor.getColumnIndex(KEY_TEAM_1);
		int t2 = cursor.getColumnIndex(KEY_TEAM_2);
		int tm = cursor.getColumnIndex(KEY_TIME);
		int vnu = cursor.getColumnIndex(KEY_VENUE);

		int rnd = cursor.getColumnIndex(KEY_ROUND);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			fixture = new Fixture(cursor.getString(dt), cursor.getString(t1),
					cursor.getString(t2), cursor.getString(tm),
					cursor.getString(vnu), cursor.getString(rnd));
			fixtureList.add(fixture);

		}

		return fixtureList;

	}

	public List<Today> getTodayFixtureData(String date) {

		String[] cloumns = new String[] { KEY_DATE, KEY_TEAM_1, KEY_TEAM_2,
				KEY_TIME, KEY_ID };
		Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, KEY_DATE
				+ "='" + date + "'", null, null, null, null);

		List<Today> fixtureList = new ArrayList<Today>();
		Today fixture;

		int dt = cursor.getColumnIndex(KEY_DATE);
		int t1 = cursor.getColumnIndex(KEY_TEAM_1);
		int t2 = cursor.getColumnIndex(KEY_TEAM_2);
		int tm = cursor.getColumnIndex(KEY_TIME);
		int vnu = cursor.getColumnIndex(KEY_ID);

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.e("D", "D1");
			fixture = new Today(cursor.getString(vnu), cursor.getString(t1),
					cursor.getString(t2), cursor.getString(dt),
					cursor.getString(tm));
			fixtureList.add(fixture);

		}

		return fixtureList;

	}

	public List<Today> getTodayFixtureDataTodayMatch(String id) {

		String[] cloumns = new String[] { KEY_DATE, KEY_TEAM_1, KEY_TEAM_2,
				KEY_TIME, KEY_ID };
		Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, KEY_ID
				+ "='" + id + "'", null, null, null, null);

		List<Today> fixtureList = new ArrayList<Today>();
		Today fixture;

		int dt = cursor.getColumnIndex(KEY_DATE);
		int t1 = cursor.getColumnIndex(KEY_TEAM_1);
		int t2 = cursor.getColumnIndex(KEY_TEAM_2);
		int tm = cursor.getColumnIndex(KEY_TIME);
		int vnu = cursor.getColumnIndex(KEY_ID);

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.e("D", "D1");
			fixture = new Today(cursor.getString(vnu), cursor.getString(t1),
					cursor.getString(t2), cursor.getString(dt),
					cursor.getString(tm));
			fixtureList.add(fixture);

		}

		return fixtureList;

	}

	public List<AnswerTrack> getAnswerData(String date) {

		String[] cloumns = new String[] { KEY_QUES_ID, KEY_QUES, KEY_EXTRA };
		Cursor cursor = ourdatabase.query(DATABASE_TABLE_3, cloumns,
				KEY_MATCH_ID + "='" + date + "'", null, null, null, null);

		List<AnswerTrack> fixtureList = new ArrayList<AnswerTrack>();
		AnswerTrack fixture;

		int dt = cursor.getColumnIndex(KEY_QUES_ID);
		int t1 = cursor.getColumnIndex(KEY_QUES);
		int t2 = cursor.getColumnIndex(KEY_EXTRA);

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.e("D", "D1");
			fixture = new AnswerTrack(null, cursor.getString(dt),
					cursor.getString(t1), cursor.getString(t2));
			fixtureList.add(fixture);

		}

		return fixtureList;

	}

	public List<History> getHistoryData() {

		String[] cloumns = new String[] { KEY_YEAR, KEY_WIN, KEY_LOSE };
		Cursor cursor = ourdatabase.query(DATABASE_TABLE_2, cloumns, null,
				null, null, null, null);

		List<History> historyList = new ArrayList<History>();
		History history;

		int yr = cursor.getColumnIndex(KEY_YEAR);
		int wn = cursor.getColumnIndex(KEY_WIN);
		int ls = cursor.getColumnIndex(KEY_LOSE);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			history = new History(cursor.getString(yr), cursor.getString(wn),
					cursor.getString(ls), null, null);
			historyList.add(history);

		}

		return historyList;

	}

	public String getMatch(String id) {

		String[] cloumns = new String[] { KEY_TEAM_1, KEY_TEAM_2 };
		Cursor cursor = ourdatabase.query(DATABASE_TABLE, cloumns, KEY_ID
				+ "='" + id + "'", null, null, null, null);

		List<Today> fixtureList = new ArrayList<Today>();
		Today fixture;
		String result = null;

		int t1 = cursor.getColumnIndex(KEY_TEAM_1);
		int t2 = cursor.getColumnIndex(KEY_TEAM_2);

		Log.e("D", "D12");

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			result = cursor.getString(t1) + "   Vs   " + cursor.getString(t2);
		}
		return result;
	}

	/*
	 * public long addApprovalId(int a) {
	 * 
	 * ContentValues values = new ContentValues(); Log.e("a", a + "");
	 * values.put(KEY_APPROVAL, a);
	 * 
	 * Log.e("H", "H");
	 * 
	 * return ourdatabase.insert(DATABASE_TABLE_2, null, values);
	 * 
	 * }
	 * 
	 * public long addItem(String item) {
	 * 
	 * ContentValues values = new ContentValues(); Log.e("a", item);
	 * values.put(KEY_CATEGORY, item);
	 * 
	 * Log.e("H", "H");
	 * 
	 * return ourdatabase.insert(DATABASE_TABLE_2, null, values);
	 * 
	 * }
	 * 
	 * public int[] ViewApproval() { String[] cloumns = new String[] {
	 * KEY_APPROVAL }; Cursor cursor = ourdatabase.query(DATABASE_TABLE_2,
	 * cloumns, null, null, null, null, null); int[] str = new
	 * int[cursor.getCount()]; int i = 0;
	 * 
	 * int catgry = cursor.getColumnIndex(KEY_APPROVAL);
	 * 
	 * for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
	 * 
	 * str[i] = cursor.getInt(catgry);
	 * 
	 * i++; }
	 * 
	 * return str; }
	 * 
	 * public String[] ViewLeaveInformation() { String[] cloumns = new String[]
	 * { KEY_NAME, KEY_FROM, KEY_TO, KEY_NDAY, KEY_REASON }; Cursor cursor =
	 * ourdatabase.query(DATABASE_TABLE, cloumns, null, null, null, null,
	 * KEY_NAME + " ASC"); String[] str = new String[cursor.getCount()]; int i =
	 * 0;
	 * 
	 * int name = cursor.getColumnIndex(KEY_NAME); int from =
	 * cursor.getColumnIndex(KEY_FROM); int to = cursor.getColumnIndex(KEY_TO);
	 * int nday = cursor.getColumnIndex(KEY_NDAY); int reason =
	 * cursor.getColumnIndex(KEY_REASON);
	 * 
	 * for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
	 * 
	 * str[i] = "Employee Name:  " + cursor.getString(name) + "\n" +
	 * "Leave From:  " + cursor.getString(from) + "-" + cursor.getString(to) +
	 * "\n" + "Number of days:  " + cursor.getString(nday) + "\n" + "Reason:  "
	 * + cursor.getString(reason) + "\n............................\n";
	 * 
	 * i++;
	 * 
	 * }
	 * 
	 * return str; }
	 */
	public void deleteAll() {

		ourdatabase.delete(DATABASE_TABLE, null, null);

	}

	public void deleteMatch(String id) {

		ourdatabase.delete(DATABASE_TABLE_3, KEY_MATCH_ID + "='" + id + "'",
				null);

	}

}
