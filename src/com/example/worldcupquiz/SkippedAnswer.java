package com.example.worldcupquiz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.d3bugbd.worldcupQuiz.R;
import com.worldcup.Utils.Constants;
import com.worldcup.javaclass.Answer;
import com.worldcup.javaclass.Quiz;
import com.worldcup.javaclass.Skip;

public class SkippedAnswer extends Activity {
	RadioGroup rgOp;
	RadioButton rbOp1, rbOp2, rbOp3, rbOp4, rbOp5, rbOp6;
	Button bOk, bSkip;
	TextView tvQues;
	ProgressDialog Progress;
	public static int length, i = 0;
	List<Quiz> quiz = new ArrayList<Quiz>();
	List<Answer> ans = new ArrayList<Answer>();
	List<Skip> skip = new ArrayList<Skip>();
	int j;
	TextView skipQues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skip_answer);
		skip = Constants.skip;
		quiz = Constants.quiz;

		rgOp = (RadioGroup) findViewById(R.id.radioGroup1);
		rbOp1 = (RadioButton) findViewById(R.id.radio0);
		rbOp2 = (RadioButton) findViewById(R.id.radio1);
		rbOp3 = (RadioButton) findViewById(R.id.radio2);
		rbOp4 = (RadioButton) findViewById(R.id.radio3);
		rbOp5 = (RadioButton) findViewById(R.id.radio4);
		rbOp6 = (RadioButton) findViewById(R.id.radio5);
		bOk = (Button) findViewById(R.id.btnOk);
		skipQues = (TextView) findViewById(R.id.tvSkipQues);

		for (j = 0; j < quiz.size(); j++) {
			if (Constants.position.contentEquals(quiz.get(j).getQuesid())) {
				skipQues.setText(quiz.get(j).getQues());
				rbOp1.setText(quiz.get(j).getOp1());
				rbOp2.setText(quiz.get(j).getOp2());
				rbOp3.setText(quiz.get(j).getOp3());
				rbOp4.setText(quiz.get(j).getOp4());
				rbOp5.setText(quiz.get(j).getOp5());
				rbOp6.setText(quiz.get(j).getOp6());
				Log.e("T m", quiz.get(j).getQues());
				break;

			}
		}

		/*
		 * rbOp1.setText(quiz.get(Constants.position).getOp1());
		 * rbOp2.setText(quiz.get(Constants.position).getOp2());
		 * rbOp3.setText(quiz.get(Constants.position).getOp3());
		 * rbOp4.setText(quiz.get(Constants.position).getOp4());
		 */

		bOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Answer an = null;
				int id = rgOp.getCheckedRadioButtonId();
				View radioButton = rgOp.findViewById(id);
				int radioId = rgOp.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rgOp.getChildAt(radioId);

				an = new Answer(quiz.get(j).getQuesid(), btn.getText()
						.toString());
				Constants.ans.add(an);

				Constants.skip.remove(Constants.position_rmv);

				finish();

			}
		});

	}

}
