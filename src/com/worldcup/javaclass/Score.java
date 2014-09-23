package com.worldcup.javaclass;

public class Score {
	
	String date;
	String num_ques;
	String score;
	public Score(String date, String num_ques, String score) {
		super();
		this.date = date;
		this.num_ques = num_ques;
		this.score = score;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNum_ques() {
		return num_ques;
	}
	public void setNum_ques(String num_ques) {
		this.num_ques = num_ques;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}

	
	
}
