package com.verbosetech.cookfu.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.verbosetech.cookfu.model.CartItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CutsomSharedPreferences {

    public static final String LAT = "LAT";
    public static final String LONG = "LONG";
    public static final String USER_ID = "USER_ID";
    public static final String PASSWORD = "PASSWORD";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_ADDRESS = "USER_ADDRESS";
    public static final String USER_PIN = "USER_PIN";
    public static final String USER_STATE = "USER_STATE";
    public static final String USER_CITY = "USER_CITY";
    public static final String USER_COUNTRY = "USER_COUNTRY";
    public static final String MARCHANT_ID = "marchant_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String ITEM_INCART = "0";
    public static final String TEMP_MARCHANT_ID= "0";
    private static SharedPreferences mSharedPref;
//    public static final String = "";
//    public static final String = "";
//    public static final String = "";
//    public static final String = "";
//    public static final String = "";
//    public static final String = "";
//    public static final String = "";
//    public static final String = "";

    public CutsomSharedPreferences() {

    }

    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String Read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void Write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }
}
