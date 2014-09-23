package com.worldcup.javaclass;

public class InQuiz {

	String quesid;
	String ques;
	String op1;
	String op2;
	String op3;
	String op4;
	String op5;
	String op6;

	public InQuiz(String quesid, String ques, String op1, String op2,
			String op3, String op4, String op5, String op6) {
		super();
		this.quesid = quesid;
		this.ques = ques;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.op4 = op4;
		this.op5 = op5;
		this.op6 = op6;
	}

	public String getQuesid() {
		return quesid;
	}

	public void setQuesid(String quesid) {
		this.quesid = quesid;
	}

	public String getQues() {
		return ques;
	}

	public void setQues(String ques) {
		this.ques = ques;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
		this.op2 = op2;
	}

	public String getOp3() {
		return op3;
	}

	public void setOp3(String op3) {
		this.op3 = op3;
	}

	public String getOp4() {
		return op4;
	}

	public String getOp5() {
		return op5;
	}

	public String getOp6() {
		return op6;
	}

}
