package com.example.dell.speedmath;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class PlayFragment extends Fragment implements View.OnClickListener, Runnable {
    public static final int TIME = 3;
    public static final String SCORE = "Score";

    private View rootView;
    private TextView tvBest;
    private TextView tvScore;
    private TextView tvMath;
    private TextView tvTime;
    private ImageButton btLeft;
    private ImageButton btRight;
    private Random mRandom;
    private int number1;
    private int number2;
    private int number3;
    private int answer;
    private int time;
    private int score;
    private boolean isResult;
    private boolean isCheck;
    private SharedPreferences pref;
    private Handler mHandler;
    private int bestScore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.play_layout, container, false);
        initView();
        typeFace();
        score = 0;
        mRandom = new Random();
        mHandler = new Handler();
        randomQuestion();
        setGameStatus();
        return rootView;
    }

    private void initView() {
        tvBest = (TextView) rootView.findViewById(R.id.tv_best_play);
        tvScore = (TextView) rootView.findViewById(R.id.tv_score_play);
        tvMath = (TextView) rootView.findViewById(R.id.tv_math);
        tvTime = (TextView) rootView.findViewById(R.id.tv_time);
        btLeft = (ImageButton) rootView.findViewById(R.id.bt_left);
        btRight = (ImageButton) rootView.findViewById(R.id.bt_right);
        btLeft.setOnClickListener(this);
        btRight.setOnClickListener(this);


    }

    private void setGameStatus() {
        String strScore = getString(R.string.score, score);
        tvScore.setText(strScore);
        pref = getActivity().getSharedPreferences(SCORE, Context.MODE_PRIVATE);
        bestScore = pref.getInt(SCORE, 0);
        String strBest = getString(R.string.best, bestScore);
        tvBest.setText(strBest);
    }

    private void typeFace() {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), MyActivity.FONT_PATH);
        tvBest.setTypeface(typeface);
        tvScore.setTypeface(typeface);
        tvMath.setTypeface(typeface);
        tvTime.setTypeface(typeface);
    }

    private void randomQuestion() {
        time = TIME;
        number1 = mRandom.nextInt(10);
        number2 = mRandom.nextInt(10);
        answer = number1 + number2;
        isResult = mRandom.nextBoolean();
        if (isResult == true) {
            number3 = answer;
            isCheck = true;
        } else {
            if (number1 % 2 == 0) {
                number3 = answer + 1;
                isCheck = false;
            } else {
                number3 = answer - 1;
                isCheck = false;
            }
        }
        String result = number1 + " + " + number2 + " = " + number3;
        tvMath.setText(result);
        for (int i = 0; i < time + 1; i++) {
            mHandler.postDelayed(this, i * 1000);
        }
    }

    @Override
    public void onClick(View v) {
        mHandler.removeCallbacksAndMessages(null);
        switch (v.getId()) {
            case R.id.bt_left:
                if (isCheck == false) {
                    score++;
                    setGameStatus();
                    randomQuestion();

                } else {
                    gameOver();
                }
                break;
            case R.id.bt_right:
                if (isCheck == true) {
                    score++;
                    setGameStatus();
                    randomQuestion();
                } else {
                    gameOver();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        tvTime.setText(time + "");
        time--;
        if (time == -1) {
            gameOver();
        }
    }

    private void gameOver() {
        if (score > bestScore) {
            pref.edit().putInt(SCORE, score).commit();
        }
        DialogFragment myDialog = MyDialog.newInstance(score);
        myDialog.show(getFragmentManager(),"Dialog");
    }


}
