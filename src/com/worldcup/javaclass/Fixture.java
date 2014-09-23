package com.worldcup.javaclass;

public class Fixture {

	private String  mDate;;
	private String  mTeam1;
	private String  mTeam2;
	private String  mTime;
	private String  mVenue;
	private String  mRound;
	public Fixture(String mDate, String mTeam1, String mTeam2, String mTime,
			String mVenue, String  mRound) {
		super();
		this.mDate = mDate;
		this.mTeam1 = mTeam1;
		this.mTeam2 = mTeam2;
		this.mTime = mTime;
		this.mVenue = mVenue;
		this.mRound = mRound;
	}
	public String getmDate() {
		return mDate;
	}
	public void setmDate(String mDate) {
		this.mDate = mDate;
	}
	public String getmTeam1() {
		return mTeam1;
	}
	public void setmTeam1(String mTeam1) {
		this.mTeam1 = mTeam1;
	}
	public String getmTeam2() {
		return mTeam2;
	}
	public void setmTeam2(String mTeam2) {
		this.mTeam2 = mTeam2;
	}
	public String getmTime() {
		return mTime;
	}
	public void setmTime(String mTime) {
		this.mTime = mTime;
	}
	public String getmVenue() {
		return mVenue;
	}
	
	public String getmRound() {
		return mRound;
	}
	
	public void setmVenue(String mVenue) {
		this.mVenue = mVenue;
	}
	
	
	
}
