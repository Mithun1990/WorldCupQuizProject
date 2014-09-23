package com.worldcup.javaclass;

public class History {
	private String  mYear;;
	private String  mWin;
	private String  mLose;
	private String  mShoe;
	private String  des;
	public History(String mYear, String mWin, String mLose, String mShoe,String  des) {
		super();
		this.mYear = mYear;
		this.mWin = mWin;
		this.mLose = mLose;
		this.mShoe = mShoe;
		this.des =des;
	}
	public String getmYear() {
		return mYear;
	}
	public void setmYear(String mYear) {
		this.mYear = mYear;
	}
	public String getmWin() {
		return mWin;
	}
	public void setmWin(String mWin) {
		this.mWin = mWin;
	}
	public String getmLose() {
		return mLose;
	}
	public void setmLose(String mLose) {
		this.mLose = mLose;
	}
	public String getmShoe() {
		return mShoe;
	}
	
	public String getDes() {
		return des;
	}
	public void setmShoe(String mShoe) {
		this.mShoe = mShoe;
	}
	
	
	
}
