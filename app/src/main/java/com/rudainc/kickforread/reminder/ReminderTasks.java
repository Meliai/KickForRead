/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rudainc.kickforread.reminder;

import android.content.Context;
import android.util.Log;

import com.rudainc.kickforread.ui.activities.BaseActivity;
import com.rudainc.kickforread.utils.KickForReadKeys;
import com.rudainc.kickforread.utils.NotificationUtils;

import java.util.Calendar;


public class ReminderTasks implements KickForReadKeys {

    public static void executeTask(Context context, String action) {
        Log.i("WTF", action);
        if (ACTION_READ.equals(action)) {
            alreadyRead(context);
        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_CHARGING_REMINDER.equals(action)) {
            issueChargingReminder(context);
        }
    }

    private static void alreadyRead(Context context) {
        Calendar calendar = Calendar.getInstance();
        if (!BaseActivity.checkIsDataAlreadyInDBorNot(calendar.getTimeInMillis())) {
            BaseActivity.addDate(calendar.getTimeInMillis());
            NotificationUtils.clearAllNotifications(context);
        }
    }

    private static void issueChargingReminder(Context context) {
        NotificationUtils.remindUserBecauseCharging(context);
    }

}