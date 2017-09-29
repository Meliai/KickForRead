package com.rudainc.kickforread.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rudainc.kickforread.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String info =  String.format(context.getString(R.string.widget_info),
                PreferenceManager.getDefaultSharedPreferences(context).getString(BOOK_AUTHOR, ""),
                PreferenceManager.getDefaultSharedPreferences(context).getString(BOOK_CATEGORY, ""),
                dateFormat.format((HelpUtil.setCalendarDate(Long.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(BOOK_START, ""))).getTime())));
        return info;
    }

    public static boolean isBookAdded(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(IS_BOOK_ADDED, false);
    }
}
