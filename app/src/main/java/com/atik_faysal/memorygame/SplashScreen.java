package com.atik_faysal.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by USER on 12/8/2017.
 */

public class SplashScreen extends AppCompatActivity
{
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.splash_screen);
                threadStart();
        }

        protected void threadStart()
        {
                Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try
                                {
                                        Thread.sleep(2000);
                                        startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                                        finish();
                                }catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                        }
                });

                thread.start();
        }
}
