package com.rudainc.kickforread.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rudainc.kickforread.database.BooksContract.*;

public class BooksDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "books.db";

    private static final int DATABASE_VERSION = 1;

    public BooksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BookEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                BookEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                BookEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                BookEntry.COLUMN_LABEL + " TEXT NOT NULL, " +
                BookEntry.COLUMN_START_DAY + " TEXT NOT NULL, " +
                BookEntry.COLUMN_IS_FINISHED + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}