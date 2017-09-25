package com.rudainc.kickforread.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rudainc.kickforread.database.FavoritesContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseUtil {

    public ArrayList<Date> getAllCheckedDays(Cursor cursor) {

        ArrayList<Date> mArrayList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            mArrayList.add(new Date());
        }
        Log.i("MYDB", mArrayList.size()+"");
        return mArrayList;
    }




    public void addDate(Date date, SQLiteDatabase mDb) {
        ContentValues cv = new ContentValues();
        cv.put(FavoritesContract.MovieEntry.COLUMN_DATE,date.toString());

        mDb.insert(FavoritesContract.MovieEntry.TABLE_NAME, null, cv);
    }


//    public void removeDa(String id) {
//        mDb.delete(FavoritesContract.MovieEntry.TABLE_NAME, FavoritesContract.MovieEntry.COLUMN_MOVIE_ID + "=" + id, null);
//    }

    public boolean checkIsDataAlreadyInDBorNot(Date date, SQLiteDatabase mDb) {
        String Query = "Select * from " + FavoritesContract.MovieEntry.TABLE_NAME + " where " + FavoritesContract.MovieEntry.COLUMN_DATE + " = " + date.toString();
        Cursor cursor = mDb.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("d-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
