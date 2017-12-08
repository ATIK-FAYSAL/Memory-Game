package com.atik_faysal.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by USER on 12/7/2017.
 */

public class ScoreCard extends AppCompatActivity
{
        private TextView txtTime,txtLose,txtDifficulty,txtScore;

        private final static String HIGHSCORE = "HighScore";

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.game_score);
                initComponent();
        }


        @Override
        public void onBackPressed() {
                super.onBackPressed();
                startActivity(new Intent(ScoreCard.this,HomeActivity.class));
        }

        private void initComponent()
        {
                int time,lose,score;
                String difficulty;

                time = getIntent().getExtras().getInt("time");
                lose = getIntent().getExtras().getInt("lose");
                score = getIntent().getExtras().getInt("score");
                difficulty = getIntent().getExtras().getString("difficulty");

                score = score - time*10;
                setHighScore(score);
                txtTime = findViewById(R.id.txtTime);
                txtLose = findViewById(R.id.txtTries);
                txtDifficulty = findViewById(R.id.txtDifficulty);
                txtScore = findViewById(R.id.txtScore);
                txtTime.setText("TIME : "+time+" Second");
                txtLose.setText("TRIES : "+lose+" time");
                txtDifficulty.setText("DIFFICULTY : "+difficulty);
                txtScore.setText("SCORE : "+score+"");
        }

        private void setHighScore(int score)
        {
                SharedPreferenceAdapter sharedPreferenceAdapter = new SharedPreferenceAdapter(this);
                if(sharedPreferenceAdapter.getSharedPreferencesValue(HIGHSCORE).equals("none"))sharedPreferenceAdapter.setSharedPreferencesValue(HIGHSCORE,String.valueOf(0));
                if(!sharedPreferenceAdapter.getSharedPreferencesValue(HIGHSCORE).equals("none"))
                {
                        int getScore = Integer.parseInt(sharedPreferenceAdapter.getSharedPreferencesValue(HIGHSCORE));
                        if(score>getScore)sharedPreferenceAdapter.setSharedPreferencesValue(HIGHSCORE,String.valueOf(score));
                }
        }
}
