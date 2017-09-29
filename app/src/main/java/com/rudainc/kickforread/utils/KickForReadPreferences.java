package com.rudainc.kickforread.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class KickForReadPreferences implements KickForReadKeys{

    public static void setBookData(Context context, String book_name, String book_author, String book_category, String book_start){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BOOK_NAME, book_name).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BOOK_AUTHOR, book_author).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BOOK_CATEGORY, book_category).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BOOK_START, book_start).apply();
    }

    public static void setBookAdded(Context context, boolean isBook){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(IS_BOOK_ADDED, isBook).apply();
    }

    public static String getBookTitle(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(BOOK_NAME, "");
    }

    public static String getBookInfo(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(BOOK_AUTHOR, "");
    }

    public static boolean isBookAdded(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(IS_BOOK_ADDED, false);
    }

    public static final String KEY_WATER_COUNT = "water-count";
    public static final String KEY_CHARGING_REMINDER_COUNT = "charging-reminder-count";

    private static final int DEFAULT_COUNT = 0;

    synchronized private static void setWaterCount(Context context, int glassesOfWater) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_WATER_COUNT, glassesOfWater);
        editor.apply();
    }

    public static int getWaterCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int glassesOfWater = prefs.getInt(KEY_WATER_COUNT, DEFAULT_COUNT);
        return glassesOfWater;
    }

    synchronized public static void incrementWaterCount(Context context) {
        int waterCount = KickForReadPreferences.getWaterCount(context);
        KickForReadPreferences.setWaterCount(context, ++waterCount);
    }

    synchronized public static void incrementChargingReminderCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int chargingReminders = prefs.getInt(KEY_CHARGING_REMINDER_COUNT, DEFAULT_COUNT);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_CHARGING_REMINDER_COUNT, ++chargingReminders);
        editor.apply();
    }

    public static int getChargingReminderCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int chargingReminders = prefs.getInt(KEY_CHARGING_REMINDER_COUNT, DEFAULT_COUNT);
        return chargingReminders;
    }
}
