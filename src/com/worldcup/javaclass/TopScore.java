package com.worldcup.javaclass;

public class TopScore {
	String name;
	String time;
	String point;

	public TopScore(String name, String time, String point) {
		super();
		this.name = name;
		this.time = time;
		this.point = point;
	}

	public String getName() {
		return name;
	}

	public String getTime() {
		return time;
	}

	public String getPoint() {
		return point;
	}

}
