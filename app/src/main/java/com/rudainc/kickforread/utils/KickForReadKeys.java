package com.rudainc.kickforread.utils;

public interface KickForReadKeys {


    String BOOK_NAME = "book_name";
    String BOOK_AUTHOR = "book_author";
    String BOOK_CATEGORY = "book_category";
    String BOOK_START = "book_start";
    String IS_BOOK_ADDED = "isBook";
    String BOOK_DATA = "book_data";

    //Clicker
    int CLICK_BOOK_ITEM = 1;
    int CLICK_BOOK_FINISHED = 2;

    //Loader
    int ID_LOADER_DAYS = 44;
    int ID_LOADER_BOOKS = 55;

    //reminder tasks
    String ACTION_READ = "already_read";
    String ACTION_DISMISS_NOTIFICATION = "dismiss_notification";
    String ACTION_CHARGING_REMINDER = "charging_reminder";

    // read notification
    int READ_REMINDER_NOTIFICATION_ID = 1234;
    //  This pending intent id is used to uniquely reference the pending intent
    int READ_REMINDER_PENDING_INTENT_ID = 5678;
    int ACTION_READ_PENDING_INTENT_ID = 22;
    int ACTION_IGNORE_PENDING_INTENT_ID = 23;

}
