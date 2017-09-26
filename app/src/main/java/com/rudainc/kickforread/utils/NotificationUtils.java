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
package com.rudainc.kickforread.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.reminder.ReminderTasks;
import com.rudainc.kickforread.reminder.ReadReminderIntentService;
import com.rudainc.kickforread.ui.activities.MainActivity;


public class NotificationUtils implements KickForReadKeys{

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserBecauseCharging(Context context) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.read_reminder_notification_title))
                .setContentText(context.getString(R.string.read_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.read_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
//                .addAction(readAction(context))
//                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        /* READ_REMINDER_NOTIFICATION_ID allows you to update or cancel the notification later on */
        notificationManager.notify(READ_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }
    private static Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, ReadReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Action ignoreReminderAction = new Action(R.drawable.ic_keyboard_arrow_left_black_48dp,
                "No, thanks.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    private static Action readAction(Context context) {
        Intent read = new Intent(context, ReadReminderIntentService.class);
        read.setAction(ReminderTasks.ACTION_READ);
        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_READ_PENDING_INTENT_ID,
                read,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Action readAction = new Action(R.drawable.ic_keyboard_arrow_right_black_48dp,
                "I did it!",
                incrementWaterPendingIntent);
        return readAction;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                READ_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_done_white_48dp);
        return largeIcon;
    }
}