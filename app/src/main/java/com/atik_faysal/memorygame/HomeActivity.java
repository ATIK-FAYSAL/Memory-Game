package com.atik_faysal.memorygame;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

        //String variable
        private String difficulty;
        //Component variable
        private Button bDifficulty,bLevel,bPlay;
        private ListView list;
        private ImageView soundButton;
        //Class variable
        private SharedPreferenceAdapter sharedPreferencesData;

        private TextView txtScore;

        //Static variable declaration
        private final static String DIFFICULTY_LEVEL = "DifficultyLevel";
        private final static String SOUND = "sound";
        private final static String HIGHSCORE = "HighScore";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_home);
                initComponent();
                onButtonClick();
                buttonTextChanges();
        }

        private void initComponent()
        {
                bDifficulty = findViewById(R.id.bDefficulty);
                bPlay = findViewById(R.id.bPlay);
                soundButton = findViewById(R.id.imSound);
                txtScore = findViewById(R.id.txtScore);
                sharedPreferencesData = new SharedPreferenceAdapter(this);
                if(sharedPreferencesData.getSharedPreferencesValue(SOUND).equals("none"))
                {
                        soundButton.setImageDrawable(getResources().getDrawable(R.drawable.sound_on));
                        sharedPreferencesData.setSharedPreferencesValue(SOUND,"on");
                }
                if(sharedPreferencesData.getSharedPreferencesValue(SOUND).equals("off"))
                        soundButton.setImageDrawable(getResources().getDrawable(R.drawable.sound_off));

                if(!((sharedPreferencesData.getSharedPreferencesValue(HIGHSCORE).equals("none"))||(sharedPreferencesData.getSharedPreferencesValue(HIGHSCORE).equals("0"))))txtScore.setText("HIGH SCORE : "+sharedPreferencesData.getSharedPreferencesValue(HIGHSCORE));
                else txtScore.setText("HIGH SCORE : 0");

        }

        private void buttonTextChanges()
        {
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.difficulty_layout,null);
                RadioButton rButton1,rButton2,rButton3;
                rButton1 = view.findViewById(R.id.rEasy);
                rButton2 = view.findViewById(R.id.rMedium);
                rButton3 = view.findViewById(R.id.rHard);
                if(!sharedPreferencesData.getSharedPreferencesValue(DIFFICULTY_LEVEL).equals("none"))
                {
                        switch (sharedPreferencesData.getSharedPreferencesValue(DIFFICULTY_LEVEL))
                        {
                                case "easy" :
                                        rButton1.setChecked(true);
                                        bDifficulty.setText("Easy");
                                        break;
                                case "medium" :
                                        rButton2.setChecked(true);
                                        bDifficulty.setText("Medium");
                                        break;
                                case "hard" :
                                        rButton3.setChecked(true);
                                        bDifficulty.setText("Hard");
                                        break;
                        }
                }
        }

        private void onButtonClick()
        {
                bDifficulty.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                difficultyLevel();
                        }
                });


                bPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                if(sharedPreferencesData.getSharedPreferencesValue(DIFFICULTY_LEVEL).equals("none"))startActivity(new Intent(HomeActivity.this,LevelEasy.class));
                                else startGame();
                        }
                });

                soundButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                if(sharedPreferencesData.getSharedPreferencesValue(SOUND).equals("on"))
                                {
                                        sharedPreferencesData.setSharedPreferencesValue(SOUND,"off");
                                        soundButton.setImageDrawable(getResources().getDrawable(R.drawable.sound_off));
                                        Toast.makeText(HomeActivity.this,"off",Toast.LENGTH_SHORT).show();
                                }else if(sharedPreferencesData.getSharedPreferencesValue(SOUND).equals("off"))
                                {
                                        sharedPreferencesData.setSharedPreferencesValue(SOUND,"on");
                                        soundButton.setImageDrawable(getResources().getDrawable(R.drawable.sound_on));
                                        Toast.makeText(HomeActivity.this,"on",Toast.LENGTH_SHORT).show();
                                }

                        }
                });


        }

        private void difficultyLevel()
        {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.difficulty_layout,null);
                builder.setView(view);
                AlertDialog alertDialog  = builder.create();
                RadioButton rButton1,rButton2,rButton3;
                rButton1 = view.findViewById(R.id.rEasy);
                rButton2 = view.findViewById(R.id.rMedium);
                rButton3 = view.findViewById(R.id.rHard);
                if(!sharedPreferencesData.getSharedPreferencesValue(DIFFICULTY_LEVEL).equals("none"))
                {
                        switch (sharedPreferencesData.getSharedPreferencesValue(DIFFICULTY_LEVEL))
                        {
                                case "easy" :
                                        rButton1.setChecked(true);
                                        bDifficulty.setText("Easy");
                                        break;
                                case "medium" :
                                        rButton2.setChecked(true);
                                        bDifficulty.setText("Medium");
                                        break;
                                case "hard" :
                                        rButton3.setChecked(true);
                                        bDifficulty.setText("Hard");
                                        break;
                        }
                }
                alertDialog.show();
        }

        public void selectDifficultyLevel(View view)
        {
                boolean checked = ((RadioButton)view).isChecked();
                switch (view.getId())
                {
                        case R.id.rEasy :
                                if(checked)difficulty = "easy";
                                sharedPreferencesData.setSharedPreferencesValue(DIFFICULTY_LEVEL,difficulty);
                                bDifficulty.setText("Easy");
                                break;
                        case  R.id.rMedium :
                                if(checked)difficulty = "medium";
                                sharedPreferencesData.setSharedPreferencesValue(DIFFICULTY_LEVEL,difficulty);
                                bDifficulty.setText("Medium");
                                break;
                        case R.id.rHard :
                                if(checked)difficulty = "hard";
                                sharedPreferencesData.setSharedPreferencesValue(DIFFICULTY_LEVEL,difficulty);
                                bDifficulty.setText("Hard");
                                break;
                }
        }

        private void startGame()
        {
                if(!sharedPreferencesData.getSharedPreferencesValue(DIFFICULTY_LEVEL).equals("none"))
                {
                        switch (sharedPreferencesData.getSharedPreferencesValue(DIFFICULTY_LEVEL))
                        {
                                case "hard" :startActivity(new Intent(HomeActivity.this,LevelHard.class));
                                        break;
                                case "medium" :startActivity(new Intent(HomeActivity.this,LevelMedium.class));
                                        break;
                                case "easy" :startActivity(new Intent(HomeActivity.this,LevelEasy.class));
                                        break;
                        }
                        finish();
                }

        }

}
