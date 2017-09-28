package com.rudainc.kickforread.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class KickForReadPreferences implements KickForReadKeys{

    public static void setBookData(Context context, String recipe_name, String ingredients){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BOOK_NAME, recipe_name).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BOOK_DATA, ingredients).apply();
    }

    public static String getBookTitle(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(BOOK_NAME, "");
    }

    public static String getBookInfo(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(BOOK_DATA, "");
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
