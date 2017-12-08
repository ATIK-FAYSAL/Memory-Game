package com.atik_faysal.memorygame;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by USER on 12/6/2017.
 */

public class SharedPreferenceAdapter
{
        private Context context;

        private SharedPreferences sharedPreferences;
        private SharedPreferences.Editor editor ;
        public SharedPreferenceAdapter(Context context)
        {
                this.context = context;
        }


        public void setSharedPreferencesValue(String prefName,String value)
        {
                sharedPreferences = context.getSharedPreferences(prefName,context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("difficulty",value);
                editor.apply();
        }

        public String getSharedPreferencesValue(String prefName)
        {
                String value;
                sharedPreferences = context.getSharedPreferences(prefName,context.MODE_PRIVATE);
                value = sharedPreferences.getString("difficulty","none");
                return value;
        }
}
