package com.rudainc.kickforread.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class BooksListContentProvider extends ContentProvider {


    public static final int CODE = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BooksDbHelper mBooksDbHelper;


    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BooksContract.BookEntry.CONTENT_AUTHORITY;

        matcher.addURI(authority, BooksContract.BookEntry.PATH_BOOKS , CODE);

        return matcher;
    }


    @Override
    public boolean onCreate() {

        mBooksDbHelper = new BooksDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mBooksDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case CODE:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {


                        long _id = db.insert(BooksContract.BookEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
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
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;


        switch (sUriMatcher.match(uri)) {

            case CODE: {
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
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {


        int numRowsDeleted;


        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE:
                numRowsDeleted = mBooksDbHelper.getWritableDatabase().delete(
                       BooksContract.BookEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in KickForRead.");
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new RuntimeException(
                "We are not implementing insert in BooksDb. Use bulkInsert instead");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new RuntimeException("We are not implementing update in BooksDb");
    }


}