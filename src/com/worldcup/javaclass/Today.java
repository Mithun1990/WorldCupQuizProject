package com.worldcup.javaclass;

public class Today {
	
	String matchid;
	String team1;
	String team2;
	String date;
	String time;
	public Today(String matchid, String team1, String team2, String date,
			String time) {
		super();
		this.matchid = matchid;
		this.team1 = team1;
		this.team2 = team2;
		this.date = date;
		this.time = time;
	}
	public String getMatchid() {
		return matchid;
	}
	public String getTeam1() {
		return team1;
	}
	public String getTeam2() {
		return team2;
	}
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	

}
