package com.atik_faysal.memorygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.Button;
import android.widget.GridLayout;


@SuppressLint("ViewConstructor")
public class MemoryGameImageButton extends android.support.v7.widget.AppCompatButton
{

        private int row,column,fontImageId;

        private Drawable fontImage,backImage;

        private boolean isImageMatch,isImageFliped;


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @SuppressLint("RestrictedApi")

        public MemoryGameImageButton(Context context, int row, int col, int fontImageId) {
                super(context);
                this.row = row;
                this.column = col;
                this.fontImageId = fontImageId;

                fontImage = AppCompatDrawableManager.get().getDrawable(context,fontImageId);
                backImage = AppCompatDrawableManager.get().getDrawable(context,R.drawable.ques);
                setBackground(backImage);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row),GridLayout.spec(col));

                params.width = (int)getResources().getDisplayMetrics().density * 60;
                params.height = (int)getResources().getDisplayMetrics().density * 60;

                setLayoutParams(params);
        }


        public int getFontImageId() {
                return fontImageId;
        }

        public boolean isImageMatch() {
                return isImageMatch;
        }

        public void setImageMatch(boolean imageMatch) {
                isImageMatch = imageMatch;
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void imageFliped()
        {
                if(isImageMatch)return;

                if(isImageFliped)
                {
                        setBackground(backImage);
                        isImageFliped = false;
                }else
                {
                        setBackground(fontImage);
                        isImageFliped = true;
                }
        }
}
