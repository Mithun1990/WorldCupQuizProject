package com.worldcup.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.worldcup.javaclass.Answer;
import com.worldcup.javaclass.History;
import com.worldcup.javaclass.InQuiz;
import com.worldcup.javaclass.Quiz;
import com.worldcup.javaclass.Score;
import com.worldcup.javaclass.Skip;
import com.worldcup.javaclass.ToadyMatch;
import com.worldcup.javaclass.TopScore;

public class Constants {

	public static String[] qustionAnswer;
	public static String[] qustionId;
	public static int trk;
	public static String user_name;
	public static int trk_1;
	public static int helpPos;
	public static List<Skip> skip = new ArrayList<Skip>();
	public static List<Quiz> quiz = new ArrayList<Quiz>();
	public static List<Answer> ans = new ArrayList<Answer>();
	public static String position;
	public static int position_rmv;
	public static List<History> history = new ArrayList<History>();
	public static String matchid;
	public static String quesid;
	
	public static List<ToadyMatch> today = new ArrayList<ToadyMatch>();
	
	public static ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	public static  List<InQuiz> iquiz = new ArrayList<InQuiz>();
	
	public static List<Score> scoreList = new ArrayList<Score>();
	public static List<TopScore> scoreTopList = new ArrayList<TopScore>();
	public static int trk_score;
	public static Context context;
	public static int quesWhichAnswed;
	public static int trk_submit_class;

}
