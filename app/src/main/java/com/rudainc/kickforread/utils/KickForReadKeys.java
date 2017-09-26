package com.rudainc.kickforread.utils;

public interface KickForReadKeys {

    int ID_LOADER = 44;

    String RECIPE_DATA = "recipe_data";

    String RECIPE_NAME = "name";

    String STEPS = "steps";

    String RECIPE_INGREDIENTS = "steps";

    //reminder tasks
    String ACTION_READ = "already_read";
    String ACTION_DISMISS_NOTIFICATION = "dismiss_notification";
    String ACTION_CHARGING_REMINDER = "charging_reminder";

// read notification
    int READ_REMINDER_NOTIFICATION_ID = 1234;
    //     This pending intent id is used to uniquely reference the pending intent
    int READ_REMINDER_PENDING_INTENT_ID = 5678;
    int ACTION_READ_PENDING_INTENT_ID = 22;
    int ACTION_IGNORE_PENDING_INTENT_ID = 23;

}
