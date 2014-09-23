package com.worldcup.javaclass;

public class Answer {

	public String qustionAnswer;
	public String qustionId;

	

	public String getQustionAnswer() {
		return qustionAnswer;
	}

	public void setQustionAnswer(String qustionAnswer) {
		this.qustionAnswer = qustionAnswer;
	}

	public String getQustionId() {
		return qustionId;
	}

	public void setQustionId(String qustionId) {
		this.qustionId = qustionId;
	}

	public Answer(String qustionId, String qustionAnswer) {
		super();
		this.qustionAnswer = qustionAnswer;
		this.qustionId = qustionId;
	}

}
