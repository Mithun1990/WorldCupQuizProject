package com.worldcup.Utils;

public class DataStore {

	String match_id;
	String ques_id;
	String ques;
	String extra;

	public DataStore(String match_id, String ques_id, String ques, String extra) {
		super();
		this.match_id = match_id;
		this.ques_id = ques_id;
		this.ques = ques;
		this.extra = extra;
	}

	public String getMatch_id() {
		return match_id;
	}

	public String getQues_id() {
		return ques_id;
	}

	public String getQues() {
		return ques;
	}

	public String getExtra() {
		return extra;
	}

}
