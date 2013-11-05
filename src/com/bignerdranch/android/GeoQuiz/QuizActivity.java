package com.bignerdranch.android.GeoQuiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.app.ActionBar;
public class QuizActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button prevButton;
    private Button cheatButton;
    private TextView questionTextView;
    private TrueFalse[] questionBank = new TrueFalse[]{
                 new TrueFalse(R.string.question_oceans, true),
                 new TrueFalse(R.string.question_africa, false),
                 new TrueFalse(R.string.question_americas, true),
                 new TrueFalse(R.string.question_mideast, false)
         } ;

    private int currentIndex =0;

    private static final String TAG="QuizActivity";
    private static final String KEY_INDEX = "index";
    private boolean isCheater;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_quiz);
        trueButton = (Button)findViewById(R.id.true_button);
        falseButton =(Button)findViewById(R.id.false_button);
        questionTextView = (TextView)findViewById(R.id.question_text_view) ;

        nextButton = (Button)findViewById(R.id.next_button);
        prevButton = (Button)findViewById(R.id.prev_button);

        cheatButton = (Button)findViewById(R.id.cheat_button);
        trueButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                checkAnswer(true);
               // Toast.makeText(QuizActivity.this, R.string.correct_toast,Toast.LENGTH_SHORT).show();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener()
            {
            public void onClick(View view) {
                checkAnswer(false);
                //Toast.makeText(QuizActivity.this, R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                currentIndex = (currentIndex +1)% questionBank.length;
                isCheater = false;
                updateQuestion();
            }
            });

        prevButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                currentIndex = currentIndex ==0 ? questionBank.length : currentIndex;
                currentIndex = (currentIndex - 1)% questionBank.length;
                isCheater = false;
                updateQuestion();
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = questionBank[currentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i,0);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            ActionBar actionBar = getActionBar();
            actionBar.setSubtitle("Bodies of Water");

        }

        if(savedInstanceState != null)
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        updateQuestion();
    }

    public void onStart(){
        super.onStart();
        Log.d(TAG, "OnStart() called");

    }
    public void onPause(){
          super.onPause();
          Log.d(TAG, "onPause() called");

      }
    public void onResume(){
          super.onResume();
          Log.d(TAG, "onResume() called");

      }
    public void onStop(){
           super.onStop();
           Log.d(TAG, "onStop() called");

       }
       public void onDestroy(){
             super.onDestroy();
             Log.d(TAG, "onDestroy() called");

         }

    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null)
            return;

        Log.d(TAG, "requestCode" + requestCode);
        Log.d(TAG, "resultCode" + resultCode);
        isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,false);
    }

      private void updateQuestion(){
          int question = questionBank[currentIndex].getQuestion();
          questionTextView.setText(question);
          int currentQuestion = question;
      }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = questionBank[currentIndex].isTrueQuestion();
        int messageResId = 0;
        if(isCheater)
            messageResId = R.string.judgment_toast;
        else{
        if(userPressedTrue == answerIsTrue)
            messageResId = R.string.correct_toast;
        else
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT).show();
    }
}
