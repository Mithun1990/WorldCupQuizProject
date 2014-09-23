package com.tourism.tabadapter;

import com.example.worldcupquiz.FixtureFragment;
import com.example.worldcupquiz.HistoryFragment;
import com.example.worldcupquiz.HomePageFragment;
import com.example.worldcupquiz.LeaderFragment;
import com.example.worldcupquiz.QuizFragment;
import com.example.worldcupquiz.TopScorer;
import com.tourism.menudrawer.MainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		android.support.v4.app.Fragment frag = null;
		switch (i) {
		case 0:
			// Fragement for Home Tab

			frag = new HomePageFragment();
			break;
		case 1:
			frag = new QuizFragment();

			break;

		case 2:
			// Fragment for Map Tab
			frag = new FixtureFragment();
			break;
		case 3:
			// Fragment for Map Tab
			frag = new HistoryFragment();
			break;
		case 4:
			// Fragment for Map Tab
			frag = new LeaderFragment();
			break;
			
		case 5:
			// Fragment for Map Tab
			frag = new TopScorer();
			break;

		default:
			break;

		}

		return frag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6; // How many do we want
	}

}
