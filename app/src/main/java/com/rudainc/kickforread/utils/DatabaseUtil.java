package com.rudainc.kickforread.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rudainc.kickforread.database.DaysContract;

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
        cv.put(DaysContract.DayEntry.COLUMN_DATE,date.toString());

        mDb.insert(DaysContract.DayEntry.TABLE_NAME, null, cv);
    }


//    public void removeDa(String id) {
//        mDb.delete(DaysContract.DayEntry.TABLE_NAME, DaysContract.DayEntry.COLUMN_MOVIE_ID + "=" + id, null);
//    }

    public boolean checkIsDataAlreadyInDBorNot(Date date, SQLiteDatabase mDb) {
        String Query = "Select * from " + DaysContract.DayEntry.TABLE_NAME + " where " + DaysContract.DayEntry.COLUMN_DATE + " = " + date.toString();
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
