package com.rudainc.kickforread.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class KickForReadPreferences implements KickForReadKeys{

    public static void setBookData(Context context, String recipe_name, String ingredients){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(RECIPE_NAME, recipe_name).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(RECIPE_INGREDIENTS, ingredients).apply();
    }

    public static String getBookTitle(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(RECIPE_NAME, "");
    }

    public static String getBookInfo(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(RECIPE_INGREDIENTS, "");
    }
}
