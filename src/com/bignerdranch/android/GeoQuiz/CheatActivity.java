package com.bignerdranch.android.GeoQuiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: xdai
 * Date: 11/4/13
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheatActivity extends Activity {
    public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.GeoQuiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.GeoQuiz.answer_shown";
    private boolean answerIsTrue;
    private TextView  answerTextView;
    private Button answerButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        answerTextView = (TextView)findViewById(R.id.answerTextview);
        answerButton = (Button)findViewById(R.id.showAnswerButton);
        answerButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if(answerIsTrue)
                            answerTextView.setText(R.string.true_button);
                        else
                            answerTextView.setText(R.string.false_button);
                        setAnswerShownResult(true);
                    }
                });

    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
