package com.rudainc.kickforread.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.ui.activities.MainActivity;
import com.rudainc.kickforread.utils.KickForReadPreferences;

/**
 * Implementation of App Widget functionality.
 */
public class BookInfoWidget extends AppWidgetProvider {

    //update it
//     KickForReadPreferences.setRecipeData(this, bakingSample.getName(),ingredients_text);
//        WidgetService.startActionUpdateRecipeWidget(this);

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews rv = getIngredientsListRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.tv_book_title);
    }

    private static RemoteViews getIngredientsListRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.book_info_widget);

        Log.i("WIDGET", KickForReadPreferences.getBookTitle(context)+" "+ KickForReadPreferences.getBookInfo(context));
        if (!KickForReadPreferences.getBookInfo(context).isEmpty()) {
            views.setTextViewText(R.id.tv_book_title, KickForReadPreferences.getBookTitle(context));
            views.setTextViewText(R.id.tv_book_title, KickForReadPreferences.getBookInfo(context));
        }
        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.tv_book_title, appPendingIntent);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startActionUpdateRecipeWidget(context);
    }



    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
