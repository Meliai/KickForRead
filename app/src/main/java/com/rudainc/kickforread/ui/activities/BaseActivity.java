package com.rudainc.kickforread.ui.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.database.CheckedDaysDbHelper;
import com.rudainc.kickforread.database.DaysContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {

    private View mCustomSnackBarView;

    private static SQLiteDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckedDaysDbHelper dbHelper = new CheckedDaysDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    private Snackbar initSnackBar(String message, boolean isError) {
        View snackBarParent = null;

        if (mCustomSnackBarView != null) {
            snackBarParent = mCustomSnackBarView;
        } else if (findViewById(android.R.id.content) != null) {
            snackBarParent = findViewById(android.R.id.content);
        }
        if (snackBarParent != null) {
            Snackbar snackbar = Snackbar.make(snackBarParent, message, Snackbar.LENGTH_SHORT).setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            snackbar.getView().setBackgroundColor(isError?ContextCompat.getColor(this,R.color.colorPrimary):ContextCompat.getColor(this,R.color.colorAccent));
            return snackbar;
        }
        return null;
    }


    public void showSnackBar(String message, boolean isError) {
        Snackbar snackbar = initSnackBar(message,isError);
        if (snackbar != null)
            snackbar.show();

    }


    public static ArrayList<Long> getAllCheckedDays(Cursor cursor) {
        ArrayList<Long> mArrayList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            mArrayList.add(cursor.getLong(cursor.getColumnIndex(DaysContract.DayEntry.COLUMN_DATE)));
        }
        Log.i("MYDB", mArrayList.size()+"");
        return mArrayList;
    }




    public static void addDate(long date) {
        ContentValues cv = new ContentValues();
        cv.put(DaysContract.DayEntry.COLUMN_DATE,date);

        mDb.insert(DaysContract.DayEntry.TABLE_NAME, null, cv);
    }


//    public void removeDa(String id) {
//        mDb.delete(DaysContract.DayEntry.TABLE_NAME, DaysContract.DayEntry.COLUMN_DATE + "=" + date, null);
//    }

    public static boolean checkIsDataAlreadyInDBorNot(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String Query = "Select * from " + DaysContract.DayEntry.TABLE_NAME + " where " + DaysContract.DayEntry.COLUMN_DATE + " = " + calendar.getTimeInMillis();
        Cursor cursor = mDb.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}
