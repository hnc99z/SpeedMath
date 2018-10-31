package com.example.dell.speedmath;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity implements View.OnClickListener{
    public static final String FONT_PATH = "font/font.otf";
    private Fragment playFragment;
    private TextView tvBest;
    private TextView tvScore;
    private Button btPlay;
    private SharedPreferences pref;
    private int bestScore;
    private int score;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView() {
        score = 0;
        tvBest = (TextView) findViewById(R.id.tv_best_start);
        tvScore = (TextView) findViewById(R.id.tv_score_start);

        btPlay = (Button) findViewById(R.id.bt_play);
        btPlay.setOnClickListener(this);
        playFragment = new PlayFragment();

    }

    @Override
    public void onStart() {
        super.onStart();
        String strScore = getString(R.string.score,score);
        tvScore.setText(strScore);
        pref = this.getSharedPreferences(PlayFragment.SCORE, Context.MODE_PRIVATE);
        bestScore = pref.getInt(PlayFragment.SCORE,0);
        String strBest = getString(R.string.best, bestScore);
        tvBest.setText(strBest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_play:
                Intent intent = new Intent(MyActivity.this,PlayActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
