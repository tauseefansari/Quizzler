package com.tauseef.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.DialogPreference;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mTrueButton, mFalseButton;
    TextView mQuestionTextView, mScoreTextView;
    int mIndex, mQuestion, mScore;
    boolean mGameOver;
    ProgressBar mProgressBar;

    private TrueFalse[] mQuestionBank = {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, false),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, true),
            new TrueFalse(R.string.question_7, false),
            new TrueFalse(R.string.question_8, true),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, false),
            new TrueFalse(R.string.question_11, true),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true),
            new TrueFalse(R.string.question_14,true),
            new TrueFalse(R.string.question_15,false)
    };

    final int PROGRESSBAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
            mGameOver = savedInstanceState.getBoolean("GameOver");
            if (mGameOver)
            {
                alert();
            }
        }
        else
        {
            mScore = 0;
            mIndex = 0;
        }

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mScoreTextView = findViewById(R.id.score);

        mScoreTextView.setText("Score : "+mScore+"/"+mQuestionBank.length);

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                updateQuestion();
            }
        });
    }

    private void updateQuestion()
    {
        mIndex = (mIndex + 1) % mQuestionBank.length;
        if (mIndex == 0)
        {
            mGameOver = true;
            alert();
        }
        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESSBAR_INCREMENT);
        mScoreTextView.setText("Score : "+mScore+"/"+mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelected)
    {
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();
        if (userSelected == correctAnswer)
        {
            mScore = mScore + 1;
            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    private void alert()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");
        alert.setCancelable(false);
        alert.setMessage("Congratulations! You Scored "+mScore+" points! out of "+mQuestionBank.length);
        alert.setNegativeButton("Retry!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
        outState.putBoolean("GameOver", mGameOver);
    }

}