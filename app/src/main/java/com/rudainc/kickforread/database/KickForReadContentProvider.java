package com.rudainc.kickforread.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rudainc.kickforread.R;

public class KickForReadContentProvider extends ContentProvider {


    public static final int CODE_DAYS = 100;
    public static final int CODE_BOOKS = 200;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private CheckedDaysDbHelper mCheckedDaysDbHelper;

    private BooksDbHelper mBooksDbHelper;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DaysContract.DayEntry.CONTENT_AUTHORITY;

        matcher.addURI(authority, DaysContract.DayEntry.PATH_DAYS, CODE_DAYS);
        matcher.addURI(authority, BooksContract.BookEntry.PATH_BOOKS, CODE_BOOKS);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mCheckedDaysDbHelper = new CheckedDaysDbHelper(getContext());
        mBooksDbHelper = new BooksDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase dbDays = mCheckedDaysDbHelper.getWritableDatabase();
        final SQLiteDatabase dbBooks = mBooksDbHelper.getWritableDatabase();
        int rowsInserted = 0;
        Log.i("MyUri", uri + "");
        switch (sUriMatcher.match(uri)) {

            case CODE_DAYS:
                dbDays.beginTransaction();

                try {
                    for (ContentValues value : values) {


                        long _id = dbDays.insert(DaysContract.DayEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    dbDays.setTransactionSuccessful();
                } finally {
                    dbDays.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;

            case CODE_BOOKS:
                dbBooks.beginTransaction();

                try {
                    for (ContentValues value : values) {


                        long _id = dbBooks.insert(BooksContract.BookEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    dbBooks.setTransactionSuccessful();
                } finally {
                    dbBooks.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            case CODE_DAYS: {
                cursor = mCheckedDaysDbHelper.getReadableDatabase().query(
                        DaysContract.DayEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            case CODE_BOOKS: {
                cursor = mBooksDbHelper.getReadableDatabase().query(
                        BooksContract.BookEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.unknown_uri) + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException(getContext().getString(R.string.provider_gettype));
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new RuntimeException(getContext().getString(R.string.provider_insert));
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new RuntimeException(getContext().getString(R.string.provider_update));
    }


}