package com.rudainc.kickforread.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DaysContract {

    public static final class DayEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.rudainc.kickforread";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String PATH_DAYS = "checked_days";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_DAYS)
                .build();

        public static final String TABLE_NAME = "checked_days_list";
        public static final String COLUMN_DATE = "checked_date";

    }
}
