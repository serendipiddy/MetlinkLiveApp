package com.metlinkliveapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import com.metlinkliveapp.StopInfo;
import android.preference.PreferenceManager;

import java.util.List;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    public StopInfo getStop() {
        return stop;
    }

    public void setStop(StopInfo stop) {
        this.stop = stop;
    }

    public static StopInfo stop = new StopInfo("");

     void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText;
         
             widgetText = stop.getStopNumber();
         
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);


         SharedPreferences preferences = context.getSharedPreferences("widget_pref",0);
         Log.i("k", preferences.contains("widget_pref") + " ");
         this.setStop(new StopInfo(preferences.getString("stop","-1")));
         Log.i("k",preferences.getString("stop","-1"));

         List<Departure> info;
         info = stop.getInfo();
         if (info.isEmpty()) {
             views.setTextViewText(R.id.textViewLarge, "no results");
         }

         else {
             Log.i("NewAppWidget","here");
             views.setTextViewText(R.id.textViewLarge, info.get(0).toString());
         }



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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

