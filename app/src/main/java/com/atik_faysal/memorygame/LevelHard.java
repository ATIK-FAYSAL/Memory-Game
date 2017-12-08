package com.atik_faysal.memorygame;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by USER on 12/8/2017.
 */

public class LevelHard extends AppCompatActivity implements View.OnClickListener
{
        private MemoryGameImageButton[] memoryGameImageButtons;

        private int[] buttonLocation;
        private int[] buttonGrapics;

        private int numberOfImage,numberOfRow,numberOfColomn;
        private int counter=0;
        private int lose = 0;
        private int timeCount = 1;
        private int score;
        private MemoryGameImageButton selectedButton1;
        private MemoryGameImageButton selectedButton2;

        private boolean isButtonOpen;

        private MemoryGameImageButton memoryGameImageButton;
        private SharedPreferenceAdapter sharedPreferenceAdapter;
        private final static String GameLevel = "GameLevel";

        private GridLayout gridLayout;
        private final static String SOUND = "sound";

        private MediaPlayer mediaPlayer;
        private MediaPlayer mediaPlayer1;

        private CountDownTimer timer;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.easy_level1);
                initializeVariable();
        }

        @Override
        public void onBackPressed() {
                super.onBackPressed();
                timer.cancel();
                startActivity(new Intent(LevelHard.this,HomeActivity.class));
        }


        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private void initializeVariable()
        {
                sharedPreferenceAdapter = new SharedPreferenceAdapter(this);
                gridLayout = findViewById(R.id.gridLayout);
                gridLayout.setRowCount(6);
                gridLayout.setColumnCount(6);
                numberOfColomn = gridLayout.getColumnCount();
                numberOfRow = gridLayout.getRowCount();
                numberOfImage = numberOfRow*numberOfColomn;
                memoryGameImageButtons = new MemoryGameImageButton[numberOfImage];
                buttonGrapics = new int[numberOfImage/2];

                buttonGrapics[0] = R.drawable.a;
                buttonGrapics[1] = R.drawable.b;
                buttonGrapics[2] = R.drawable.c;
                buttonGrapics[3] = R.drawable.d;
                buttonGrapics[4] = R.drawable.e;
                buttonGrapics[5] = R.drawable.f;
                buttonGrapics[6] = R.drawable.g;
                buttonGrapics[7] = R.drawable.j;
                buttonGrapics[8] = R.drawable.p;
                buttonGrapics[9] = R.drawable.x;
                buttonGrapics[10] = R.drawable.z;
                buttonGrapics[11] = R.drawable.y;
                buttonGrapics[12] = R.drawable.h;
                buttonGrapics[13] = R.drawable.l;
                buttonGrapics[14] = R.drawable.q;
                buttonGrapics[15] = R.drawable.w;
                buttonGrapics[16] = R.drawable.s;
                buttonGrapics[17] = R.drawable.t;
                buttonLocation = new int[numberOfImage];


                mediaPlayer = MediaPlayer.create(this,R.raw.wrong);
                mediaPlayer1 = MediaPlayer.create(this,R.raw.win_sound);
                timeCount();
                randomImage();
                setImageInGridLayout();

        }

        private void randomImage()
        {
                int tempIndex,swapIndex;

                Random random = new Random();

                for (int i=0;i<numberOfImage;i++)buttonLocation[i] = i % (numberOfImage/2);

                for(int i=0;i<numberOfImage;i++)
                {
                        tempIndex = buttonLocation[i];
                        swapIndex = random.nextInt(36);
                        buttonLocation[i] = buttonLocation[swapIndex];
                        buttonLocation[swapIndex] = tempIndex;
                }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private void setImageInGridLayout()
        {
                for(int row=0;row<numberOfRow;row++)
                {
                        for(int col=0;col<numberOfColomn;col++)
                        {
                                memoryGameImageButton = new MemoryGameImageButton(this,row,col,buttonGrapics[buttonLocation[row * numberOfColomn+col]]);
                                memoryGameImageButton.setId(View.generateViewId());
                                memoryGameImageButton.setOnClickListener(this);
                                memoryGameImageButtons[row * numberOfColomn + col] = memoryGameImageButton;
                                gridLayout.addView(memoryGameImageButton);
                        }
                }
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {

                MemoryGameImageButton memoryGameImageButton = (MemoryGameImageButton)view;

                if(memoryGameImageButton.isImageMatch())return;

                if(selectedButton1 == null)
                {
                        selectedButton1 = memoryGameImageButton;
                        selectedButton1.imageFliped();
                        return;
                }

                if(selectedButton1.getFontImageId() == memoryGameImageButton.getFontImageId())
                {
                        memoryGameImageButton.imageFliped();
                        selectedButton1.setImageMatch(true);
                        memoryGameImageButton.setImageMatch(true);
                        selectedButton1.setEnabled(false);
                        memoryGameImageButton.setEnabled(false);
                        selectedButton1 = null;
                        counter++;
                        if(counter==numberOfImage/2)
                        {
                                Intent page = new Intent(LevelHard.this,ScoreCard.class);
                                page.putExtra("time",timeCount);
                                page.putExtra("lose",lose);
                                page.putExtra("score",5000);
                                page.putExtra("difficulty","Easy");
                                startActivity(page);
                                if(sharedPreferenceAdapter.getSharedPreferencesValue(SOUND).equals("on"))mediaPlayer1.start();
                                sharedPreferenceAdapter.setSharedPreferencesValue(GameLevel,"2");
                                timer.cancel();
                                finish();
                        }
                        return;
                }else
                {
                        selectedButton2 = memoryGameImageButton;
                        selectedButton2.imageFliped();
                        isButtonOpen = true;
                        lose++;
                        if(sharedPreferenceAdapter.getSharedPreferencesValue(SOUND).equals("on"))mediaPlayer.start();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                        selectedButton1.imageFliped();
                                        selectedButton2.imageFliped();
                                        selectedButton1 = null;
                                        selectedButton2 = null;
                                        isButtonOpen = false;
                                }
                        },400);
                }
        }

        private void timeCount()
        {
                timer =  new CountDownTimer(1000000, 1000) {
                        @Override
                        public void onTick(long l) {
                                if(timeCount==80)
                                {
                                        Intent page = new Intent(LevelHard.this,ScoreCard.class);
                                        page.putExtra("time",timeCount);
                                        page.putExtra("lose",lose);
                                        page.putExtra("score",5000);
                                        page.putExtra("difficulty","Easy");
                                        startActivity(page);
                                        timer.cancel();
                                        Toast.makeText(LevelHard.this,"Time Out",Toast.LENGTH_SHORT).show();
                                        finish();

                                }
                                timeCount++;
                        }

                        @Override
                        public void onFinish() {

                        }
                }.start();
        }
}

