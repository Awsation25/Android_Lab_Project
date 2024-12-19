package com.example.awsshaheen_ghassanqandeel_encs5150_project;

import android.content.Context;
import android.content.SharedPreferences;

public class EmailSharedPrefManager {
    private static final String SHARED_PREF_NAME = "Email SharedPrefManager";
    private static final int SHARED_PREF_PRIVATE = Context.MODE_PRIVATE;
    private static EmailSharedPrefManager ourInstance = null;
    private static SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public static EmailSharedPrefManager getInstance(Context context) {
        if (ourInstance != null) {
            return ourInstance;
        }
        ourInstance = new EmailSharedPrefManager(context);
        return ourInstance;
    }

    public EmailSharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean writeString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}
