package com.tourism.menudrawer;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import com.actionbarsherlock.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.d3bugbd.worldcupQuiz.R;
import com.tourism.tabadapter.TabPagerAdapter;
import com.worldcup.Utils.Constants;
import com.worldcup.Utils.Database;
import com.worldcup.Utils.DetectConnection;
import com.worldcup.Utils.NotifyService;
import com.worldcup.Utils.WebViewData;
import com.worldcup.javaclass.Answer;
import com.worldcup.javaclass.Fixture;
import com.worldcup.javaclass.Quiz;
import com.worldcup.javaclass.Skip;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends SherlockFragmentActivity implements
		OnTabChangeListener {
	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	ViewPager Tab;
	TabPagerAdapter TabAdapter;
	com.actionbarsherlock.app.ActionBar actionBar;
	SharedPreferences prf;
	public static MenuInflater inflater = null;
	public static MenuItem item;
	Intent intestTostartService;
	public static Menu menu = null;

	ListView listFixture;

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

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);

		/** Adapter For Setting Tab */

		TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
		Tab = (ViewPager) findViewById(R.id.pager);

		/** Page Change Listener using swiping */

		Tab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@SuppressLint("NewApi")
			@Override
			public void onPageSelected(int position) {
				actionBar = getSupportActionBar();
				actionBar.setSelectedNavigationItem(position);
			}
		});
		Tab.setAdapter(TabAdapter);
		actionBar = getSupportActionBar();

		// Enable Tabs on Action Bar

		actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#034c5f")));

		// actionBar.setStackedBackgroundDrawable(getResources().getDrawable(R.drawable.tab_bg));
		// actionBar.setDisplayShowCustomEnabled(true);
		// actionBar.setSplitBackgroundDrawable(new ColorDrawable(Color.BLACK));
		Constants.context = this;

		prefs = PreferenceManager
				.getDefaultSharedPreferences(Constants.context);
		get = prefs.getInt("fixtureTrack", 0);
		if (get == 0) {

			database = new Database(Constants.context);
			database.open();

			try {
				database.addFixtureData("13-Jun-2014", "Brazil", "Croatia",
						"02.00 Am", "Arena Corinthians", "Group");

				database.addFixtureData("13-Jun-2014", "Mexico", "Cameroon",
						"10.00 Pm", "Estadio das Dunas", "Group");

				database.addFixtureData("14-Jun-2014", "Spain", "Netherlands",
						"01.00 Am", "Arena Fonte Nova", "Group");

				database.addFixtureData("14-Jun-2014", "Chile", "Australia",
						"04.00 Am", "Arena Pantanal", "Group");

				database.addFixtureData("14-Jun-2014", "Colombia", "Greece",
						"10.00 Pm", "Estadio Mineirao", "Group");

				database.addFixtureData("15-Jun-2014", "Uruguay", "Costa Rica",
						"01.00 Am", "Estadio Castelao", "Group");

				database.addFixtureData("15-Jun-2014", "England", "Italy",
						"04.00 Am", "Arena Amazonia", "Group");

				database.addFixtureData("15-Jun-2014", "Ivory Coast", "Japan",
						"07.00 Am", "Arena Pernambuco", "Group");

				database.addFixtureData("15-Jun-2014", "Switzerland",
						"Ecuador", "10.00 Pm", "Nacional", "Group");

				database.addFixtureData("16-Jun-2014", "France", "Honduras",
						"01.00 Am", "Estadio Beira-Rio", "Group");

				database.addFixtureData("16-Jun-2014", "Argentina",
						"Bosnia-Herzegovina", "04.00 Am",
						"Estadio do Maracana", "Group");

				database.addFixtureData("16-Jun-2014", "Germany", "Portugal",
						"10.00 Pm", "Arena Fonte Nova", "Group");

				database.addFixtureData("17-Jun-2014", "Iran", "Nigeria",
						"01.00 Am", "Arena da Baixada", "Group");

				database.addFixtureData("17-Jun-2014", "United States",
						"Ghana", "04.00 Am", "Estadio das Dunas", "Group");

				database.addFixtureData("17-Jun-2014", "Belgium", "Algeria",
						"10.00 Pm", "Estadio Mineirao", "Group");

				database.addFixtureData("18-Jun-2014", "Brazil", "Mexico",
						"01.00 Am", "Estadio Castelao", "Group");

				database.addFixtureData("18-Jun-2014", "Russia", "South Korea",
						"04.00 Am", "Arena Pantanal", "Group");

				database.addFixtureData("18-Jun-2014", "Australia",
						"Netherlands", "10.00 Pm", "Estadio Beira-Rio", "Group");

				database.addFixtureData("19-Jun-2014", "Spain", "Chile",
						"01.00 Am", "Estadio do Maracana", "Group");

				database.addFixtureData("19-Jun-2014", "Cameroon", "Croatia",
						"04.00 Am", "Arena Amazonia", "Group");

				database.addFixtureData("19-Jun-2014", "Colombia",
						"Ivory Coast", "10.00 Pm", "Nacional", "Group");

				database.addFixtureData("20-Jun-2014", "Uruguay", "England",
						"01.00 Am", "Arena Corinthians", "Group");

				database.addFixtureData("20-Jun-2014", "Japan", "Greece",
						"04.00 Am", "Estadio das Dunas", "Group");

				database.addFixtureData("20-Jun-2014", "Italy", "Costa Rica",
						"10.00 Pm", "Arena Pernambuco", "Group");

				database.addFixtureData("21-Jun-2014", "Switzerland", "France",
						"01.00 Am", "Arena Fonte Nova", "Group");

				database.addFixtureData("21-Jun-2014", "Honduras", "Ecuador",
						"04.00 Am", "Arena da Baixada", "Group");

				database.addFixtureData("21-Jun-2014", "Argentina", "Iran",
						"10.00 Pm", "Estadio Mineirao", "Group");

				database.addFixtureData("22-Jun-2014", "Germany", "Ghana",
						"01.00 Am", "Estadio Castelao", "Group");

				database.addFixtureData("22-Jun-2014", "Nigeria",
						"Bosnia-Herzegovina", "04.00 Am", "Arena Pantanal",
						"Group");

				/*
				 * database.addFixtureData("21-Jun-2014", "Italy", "Costa Rica",
				 * "10.00 Pm", "Arena Pernambuco", "Group");
				 * 
				 * database.addFixtureData("22-Jun-2014", "Switzerland",
				 * "France", "01.00 Am", "Arena Fonte Nova", "Group");
				 * 
				 * database.addFixtureData("22-Jun-2014", "Honduras", "Ecuador",
				 * "04.00 Am", "Arena da Baixada", "Group");
				 * 
				 * database.addFixtureData("22-Jun-2014", "Argentina", "Iran",
				 * "10.00 Pm", "Estadio Mineirao", "Group");
				 * 
				 * database.addFixtureData("23-Jun-2014", "Germany", "Ghana",
				 * "01.00 Am", "Estadio Castelao", "Group");
				 * 
				 * database.addFixtureData("23-Jun-2014", "Nigeria",
				 * "Bosnia-Herzegovina", "04.00 Am", "Arena Pantanal", "Group");
				 */

				database.addFixtureData("22-Jun-2014", "Belgium", "Russia",
						"10.00 Pm", "Estadio do Maracana", "Group");

				database.addFixtureData("23-Jun-2014", "South Korea",
						"Algeria", "01.00 Am", "Estadio Beira-Rio", "Group");

				database.addFixtureData("23-Jun-2014", "United States",
						"Portugal", "04.00 Am", "Arena Amazonia", "Group");

				database.addFixtureData("23-Jun-2014", "Australia", "Spain",
						"10.00 Pm", "Arena da Baixada", "Group");

				database.addFixtureData("23-Jun-2014", "Netherlands", "Chile",
						"10.00 pm", "Arena Corinthians", "Group");

				database.addFixtureData("24-Jun-2014", "Croatia", "Mexico",
						"02.00 Am", "Arena Pernambuco", "Group");

				database.addFixtureData("24-Jun-2014", "Cameroon", "Brazil",
						"02.00 Am", "Nacional", "Group");

				database.addFixtureData("24-Jun-2014", "Italy", "Uruguay",
						"10.00 Pm", "Estadio das Dunas", "Group");

				database.addFixtureData("24-Jun-2014", "Costa Rica", "England",
						"10.00 pm", "Estadio Mineirao", "Group");

				database.addFixtureData("25-Jun-2014", "Japan", "Colombia",
						"02.00 Am", "Arena Pantanal", "Group");

				database.addFixtureData("25-Jun-2014", "Greece", "Ivory Coast",
						"02.00 Am", "Estadio Castelao", "Group");

				database.addFixtureData("25-Jun-2014", "Nigeria", "Argentina",
						"10.00 Pm", "Estadio Beira-Rio", "Group");

				database.addFixtureData("25-Jun-2014", "Bosnia-Herzegovina",
						"Iran", "10.00 pm", "Arena Fonte Nova", "Group");

				database.addFixtureData("26-Jun-2014", "Honduras",
						"Switzerland", "02.00 Am", "Arena Amazonia", "Group");

				database.addFixtureData("26-Jun-2014", "Ecuador", "France",
						"02.00 Am", "Estadio do Maracana", "Group");

				database.addFixtureData("26-Jun-2014", "United States",
						"Germany", "10.00 Pm", "Arena Pernambuco", "Group");

				database.addFixtureData("26-Jun-2014", "Portugal", "Ghana",
						"10.00 pm", "Nacional", "Group");

				database.addFixtureData("27-Jun-2014", "South Korea",
						"Belgium", "02.00 Am", "Arena Corinthians", "Group");

				database.addFixtureData("27-Jun-2014", "Algeria", "Russia",
						"02.00 Am", "Arena da Baixada", "Group");

				database.addFixtureData("28-Jun-2014", "1A", "2B", "10.00 pm",
						"Estadio Mineirao", "Second Round");

				database.addFixtureData("29-Jun-2014", "1C", "2D", "02.00 Am",
						"Estadio do Maracana", "Second Round");

				database.addFixtureData("29-Jun-2014", "1B", "2A", "10.00 pm",
						"Estadio Castelao", "Second Round");

				database.addFixtureData("30-Jun-2014", "1D", "2C", "02.00 Am",
						"Arena Pernambuco", "Second Round");

				database.addFixtureData("30-Jun-2014", "1E", "2F", "10.00 pm",
						"Nacional", "Second Round");

				database.addFixtureData("01-Jul-2014", "1G", "2H", "02.00 Am",
						"Estadio Beira-Rio", "Second Round");

				database.addFixtureData("01-Jul-2014", "1F", "2E", "10.00 pm",
						"Arena Corinthians", "Second Round");

				database.addFixtureData("02-Jul-2014", "1H", "2G", "02.00 Am",
						"Arena Fonte Nova", "Second Round");

				database.addFixtureData("04-Jul-2014", "Winner Match 53",
						"Winner Match 54", "10.00 pm", "Estadio do Maracana",
						"finals");

				database.addFixtureData("05-Jul-2014", "Winner Match 49",
						"Winner Match 50", "02.00 Am", "Estadio Castelao",
						"finals");

				database.addFixtureData("05-Jul-2014", "Winner Match 55",
						"Winner Match 56", "10.00 pm", "Nacional", "finals");

				database.addFixtureData("06-Jul-2014", "Winner Match 51",
						"Winner Match 52", "02.00 Am", "Arena Fonte Nova",
						"finals");

				database.addFixtureData("09-Jul-2014", "Winner Match 57",
						"Winner Match 58", "02.00 Am", "Estadio Mineirao",
						"finals");

				database.addFixtureData("10-Jul-2014", "Winner Match 59",
						"Winner Match 60", "02.00 Am", "Arena Corinthians",
						"finals");

				database.addFixtureData("14-Jul-2014", "Winner Match 61",
						"Winner Match 62", "02.00 Am", "Estadio do Maracana",
						"finals");

				/****
				 * -------------------------------History-------------- ----
				 * ----
				 */

				database.addHistoryData("1954", "Uruguay", "Brazil ", null);

				database.addHistoryData("1954", "West Germany", "Hungary", null);

				database.addHistoryData("1958", "Brazil ", "Uruguay", null);

				database.addHistoryData("1962", "Brazil  ", "Czechoslovakia",
						null);

				database.addHistoryData("1970", "Brazil", "Italy", null);

				database.addHistoryData("1974", "West Germany", "Netherlands",
						null);

				database.addHistoryData("1978", "Argentina", "Denmark", null);

				database.addHistoryData("1982", "Italy", "West Germany", null);

				database.addHistoryData("1986", "Argentina", "Netherlands",
						null);

				database.addHistoryData("1990", "West Germany", "Argentina",
						null);

				database.addHistoryData("1994", "Brazil", "Italy", null);

				database.addHistoryData("1998", "France", "Brazil", null);

				database.addHistoryData("2002", "Brazil", "Germany", null);

				database.addHistoryData("2006", "Italy", "French", null);

				database.addHistoryData("2010", "Spain", "Netherlands", null);

				database.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Editor ed = prefs.edit();
				ed.putInt("fixtureTrack", 1);
				ed.commit();

			}
		}

		Intent in = new Intent(this, NotifyService.class);
		startService(in);

		Log.e("YYY MMM", "YYY MMM");

		/** Enable Tabs on Action Bar */

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			// TODO Auto-generated method stub

			@Override
			public void onTabSelected(
					com.actionbarsherlock.app.ActionBar.Tab tab,
					android.support.v4.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				Tab.setCurrentItem(tab.getPosition());

			}

			@Override
			public void onTabUnselected(
					com.actionbarsherlock.app.ActionBar.Tab tab,
					android.support.v4.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabReselected(
					com.actionbarsherlock.app.ActionBar.Tab tab,
					android.support.v4.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

		};

		LayoutInflater infl = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = infl
				.inflate(R.layout.custom_tab, null);

		LayoutInflater infl1 = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView1 = infl1.inflate(R.layout.custom_tab1,
				null);

		LayoutInflater infl2 = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView2 = infl2.inflate(R.layout.custom_tab2,
				null);

		LayoutInflater infl3 = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView3 = infl3.inflate(R.layout.custom_tab3,
				null);

		LayoutInflater infl4 = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView4 = infl4.inflate(R.layout.custom_tab4,
				null);
		
		LayoutInflater infl5 = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView5 = infl5.inflate(R.layout.custom_tab5,
				null);

		ImageView im = (ImageView) customActionBarView.findViewById(R.id.icon);
		im.setImageDrawable(getResources().getDrawable(R.drawable.home));

		ImageView im1 = (ImageView) customActionBarView1
				.findViewById(R.id.icon1);
		im1.setImageDrawable(getResources().getDrawable(R.drawable.quiz));

		ImageView im2 = (ImageView) customActionBarView2
				.findViewById(R.id.icon2);
		im2.setImageDrawable(getResources().getDrawable(R.drawable.fixture));

		ImageView im3 = (ImageView) customActionBarView3
				.findViewById(R.id.icon3);
		im3.setImageDrawable(getResources().getDrawable(R.drawable.history));

		ImageView im4 = (ImageView) customActionBarView4
				.findViewById(R.id.icon4);
		im4.setImageDrawable(getResources().getDrawable(R.drawable.score));
		
		ImageView im5 = (ImageView) customActionBarView5
				.findViewById(R.id.icon5);
		im5.setImageDrawable(getResources().getDrawable(R.drawable.leader));

		/** Add new Tab */

		actionBar.addTab(actionBar.newTab().setCustomView(customActionBarView)

		.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setCustomView(customActionBarView1)
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setCustomView(customActionBarView2)
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setCustomView(customActionBarView3)
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setCustomView(customActionBarView4)
				.setTabListener(tabListener));
		
		actionBar.addTab(actionBar.newTab().setCustomView(customActionBarView5)
				.setTabListener(tabListener));

		// set drawer title
		/*
		 * mTitle = mDrawerTitle = getTitle();
		 * 
		 * // load slide menu items navMenuTitles =
		 * getResources().getStringArray(R.array.nav_drawer_items);
		 * 
		 * // nav drawer icons from resources
		 * 
		 * navMenuIcons = getResources()
		 * .obtainTypedArray(R.array.nav_drawer_icons);
		 * 
		 * mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		 * mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		 * 
		 * navDrawerItems = new ArrayList<NavDrawerItem>();
		 * 
		 * // adding nav drawer items to array // Home navDrawerItems.add(new
		 * NavDrawerItem(navMenuTitles[0], navMenuIcons .getResourceId(0, -1)));
		 * // Leave Application navDrawerItems.add(new
		 * NavDrawerItem(navMenuTitles[1], navMenuIcons .getResourceId(1, -1)));
		 * // Map navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
		 * navMenuIcons .getResourceId(2, -1)));
		 * 
		 * navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
		 * .getResourceId(3, -1)));
		 * 
		 * navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
		 * .getResourceId(4, -1)));
		 * 
		 * // Recycle the typed array navMenuIcons.recycle();
		 * 
		 * this.mDrawerList.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @SuppressWarnings("unused")
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2, long arg3) { // TODO Auto-generated method stub
		 * 
		 * Log.e("C", "C11");
		 * 
		 * android.support.v4.app.Fragment fragment = null; switch (arg2) {
		 * 
		 * case 0: Log.e("C", "C3"); // set tab for selecting drawer item
		 * Tab.setCurrentItem(0); mDrawerLayout.closeDrawer(mDrawerList); //
		 * close drawer when // selected break; case 1: // set tab for selecting
		 * drawer item Tab.setCurrentItem(1);
		 * mDrawerLayout.closeDrawer(mDrawerList);// close drawer when //
		 * selected break; case 2: // set tab for selecting drawer item
		 * Tab.setCurrentItem(2); mDrawerLayout.closeDrawer(mDrawerList);//
		 * close drawer when // selected break;
		 * 
		 * case 3: // set tab for selecting drawer item Tab.setCurrentItem(3);
		 * mDrawerLayout.closeDrawer(mDrawerList);// close drawer when //
		 * selected break;
		 * 
		 * case 4: // set tab for selecting drawer item Tab.setCurrentItem(4);
		 * mDrawerLayout.closeDrawer(mDrawerList);// close drawer when //
		 * selected break;
		 * 
		 * 
		 * default: break; }
		 * 
		 * } });
		 * 
		 * // setting the nav drawer list adapter adapter = new
		 * NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		 * mDrawerList.setAdapter(adapter);
		 * 
		 * // enabling action bar app icon and behaving it as toggle button
		 * getActionBar().setDisplayHomeAsUpEnabled(true);
		 * getActionBar().setHomeButtonEnabled(true);
		 * 
		 * mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
		 * R.drawable.ic_drawer, // nav menu toggle icon R.string.app_name, //
		 * nav drawer open - description for // accessibility R.string.app_name
		 * // nav drawer close - description for // accessibility ) { public
		 * void onDrawerClosed(View view) {
		 * 
		 * getActionBar().setTitle(mTitle); // calling onPrepareOptionsMenu() to
		 * show action bar icons invalidateOptionsMenu(); Log.e("C", "C7"); }
		 * 
		 * public void onDrawerOpened(View drawerView) {
		 * getActionBar().setTitle(mDrawerTitle); Log.e("C", "C8"); // calling
		 * onPrepareOptionsMenu() to hide action bar icons
		 * invalidateOptionsMenu(); } };
		 * mDrawerLayout.setDrawerListener(mDrawerToggle);
		 * 
		 * if (savedInstanceState == null) { // on first time display view for
		 * first nav item displayView(0); Log.e("C", "C5"); }
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub

		inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);

		menu.findItem(R.id.action_settings).setVisible(true);

		return true;
	}

	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) { // respond to menu

		switch (item.getItemId()) {

		case R.id.action_settings: // show leave information from local database

			if (DetectConnection.checkInternetConnection(Constants.context)) {
				Intent in = new Intent(MainActivity.this, WebViewData.class);
				startActivity(in);
			} else {
				Toast.makeText(Constants.context, "No internet Connection!",
						Toast.LENGTH_LONG).show();
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub

	}

	public void tabChanged() {

		menu.findItem(R.id.action_settings).setVisible(true);
	}

	/*
	 * @Override public boolean onPrepareOptionsMenu(Menu menu) { // if nav
	 * drawer is opened, hide the action items boolean drawerOpen =
	 * mDrawerLayout.isDrawerOpen(mDrawerList); //
	 * menu.findItem(R.id.action_settings).setVisible(!drawerOpen); return
	 * super.onPrepareOptionsMenu(menu); }
	 */

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	/*
	 * @Override protected void onPostCreate(Bundle savedInstanceState) {
	 * super.onPostCreate(savedInstanceState); // Sync the toggle state after
	 * onRestoreInstanceState has occurred. mDrawerToggle.syncState(); }
	 * 
	 * @Override public void onConfigurationChanged(Configuration newConfig) {
	 * super.onConfigurationChanged(newConfig); // Pass any configuration change
	 * to the drawer toggls mDrawerToggle.onConfigurationChanged(newConfig); }
	 */

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		// alertDialogBuilder.setTitle("Your Title");

		// set dialog message
		alertDialogBuilder
				.setMessage("Do you want to exit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								System.exit(0);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resutlCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resutlCode, data);
		Log.e("T", requestCode + "");
		Log.e("T", resutlCode + "");
		if (requestCode == 1) {
			Log.e("T22", Constants.trk_submit_class + "");
			if (Constants.trk_submit_class == 1) {
				Log.e("T23", "Tb0");
				Log.e("T", Constants.trk_submit_class + "");
				Tab.setCurrentItem(0);
				Constants.trk_submit_class = 0;
			}

		}
	}

}
