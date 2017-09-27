package com.rudainc.kickforread.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class BooksContract {

    public static final class BookEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.rudainc.kickforread";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String PATH_BOOKS = "books";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_BOOKS)
                .build();

        public static final String TABLE_NAME = "books_list";
        public static final String COLUMN_AUTHOR = "book_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_START_DAY = "start_date";
        public static final String COLUMN_IS_FINISHED = "finished";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_LABEL = "label";

    }
}
