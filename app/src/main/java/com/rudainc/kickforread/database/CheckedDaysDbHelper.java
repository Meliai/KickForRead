package com.rudainc.kickforread.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.rudainc.kickforread.database.DaysContract.*;


public class CheckedDaysDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "checked_days.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public CheckedDaysDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_DAYS_TABLE = "CREATE TABLE " + DayEntry.TABLE_NAME + " (" +
                DayEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DayEntry.COLUMN_DATE + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_DAYS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DayEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}