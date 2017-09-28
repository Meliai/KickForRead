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
import com.rudainc.kickforread.database.BooksContract;
import com.rudainc.kickforread.database.BooksDbHelper;
import com.rudainc.kickforread.database.CheckedDaysDbHelper;
import com.rudainc.kickforread.database.DaysContract;
import com.rudainc.kickforread.models.BooksModel;
import com.rudainc.kickforread.reminder.ReminderUtilities;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    private View mCustomSnackBarView;

    private static SQLiteDatabase mDbDays;
    private static SQLiteDatabase mDbBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckedDaysDbHelper dbHelper = new CheckedDaysDbHelper(this);
        mDbDays = dbHelper.getWritableDatabase();
        BooksDbHelper booksDbHelper = new BooksDbHelper(this);
        mDbBooks = booksDbHelper.getWritableDatabase();

        ReminderUtilities.scheduleChargingReminder(this);
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

        mDbDays.insert(DaysContract.DayEntry.TABLE_NAME, null, cv);
    }


//    public void removeDa(String id) {
//        mDbDays.delete(DaysContract.DayEntry.TABLE_NAME, DaysContract.DayEntry.COLUMN_DATE + "=" + date, null);
//    }

    public static boolean checkIsDataAlreadyInDBorNot(long date) {
        Log.i("DATE", date + "");

        String Query = "Select * from " + DaysContract.DayEntry.TABLE_NAME + " where " + DaysContract.DayEntry.COLUMN_DATE + " = " + date;
        Cursor cursor = mDbDays.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
//
    public static ArrayList<BooksModel> getAllBooks(Cursor cursor) {
        ArrayList<BooksModel> mArrayList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            mArrayList.add(new BooksModel(String.valueOf(cursor.getLong(cursor.getColumnIndex("_id"))), cursor.getString(cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_AUTHOR)),
                    cursor.getString(cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_LABEL)),
                    cursor.getString(cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_START_DAY)),
                    cursor.getString(cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_IS_FINISHED))));
        }
        Log.i("MYDB", mArrayList.size()+"");
        return mArrayList;
    }




    public static void addBook(String title, String author, String category, String label, String start_day) {
        ContentValues cv = new ContentValues();
        cv.put(BooksContract.BookEntry.COLUMN_TITLE,title);
        cv.put(BooksContract.BookEntry.COLUMN_AUTHOR,author);
        cv.put(BooksContract.BookEntry.COLUMN_CATEGORY,category);
        cv.put(BooksContract.BookEntry.COLUMN_LABEL,label);
        cv.put(BooksContract.BookEntry.COLUMN_START_DAY,start_day);
        cv.put(BooksContract.BookEntry.COLUMN_IS_FINISHED,"false");

        mDbBooks.insert(BooksContract.BookEntry.TABLE_NAME, null, cv);
    }


    public static void updateBook(BooksModel booksModel) {
        Log.i("Book",booksModel.getId());
        ContentValues cv = new ContentValues();
        cv.put(BooksContract.BookEntry.COLUMN_IS_FINISHED,"true"); //These Fields should be your String values of actual column names

        mDbBooks.update(BooksContract.BookEntry.TABLE_NAME, cv, "_id="+booksModel.getId(), null);
    }

    public static boolean checkIsBookAlreadyInDBorNot(BooksModel book) {
        Log.i("book", book.getTitle() + "");

        String Query = "Select * from " + BooksContract.BookEntry.TABLE_NAME + " where " + BooksContract.BookEntry.COLUMN_TITLE + " = " + book.getTitle();
        Cursor cursor = mDbBooks.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
